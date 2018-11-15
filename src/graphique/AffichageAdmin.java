package graphique;

import java.util.Observable;
import java.util.Observer;
import controleur.ChangementFenetre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.Modele;

/**
 * Vue du mode administrateur (Gridpane).
 */
public class AffichageAdmin extends GridPane implements Observer{
	/**
	 * Modèle de l'application. 
	 */
	private Modele model;
	
	/**
	 * Table de résultat du modèle (TableView) que l'on intègre à l'interface.
	 */
	private VBox resultat;
	
	/**
	 * Constructeur de la vue administrateur.
	 * @param w largeur de la vue.
	 * @param h hauteur de la vue.
	 * @param m modèle de l'application.
	 */
	public AffichageAdmin(int w, int h, Modele m) {
		super();
		model = m;
		this.setWidth(w);
		this.setHeight(h);
		this.getStyleClass().add("GridPane");
		
		//TOP
		ChangementFenetre control = new ChangementFenetre(model);
		
		ObservableList<String> tab = FXCollections.observableArrayList("Tous","0","1","2","3");
		Label lab = new Label("Nbr. de modèles différents loués");
		lab.getStyleClass().add("Label");
		
		GridPane top = new GridPane();
		top.getStyleClass().add("GridPane");
		
		ComboBox<String> nbChoix = new ComboBox<String>(tab);
		nbChoix.getStyleClass().add("ComboBox");
		nbChoix.getSelectionModel().selectFirst();
		nbChoix.valueProperty().addListener(control); // Changed
		Button bAff = new Button("Afficher") ;
		bAff.getStyleClass().add("Button");
		bAff.setOnAction(control);
		top.add(lab, 0, 0);
		top.add(nbChoix, 1, 0);
		top.add(bAff, 2, 0);
		
		Button bRetour = new Button("Retour");
		bRetour.setOnAction(control);
		
		Button bAudit = new Button("Afficher la table Audit");
		bAudit.setOnAction(control);
		
		FlowPane bottom = new FlowPane();
		bottom.getChildren().add(bRetour);
		bottom.getChildren().add(bAudit);
		bottom.setAlignment(Pos.CENTER);
		
		m.addObserver(this);
		
		//bas
		resultat = m.getResultat();
		
		//on ajoute tt
		this.add(top, 0, 0);
		this.add(resultat, 0, 1);
		this.add(bottom, 0, 2);
	}

	/**
	 * Sert à mettre à jour notre table de résultat.
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		resultat = ((Modele)arg0).getResultat();
	}
}
