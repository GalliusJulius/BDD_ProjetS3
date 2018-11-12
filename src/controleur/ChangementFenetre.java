package controleur;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import modele.Modele;

/**
 * Controleur de toutes les vues, gère tous les événements de tous les composants présents dans l'application.
 * L'interface ChangeListener nous sert de controleur pour le DatePicker de Fin (de AffichageAppli) et le ComboBox (du mode Admin).
 * Et l'interface EventHandler sert pour tout les autres événements.
 * Il n'a que très peu de différences entre les deux interfaces, on utilise ces 2 interafces pour distinguer les 2 Combobox et les 2 DatePicker de notre application. 
 */
public class ChangementFenetre implements EventHandler, ChangeListener {

	/**
	 * Modele de l'application.
	 */
	private Modele model;
	
	/**
	 * Représente le nombre de modèles différents loués (que l'on choisi dans le ComboBox du mode admin) qui ont été loué.
	 * -1 représente tout les modèles.
	 */
	private int nbChoixClient = -1;
	
	/**
	 * Représente l'immatriculation de la voiture que l'on choisi dans le cas d'une réservation (sert pour la mise à jour de la base de données).
	 */
	private String immat="";
	
	/**
	 * Constructeur de base du controleur (utilisé dans les vues).
	 * @param m Modele de l'application
	 */
	public ChangementFenetre(Modele m) {
		model = m;
	}
	
	/**
	 * Constructeur avancé du controleur, il est utilisé pour les boutons de réservation de la table (utilisé dans le modèle).
	 * @param m Modele de l'application.
	 * @param im immatriculation de la voiture choisi pour une réservation.
	 */
	public ChangementFenetre(Modele m,String im) {
		model = m;
		immat = im;
	}
	
	/**
	 * Setter intermédiaire du mode agence pour le modèle (utilisé dans la vue ChoixSelects).
	 * @param b activation (ou non) du mode agence.
	 */
	public void setModeAgence(boolean b) {
		model.setModeAgence(b);
	}
	
	/**
	 * Sert à gérer tout les événements de l'application (sauf les 2 exceptions énumérés précédemment).
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(Event e) {
		if(e.getSource() instanceof Button) {
			//on veut se connecter
			if(((Button)e.getSource()).getText().equals("Se connecter")) {
				try {
					model.seConnecter();
					model.afficherAppli();
				} catch (SQLException expt) { expt.printStackTrace(); }
			}
			// Affiche le resultat
			else if(((Button)e.getSource()).getText().equals("Rechercher")) {
				try {
					if(! model.getModeAgence()) {
						model.rechercher();
					}
					else {
						model.rechercherAgence();
					}
				} catch (SQLException expt) { expt.printStackTrace(); }
			}
			else if(((Button)e.getSource()).getText().equals("Réserver")) {
					try {
						model.mettreAjour(immat);
					} catch (SQLException expt) { expt.printStackTrace(); }
			}
			
			else if(((Button)e.getSource()).getText().equals("Retour")) {
				model.afficherAppli();
			}
			else if(((Button)e.getSource()).getText().equals("Afficher")) {
				try {
					model.afficherClient(nbChoixClient);
				} catch (SQLException expt) { expt.printStackTrace(); }
			}
			else if(((Button)e.getSource()).getText().equals("Afficher la table Audit")){
				try {
					model.afficherTableAudit();
				} catch (SQLException e1) { e1.printStackTrace(); }
			}
			else if(((Button)e.getSource()).getText().equals("Mode Admin")){
				model.modeAdmin();
			}
			
		}
		else if(e.getSource() instanceof ComboBox) {
			model.setCatVehicule((String) ((ComboBox)e.getSource()).getValue());
			
		}
		else if(e.getSource() instanceof CheckBox) {
			if(((CheckBox)e.getSource()).getText().equals("Mode Agence")) {
				model.setModeAgence(((CheckBox)e.getSource()).isSelected());
				
			}
		}
		else if(e.getSource() instanceof DatePicker) {
			LocalDate ldate = ((DatePicker)e.getSource()).getValue();
			Date date = calculerDate(ldate);
			
			model.setDateD(date);
			System.out.println(date);
		}
	}

	/**
	 * Sert à gérer les 2 événements 'exceptions' afin de les différenciers des autres (On différencie les 2 ComboBox et 2 DatePicker).
	 * Donc ici, sera traité uniquement le comboBox du mode admin et le DatePicker représentant la fin de la période choisi (dans l'AffichageAppli).
	 * 
	 * @see javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void changed(ObservableValue oV, Object o1, Object o2) {
		if(o2 instanceof String) {
			String res = (String) o2;
			if(res.equals("Tous")) {
				res="-1";
			}
			nbChoixClient = Integer.parseInt(res);
			System.out.println(res);
		} else if(o2 instanceof LocalDate) {
			LocalDate ldate = (LocalDate) o2;
			Date date = calculerDate(ldate);
			
			model.setDateF(date);
			System.out.println(date);
		}
		
	}
	
	/**
	 * Méthode privée seravnt à calculer une date en fonction d'une LocalDate (optimisation de l'écriture).
	 * @param ldate LocaleDate donné par le datePicker.
	 * @return Objet date que l'on utilisera dans les requêtes (du modèle).
	 */
	private Date calculerDate(LocalDate ldate) {
		int day = ldate.getDayOfMonth();
		int month = ldate.getMonthValue();
		int year = ldate.getYear();
		Date date = Date.valueOf(year + "-" + month + "-" + day);
		
		return date;
	}
}
