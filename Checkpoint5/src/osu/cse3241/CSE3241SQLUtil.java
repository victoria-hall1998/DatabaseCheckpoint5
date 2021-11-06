package osu.cse3241;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class CSE3241SQLUtil {

    /**
     * Utility class for SQL operations: sqlQuery(String sql, Connection
     * connection, String[] vals) printResultSet(ResultSet results)
     */

    private CSE3241SQLUtil() {
    }

    /**
     * Runs a sql query using a prepared statement
     *
     * @param sql
     *            - the sql prepared statement string
     * @param conn
     *            - the sql connection
     * @param vals
     *            - values to use for prepared statements
     * @return - result set containing the results of a query
     */
    public static ResultSet sqlQuery(String sql, Connection conn,
            String[] vals) {
        PreparedStatement query;
        ResultSet queryResults = null;
        try {
            query = conn.prepareStatement(sql);
            for (int i = 0; i < vals.length; i++) {
                query.setString(i + 1, vals[i]);
            }
            queryResults = query.executeQuery();
            return queryResults;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryResults;
    }

    /**
     * Prints a ResultSet from a sql query in tabular form
     *
     * @param result
     *            the result set to print
     */
    public static void printResultSet(ResultSet result) {

        try {
            ResultSetMetaData meta = result.getMetaData();
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
            while (result.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(result.getString(i) + ",\t");
                }

                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
