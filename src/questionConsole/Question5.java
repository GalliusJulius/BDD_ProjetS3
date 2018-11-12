package questionConsole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe permettant de repondre a la question 5
 * @author victo & rem
 *
 */
public class Question5 {
	
	/**
	 * Methode peremettant de montrer les clients ayant reserve deux modeles de vehicules different
	 * @param cnt connexion actuelle 
	 * @throws SQLException erreur en cas de soucis dans la requette
	 */
	public static void repondreQuestion(Connection cnt) throws SQLException {
		PreparedStatement stt = cnt.prepareStatement(" select NOM, VILLE, CODPOSTAL from CLIENT"
				+" inner join DOSSIER D on CLIENT.CODE_CLI = D.CODE_CLI"
				+" inner join VEHICULE V on D.NO_IMM = V.NO_IMM"
				+" group by NOM, VILLE, CODPOSTAL"
				+" having count(*) >= 2");
		ResultSet res = stt.executeQuery();
		while(res.next()) {
			System.out.println(res.getString(1)+ " , "+ res.getString(2)+" , "+ res.getString(3));
		}
		res.close();
		stt.close();
	}

}