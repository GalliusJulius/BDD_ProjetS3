package graphique;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.event.AncestorListener;

import com.toedter.calendar.*;

import Controlleurs.ChangementFenetre;
// A télécharger sur : http://www.java2s.com/Code/Jar/j/Downloadjcalendar14jar.htm
// + Inclure dans éclipse (propriétés du projet -> Java Build Path -> Librairies -> Add external Jars)

/**
 * JPanel avec les choix pour acceder a la base de donne
 * @author moreliere
 *
 */
public class ChoixSelects extends JPanel{
	
	/**
	 * Constructeur avec les differents boutons de contols (a definir)
	 */
	public ChoixSelects(ChangementFenetre cont) {
		String[] tabCat = {"Toutes catégories","Citadine","Compacte","Familiale"};
		
		this.setLayout(new GridLayout(1,6));
		//Question 4
		JCheckBox c1 = new JCheckBox("Mode Agence");
		c1.addActionListener(cont);
		//question 1
		JComboBox c2 = new JComboBox(tabCat);
		c2.addActionListener(cont);
		
		// Date de début
		JDateChooser c4 = cont.getDateD();
		c4.addPropertyChangeListener(cont);
		// Date de fin
		JDateChooser c5 = cont.getDateF();
		c5.addPropertyChangeListener(cont);
		
		//lancer la recherhe
		JButton c6 = new JButton("Rechercher");
		c6.addActionListener(cont);
		
		this.add(c1);
		this.add(c2);
		//this.add(c3);
		this.add(c4);
		this.add(c5);
		this.add(c6);
	}

}
