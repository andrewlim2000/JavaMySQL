# JavaMySQL
1. Start MySQL Server.
2. Execute the SQL Script (sql/lim_andrew_backend.sql) in MySQLWorkbench.
3. Edit the user and password in the data access object class (src/controller/DAO.java) to establish a connection to the given database URL.
```java
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
```
4. Run the application GUI class (src/view/app.java).
![Application GUI](images/JavaSQL.png?raw=true)
