package osu.cse3241;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CSE3241IOUtil {
	/**
	 * This class is used as a helper to check the input of the user before 
	 */
	
	private CSE3241IOUtil() {
    	}
	
	/**
	 * Checks if the Work_ID input is 15 numbers
	 */
	public static boolean checkWorkID(String workid) {
		return (workid.length() == 15) && checkIfNums(workid);
	}
	
	/**
	 * Checks if all the values are numbers
	 */
	public static boolean checkIfNums(String workid) {
		boolean allNums = true;
		for (int i = 0; i < workid.length(); i++) {
			if (!Character.isDigit(workid.charAt(i))) {
				allNums = false;
			}
		}
		return allNums;
	}
	
	/**
	 * Checks if the value should be set to null
	 */
	public static boolean checkForNull(String value) {
		return value.isBlank();
	}
	
	/**
	 * Checks if the length is not over max
	 */
	public static boolean checkLength(String value, int maxlength) {
		return value.length() > maxlength;
	}
	
	/**
	 * Checks the formatting of a double. Must be "0.0".
	 */
	public static boolean checkDoubleFormatting(String value) {
		boolean makedoub = true;
		boolean betweenvals = false;
		Double doubval = null;
		try {
			doubval = Double.parseDouble(value);
		}
		catch (NumberFormatException e) {
			makedoub = false;
		}
		
		if (makedoub) {
			betweenvals = 0.0 <= doubval && doubval <= 10.0;
		}
		
		return betweenvals && makedoub;
	}
	
	/**
	 * Check if the date is formatted properly
	 */
	public static boolean checkDateFormat(String date) {
		boolean formattedProperly = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        	dateFormat.setLenient(false);
        	try {
            	dateFormat.parse(date.trim());
        	} catch (ParseException pe) {
        	    formattedProperly = false;
        	}
        	return formattedProperly;
	}
}
