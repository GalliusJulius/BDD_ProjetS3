package graphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Controlleurs.ChangementFenetre;
import Principale.Modele;

public class AffichageAdmin extends JPanel implements Observer{
	private Modele model;
	private JPanel resultat;
	
	public AffichageAdmin(int w, int h,Modele m) {
		super();
		model = m;
		this.setPreferredSize(new Dimension(w,h));
		this.setLayout(new BorderLayout());
		//TOP
		ChangementFenetre control = new ChangementFenetre(model);
		JLabel lab= new JLabel("Nb de modèles différents loués");
		String[] tab = {"Tous","0","1","2","3"};
		JComboBox nbChoix = new JComboBox(tab);
		nbChoix.addItemListener(control);
		JButton go = new JButton("GO") ;
		go.addActionListener(control);
		JPanel top = new JPanel();
		
		JPanel bottom = new JPanel();
		JButton btn = new JButton("Retour");
		btn.addActionListener(control);
		bottom.add(btn);
		
		top.setLayout(new GridLayout(1,3));
		top.add(lab);
		top.add(nbChoix);
		top.add(go);
		m.addObserver(this);
		//bas
		JPanel bas = m.getResultat();
		
		//on ajoute tt
		this.add(top,BorderLayout.NORTH );
		this.add(bas, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		resultat = ((Modele)arg0).getResultat();
		repaint();
		revalidate();
		
	}
	
	
}
