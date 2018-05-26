package app;

import forms.LoginForm;

import javax.swing.*;

public class ClientMain {

    public static void main(String[] args)
        throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new LoginForm().init();

    }

}
