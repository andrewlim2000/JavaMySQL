package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object that connects to the database.
 * @author Andrew Lim
 *
 */
public class DAO {
    /**
     * Connection to the database.
     */
    private static Connection conn;
    
    /**
     * Creates a data access object that establishes a database connection.
     */
    public DAO() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/lim_andrew_db?serverTimezone=UTC";
            String user = "root";
            String password = "root";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Gets all of the store locations.
     * @return list of all store locations
     */
    public List<String> getStoreLocations() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM STORE_LOCATION;");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("LocationID") + "@" + 
                        result.getString("LocationName") + "@" + 
                        result.getString("StoreAddress") + "@" + 
                        result.getString("StorePhoneNumber"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Gets all the products.
     * @return list of all products
     */
    public List<String> getProducts() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM PRODUCT;");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("ProductID") + "@" + 
                        result.getString("ProductName") + "@" + 
                        result.getString("ProductDescription") + "@" + 
                        result.getString("Price") + "@" +
                        result.getString("CategoryID") + "@" +
                        result.getString("BrandID"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Gets the inventory for each product in each store.
     * @return list of all inventory information
     */
    public List<String> getInventory() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM INVENTORY;");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("LocationID") + "@" + 
                        result.getString("ProductID") + "@" + 
                        result.getString("Availability") + "@" + 
                        result.getString("Stock"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Gets all the employees.
     * @return list of all employees
     */
    public List<String> getEmployees() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM EMPLOYEE AS E " +
                "JOIN STORE_LOCATION AS SL ON E.LocationID = SL.LocationID JOIN DEPARTMENT AS D " +
                "ON E.DepartmentID = D.DepartmentID JOIN JOB AS J ON E.JobID = J.JobID;");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("EmployeeID") + "@" + 
                        result.getString("EmployeeLastName") + "@" + 
                        result.getString("EmployeeFirstName") + "@" + 
                        result.getString("LocationName") + "@" + 
                        result.getString("DepartmentName") + "@" + 
                        result.getString("JobTitle"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Gets all customers.
     * @return list of all customers
     */
    public List<String> getCustomers() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT C.CustomerID, " +
                "CustomerLastName, CustomerFirstName, COUNT(OrderID) AS NumOfOrders " +
                "FROM CUSTOMER AS C LEFT JOIN `ORDER` AS O ON C.CustomerID = O.CustomerID " + 
                "GROUP BY CustomerID;");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("CustomerID") + "@" + 
                        result.getString("CustomerLastName") + "@" + 
                        result.getString("CustomerFirstName") + "@" + 
                        result.getString("NumOfOrders"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Searches for store locations with the given location name.
     * @param locationName the store to search for
     * @return list of store locations matching the given location name
     */
    public List<String> searchStoreLocations(String locationName) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM STORE_LOCATION " +
                    "WHERE LocationName LIKE '" + locationName + "%';");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("LocationID") + "@" + 
                        result.getString("LocationName") + "@" + 
                        result.getString("StoreAddress") + "@" + 
                        result.getString("StorePhoneNumber"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Searches for products with the given product name.
     * @param productName the product to search for
     * @return list of products matching the given product name
     */
    public List<String> searchProducts(String productName) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM PRODUCT " +
                    "WHERE ProductName LIKE '" + productName + "%';");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("ProductID") + "@" + 
                        result.getString("ProductName") + "@" + 
                        result.getString("ProductDescription") + "@" + 
                        result.getString("Price") + "@" +
                        result.getString("CategoryID") + "@" +
                        result.getString("BrandID"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Searches for the inventory of the given location ID and product ID.
     * @param locationID the location ID of the inventory
     * @param productID the product ID of the inventory
     * @return list of inventory information matching the given location ID and product ID
     */
    public List<String> searchInventory(String locationID, String productID) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM INVENTORY " +
                    "WHERE LocationID LIKE '" + locationID + "%' " + 
                    "AND ProductID LIKE '" + productID + "%';");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("LocationID") + "@" + 
                        result.getString("ProductID") + "@" + 
                        result.getString("Availability") + "@" + 
                        result.getString("Stock"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Searches for employees with the given last name and first name.
     * @param employeeLastName  last name of the employee to be searched for
     * @param employeeFirstName first name of the employee to be searched for
     * @return list of employees matching the given last name and first name
     */
    public List<String> searchEmployees(String employeeLastName, String employeeFirstName) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM EMPLOYEE AS E " +
                    "JOIN STORE_LOCATION AS SL ON E.LocationID = SL.LocationID JOIN DEPARTMENT AS D " +
                    "ON E.DepartmentID = D.DepartmentID JOIN JOB AS J ON E.JobID = J.JobID " +
                    "WHERE EmployeeLastName LIKE '" + employeeLastName + "%' " + 
                    "AND EmployeeFirstName LIKE '" + employeeFirstName + "%';");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("EmployeeID") + "@" + 
                        result.getString("EmployeeLastName") + "@" + 
                        result.getString("EmployeeFirstName") + "@" + 
                        result.getString("LocationName") + "@" +
                        result.getString("DepartmentName") + "@" +
                        result.getString("JobTitle"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Searches for customers with the given last name and first name.
     * @param customerLastName  last name of the customer to be searched for
     * @param customerFirstName first name of the customer to be searched for
     * @return list of customers matching the given last name and first name
     */
    public List<String> searchCustomers(String customerLastName, String customerFirstName) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT C.CustomerID, " +
                "CustomerLastName, CustomerFirstName, COUNT(OrderID) AS NumOfOrders " +
                "FROM CUSTOMER AS C LEFT JOIN `ORDER` AS O ON C.CustomerID = O.CustomerID " + 
                "WHERE CustomerLastName LIKE '" + customerLastName +
                "%' AND CustomerFirstName LIKE '" + customerFirstName + "%' " +
                "GROUP BY CustomerID;");
            ResultSet result = statement.executeQuery();
            List<String> array = new ArrayList<>();
            while (result.next()) {
                array.add(result.getString("CustomerID") + "@" + 
                        result.getString("CustomerLastName") + "@" + 
                        result.getString("CustomerFirstName") + "@" + 
                        result.getString("NumOfOrders"));
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Adds the given store location to the database.
     * @param locationID        ID of the store
     * @param locationName      name of the store
     * @param storeAddress      address of the store
     * @param storePhoneNumber  phone number of the store
     */
    public void insertStoreLocation(String locationID, String locationName,
            String storeAddress, String storePhoneNumber) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO STORE_LOCATION " +
                    "VALUES (" + locationID + ", '" + locationName + "', '" + storeAddress +
                    "', '" + storePhoneNumber + "');");
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Adds the given product to the database.
     * @param productID             ID of the product
     * @param productName           name of the product
     * @param productDescription    description of the product
     * @param Price                 price of the product
     * @param categoryID            category ID of the product
     * @param brandID               brand ID of the product
     */
    public void insertProduct(String productID, String productName,
            String productDescription, String Price, String categoryID, String brandID) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO PRODUCT " +
                    "VALUES (" + productID + ", '" + productName + "', '" + productDescription +
                    "', '" + Price + "', " + categoryID + ", " + brandID + ");");
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Updates the inventory with the current availability and stock.
     * @param locationID    the store inventory to be updated
     * @param productID     the product inventory to be updated
     * @param availability  the current availability of the product
     * @param stock         the current stock of the product
     */
    public void updateInventory(String locationID, String productID,
            String availability, String stock) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE INVENTORY SET " +
                    "Availability = " + availability + ", Stock = " + stock + 
                    " WHERE LocationID = " + locationID + " AND ProductID = " + 
                    productID + ";");
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Add an employee to the database.
     * @param employeeID            ID of the employee
     * @param employeeLastName      last name of the employee
     * @param employeeFirstName     first name of the employee
     * @param employeeDOB           date of birth of the employee
     * @param employeeGender        gender of the employee
     * @param employeePhoneNumber   phone number of the employee
     * @param employeeAddress       address of the employee
     * @param hireDate              date of which the employee was hired
     * @param locationID            location at which the employee works at
     * @param departmentID          department the employee is working in
     * @param jobID                 job of the employee
     */
    public void insertEmployee(String employeeID, String employeeLastName,
            String employeeFirstName, String employeeDOB, String employeeGender, 
            String employeePhoneNumber, String employeeAddress, String hireDate, 
            String locationID, String departmentID, String jobID) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO EMPLOYEE " +
                    "VALUES (" + employeeID + ", '" + employeeLastName + "', '" + 
                    employeeFirstName + "', '" + employeeDOB + "', '" + employeeGender + 
                    "', '" + employeePhoneNumber + "', '" + employeeAddress + 
                    "', '" + hireDate + "', " + locationID + ", " + departmentID + 
                    ", " + jobID + ");");
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Add a customer to the database.
     * @param customerID            ID of the customer
     * @param customerLastName      last name of the customer
     * @param customerFirstName     first name of the customer
     * @param customerDOB           date of birth of the customer
     * @param customerGender        gender of the customer
     * @param customerAddress       address of the customer
     * @param customerPhoneNumber   phone number of the customer
     */
    public void insertCustomer(String customerID, String customerLastName,
            String customerFirstName, String customerDOB, String customerGender, 
            String customerAddress, String customerPhoneNumber) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Customer " +
                    "VALUES (" + customerID + ", '" + customerLastName + "', '" + 
                    customerFirstName + "', '" + customerDOB + "', '" + customerGender + 
                    "', '" + customerAddress + "', '" + customerPhoneNumber + "');");
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
