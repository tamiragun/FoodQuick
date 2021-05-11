package com.company;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;

public class Order {

    //Attributes unique to the order
    private Customer customer;
    private Restaurant selectedRestaurant;
    private List<List> menuItemsList = new ArrayList<List>();
    private String preparationInstructions;
    private static int totalOrders;
    private int orderNumber;
    private double totalBill;
    private String invoiceText;

    //Constructor method
    public Order(Customer customer, Restaurant selectedRestaurant,
                 List<List> menuItemsList, String preparationInstructions) {
        this.customer = customer;
        this.selectedRestaurant = selectedRestaurant;
        this.menuItemsList = menuItemsList;
        this.preparationInstructions = preparationInstructions;
        this.orderNumber = totalOrders+1;
        totalOrders+=1;
        this.totalBill = calculateTotal(menuItemsList);
        this.invoiceText = createInvoice();
    }

    /*Helper method to calculate the total cost of the bill based on the menu items
     * ordered, the quantities ordered, and the price of the menu items.
     * Returns a double that can be used in the invoice generator method
     * to print out the total cost to invoice.txt
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

    //Helper method to print the List of Lists so that it can be more easily called in the constructor

    private String printMenuItems(List<List> menuItemsList) {

        /*Declare empty string that we are going to append to as we loop through
         * the list.
         */

        String menuItems = "";

        //Loop through the list and concatenate a string for each item
        for (int j =0; j < this.menuItemsList.size(); j++) {
            int quantity = (int) menuItemsList.get(j).get(0);
            String item = (String) menuItemsList.get(j).get(1);
            double price = (double) menuItemsList.get(j).get(2);

            //Then add to the total string
            menuItems += (quantity + " x " + item + " (R" + price + ")\n");
        }

        //After finishing the loop, return the full string with all items
        return menuItems;
    }

    //Helper method to create the invoice text

    private String createInvoice() {

        /*If there is no driver near the restaurant (used by calling the
         * nearestDriver method  on the restaurant object), then the invoice
         * needs to say that no driver was found.
         */
        if (selectedRestaurant.nearestDriver(selectedRestaurant.getLocation()).equals("No drivers")) {
            return "Sorry! Our drivers are too far away from you to be able "
                    + "to deliver to your location.";
        }

        /*If there is a driver found (used by calling the nearestDriver method
         * on the restaurant object), then we print out the full invoice
         */
        else {
            return "Order number " + this.orderNumber +
                    "\nCustomer: " + customer.getName() +
                    "\nEmail: " + customer.getEmail() +
                    "\nPhone number: " + customer.getContactNumber() +
                    "\nLocation: " + customer.getLocation() +
                    "\n \nYou have ordered the following from " + selectedRestaurant.getName()
                    + " in " + selectedRestaurant.getLocation() + ":" +
                    "\n \n" + printMenuItems(menuItemsList) +
                    "\nSpecial instructions: " + preparationInstructions +
                    "\n \nTotal: R" + this.totalBill +
                    "\n \n" + selectedRestaurant.nearestDriver(selectedRestaurant.getLocation()) +
                    " is nearest to the restaurant and so he/she will be delivering "
                    + "your order to you at: \n \n" + customer.getAddress() + "\n" +
                    customer.getLocation() + "\n\nIf you need to contact the restaurant, "
                    + "their number is " + selectedRestaurant.getContactNumber() + ".";
        }

    }

    //Public method to print the invoice to a text file

    public void printInvoice() {

        //Create a formatter object to write the invoice to
        Formatter invoice = null;

        try {
            invoice = new Formatter("invoice.txt");

            //Write the invoice text to the file
            invoice.format("%s", this.invoiceText);
        } catch (FileNotFoundException fileNotFoundException) {
            //Display error message and error if this fails
            fileNotFoundException.printStackTrace();
        } finally {
            //Close the file
            invoice.close();
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

    public static int getTotalOrders() {
        return totalOrders;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public String getInvoiceText() {
        return invoiceText;
    }

    //Mutator methods. To be used for example if a customer wants to make changes after the order is placed.

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSelectedRestaurant(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }

    public void setMenuItemsList(List<List> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }

    public void setPreparationInstructions(String preparationInstructions) {
        this.preparationInstructions = preparationInstructions;
    }

    public static void setTotalOrders(int totalOrders) {
        Order.totalOrders = totalOrders;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public void setInvoiceText(String invoiceText) {
        this.invoiceText = invoiceText;
    }
}
