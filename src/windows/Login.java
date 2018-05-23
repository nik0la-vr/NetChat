package windows;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import filters.IntFilter;
import validation.RequiredValidator;

public class Login extends Window {
	private JTextField txtName;
	private JTextField txtIp;
	private JTextField txtPort;
	
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 380;

	public Login() {
		setTitle("Login");
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
			
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		((AbstractDocument)txtPort.getDocument()).setDocumentFilter(new IntFilter(txtPort));
		contentPane.add(txtPort);
		
		JLabel lblPortEg = new JLabel("(eg. 8192)");
		lblPortEg.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortEg.setBounds(64, 234, 165, 14);
		contentPane.add(lblPortEg);
		
		// Validators
		RequiredValidator txtIpRequired = new RequiredValidator(txtIp);
		RequiredValidator txtNameRequired = new RequiredValidator(txtName);
		RequiredValidator txtPortRequired = new RequiredValidator(txtPort);
		
		// Login Button
		JButton btnLogin = new JButton("Login");
		getRootPane().setDefaultButton(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Display red border around all empty text
				// fields (not only around the first one).
				boolean check = txtIpRequired.check();
				check = txtNameRequired.check() && check;
				check = txtPortRequired.check() && check;
				if (check) {
					login(
						txtName.getText(),
						txtIp.getText(),
						Integer.parseInt(txtPort.getText())
					);
				}
			}
		});
		btnLogin.setBounds(102, 289, 89, 23);
		contentPane.add(btnLogin);
	}
	
	private void login(String name, String ip, int port) {
		dispose();
		new Client(name, ip, port);
	}
}
