package Principale;

import java.sql.*;
import java.util.Observable;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	private JPanel fenetreActu;
	private JFrame fen;
	private int width,heigth;
	private boolean modeAgence;
	private Date dateD, dateF;
	private String catVehicule, resultat;
	private Connection cnt;
	
	public Modele(int w,int h) {
		fenetreActu=new AffichageConnection(w,h,this);
		width=w;
		heigth=h;
		fen=new Fenetre(fenetreActu);
		resultat = "";
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
		
		stt.setString(1, catVehicule.toLowerCase());
		stt.setDate(2, dateD);
		stt.setDate(3, dateF);
		
		ResultSet res = stt.executeQuery();
		StringBuffer str = new StringBuffer("");
		
		// A voir au niveau de l'affichage :
		while(res.next()) {
			str.append(res.getString(1));
			str.append(" | ");
			str.append(res.getString(2));
			str.append("\n");
		}
		
		stt.close();
		res.close();
		
		resultat = str.toString();
		
		if(resultat.equals("")) {
			resultat = "Aucun Resultat !";
		}
		
		System.out.println(resultat);
		
		setChanged();
		notifyObservers();
		
		System.out.println("OK rechercher !");
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
	
	public String getResultat() {
		return resultat;
	}
}
