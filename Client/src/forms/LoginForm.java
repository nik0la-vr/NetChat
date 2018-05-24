package forms;

import validation.IntegerValidator;
import validation.MaxLenValidator;
import validation.RequiredValidator;

import javax.swing.*;

public class LoginForm extends AbstractForm {
    private JPanel panel;
    private JTextField txtIp;
    private JTextField txtPort;
    private JButton btnConnect;

    public LoginForm() {
        super.createWindow(panel);
        super.setDefaultButton(btnConnect);

        IntegerValidator txtPortInteger = new IntegerValidator(txtPort);
        MaxLenValidator txtPortMaxLen = new MaxLenValidator(txtPort, 5);
        RequiredValidator txtIpRequired = new RequiredValidator(txtIp);
        RequiredValidator txtPortRequired = new RequiredValidator(txtPort);

        btnConnect.addActionListener(e -> {
            boolean validIp = txtIpRequired.validate();
            boolean validPort = txtPortRequired.validate() && txtPortInteger.validate() && txtPortMaxLen.validate();

            if (validIp && validPort) {
                super.dispose();
                new ChatForm(txtIp.getText(), Integer.parseInt(txtPort.getText()));
            }
        });
    }

    public String getTitle() {
        return "Login";
    }

}
