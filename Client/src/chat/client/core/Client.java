package chat.client.core;

import chat.client.Utils;
import chat.client.forms.ChatForm;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client extends SwingWorker<Void, GUITask> {
    private int port;
    private String ip;
    private String name;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ChatForm chatForm;
    private String chosenName;

    public Client(String ip, int port, ChatForm chatForm) {
        this.ip = ip;
        this.port = port;
        this.chatForm = chatForm;
    }

    @Override
    protected Void doInBackground() {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            publish(new GUITask(chatForm::success, "Connection to the server established."));
            publish(new GUITask(chatForm::info, "You have to set your name by typing 'NAME <name>'."));

            try {
                String line;

                while ((line = in.readLine()) != null) {
                    String tokens[] = line.split("\\s+");
                    System.out.println("Received:\n  " + line);
                    tokens[0] = tokens[0].toUpperCase();

                    switch (tokens[0]) {
                        case "ONLINE":
                            if (tokens[1].equals("new")) {
                                publish(new GUITask(chatForm::addUser, tokens[2]));
                                publish(new GUITask(chatForm::info, tokens[2] + " is now online."));
                            } else if (tokens[1].equals("all")) {
                                for (int i = 2; i < tokens.length; ++i) {
                                    if (!tokens[i].equals(name)) {
                                        publish(new GUITask(chatForm::addUser, tokens[i]));
                                    }
                                }
                            }
                            break;
                        case "OFFLINE":
                            publish(new GUITask(chatForm::removeUser, tokens[1]));
                            publish(new GUITask(chatForm::info, tokens[1] + " went offline."));
                            break;
                        case "RECEIVE":
                            publish(new GUITask(chatForm::plain, tokens[1] + ": " + Utils.extractMessage(line)));
                            break;
                        case "ERROR":
                            if (tokens[1].equals("recipient")) {
                                publish(new GUITask(chatForm::error, "Couldn't find specified recipient."));
                            }
                            break;
                        case "NAME":
                            if (tokens[1].equals("ok")) {
                                name = chosenName;
                                publish(new GUITask(chatForm::setTitle, chatForm.getTitle() + " (" + name + ") "));
                                publish(new GUITask(chatForm::success, "Name set to " + name + "."));
                                sendCommand("ONLINE all");
                            } else {
                                publish(new GUITask(chatForm::error, "That name is taken."));
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
                publish(new GUITask(chatForm::criticalError, "Connection to the server was abruptly closed."));
            } finally {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            publish(new GUITask(chatForm::criticalError, "Connection to the server failed."));
        }

        return null;
    }

    @Override
    protected void process(List<GUITask> chunks) {
        for (GUITask task : chunks) {
            task.action.accept(task.data);
        }
    }

    public void sendMessage(String message) {
        message = message.trim();
        if (message.equals("")) return;
        String[] tokens = message.split("\\s+");

        if (chosenName != null) {
            publish(new GUITask(chatForm::info, "Waiting for the server to approve your name..."));
        } else if (name == null) {
            if (tokens[0].equals("NAME")) {
                if (tokens.length == 2) {
                    chosenName = tokens[1];
                    sendCommand("NAME " + chosenName);
                } else {
                    publish(new GUITask(chatForm::error, "Name should not contain whitespaces."));
                }
            } else {
                publish(new GUITask(chatForm::info, "You have to set your name by typing 'NAME <name>'."));
            }
        } else if (tokens[0].equals("QUIT")) {
            sendCommand("QUIT");
            System.exit(0);
        } else {
            String recipient = chatForm.getRecipient();
            sendCommand("SEND " + recipient + " " + message);
            publish(new GUITask(chatForm::mine, "You2" + recipient + ": " + message));
        }
    }

    private void sendCommand(String message) {
        out.println(message);
        System.out.println("Sent:\n  " + message);
    }
}
