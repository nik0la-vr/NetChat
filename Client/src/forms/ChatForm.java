package forms;

import core.Client;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ChatForm extends AbstractForm {
    private Client client;

    private JPanel panel;
    private Action action;
    private JButton btnSend;
    private JTextPane textPane;
    private JList<String> list;
    private JTextField txtMessage;
    private DefaultListModel<String> listModel;

    public static final int FRAME_WIDTH = 640;
    public static final int FRAME_HEIGHT = 480;

    public static final Color colorInfo = Color.BLUE;
    public static final Color colorError = Color.RED;
    public static final Color colorWarn = Color.ORANGE;
    public static final Color colorSuccess = new Color(0, 102, 0);

    public ChatForm(String ip, int port) {
        this();
        client = new Client(this);
        if (client.connect(ip, port)) {
            write("Connection to the server established.", ChatForm.colorSuccess);
            write("You have to set your name by typing 'NAME <name>'.", ChatForm.colorInfo);
            client.start();
        } else {
            criticalError("Connection to the server failed.");
        }
    }

    private ChatForm() {
        super.createWindow(panel, FRAME_WIDTH, FRAME_HEIGHT);
        super.setDefaultButton(btnSend);

        action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(txtMessage.getText());
                txtMessage.setText("");
            }
        };

        txtMessage.addActionListener(action);
        btnSend.addActionListener(action);

        textPane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtMessage.requestFocusInWindow();
            }
        });
    }

    private void createUIComponents() {
        listModel = new DefaultListModel<>();
        listModel.addElement("all");
        list = new JList<>(listModel);
        list.setSelectedIndex(0);
    }

    public void addUser(String name) {
        listModel.addElement(name);
    }

    public void removeUser(String name) {
        listModel.removeElement(name);
    }

    public String getRecipient() {
        return list.getSelectedValue();
    }

    public void criticalError(String error) {
        txtMessage.removeActionListener(action);
        btnSend.removeActionListener(action);
        write("Critical error!!! " + error, ChatForm.colorError);
    }

    public void write(String message) {
        write(message, Color.BLACK);
    }

    public void write(String message, Color color) {
        message += "\n";
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(message);
    }

    @Override
    String getInitialTitle() {
        return "Chat";
    }

}
