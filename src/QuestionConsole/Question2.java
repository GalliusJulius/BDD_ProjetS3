package QuestionConsole;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Classe repondant à la question 2
 * @author victo et rem
 *
 */
public class Question2 {
	
	/**
	 * Methode qui va mette a jour la table sur une voiture qui est dispo
	 * @param cnt connection courrante
	 * @param listeImmat liste des plaques des vehicules dispo
	 * @param dateD date debut reservation
	 * @param dateF date fin reservation 
	 * @throws SQLException erreur d'execution
	 */
	public static void repondreQuestion(Connection cnt, ArrayList<String> listeImmat,Date dateD,Date dateF) throws SQLException {
		System.out.println("Veuillez entrer le numéro d'immatriculation parmi les véhicules proposés pour le réserver aux dates que vous avez rentré sinon tapez -1");
		Scanner sc = new Scanner(System.in);
		String immat = sc.next();
		while(!listeImmat.contains(immat) && !immat.equals("-1")) {
			System.out.println("Immat introuvable entrez une valeur valable ou entrez -1 pour sortir");
			immat = sc.next();
		}
		//si l'user veut reserver
		if(!immat.equals("-1")) {
			//tant qu'il n'a pas entré un immatriculation valide
			Date iteration = (Date) dateD.clone(); 
			//on parcourt les dates une par une (pour pouvoir inserer les lignes quand n'éxiste pas dans calendrier)
			while(iteration.before(dateF)) {
				PreparedStatement stt = cnt.prepareStatement("select NO_IMM from CALENDRIER "
					+ "where DATEJOUR = ? "
					+ "and NO_IMM = ?");
				stt.setDate(1, iteration);
				stt.setString(2, immat);
				ResultSet res = stt.executeQuery();
				stt.close();
				//si la ligne n'existe pas
				if(res.getRow() ==0) {
					stt = cnt.prepareStatement("insert into calendrier values (?,?,'x')");
				}
				//sinon on met à jour la ligne pour comme plus dispo
				else {
					stt = cnt.prepareStatement("update CALENDRIER set PASLIBRE = 'x'"
												+ " where NO_IMM=?"
												+ " and DATEJOUR=?");
				}
				stt.setString(1, immat);
				stt.setDate(2, iteration);
				stt.executeUpdate();
				//on incremente la date de 1 jour
				Calendar c = Calendar.getInstance();
				c.setTime(iteration);
				c.add(Calendar.DAY_OF_MONTH, 1);
				iteration = new Date(c.getTime().getTime());
				//on ferme les flux (pour éviter une surcharge)
				res.close();
				stt.close();
			}
			Question3.repondreQuestion(cnt, immat, dateD, dateF);
		}
	}

}
