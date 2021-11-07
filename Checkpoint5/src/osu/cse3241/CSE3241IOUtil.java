package osu.cse3241;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
        boolean properID = (workid.length() == 15) && checkIfNums(workid);
        return properID;
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
        return value.length() < maxlength;
    }

    /**
     * IO for error/exception handling
     *
     * @param e
     *            - exception or error to handle
     */
    public static void printThrowable(Throwable e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    /**
     * Prints a ResultSet object in tabular format
     *
     * @param rs
     *            the ResultSet object to print
     */
    public static void printResultSet(ResultSet rs) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();
            // print column headers
            for (int i = 1; i <= columns; i++) {
                String value = meta.getColumnName(i);
                System.out.print(value);
                if (i < columns) {
                    System.out.print(",\t");
                }
            }
            System.out.println();
            // print data
            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(rs.getString(i) + ",\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            printThrowable(e);
        }
    }
}
