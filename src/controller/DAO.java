package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	private static Connection conn;
	
	public DAO() {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/lim_andrew_db?serverTimezone=UTC";
			String username = "root";
			String password = "root";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static List<String> getStoreLocations() throws Exception {
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
	
	public static List<String> getProducts() throws Exception {
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
	
	public static List<String> getInventory() throws Exception {
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
	
	public static List<String> getEmployees() throws Exception {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM EMPLOYEE;");
			ResultSet result = statement.executeQuery();
			List<String> array = new ArrayList<>();
			while (result.next()) {
				array.add(result.getString("EmployeeID") + "@" + 
						result.getString("EmployeeLastName") + "@" + 
						result.getString("EmployeeFirstName") + "@" + 
						result.getString("EmployeeDOB") + "@" + 
						result.getString("EmployeeGender") + "@" + 
						result.getString("EmployeePhoneNumber") + "@" + 
						result.getString("EmployeeAddress") + "@" + 
						result.getString("hireDate") + "@" + 
						result.getString("LocationID") + "@" + 
						result.getString("DepartmentID") + "@" + 
						result.getString("JobID"));
			}
			return array;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
