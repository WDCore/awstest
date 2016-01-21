package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Primary;

public class DatabaseUtility {
	private Connection conn = null;
	private boolean connectivity = true;
	
	public DatabaseUtility() {
		testConnectivity();
	}
	
	public boolean connect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://sql2.freemysqlhosting1.net/sql288864?characterEncoding=utf8", "sql288864", "kQ8!fP7%");
//			conn = DriverManager.getConnection("jdbc:mysql://showroomeraws.c77584w8snul.us-west-2.rds.amazonaws.com/issues", "showroomerroot", "s1h2o2w1r1o1o2m2e1r1");
//			conn = DriverManager.getConnection("jdbc:mysql://mysql.hostinger.co.uk/u352885862_issue", "u352885862_issue", "dn1bll5z501SflNNsU");
			
			return true;
		} catch (SQLException e) {
//			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet execute(String query) {
		try {
			if(connect()) {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				return rs;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Object execute(String query, String fieldToReturn) {
		try {
			if(connect()) {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				Object returnValue = null;
				
				if(rs.next()) {
					returnValue = rs.getInt(fieldToReturn);
				}
				
				disconnect();
				
				return returnValue;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int applyToDatabase(String query, String... arguments) {
		int dbRowId = 0;
		
		try {
			if(connect()) {
				PreparedStatement prep = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				
				for (int i = 0; i < arguments.length; i++) {
					try {
						int argValue = Integer.parseInt(arguments[i]);
	
						prep.setInt(i+1, argValue);
					}
					catch (NumberFormatException ex) {
						prep.setString(i+1, arguments[i]);
					}
				}
				
				prep.executeUpdate();
				
				ResultSet rs = prep.getGeneratedKeys();
				
				if(rs.next()) {
					dbRowId = rs.getInt(1);
				}
				
				disconnect();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dbRowId;
	}
	
	public boolean hasResult(String query) {
		try {
			connect();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			boolean result = rs.next();
			
			disconnect();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void disconnect() {
		if(conn != null) {
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean hasConnectivity() {
		return connectivity;
	}

	public void setConnectivity(boolean connectivity) {
		this.connectivity = connectivity;
	}
	
	public void testConnectivity() {
		if(connect()) {
			connectivity = true;
			Primary.settings.remove("xmlMode");
			Primary.settings.put("xmlMode", false);
		}
		else {
			connectivity = false;
			Primary.settings.remove("xmlMode");
			Primary.settings.put("xmlMode", true);
		}
	}
}