package validation;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

public class RequiredValidator {
	private Border border;
	private JTextComponent text;
	
	public RequiredValidator(JTextComponent text) {
		this.text = text;
	}
	
	public boolean check() {
		if (text.getText().trim().isEmpty()) {
			border = text.getBorder();
			text.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;			
		} else {
			text.setBorder(border);
			return true;
		}
	}
}
