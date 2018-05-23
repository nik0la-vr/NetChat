package windows;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;

	private int port;
	private String ip;
	private String name;
	
	private JPanel contentPane;

	private Client() {
		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setVisible(true);
	}
	
	public Client(String name, String ip, int port) {
		this();
		this.ip = ip;
		this.name = name;
		this.port = port;
	}

}
