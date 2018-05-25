package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMain {
    public final Map<String, ServerWorker> workers;

    public ServerMain() {
        workers = new ConcurrentHashMap<>();
    }

    public void startServer(int port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket.getInetAddress() + ":" + port + ".");

            while (true) {
                // neprekidno osluskujemo ima li novih konekcija
                Socket clientSocket = serverSocket.accept();
                // za svakog klijenta pravimo novi thread
                ServerWorker worker = new ServerWorker(this, clientSocket);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter port number: ");
        int port = scanner.nextInt();
        scanner.close();

        ServerMain server = new ServerMain();
        server.startServer(port);
    }
}
