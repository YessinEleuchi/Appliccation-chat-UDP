package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class ServerThread implements Runnable {
    private DatagramSocket socket;
    private HashMap<String, InetAddress> clientAddresses;
    private HashMap<String, Integer> clientPorts;

    public ServerThread(DatagramSocket socket) {
        this.socket = socket;
        this.clientAddresses = new HashMap<>();
        this.clientPorts = new HashMap<>();
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];

        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                String[] parts = message.split(":");

                if (parts[0].equals("private")) {
                    // Message privé
                    String recipient = parts[1];
                    String sender = parts[2];
                    String privateMessage = sender + " (private): " + parts[3];
                    sendPrivateMessage(privateMessage, recipient);
                } else if (parts[0].equals("broadcast")) {
                    // Message broadcast
                    String sender = parts[1];
                    String broadcastMessage = sender + " (broadcast): " + parts[2];
                    broadcast(broadcastMessage, clientAddress, clientPort);
                } else {
                    // Enregistrer le client s'il n'est pas encore enregistré
                    String clientName = parts[0];
                    if (!clientAddresses.containsKey(clientName)) {
                        clientAddresses.put(clientName, clientAddress);
                        clientPorts.put(clientName, clientPort);
                        System.out.println(clientName + " has joined the chat!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode broadcast pour envoyer un message à tous les clients sauf l'expéditeur
    private void broadcast(String message, InetAddress senderAddress, int senderPort) {
        byte[] buffer = message.getBytes();

        for (String clientName : clientAddresses.keySet()) {
            try {
                InetAddress address = clientAddresses.get(clientName);
                int port = clientPorts.get(clientName);

                // N'envoie pas le message à l'expéditeur
                if (!(address.equals(senderAddress) && port == senderPort)) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                    socket.send(packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour envoyer un message privé à un destinataire spécifique
    private void sendPrivateMessage(String message, String recipient) {
        byte[] buffer = message.getBytes();

        try {
            InetAddress address = clientAddresses.get(recipient);
            int port = clientPorts.get(recipient);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Recipient not found: " + recipient);
        }
    }

    // Méthode pour envoyer un message de shutdown à tous les clients
    public void broadcastShutdown(String shutdownMessage) {
        byte[] buffer = shutdownMessage.getBytes();

        for (String clientName : clientAddresses.keySet()) {
            try {
                InetAddress address = clientAddresses.get(clientName);
                int port = clientPorts.get(clientName);

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
