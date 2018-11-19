package chat.client.app;

import chat.client.forms.LoginForm;

import javax.swing.*;

public class ClientMain {

    public static void main(String[] args)
            throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new LoginForm().init();

    }

}
