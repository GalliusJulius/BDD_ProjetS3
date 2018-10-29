package graphique;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * JPanel avec les choix pour acceder a la base de donne
 * @author moreliere
 *
 */
public class ChoixSelects extends JPanel{
	
	/**
	 * Constructeur avec les differents boutons de contols (a definir)
	 */
	public ChoixSelects() {
		String[] tabCat = {"Toutes catégories","Citadine","Compacte","Familiale"};
		this.setLayout(new GridLayout(1,4));
		//Question 4
		JCheckBox c1 = new JCheckBox("Mode Agence");
		//question 1
		JComboBox c2 = new JComboBox(tabCat);
		//Choisir des dates
		JCheckBox c3 = new JCheckBox("3");
		//lancer la recherhe
		JButton c4 = new JButton("GO");
		this.add(c1);
		this.add(c2);
		this.add(c3);
		this.add(c4);
	}

}
