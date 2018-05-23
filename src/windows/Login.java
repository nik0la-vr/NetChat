package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JTextField txtName;
	private JTextField txtIp;
	private JTextField txtPort;

	public Login() {
		setTitle("Login");
		setSize(300, 380);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Name
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(64, 43, 165, 14);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(64, 61, 165, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		// IP
		JLabel lblIp = new JLabel("IP Address:");
		lblIp.setBounds(64, 112, 165, 14);
		contentPane.add(lblIp);
		
		txtIp = new JTextField();
		txtIp.setColumns(10);
		txtIp.setBounds(64, 131, 165, 20);
		contentPane.add(txtIp);
		
		JLabel lblIpEg = new JLabel("(eg. 192.168.0.2)");
		lblIpEg.setHorizontalAlignment(SwingConstants.CENTER);
		lblIpEg.setBounds(64, 157, 165, 14);
		contentPane.add(lblIpEg);
		
		// Port
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(64, 193, 165, 14);
		contentPane.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(64, 210, 165, 20);
		contentPane.add(txtPort);
		
		JLabel lblPortEg = new JLabel("(eg. 8192)");
		lblPortEg.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortEg.setBounds(64, 234, 165, 14);
		contentPane.add(lblPortEg);
		
		// Login Button
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String ip = txtIp.getText();
				int port = Integer.parseInt(txtPort.getText());
				login(name, ip, port);
			}
		});
		btnLogin.setBounds(102, 289, 89, 23);
		contentPane.add(btnLogin);
	}
	
	private void login(String name, String ip, int port) {
		dispose();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
