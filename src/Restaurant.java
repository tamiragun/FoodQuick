/*This class describes Restaurants, that get created by the Customer, have
 * attributes that can be accessed from the Customer class, and have a method
 * that checks which drivers in the same city as the restaurant has the lowest
 * load, based on an input file drive-info.txt.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Restaurant {

    //Attributes
    private String name;
    private String location;
    private String contactNumber;
    private String menuItem1 = "Hamburger";
    private double priceItem1 = 90.00;
    private String menuItem2 = "Pizza";
    private double priceItem2 = 85.50;
    private String menuItem3 = "Fish & chips";
    private double priceItem3 = 89.99;

    //Constructor

    public Restaurant(String name,String location, String contactNumber) {
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
    }


    /**
    * Checks if there are any drivers in the city of the selected restaurant. 
    * If there are, it selects the driver with the lowest load, and updates
    * that driver's load by 1 delivery.  
    *
    * @param  	location  a string denoting the location to search for drivers
    * @return   the id of the driver with the lowest load, or if there are 
    * 			no drivers in the given location, it returns the number 0
    */
    
    public int nearestDriver(String location) {
    	
    	//Declare variable to store the driver that the query will return
    	//Initialize as "0" in case the search comes up empty
		int closestDriver = 0;
		
    	try {
			//Establish a connection to the database
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
				"task18" ,
				"task18b"
				);
			
			// Create a direct line to the database 
			Statement statement = connection.createStatement();
			
			//Create a result set, and run the SQL query that selects the driver 
			//with the lowest load in that city
			ResultSet results = statement.executeQuery(
					"SELECT TOP 1 driver_id, load FROM drivers"
					+ " WHERE city = \'" + location
					+ "\'  ORDER BY load ASC;");
			
			/*Declare variables to store the driver id and load that the query will return
			 * Initialize them to blank values so that they can be accessed outside the 
			 * while loop. Normally, these results should never be used because if there
			 * is no match, the if statement below will return false, and the program
			 * will jump straight to returning the string "No drivers".
			 */
			
			int load = 0;
			
			//Retrieve the driver name, id, and load from the query, and store them in variables
			while (results.next()) {
				closestDriver = results.getInt("driver_id");
				load = results.getInt("load");

			}

			//If a driver was found, update that driver's load with one  delivery
			if (closestDriver != 0) {
				load+=1;
				statement.executeUpdate(
						"UPDATE drivers SET load = " + load +
						"WHERE driver_id = " + closestDriver + ";");
			}
			
			// Close up our connections
			results.close();
			statement.close();
			connection.close();
			
			} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
			}
    	
    	
    		//Return the name of the driver, which will be used to print the invoice 
    		//and create the row in the orders table
    		return closestDriver;
    		

    }
    
    //Access methods
    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public String getMenuItem1() {
        return this.menuItem1;
    }

    public String getMenuItem2() {
        return this.menuItem2;
    }

    public String getMenuItem3() {
        return this.menuItem3;
    }

    public double getPriceItem1() {
        return this.priceItem1;
    }

    public double getPriceItem2() {
        return this.priceItem2;
    }

    public double getPriceItem3() {
        return this.priceItem3;
    }

    //Mutator methods
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


}