package app;

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

        System.out.println(String.format("Connected to %s.", id));

        try {
			in  = socket.getInputStream();
			out = socket.getOutputStream();
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
                            name = String.join(" ", tokens);
                        } else {
                            write("error expected name <name>");
                        }
                    } else {
                        write("error required name");
                    }
                } else {
                    switch (command) {
                        default:
                            write("error unknown");
                            break;
                    }
                }

			}
		} catch (IOException e) {
			System.out.println(String.format("Connection to %s was abruptly closed.", id));
		}
	}

	private void write(String message) throws IOException {
        out.write(message.getBytes());
    }

}
