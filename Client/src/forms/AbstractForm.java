package forms;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractForm {
    private JFrame frame;

    AbstractForm createFrame(String title, JPanel panel, Dimension dimension) {
        frame = new JFrame(title);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (dimension == null) {
            frame.pack();
        } else {
            frame.setSize(dimension.width, dimension.height);
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return this;
    }

    AbstractForm setDefaultButton(JButton button) {
        frame.getRootPane().setDefaultButton(button);
        return this;
    }

    public void dispose() {
        frame.dispose();
    }

    public String getTitle() {
        return frame.getTitle();
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }
}
