package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import model.TableAdapterGuess;
import net.miginfocom.swing.MigLayout;
import util.GenericMethods;

/*
 * Giving a short description to user and redirecting to main screen.
 * 
 * @author Zekai Uregen
 */
public class FrontScreen extends JFrame {


	public FrontScreen() {
		this.setTitle("STARTING FIND NUMBER GAME");
		try
		{
		    setUIFont(new javax.swing.plaf.FontUIResource("Time news roman",Font.PLAIN,12));
		}
		catch(Exception e){}
		
		this.setPreferredSize(new Dimension(450,170));
		setSize(new Dimension(450,170));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		setLayout(new MigLayout(""));
		String description = "<html>Computer has kept in mind a number which has 4 different digits.<br/> "
				           + "Keep in mind a number as computer did, too.<br/> Computer will try to find your number.<br/>"
				           + "But, perhaps, you will find its secret number before it.<br/>"
				           + "Whoever succeeds in finding the number first wins. Let start the game..</html>";
		JLabel lblDescription = new JLabel(description);
		add(lblDescription,"center center, gapy 10, span, wrap, growy");
		
		JButton btnStart = new JButton("Start Game");
		add(btnStart,"span,align center");
		
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainScreen mainScreen = new MainScreen();
				mainScreen.pack();

				// make the frame half the height and width
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int height = screenSize.height;
				int width = screenSize.width;
				mainScreen.setSize(width / 2, height / 2);

				// here's the part where i center the jframe on screen
				mainScreen.setLocationRelativeTo(null);

				mainScreen.setVisible(true);
				
				dispose();
				
			}
		});
		
	}
	
	public static void setUIFont(javax.swing.plaf.FontUIResource f)
	{   
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while(keys.hasMoreElements())
	    {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if(value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
	    }
	}
}