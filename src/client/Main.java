package client;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 5000;

            new Thread(new ClientRunnable(socket)).start();

            Scanner scanner = new Scanner(System.in);
            String userInput;
            String clientName = "empty";

            do {
                if (clientName.equals("empty")) {
                    System.out.println("Enter your name: ");
                    clientName = scanner.nextLine();
                    sendMessage(socket, serverAddress, serverPort, clientName);
                }

                System.out.println("Enter your message (use '@recipient' for private messages): ");
                userInput = scanner.nextLine();

                if (userInput.startsWith("@")) {
                    // Message privé
                    int spaceIndex = userInput.indexOf(" ");
                    if (spaceIndex != -1) {
                        String recipient = userInput.substring(1, spaceIndex);  // Récupère le nom du destinataire après "@"
                        String messageContent = userInput.substring(spaceIndex + 1);  // Récupère le contenu du message après le nom du destinataire
                        String message = "private:" + recipient + ":" + clientName + ": " + messageContent;
                        sendMessage(socket, serverAddress, serverPort, message);
                    } else {
                        System.out.println("Invalid private message format. Use '@recipient message'.");
                    }
                } else {
                    // Message broadcast
                    String message = "broadcast:" + clientName + ": " + userInput;
                    sendMessage(socket, serverAddress, serverPort, message);
                }

            } while (!userInput.equals("exit"));

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(DatagramSocket socket, InetAddress address, int port, String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
