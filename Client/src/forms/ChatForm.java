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
    private JButton btnSend;
    private JTextPane textPane;
    private JTextField txtMessage;

    public static final int FRAME_WIDTH = 640;
    public static final int FRAME_HEIGHT = 480;

    public static final Color colorInfo = Color.BLUE;
    public static final Color colorError = Color.RED;
    public static final Color colorWarn = Color.ORANGE;
    public static final Color colorSuccess = new Color(0, 102, 0);

    public ChatForm(String ip, int port) {
        this();
        client = new Client(this, ip, port);
    }

    private ChatForm() {
        super.createWindow(panel, FRAME_WIDTH, FRAME_HEIGHT);
        super.setDefaultButton(btnSend);

        textPane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtMessage.requestFocusInWindow();
            }
        });

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendRequest(txtMessage.getText());
                txtMessage.setText("");
            }
        };

        txtMessage.addActionListener(action);
        btnSend.addActionListener(action);
    }

    public void write(String message, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(message);
    }

    public String getTitle() {
        return "Chat";
    }

}
