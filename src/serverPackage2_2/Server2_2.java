package serverPackage2_2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import commonPackage.Operation;

public class Server2_2 {
	 public static void main(String[] args) {

	        try (ServerSocket serveur = new ServerSocket(5000)) {

	            System.out.println("Serveur demarre sur le port 5000.");
	            System.out.println("En attente d'un client...");

	            try (Socket socket = serveur.accept();
	                 ObjectOutputStream sortie =
	                     new ObjectOutputStream(socket.getOutputStream())) {

	                sortie.flush();

	                try (ObjectInputStream entree =
	                         new ObjectInputStream(socket.getInputStream())) {

	                    System.out.println("Client connecte.");

	                    while (true) {

	                        Object objet = entree.readObject();

	                        if (objet == null) {
	                            break;
	                        }

	                        Operation operation = (Operation) objet;

	                        double a = operation.getOperande1();
	                        char op = operation.getOperateur();
	                        double b = operation.getOperande2();

	                        System.out.println("Operation recue : "
	                                + a + " " + op + " " + b);

	                        String reponse;

	                        switch (op) {
	                            case '+':
	                                reponse = String.valueOf(a + b);
	                                break;

	                            case '-':
	                                reponse = String.valueOf(a - b);
	                                break;

	                            case '*':
	                                reponse = String.valueOf(a * b);
	                                break;

	                            case '/':
	                                if (b == 0) {
	                                    reponse = "Erreur : division par zero.";
	                                } else {
	                                    reponse = String.valueOf(a / b);
	                                }
	                                break;

	                            default:
	                                reponse = "Erreur : operateur invalide.";
	                        }

	                        System.out.println("Resultat envoye : " + reponse);

	                        sortie.writeObject(reponse);
	                        sortie.flush();
	                    }
	                }
	            }

	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Erreur serveur : " + e.getMessage());
	        }
	    }
}
