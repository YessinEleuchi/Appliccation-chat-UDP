package server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //Liste pour gerer tous les clients (optionnel en UDP, mais gardee pour coherence)
        ArrayList<ServerThread> threadList = new ArrayList<>();

        try (DatagramSocket serverSocket = new DatagramSocket(5000)) {
            System.out.println("Server is waiting for clients...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                // Reception du message du client
                serverSocket.receive(packet);

                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server received: " + receivedMessage);

                // Envoi de la reponse a tous les clients
                String response = "Server says: " + receivedMessage;
                byte[] responseData = response.getBytes();

                // Envoyer la reponse au client qui a envoye le paquet
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
                serverSocket.send(responsePacket);
            }
        } catch (Exception e) {
            System.out.println("Error occurred in server: " + e.getMessage());
        }
    }
}
