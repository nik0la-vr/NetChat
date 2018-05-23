package validation;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

public class Required {
	public static boolean check(JTextComponent text) {
		if (text.getText().trim().isEmpty()) {
			text.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;			
		} else {
			text.setBorder(null);
			return true;
		}
	}
}
