package graphique;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modele.Modele;

/**
 * Fenêtre principale (ou on peut parler d'application) sur laquelle est chargée
 * les différentes scènes (ou affichages) au cours de l'execution de
 * l'application.
 * 
 * @author victo et rem
 */
public class Fenetre extends Application {

	/**
	 * Largeur de la fenêtre.
	 */
	public static final int WIDTH = 1000;

	/**
	 * Hauteur de la fenêtre.
	 */
	public static final int HEIGHT = 500;

	/**
	 * Elément de notre fenêtre sur lequel on y place des scènes. La scène placée
	 * dans cet élément (via la fonction setScene()) représente l'affichage en
	 * cours.
	 */
	private static Stage stage;

	/**
	 * Scène représentant l'affichage en cours (ici la page d'acceuil).
	 */
	private static Scene affichage;

	/**
	 * Modèle de notre applcation (sert à récupérer l'affichage en cours).
	 */
	private static Modele mod;

	/**
	 * Constructeur de la fenetre de l'application.
	 */
	public Fenetre() {
		mod = new Modele(WIDTH, HEIGHT);
		Pane pan = mod.getFenetreActu();
		affichage = new Scene(pan, WIDTH, HEIGHT);
	}

	/**
	 * Permet de charger l'affichage en cours, à savoir ici, la page d'acceuil (de connexion à la base de données).
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage st) throws Exception {
		stage = st;
		mod.setStage(st);
		stage.setMaximized(true);
		stage.setTitle("Projet BDD");
		affichage.getStylesheets().add(getClass().getResource("../css/acceuil.css").toExternalForm());
		stage.setScene(affichage);
		stage.show();
	}
}
