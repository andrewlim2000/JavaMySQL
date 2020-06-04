package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
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
		return panel;
	}

	public static JPanel productsTab() {
		return null;
	}

	public static JPanel inventoryTab() {
		return null;
	}
	
	public static JPanel employeesTab() {
		return null;
	}
	
	public static JPanel customersTab() {
		return null;
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
