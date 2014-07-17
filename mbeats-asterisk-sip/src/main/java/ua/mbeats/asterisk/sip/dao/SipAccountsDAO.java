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
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
		Db db = (Db) ctx.getBean("db");
		this.driver = db.getDriver();
		this.host = db.getDbUrl();
		this.user = db.getUsername();
		this.password = db.getPassword();
		ctx.close();
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

	public String showSipList() {

		String value = "";

		try {
			String query = "SELECT name FROM sip_custom WHERE advanced='1' ORDER BY name";

			// Execute a query
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				value += "\""+rs.getString(1)+"\"";
				if(!rs.isLast()){
					value += ", ";
				}
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("Query execution failed!");
			e.printStackTrace();
		}

		return value;
	}
	
	public String fetchSipId(String sipName){
		String value = "";
		try {
			String query = "SELECT id FROM sip_custom WHERE advanced='1' and name = '"+sipName+"'";

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
