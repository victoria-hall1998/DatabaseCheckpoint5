package osu.cse3241;

import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.sql.Date;  
import java.util.*;

public class CSE3241app {
    
	/**
	 *  The database file name.
	 *  
	 *  Make sure the database file is in the root folder of the project if you only provide the name and extension.
	 *  
	 *  Otherwise, you will need to provide an absolute path from your C: drive or a relative path from the folder this class is in.
	 */
	private static String DATABASE = "C:/OSU Files/Database/SQLiteStudio/Checkpoint5";
	// This line ^^^ will need to be changed. We can put a .db file into the project folder
	
	
	/**
	 *  The query statement to be executed.
	 *  
	 *  Remember to include the semicolon at the end of the statement string.
	 *  (Not all programming languages and/or packages require the semicolon (e.g., Python's SQLite3 library))
	 */
	private static String sqlSearchArtist = "SELECT * FROM Artist WHERE Stage_Name LIKE ?;";
	private static String sqlSearchTrack = "SELECT * FROM Track WHERE Track_Title LIKE ?;";
	private static String sqlInsertIntoArtist = "INSERT INTO Artist(Artist_ID, No_Members, Stage_Name) VALUES (?, ?, ?);";
	private static String sqlInsertIntoAudiobook = "INSERT INTO Audiobook(Work_ID, Lit_Genre, No_Pages, No_Chapters, Title, Rating, Release_Date, Physical_Copies_Available, Physical_Copies_Out, Digital_Copies_Available, Digital_Copies_Out) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	
    /**
     * Connects to the database if it exists, creates it if it does not, and returns the connection object.
     * 
     * @param databaseFileName the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
    	/**
    	 * The "Connection String" or "Connection URL".
    	 * 
    	 * "jdbc:sqlite:" is the "subprotocol".
    	 * (If this were a SQL Server database it would be "jdbc:sqlserver:".)
    	 */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
            	// Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful.");
            } else {
            	// Provides some feedback in case the connection failed but did not throw an exception.
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows
     * This query is written with the Statement class, typically 
     * used for static SQL SELECT statements
     */
    public static void sqlQuery(Connection conn, String sql){
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Queries the database and prints the results when the user gives an input.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows
     * This query is written with the Statement class, typically 
     * used for static SQL SELECT statements
     */
    public static void sqlQuerySearch(Connection conn, PreparedStatement ps){
        try {
        	ResultSet rs = ps.executeQuery();
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
	/**
	 * Main Menu Options Print
	 */
	public static void printMenuOptions() {
		System.out.println("Main Menu");
		System.out.println("Please select from one of the following options by entering the letter next to the option and then pressing the enter key.");
		System.out.println("a. Search");
		System.out.println("b. Add new records");
		System.out.println("c. Order items");
		System.out.println("d. Edit records");
		System.out.println("e. Useful Reports (not implemented yet)");
		System.out.println("Any other letter will Exit the program");
	}
	
	/**
	 * Check if the input is allowing the user to keep picking options from the Main Menu
	 */
	public static boolean checkInputContinue(Connection conn, Scanner input) {
		char option = optionSelected(input);
		switch(option) {
			case 'a':
				searchOption(conn, input);
				break;
			case 'b':
				addNewRecordOption(conn, input);
				break;
			case 'c':
				orderItemsOption(input);
				break;
			case 'd':
				editRecordsOption(input);
				break;
			case 'e':
				usefulReportsOption(input);
				break;
			default:
				System.out.println("Exiting the Program");
				System.out.println("Have a good day");
				break;
		}
		return !((option >= 97) && (option <= 101));
	}
	
	/**
	 * Get the Option of the User
	 */
	public static char optionSelected(Scanner input) {
		System.out.print("Option: ");
		String option = "t";
		if(input.hasNextLine()) {
			option = input.nextLine();
		}

		return option.charAt(0);
	}
	
	/**
	 * User selected a. Search as their option from Main Menu
	 */
	public static void searchOption(Connection conn, Scanner input) {
		/*
		 * User selects if they want an artist or a track?
		 * Then get all the values of selection
		 * search through VALUE attributes for the stagename or tracktitle
		 * display all the info
		 */
		//boolean found = false;
		System.out.println("Select what you would like to search for:");
		System.out.println("a. Artist");
		System.out.println("b. Track");
		char option = optionSelected(input);
		switch(option) {
			case 'a':
				System.out.print("What artist would you like to search for? ");
				String artist = input.nextLine();
				
	        	try {
	        		PreparedStatement artistps;
	        		artistps = conn.prepareStatement(sqlSearchArtist);
		        	artistps.setString(1, artist);
					sqlQuerySearch(conn, artistps);
	        	} catch (SQLException e) {
	        		System.out.println(e.getMessage());
	        		e.printStackTrace();
	        	}
				break;
			case 'b':
				System.out.print("What track would you like to search for? ");
				String track = input.nextLine();
				
				try {
					PreparedStatement trackps;
					trackps = conn.prepareStatement(sqlSearchTrack);
					trackps.setString(1, track);
					sqlQuerySearch(conn, trackps);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			default:
				// IF the user does not select a or b, then give an error
				System.out.println("Invalid option selected. Please try again with a valid selection.");
				break;
		}
	}
	
	/**
	 * User selected b. Add new records as their option from Main Menu
	 */
	public static void addNewRecordOption(Connection conn, Scanner input) {
		// Ask if the user wants to add an artist or a track
		System.out.println("Select what you would like to new record for:");
		System.out.println("a. Artist");
		System.out.println("b. Audiobook");
		char option = optionSelected(input);
		switch(option) {
			case 'a':
				System.out.print("What is the Artist_ID (15 numbers)? ");
		    	String artistidart = input.nextLine();
		    	
		    	System.out.print("How many Memebers are in the band? ");
		    	String membersart = input.nextLine();
		    	
		    	System.out.print("What is their Stage_Name? ");
		    	String nameart = input.nextLine();
		    	
	        	try {
	        		PreparedStatement artistps;
	        		artistps = conn.prepareStatement(sqlInsertIntoArtist);
	        		artistps.setString(1, artistidart);
	        		artistps.setInt(2, Integer.parseInt(membersart));
	        		artistps.setString(3, nameart);
	        		artistps.executeUpdate();
	        	} catch (SQLException e) {
	        		System.out.println(e.getMessage());
	        		e.printStackTrace();
	        	}
				break;
			case 'b':
				System.out.print("What is the Work_ID (15 numbers)? ");
		    	String workidab = input.nextLine();
		    	
		    	System.out.print("What is the Lit_Genre of the Album? ");
		    	String genreab = input.nextLine();
		    	
		    	System.out.print("How many Pages does the Audiobook have? ");
		    	String numpagesab = input.nextLine();
		    	
		    	System.out.print("How many Chapters are in the Audiobook? ");
		    	String numchaptersab = input.nextLine();
		    	
		    	System.out.print("What is the Title of the Audiobook? ");
		    	String titleab = input.nextLine();
		    	
		    	System.out.print("What is the Rating of the Audiobook? ");
		    	String ratingab = input.nextLine();
		    	
		    	System.out.print("When was/is the Audiobook's Release_Date? ");
		    	String releasedateab = input.nextLine();
		    	
		    	System.out.print("How many Physical_Copies are Available? ");
		    	String pcopies_availab = input.nextLine();
		    	
		    	System.out.print("How many Physical_Copies are Checked Out? ");
		    	String pcopies_outab = input.nextLine();
		    	
		    	System.out.print("How many Digital_Copies are Available? ");
		    	String dcopies_availab = input.nextLine();
		    	
		    	System.out.print("How many Digital_Copies are Checked Out? ");
		    	String dcopies_outab = input.nextLine();
		    	
	        	try {
	        		PreparedStatement audiobookps;
	        		audiobookps = conn.prepareStatement(sqlInsertIntoAudiobook);
	        		audiobookps.setString(1, workidab);
	        		audiobookps.setString(2, genreab);
	        		audiobookps.setDouble(3, Integer.parseInt(numpagesab));
	        		audiobookps.setInt(4, Integer.parseInt(numchaptersab));
	        		audiobookps.setString(5, titleab);
	        		audiobookps.setDouble(6, Double.parseDouble(ratingab));
	            	//artistps.setDate(7, Date.valueOf(releasedateab));
	        		audiobookps.setDate(7, null);
	        		audiobookps.setInt(8, Integer.parseInt(pcopies_availab));
	        		audiobookps.setInt(9, Integer.parseInt(pcopies_outab));
	        		audiobookps.setInt(10, Integer.parseInt(dcopies_availab));
	        		audiobookps.setInt(11, Integer.parseInt(dcopies_outab));
	        		audiobookps.executeUpdate();
	        	} catch (SQLException e) {
	        		System.out.println(e.getMessage());
	        		e.printStackTrace();
	        	}
				break;		
			default:
				// IF the user entered something other than 1 or 2, give an error
				System.out.println("Invalid option selected. Please try again with a valid selection.");
				break;
		}
	}
	
	/**
	 * User selected c. Order items as their option from Main Menu
	 */
	public static void orderItemsOption(Scanner input) {
		System.out.println("Select what you would like to do:");
		System.out.println("a. Order a Movie");
		System.out.println("b. Activate item record (Not yet implemented)");
		char option = optionSelected(input);
		switch(option) {
			case 'a':
				
				break;
			case 'b':
				// IF the user selects to activate item record
				System.out.println("You selected to activate item record, but this function is not yet implemented. Please try it again later.");
				break;
			default:
				// IF the user does not select a or b, then give an error
				System.out.println("Invalid option selected. Please try again with a valid selection.");
				break;
		}
	}
	
	/**
	 * User selected d. Edit records as their option from Main Menu
	 */
	public static void editRecordsOption(Scanner input) {
		// boolean for if the artist is found in the database
		//boolean found = false;
		
		// Get the list of all the artists from fauxdatabase
		
	}
	
	/**
	 * User selected e. Useful reports as their option from Main Menu
	 */
	public static void usefulReportsOption(Scanner input) {
		System.out.println("Which option would you like to choose?");
		System.out.println("a. Tracks by ARTIST released before YEAR");
		System.out.println("b. Number of albums checked out by a single patron");
		System.out.println("c. Most popular actor in the database");
		System.out.println("d. Most listened to artist in the database");
		System.out.println("e. Patron who has checked out the most videos");
		System.out.println("Any other entry will exit the program");
		char option = optionSelected(input);
		switch(option) {
			case 'a': 
				System.out.println("You chose to search for tracks by artist released before year");
				break;
			case 'b': 
				System.out.println("You chose to search for number of albums checked out by a single patrong");
				break;
			case 'c': 
				System.out.println("You chose to search for most popular actor in the database");
				break;
			case 'd': 
				System.out.println("You chose to search for most listened to artist in the database");
				break;
			case 'e': 
				System.out.println("You chose to search for the person who has checked out the most videos");
				break;
			default:
				System.out.println("Invalid choice. Process restarting. ");
				break;
		}
	}
    
    /**
     * Allow user to insert a new Artist into the Artist Table in the Database
     * @param conn a connection object
     * @param input a scanner to get the users input for the values of the new Artist
     */
    public static void sqlInsertArtist(Connection conn, Scanner input) {
    	System.out.print("What is the Work_ID (15 numbers)? ");
    	String workid = input.nextLine();
    	
    	System.out.print("What is the Music_Genre of the Album? ");
    	String genre = input.nextLine();
    	
    	System.out.print("How long is the Play_Time (in minutes)? ");
    	String playtime = input.nextLine();
    	
    	System.out.print("How many songs are on the Album? ");
    	String numsongs = input.nextLine();
    	
    	System.out.print("What is the Title of the Album? ");
    	String title = input.nextLine();
    	
    	System.out.print("What is the Rating of the Album? ");
    	String rating = input.nextLine();
    	
    	System.out.print("When was/is the Album's Release_Date? ");
    	String releasedate = input.nextLine();
    	
    	System.out.print("How many Physical_Copies are Available? ");
    	String pcopies_avail = input.nextLine();
    	
    	System.out.print("How many Physical_Copies are Out? ");
    	String pcopies_out = input.nextLine();
    	
    	System.out.print("How many Digital_Copies are Available? ");
    	String dcopies_avail = input.nextLine();
    	
    	System.out.print("How many Digital_Copies are Out? ");
    	String dcopies_out = input.nextLine();
    	
    	try {
    		PreparedStatement sqlInsertStatement = conn.prepareStatement(sqlInsertIntoArtist);
        	sqlInsertStatement.setString(1, workid);
        	sqlInsertStatement.setString(2, genre);
        	sqlInsertStatement.setDouble(3, Double.parseDouble(playtime));
        	sqlInsertStatement.setInt(4,Integer.parseInt(numsongs));
        	sqlInsertStatement.setString(5, title);
        	sqlInsertStatement.setDouble(6, Double.parseDouble(rating));
        	//sqlInsertStatement.setDate(7, Date.valueOf(releasedate));
        	sqlInsertStatement.setDate(7, null);
        	sqlInsertStatement.setInt(8, Integer.parseInt(pcopies_avail));
        	sqlInsertStatement.setInt(9, Integer.parseInt(pcopies_out));
        	sqlInsertStatement.setInt(10, Integer.parseInt(dcopies_avail));
        	sqlInsertStatement.setInt(11, Integer.parseInt(dcopies_out));
    		sqlInsertStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
    	// Set up the database
    	//System.out.println("This is a new run");
    	Connection conn = initializeDB(DATABASE);
    	
    	// While the user still wants to do something, continue showing the main menu
    	boolean exitFlag = false;
    	Scanner input = new Scanner(System.in);
    	while(!exitFlag) {
    		printMenuOptions();
    		exitFlag = checkInputContinue(conn, input);
    		System.out.println();
    	}

    	input.close();
    }
}
