package graphique;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import com.toedter.calendar.*;
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
	public ChoixSelects() {
		String[] tabCat = {"Toutes catégories","Citadine","Compacte","Familiale"};
		// 
		JPanel jp1 = new JPanel();
		
		jp1.setLayout(new GridLayout(1,4));
		//Question 4
		JCheckBox c1 = new JCheckBox("Mode Agence");
		//question 1
		JComboBox c2 = new JComboBox(tabCat);
		
		JCheckBox c3 = new JCheckBox("3");
		//lancer la recherhe
		JButton c6 = new JButton("GO");
		
		jp1.setPreferredSize(new Dimension(1000, 25));
		jp1.add(c1);
		jp1.add(c2);
		jp1.add(c3);
		jp1.add(c6);
		
		//Choix des dates
		JPanel jp2 = new JPanel();
		jp2.setLayout(new GridLayout(1,3));
		JCalendar c4 = new JCalendar();
		JCalendar c5 = new JCalendar();
		jp2.add(Box.createGlue());
		jp2.add(c4);
		jp2.add(c5);
		
		// TODO revoir la dispostion !?
		this.setLayout(new BorderLayout());
		this.add(jp1, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.WEST);
	}

}
