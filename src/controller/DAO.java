package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object
 * @author Andrew Lim
 *
 */
public class DAO {
	private static Connection conn;
	
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
	
	public List<String> getStoreLocations() throws Exception {
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
	
	public List<String> getProducts() throws Exception {
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
	
	public List<String> getInventory() throws Exception {
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
	
	public List<String> getEmployees() throws Exception {
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
	
	public List<String> getCustomers() throws Exception {
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
	
	public List<String> searchStoreLocations(String locationName) throws Exception {
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
	
	public List<String> searchProducts(String productName) throws Exception {
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
	
	public List<String> searchInventory(String locationID, String productID) throws Exception {
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
	
	public List<String> searchEmployees(String employeeLastName, String employeeFirstName) 
			throws Exception {
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
	
	public List<String> searchCustomers(String customerLastName, String customerFirstName)
			throws Exception {
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
	
	public void insertStoreLocation(String locationID, String locationName,
			String storeAddress, String storePhoneNumber) throws Exception {
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO STORE_LOCATION " +
					"VALUES (" + locationID + ", '" + locationName + "', '" + storeAddress +
					"', '" + storePhoneNumber + "');");
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void insertProduct(String productID, String productName,
			String productDescription, String Price, String categoryID, String brandID) 
			throws Exception {
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO PRODUCT " +
					"VALUES (" + productID + ", '" + productName + "', '" + productDescription +
					"', '" + Price + "', " + categoryID + ", " + brandID + ");");
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateInventory(String locationID, String productID,
			String availability, String stock) 
			throws Exception {
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
	
	public void insertEmployee(String employeeID, String employeeLastName,
			String employeeFirstName, String employeeDOB, String employeeGender, 
			String employeePhoneNumber, String employeeAddress, String hireDate, 
			String locationID, String departmentID, String jobID) 
			throws Exception {
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
	
	public void insertCustomer(String customerID, String customerLastName,
			String customerFirstName, String customerDOB, String customerGender, 
			String customerAddress, String customerPhoneNumber) 
			throws Exception {
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
