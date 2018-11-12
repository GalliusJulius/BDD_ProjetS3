package Principale;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.*;

public class Principale {
	
	public static void main (String [] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Vous allez choisir entre le mode graphique ou le mode console, pour le mode graphique tapez 1 sinon tapez n'importe quoi d'autre!");
		String rep = sc.next();
		if(rep.equals("1")) {
			Modele mod = new Modele(1000,500);
		}
		else {
			ModeConsole mod = new ModeConsole();
		}
	}

}
