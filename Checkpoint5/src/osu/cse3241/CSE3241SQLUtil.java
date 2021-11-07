package osu.cse3241;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CSE3241SQLUtil {

    /**
     * Group of static methods for SQL operations
     */

    private CSE3241SQLUtil() {
    }

    /**
     * Connects to the database if it exists, creates it if it does not, and
     * returns the connection object.
     *
     * @param databaseFileName
     *            the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
        /**
         * The "Connection String" or "Connection URL".
         *
         * "jdbc:sqlite:" is the "subprotocol". (If this were a SQL Server
         * database it would be "jdbc:sqlserver:".)
         */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                // Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out
                        .println("The driver name is " + meta.getDriverName());
                System.out.println(
                        "The connection to the database was successful.");
            } else {
                // Provides some feedback in case the connection failed but did not throw an exception.
                System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            CSE3241IOUtil.printThrowable(e);
            System.out
                    .println("There was a problem connecting to the database.");
        }
        return conn;
    }

    /**
     * Queries the database and prints the results when the user gives an input.
     *
     * @param conn
     *            a connection object
     * @param sql
     *            a SQL statement that returns rows This query is written with
     *            the Statement class, typically used for static SQL SELECT
     *            statements
     */
    public static void sqlQuerySearchAndPrint(Connection conn,
            PreparedStatement ps) {
        ResultSet rs = sqlQuerySearch(conn, ps);
        CSE3241IOUtil.printResultSet(rs);
    }

    /**
     * Queries the database and returns a ResultSet
     *
     * @param Connection
     *            conn a connection object
     * @param ps
     *            a prepared statement object
     * @return ResultSet from the SQL query
     */
    public static ResultSet sqlQuerySearch(Connection conn,
            PreparedStatement ps) {
        ResultSet result = null;
        try {
            result = ps.executeQuery();
        } catch (SQLException e) {
            CSE3241IOUtil.printThrowable(e);
        }
        return result;
    }

}
