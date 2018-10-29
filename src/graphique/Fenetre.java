package graphique;
import java.awt.Dimension;

import javax.swing.*;

/**
 * Fenetre principale de l'appli
 * @author moreliere
 *
 */
public class Fenetre extends JFrame{
	
	/**
	 * Construit une fenetre avec un JPnanel a l'interieur
	 * @param interieur JPanel principale
	 */
	public Fenetre(JPanel interieur) {
		super("Projet BDD");
		add(interieur);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
