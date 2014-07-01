package ua.mbeats.asterisk.sip.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.mbeats.asterisk.sip.spring.beans.Db;

public class SipAccountsDAO {
	// JDBC driver name and database URL

	// Database credentials
	private String driver = null;
	private String host = null;
	private String user = null;
	private String password = null;

	private Connection connection = null;
	private Statement statement = null;

	public SipAccountsDAO() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("db-config.xml");
		Db db = (Db) ctx.getBean("db");
		this.driver = db.getDriver();
		this.host = db.getDbUrl();
		this.user = db.getUsername();
		this.password = db.getPassword();
		ctx.close();
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
			Class.forName(driver);

			// Open a connection
			connection = DriverManager.getConnection(host, user, password);
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
			String query = "SELECT * FROM sip";

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

	public void addSipAcc(String sipuser, String sippassword, String sipprovider){
		try {
			String query = "INSERT INTO sip (sipuser,sippassword,sipprovider) values ('"+sipuser+"', '"+sippassword+"', '"+sipprovider+"')";

			// Execute a query
			statement = connection.createStatement();
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.println("Query execution failed!");
			e.printStackTrace();
		}
	}
	
	public void deleteSipAcc(String sipuser){
		try {
			String deleteQuery = "DELETE FROM sip WHERE sipuser='"+sipuser+"'";

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
