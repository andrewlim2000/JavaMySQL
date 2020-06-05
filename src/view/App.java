package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import controller.DAO;

public class App extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1063852224650278010L;
	
	private static DAO dao;

	public App() {
		super("App");
		dao = new DAO();
	}
	
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
	
	public static JPanel storeLocationsTab() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		String[][] data = null;
		try {
        	List<String> list = DAO.getStoreLocations();
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
		        	List<String> list = DAO.searchStoreLocations(locationName);
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

	public static JPanel productsTab() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		String[][] data = null;
		try {
        	List<String> list = DAO.getProducts();
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
		String[] columnNames = { "ProductID", "ProductName", "ProductDescription", 
			"Price" };
		JTable j = new JTable(data, columnNames);
		JScrollPane sp = new JScrollPane(j);
		panel.add(sp, BorderLayout.CENTER);
		return panel;
	}

	public static JPanel inventoryTab() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		String[][] data = null;
		try {
        	List<String> list = DAO.getInventory();
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
		String[] columnNames = { "LocationID", "ProductID", "Availability", 
			"Stock" };
		JTable j = new JTable(data, columnNames);
		JScrollPane sp = new JScrollPane(j);
		panel.add(sp, BorderLayout.CENTER);
		return panel;
	}
	
	public static JPanel employeesTab() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		String[][] data = null;
		try {
        	List<String> list = DAO.getEmployees();
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
			"LocationName",  "DepartmentName", "JobTitle" };
		JTable j = new JTable(data, columnNames);
		JScrollPane sp = new JScrollPane(j);
		panel.add(sp, BorderLayout.CENTER);
		return panel;
	}
	
	public static JPanel customersTab() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		String[][] data = null;
		try {
        	List<String> list = DAO.getCustomers();
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
		return panel;
	}
	
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
