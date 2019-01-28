package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
/*
 * Redirecting to FrontScreen
 * 
 * @author Zekai Uregen
 */
public class ViewerStart {
	public static void main(String[] args) {

		FrontScreen frontScreen = new FrontScreen();
		frontScreen.pack();

		// make the frame half the height and width
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// here's the part where i center the jframe on screen
		frontScreen.setLocationRelativeTo(null);

		frontScreen.setVisible(true);
	}
}
