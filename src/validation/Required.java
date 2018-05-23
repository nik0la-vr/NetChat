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
			// 'null' tells the look & feel to use the native border style
			text.setBorder(BorderFactory.createEmptyBorder());
			return true;
		}
	}
}
