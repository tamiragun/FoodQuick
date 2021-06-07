import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Collections;

/**
* FoodQuick is a food delivery service. 
* <p>
* This class has no attributes, but has several methods that can be 
* performed: adding a new customer, updating a customer's info in
* the database, printing a list of customers by location or in
* alphabetical order, searching for an order, and retrieving all
* incomplete orders.
*
* @author      Tamira
* @version     7 June 2021
*/
public class FoodQuick {

	/**
	* Basic constructor method, no parameters necessary as there are no 
	* attributes to this class
	*/
    public FoodQuick() {
        
    }

    /**
    * Takes user input and creates a new customer record in the 
    * database. Also creates a customer object with the given inputs. 
    * <p>
    * The method has no parameters as all input is obtained from the
    * console.
    * 
    * @return      a customer object based in the given inputs
    */
    public Customer newCustomer() {

        //Create scanner object to take input from the console
        Scanner input = new Scanner(System.in);

        //Save the inputs into variables that will be used to create Customer object
        System.out.println("What is your first name?");
        String firstName = input.nextLine();
        
        System.out.println("What is your last name?");
        String lastName = input.nextLine();

        System.out.println("What is your street name?");
        String streetName = input.nextLine();
        
        System.out.println("What is your street number?");
        String streetNumber = input.nextLine();

        System.out.println("What city do you live in?");
        String city = input.nextLine();

        System.out.println("What is your phone number?");
        String phone = input.nextLine();

        System.out.println("What is your email address?");
        String email = input.nextLine();

        int latestCustomerId = 0;

        try {
			//Establish a connection to the database
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
				"task18" ,
				"task18b"
				);
			
			// Create a direct line to the database 
			Statement statement = connection.createStatement();
			
			/*First, establish a unique customer id by finding the current highest id and 
			 * incrementing that by 1
			 * 
			 */
			ResultSet results = statement.executeQuery(
					"SELECT TOP 1 customer_id FROM customers"
					+ " ORDER BY customer_id DESC;");
			
			//Retrieve the customer id, and update the variable
			while (results.next()) {
				latestCustomerId = results.getInt("customer_id");
			}
			
			//Increment the latest customerId by 1 to generate a new customerId
			latestCustomerId+=1;
			
			//Now create a new customer with the generated customerId and the above input
			statement.executeUpdate(
					"INSERT INTO customers VALUES (" + latestCustomerId + ", '" 
							+ firstName + "', '" + lastName + "', '" + email + "', '" + streetName 
							+ "', '" + streetNumber + "', '" + city + "', '" + phone + "');");
			
			
			// Close up our connections
			results.close();
			statement.close();
			connection.close();
						
		} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
		}
			    	

        //Create a customer object with the given input
        Customer newCustomer = new Customer(latestCustomerId, firstName, lastName, 
        		city, phone, streetName, streetNumber, email);

        return newCustomer;
    }

    /**
    * Retrieves a list of all orders where the order status is NULL
    * (as opposed to "finalized") from the database. 
    * 
    * @return      a String with all the incomplete orders and their details
    */
    public String retrieveIncompleteOrders() {
    	
    	try {
			//Establish a connection to the database
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
				"task18" ,
				"task18b"
				);
				
			// Create a direct line to the database 
			Statement statement = connection.createStatement();
			ResultSet results = null;
				
	 		//Create a result set, and run the SQL query that finds incomplete orders
	 		results = statement.executeQuery(
	 					"SELECT * FROM orders WHERE finalised IS NULL;");

			/* Initialize the string as empty, so that it can be accessed outside
			 * of the try block, and so that we can print an error message if it
			 * remains empty.
			 */
	 		
			String incompleteOrders = "";
			
			
			//Retrieve the order info and print and return the string
			while (results.next()) {
				incompleteOrders = "Order number: " + results.getInt("order_number") + "\nCustomer ID: " 
				+ results.getInt("customer_id") + "\nRestaurant ID: " + results.getInt("restaurant_id") + "\nDriver ID: "
				+ results.getInt("driver_id") + "\nTotal bill amount: R" + results.getFloat("total_bill") + "\nPreparation instructions: "
				+ results.getString("instructions") + "\nOrder status: " + results.getString("finalised") + "\nDate order was finalised: "
				+ results.getDate("date_finalised") + "\n";
				
				System.out.println(incompleteOrders);
				return incompleteOrders;
			}
				
			//If the query returns no results, warn the user
			if (incompleteOrders == "") {
				return "There are currently no incomplete orders.";
			}
				
			// Close up our connections
			results.close();
			statement.close();
			connection.close();
							
		} catch (SQLException e) {
			// This is to catch a SQLException - e.g. the id is not in the table, etc.
			e.printStackTrace();
			return "Unable to execute your query.";
		}
    	
    	//Return statement if all else fails
    	return "There are currently no incomplete orders.";
    }
    
    /**
    * Lets the user search an order by customer name or order number. 
    * There are no parameters as all the input is taken from the console. 
    * The results are printed directly to the console, of returning a string 
    * 
    * @return      a String with all matching orders and their details
    */
    public String searchRecord() {
    	
    	//Create scanner object to take input from the console
        Scanner input = new Scanner(System.in);
             
        
        /* Create string to store the result in, so that it can be accessed
         * outside the try block, and so that an error message can be sent
         * if it remains empty.
         */
        String retrievedRecord = "";
        
        
        //Get inputs required to find column that needs updating
        System.out.println("Which field would you like to search by? \nType"
        		+ " \"order_number\", \"first_name\", or \"last_name\".");
        String fieldToSearch = input.nextLine();
        
        
        //If the user enters an invalid field, print error message and stop
        if (!(fieldToSearch.equals("first_name") || fieldToSearch.equals("last_name") || 
     		fieldToSearch.equals("order_number"))) {
     	   System.out.println("You entered an incorrect field to update. "
     	   		+ "Please restart the programme and try again.");
     	   return "You entered an incorrect field to update. "
        	   		+ "Please restart the programme and try again.";
     	
     	//If a valid entry was given, obtain further input
        } else {
        	System.out.println("Which value do you want to search?");
            String valueToSearch = input.nextLine();
        	
 	       try {
 				//Establish a connection to the database
 				Connection connection = DriverManager.getConnection(
 					"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
 					"task18" ,
 					"task18b"
 					);
 				
 				// Create a direct line to the database 
 				Statement statement = connection.createStatement();
 				ResultSet results = null;
 				
 				if (fieldToSearch.equals("order_number")) {
 	 				//Update the result set with running the SQL query on orders table
 	 				results = statement.executeQuery(
 	 						"SELECT order_number, customer_id, restaurant_id, "
 	 						+ "driver_id, total_bill, instructions, finalised, "
 	 						+ "date_finalised FROM orders WHERE order_number = " 
 	 						+ Integer.parseInt(valueToSearch) + ";");
 	 				
 				} else if (fieldToSearch.equals("first_name")) {

 					//Update the result set with running the SQL query combining both tables
 	 				results = statement.executeQuery(
 	 						"SELECT orders.order_number, orders.customer_id, "
 	 						+ "orders.restaurant_id, orders.driver_id, "
 	 						+ "orders.total_bill, orders.instructions, orders.finalised, "
 	 						+ "orders.date_finalised \r\n"
 	 						+ "FROM orders \r\n"
 	 						+ "JOIN customers\r\n"
 	 						+ "ON orders.customer_id = customers.customer_id\r\n"
 	 						+ "WHERE customers.first_name = '" + valueToSearch + "';");
 	 				
 				} else if (fieldToSearch.equals("last_name")) {

 	 				results = statement.executeQuery(
 	 						"SELECT orders.order_number, orders.customer_id, "
 	 	 	 					+ "orders.restaurant_id, orders.driver_id, "
 	 	 	 					+ "orders.total_bill, orders.instructions, orders.finalised, "
 	 	 	 					+ "orders.date_finalised \r\n"
 	 	 	 					+ "FROM orders \r\n"
 	 	 	 					+ "JOIN customers\r\n"
 	 	 	 					+ "ON orders.customer_id = customers.customer_id\r\n"
 	 	 	 					+ "WHERE customers.last_name = '" + valueToSearch + "';");
 				}
 				
 				//Retrieve the order info, and save it to the string
 				while (results.next()) {
 					
 					retrievedRecord += "Order number: " + results.getInt("order_number")
 					+ "\nCustomer ID: " + results.getInt("customer_id") 
 					+ "\nRestaurant ID: " + results.getInt("restaurant_id") 
 					+ "\nDriver ID: " + results.getInt("driver_id") + "\nTotal bill amount: R" 
 					+ results.getFloat("total_bill") + "\nPreparation instructions: "
 					+ results.getString("instructions") + "\nOrder status: " 
 					+ results.getString("finalised") + "\nDate order was finalised: "
 					+ results.getDate("date_finalised") + "\n\n";
 					
 				}
 				
 				
 				//If the query returns no results, warn the user
 				if (retrievedRecord == "") {
 					System.out.println("We couldn't find an existing record "
 							+ "with this information. Please restart the "
 							+ "programme to search again.");
 					return "We couldn't find an existing record "
 							+ "with this information. Please restart the "
 							+ "programme to search again.";
 				}
 				
 				// Close up our connections
 				results.close();
 				statement.close();
 				connection.close();
 							
 			} catch (SQLException e) {
 					// This is to catch a SQLException - e.g. the id is not in the table, etc.
 					e.printStackTrace();
 					return "There was an error executing your query.";
 			}
 	    
        }
 	      
        //print and return the String of the retrieved result(s)
        System.out.println(retrievedRecord);
        return retrievedRecord;
    }
    
    /**
    * Generates a .txt file with a list of all customers and their order
    * number in alphabetical order.
    * 
    */
    public void printCustomersAlphabetical() {
    	//Create a formatter object to write the alphabetical list to
        Formatter customersAlphabetical = null;
    	try {
			//Establish a connection to the database
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
				"task18" ,
				"task18b"
				);
			
			// Create a direct line to the database 
			Statement statement = connection.createStatement();
			ResultSet results = null;
			
	        
	        customersAlphabetical = new Formatter("customersAlphabetical.txt");
			
 			//Create a result set, and run the SQL query that selects a new 
 			//row in the customers table
 			results = statement.executeQuery(
 						"SELECT customers.first_name, customers.last_name, orders.order_number\r\n"
 						+ "FROM customers\r\n"
 						+ "LEFT JOIN orders\r\n"
 						+ "ON customers.customer_id = orders.customer_id\r\n"
 						+ "ORDER BY first_name ASC;");

			//Retrieve the order info
			while (results.next()) {
								
				customersAlphabetical.format("%s %s %s%s %o %s", "Customer: ",results.getString("first_name"), results.getString("last_name"),", Order number: ",
						results.getInt("order_number"),"\n");
				
			}
					
			// Close up our connections
			results.close();
			statement.close();
			connection.close();
						
		} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
				//return "Unable to execute your query.";
		} catch (FileNotFoundException fileNotFoundException) {
            //Display error message and error if this fails
            fileNotFoundException.printStackTrace();
        } finally {
            //Close the file
            if (customersAlphabetical != null) {
                customersAlphabetical.close();
            }
        }
    }

    /**
    * Generates a .txt file with a list of all customers by city.
    */
    public void printCustomersByLocation() {
    	//Create a formatter object to write the alphabetical list to
        Formatter customersByLocation = null;
    	try {
			//Establish a connection to the database
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
				"task18" ,
				"task18b"
				);
			
			// Create a direct line to the database 
			Statement statement = connection.createStatement();
			ResultSet results = null;
			
	        
			customersByLocation = new Formatter("customersLocation.txt");
			
 			//Create a result set, and run the SQL query that selects a new 
 			//row in the customers table
 			results = statement.executeQuery(
 						"SELECT city, first_name, last_name\r\n"
 						+ "FROM customers\r\n"
 						+ "ORDER BY city;");

			//Retrieve the order info
			while (results.next()) {

				customersByLocation.format("%s%s%s%s %s %s",
						"City: ", results.getString("city"), 
						", Customer: ", results.getString("first_name"), 
							results.getString("last_name"),
						"\n");
				
			}
					
			// Close up our connections
			results.close();
			statement.close();
			connection.close();
						
		} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
				//return "Unable to execute your query.";
		} catch (FileNotFoundException fileNotFoundException) {
            //Display error message and error if this fails
            fileNotFoundException.printStackTrace();
        } finally {
            //Close the file
            if (customersByLocation != null) {
            	customersByLocation.close();
            }
        }
    }

}
