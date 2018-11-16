package modele;

import javafx.scene.control.Button;

/**
 * Classe représentant les différentes données que l'on peut obtenir lors de l'execution des requêtes du modèle.
 * Cette classe sert uniquement à ce que l'on puisse afficher les données résultantes (d'une requête) dans la table (TableView) du modèle.
 * Chaque constructeur correspond à un résultat d'une requête se trouvant dans le modèle.
 * 
 * @author victo et rem
 */
public class Contenu implements Table{
	/**
	 * Les différents résultats que l'on peut obtenir lors de l'execution d'une requête.
	 * Peut comprendre : l'immatriculation d'un véhicule, le modèle d'un véhicule, le code d'une agence, le nom d'un client, la ville d'un client, le code postal d'un client.
	 */
	private String no_im, modele, code_ag, nom, ville, cp;
	
	/**
	 * Bouton de réservation dans le cas de la requête concernant la liste des véhicules loués. 
	 */
	private Button reservation;
	
	/**
	 * Constructeur de Contenu du cas de la requête concernant la liste des véhicules loués (question 1).
	 * @param n immatriculation du véhicule
	 * @param m modèle du véhicule
	 * @param r bouton de réservation (associé à ce véhicule).
	 */
	public Contenu(String n, String m, Button r) {
		no_im = n;
		modele = m;
		reservation = r;
	}
	
	/**
	 * Constructeur de Contenu du cas de la requête concerant la liste des agences.
	 * @param a code de l'agence
	 */
	public Contenu(String a) {
		code_ag = a;
	}
	
	/**
	 * Constructeur de contenu du cas de la requête concernant la liste des clients (disponible dans le mode admin).
	 * @param n vom du client
	 * @param v ville du client
	 * @param c code postal du client
	 */
	public Contenu(String n, String v, String c) {
		nom = n;
		ville = v;
		cp = c;
	}

	/**
	 * Getter du numéro d'immatriculation.
	 * @return numéro d'immatriculation
	 */
	public String getNo_im() {
		return no_im;
	}

	/**
	 * Setter du numéro d'immatriculation.
	 * @param no_im numéro d'immatriculation.
	 */
	public void setNo_im(String no_im) {
		this.no_im = no_im;
	}

	/**
	 * Getter du modèle du véhicule.
	 * @return modèle du véhicule.
	 */
	public String getModele() {
		return modele;
	}

	/**
	 * Setter du modèle du véhicule.
	 * @param modele modèle du véhicule
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}

	/**
	 * Getter du bouton de réservation.
	 * @return bouton de réservation
	 */
	public Button getReservation() {
		return reservation;
	}

	/**
	 * Setter du bouton de réservation.
	 * @param reservation bouton de réservation.
	 */
	public void setReservation(Button reservation) {
		this.reservation = reservation;
	}

	/**
	 * Getter du code de l'agence
	 * @return code de l'agence
	 */
	public String getCode_ag() {
		return code_ag;
	}

	/**
	 * Setter du code de l'agence.
	 * @param code_ag code de l'agence.
	 */
	public void setCode_ag(String code_ag) {
		this.code_ag = code_ag;
	}

	/**
	 * Getter du nom du client.
	 * @return nom du client
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter du nom du client.
	 * @param nom nom du cient
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter de la ville du client.
	 * @return ville du client
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * Setter de la ville du client.
	 * @param ville ville du client
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * Getter du code postal du client.
	 * @return code postal du client
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * Setter du code postal du client.
	 * @param cp code postal du client
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
}
