import java.sql.*;

public class Main {
	
	

    public static void main(String[] args) {
    	
    	   	
    	
    	//Create object for the FoodQuick outlet
    	FoodQuick foodQuick = new FoodQuick();
    	
    	//Create customers who place orders, for which invoices are sent, and after which the drivers' load is updated
    	Customer customer1 = foodQuick.newCustomer();
    	Order order1 = customer1.placeOrder();

		//Print a list of all customers and their order number alphabetically
		foodQuick.printCustomersAlphabetical();

		//Print a list of all customers by location
		foodQuick.printCustomersByLocation();
		
		//Update a customer record
		//Customer.updateCustomerInfo();
		
		//Search a record
		//foodQuick.searchRecord();
		
		//Retrieve all incomplete orders
		//foodQuick.pullIncompleteOrders()
		
		
    }
}
