package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.DAO;

/**
 * Application GUI to interact with the retail store database.
 * @author Andrew Lim
 *
 */
public class App extends JFrame {
    
    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = 1063852224650278010L;
    
    /**
     * Data access object.
     */
    private static DAO dao;

    /**
     * Creates the application.
     */
    public App() {
        super("App");
        dao = new DAO();
    }
    
    /**
     * Creates the structure of the application frame.
     */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Store Locations", storeLocationsTab());
        tabbedPane.addTab("Products", productsTab());
        tabbedPane.addTab("Inventory", inventoryTab());
        tabbedPane.addTab("Employees", employeesTab());
        tabbedPane.addTab("Customers", customersTab());
        add(tabbedPane);
        pack();
        setVisible(true);        
    }
    
    /**
     * Creates the store locations tab.
     * @return panel containing the UI to interact with the store locations table
     */
    public static JPanel storeLocationsTab() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        String[][] data = null;
        try {
            List<String> list = dao.getStoreLocations();
            data = new String[list.size()][4];
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                String[] arrOfStr = str.split("@", 4);
                for (int j = 0; j < arrOfStr.length; j++) {
                    data[i][j] = arrOfStr[j];
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String[] columnNames = { "LocationID", "LocationName", "StoreAddress", "StorePhoneNumber" };
        JTable j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        panel.add(sp, BorderLayout.CENTER); 
        final JPanel panel1 = new JPanel();
        final JLabel label = new JLabel("Search by location name:");
        final JTextField textField = new JTextField("", 20);
        final JButton button = new JButton("Search");
        panel1.add(label);
        panel1.add(textField);
        panel1.add(button);
        panel.add(panel1, BorderLayout.NORTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = null;
                String locationName = textField.getText();
                try {
                    List<String> list = dao.searchStoreLocations(locationName);
                    data = new String[list.size()][4];
                    for (int i = 0; i < list.size(); i++) {
                        String str = list.get(i);
                        String[] arrOfStr = str.split("@", 4);
                        for (int j = 0; j < arrOfStr.length; j++) {
                            data[i][j] = arrOfStr[j];
                        }
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                DefaultTableModel model = new DefaultTableModel(data,columnNames); 
                j.setModel(model);
                model.fireTableDataChanged();
            }
        });
        final JPanel panel2 = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxLayout);
        final JLabel locationIDLabel = new JLabel("LocationID:");
        final JLabel locationNameLabel = new JLabel("LocationName:");
        final JLabel storeAddressLabel = new JLabel("StoreAddress:");
        final JLabel storePhoneNumberLabel = new JLabel("StorePhoneNumber:");
        final JTextField locationIDTextField = new JTextField("", 30);
        final JTextField locationNameTextField = new JTextField("", 30);
        final JTextField storeAddressTextField = new JTextField("", 30);
        final JTextField storePhoneNumberTextField = new JTextField("", 30);
        final JPanel locationIDPanel = new JPanel();
        locationIDPanel.add(locationIDLabel);
        locationIDPanel.add(locationIDTextField);
        final JPanel locationNamePanel = new JPanel();
        locationNamePanel.add(locationNameLabel);
        locationNamePanel.add(locationNameTextField);
        final JPanel storeAddressPanel = new JPanel();
        storeAddressPanel.add(storeAddressLabel);
        storeAddressPanel.add(storeAddressTextField);
        final JPanel storePhoneNumberPanel = new JPanel();
        storePhoneNumberPanel.add(storePhoneNumberLabel);
        storePhoneNumberPanel.add(storePhoneNumberTextField);
        final JButton insert = new JButton("Insert");
        panel2.add(locationIDPanel);
        panel2.add(locationNamePanel);
        panel2.add(storeAddressPanel);
        panel2.add(storePhoneNumberPanel);
        panel2.add(insert);
        panel.add(panel2, BorderLayout.SOUTH);
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String locationID = locationIDTextField.getText();
                final String locationName = locationNameTextField.getText();
                final String storeAddress = storeAddressTextField.getText();
                final String storePhoneNumber = storePhoneNumberTextField.getText();
                try {
                    dao.insertStoreLocation(locationID, locationName, storeAddress, storePhoneNumber);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return panel;
    }
    
    /**
     * Creates the products tab.
     * @return  panel containing the UI to interact with the products table
     */
    public static JPanel productsTab() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        String[][] data = null;
        try {
            List<String> list = dao.getProducts();
            data = new String[list.size()][6];
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                String[] arrOfStr = str.split("@", 6);
                for (int j = 0; j < arrOfStr.length; j++) {
                    data[i][j] = arrOfStr[j];
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String[] columnNames = { "ProductID", "ProductName", "ProductDescription", 
            "Price", "CategoryID", "BrandID" };
        JTable j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        panel.add(sp, BorderLayout.CENTER); 
        final JPanel panel1 = new JPanel();
        final JLabel label = new JLabel("Search by product name:");
        final JTextField textField = new JTextField("", 20);
        final JButton button = new JButton("Search");
        panel1.add(label);
        panel1.add(textField);
        panel1.add(button);
        panel.add(panel1, BorderLayout.NORTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = null;
                String productName = textField.getText();
                try {
                    List<String> list = dao.searchProducts(productName);
                    data = new String[list.size()][6];
                    for (int i = 0; i < list.size(); i++) {
                        String str = list.get(i);
                        String[] arrOfStr = str.split("@", 6);
                        for (int j = 0; j < arrOfStr.length; j++) {
                            data[i][j] = arrOfStr[j];
                        }
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                DefaultTableModel model = new DefaultTableModel(data,columnNames); 
                j.setModel(model);
                model.fireTableDataChanged();
            }
        });
        final JPanel panel2 = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxLayout);
        final JLabel productIDLabel = new JLabel("ProductID:");
        final JLabel productNameLabel = new JLabel("ProductName:");
        final JLabel productDescriptionLabel = new JLabel("ProductDescription:");
        final JLabel priceLabel = new JLabel("Price:");
        final JLabel categoryIDLabel = new JLabel("CategoryID:");
        final JLabel brandIDLabel = new JLabel("BrandID:");
        final JTextField productIDTextField = new JTextField("", 30);
        final JTextField productNameTextField = new JTextField("", 30);
        final JTextField productDescriptionTextField = new JTextField("", 30);
        final JTextField priceTextField = new JTextField("", 30);
        final JTextField categoryIDTextField = new JTextField("", 30);
        final JTextField brandIDTextField = new JTextField("", 30);
        final JPanel productIDPanel = new JPanel();
        productIDPanel.add(productIDLabel);
        productIDPanel.add(productIDTextField);
        final JPanel productNamePanel = new JPanel();
        productNamePanel.add(productNameLabel);
        productNamePanel.add(productNameTextField);
        final JPanel productDescriptionPanel = new JPanel();
        productDescriptionPanel.add(productDescriptionLabel);
        productDescriptionPanel.add(productDescriptionTextField);
        final JPanel pricePanel = new JPanel();
        pricePanel.add(priceLabel);
        pricePanel.add(priceTextField);
        final JPanel categoryIDPanel = new JPanel();
        categoryIDPanel.add(categoryIDLabel);
        categoryIDPanel.add(categoryIDTextField);
        final JPanel brandIDPanel = new JPanel();
        brandIDPanel.add(brandIDLabel);
        brandIDPanel.add(brandIDTextField);
        final JButton insert = new JButton("Insert");
        panel2.add(productIDPanel);
        panel2.add(productNamePanel);
        panel2.add(productDescriptionPanel);
        panel2.add(pricePanel);
        panel2.add(categoryIDPanel);
        panel2.add(brandIDPanel);
        panel2.add(insert);
        panel.add(panel2, BorderLayout.SOUTH);
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String productID = productIDTextField.getText();
                final String productName = productNameTextField.getText();
                final String productDescription = productDescriptionTextField.getText();
                final String price = priceTextField.getText();
                final String categoryID = categoryIDTextField.getText();
                final String brandID = brandIDTextField.getText();
                try {
                    dao.insertProduct(productID, productName, productDescription, price,
                            categoryID, brandID);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return panel;
    }

    /**
     * Creates the inventory tab.
     * @return panel containing the UI to interact with the inventory table
     */
    public static JPanel inventoryTab() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        String[][] data = null;
        try {
            List<String> list = dao.getInventory();
            data = new String[list.size()][4];
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                String[] arrOfStr = str.split("@", 4);
                for (int j = 0; j < arrOfStr.length; j++) {
                    data[i][j] = arrOfStr[j];
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String[] columnNames = { "LocationID", "ProductID", "Availability", "Stock" };
        JTable j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        panel.add(sp, BorderLayout.CENTER); 
        final JPanel panel1 = new JPanel();
        final JLabel label1 = new JLabel("Search by location ID:");
        final JTextField textField1 = new JTextField("", 20);
        final JLabel label2 = new JLabel("Search by product ID:");
        final JTextField textField2 = new JTextField("", 20);
        final JButton button = new JButton("Search");
        panel1.add(label1);
        panel1.add(textField1);
        panel1.add(label2);
        panel1.add(textField2);
        panel1.add(button);
        panel.add(panel1, BorderLayout.NORTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = null;
                String locationID = textField1.getText();
                String productID = textField2.getText();
                try {
                    List<String> list = dao.searchInventory(locationID, productID);
                    data = new String[list.size()][4];
                    for (int i = 0; i < list.size(); i++) {
                        String str = list.get(i);
                        String[] arrOfStr = str.split("@", 4);
                        for (int j = 0; j < arrOfStr.length; j++) {
                            data[i][j] = arrOfStr[j];
                        }
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                DefaultTableModel model = new DefaultTableModel(data,columnNames); 
                j.setModel(model);
                model.fireTableDataChanged();
            }
        });
        final JPanel panel2 = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxLayout);
        final JLabel locationIDLabel = new JLabel("LocationID:");
        final JLabel productIDLabel = new JLabel("ProductID:");
        final JLabel availabilityLabel = new JLabel("Availability:");
        final JLabel stockLabel = new JLabel("Stock:");
        final JTextField locationIDTextField = new JTextField("", 30);
        final JTextField productIDTextField = new JTextField("", 30);
        final JTextField availabilityTextField = new JTextField("", 30);
        final JTextField stockTextField = new JTextField("", 30);
        final JPanel locationIDPanel = new JPanel();
        locationIDPanel.add(locationIDLabel);
        locationIDPanel.add(locationIDTextField);
        final JPanel productIDPanel = new JPanel();
        productIDPanel.add(productIDLabel);
        productIDPanel.add(productIDTextField);
        final JPanel availabilityPanel = new JPanel();
        availabilityPanel.add(availabilityLabel);
        availabilityPanel.add(availabilityTextField);
        final JPanel stockPanel = new JPanel();
        stockPanel.add(stockLabel);
        stockPanel.add(stockTextField);
        final JButton update = new JButton("Update");
        panel2.add(locationIDPanel);
        panel2.add(productIDPanel);
        panel2.add(availabilityPanel);
        panel2.add(stockPanel);
        panel2.add(update);
        panel.add(panel2, BorderLayout.SOUTH);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String locationID = locationIDTextField.getText();
                final String productID = productIDTextField.getText();
                final String availability = availabilityTextField.getText();
                final String stock = stockTextField.getText();
                try {
                    dao.updateInventory(locationID, productID, availability, stock);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return panel;
    }
    
    /**
     * Creates the employees tab.
     * @return panel containing the UI to interact with the employees table
     */
    public static JPanel employeesTab() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        String[][] data = null;
        try {
            List<String> list = dao.getEmployees();
            data = new String[list.size()][6];
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                String[] arrOfStr = str.split("@", 6);
                for (int j = 0; j < arrOfStr.length; j++) {
                    data[i][j] = arrOfStr[j];
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String[] columnNames = { "EmployeeID", "EmployeeLastName", "EmployeeFirstName", 
                "LocationName", "DepartmentName", "JobTitle" };
        JTable j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        panel.add(sp, BorderLayout.CENTER); 
        final JPanel panel1 = new JPanel();
        final JLabel label1 = new JLabel("Search by last name:");
        final JTextField textField1 = new JTextField("", 20);
        final JLabel label2 = new JLabel("Search by first name:");
        final JTextField textField2 = new JTextField("", 20);
        final JButton button = new JButton("Search");
        panel1.add(label1);
        panel1.add(textField1);
        panel1.add(label2);
        panel1.add(textField2);
        panel1.add(button);
        panel.add(panel1, BorderLayout.NORTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = null;
                String employeeLastName = textField1.getText();
                String employeeFirstName = textField2.getText();
                try {
                    List<String> list = dao.searchEmployees(employeeLastName, employeeFirstName);
                    data = new String[list.size()][6];
                    for (int i = 0; i < list.size(); i++) {
                        String str = list.get(i);
                        String[] arrOfStr = str.split("@", 6);
                        for (int j = 0; j < arrOfStr.length; j++) {
                            data[i][j] = arrOfStr[j];
                        }
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                DefaultTableModel model = new DefaultTableModel(data,columnNames); 
                j.setModel(model);
                model.fireTableDataChanged();
            }
        });
        final JPanel panel2 = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxLayout);
        final JLabel employeeIDLabel = new JLabel("EmployeeID:");
        final JLabel employeeLastNameLabel = new JLabel("EmployeeLastName:");
        final JLabel employeeFirstNameLabel = new JLabel("EmployeeFirstName:");
        final JLabel employeeDOBLabel = new JLabel("EmployeeDOB:");
        final JLabel employeeGenderLabel = new JLabel("EmployeeGender:");
        final JLabel employeePhoneNumberLabel = new JLabel("EmployeePhoneNumber:");
        final JLabel employeeAddressLabel = new JLabel("EmployeeAddress:");
        final JLabel hireDateLabel = new JLabel("HireDate:");
        final JLabel locationIDLabel = new JLabel("LocationID:");
        final JLabel departmentIDLabel = new JLabel("DepartmentID:");
        final JLabel jobIDLabel = new JLabel("JobID:");
        final JTextField employeeIDTextField = new JTextField("", 30);
        final JTextField employeeLastNameTextField = new JTextField("", 30);
        final JTextField employeeFirstNameTextField = new JTextField("", 30);
        final JTextField employeeDOBTextField = new JTextField("", 30);
        final JTextField employeeGenderTextField = new JTextField("", 30);
        final JTextField employeePhoneNumberTextField = new JTextField("", 30);
        final JTextField employeeAddressTextField = new JTextField("", 30);
        final JTextField hireDateTextField = new JTextField("", 30);
        final JTextField locationIDTextField = new JTextField("", 30);
        final JTextField departmentIDTextField = new JTextField("", 30);
        final JTextField jobIDTextField = new JTextField("", 30);
        final JPanel employeeIDPanel = new JPanel();
        employeeIDPanel.add(employeeIDLabel);
        employeeIDPanel.add(employeeIDTextField);
        final JPanel employeeLastNamePanel = new JPanel();
        employeeLastNamePanel.add(employeeLastNameLabel);
        employeeLastNamePanel.add(employeeLastNameTextField);
        final JPanel employeeFirstNamePanel = new JPanel();
        employeeFirstNamePanel.add(employeeFirstNameLabel);
        employeeFirstNamePanel.add(employeeFirstNameTextField);
        final JPanel employeeDOBPanel = new JPanel();
        employeeDOBPanel.add(employeeDOBLabel);
        employeeDOBPanel.add(employeeDOBTextField);
        final JPanel employeeGenderPanel = new JPanel();
        employeeGenderPanel.add(employeeGenderLabel);
        employeeGenderPanel.add(employeeGenderTextField);
        final JPanel employeePhoneNumberPanel = new JPanel();
        employeePhoneNumberPanel.add(employeePhoneNumberLabel);
        employeePhoneNumberPanel.add(employeePhoneNumberTextField);
        final JPanel employeeAddressPanel = new JPanel();
        employeeAddressPanel.add(employeeAddressLabel);
        employeeAddressPanel.add(employeeAddressTextField);
        final JPanel hireDatePanel = new JPanel();
        hireDatePanel.add(hireDateLabel);
        hireDatePanel.add(hireDateTextField);
        final JPanel locationIDPanel = new JPanel();
        locationIDPanel.add(locationIDLabel);
        locationIDPanel.add(locationIDTextField);
        final JPanel departmentIDPanel = new JPanel();
        departmentIDPanel.add(departmentIDLabel);
        departmentIDPanel.add(departmentIDTextField);
        final JPanel jobIDPanel = new JPanel();
        jobIDPanel.add(jobIDLabel);
        jobIDPanel.add(jobIDTextField);
        final JButton insert = new JButton("Insert");
        panel2.add(employeeIDPanel);
        panel2.add(employeeLastNamePanel);
        panel2.add(employeeFirstNamePanel);
        panel2.add(employeeDOBPanel);
        panel2.add(employeeGenderPanel);
        panel2.add(employeePhoneNumberPanel);
        panel2.add(employeeAddressPanel);
        panel2.add(hireDatePanel);
        panel2.add(locationIDPanel);
        panel2.add(departmentIDPanel);
        panel2.add(jobIDPanel);
        panel2.add(insert);
        panel.add(panel2, BorderLayout.SOUTH);
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String employeeID = employeeIDTextField.getText();
                final String employeeLastName = employeeLastNameTextField.getText();
                final String employeeFirstName = employeeFirstNameTextField.getText();
                final String employeeDOB = employeeDOBTextField.getText();
                final String employeeGender = employeeGenderTextField.getText();
                final String employeePhoneNumber = employeePhoneNumberTextField.getText();
                final String employeeAddress = employeeAddressTextField.getText();
                final String hireDate = hireDateTextField.getText();
                final String locationID = locationIDTextField.getText();
                final String departmentID = departmentIDTextField.getText();
                final String jobID = jobIDTextField.getText();
                try {
                    dao.insertEmployee(employeeID, employeeLastName, employeeFirstName, 
                            employeeDOB, employeeGender, employeePhoneNumber, employeeAddress,
                            hireDate, locationID, departmentID, jobID);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return panel;
    }
    
    /**
     * Creates the customers tab.
     * @return panel containing the UI to interact with the customers table
     */
    public static JPanel customersTab() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        String[][] data = null;
        try {
            List<String> list = dao.getCustomers();
            data = new String[list.size()][4];
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                String[] arrOfStr = str.split("@", 4);
                for (int j = 0; j < arrOfStr.length; j++) {
                    data[i][j] = arrOfStr[j];
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String[] columnNames = { "CustomerID", "CustomerLastName", "CustomerFirstName", 
                "NumOfOrders" };
        JTable j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        panel.add(sp, BorderLayout.CENTER); 
        final JPanel panel1 = new JPanel();
        final JLabel label1 = new JLabel("Search by last name:");
        final JTextField textField1 = new JTextField("", 20);
        final JLabel label2 = new JLabel("Search by first name:");
        final JTextField textField2 = new JTextField("", 20);
        final JButton button = new JButton("Search");
        panel1.add(label1);
        panel1.add(textField1);
        panel1.add(label2);
        panel1.add(textField2);
        panel1.add(button);
        panel.add(panel1, BorderLayout.NORTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = null;
                String customerLastName = textField1.getText();
                String customerFirstName = textField2.getText();
                try {
                    List<String> list = dao.searchCustomers(customerLastName, customerFirstName);
                    data = new String[list.size()][4];
                    for (int i = 0; i < list.size(); i++) {
                        String str = list.get(i);
                        String[] arrOfStr = str.split("@", 4);
                        for (int j = 0; j < arrOfStr.length; j++) {
                            data[i][j] = arrOfStr[j];
                        }
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                DefaultTableModel model = new DefaultTableModel(data,columnNames); 
                j.setModel(model);
                model.fireTableDataChanged();
            }
        });
        final JPanel panel2 = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxLayout);
        final JLabel customerIDLabel = new JLabel("CustomerID:");
        final JLabel customerLastNameLabel = new JLabel("CustomerLastName:");
        final JLabel customerFirstNameLabel = new JLabel("CustomerFirstName:");
        final JLabel customerDOBLabel = new JLabel("CustomerDOB:");
        final JLabel customerGenderLabel = new JLabel("CustomerGender:");
        final JLabel customerAddressLabel = new JLabel("CustomerAddress:");
        final JLabel customerPhoneNumberLabel = new JLabel("CustomerPhoneNumber:");
        final JTextField customerIDTextField = new JTextField("", 30);
        final JTextField customerLastNameTextField = new JTextField("", 30);
        final JTextField customerFirstNameTextField = new JTextField("", 30);
        final JTextField customerDOBTextField = new JTextField("", 30);
        final JTextField customerGenderTextField = new JTextField("", 30);
        final JTextField customerAddressTextField = new JTextField("", 30);
        final JTextField customerPhoneNumberTextField = new JTextField("", 30);
        final JPanel customerIDPanel = new JPanel();
        customerIDPanel.add(customerIDLabel);
        customerIDPanel.add(customerIDTextField);
        final JPanel customerLastNamePanel = new JPanel();
        customerLastNamePanel.add(customerLastNameLabel);
        customerLastNamePanel.add(customerLastNameTextField);
        final JPanel customerFirstNamePanel = new JPanel();
        customerFirstNamePanel.add(customerFirstNameLabel);
        customerFirstNamePanel.add(customerFirstNameTextField);
        final JPanel customerDOBPanel = new JPanel();
        customerDOBPanel.add(customerDOBLabel);
        customerDOBPanel.add(customerDOBTextField);
        final JPanel customerGenderPanel = new JPanel();
        customerGenderPanel.add(customerGenderLabel);
        customerGenderPanel.add(customerGenderTextField);
        final JPanel customerAddressPanel = new JPanel();
        customerAddressPanel.add(customerAddressLabel);
        customerAddressPanel.add(customerAddressTextField);
        final JPanel customerPhoneNumberPanel = new JPanel();
        customerPhoneNumberPanel.add(customerPhoneNumberLabel);
        customerPhoneNumberPanel.add(customerPhoneNumberTextField);
        final JButton insert = new JButton("Insert");
        panel2.add(customerIDPanel);
        panel2.add(customerLastNamePanel);
        panel2.add(customerFirstNamePanel);
        panel2.add(customerDOBPanel);
        panel2.add(customerGenderPanel);
        panel2.add(customerAddressPanel);
        panel2.add(customerPhoneNumberPanel);
        panel2.add(insert);
        panel.add(panel2, BorderLayout.SOUTH);
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String customerID = customerIDTextField.getText();
                final String customerLastName = customerLastNameTextField.getText();
                final String customerFirstName = customerFirstNameTextField.getText();
                final String customerDOB = customerDOBTextField.getText();
                final String customerGender = customerGenderTextField.getText();
                final String customerAddress = customerAddressTextField.getText();
                final String customerPhoneNumber = customerPhoneNumberTextField.getText();
                try {
                    dao.insertCustomer(customerID, customerLastName, customerFirstName, 
                            customerDOB, customerGender, customerAddress, customerPhoneNumber);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return panel;
    }
    
    /**
     * Main method to run the application.
     * @param theArgs
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final App app = new App();
                app.start();
            }
        });
    }
}
