package model;

public class Guess {
	private String guessNumber;
	private String answer;
	
	
	
	public Guess(String guessNumber, String answer) {
		super();
		this.guessNumber = guessNumber;
		this.answer = answer;
	}
	public String getGuessNumber() {
		return guessNumber;
	}
	public void setGuessNumber(String guessNumber) {
		this.guessNumber = guessNumber;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
