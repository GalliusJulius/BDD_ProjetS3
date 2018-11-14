package Principale;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import QuestionConsole.Question1;
import QuestionConsole.Question4;
import QuestionConsole.Question5;
import QuestionConsole.Question6a;
import QuestionConsole.Question6b;


/**
 * Permet de creer le mode console et de l'utiliser
 * @author victo & rem
 *
 */
public class ModeConsole {
	/**
	 * Connection lie a l'instance de la classe 
	 */
	Connection cnt;
	
	/**
	 * Lance la methode principale du mode console
	 */
	public ModeConsole() {
		principale();
	}
	
	/**
	 * Methode qui a pour but de faire le principale pour le mode console
	 */
	public void principale() {
		boolean errone = false;
		boolean continuer = true;
		Scanner sc = new Scanner(System.in);
		boolean connecte = false;
		while(!connecte) {
			try {
				//TODO : faire une saisie clavier pour la connec (plus simple pour se connecter pour dev)
				System.out.println("Veuilez rentrer l'url de votre bdd");
				String url = sc.next();
				System.out.println("Veuillez rentrer votre login");
				String login = sc.next();
				System.out.println("Veuillez rentrer votre mot de passe");
				String mdp =sc.next();
				//jdbc:oracle:thin:@localhost:1521:XE
				cnt = DriverManager.getConnection(url,login,mdp);
				connecte = true;
			} catch (SQLException e) {
				System.out.println("Connection échoué!");
			}
		}
		System.out.println("Bienvenue en mode console!");
		while(continuer) {
			System.out.println("Veuillez entrer le numéro correspondant à/aux question(s) que vous souhaitez effectuer \n1=>question 1,2 et3 \n2=>question 4 \n3=>question 5 \n4=>question 6 ");
			String choix = sc.next();
			switch(choix) {
			case "1":
				errone = true;
				Date dateD = null;
				Date dateF = null;
				String cat= "";
				//tant que le format de la date n'est pas bon
				while(errone) {
					errone = true;
					//demande des infos necessaires pour l'execution
					System.out.println("Veuillez entrer la date de début dans le format : jour-mois-année (avec les tirets et tout en chiffre))");
					String dateDt = sc.next();
					System.out.println("Veuillez entrer la date de fin dans le format : jour-mois-année (avec les tirets et tout en chiffre)");
					String dateFt = sc.next();
					System.out.println("Veuillez entrer la catégorie du véhicule voulue");
					cat = sc.next();
					//conversion des phrase en date
					try{
						if((dateDt.split("-").length==3 && dateFt.split("-").length==3)){
							dateD = new Date(Integer.parseInt(dateDt.split("-")[2])-1900,Integer.parseInt(dateDt.split("-")[1])-1,Integer.parseInt(dateDt.split("-")[0]));
							dateF = new Date(Integer.parseInt(dateFt.split("-")[2])-1900,Integer.parseInt(dateFt.split("-")[1])-1,Integer.parseInt(dateFt.split("-")[0]));
							if(dateD.after(dateF)) {
								System.out.println("La date de début est après celle de de début veuillez renrter des valeurs autorisé!");
							}
							else {
								errone=false;
							}
						}
						else {
							System.out.println("Format de date invalide veuillez recommencer");
						}
					}
					catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
						System.out.println("Format de date invalide veuillez recommencer");
					}
				}
				try {
					//on repond à la question1
					Question1.repondreQuestion(cnt, cat, dateD, dateF);
				} catch (SQLException e) {
					System.out.println("erreur sql");
					e.printStackTrace();
				}
				break;
			case "2":
				try {
					Question4.repondreQuestion(cnt);
				} catch (SQLException e) {
					System.out.println("erreur sql :");
					e.printStackTrace();
				}
				break;
			case "3":
				try {
					Question5.repondreQuestion(cnt);
				} catch (SQLException e) {
					System.out.println("erreur sql :");
					e.printStackTrace();
				}
				break;
			case "4":
				System.out.println("Vous etes dans le mode pour vérifier les triggers, tapez 1 pour vérifier le 1er sinon tapez autre chose");
				String trig = sc.next();
				if(trig.equals("1")) {
					try {
						Question6a.repondreQuestion(cnt);
					} catch (SQLException e) {
						System.out.println("erreur sql :");
						e.printStackTrace();
					}
				}
				else {
					try {
						Question6b.repondreQuestion(cnt);
					} catch (SQLException e) {
						System.out.println("erreur sql :");
						e.printStackTrace();
					}
				}
				break;
			default :
				errone = true;
				System.out.println("Ce numéro n'est pas entre 1 et 4, veuillez entrer un autre numéro!");
				break;
			}
			if(!errone) {
				System.out.println("Si vous souhaitez quitter l'application tapez 1 sinon tapez autre chose pour voir une autre question");
				String rep = sc.next();
				if(rep.equals("1")) {
					continuer = false;
				}
			}
		}
		sc.close();
	}
	
}
