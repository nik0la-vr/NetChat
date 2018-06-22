package chat.client.forms;

import chat.client.core.Client;

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

    //region Constants
    public static final int FRAME_WIDTH = 640;
    public static final int FRAME_HEIGHT = 480;
    public static final Color COLOR_INFO = Color.BLUE;
    public static final Color COLOR_ERROR = Color.RED;
    public static final Color COLOR_PLAIN = Color.BLACK;
    public static final Color COLOR_WARN = Color.ORANGE;
    public static final Color COLOR_MINE = new Color(136, 0, 21);
    public static final Color COLOR_SUCCESS = new Color(0, 102, 0);
    //endregion

    private void createUIComponents() {
        listModel = new DefaultListModel<>();
        listModel.addElement("all");
        list = new JList<>(listModel);
        list.setSelectedIndex(0);
    }

    public ChatForm() {
        super.createFrame("Chat", panel, new Dimension(FRAME_WIDTH, FRAME_HEIGHT)).setDefaultButton(btnSend);

        textPane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtMessage.requestFocusInWindow();
            }
        });

        txtMessage.requestFocusInWindow();
    }

    void connect(String ip, int port) {
        client = new Client(ip, port, this);
        client.execute();

        action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(txtMessage.getText());
                txtMessage.setText("");
            }
        };

        btnSend.addActionListener(action);
        txtMessage.addActionListener(action);
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
        btnSend.removeActionListener(action);
        txtMessage.removeActionListener(action);
        error("Critical error: " + error);
    }

    public void success(String message) {
        write(message, ChatForm.COLOR_SUCCESS);
    }

    public void error(String message) {
        write(message, ChatForm.COLOR_ERROR);
    }

    public void warn(String message) {
        write(message, ChatForm.COLOR_WARN);
    }

    public void info(String message) {
        write(message, ChatForm.COLOR_INFO);
    }

    public void plain(String message) {
        write(message, ChatForm.COLOR_PLAIN);
    }

    public void mine(String message) {
        write(message, ChatForm.COLOR_MINE);
    }

    private void write(String message, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(message + "\n");
    }
}
