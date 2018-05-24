package windows;

import validation.IntegerValidator;
import validation.MaxLenValidator;
import validation.RequiredValidator;

import javax.swing.*;

public class Login {
    private JTextField txtIp;
    private JTextField txtPort;
    private JButton btnConnect;
    private JPanel panel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Login() {
        IntegerValidator txtPortInteger = new IntegerValidator(txtPort);
        MaxLenValidator txtPortMaxLen = new MaxLenValidator(txtPort, 5);
        RequiredValidator txtIpRequired = new RequiredValidator(txtIp);
        RequiredValidator txtPortRequired = new RequiredValidator(txtPort);

        btnConnect.addActionListener(e -> {
            boolean validIp = txtIpRequired.validate();
            boolean validPort = txtPortRequired.validate() && txtPortInteger.validate() && txtPortMaxLen.validate();

            if (validIp && validPort) {

            }
        });
    }
}
