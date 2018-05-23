package filters;

import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntFilter extends DocumentFilter {

	private JTextField textField;

	public IntFilter() {
	}
	
	public IntFilter(JTextField txtField) {
		this.textField = txtField;
		this.textField.setToolTipText("Only integer numbers are allowed.");
	}

	private boolean test(String text) {
		try {
			Integer.parseInt(text.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void warn() {
		if (textField != null) {			
			ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(textField, 0, 0, 0, 0, 0, 0, false));
		}
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (test(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			warn();
		}

	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			warn();
		}

	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (sb.toString().length() == 0 || test(sb.toString())) {
			super.remove(fb, offset, length);
		} else {
			warn();
		}

	}
}
