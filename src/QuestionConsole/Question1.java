package QuestionConsole;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe permettant de repondre a la premiere question
 * @author victo & rem
 *
 */
public class Question1{

	/**
	 * Methode qui donnera la liste des vehicules dispos durant une periode pour une categorie donne
	 * @param cat
	 * @throws SQLException 
	 */
	public static void repondreQuestion(Connection cnt, String cat,Date dateD, Date dateF) throws SQLException {
		PreparedStatement stt = cnt.prepareStatement("SELECT DISTINCT VEHICULE.NO_IMM, VEHICULE.MODELE from VEHICULE"
				 +" INNER JOIN CATEGORIE ON VEHICULE.CODE_CATEG = CATEGORIE.CODE_CATEG"
				 +" INNER JOIN CALENDRIER ON VEHICULE.NO_IMM = CALENDRIER.NO_IMM"
				 +" where LIBELLE like ? and VEHICULE.NO_IMM not in"
				 +" (select NO_IMM from CALENDRIER"
				 +" where ? <= DATEJOUR and ? >= DATEJOUR and PASLIBRE is not null)");
		stt.setString(1,cat.toLowerCase());
		stt.setDate(2, dateD);
		stt.setDate(3, dateF);
		ResultSet res = stt.executeQuery();
		System.out.println("Voici les résultats de votre recherche sur les véhicules disponibles de la catégorie "+cat+" entre le "+dateD+" et le "+dateF);
		int i=1;
		ArrayList<String> listeImmat= new ArrayList<String>();
		while(res.next()) {
			listeImmat.add(res.getString(1));
			System.out.println(i+" ) "+res.getString(1)+" , "+res.getString(2));
		}
		if(i==1) {
			System.out.println("Aucune réponse ne correspond à votre recherche, vérifiez si votre catégorie de véhicule éxiste ou alors aucun véhicule n'est disponible aux notes que vous avez rentré!");
		}
		stt.close();
		res.close();
		Question2.repondreQuestion(cnt, listeImmat, dateD, dateF);
	}

}
