package graphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import Controlleurs.ChangementFenetre;
import Principale.Modele;

public class AffichageAdmin extends JPanel{
	Modele model;
	
	public AffichageAdmin(int w, int h,Modele m) {
		super();
		model = m;
		this.setPreferredSize(new Dimension(w,h));
		this.setLayout(new BorderLayout());
		//TOP
		JLabel lab= new JLabel("Nb de modèles différents loués");
		String[] tab = {"Tous","0","1","2","3"};
		JComboBox nbChoix = new JComboBox(tab);
		JButton go = new JButton("GO") ;
		JPanel top = new JPanel();
		
		JPanel bottom = new JPanel();
		JButton btn = new JButton("Retour");
		btn.addActionListener(new ChangementFenetre(model));
		bottom.add(btn);
		
		top.setLayout(new GridLayout(1,3));
		top.add(lab);
		top.add(nbChoix);
		top.add(go);
		
		//bas
		JPanel bas = new JPanel();
		
		//on ajoute tt
		this.add(top,BorderLayout.NORTH );
		this.add(bas, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	
}
