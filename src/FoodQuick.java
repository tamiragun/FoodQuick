import java.io.FileNotFoundException;
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

        //Add the new customer to the customer list in the FoodQuick company
        customersList.add(newCustomer);

        //input1.close(); --Code doesn't run past this block if I close the scanner
        return newCustomer;
    }

    //Method to organise customers alphabetically and print out the result

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
