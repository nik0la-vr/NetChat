package core;

import forms.ChatForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client extends Thread {
    private String name;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private ChatForm chatForm;
    private String chosenName;

    public Client(ChatForm chatForm, String ip, int port) {
        this.chatForm = chatForm;

        try {
            socket = new Socket(ip, port);
            in = new BufferedInputStream(socket.getInputStream());
            out = new BufferedOutputStream(socket.getOutputStream());
            chatForm.write("Connection to the server established.", ChatForm.colorSuccess);
            chatForm.write("You have to set your name by typing 'name <name>'.", ChatForm.colorInfo);
        } catch (IOException e) {
            chatForm.write("There was a problem connecting to the server, please try again.", ChatForm.colorError);
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.equals("")) continue;

                ArrayList<String> tokens = parse(line);
                String command = tokens.get(0);
                tokens.remove(0);

                switch (command) {
                    case "error":
                        error(tokens);
                        break;
                    case "online":
                        chatForm.addUser(String.join(" ", tokens));
                        break;
                    case "offline":
                        chatForm.removeUser(String.join(" ", tokens));
                        break;
                    case "receive":
                        String sender = tokens.get(0);
                        tokens.remove(0);
                        chatForm.write(String.format("%s: %s", sender, String.join(" ", tokens)));
                        break;
                    case "name":
                        if (tokens.get(0).equals("ok")) {
                            name = chosenName;
                            chatForm.setTitle(String.format("%s (%s)", chatForm.getTitle(), name));
                            chatForm.write(String.format("Name set to %s.", name), ChatForm.colorSuccess);
                        } else {
                            chatForm.write("That name is already taken.", ChatForm.colorError);
                        }
                        chosenName = null;
                        break;
                    default:
                        chatForm.write(String.format("Server sent unknown command '%s'.", command), ChatForm.colorWarn);
                        sendCommand("error unknown " + command);
                        break;
                }
            }
        } catch (IOException e) {
            chatForm.write("Connection to the server was abruptly closed.", ChatForm.colorError);
        }
    }

    public void sendMessage(String message) {
        if (message.equals("")) return;

        try {
            if (name == null && chosenName == null) {
                ArrayList<String> tokens = parse(message);
                if (tokens.get(0).equals("name")) {
                    if (tokens.size() == 2) {
                        chosenName = tokens.get(1);
                        sendCommand("name " + chosenName);
                    } else {
                        chatForm.write("Name should not contain whitespaces.", ChatForm.colorError);
                    }
                } else {
                    chatForm.write("You have to set your name by typing 'name <name>'.", ChatForm.colorInfo);
                }
            } else {
                String recipient = chatForm.getRecipient();
                if (recipient != null) {
                    sendCommand(String.format("send %s %s", recipient, message));
                } else {
                    chatForm.write("You need to choose a recipient before this message can be sent", ChatForm.colorError);
                }
            }
        } catch (IOException e) {
            chatForm.write("Couldn't send message to the server.", ChatForm.colorError);
        }
    }

    private ArrayList<String> parse(String line) {
        return new ArrayList<>(Arrays.asList(line.trim().split("\\s+")));
    }

    private void error(ArrayList<String> tokens) {
        String type = tokens.get(0);
        switch (type) {
            case "unknown":
                chatForm.write(String.format("Unknown command '%s' sent to server", tokens.get(1)), ChatForm.colorError);
                break;
            default:
                chatForm.write("Server reported undefined error.", ChatForm.colorWarn);
                break;
        }
    }

    private void sendCommand(String message) throws IOException {
        out.write(message.getBytes());
    }
}
