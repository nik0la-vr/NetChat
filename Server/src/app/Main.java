package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import workers.ServerWorker;

public class Main {
	
	private static boolean working = true;

	public static void main(String[] args) {
		try {
			System.out.print("Port number: ");
			
			Scanner scanner = new Scanner(System.in);
			ServerSocket serverSocket = new ServerSocket(scanner.nextInt());
			scanner.close();
			
			while (working) {
				// neprekidno osluskujemo ima li novih konekcija
				System.out.println("Waiting for connection...");
				Socket clientSocket = serverSocket.accept();
				System.out.println(String.format("Connected to %s:%d.", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort()));
				// za svakog klijenta pravimo novi thread, pre svega zato sto je accept metoda blokirajuca
				ServerWorker worker = new ServerWorker(clientSocket);
				worker.start();
			}
			
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void turnOff() {
		working = false;
	}

}
