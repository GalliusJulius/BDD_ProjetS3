package graphique;

import controleur.ChangementFenetre;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import modele.Modele;

/**
 * Vue de la page d'acceuil [connection à la base de données] (Gridpane).
 */
public class AffichageConnection extends GridPane {
	
	/**
	 * Champs à remplir et à verifier lors de la connection à la base de données.
	 * Comprenant : l'url, le login et le mot de passe de la base de données (BDD). 
	 */
	private TextField url, login;
	private PasswordField mdp;
	
	/**
	 * Constructeur de la vue.
	 * @param width largeur de la vue.
	 * @param height hauteur de la vue.
	 * @param m modèle de l'application.
	 */
	public AffichageConnection(int width, int height, Modele m) {
		this.setWidth(width);
		this.setHeight(height);
		this.getStyleClass().add("GridPane");
		
		Label phrase1 = new Label("Bienvenue !");
		Label phrase2 = new Label("Veuillez vous identifier sur votre base :");
		phrase1.getStyleClass().add("Label");
		phrase2.getStyleClass().add("Label");
		VBox boxPhrase = new VBox(phrase1, phrase2);
		boxPhrase.getStyleClass().add("PLabel");
		
		url = new TextField();
		url.getStyleClass().add("TextField");
		url.setPromptText("URL");
		url.setFocusTraversable(false);
		
		
		login = new TextField();
		login.getStyleClass().add("TextField");
		login.setPromptText("Identifiant");
		login.setFocusTraversable(false);
		
		
		mdp = new PasswordField();
		mdp.setPromptText("Mot de passe");
		mdp.setFocusTraversable(false);
		
		
		
		Button connec = new Button("Se connecter");
		connec.getStyleClass().add("Button");
		connec.setOnAction(new ChangementFenetre(m));
		VBox boxConn = new VBox(connec);
		boxConn.getStyleClass().add("PButton");
		/**Border bordureText = BorderFactory.createEmptyBorder(0, 20, 0 , 20);
		url.setBorder(bordureText);
		login.setBorder(bordureText);
		mdp.setBorder(bordureText);*/
		Border bordure = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
		this.setBorder(bordure);
		
		this.add(boxPhrase, 0, 0);
		this.add(url, 0, 1);
		this.add(login, 0, 2);
		this.add(mdp, 0, 3);
		this.add(boxConn, 0, 4);
	}

	/**
	 * Getter de l'url de la BDD.
	 * @return url
	 */
	public String getUrl() {
		return url.getText();
	}

	/**
	 * Setter de l'url de la BDD.
	 * @param url url de la BDD.
	 */
	public void setUrl(String url) {
		this.url.setText(url);
	}

	/**
	 * Getter du mot de passe de la BDD.
	 * @return mot de passe.
	 */
	public String getMdp() {
		return mdp.getText();
	}

	/**
	 * Setter du mot de passe de la BDD.
	 * @param mdp mot de passe de la BDD.
	 */
	public void setMdp(String mdp) {
		this.mdp.setText(mdp);;
	}

	/**
	 * Getter du login de la BDD.
	 * @return login
	 */
	public String getLogin() {
		return login.getText();
	}

	/**
	 * Setter du login de la BDD.
	 * @param login login de la BDD.
	 */
	public void setLogin(String login) {
		this.login.setText(login);
	}
}
