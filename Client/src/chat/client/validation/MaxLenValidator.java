package chat.client.validation;

import javax.swing.text.JTextComponent;

public class MaxLenValidator extends TextValidator {
    private int maxLen;

    public MaxLenValidator(JTextComponent textField, int maxLen) {
        super(textField, "Maximum number of characters is " + maxLen + ".");
        this.maxLen = maxLen;
    }

    @Override
    public boolean isValid() {
        return textField.getText().length() < maxLen;
    }
}
