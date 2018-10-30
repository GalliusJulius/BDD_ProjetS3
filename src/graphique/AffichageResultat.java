package graphique;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Principale.Modele;

public class AffichageResultat extends JPanel implements Observer{
	
	private JTextArea resultat;
	private Modele model;
	
	public AffichageResultat(Modele m) {
		model = m;
		resultat = new JTextArea(model.getResultat());
		this.add(resultat);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		model = (Modele) arg0;
		resultat.setText(model.getResultat());
		repaint();
	}
}
