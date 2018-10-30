package Controlleurs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

import Principale.Modele;

/**
 * Controlleurpour changer de fenetre
 * @author moreliere
 *
 */
public class ChangementFenetre implements ActionListener{

	/**
	 * Modele du controleur
	 */
	private Modele model;
	
	/**
	 * Constructeur
	 * @param m Modele du controleur
	 */
	public ChangementFenetre(Modele m) {
		model=m;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//on veut se connecter
		if(((JButton)arg0.getSource()).getText().equals("Se connecter")) {
			try {
				model.seConnecter();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//on passe en mode admin
		else {
			model.modeAdmin();
		}
	}

}
