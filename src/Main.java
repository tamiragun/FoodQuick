
public class Main {

	/**
	 * The main method. Uncomment the operation you want to run.
	 * 
	 * @param args The command line arguments.
	 **/
    public static void main(String[] args) {
    	
    	//Create object for the FoodQuick outlet
    	FoodQuick foodQuick = new FoodQuick();
    	
    	//Create a customer who can place an order next
    	//Customer customer1 = foodQuick.newCustomer();
    	
    	/*Place an order for that customer, which will save to the databse, 
    	 * update the driver's load, and print an invoice to the customer.
    	 * To execute this statement, you will need to uncomment the one
    	 * above (to create a customer object).
    	 */
    	//Order order1 = customer1.placeOrder();

		//Print a list of all customers and their order number alphabetically
		//foodQuick.printCustomersAlphabetical();

		//Print a list of all customers by location
		//foodQuick.printCustomersByLocation();
		
		//Update a customer record
		//Customer.updateCustomerInfo();
		
		//Search a record
    	//foodQuick.searchRecord();
		
		//Retrieve all incomplete orders
		//foodQuick.retrieveIncompleteOrders();
		
    }
}
