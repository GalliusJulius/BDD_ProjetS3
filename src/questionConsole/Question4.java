package questionConsole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe permettant de répondre à la question 4 en mode console
 * @author victo &rem
 * 
 */
public class Question4 {
	
	/**
	 * Methode permettant de montrer les codes agences qui possedent toutes les categories de véhicules
	 * @param cnt connection courrante 
	 * @throws SQLException erreur sql
	 */
	public static void repondreQuestion(Connection cnt) throws SQLException {
		PreparedStatement stt = cnt.prepareStatement("SELECT AGENCE.CODE_AG from AGENCE INNER JOIN VEHICULE on AGENCE.CODE_AG = VEHICULE.CODE_AG"
				 +" INNER JOIN CATEGORIE on VEHICULE.CODE_CATEG = CATEGORIE.CODE_CATEG"
				 +" group by AGENCE.CODE_AG"
				 +" having count(distinct VEHICULE.CODE_CATEG) = (select count(*) from CATEGORIE)");
		ResultSet res = stt.executeQuery();
		System.out.println("Voici la liste des agences possédant toutes les catégories de véhicules");
		while(res.next()) {
			System.out.println(res.getString(1));
		}
		res.close();
		stt.close();
	}

}