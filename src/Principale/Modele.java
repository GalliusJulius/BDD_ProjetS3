package Principale;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controlleurs.ChangementFenetre;
import graphique.AffichageAdmin;
import graphique.AffichageAppli;
import graphique.Fenetre;
import graphique.AffichageConnection;

/**
 * Modele de la vue (controle tout)
 * @author moreliere
 *
 */
public class Modele extends Observable{
	
	private JPanel fenetreActu, resultat;
	private JFrame fen;
	private int width,heigth;
	private boolean modeAgence;
	private Date dateD, dateF;
	private String catVehicule ="Toutes catégories";
	private Connection cnt;
	
	public Modele(int w,int h) {
		fenetreActu=new AffichageConnection(w,h,this);
		width=w;
		heigth=h;
		fen=new Fenetre(fenetreActu);
		resultat = new JPanel();
	}
	
	/**
	 * Permet de se connecter a la base
	 * @throws SQLException 
	 */
	public void seConnecter() throws SQLException {
		if(fenetreActu instanceof AffichageConnection) {
			String url = ((AffichageConnection)fenetreActu).getUrl();
			String mdp = ((AffichageConnection)fenetreActu).getMdp();
			String login = ((AffichageConnection)fenetreActu).getLogin();
			System.out.println("Tentative de connection sur "+url+" avec l'id :"+login+" et le mdp : "+mdp);
			
			//CONNECTION :
			// URL : jdbc:oracle:thin:@localhost:1521:XE
			cnt = DriverManager.getConnection(url, login, mdp);
			// Vérifie que la connection est bien effectuée :
			System.out.println("Connecté : " + cnt.isValid(100));
			
			fenetreActu = new AffichageAppli(width,heigth,this);
			fen.setContentPane(fenetreActu);
			fen.revalidate();
			
		}
	}
	
	public void modeAdmin() {
		if(fenetreActu instanceof AffichageAppli) {
			fenetreActu = new AffichageAdmin(width,heigth);
			fen.setContentPane(fenetreActu);
			fen.revalidate();
		}
	}
	
