/*This class describes Restaurants, that get created by the Customer, have
 * attributes that can be accessed from the Customer class, and have a method
 * that checks which drivers in the same city as the restaurant has the lowest
 * load, based on an input file drivers.txt.
 */

package com.company;
import java.io.File;
import java.util.Scanner;

public class Restaurant {

    //Attributes
    private String name;
    private String location;
    private String contactNumber;
    private String menuItem1 = "Hamburger";
    private double priceItem1 = 90.00;
    private String menuItem2 = "Pizza";
    private double priceItem2 = 85.50;
    private String menuItem3 = "Fish & chips";
    private double priceItem3 = 89.99;

    //Constructor

    public Restaurant(String name,String location, String contactNumber) {
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    /*Method to read an input file and check if there is a driver in the
     * restaurant's location. If so, it returns the driver with the lowest load.
     * This will be used in the Customer class' placeOrder method, to know what
     * to print out to the invoice.
     */

    public String nearestDriver(String location) {

        /*Create file object to read from the file, and scanner object to search
         * the file.
         */
        File text1 = new File("drivers.txt");
        Scanner scnr1 = null;

        /*Set default value for nearest driver. If no driver is found by the
         * scanner, then this will be returned and the Customer's placeOrder
         * method, and the invoice will say that there are no drivers.
         *
         * Also set default load to 100, so that any other load would be less
         */
        String closestDriver ="No drivers";
        int lowestLoad = 100;


        try {
            //Initialise Scanner instance within try/catch block

            scnr1 = new Scanner(text1);

            //Read each line of the given file using Scanner class

            while(scnr1.hasNextLine()){

                /* Split each line by the comma, so that we instead get a list
                 * with 3 values for each line. This is so that the driver,
                 * location, and load can be accessed individually for each line
                 */
                String line = scnr1.nextLine();
                String[] splittedLine = line.split(", ");

                /*IF the location element equals the location passed into the
                 * method as a parameter, then we check for the load. If not,
                 * we move on to the next line.
                 */
                if (splittedLine[1].equals(location)) {

                    String driverName = splittedLine[0];
                    int driverLoad = Integer.valueOf(splittedLine[2]);

                    /*Now we check if this driver's load is less than the lowest
                     * load recorded this far, as saved in the variable declared
                     * at the start of this method
                     */
                    if (driverLoad < lowestLoad) {

                        /*If the load is lower than the lowest, set it as the
                         * new lowest load and also set the driver as the new
                         * nearest driver.
                         */
                        lowestLoad = driverLoad;
                        closestDriver = driverName;
                    }
                }


            }

        }

        //In case there is an error, print to the console instead of crashing
        catch (Exception e) {

            System.out.println("Error");
            System.out.println(e);
        }

        //Close the scanner
        finally {

            scnr1.close();
        }

        //Return the closest driver, this will be used in the Customer class
        return closestDriver;
    }


    //getters and setters
    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public String getMenuItem1() {
        return this.menuItem1;
    }

    public String getMenuItem2() {
        return this.menuItem2;
    }

    public String getMenuItem3() {
        return this.menuItem3;
    }

    public double getPriceItem1() {
        return this.priceItem1;
    }

    public double getPriceItem2() {
        return this.priceItem2;
    }

    public double getPriceItem3() {
        return this.priceItem3;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


}