package Principale;

import java.sql.*;
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
public class Modele {
	
	private JPanel fenetreActu;
	private JFrame fen;
	private int width,heigth;
	private boolean modeAgence;
	private String catVehicule, dateD, dateF;
	
	public Modele(int w,int h) {
		fenetreActu=new AffichageConnection(w,h,this);
		width=w;
		heigth=h;
		fen=new Fenetre(fenetreActu);
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
			Connection cnt = DriverManager.getConnection(url, login, mdp);
			// Vérifie que la connection est bien effectuée :
			System.out.println(cnt.isValid(100));
			
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
	
	public void rechercher() {
		System.out.println("OK rechercher !");
		// TODO 
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
	
	public void setDateD(String s) {
		dateD = s;
	}
	
	public void setDateF(String s) {
		dateF = s;
	}
	
	public void setModeAgence(boolean b) {
		modeAgence = b;
	}
}
