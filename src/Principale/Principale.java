package Principale;

import java.util.Scanner;
import graphique.Fenetre;
import javafx.application.Application;

/**
 * Classe principale de l'application.
 */
public class Principale {
	
	public static void main (String [] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Vous allez choisir entre le mode graphique ou le mode console. \nPour le mode graphique tapez 1 sinon tapez n'importe quoi d'autre!");
		String rep = sc.next();
		if(rep.equals("1")) {
			Application.launch(Fenetre.class, args);
		}
		else {
			ModeConsole mod = new ModeConsole();
		}
	}

}
