package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {

	public static void main(String[] args) {
        System.out.print("Enter port number: ");
	    Scanner scanner = new Scanner(System.in);
        Server server = new Server(scanner.nextInt());
        scanner.close();

        server.run();
	}

}