	public void rechercher() throws SQLException {
		
		PreparedStatement stt = cnt.prepareStatement("SELECT DISTINCT VEHICULE.NO_IMM, VEHICULE.MODELE from VEHICULE"
                            							 +" INNER JOIN CATEGORIE ON VEHICULE.CODE_CATEG = CATEGORIE.CODE_CATEG"
														 +" INNER JOIN CALENDRIER ON VEHICULE.NO_IMM = CALENDRIER.NO_IMM"
														 +" where LIBELLE like ? and VEHICULE.NO_IMM not in"
														 +" (select NO_IMM from CALENDRIER"
														 +" where ? <= DATEJOUR and ? >= DATEJOUR and PASLIBRE is not null)");
		if(catVehicule.equals("Toutes catégories")){
			stt.setString(1, "%");
		}
		else {
			stt.setString(1, catVehicule.toLowerCase());
		}
		stt.setString(1, catVehicule.toLowerCase());
		stt.setDate(2, dateD);
		stt.setDate(3, dateF);
		
		ResultSet res = stt.executeQuery();
		ArrayList<ArrayList<String>> tabRes = new ArrayList<ArrayList<String>>();
		int i = 0;
		
		tabRes.add(new ArrayList<String>());
		tabRes.get(i).add(res.getMetaData().getColumnName(1));
		tabRes.get(i).add(res.getMetaData().getColumnName(2));
		tabRes.get(i).add("Reserver maintenant");
		i++;
		
		while(res.next()) {
			tabRes.add(new ArrayList<String>());
			tabRes.get(i).add(res.getString(1));
			tabRes.get(i).add(res.getString(2));
			tabRes.get(i).add("btn");
			i++;
		}
		
		//System.out.println(tabRes);
		stt.close();
		res.close();
		resultat.removeAll();
		resultat.setLayout(new GridLayout(tabRes.size(),tabRes.get(0).size()));
		for(ArrayList<String> tab : tabRes) {
			for(String s : tab) {
				JComponent text = null;
				if(s.equals("btn")) {
					text = new JButton("reserve moi");
					((JButton)text).addActionListener(new ChangementFenetre(this,tab.get(0)));
				}
				else{
					text = new JTextArea(s);
				}
				text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), 
			            	   BorderFactory.createEmptyBorder(10, 10, 10, 10)));
				resultat.add(text);
			}
		}
		
		setChanged();
		notifyObservers();
		//System.out.println("OK rechercher !");
	}
	
	public void mettreAjour(String immat) throws SQLException {
		//on prend la date debut
		PreparedStatement stt = null;
		Date temp = (Date) dateD.clone();
		//tant que la date incremete est plus petit ou egale a la date max
		System.out.println(immat);
		while(temp.before(dateF) || temp.equals(dateF)) {
			//on regarde si la reservation existe pour cette date
			stt = cnt.prepareStatement("select NO_IMM from CALENDRIER "
														+ "where DATEJOUR = ? "
														+ "and NO_IMM = ?");
			stt.setDate(1, temp);
			stt.setString(2, immat);
			ResultSet res = stt.executeQuery();
			//si il n'y pas de lignes
			if(res.getRow() ==0) {
				stt = cnt.prepareStatement("insert into calendrier values (?,?,'x')");
			}
			else {
				stt = cnt.prepareStatement("update CALENDRIER set PASLIBRE = 'x'"
											+ " where NO_IMM=?"
											+ " and DATEJOUR=?");
			}
			stt.setString(1, immat);
			stt.setDate(2, temp);
			stt.executeUpdate();
			//incrementation d'un jour (j'ai pas trouvé plus facile)
			Calendar c = Calendar.getInstance();
			c.setTime(temp);
			c.add(Calendar.DAY_OF_MONTH, 1);
			temp = new Date(c.getTime().getTime());
			res.close();
		}
		double prix = 0.0;
		if(stt != null) {
			stt.close();
			
			PreparedStatement stt2 = cnt.prepareStatement("select TARIF_JOUR, TARIF_HEBDO from TARIF"
					+" inner join CATEGORIE on TARIF.CODE_TARIF = CATEGORIE.CODE_TARIF"
					+" inner join VEHICULE on CATEGORIE.CODE_CATEG = VEHICULE.CODE_CATEG" 
					+" where MODELE like (select MODELE from VEHICULE where NO_IMM like ?)");
			stt2.setString(1, immat);
			
			ResultSet res = stt2.executeQuery();			
			double prixJour = 0.0, prixHebdo = 0.0;
			if(res.next()) {
				prixJour = res.getDouble(1);
				prixHebdo = res.getDouble(2);
			}
			System.out.println(prixJour +" "+prixHebdo);
			int nbJour =(int)((dateF.getTime() - dateD.getTime() )/(1000*60*60*24)); 
			prix = (nbJour%7*prixJour + ((int)(nbJour/7))*prixHebdo);
			stt2.close();
			
		}
		rechercher();
		JOptionPane jop1 = new JOptionPane();
		jop1.showMessageDialog(null, "Votre réservation est confirmé, elle coutera : " + prix + "€", "Tarif", JOptionPane.INFORMATION_MESSAGE);
	}

	public JPanel getFenetreActu() {
		return fenetreActu;
	}

	public void setFenetreActu(JPanel fenetreActu) {
		this.fenetreActu = fenetreActu;
	}
	
	
	public void setCatVehicule(String s) {
		catVehicule = s;
	}
	
	public void setDateD(Date d) {
		dateD = d;
	}
	
	public void setDateF(Date d) {
		dateF = d;
	}
	
	public void setModeAgence(boolean b) {
		modeAgence = b;
	}
	
	public JPanel getResultat() {
		return resultat;
	}
}
