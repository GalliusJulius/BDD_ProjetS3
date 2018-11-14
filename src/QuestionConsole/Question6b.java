package QuestionConsole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe peremettant de vérifier le trigger de la question 6b
 * @author victo & rem
 *
 */
public class Question6b {
	
	public static void repondreQuestion(Connection cnt) throws SQLException {
		System.out.println("Verification de l'ajout automatique dans la table audit");
		System.out.println("Assurez-vous que le trigger et la table audit sont bien présentes sur votre bdd");
		System.out.println("Tapez sur la touche entrée pour continuer");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println("Nous allons tout d'abbord droper toutes les entrées dans votre table dossier");
		System.out.println("Ensuite nous réinsererons toutes les entrées et enfin afficher audit");
		System.out.println("Tapez sur la touche entrée pour continuer");
		sc.nextLine();
		PreparedStatement stt = cnt.prepareStatement("delete from Audit_");
		stt.executeUpdate();
		stt.close();
		stt = cnt.prepareStatement("delete from dossier");
		stt.executeUpdate();
		stt.close();
		List<List<String>> listeStrings=Arrays.asList(Arrays.asList("1","1-10-2015","5-10-2015","t1","x","duvig001","7418yc54","Nancy","Nancy","Nancy"),Arrays.asList("2","1-10-2015","5-10-2015","t1",null,"dumon001","2569yp54","Nancy","Nancy","Nancy"),Arrays.asList("3","2-10-2015","10-10-2015","t1","x","delar001","1789xv54","Nancy","Nancy","Nancy"),Arrays.asList("4","2-10-2015","5-10-2015","t1",null,"delam001","5213ye54","Nancy","Nancy","Nancy"),Arrays.asList("5","6-10-2015","7-10-2015","t2","x","roule001","7418yc54","Nancy","Nancy","Nancy"),Arrays.asList("6","10-10-2015","15-10-2015","t1",null,"duvig001","6213yd54","Nancy","Strasbourg","Nancy"),Arrays.asList("7","10-10-2015","20-10-2015","t1","x","dumon001","1234ya54","Nancy","Nancy","Nancy"),Arrays.asList("8","13-10-2015","14-10-2015","t3",null,"delar001","7418yc54","Nancy","Nancy","Nancy"),Arrays.asList("9","13-10-2015","14-10-2015","t2",null,"delar001","6213yd54","Nancy","Nancy","Nancy"),Arrays.asList("10","21-10-2015","25-10-2015","t1","x","roule001","1234ya54","Nancy","Nancy","Nancy"));
		for(int i =0;i<10;i++) {
			System.out.println(i);
			stt = cnt.prepareStatement(" insert into dossier values(?,?,?,null,null,null,?,?,null,null,null,?,?,?,?,?)");  
			for(int x =1;x<=10;x++) {
				System.out.println(x);
				if(x==2 || x==3) {
					stt.setDate(x, new Date(Integer.parseInt(listeStrings.get(i).get(x-1).split("-")[2])-1900,Integer.parseInt(listeStrings.get(i).get(x-1).split("-")[1])-1,Integer.parseInt(listeStrings.get(i).get(x-1).split("-")[0])));
				}
				else {
					stt.setString(x, listeStrings.get(i).get(x-1));
				}
			}
			stt.executeUpdate();
			stt.close();
		}
		System.out.println("VOICI LA TABLE AUDIT_ : ");
		stt = cnt.prepareStatement(" select NO_DOSSIER, DATE_REGISTRE, NOM, MARQUE, MODELE from AUDIT_");
		ResultSet res = stt.executeQuery();
		while(res.next()) {
			System.out.println(res.getString(1)+ " , "+ res.getString(2));
		}
		stt.close();
	}

}
