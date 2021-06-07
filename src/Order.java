
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;


/**
* The Order class has the main info about an order, including the
* customer object who placed the order, and the restaurant object
* where the order is placed from.
* <p>
* It includes calculated attributes such as the bill amount, the 
* order number, and the nearest driver.
* <p>
* Upon inititalisation, it creates an order in the database,
* calculates the remaining fields, and then completes the order's
* details in the database.
* <p>
* The class has several private helper methods to accomplish this.
*
* @author      Tamira
* @version     7 June 2021
*/
public class Order {

    //Attributes unique to the order
    private Customer customer;
    private Restaurant selectedRestaurant;
    private List<List> menuItemsList = new ArrayList<List>();
    private String preparationInstructions;
    private int orderNumber;
    private double totalBill;
    private int nearestDriver;

    /**
    * The constructor method takes input, but also makes some
    * calculations using helper methods belonging to the class.
    * <p>
    * Lastly, it performs two methods upon inititalisation:
    * it saves the order to the database and it completes the 
    * order in the database.
    *
    * @param	customer	The customer object that is placing the order
    * @param    selectedRestaurant	The restaurant object the order is placing with
    * @param	menuItemList The list of menu items, quantities, and prices
    * 			as inputted by the user when the order was placed.
    * @param	preparationInstructions	Any special cooking instructions
    * 			provided by the user when placing the order
    */
    public Order(Customer customer, Restaurant selectedRestaurant,
                 List<List> menuItemsList, String preparationInstructions) {
        this.customer = customer;
        this.selectedRestaurant = selectedRestaurant;
        this.menuItemsList = menuItemsList;
        this.preparationInstructions = preparationInstructions;
        this.orderNumber = calculateOrderNumber();
        this.totalBill = calculateTotal(menuItemsList);
        this.nearestDriver = selectedRestaurant.nearestDriver(selectedRestaurant.getCity());
        saveOrderInDatabase();
        finaliseAndPrintInvoice();
    }

    
    /**
    * Calculates the total cost of the bill based on the menu items ordered,
    * the quantities ordered, and the price of the menu items. 
    *
    * @param	menuItemList The list of menu items, quantities, and prices
    * 			as inputted by the user when the order was placed.
    * @returns	a double that is assigned to the object's attribute and also
    * 			saved to the database and printed to the customer's invoice
    */
    private double calculateTotal(List<List> menuItemsList) {

        //Set local variable that will be updated as we loop through every item
        double sum = 0.00;

        /*Loop through the List of Lists, which is one of the attributes of this
         * class, and will have been set by the placeOrder method.
         */
        for (int j =0; j < this.menuItemsList.size(); j++) {

            /*For each row in the List, we get the quantity and the price and
             * multiply those to get the total cost of that menu item. Sum is
             * then updated to add that to it.
             */
            int quantity = (int) menuItemsList.get(j).get(0);
            double price = (double) menuItemsList.get(j).get(2);

            sum += quantity * price;
        }

        /*Once done looping through all lists, the final sum is assigned to the
         * Customer's attribute and returned, so that this can be used in the
         * invoice creation method
         */

        this.totalBill = sum;
        return sum;
    }

    /**
    * Assigns the order a unique order number that does not already exist in
    * the database. 
    * <p>
    * It does this by ordering the database's order numbers and taking the 
    * highest value, then incrementing that value by one. 
    *
    * @returns	an integer that is assigned to the object's attribute and also
    * 			saved to the database and printed to the customer's invoice
    */
    private int calculateOrderNumber() {
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
					"SELECT TOP 1 order_number FROM orders"
					+ " ORDER BY order_number DESC;");
			
			
			int latestOrderId = 0;
			
			//Retrieve the driver name, id, and load from the query, and store them in variables
			while (results.next()) {
				latestOrderId = results.getInt("order_number");
			}
			
			//Increment the latest customerId by 1 to generate a new customerId
			latestOrderId+=1;
			
			// Close up our connections
			results.close();
			statement.close();
			connection.close();
			
