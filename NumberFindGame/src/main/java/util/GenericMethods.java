package util;

import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class GenericMethods {
	

	
	public String compareNumbers(String number1, String number2) {
		int resultPlus = 0, resultMinus = 0;
		 
        for (int i = 0; i < number2.length(); i++)
        {
            int ind = number1.indexOf(number2.charAt(i));
            if (ind >= 0)
            {
                if (ind == i) resultPlus++;
                else resultMinus++;
            }
        }
        return "+" + resultPlus + "-" + resultMinus;
	}
	
	
	
	public String numberGenerator() {
	    Random randy = new Random();
	    StringBuffer number = new StringBuffer("XXXX");
	    
	    for (int i = 0; i < number.length(); i++) {
	    	int temp = randy.nextInt(9);
	    	
	    	while((temp == 0 && i == 1) // first digit must not be 0  
	    			||(Character.getNumericValue(number.charAt(0)) == temp && i != 0)
	    			|| (Character.getNumericValue(number.charAt(1)) == temp && i != 1)
	    			|| (Character.getNumericValue(number.charAt(2)) == temp && i != 2)
	    			|| (Character.getNumericValue(number.charAt(3)) == temp && i != 3))
					temp = randy.nextInt(9);
			
	    	number.setCharAt(i, (char)(temp+'0'));
		}
	    return number.toString();
	}
	
    public void alert(Component component,String message,String title,int messageType) {
    	JOptionPane.showMessageDialog(component,message,title,messageType);
    }
    
    public boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
            if(d/1000 < 1) // 4 digit control
            	return false;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
	
	public String getStringRepresentation(List<Character> list)
	{    
	    StringBuilder builder = new StringBuilder(list.size());
	    for(Character ch: list)
	    {
	        builder.append(ch);
	    }
	    return builder.toString();
	}
}
