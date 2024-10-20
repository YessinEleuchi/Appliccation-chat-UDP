package client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientRunnable implements Runnable {

    private DatagramSocket socket;
    private byte[] buffer = new byte[1024];

    public ClientRunnable(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Recevoir la reponse du serveur
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // Afficher la reponse du serveur
                String response = new String(packet.getData(), 0, packet.getLength());
                System.out.println(response);

                // Sortir de la boucle si le message est "exit"
                if (response.trim().equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
