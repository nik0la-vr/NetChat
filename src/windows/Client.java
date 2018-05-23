package windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;

	private int port;
	private String ip;
	private String name;
	
	private JPanel contentPane;
	private JTextField txtMessage;
	
	private static final int PADDING = 10;
	private static final int FRAME_WIDTH = 640;
	private static final int FRAME_HEIGHT = 480;

	private Client() {
		setVisible(true);
		setTitle("Chat Client");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
			
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 30};
		gbl_contentPane.rowHeights = new int[]{0, 25};
		gbl_contentPane.columnWeights = new double[]{1.0};
		gbl_contentPane.rowWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		JTextArea txtHistory = new JTextArea();
		GridBagConstraints gbc_txtHistory = new GridBagConstraints();
		gbc_txtHistory.insets = new Insets(0, 0, 5, 0);
		gbc_txtHistory.fill = GridBagConstraints.BOTH;
		gbc_txtHistory.gridx = 0;
		gbc_txtHistory.gridy = 0;
		gbc_txtHistory.gridwidth = 2;
		contentPane.add(txtHistory, gbc_txtHistory);
		
		txtMessage = new JTextField();
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.BOTH;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 1;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.VERTICAL;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 1;
		contentPane.add(btnSend, gbc_btnSend);
	}
	
	public Client(String name, String ip, int port) {
		this();
		this.ip = ip;
		this.name = name;
		this.port = port;
	}

}
