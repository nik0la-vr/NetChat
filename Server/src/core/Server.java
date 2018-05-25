package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final int port;

    final Map<String, ServerWorker> workers;

    public Server(int port) {
        this.port = port;
        workers = new HashMap<>();
    }

    public void run() {
        try {
            boolean working = true;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(serverSocket.getInetAddress().getHostAddress() + ":" + port);

            while (working) {
                // neprekidno osluskujemo ima li novih konekcija
                System.out.println("Waiting for connection...");
                Socket clientSocket = serverSocket.accept();
                // za svakog klijenta pravimo novi thread
                ServerWorker worker = new ServerWorker(this, clientSocket);
                worker.start();
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
