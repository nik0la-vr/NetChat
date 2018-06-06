package chat.client.forms;

import chat.client.validation.IntegerValidator;
import chat.client.validation.MaxLenValidator;
import chat.client.validation.RequiredValidator;

import javax.swing.*;

public class LoginForm extends AbstractForm {
    private JPanel panel;
    private JTextField txtIp;
    private JTextField txtPort;
    private JButton btnConnect;

    public LoginForm() {
        super.createFrame("Login", panel, null).setDefaultButton(btnConnect);
    }

    public void init() {
        IntegerValidator txtPortInteger = new IntegerValidator(txtPort);
        MaxLenValidator txtPortMaxLen = new MaxLenValidator(txtPort, 5);
        RequiredValidator txtIpRequired = new RequiredValidator(txtIp);
        RequiredValidator txtPortRequired = new RequiredValidator(txtPort);

        btnConnect.addActionListener(e -> {
            // Validacija ova dva polja je odvojena jer bi se inace odradila validacija samo prvog
            // polja u slucaju da ono nije validno (&& staje prvom prilikom kad naidje na false).
            boolean validIp = txtIpRequired.validate();
            boolean validPort = txtPortRequired.validate() && txtPortInteger.validate() && txtPortMaxLen.validate();

            if (validIp && validPort) {
                super.dispose();
                new ChatForm().connect(txtIp.getText(), Integer.parseInt(txtPort.getText()));
            }
        });
    }
}
