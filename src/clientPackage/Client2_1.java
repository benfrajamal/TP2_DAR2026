
package clientPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2_1 {

    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     socket.getOutputStream(), true);
             Scanner clavier = new Scanner(System.in)) {

            System.out.println("Connecté au serveur.");

            while (true) {

                System.out.print(
                        "Entrez une operation (exemple : 55 * 25), "
                        + "ou FIN pour quitter : ");

                String operation = clavier.nextLine().trim();

                if (operation.equalsIgnoreCase("FIN")) {
                    out.println("FIN");
                    System.out.println("Serveur : " + in.readLine());
                    break;
                }

                if (!estValide(operation)) {
                    System.out.println(
                            "Operation invalide ! "
                            + "Exemple correct : 55 * 25");
                    continue;
                }

                out.println(operation);

                String resultat = in.readLine();

                if (resultat == null) {
                    System.out.println("Le serveur a ferme la connexion.");
                    break;
                }

                System.out.println("Résultat reçu : " + resultat);
            }

        } catch (Exception e) {
            System.out.println("Erreur client : " + e.getMessage());
            System.out.println(
                    "Vérifiez que le serveur est lancé avant le client.");
        }
    }

    private static boolean estValide(String operation) {

        String[] elements = operation.trim().split("\\s+");

        if (elements.length != 3) {
            return false;
        }

        try {
            Double.parseDouble(elements[0]);
            Double.parseDouble(elements[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        String operateur = elements[1];

        return operateur.equals("+")
                || operateur.equals("-")
                || operateur.equals("*")
                || operateur.equals("/");
    }
}

