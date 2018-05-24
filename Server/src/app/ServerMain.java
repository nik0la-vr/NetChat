package app;

import core.Server;

import java.util.Scanner;

public class ServerMain {

	public static void main(String[] args) {
        System.out.print("Enter port number: ");
	    Scanner scanner = new Scanner(System.in);
	    int port = scanner.nextInt();
        scanner.close();

        Server server = new Server(port);
        server.run();
	}

}
