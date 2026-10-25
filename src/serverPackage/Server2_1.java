
package serverPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2_1 {

    private static final int PORT = 5000;

    public static void main(String[] args) {

        System.out.println("Serveur : démarrage...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Serveur en attente d'un client sur le port " + PORT);

            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(
                         socket.getOutputStream(), true)) {

                System.out.println("Client connecté : "
                        + socket.getInetAddress());

                String operation;

                while ((operation = in.readLine()) != null) {

                    System.out.println("Opération reçue : " + operation);

                    if (operation.equalsIgnoreCase("FIN")) {
                        out.println("Communication terminée.");
                        break;
                    }

                    String resultat = calculer(operation);

                    out.println(resultat);

                    System.out.println("Résultat envoyé : " + resultat);
                }

                System.out.println("Connexion client terminée.");

            }

        } catch (Exception e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }

        System.out.println("Serveur arrêté.");
    }

    private static String calculer(String operation) {

        try {
            String[] elements = operation.trim().split("\\s+");

            if (elements.length != 3) {
                return "Erreur : format incorrect. Exemple : 55 * 25";
            }

            double a = Double.parseDouble(elements[0]);
            String operateur = elements[1];
            double b = Double.parseDouble(elements[2]);

            double resultat;

            switch (operateur) {

                case "+":
                    resultat = a + b;
                    break;

                case "-":
                    resultat = a - b;
                    break;

                case "*":
                    resultat = a * b;
                    break;

                case "/":
                    if (b == 0) {
                        return "Erreur : division par zero.";
                    }
                    resultat = a / b;
                    break;

                default:
                    return "Erreur : operateur non autorise.";
            }

            if (Double.isFinite(resultat)
                    && resultat == Math.rint(resultat)
                    && Math.abs(resultat) < 1.0e16) {
                return String.valueOf((long) resultat);
            }

            return String.valueOf(resultat);

        } catch (NumberFormatException e) {
            return "Erreur : les operandes doivent etre numeriques.";
        }
    }
}

