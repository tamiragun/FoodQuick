package com.company;

public class Main {

    public static void main(String[] args) {
	Customer customer = Customer.newCustomer();
	Order order = customer.placeOrder();
	order.printInvoice();

    }
}
