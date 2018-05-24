package workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerWorker extends Thread {
	
	// client
	private String id;
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	
	public ServerWorker(Socket socket) {
		this.socket = socket;
		id = String.format("%s:%d", socket.getInetAddress().getHostAddress(), socket.getPort());
		
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
				System.out.println(id + " -> " + line);
			}
		} catch (IOException e) {
			System.out.println(String.format("Connection to %s was abruptly closed.", id));
		}
	}
}
