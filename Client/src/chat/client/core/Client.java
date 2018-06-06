package chat.client.core;

import chat.client.forms.ChatForm;
import chat.comon.Utils;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private String name;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ChatForm chatForm;
    private String chosenName;

    public Client(ChatForm chatForm) {
        this.chatForm = chatForm;
    }

    public boolean connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        try {
            String line;

            while ((line = in.readLine()) != null) {
                String tokens[] = line.split("\\s+");
                System.out.println("Received:\n  " + line);
                tokens[0] = tokens[0].toUpperCase();

                switch (tokens[0]) {
                    case "ONLINE":
                        if (tokens[1].equals("new")) {
                            chatForm.addUser(tokens[2]);
                            chatForm.info(tokens[2] + " is now online.");
                        } else if (tokens[1].equals("all")) {
                            for (int i = 2; i < tokens.length; ++i) {
                                if (!tokens[i].equals(name)) {
                                    chatForm.addUser(tokens[i]);
                                }
                            }
                        }
                        break;
                    case "OFFLINE":
                        chatForm.removeUser(tokens[1]);
                        chatForm.info(tokens[1] + " went offline.");
                        break;
                    case "RECEIVE":
                        chatForm.plain(tokens[1] + ": " + Utils.extractMessage(line));
                        break;
                    case "ERROR":
                        if (tokens[1].equals("recipient")) {
                            chatForm.error("Couldn't find specified recipient.");
                        }
                        break;
                    case "NAME":
                        if (tokens[1].equals("ok")) {
                            name = chosenName;
                            chatForm.setTitle(chatForm.getTitle() + " (" + name + ") ");
                            chatForm.success("Name set to " + name + ".");
                            sendCommand("ONLINE all");
                        } else {
                            chatForm.error("That name is taken.");
                        }
                        chosenName = null;
                        break;
                    default:
                        System.err.println("Unknown command " + tokens[0] + ".");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            chatForm.criticalError("Connection to the server was abruptly closed.");
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        message = message.trim();
        if (message.equals("")) return;
        String[] tokens = message.split("\\s+");

        if (chosenName != null) {
            chatForm.info("Waiting for the server to approve your name...");
        } else if (name == null) {
            if (tokens[0].equals("NAME")) {
                if (tokens.length == 2) {
                    chosenName = tokens[1];
                    sendCommand("NAME " + chosenName);
                } else {
                    chatForm.error("Name should not contain whitespaces.");
                }
            } else {
                chatForm.info("You have to set your name by typing 'NAME <name>'.");
            }
        } else if (tokens[0].equals("QUIT")) {
            sendCommand("QUIT");
            System.exit(0);
        } else {
            String recipient = chatForm.getRecipient();
            sendCommand("SEND " + recipient + " " + message);
            chatForm.mine("You2" + recipient + ": " + message);
        }
    }

    private void sendCommand(String message) {
        out.println(message);
        System.out.println("Sent:\n  " + message);
    }
}
