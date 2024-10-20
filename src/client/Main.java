package client;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            Scanner scanner = new Scanner(System.in);
            byte[] buffer = new byte[1024];
            String userInput;
            String clientName = "empty";

            do {
                if (clientName.equals("empty")) {
                    System.out.println("Enter your name:");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                } else {
                    System.out.println("(" + clientName + "):");
                    userInput = scanner.nextLine();
                }

                // Creer le message a envoyer au serveur
                String message = "(" + clientName + "): " + userInput;
                byte[] sendData = message.getBytes();

                // Creer et envoyer un paquet UDP au serveur
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 5000);
                clientSocket.send(sendPacket);

                // Recevoir la reponse du serveur
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(receivePacket);
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(receivedMessage);

            } while (!userInput.equals("exit"));

        } catch (Exception e) {
            System.out.println("Error occurred in client: " + e.getMessage());
        }
    }
}
