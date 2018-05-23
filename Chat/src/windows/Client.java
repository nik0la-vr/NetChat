package windows;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Client extends Window {
	private int port;
	private String ip;
	private String name;
	
	private JTextPane textPane;
	private JTextField txtMessage;
	
	private static final int PADDING = 10;
	private static final int FRAME_WIDTH = 640;
	private static final int FRAME_HEIGHT = 480;
	
	public Client(String name, String ip, int port) {
		this();
		this.ip = ip;
		this.name = name;
		this.port = port;
		console(String.format("Attempting connection to %s:%s", ip, port), Color.BLUE);
	}

	private Client() {
		super();
		// JFrame
		setTitle("Chat Client");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				console(txtMessage.getText());
				txtMessage.setText("");
			}
		};
		
		// contentPane
		contentPane.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 30};
		gbl_contentPane.rowHeights = new int[]{0, 25};
		gbl_contentPane.columnWeights = new double[]{1.0};
		gbl_contentPane.rowWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		// textPane
		textPane = new JTextPane();
		JScrollPane scrollPane = new JScrollPane(textPane);
		textPane.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// Prevent user from typing.
				txtMessage.requestFocusInWindow();
			}
		});

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		gbc_scrollPane.gridwidth = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		// txtMessage
		txtMessage = new JTextField();
		txtMessage.addActionListener(action);
		txtMessage.setColumns(10);
		
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.BOTH;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 1;
		contentPane.add(txtMessage, gbc_txtMessage);
		
		// btnSend
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(action);
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.VERTICAL;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 1;
		contentPane.add(btnSend, gbc_btnSend);
	
		txtMessage.requestFocusInWindow();
	}
	
	private void console(String message) {
		console(message, Color.BLACK);
	}
	
	private void console(String message, Color color) {
		appendToPane(String.format("%s: %s\n", name, message), color);
	}
	
	private void appendToPane(String message, Color color)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(message);
    }
	
}
