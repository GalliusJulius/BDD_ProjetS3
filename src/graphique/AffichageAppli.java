package graphique;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controlleurs.ChangementFenetre;
import Principale.Modele;

/**
 * JPanel principale apres la connection a la base
 * @author moreliere
 *
 */
public class AffichageAppli extends JPanel implements Observer{
	
	private JPanel resultat;
	
	/**
	 * Consrtucteur avec la taille de la fenetre
	 * @param width largeur
	 * @param height hauteur
	 */
	public AffichageAppli(int width,int height,Modele m) {
		super();
		setPreferredSize(new Dimension(width,height));
		ChangementFenetre cont = new ChangementFenetre(m);
		this.setLayout(new BorderLayout());
		
		this.add(new ChoixSelects(cont),BorderLayout.NORTH);
		
		m.addObserver(this);
		resultat = m.getResultat();
		this.add(resultat,BorderLayout.CENTER);
		
		JPanel pan = new JPanel();
		JButton admin = new JButton("Mode Admin");
		admin.addActionListener(cont);
		pan.add(admin);
		this.add(pan,BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		resultat = ((Modele)arg0).getResultat();
		repaint();
		revalidate(); // IMPORTANT !
	}
}
