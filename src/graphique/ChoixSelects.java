package graphique;

import controleur.ChangementFenetre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;

/**
 * Vue des choix séléctionnés dans la vue de l'application (GridPane).
 */
public class ChoixSelects extends GridPane {
	
	/**
	 * CheckBox du mode agence. 
	 */
	CheckBox c1;
	
	/**
	 * ComboBox du choix des catégories des voitures.
	 */
	ComboBox<String> c2;
	
	/**
	 * DatePicker représentant une période (date de début et date de fin). 
	 */
	DatePicker c4,c5;
	
	/**
	 * Constructeur de la vue.
	 * @param cont controleur de notre application qui sera affecté à la ComboBox et au DatePicker.
	 */
	public ChoixSelects(ChangementFenetre cont) {
		super();
		ObservableList<String> tabCat = FXCollections.observableArrayList("Toutes catégories","Citadine","Compacte","Familiale");
		
		//Question 4
		c1 = new CheckBox("Mode Agence");
		c1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(c1.isSelected()) {
					c2.setEditable(false);
					c4.setEditable(false);
					c5.setEditable(false);
					cont.setModeAgence(true);
				}
				else {
					c2.setEditable(true);
					c4.setEditable(true);
					c5.setEditable(true);
					cont.setModeAgence(false);
				}
			}
		});
		
		
		//question 1
		c2 = new ComboBox<String>(tabCat);
		c2.getSelectionModel().selectFirst();
		c2.setOnAction(cont);
		
		// Date de début
		c4 = new DatePicker();
		c4.setOnAction(cont);
		// Date de fin
		c5 = new DatePicker();
		c5.valueProperty().addListener(cont);
		
		//lancer la recherhe
		Button c6 = new Button("Rechercher");
		c6.setOnAction(cont);
		
		this.add(c1, 0, 0);
		this.add(c2, 1, 0);
		//GridPane.setHalignment(loginButton, HPos.RIGHT); 
		this.add(c4, 2, 0);
		this.add(c5, 3, 0);
		this.add(c6, 4, 0);
	}

}
