package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

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

				ArrayList<String> tokens = new ArrayList<>(Arrays.asList(line.trim().split("\\s+")));
                String command = tokens.get(0);
                tokens.remove(0);

                if (name == null) {
                    if (command.equals("name")) {
                        if (tokens.size() > 0) {
                            String myName = String.join(" ", tokens);
                            if (server.workers.containsKey(myName)) {
                                sendMessage("name taken");
                            } else {
                                name = myName;
                                broadcastMessage("online " + name);
                                server.workers.put(name, this);
                                sendMessage("name ok");
                            }
                        } else {
                            sendMessage("error expected 'name <name>'");
                        }
                    } else {
                        sendMessage("error name");
                    }
                } else {
                    switch (command) {
                        default:
                            sendMessage("error unknown");
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
	        broadcastMessage("offline " + name);
        }
    }

    private void broadcastMessage(String message) throws IOException {
        for (Map.Entry<String, ServerWorker> entry : server.workers.entrySet()) {
            entry.getValue().sendMessage(message);
        }
    }

	private void sendMessage(String message) throws IOException {
        out.write(message.getBytes());
    }

}
