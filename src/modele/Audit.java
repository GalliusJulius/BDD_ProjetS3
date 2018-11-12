package modele;

import java.sql.Date;

public class Audit implements Table {
	
	private int num_doss; 
	private Date date;
	private String nom, marque, modele;
	
	public Audit(int num, Date d, String n, String ma, String mo) {
		num_doss = num;
		date = d;
		nom = n;
		marque = ma;
		modele = mo;
	}

	public int getNum_doss() {
		return num_doss;
	}

	public void setNum_doss(int num_doss) {
		this.num_doss = num_doss;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}
}
