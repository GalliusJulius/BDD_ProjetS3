package Principale;

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
	
	public Modele(int w,int h) {
		fenetreActu=new AffichageConnection(w,h,this);
		width=w;
		heigth=h;
		fen=new Fenetre(fenetreActu);
	}
	
	/**
	 * Permet de se connecter a la base
	 */
	public void seConnecter() {
		if(fenetreActu instanceof AffichageConnection) {
			String url = ((AffichageConnection)fenetreActu).getName();
			String mdp = ((AffichageConnection)fenetreActu).getMdp();
			String login = ((AffichageConnection)fenetreActu).getLogin();
			System.out.println("Tentative de connection sur "+url+" avec l'id :"+login+" et le mdp : "+mdp);
			//Implanter la connection
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

	public JPanel getFenetreActu() {
		return fenetreActu;
	}

	public void setFenetreActu(JPanel fenetreActu) {
		this.fenetreActu = fenetreActu;
	}
	
	

}
