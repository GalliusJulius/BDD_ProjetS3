package graphique;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	JCheckBox c1;
	JComboBox c2;
	JDateChooser c4,c5;
	
	/**
	 * Constructeur avec les differents boutons de contols (a definir)
	 */
	public ChoixSelects(ChangementFenetre cont) {
		String[] tabCat = {"Toutes cat�gories","Citadine","Compacte","Familiale"};
		
		this.setLayout(new GridLayout(1,6));
		//Question 4
		c1 = new JCheckBox("Mode Agence");
		c1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(c1.isSelected()) {
					c2.setEnabled(false);
					c4.setEnabled(false);
					c5.setEnabled(false);
					cont.setModeAgence(true);
				}
				else {
					c2.setEnabled(true);
					c4.setEnabled(true);
					c5.setEnabled(true);
					cont.setModeAgence(false);
				}
				
			}
			
		});
		//question 1
		c2 = new JComboBox(tabCat);
		c2.addActionListener(cont);
		
		// Date de début
		c4 = cont.getDateD();
		c4.addPropertyChangeListener(cont);
		// Date de fin
		c5 = cont.getDateF();
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
