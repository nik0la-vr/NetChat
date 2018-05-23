package windows;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Window extends JFrame {
	protected JPanel contentPane;
	
	public Window() {
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
	}
}
