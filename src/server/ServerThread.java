package server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private DatagramSocket socket;
    private ArrayList<ServerThread> threadList;
    private byte[] buffer = new byte[1024];

    public ServerThread(DatagramSocket socket, ArrayList<ServerThread> threadList) {
        this.socket = socket;
        this.threadList = threadList;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Reception dun message du client
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server received: " + message);

                // Envoi dune reponse a tous les clients
                String response = "Server says: " + message;
                byte[] responseData = response.getBytes();

                // Envoyer la reponse a tous les clients (broadcast)
                for (ServerThread thread : threadList) {
                    DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
                    socket.send(responsePacket);
                }

                // Si lutilisateur tape "exit", on sort de la boucle
                if (message.trim().equals("exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in server thread: " + e.getMessage());
        }
    }
}
