package chat.server.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMain {
    final Map<String, ServerWorker> workers;

    private ServerMain() {
        workers = new ConcurrentHashMap<>();
    }

    private void startServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port + ".");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ServerWorker worker = new ServerWorker(this, clientSocket);
            worker.start();
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 8818;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new ServerMain().startServer(port);
    }
}
