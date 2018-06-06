package chat.client.validation;

import javax.swing.text.JTextComponent;

public class IntegerValidator extends TextValidator {

	public IntegerValidator(JTextComponent textField) {
		super(textField, "Only integer numbers are allowed.");
	}

	@Override
	public boolean isValid() {
		try {
			Integer.parseInt(textField.getText());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
