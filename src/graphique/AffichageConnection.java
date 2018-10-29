package graphique;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Controlleurs.ChangementFenetre;
import Principale.Modele;

/**
 * Classe du JPanel pour se connecter a la base de donnee
 * @author moreliere
 *
 */
public class AffichageConnection extends JPanel {
	/**
	 *Champs a remplir et a verifier lors de la connction 
	 */
	private JTextField url,mdp,login;
	
	public AffichageConnection(int width,int heigh,Modele m) {
		this.setPreferredSize(new Dimension(width,heigh));
		this.setLayout(new GridLayout(5,1));
		JLabel phrase = new JLabel("Bienvenue veuillez vous identifier sur votre base:");
		url = new JTextField("url base de donnée");
		url.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				if(url.getText().equals("")) {
					url.setText("url base de donnée");
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(url.getText().equals("url base de donnée")) {
					url.setText("");
				}
			}
		});
		login = new JTextField("login utilisateur");
		login.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				if(login.getText().equals("")) {
					login.setText("login utilisateur");
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(login.getText().equals("login utilisateur")) {
					login.setText("");
				}
			}
		});
		mdp = new JTextField("Mot de passe");
		mdp.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				if(mdp.getText().equals("")) {
					mdp.setText("Mot de passe");
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(mdp.getText().equals("Mot de passe")) {
					mdp.setText("");
				}
			}
		});
		JButton connec = new JButton("Se connecter");
		/**Border bordureText = BorderFactory.createEmptyBorder(0, 20, 0 , 20);
		url.setBorder(bordureText);
		login.setBorder(bordureText);
		mdp.setBorder(bordureText);*/
		Border bordure = BorderFactory.createEmptyBorder(heigh/8, width/6, heigh/8 , width/6);
		this.setBorder(bordure);
		this.add(phrase);
		this.add(url);
		this.add(login);
		this.add(mdp);
		this.add(connec);
		connec.addActionListener(new ChangementFenetre(m));
	}

	public String getUrl() {
		return url.getText();
	}

	public void setUrl(String url) {
		this.url.setText(url);
	}

	public String getMdp() {
		return mdp.getText();
	}

	public void setMdp(String mdp) {
		this.mdp.setText(mdp);;
	}

	public String getLogin() {
		return login.getText();
	}

	public void setLogin(String login) {
		this.login.setText(login);
	}
	
	
}
