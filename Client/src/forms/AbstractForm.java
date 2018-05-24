package forms;

import javax.swing.*;

abstract class AbstractForm {
    private JFrame frame;

    void setDefaultButton(JButton button) {
        frame.getRootPane().setDefaultButton(button);
    }

    void createWindow(JPanel panel) {
        frame = new JFrame(this.getTitle());
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void createWindow(JPanel panel, int width, int height) {
        frame = new JFrame(this.getTitle());
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public abstract String getTitle();
}
