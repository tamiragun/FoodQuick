//This class is for customers, who have contact info and can instantiate objects of their class using user input.

package com.company;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer {

    //Attributes unique to the customer

    private String name;
    private String location;
    private String contactNumber;
    private String address;
    private String email;

    //Constructor method

    public Customer(String name, String location, String contactNumber,
                    String address, String email) {
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
        this.address = address;
        this.email = email;
    }

    // Method that takes user input and creates a customer object with it

    public static Customer newCustomer() {

        //Create scanner object to take input from the console
        Scanner input = new Scanner(System.in);

        //Save the inputs into variables that will be used to create Customer object
        System.out.println("What is your name?");
        String name = input.nextLine();

        System.out.println("What is your address?");
        String address = input.nextLine();

        System.out.println("What city do you live in?");
        String location = input.nextLine();

        System.out.println("What is your phone number?");
        String contactNumber = input.nextLine();

        System.out.println("What is your email address?");
        String email = input.nextLine();

        //Create a customer object with the given input
        Customer newCustomer = new Customer(name, location, contactNumber,
                address, email);

        //input1.close(); --Code doesn't run past this block if I close the scanner
        return newCustomer;
    }

    /* This method takes user input to place an order. Placing an order involves creating a restaurant object,
    making a list of desired menu items, giving special instructions, and returning an Order object.
     */

   public Order placeOrder() {

        //Create scanner object to take input from the console
        Scanner input = new Scanner(System.in);

        //Get inputs required to create a Restaurant object
        System.out.println("Which restaurant would you like to order from?");
        String restaurantName = input.nextLine();

        System.out.println("What city is the restaurant located in?");
        String restaurantLocation = input.nextLine();

        System.out.println("What is the restaurant's contact number?");
        String restaurantContactNumber = input.nextLine();

        //Create a restaurant object, which will be used to check for drivers
        Restaurant selectedRestaurant = new Restaurant(restaurantName, restaurantLocation,
                restaurantContactNumber);

        //Get inputs required to calculate the total bill and populate the invoice
        int meal = 1;
        List<List> menuItemsList = new ArrayList<List>();

        //While loop to keep asking for meal choices until the user stops it
        while (meal != 0) {

            //The 3 options correspond to what the Restaurant class has on offer
            System.out.println("What meal would you like to order? "
                    + "\n \n Type 1 for Burger \n Type 2 for Pizza \n Type 3 for "
                    + "Fish & chips. \n \nIf you don't want to order anything else, "
                    + "type 0.");

            meal = input.nextInt();
            /*Calling next line after having called nextInt (else it won't
             * take more input)
             */
            input.nextLine();


            if (meal == 0) {
                break;
            }

            //Ask quantity, which will be used to calculate total bill amount
            System.out.println("How many of these would you like to order?");
            int quantity = input.nextInt();
            /*Calling next line after having called nextInt (else it won't
             * take more input)
             */
            input.nextLine();


            /*Add a row to the List of ordered menu items in the Customer's
             * attributes. Depending on the menu item selected, we associated
             * the full name and price of the menu item, taken from the
             * restaurant object.
             */

            switch (meal) {
                case 1:
                    List list1=new ArrayList();
                    list1.add(quantity);
                    list1.add(selectedRestaurant.getMenuItem1());
                    list1.add(selectedRestaurant.getPriceItem1());
                    menuItemsList.add(list1);
                    break;
                case 2:
                    List list2=new ArrayList();
                    list2.add(quantity);
                    list2.add(selectedRestaurant.getMenuItem2());
                    list2.add(selectedRestaurant.getPriceItem2());
                    menuItemsList.add(list2);
                    break;
                case 3:
                    List list3=new ArrayList();
                    list3.add(quantity);
                    list3.add(selectedRestaurant.getMenuItem3());
                    list3.add(selectedRestaurant.getPriceItem3());
                    menuItemsList.add(list3);
                    break;
                default:
                    System.out.println("You entered an invalid menu choice. "
                            + "Please place a new order");
            }


        }

        /*Get user input for special instructons and assign the result to the
         * customer's corresponding attribute.
         */
        System.out.println("Do you have any special instructions?");
        String preparationInstructions = input.nextLine();

        /*Print message to end the order and indicate that a text file was
         * generated, so the user knows where to look.
         */
        System.out.println("\nThank you for your order. Please open invoice.txt "
                + "to see your invoice.");


        //close the scanner
        input.close();

        /*Create and return the Order object.
         */
        return new Order(this, selectedRestaurant, menuItemsList, preparationInstructions);
    }

    //Getters and setters
    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    private void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setEmail(String email) {
        this.email = email;
    }
}
