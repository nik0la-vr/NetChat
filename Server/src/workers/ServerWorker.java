package workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.sound.sampled.Line;

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

			for (
				String line = reader.readLine(); // unutar readLine je beskonacna petlja koja se vrti sve dok je bafer prazan
				!(socket.isClosed() || line.equalsIgnoreCase("quit") || line == null);
				line = reader.readLine()
			) {
				
				System.out.println(id + " -> " + line);
			
			}

			if (!socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
