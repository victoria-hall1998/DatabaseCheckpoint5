package osu.cse3241;

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
	
	
}
