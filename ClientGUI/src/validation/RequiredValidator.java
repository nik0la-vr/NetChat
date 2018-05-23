package validation;

import javax.swing.text.JTextComponent;

public class RequiredValidator extends TextValidator {
	
	public RequiredValidator(JTextComponent textField) {
		super(textField, "This field is required.");
	}

	@Override
	public boolean isValid() {
		return !textField.getText().isEmpty();
	}
	
}
