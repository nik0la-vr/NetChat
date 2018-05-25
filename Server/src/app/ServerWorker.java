package app;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Client -> Server
// QUIT
// NAME <my_name>
// SEND <recipient_name> <message>

// Server -> Client
// NAME [ok|taken]
// ONLINE <client_name>
// OFFLINE <client_name>
// RECEIVE <sender_name> <message>

public class ServerWorker extends Thread {
	
	// client
    private String id;
    private String name;
	private PrintWriter out;
	private BufferedReader in;
	private ServerMain server;
	
	public ServerWorker(ServerMain server, Socket socket) throws IOException {
	    this.server = server;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		id = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
        System.out.println("Connected to " + id + ".");
    }
	
	@Override
	public void run() {
        try {
			String line;

			while ((line = in.readLine()) != null && !line.equals("QUIT")) {
                String tokens[] = line.split("\\s+");
                System.out.println("\nReceived from " + (name == null ? id : name) + ":\n\t" + line);

                if (name == null) {
                    if (tokens[0].equals("NAME")) {
                        if (server.workers.containsKey(tokens[1])) {
                            sendCommand("NAME taken");
                        } else {
                            name = tokens[1];
                            broadcastCommand("ONLINE new " + name);
                            server.workers.put(name, this);
                            sendCommand("NAME ok");
                        }
                    }
                } else {
                    switch (tokens[0]) {
                        case "ONLINE":
                            if (tokens[1].equals("all")) {
                                sendCommand("ONLINE all " + String.join(" ", new ArrayList<>(server.workers.keySet())));
                            }
                            break;
                        case "SEND":
                            ServerWorker recipient = server.workers.get(tokens[1]);
                            if (recipient != null) {
                                recipient.sendCommand("RECEIVE " + name + " " + extractMessage(line));
                            }
                            break;
                        default:
                            System.err.println("Unknown command " + tokens[0] + ".");
                            break;
                    }
                }
			}

            logout(server.workers);
        } catch (IOException e) {
            logout(server.workers);
            System.out.println("Connection to " + (name != null ? name : id) + " was abruptly closed.");
		}
	}

	private String extractMessage(String line) {
	    return line.replaceFirst("\\w+\\s+\\w+\\s+", "");
    }

	private void logout(Map<String, ServerWorker> map) {
	    if (name != null) {
	        map.remove(name);
            broadcastCommand("OFFLINE " + name);
        }
    }

    private void broadcastCommand(String message) {
        for (Map.Entry<String, ServerWorker> entry : server.workers.entrySet()) {
            entry.getValue().sendCommand(message);
        }
        System.out.println("\nSent to all:\n\t" + message);
    }

    private void sendCommand(String message) {
        out.println(message);
        System.out.println("\nSent to " + (name != null ? name : id) + ":\n\t" + message);
    }

}
