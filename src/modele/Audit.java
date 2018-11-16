package modele;

import java.sql.Date;

/**
 * Classe représentant les différentes données qui se trouvent dans la table Audit.
 * Cette classe sert uniquement à ce que l'on puisse afficher les données résultantes (d'une requête) dans la table (TableView) du modèle.
 * 
 * @author victo et rem
 */
public class Audit implements Table {
	
	/**
	 * Numéro du dossier.
	 */
	private int num_doss; 
	
	/**
	 * Date de la mise à jour de la table Audit_.
	 */
	private Date date;
	
	/**
	 * Les autres informations se trouvant dans la table Audit_.
	 * A savoir : le nom et prenom du client (dans l'attribut nom), la marque et le modele et du véhicule loué. 
	 */
	private String nom, marque, modele;
	
	/**
	 * Constructeur de Audit
	 * 
	 * @param num numéro du dossier.
	 * @param d date de la mise à jour dans la table Audit_.
	 * @param n nom (contenant le nom et prénom du client).
	 * @param ma marque du véhicule loué par le client.
	 * @param mo modèle du véhicule loué par le client.
	 */
	public Audit(int num, Date d, String n, String ma, String mo) {
		num_doss = num;
		date = d;
		nom = n;
		marque = ma;
		modele = mo;
	}

	/**
	 * Getter de num_doss.
	 * @return le nuémro du dossier.
	 */
	public int getNum_doss() {
		return num_doss;
	}

	/**
	 * Setter du num_doss.
	 * @param num_doss numéro du dossier.
	 */
	public void setNum_doss(int num_doss) {
		this.num_doss = num_doss;
	}

	/**
	 * Getter de date
	 * @return la date de la mise à jour de la ligne dans la table Audit_.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Setter de la date de mise à jour de de la ligne de la table Audit_.
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Getter de nom
	 * @return nom et prénom du client.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter de nom.
	 * @param nom nom et prenom du client.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter de marque.
	 * @return la marque du véhicule loué.
	 */
	public String getMarque() {
		return marque;
	}

	/**
	 * Setter de marque.
	 * @param marque marque du véhicule loué.
	 */
	public void setMarque(String marque) {
		this.marque = marque;
	}

	/**
	 * Getter de modele.
	 * @return le modèle du véhicule loué.
	 */
	public String getModele() {
		return modele;
	}

	/**
	 * Setter de modele.
	 * @param modele modèle du véhicule loué.
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}
}
