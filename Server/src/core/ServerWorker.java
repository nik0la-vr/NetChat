package core;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

class ServerWorker extends Thread {
	
	// client
    private String id;
    private String name;
    private Socket socket;
	private InputStream in;
	private OutputStream out;

	private Server server;
	
	ServerWorker(Server server, Socket socket) {
		this.server = server;
	    this.socket = socket;

	    id = String.format("%s:%d", socket.getInetAddress().getHostAddress(), socket.getPort());

        try {
			in  = socket.getInputStream();
			out = socket.getOutputStream();
            System.out.println(String.format("Connected to %s.", id));
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			for (String line = reader.readLine(); !(line == null || line.equalsIgnoreCase("quit")); line = reader.readLine()) {
				if (line.equals("")) continue;

                System.out.print("received: " + line);

				ArrayList<String> tokens = new ArrayList<>(Arrays.asList(line.trim().split("\\s+")));
                String command = tokens.get(0);
                tokens.remove(0);

                if (name == null) {
                    if (command.equals("name")) {
                        if (tokens.size() > 0) {
                            String myName = tokens.get(0);
                            if (server.workers.containsKey(myName)) {
                                sendCommand("name taken");
                            } else {
                                name = myName;
                                broadcastCommand("online " + name);
                                server.workers.put(name, this);
                                sendCommand("name ok");
                            }
                        } else {
                            sendCommand("error expected 'name <name>'");
                        }
                    } else {
                        sendCommand("error name");
                    }
                } else {
                    switch (command) {
                        case "send":
                            String recipientName = tokens.get(0);
                            tokens.remove(0);
                            ServerWorker recipient = server.workers.get(recipientName);
                            if (recipient != null) {
                                recipient.sendCommand(String.format("receive %s %s", name, String.join(" ", tokens)));
                            }
                            break;
                        default:
                            sendCommand("error unknown");
                            break;
                    }
                }
			}

            logout(server.workers);
        } catch (IOException e) {
            try {
                logout(server.workers);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println(String.format("Connection to %s was abruptly closed.", name != null ? name : id));
		}
	}

	private void logout(Map<String, ServerWorker> map) throws IOException {
	    if (name != null) {
	        map.remove(name);
            broadcastCommand("offline " + name);
        }
    }

    private void broadcastCommand(String message) throws IOException {
        for (Map.Entry<String, ServerWorker> entry : server.workers.entrySet()) {
            entry.getValue().sendCommand(message);
        }
    }

    private void sendCommand(String message) throws IOException {
        out.write(message.getBytes());
    }

}
