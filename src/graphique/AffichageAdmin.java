package graphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class AffichageAdmin extends JPanel{
	
	public AffichageAdmin(int w, int h) {
		super();
		this.setPreferredSize(new Dimension(w,h));
		this.setLayout(new BorderLayout());
		//TOP
		JLabel lab= new JLabel("Nb de modèles différents loués");
		String[] tab = {"Tous","0","1","2","3"};
		JComboBox nbChoix = new JComboBox(tab);
		JButton go = new JButton("GO") ;
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1,3));
		top.add(lab);
		top.add(nbChoix);
		top.add(go);
		
		//bas
		JPanel bas = new JPanel();
		
		//on ajoute tt
		this.add(top,BorderLayout.NORTH );
		this.add(bas, BorderLayout.CENTER);
	}
	
	
}
