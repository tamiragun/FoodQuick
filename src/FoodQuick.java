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

public class FoodQuick {

    //Attribute: list of customer objects, which have order numbers and locations
    private ArrayList<Customer> customersList;

    //Constructor method
    public FoodQuick() {
        this.customersList = new ArrayList<Customer>();
    }

    //Method that takes user input and creates a customer object with it. Adds the customer to the list of customers

    public Customer newCustomer() {

        //Create scanner object to take input from the console
        Scanner input = new Scanner(System.in);

        //Save the inputs into variables that will be used to create Customer object
        System.out.println("What is your first name?");
        String first_name = input.nextLine();
        
        System.out.println("What is your last name?");
        String last_name = input.nextLine();

        System.out.println("What is your street name?");
        String street_name = input.nextLine();
        
        System.out.println("What is your street number?");
        String street_number = input.nextLine();

        System.out.println("What city do you live in?");
        String location = input.nextLine();

        System.out.println("What is your phone number?");
        String contactNumber = input.nextLine();

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
			
			//Create a result set, and run the SQL query that creates a new 
			//row in the customers table
			ResultSet results = statement.executeQuery(
					"SELECT TOP 1 customer_id FROM customers"
					+ " ORDER BY customer_id DESC;");
			
			
			
			
			//Retrieve the driver name, id, and load from the query, and store them in variables
			while (results.next()) {
				latestCustomerId = results.getInt("customer_id");
			}
			
			//Increment the latest customerId by 1 to generate a new customerId
			latestCustomerId+=1;
			
			//Now create a new customer with the generated customerId and the above input
			statement.executeUpdate(
					"INSERT INTO customers VALUES (" + latestCustomerId + ", '" 
							+ first_name + "', '" + last_name + "', '" + email + "', '" + street_name 
							+ "', '" + street_number + "', '" + location + "', '" + contactNumber + "');");
			
			
			// Close up our connections
			results.close();
			statement.close();
			connection.close();
						
		} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
		}
			    	
      //**************************************************************************************************
        //UPDATE THIS WHEN UPDATING THE CUSTOMER ATTRIBUTES AND CONSTRUCTOR
        //**************************************************************************************************
        
        //Create a customer object with the given input
        Customer newCustomer = new Customer(latestCustomerId, (first_name + " " + last_name), 
        		location, contactNumber, (street_number + " " + street_name), email);

        //Add the new customer to the customer list in the FoodQuick company
        customersList.add(newCustomer);
        
        //input1.close(); --Code doesn't run past this block if I close the scanner
        return newCustomer;
    }

    //Method to organise customers alphabetically and print out the result

    public boolean searchRecord() {
    	 //Create scanner object to take input from the console
        Scanner input = new Scanner(System.in);
             
        //Get inputs required to find column that needs updating
        System.out.println("Which field would you like to search by? \nType"
        		+ " \"order_number\", \"first_name\", or \"last_name\".");
        String fieldToSearch = input.nextLine();
        
        if (!(fieldToSearch.equals("first_name") || fieldToSearch.equals("last_name") || 
     		fieldToSearch.equals("order_number"))) {
     	   System.out.println("You entered an incorrect field to update. "
     	   		+ "Please restart the programme and try again.");
     	   return false;
     	   
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
 	 				//Create a result set, and run the SQL query that selects a new 
 	 				//row in the customers table
 	 				results = statement.executeQuery(
 	 						"SELECT * FROM orders WHERE order_number = " + Integer.parseInt(valueToSearch) + ";");
 				} else if (fieldToSearch.equals("first_name")) {
 	 				//Create a result set, and run the SQL query that selects a new 
 	 				//row in the customers table
 	 				results = statement.executeQuery(
 	 						"SELECT * \r\n"
 	 						+ "FROM orders \r\n"
 	 						+ "JOIN customers\r\n"
 	 						+ "ON orders.customer_id = customers.customer_id\r\n"
 	 						+ "WHERE customers.first_name = '" + valueToSearch + "';");
 				} else if (fieldToSearch.equals("last_name")) {
 	 				//Create a result set, and run the SQL query that selects a new 
 	 				//row in the customers table
 	 				results = statement.executeQuery(
 	 						"SELECT * \r\n"
 	 						+ "FROM orders \r\n"
 	 						+ "JOIN customers\r\n"
 	 						+ "ON orders.customer_id = customers.customer_id\r\n"
 	 						+ "WHERE customers.last_name = '" + valueToSearch + "';");
 				}
 				
 				String orderInformation = "";
 				//Retrieve the order info
 				while (results.next()) {
 					orderInformation = "Order number: " + results.getInt("order_number") + "\nCustomer ID: " 
 				+ results.getInt("customer_id") + "\nRestaurant ID: " + results.getInt("restaurant_id") + "\nDriver ID: "
 				+ results.getInt("driver_id") + "\nTotal bill amount: R" + results.getFloat("total_bill") + "\nPreparation instructions: "
 				+ results.getString("instructions") + "\nOrder status: " + results.getString("finalised") + "\nDate order was finalised: "
 				+ results.getDate("date_finalised") + "\n";
 					System.out.println(orderInformation);
 				}
 				
 				
 				//If the query returns no results, warn the user
 				if (orderInformation == "") {
 					System.out.println("We couldn't find an existing record "
 							+ "with this information. Please restart the "
 							+ "programme to search again.");
 					return false;
 				}
 				
 				// Close up our connections
 				results.close();
 				statement.close();
 				connection.close();
 							
 			} catch (SQLException e) {
 					// This is to catch a SQLException - e.g. the id is not in the table, etc.
 					e.printStackTrace();
 					return false;
 			}
 	    
        }
 	       return true;
    }
    	
    public void printCustomersAlphabetical() {

        /*Sort Arraylist of customer objects alphabetically which is possible because the Customer objects implement the
         *Comparable interface and have their own compareTo method.
         */
        Collections.sort(customersList);

        //Create a formatter object to write the alphabetical list to
        Formatter customersAlphabetical = null;

        try {
            customersAlphabetical = new Formatter("customersAlphabetical.txt");

            //Write the customers and their order number to the file
            for (Customer customer : customersList) {
                customersAlphabetical.format("%s %s %s %o %s", "Customer: ",customer.getName(),", Order number: ",
                        customer.getOrder().getOrderNumber(),"\n");
            }

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

    //Method to organise customers by location and print out the result

    public void printCustomersLocation() {

        //Create a list where each element is a customer's location and then their name
        ArrayList<String> namesLocations = new ArrayList<String>();

        for (Customer customer : customersList) {
            namesLocations.add(customer.getLocation()+", "+customer.getName());
        }

        //Then sort that ArrayList alphabetically by location
        Collections.sort(namesLocations);

        //Create a formatter object to write the location based list to
        Formatter customersLocation = null;

        try {
            customersLocation = new Formatter("customersLocation.txt");

            //Write the locations and customers to the file
            for (String customer : namesLocations) {
                customersLocation.format("%s %s", customer, "\n");
            }

        } catch (FileNotFoundException fileNotFoundException) {
            //Display error message and error if this fails
            fileNotFoundException.printStackTrace();
        } finally {
            //Close the file
            if (customersLocation != null) {
                customersLocation.close();
            }
        }
    }
}
