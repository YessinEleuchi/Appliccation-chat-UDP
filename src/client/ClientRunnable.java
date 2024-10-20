package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientRunnable implements Runnable {
    private DatagramSocket socket;

    public ClientRunnable(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String response = new String(packet.getData(), 0, packet.getLength());

                if (response.equals("server_shutdown")) {
                    System.out.println("Server has shut down. Exiting...");
                    break;  // Quitte la boucle pour arrêter le client
                }

                System.out.println(response); // Affiche les autres messages
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();  // Fermer le socket après avoir quitté la boucle
        }
    }
}
