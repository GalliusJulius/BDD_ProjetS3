package questionConsole;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe permettant de répondre à la question 3
 * @author victo & rem
 *
 */
public class Question3 {
	
	/**
	 * Methode permettant de calculer le prix d'un véhicule pour une reservation faites
	 * @param cnt connection courrante 
	 * @param immat immatriculation du vehicule
	 * @param dateD date de debut reservation
	 * @param dateF date de fin reservation
	 * @throws SQLException erreur d'ecriture
	 */
	public static void repondreQuestion(Connection cnt, String immat,Date dateD,Date dateF) throws SQLException {
		double prix = 0.0;
		int nbJour = 0;
		//requete
		PreparedStatement stt = cnt.prepareStatement("select TARIF_JOUR, TARIF_HEBDO from TARIF"
				+" inner join CATEGORIE on TARIF.CODE_TARIF = CATEGORIE.CODE_TARIF"
				+" inner join VEHICULE on CATEGORIE.CODE_CATEG = VEHICULE.CODE_CATEG" 
				+" where MODELE like (select MODELE from VEHICULE where NO_IMM like ?)");
		stt.setString(1, immat);
		ResultSet res = stt.executeQuery();	
		//calcul du prix
		double prixJour = 0.0, prixHebdo = 0.0;
		if(res.next()) {
			prixJour = res.getDouble(1);
			prixHebdo = res.getDouble(2);
			nbJour =(int)((dateF.getTime() - dateD.getTime() )/(1000*60*60*24)); 
			prix = (nbJour%7*prixJour + ((int)(nbJour/7))*prixHebdo);
			stt.close();
			System.out.println("Votre réservation de "+nbJour+" jour(s) à compter du "+dateD+ "au "+dateF +" coutera "+prix);
		}
	}

}