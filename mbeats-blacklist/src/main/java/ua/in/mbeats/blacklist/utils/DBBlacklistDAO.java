package ua.in.mbeats.blacklist.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBBlacklistDAO {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/asterisk";

	// Database credentials
	private String user = null;
	private String password = null;

	private Connection connection = null;
	private Statement statement = null;

	public DBBlacklistDAO() {
	}

	public DBBlacklistDAO(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void connect() {

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			connection = DriverManager.getConnection(DB_URL, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Class NOT FOUND!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL error!");
			e.printStackTrace();
		}
	}

	public String getList() {

		String value = "";

		try {
			String query = "SELECT * FROM blacklist";

			// Execute a query
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				value += rs.getString(1);
				if(!rs.isLast()){
					value += ";";
				}
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("Query execution failed!");
			e.printStackTrace();
		}

		return value;
	}

	public void addValueForList(String msisdn){
		try {
			String query = "INSERT INTO blacklist (msisdn) values ('"+msisdn+"')";

			// Execute a query
			statement = connection.createStatement();
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.println("Query execution failed!");
			e.printStackTrace();
		}
	}
	
	public void deleteValueFromList(){
		try {
			String deleteQuery = "DELETE FROM blacklist";

			// Execute a query
			statement = connection.createStatement();
			statement.executeUpdate(deleteQuery);
			
		} catch (SQLException e) {
			System.out.println("Query execution failed!");
			e.printStackTrace();
		}
	}
	
	public void deleteValueFromListWithID(String msisdn){
		try {
			String deleteQuery = "DELETE FROM blacklist WHERE msisdn='"+msisdn+"'";

			// Execute a query
			statement = connection.createStatement();
			statement.executeUpdate(deleteQuery);
			
		} catch (SQLException e) {
			System.out.println("Query execution failed!");
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
