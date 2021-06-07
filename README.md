# FoodQuick

## Introdcution

As part of my sofware engineering bootcamp, I had to create a programme for the fiction food delivery company "FoodQuick". These were the requirements:

### Version 1:
- It reads a text file with a list of drivers who are in different cities and carry different loads of deliveries. 
- It lets customers place orders, via console input. 
  - The customer enters their own details and the restaurant contact details. 
  - The customer enters their menu choices, quantities, and any special instructions. All restaurants have the same default menu.
- If there is a driver nearby that restaurant, the driver with the lowest load is assigned to pick up the order. 
- An invoice is printed to invoice.txt for the customer. The invoice shows the driver name, menu choices, bill total, and contact info.
- The FoodQuick administrator can print a list of all customers by location or alphabetically.

### Version2:
- The programe connects to a SQL Server database.
- The list of drivers is now stored in the database instead of .txt file. 
- The customer enters their own details, which creates a new entry in the customers table of the database.
- Instead of entering the restaurant info, the customer selects one from the databse. All restaurants still have the same default menu.
- The invoice and list of customers are still printed to .txt files, but they pull the data directly from the database.
- The FoodQuick administrator can retrieve a list of all incomplete orders, search an order by customer name or order number, and update a customer's details in the database.

## Installation
To run this programme, you will need SQL Server, Microsoft JDBC Driver, JDE and JRE 15. Or you can download the Docker image from <https://hub.docker.com/repository/docker/timigun/food_quick>.

## Usage
The main method in the Main class runs the programme. It has several operations commented out, including adding a customer, placing an order, updating a customer, getting the list of customers, getting a list of all incomplete orders, or searching an order. You can simply uncomment the operations you would like to run.

## Credits
Big thanks to my coding mentor Dayle at HyperionDev for helpful advice along the way.
