/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science  &  Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL String
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query String
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Executes a query and returns the result as a list of rows.
    */
   public List<List<String>> executeQueryAndReturnResult(String query) throws SQLException {
      Statement stmt = this._connection.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();
      int numCol = rsmd.getColumnCount();

      List<List<String>> result = new ArrayList<List<String>>();
      while (rs.next()) {
         List<String> row = new ArrayList<String>();
         for (int i = 1; i <= numCol; ++i) {
            row.add(rs.getString(i));
         }
         result.add(row);
      }
      stmt.close();
      return result;
   }//end executeQueryAndReturnResult

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
	  // Given customer details add the customer in the DB 
      // Your code goes here.
      // ...
      // ...
      try {
         System.out.print("Enter customer id: ");
         String id = in.readLine();

         System.out.print("Enter first name: ");
         String fName = in.readLine();

         System.out.print("Enter last name: ");
         String lName = in.readLine();

         System.out.print("Enter address: ");
         String address = in.readLine();

         System.out.print("Enter phone number: ");
         String phno = in.readLine();

         System.out.print("Enter date of birth (YYYY-MM-DD): ");
         String DOB = in.readLine();

         System.out.print("Enter gender (M/F): ");
         String gender = in.readLine();

         String query = "INSERT INTO Customer(customerID, fName, lName, address, phno, DOB, gender)"+
                        "VALUES (" + id + ", "+fName +", " + lName + ", " + address + ", " + phno + ", " + DOB + ", " + gender + ")";
         
         esql.executeUpdate(query);
         System.out.println("Added customer");
      } catch (Exception e){
         System.err.println(e.getMessage());
      }
   }//end addCustomer

   public static void addRoom(DBProject esql){
	  // Given room details add the room in the DB
      // Your code goes here.
      // ...
      // ...

      try{
         System.out.print("Enter hotel id: ");
         String hotelID = in.readLine();

         System.out.print("Enter room number: ");
         String roomNo = in.readLine();

         System.out.print("Enter room type: ");
         String type = in.readLine();

         String query = "INSERT INTO Room(hotelID, roomNo, roomType)"+
                        "VALUES (" + hotelID + ", " + roomNo + ", " + type + ")";
         esql.executeUpdate(query);
         System.out.println("Added room");
      } catch (Exception e){
         System.err.println (e.getMessage());
      }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
      // ...
      // ...
      try{
         System.out.print("Enter company id: ");
         String companyID = in.readLine();

         System.out.print("Enter company name: ");
         String comName = in.readLine();

         System.out.print("Enter company address: ");
         String address = in.readLine();

         System.out.print("Is the company certified? (y/n): ");
         String certInput = in.readLine().trim().toLowerCase();
         String isCertified = certInput.equals("y")? "true" : "false";

         String query = "INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified)" + 
                        "VALUES (" + companyID + ", " + comName + ", " + address + ", " + isCertified + ")";

         esql.executeUpdate(query);
         System.out.println("Added maintenance company");
      } catch (Exception e){
         System.err.println(e.getMessage());
      }
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
	  // Given repair details add repair in the DB
      // Your code goes here.
      // ...
      // ...
      try{
         System.out.print("Enter repair ID: ");
         String rID = in.readLine();

         System.out.print("Enter hotel ID: ");
         String hotelID = in.readLine();

         System.out.print("Enter room number: ");
         String roomNo = in.readLine();

         System.out.print("Enter maintenance company ID: ");
         String mCompany = in.readLine();

         System.out.print("Enter repair date (YYYY-MM-DD): ");
         String date = in.readLine();

         System.out.print("Enter repair description: ");
         String description = in.readLine();

         System.out.print("Enter repair type: ");
         String type = in.readLine();

         String query = "INSERT INTO Repair(rID, hotelID, roomNo, mCompany, repairDate, description, repairType)" + 
                        "VALUES (" + rID + ", " + hotelID + ", " + roomNo + ", "+mCompany + ", " + date + ", " + description + ", "+type + ")";

         esql.executeUpdate(query);
         System.out.println("Added repair");
      } catch (Exception e){
         System.err.println(e.getMessage());
      }
   }//end addRepair

   public static void bookRoom(DBProject esql){
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
      // Your code goes here.
      // ...
      // ...
      try{
         System.out.print("Enter hotelID: ");
         String hotelID = in.readLine();

         System.out.print("Enter room number: ");
         String roomNo = in.readLine();

         System.out.print("Enter customer first name: ");
         String fName = in.readLine();

         System.out.print("Enter customer last name: ");
         String lName = in.readLine();

         String customerQuery = "SELECT customerID FROM Customer WHERE fName = '" + fName+"' AND lName = '"+lName +"'";
         int customerID = Integer.parseInt(esql.executeQueryAndReturnResult(customerQuery).get(0).get(0));

         System.out.print("Enter booking date(YYYY-MM-DD): ");
         String bookingDate = in.readLine();

         System.out.print("Enter number of people: ");
         String noOfPeople = in.readLine();

         System.out.print("Enter price: ");
         String price = in.readLine();

         String IDQuery = "SELECT MAX(bID) FROM Booking";
         List<List<String>> result = esql.executeQueryAndReturnResult(IDQuery);
         int newID = Integer.parseInt(result.get(0).get(0))+1;

         String query = "INSERT INTO Booking(bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price)" + 
                        "VALUES (" + newID+ ", "+customerID + ", "+hotelID + ", "+roomNo+", "+bookingDate+", "+noOfPeople+", "+price+")";
         esql.executeUpdate(query);
         System.out.println("Added booking");
      } catch (Exception e){
         System.err.println(e.getMessage());
      }
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.
      // ...
      // ...
      try {
         System.out.print("Enter hotelID: ");
         String hotelID = in.readLine();

         System.out.print("Enter staff SSN: ");
         String ssn = in.readLine();

         String staffQuery ="SELECT employerID FROM Staff WHERE SSN =" + ssn;
         String staffID = esql.executeQueryAndReturnResult(staffQuery).get(0).get(0);

         System.out.print("Enter room number: ");
         String roomNo = in.readLine();

         String asgIDQuery = "SELECT MAX(asgID) FROM Assigned";
         List<List<String>> result = esql.executeQueryAndReturnResult(asgIDQuery);
         int asgID = Integer.parseInt(result.get(0).get(0))+1;

         String query = "INSERT INTO Assigned(asgID, staffID, hotelID, roomNo)" + 
                        "VALUES (" + asgID+", "+ staffID+", "+hotelID + ", "+roomNo + ")";
         esql.executeUpdate(query);
         System.out.println("Added house cleaning assignment");
      } catch (Exception e){
         System.err.println(e.getMessage());
      }
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      // Your code goes here.
      // ...
      // ...
      try{
         System.out.print("Enter hotelID: ");
         String hotelID = in.readLine();

         System.out.print("Enter staff SSN: ");
         String ssn = in.readLine();

         String verifyManager = "SELECT StaffRole FROM Staff WHERE SSN=" + ssn;
         String role = esql.executeQueryAndReturnResult(verifyManager).get(0).get(0);
         if(role != "Manager"){
            System.out.println("Only manager can request repair. ");
            return;
         }

         String MIDQuery = "SELECT employerID FROM Staff WHERE SSN=" + ssn;
         String MID = esql.executeQueryAndReturnResult(MIDQuery).get(0).get(0);

         System.out.print("Enter room number: ");
         String roomNo = in.readLine();

         System.out.print("Enter repair ID: ");
         String repairID = in.readLine();

         System.out.print("Enter date (YYYY-MM-DD): ");
         String date = in.readLine();

         String reqIDQuery = "SELECT MAX(reqID) FROM Request";
         List<List<String>> result = esql.executeQueryAndReturnResult(reqIDQuery);
         int reqID = Integer.parseInt(result.get(0).get(0))+1;

         System.out.print("Enter description (optional, press ENTER to skip): ");
         String desc = in.readLine().trim();

         if (desc.isEmpty()){
            desc = "";
         }

         String query = "INSERT INTO Request(reqID, managerID, repairID, requestDate, description)" + 
                        "VALUES (" + reqID + ", "+MID+", "+repairID+", "+date+", "+desc+")";
         esql.executeUpdate(query);
         System.out.println("Added repair request");
      } catch (Exception e){
         System.err.println(e.getMessage());
      }
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms available 
      // Your code goes here.
      // ...
      // ...
      try{
         String query = "SELECT count(*) FROM Room WHERE hotelID = ";
         System.out.print("\tEnter hotelID: $");
         String input = in.readLine();
         query += input;

         int rowCount = esql.executeQuery(query);
         System.out.println ("Number of rooms for hotel " + input + " is "  + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }



   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      // ...
      // ...
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms booked for a week(including the input date) 
      // Your code goes here.
      // ...
      // ...
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      // ...
      // ...
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer Name, List Top K highest booking price for a customer 
      // Your code goes here.
      // ...
      // ...
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      // ...
      // ...
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      // ...
      // ...
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	  // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      // ...
      // ...
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
      // ...
      // ...
   }//end listRepairsMade

}//end DBProject
