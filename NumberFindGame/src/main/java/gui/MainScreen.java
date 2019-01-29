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
 * The computer and the user keep a 4-digit number(different digits) in their mind.
 * Both of them try to find each other secret number.
 * Whoever succeeds in finding the number first wins.
 * 
 * Main gui of the game is this Frame.
 * The purpose is to ensure that the computer is mostly the winner.
 * 
 * Computer creates a possible numbers pool and eliminate numbers from this pool according to each guesses and answers.
 * 
 * User gives answer to computer guesses. Answer format must be "+a-b". When the user entered wrong format, all errors were handled.
 * Application takes user guess too. User guess must a number which has different 4-digit. When user entered wrong number format, all errors were handled.
 * 
 * Always the user starts to guess firstly.
 * So, when the user successes to answer of '+4-0', user waits computer next guess. 
 * If the computer can't find the number in this situation, user wins. Otherwise, the game ends in a draw.
 * When the computer successes to answer of '+4-0', computer wins. 
 *  
 * getPossibleNumberPool() method creates this pool.
 * eliminateNumbersForTheAnswerFromPool(String guessNumber,String answer) method eliminates numbers
 * 
 * Some generic methods are used from util.GenericMethods object.
 * 
 * 
 * @author Zekai Uregen
 */
public class MainScreen extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static String computerSecretNumber;
	public char[] digits = {'0','1','2','3','4','5','6','7','8','9'};

	private JTextField txtUserGuessedNumber = null;
	private String buttonText = null;
	private JButton buttonUserGuess  = null;
	private JLabel lblComputerGuess;
	private JTextField txtUserAnswerForComputerGuess;
	private JButton buttonUserAnalyze;
	private JLabel lblComputerAnalyzeResult;
	private JButton buttonContinue;
	private List<String> possibleNumberPool;
	private JTable tblComputerGuesses;
	private TableAdapterGuess tblComputerGuessesModel;
	private JScrollPane computerGuessesScrollPane;
	private TableAdapterGuess tblUserGuessesModel;
	private JTable tblUserGuesses;
	private JScrollPane userGuessesScrollPane;
	private GenericMethods genericMethods;
	private JPanel processUserPanel;
	private JPanel processComputerPanel;
	private boolean isRestarted;
	
	public MainScreen() {
		this.setTitle("FIND NUMBER GAME");
		try
		{
		    setUIFont(new javax.swing.plaf.FontUIResource("Time news roman",Font.PLAIN,12));
		}
		catch(Exception e){}
		
		this.setPreferredSize(new Dimension(800,500));
		setSize(new Dimension(800,500));

		genericMethods = new GenericMethods();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		setLayout(new MigLayout(""));

		processUserPanel = new JPanel();
		processUserPanel.setBorder(BorderFactory.createTitledBorder("User Guess Panel"));
		processUserPanel.setLayout(new MigLayout(""));
        processUserPanel.add(new JLabel("Guess a 4-digit Number:"),"align center,split 2,span");
		txtUserGuessedNumber = new JTextField();
		processUserPanel.add(txtUserGuessedNumber,"width 45:80:100,align left,wrap");
		buttonUserGuess = new JButton("Enter");
		processUserPanel.add(buttonUserGuess,"height 15:20:24,align right,split 3,span");
		processUserPanel.add(new JLabel("Result:"),"align right");
		lblComputerAnalyzeResult = new JLabel("____");
		processUserPanel.add(lblComputerAnalyzeResult, "align right,wrap");
		buttonContinue = new JButton("Continue to play..");
		buttonContinue.setVisible(false);
		buttonContinue.setPreferredSize(new Dimension(80,24));
		processUserPanel.add(buttonContinue,"align right,width 30:200:250,span");
		processUserPanel.setVisible(true);
		
		buttonUserGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonUserGuessAction();
			}
		});
        processComputerPanel = new JPanel();
        processComputerPanel.setBorder(BorderFactory.createTitledBorder("Computer Guess"));
        processComputerPanel.setLayout(new MigLayout(""));
        processComputerPanel.add(new JLabel("Computer Number Guess:"),"align center");
        lblComputerGuess = new JLabel(getNewGuess());
        processComputerPanel.add(lblComputerGuess,"width 45:80:100,wrap,align left");
		buttonUserAnalyze = new JButton("Return Result");
		processComputerPanel.add(buttonUserAnalyze,"height 15:18:24,align right");
		txtUserAnswerForComputerGuess = new JTextField();
		processComputerPanel.add(txtUserAnswerForComputerGuess,"width 30:50:70,align right");
		processComputerPanel.setVisible(false);
		
		buttonUserAnalyze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonUserAnalyzeAction();
			}
		});
		
		JPanel processPanel = new JPanel();
		processPanel.setLayout(new BorderLayout());
		processPanel.add(processComputerPanel,BorderLayout.NORTH);
		processPanel.add(processUserPanel,BorderLayout.CENTER);

		getContentPane().add(getUserGuessesScrollPane());
		getContentPane().add(processPanel,"width :250:");
		getContentPane().add(getComputerGuessesScrollPane());
		
		computerSecretNumber = genericMethods.numberGenerator();
		
		buttonContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonContinueAction();
			}
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/3-this.getSize().width/2, dim.height/3-this.getSize().height/2);
	}
	

	// Restart the game.
	protected void clearAll() {
		txtUserGuessedNumber.setText("");
		buttonUserGuess.setVisible(true);
		lblComputerAnalyzeResult.setText("");
		buttonContinue.setVisible(false);
		txtUserAnswerForComputerGuess.setText("");
		
		tblComputerGuessesModel.getValues().clear();
		tblComputerGuessesModel.fireTableDataChanged();
		tblUserGuessesModel.getValues().clear();
		tblUserGuessesModel.fireTableDataChanged();
		
		processComputerPanel.setVisible(false);
		processUserPanel.setVisible(true);
		
		isRestarted = true;
		
	}
	// Check the user guess number whether has different 4-digit. When user entered wrong number format, all errors were handled.
	public boolean isValid4DigitNumber(String number) {
		
		if(number.length() != 4) {
			genericMethods.alert(getContentPane(),"Please guess a 4-digit number!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!genericMethods.isNumeric(number)) {
			genericMethods.alert(getContentPane(),"Please guess a numeric value!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(number.charAt(0) == number.charAt(1) || number.charAt(0) == number.charAt(2) 
					|| number.charAt(0) == number.charAt(3) || number.charAt(1) == number.charAt(2) 
						|| number.charAt(1) == number.charAt(3) || number.charAt(2) == number.charAt(3)) {
			genericMethods.alert(getContentPane(),"Digits must be different from each other. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	// Check the format of the answer of the computer guess coming from user.
	protected String checkFormatTheAnswer(String answer) {
		
		if(answer.length() == 4 && answer.startsWith("+") && answer.charAt(2) == '-' 
				&& Character.isDigit(answer.charAt(1)) && Character.isDigit(answer.charAt(3))){
			
		    int totalCount = Character.getNumericValue(answer.charAt(1)) + Character.getNumericValue(answer.charAt(3));
			if(totalCount > 4 || totalCount < 0) { 
				genericMethods.alert(getContentPane(),"The Sum of '+' and '-' values must not be bigger than 4. Please, try again. ", "FORMAT ERROR", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}else {
			genericMethods.alert(getContentPane(),"Format error in answer. Example answers: '+2-1','+0-2','+0-0', etc.Please, try again. ", "FORMAT ERROR",  JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return answer;
	}
	//Computer creates a possible numbers pool and eliminate numbers from this pool according to each guesses and answers.
	private List<String> getPossibleNumberPool()
	{
		if(possibleNumberPool == null) {
			possibleNumberPool = new ArrayList<String>();
			for (int n1 = 0; n1 < digits.length; n1++)
				for (int n2 = 0; n2 < digits.length; n2++)
					for (int n3 = 0; n3 < digits.length; n3++)
						for (int n4 = 0; n4 < digits.length; n4++) {
							if (n1 != n2 && n1 != n3 && n1 != n4 && n2 != n3 && n2 != n4 && n3 != n4) {
								if(digits[n1] != '0') // First digit must not be 0
									possibleNumberPool.add(String.valueOf(digits[n1]) + String.valueOf(digits[n2])
										+ String.valueOf(digits[n3]) + String.valueOf(digits[n4]));
							}
						}
		}
		return possibleNumberPool;
	}
	//Eliminate numbers from possibleNumberPool according to guess and answer.
    private void eliminateNumbersForTheAnswerFromPool(String guessNumber,String answer)
    {
        for (int i = 0; i < getPossibleNumberPool().size(); i++)
        {
            String possibleNumber = getPossibleNumberPool().get(i);
            if (!genericMethods.compareNumbers(possibleNumber, guessNumber).equals(answer))
            	getPossibleNumberPool().remove(i--);
        }
        tblComputerGuessesModel.add(guessNumber, answer);
        tblComputerGuessesModel.fireTableDataChanged();
     }
    
    //Takes a number from possibleNumberPool randomly. 
    public String getNewGuess() {
    	//If the pool is empty, this means that user gave a wrong answer to a computer guess. 
		if(possibleNumberPool != null && possibleNumberPool.size() == 0) {
			if(JOptionPane.showConfirmDialog(getContentPane(),"There is an error in your answers to my guess.Please check each of them!. Computer Secret Number:" + computerSecretNumber + " Do you want play again?.","ERROR!",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){	
				possibleNumberPool = null;
				clearAll();
			}
			else
				System.exit(0);
		}
		return getPossibleNumberPool().get(new Random().nextInt(getPossibleNumberPool().size()));
	}
	
    /*
     * Button Actions
     */
	protected void buttonUserGuessAction() {
		if(isValid4DigitNumber(txtUserGuessedNumber.getText())) {
			lblComputerAnalyzeResult.setText(genericMethods.compareNumbers(computerSecretNumber,txtUserGuessedNumber.getText()));
			tblUserGuessesModel.add(txtUserGuessedNumber.getText(), lblComputerAnalyzeResult.getText());
	        tblUserGuessesModel.fireTableDataChanged();
	        buttonUserGuess.setVisible(false);
	        buttonUserAnalyze.setVisible(true);
			buttonContinue.setVisible(true);
		}
	}

	protected void buttonContinueAction() {
		buttonContinue.setVisible(false);
		if(processUserPanel.isVisible()) {
			lblComputerGuess.setText(getNewGuess());
			if(!isRestarted) {
				txtUserAnswerForComputerGuess.setText("");
				processComputerPanel.setVisible(true);
				processUserPanel.setVisible(false);
			}
			isRestarted = false;
		}
		
	}
	
	protected void buttonUserAnalyzeAction() {

		String answerOfComputerLastGuess = checkFormatTheAnswer(txtUserAnswerForComputerGuess.getText());
		if(answerOfComputerLastGuess != null) {
			String message = null;
			if(tblUserGuessesModel.getRowCount() != 0) {
				String answerOfUserLastGuess = (String) tblUserGuessesModel.getValueAt(tblUserGuessesModel.getRowCount()-1,2);
				message = controlForIfThereIsWinner(answerOfUserLastGuess,answerOfComputerLastGuess);
			}
			if(message != null)
			{
				if(JOptionPane.showConfirmDialog(getContentPane(),message,"GAME OVER!",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					clearAll();
				else
					System.exit(0);
			}else
				eliminateNumbersForTheAnswerFromPool(lblComputerGuess.getText(), answerOfComputerLastGuess);
			
			txtUserGuessedNumber.setText("");
			processComputerPanel.setVisible(false);
			processUserPanel.setVisible(true);
			buttonUserGuess.setVisible(true);
			lblComputerAnalyzeResult.setText("____");
		}
	}
    

	/* Always the user start to guess firstly.
	 * So, when the user success to answer of '+4-0', user waits computer next guess. 
	 * If the computer can't find the number in this situation, user wins. Otherwise, the game ends in a draw.
	 * When the computer success to answer of '+4-0', computer wins. 
	 */
	private String controlForIfThereIsWinner(String answerOfUserLastGuess, String answerOfComputerLastGuess) {
		String message = null;
		if("+4-0".equals(answerOfUserLastGuess) && "+4-0".equals(answerOfComputerLastGuess))
			message = "The game ended in a draw. Do you want play again?";
		else if(!"+4-0".equals(answerOfUserLastGuess) && "+4-0".equals(answerOfComputerLastGuess))
			message = "COMPUTER WIN THE GAME :):). Computer secret number:" + computerSecretNumber + ". Do you want play again?.";
		else if("+4-0".equals(answerOfUserLastGuess) && !"+4-0".equals(answerOfComputerLastGuess))
			message = "CONGRATULATIONS! YOU WIN THE GAME :):).Do you want play again?.";
		System.out.println(answerOfUserLastGuess + " "  + answerOfComputerLastGuess + " " + computerSecretNumber);
		return message;
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
	public String getButtonText() {
		return buttonText;
	}


	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
		if(buttonUserGuess != null)
			buttonUserGuess.setText(buttonText);
	}

	
	private JScrollPane getUserGuessesScrollPane() {
		if (userGuessesScrollPane == null) {
			userGuessesScrollPane = new JScrollPane();
			userGuessesScrollPane.setBorder(BorderFactory.createTitledBorder("USER GUESSES"));
			userGuessesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			userGuessesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			userGuessesScrollPane.setViewportView(getUserGuessesTable());
			
		}
		return userGuessesScrollPane;
	}
	
	private JTable getUserGuessesTable() {
		if (tblUserGuesses == null) {
			tblUserGuesses = new JTable();
			tblUserGuesses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tblUserGuessesModel = new TableAdapterGuess();
			tblUserGuesses.setModel(tblUserGuessesModel);
			tblUserGuesses.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			
		}
		return tblUserGuesses;
	}
	
	private JScrollPane getComputerGuessesScrollPane() {
		if (computerGuessesScrollPane == null) {
			computerGuessesScrollPane = new JScrollPane();
			computerGuessesScrollPane.setBorder(BorderFactory.createTitledBorder("COMPUTER GUESSES"));
			computerGuessesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			computerGuessesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			computerGuessesScrollPane.setViewportView(getComputerGuessesTable());
			
		}
		return computerGuessesScrollPane;
	}
	
	private JTable getComputerGuessesTable() {
		if (tblComputerGuesses == null) {
			tblComputerGuesses = new JTable();
			tblComputerGuesses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tblComputerGuessesModel = new TableAdapterGuess();
			tblComputerGuesses.setModel(tblComputerGuessesModel);
			tblComputerGuesses.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			
		}
		return tblComputerGuesses;
	}

}