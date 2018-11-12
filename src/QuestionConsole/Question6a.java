package QuestionConsole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Classe peremttant de tester le 1er triger
 * @author victo
 *
 */
public class Question6a {
	
	public static void repondreQuestion(Connection cnt) throws SQLException {
		//Test l'incrémentation en cas de modif du kilométrage retour sur dossier 1
		System.out.println("Verification de la mise à jour du kilométrage d'un véhicule:");
		System.out.println("Avant tout voici les kilométrages des véhicules dans votre table:");
		Question6a.afficherTableVehicule(cnt);
		System.out.println("Nous allons donc tout d'abbord update le kilométrage de retour du dossier 1 dans votre base à 5 (de base ils sont tous nuls).");
		System.out.println("Nous afficherons ensuite la table véhicule pour constater l'incrémentation pour le véhicules 7418yc54 qui est dans le dossier 1");
		System.out.println("Tapez ce que vous voulez et tapez sur entrer quand vous etes pret");
		Scanner sc = new Scanner(System.in);
		sc.next();
		PreparedStatement stt = cnt.prepareStatement("update DOSSIER set KIL_RETOUR = 5 where NO_DOSSIER=1");
		stt.executeUpdate();
		stt.close();
		Question6a.afficherTableVehicule(cnt);
		System.out.println("vous pouvez constater la modification du kilométrage du véhicule immatriculé 7418yc54");
		System.out.println("Tapez quelque chose puis tapez la touche entré pour continuer le test");
		sc.next();
		System.out.println("On va maintenant tester en mettant 250 dans retrait et 500 dans retour sur le dossier 2 correspondant au véhicule 2569yp54 pour vérifier que son kilométrage s'incrémente de 250");
		System.out.println("Tapez une touche puis la touche entré pour commencer");
		sc.next();
		stt = cnt.prepareStatement("update DOSSIER set KIL_RETRAIT = 250 where NO_DOSSIER=2");
		stt.executeUpdate();
		stt.close();
		stt = cnt.prepareStatement("update DOSSIER set KIL_RETOUR = 500 where NO_DOSSIER=2");
		stt.executeUpdate();
		stt.close();
		Question6a.afficherTableVehicule(cnt);
		System.out.println("Le test du triger 1 est fini!");
	}
	
	public static void afficherTableVehicule(Connection cnt) throws SQLException {
		PreparedStatement stt = cnt.prepareStatement(" select NO_IMM, KILOMETRES from VEHICULE");
		ResultSet res = stt.executeQuery();
		while(res.next()) {
			System.out.println(res.getString(1)+ " , "+ res.getString(2));
		}
		stt.close();
	}

}
