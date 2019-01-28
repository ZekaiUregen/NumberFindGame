package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class TableAdapterGuess extends AbstractTableModel {
     private static final String[] columnNames = {"Count","Guess","Answer"};
     private ArrayList<Guess> values = new ArrayList<Guess>();

     @Override
	public String getColumnName(int col) { return columnNames[col]; }

     public int getColumnCount() {
         return columnNames.length;
     }

	public Object getValueAt(int row, int col) {
		if (col == 0)
			return row + 1;
		else if (col == 1)
			return values.get(row).getGuessNumber();
		else if (col == 2)
			return values.get(row).getAnswer();
		else
			return "---";

	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return values.size();
	}
	public void add(Object obj){
		values.add((Guess)obj);
	}
	public void add(String guessNumber, String answer){
		values.add(new Guess(guessNumber, answer));
	}
	

	public void truncate() {
		values.clear();
	}

	public ArrayList<Guess> getValues() {
		return values;
	}
	
	
}
