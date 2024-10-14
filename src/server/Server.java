package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    private Socket socket;
    private ArrayList<Server> threadList;
    private PrintWriter output;

    public Server(Socket socket, ArrayList<Server> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {
            // Reading the input from Client
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // returning the output to the client : true statement is to flush the buffer
            // otherwise
            // we have to do it manuallyy
            output = new PrintWriter(socket.getOutputStream(), true);

            // inifite loop for server
            while (true) {
                String outputString = input.readLine();
                // if user types exit command
                if (outputString.equals("exit")) {
                    break;
                }
                printToALlClients(outputString);
                // output.println("Server says " + outputString);
                System.out.println("Server received " + outputString);

            }

        } catch (Exception e) {
            System.out.println("Error occured " + e.getStackTrace());
        }
    }

    private void printToALlClients(String outputString) {
        for (Server sT : threadList) {
            sT.output.println(outputString);
        }

    }
}
