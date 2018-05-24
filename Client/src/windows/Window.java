package windows;

import javax.swing.JFrame;
import javax.swing.JPanel;

abstract class Window {
	JFrame frame;
	JPanel contentPane;
	
	public Window() {
		frame = new JFrame();
		contentPane = new JPanel();
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
