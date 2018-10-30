package graphique;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AffichageResultat extends JPanel{
	
	private JTextArea resultat;
	
	public AffichageResultat() {
		resultat = new JTextArea("OK");
		this.add(resultat);
	}
}
