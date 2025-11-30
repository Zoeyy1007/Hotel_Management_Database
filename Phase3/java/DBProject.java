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
            System.out.print("\t");
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
         int newID = 1;
         if (result.size() >0 && result.get(0).get(0) != null){
            newID = Integer.parseInt(result.get(0).get(0)) + 1;
         }

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
         if(!role.equals("Manager")){
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
         System.out.print("\tEnter hotelID: ");
         String input = in.readLine();
         String query = "SELECT COUNT(*) FROM Room R WHERE R.hotelID = " + input + 
                     " AND (R.hotelID, R.roomNo) NOT IN " +
                     "(SELECT B.hotelID, B.roomNo FROM Booking B WHERE B.hotelID = " + input + ")";


         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            System.out.println ("Number of rooms available for hotel " + input + " is "  + result.get(0).get(0));
         }
      }catch(Exception e){
         System.err.println (e.getMessage());
      }



   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      try{
         System.out.print("\tEnter hotelID: ");
         String input = in.readLine();
         String query = "SELECT COUNT(*) FROM Room R WHERE R.hotelID = " + input + 
                     " AND (R.hotelID, R.roomNo) IN " +
                     "(SELECT B.hotelID, B.roomNo FROM Booking B WHERE B.hotelID = " + input + ")";


         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            System.out.println ("Number of rooms booked for hotel " + input + " is "  + result.get(0).get(0));
         }
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms booked for a week(including the input date) 
      // Your code goes here.
      try{
         System.out.print("\tEnter hotelID: ");
         String hotelID = in.readLine();

         System.out.print("\tEnter date (YYYY-MM-DD): ");
         String date = in.readLine();

         String query = "SELECT B.roomNo, B.bookingDate FROM Booking B " +
                        "WHERE B.hotelID = " + hotelID + 
                        " AND B.bookingDate >= '" + date + "' " +
                        " AND B.bookingDate < '" + date + "'::date + interval '7 days'";

         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            System.out.println ("Rooms booked for hotel " + hotelID + " for the week starting " + date + ":");
            for (List<String> row : result) {
               System.out.println("Room No: " + row.get(0) + ", Booking Date: " + row.get(1));
            }
         } else {
            System.out.println("No rooms booked for hotel " + hotelID + " for the week starting " + date);
         }
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      try{
         System.out.print("\tEnter start date (YYYY-MM-DD): ");
         String startDate = in.readLine();

         System.out.print("\tEnter end date (YYYY-MM-DD): ");
         String endDate = in.readLine();

         System.out.print("\tEnter K: ");
         int k = Integer.parseInt(in.readLine());

         String query = "SELECT R.hotelID, R.roomNo, MAX(B.price) AS max_price " +
                        "FROM Room R JOIN Booking B ON R.hotelID = B.hotelID AND R.roomNo = B.roomNo " +
                        "WHERE B.bookingDate >= '" + startDate + "' AND B.bookingDate <= '" + endDate + "' " +
                        "GROUP BY R.hotelID, R.roomNo " +
                        "ORDER BY max_price DESC " +
                        "LIMIT " + k;

         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            System.out.println ("Top " + k + " rooms with highest price from " + startDate + " to " + endDate + ":");
            for (List<String> row : result) {
               System.out.println("Hotel ID: " + row.get(0) + ", Room No: " + row.get(1) + ", Price: " + row.get(2));
            }
         } else {
            System.out.println("No rooms found in the given date range.");
         }
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer Name, List Top K highest booking price for a customer 
      // Your code goes here.
      try{
         System.out.print("\tEnter Customer First Name: ");
         String fName = in.readLine();
         
         System.out.print("\tEnter Customer Last Name: ");
         String lName = in.readLine();
         
         System.out.print("\tEnter K: ");
         String k = in.readLine();

         // Query: Join Booking and Customer, filter by name, order by price DESC
         String query = "SELECT B.hotelID, B.roomNo, B.bookingDate, B.price " +
                        "FROM Booking B, Customer C " +
                        "WHERE B.customer = C.customerID " +
                        "AND C.fName = '" + fName + "' AND C.lName = '" + lName + "' " +
                        "ORDER BY B.price DESC LIMIT " + k;
         
         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         
         if(result.size() > 0) {
             System.out.println("Top " + k + " Highest Price Bookings for " + fName + " " + lName + ":");
             System.out.println("HotelID\tRoomNo\tDate\t\tPrice");
             for (List<String> row : result) {
                 System.out.println(row.get(0) + "\t" + row.get(1) + "\t" + row.get(2) + "\t" + row.get(3));
             }
         } else {
             System.out.println("No bookings found for customer: " + fName + " " + lName);
         }

      }catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      try{
         System.out.print("\tEnter hotelID: ");
         String hotelID = in.readLine();

         System.out.print("\tEnter Customer First Name: ");
         String fName = in.readLine();

         System.out.print("\tEnter Customer Last Name: ");
         String lName = in.readLine();

         System.out.print("\tEnter start date (YYYY-MM-DD): ");
         String startDate = in.readLine();

         System.out.print("\tEnter end date (YYYY-MM-DD): ");
         String endDate = in.readLine();

         String query = "SELECT SUM(B.price) " +
                        "FROM Booking B, Customer C " +
                        "WHERE B.customer = C.customerID " +
                        "AND B.hotelID = " + hotelID + " " +
                        "AND C.fName = '" + fName + "' AND C.lName = '" + lName + "' " +
                        "AND B.bookingDate >= '" + startDate + "' " +
                        "AND B.bookingDate <= '" + endDate + "'";

         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            String totalCost = result.get(0).get(0);
            System.out.println("Total cost incurred by " + fName + " " + lName + 
                               " at hotel " + hotelID + 
                               " from " + startDate + " to " + endDate + 
                               " is: " + (totalCost != null ? totalCost : "0"));
         }
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      try{
         System.out.print("\tEnter Maintenance Company Name: ");
         String companyName = in.readLine();

         String query = "SELECT R.repairType, R.hotelID, R.roomNo " +
                        "FROM Repair R, MaintenanceCompany M " +
                        "WHERE R.mCompany = M.cmpID " +
                        "AND M.name = '" + companyName + "'";

         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            System.out.println("Repairs made by " + companyName + ":");
            System.out.println("RepairType\tHotelID\tRoomNo");
            for (List<String> row : result) {
               System.out.println(row.get(0) + "\t" + row.get(1) + "\t" + row.get(2));
            }
         } else {
            System.out.println("No repairs found for maintenance company: " + companyName);
         }
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	  // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      try{
         System.out.print("\tEnter K: ");
         String k = in.readLine();

         String query = "SELECT M.name, COUNT(R.rID) AS repair_count " +
                        "FROM MaintenanceCompany M LEFT JOIN Repair R ON M.cmpID = R.mCompany " +
                        "GROUP BY M.name " +
                        "ORDER BY repair_count DESC " +
                        "LIMIT " + k;

         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            System.out.println("Top " + k + " Maintenance Companies based on repair count:");
            System.out.println("Company Name\tRepair Count");
            for (List<String> row : result) {
               System.out.println(row.get(0) + "\t" + row.get(1));
            }
         } else {
            System.out.println("No maintenance companies found.");
         }
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
      try{
         System.out.print("\tEnter hotelID: ");
         String hotelID = in.readLine();

         System.out.print("\tEnter room number: ");
         String roomNo = in.readLine();

         String query = "SELECT EXTRACT(YEAR FROM R.repairDate) AS repair_year, COUNT(R.rID) AS repair_count " +
                        "FROM Repair R " +
                        "WHERE R.hotelID = " + hotelID + " AND R.roomNo = " + roomNo + " " +
                        "GROUP BY repair_year " +
                        "ORDER BY repair_year";

         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if(result.size() > 0) {
            System.out.println("Number of repairs for room " + roomNo + " at hotel " + hotelID + " per year:");
            System.out.println("Year\tRepair Count");
            for (List<String> row : result) {
               System.out.println(row.get(0) + "\t" + row.get(1));
            }
         } else {
            System.out.println("No repairs found for room " + roomNo + " at hotel " + hotelID);
         }
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end numberOfRepairsForEachRoomPerYear

}//end DBProject
