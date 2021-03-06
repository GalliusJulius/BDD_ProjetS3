package graphique;
import java.util.Observable;
import java.util.Observer;

import controleur.ChangementFenetre;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.Modele;

/**
 * Vue de l'application [après la page d'acceuil] (Gridpane).
 * 
 * @author victo et rem
 */
public class AffichageAppli extends GridPane implements Observer{
	
	/**
	 * Table de résultat du modèle (TableView) que l'on intègre à l'interface.
	 */
	private VBox resultat;
	
	/**
	 * Consrtucteur de la vue de l'application.
	 * @param width largeur de la vue.
	 * @param height hauteur de la vue.
	 * @param m modèle de l'application.
	 */
	public AffichageAppli(int width, int height, Modele m) {
		super();
		this.setWidth(width);
		this.setHeight(height);
		this.getStyleClass().add("GridPane");
		
		ChangementFenetre cont = new ChangementFenetre(m);
		
		this.add(new ChoixSelects(cont), 0, 0);
		
		m.addObserver(this);
		
		resultat = m.getResultat();
		resultat.setFillWidth(true);
		this.add(resultat, 0, 1);
		
		FlowPane pan = new FlowPane();
		pan.getStyleClass().add("FlowPane");
		Button admin = new Button("Mode Admin");
		admin.setOnAction(cont);
		pan.getChildren().add(admin);
		pan.setAlignment(Pos.CENTER);
		this.add(pan, 0, 2);
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
