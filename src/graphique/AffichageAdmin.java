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
 * 
 * @author victo et rem
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
		top.getStyleClass().add("GridPaneTop");
		
		ComboBox<String> nbChoix = new ComboBox<String>(tab);
		nbChoix.getSelectionModel().selectFirst();
		nbChoix.valueProperty().addListener(control);
		Button bAff = new Button("Afficher");
		bAff.setOnAction(control);
		GridPane bBox = new GridPane();
		bBox.getStyleClass().add("GridPaneInter");
		bBox.add(lab, 0, 0);
		bBox.add(nbChoix, 1, 0);
		top.add(bBox, 0, 0);
		top.add(bAff, 1, 0);
		
		Button bRetour = new Button("Retour");
		bRetour.setOnAction(control);
		
		Button bAudit = new Button("Afficher la table Audit");
		bAudit.setOnAction(control);
		
		GridPane bottom = new GridPane();
		bottom.getStyleClass().add("GridPaneBottom");
		bottom.add(bRetour, 0, 0);
		bottom.add(bAudit, 1, 0);
		
		m.addObserver(this);
		
		//bas
		resultat = m.getResultat();
		resultat.setFillWidth(true);
		
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
