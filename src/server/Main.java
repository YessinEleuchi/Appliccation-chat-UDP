package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Server is waiting for clients...");

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            // Démarrer le serveur dans un thread
            ServerThread serverThread = new ServerThread(socket);
            Thread thread = new Thread(serverThread);
            thread.start();

            // Attendre que l'administrateur décide d'arrêter le serveur
            System.out.println("Press Enter to stop the server.");
            System.in.read();

            // Envoyer le message de shutdown avant de fermer le serveur
            String shutdownMessage = "server_shutdown";
            serverThread.broadcastShutdown(shutdownMessage);

            // Attendre la fin du thread avant de fermer complètement
            thread.join();
            System.out.println("Server stopped.");
        } catch (Exception e) {
            System.out.println("Error occurred in main: " + e.getMessage());
        }
    }
}