			//Return the latest order id
			return latestOrderId;
									
		} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
		}	    	
		
    	//In case the try block fails, return zero, which the database will reject
		return 0;
    }
    
    /**
    * Creates an entry in the orders table in the database for this order.
    * <p>
    * Where there is a driver, it assigns the driver id as well as the other
    * known values, except the finalisation and finalisation_date.
    * <p>
    * Where there is no driver, it assigns null in that column to avoid errors
    * in the database. 
    */
    public void saveOrderInDatabase() {
    	try {
			//Establish a connection to the database
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
				"task18" ,
				"task18b"
				);
			
			// Create a direct line to the database 
			Statement statement = connection.createStatement();

			/*If there is no driver, the order should be created with 'NULL' as a 
			 * value for driver. Otherwise, it should pass the driver_id to the database.
			 */
			if (nearestDriver != 0) {
			//Create the record based on the input given
				statement.executeUpdate(
						"INSERT INTO orders VALUES (" + this.orderNumber + ", " 
						+ this.customer.getCustomerId() + ", " 
						+ this.selectedRestaurant.getRestaurantId() 
						+ ", " + nearestDriver + ", " + this.totalBill + ", '" 
						+ this.preparationInstructions + "', NULL, NULL);");					
			} else {
				statement.executeUpdate(
						"INSERT INTO orders VALUES (" + this.orderNumber + ", " 
						+ this.customer.getCustomerId() + ", " 
						+ this.selectedRestaurant.getRestaurantId() 
						+ ", NULL , " + this.totalBill + ", '" 
						+ this.preparationInstructions + "', NULL, NULL);");		
			}
			
			// Close up our connections
			statement.close();
			connection.close();
						
		} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
		}
    }
    
    /**
    * Prints the 'List of Lists' of menu items to a String, so that it can 
    * be more easily called in the constructor and when printing the invoice.
    *
    * @param	menuItemList The list of menu items, quantities, and prices
    * 			as inputted by the user when the order was placed.
    * @returns	a String containing the ordered menu items, quantities, and
    * 			prices.
    */
    private String printMenuItems(List<List> menuItemsList) {

        /*Declare empty string that we are going to append to as we loop through
         * the list.
         */

        String menuItems = "";
        
        try {
			//Establish a connection to the database
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
				"task18" ,
				"task18b"
				);
			
			// Create a direct line to the database 
			Statement statement = connection.createStatement();
			
			
	        //Loop through the list and concatenate a string for each item
	        for (int j =0; j < this.menuItemsList.size(); j++) {
	            int quantity = (int) menuItemsList.get(j).get(0);
	            //String itemNumberAsString = (String) menuItemsList.get(j).get(1);
	            int itemNumberAsInt = (int) menuItemsList.get(j).get(1);
	            double price = (double) menuItemsList.get(j).get(2);

				//Create the record based on the input given
				statement.executeUpdate(
						"INSERT INTO orders_menu_items VALUES (" + this.orderNumber + ", " + itemNumberAsInt + ", " + quantity + ");");
				
				//Convert the menu choice to text
				String itemDescription = "";
				switch (itemNumberAsInt) {
					case 1:
						itemDescription = selectedRestaurant.getMenuItem1();
						break;
					case 2:
						itemDescription = selectedRestaurant.getMenuItem2();
						break;
					case 3:
						itemDescription = selectedRestaurant.getMenuItem3();
						break;
					default:
						itemDescription = "error";
				}
				
	            //Then add to the total string
	            menuItems += (quantity + " x " + itemDescription + " (R" + price + ")\n");
	        }

			
			// Close up our connections
			statement.close();
			connection.close();
						
		} catch (SQLException e) {
				// This is to catch a SQLException - e.g. the id is not in the table, etc.
				e.printStackTrace();
		}
        
        //After finishing the loop, return the full string with all items
        return menuItems;
    }

    /**
    * Saves the remaining details in the order's entry in the database and
    * prints an invoice to a .txt file for the customer.
    * <p>
    * 
    *
    * @param	menuItemList The list of menu items, quantities, and prices
    * 			as inputted by the user when the order was placed.
    * @returns	a String containing the ordered menu items, quantities, and
    * 			prices.
    */
    private void finaliseAndPrintInvoice() {

        /*If there is no driver near the restaurant (used by calling the
         * nearestDriver method  on the restaurant object), then the invoice
         * needs to say that no driver was found.
         */
    	String invoiceText;
    	
    	if (nearestDriver == 0) {
        	invoiceText = "Sorry! Our drivers are too far away from you to be able "
                    + "to deliver to your location.";
        }

        /*If there is a driver found (used by calling the nearestDriver method
         * on the restaurant object), then we print out the full invoice
         */
        
        else {
        	
        	/* First, find the driver's name in the database based on their id
        	 * Inititalise the driver name to an empty string, so that we can
        	 * use it later outside of the try block
        	 */
        	String driverName = "";
        	
        	try {
     			//Establish a connection to the database
     			Connection connection = DriverManager.getConnection(
     				"jdbc:sqlserver://DESKTOP-JPRBQEE\\SQLEXPRESS;database=food_quick" ,
     				"task18" ,
     				"task18b"
     				);
     			
     			// Create a direct line to the database 
     			Statement statement = connection.createStatement();
     			
     			//Create a result set, and assign it the query searching the driver id
     			ResultSet results = statement.executeQuery("SELECT first_name, last_name "
     					+ "FROM drivers WHERE driver_id = " + nearestDriver + ";");

     			// Loop over the results, assigning them to the name variable.
     			while (results.next()) {
     				driverName = results.getString( "first_name" ) + " " 
     							+ results.getString( "last_name" );
     			}
     			
     			/*Next, update the order in the database to set it as fianlised (i.e.) 
     			 * a driver has been found so the order can go through)
     			 */
     			statement.executeUpdate("UPDATE orders SET finalised = 'finalised' "
     					+ "WHERE order_number = "+ this.orderNumber + "; "
     					+ "UPDATE orders SET date_finalised = GETDATE() "
     					+ "WHERE order_number = "+ this.orderNumber + ";");
     		
     			// Close up our connections
     			results.close();
     			statement.close();
     			connection.close();
     					
     		} catch (SQLException e) {
     			// This is to catch a SQLException - e.g. the id is not in the table, etc.
     			e.printStackTrace();
     		}
        	
        	
        	/*Lastly, create the invoice text and save it to a string that will be printed
        	 * to a .txt file next.
        	 */
        	invoiceText = "Order number " + this.orderNumber +
                    "\nCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                    "\nEmail: " + customer.getEmail() +
                    "\nPhone number: " + customer.getContactNumber() +
                    "\nCity: " + customer.getCity() +
                    "\n \nYou have ordered the following from " + selectedRestaurant.getName()
                    + " in " + selectedRestaurant.getCity() + ":" +
                    "\n \n" + printMenuItems(menuItemsList) +
                    "\nSpecial instructions: " + preparationInstructions +
                    "\n \nTotal: R" + this.totalBill +
                    "\n \n" + driverName +
                    " is nearest to the restaurant and so he/she will be delivering "
                    + "your order to you at: \n \n" + customer.getStreetNumber() + " " 
                    + customer.getStreetName() + "\n" +
                    customer.getCity() + "\n\nIf you need to contact the restaurant, "
                    + "their number is " + selectedRestaurant.getPhone() + ".";
        	
        	
        	//Create a formatter object to write the invoice to
            Formatter invoice = null;

            try {
                invoice = new Formatter("invoice.txt");

                //Write the invoice text to the file
                invoice.format("%s", invoiceText);
            } catch (FileNotFoundException fileNotFoundException) {
                //Display error message and error if this fails
                fileNotFoundException.printStackTrace();
            } finally {
                //Close the file
                if (invoice != null) {
                    invoice.close();
                }
            }
        	
        }

    }

    //Accessor methods
    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getSelectedRestaurant() {
        return selectedRestaurant;
    }

    public List<List> getMenuItemsList() {
        return menuItemsList;
    }

    public String getPreparationInstructions() {
        return preparationInstructions;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public double getTotalBill() {
        return totalBill;
    }

}
