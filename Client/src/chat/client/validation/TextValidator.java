package chat.client.validation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;

public abstract class TextValidator {
    protected JTextComponent textField;
    private Border border;

    public TextValidator(JTextComponent textField, String message) {
        this.textField = textField;
        border = textField.getBorder();

        String tooltip = this.textField.getToolTipText();

        if (tooltip == null) {
            this.textField.setToolTipText("<html>" + message + "</html>");
        } else {
            this.textField.setToolTipText(tooltip.replaceFirst("</html>", "") + "<br>" + message + "</html>");
        }
    }

    public boolean validate() {
        textField.setText(textField.getText().trim());
        if (isValid()) {
            textField.setBorder(border);
            return true;
        } else {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            return false;
        }
    }

    public abstract boolean isValid();
}
