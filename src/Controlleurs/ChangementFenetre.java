package Controlleurs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Principale.Modele;

/**
 * Controlleurpour changer de fenetre
 * @author moreliere
 *
 */
public class ChangementFenetre implements ActionListener, PropertyChangeListener{

	/**
	 * Modele du controleur
	 */
	private Modele model;
	
	/*
	 * JDateChooser placé en attribut, car impossible de les différenciers autrement (sauf si tu as une autre idée) 
	 */
	private JDateChooser dateD, dateF; 
	
	/**
	 * Constructeur
	 * @param m Modele du controleur
	 */
	public ChangementFenetre(Modele m) {
		model=m;
		dateD = new JDateChooser();
		dateF = new JDateChooser();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof JButton) {
			//on veut se connecter
			if(((JButton)arg0.getSource()).getText().equals("Se connecter")) {
				try {
					model.seConnecter();
				} catch (SQLException e) { e.printStackTrace(); }
			}
			// Affiche le resultat
			else if(((JButton)arg0.getSource()).getText().equals("Rechercher")) {
				//System.out.println("Recherche...");
				try {
					model.rechercher();
				} catch (SQLException e) { e.printStackTrace(); }
			}
			//on passe en mode admin
			else {
				model.modeAdmin();
			}
		} else if(arg0.getSource() instanceof JComboBox) {
			model.setCatVehicule((String) ((JComboBox)arg0.getSource()).getSelectedItem());
			//System.out.println("Cat. Veh. : " + (String) ((JComboBox)arg0.getSource()).getSelectedItem());
		} else if(arg0.getSource() instanceof JCheckBox) {
			if(((JCheckBox)arg0.getSource()).getText().equals("Mode Agence")) {
				model.setModeAgence(((JCheckBox)arg0.getSource()).isSelected());
				//System.out.println("Mode Agence : " + ((JCheckBox)arg0.getSource()).isSelected());
			}
		}
		
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if(arg0.getSource() instanceof JDateChooser) {
			int day = ((JDateChooser)arg0.getSource()).getJCalendar().getDayChooser().getDay();
			int month = ((JDateChooser)arg0.getSource()).getJCalendar().getMonthChooser().getMonth() + 1;
			int year = ((JDateChooser)arg0.getSource()).getJCalendar().getYearChooser().getYear();
			Date date = Date.valueOf(year + "-" + month + "-" + day);
			//System.out.println("OK JDateChooser !");
			//System.out.println(day + "/" + month + "/" + year);
			
			if(arg0.getSource().equals(dateD)){
				//System.out.println("OK Date Debut !");
				model.setDateD(date);
			} else {
				//System.out.println("OK Date Fin !");
				model.setDateF(date);
			}
		}
	}
	
	
	// A voir si bien ;)
	public JDateChooser getDateD() {
		return dateD;
	}
	
	public JDateChooser getDateF() {
		return dateF;
	}
}
