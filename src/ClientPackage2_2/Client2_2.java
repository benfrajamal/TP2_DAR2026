package ClientPackage2_2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import commonPackage.Operation;

public class Client2_2 {
	public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream sortie =
                 new ObjectOutputStream(socket.getOutputStream())) {

            sortie.flush();

            try (ObjectInputStream entree =
                     new ObjectInputStream(socket.getInputStream());
                 Scanner clavier = new Scanner(System.in)) {

                System.out.println("Connecte au serveur.");

                while (true) {

                    System.out.print(
                        "Premier operande (ou FIN pour quitter) : ");
                    String texte = clavier.nextLine().trim();

                    if (texte.equalsIgnoreCase("FIN")) {
                        break;
                    }

                    double a;

                    try {
                        a = Double.parseDouble(texte);
                    } catch (NumberFormatException e) {
                        System.out.println("Premier nombre invalide.");
                        continue;
                    }

                    System.out.print("Operateur (+, -, *, /) : ");
                    String symbole = clavier.nextLine().trim();

                    if (symbole.length() != 1 ||
                        "+-*/".indexOf(symbole.charAt(0)) == -1) {
                        System.out.println("Operateur invalide.");
                        continue;
                    }

                    char op = symbole.charAt(0);

                    System.out.print("Deuxieme operande : ");
                    texte = clavier.nextLine().trim();

                    double b;

                    try {
                        b = Double.parseDouble(texte);
                    } catch (NumberFormatException e) {
                        System.out.println("Deuxieme nombre invalide.");
                        continue;
                    }

                    Operation operation = new Operation(a, op, b);

                    sortie.writeObject(operation);
                    sortie.flush();

                    String resultat = (String) entree.readObject();

                    System.out.println("Resultat recu : " + resultat);
                    System.out.println();
                }

                System.out.println("Communication terminee.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}

