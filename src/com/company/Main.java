package com.company;

public class Main {

    public static void main(String[] args) {
    	//Create object for the FoodQuick outlet
    	FoodQuick foodQuick = new FoodQuick();

    	//Create customers who place orders, for which invoices are sent, and after which the drivers' load is updated
    	Customer customer1 = foodQuick.newCustomer();
    	Order order1 = customer1.placeOrder();
    	order1.printInvoice();

		Customer customer2 = foodQuick.newCustomer();
		Order order2 = customer2.placeOrder();
		order2.printInvoice();

		//Print a list of all customers and their order number alphabetically
		foodQuick.printCustomersAlphabetical();

		//Print a list of all customers by location

    }
}
