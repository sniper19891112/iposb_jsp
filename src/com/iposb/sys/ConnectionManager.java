package com.iposb.sys;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ConnectionManager {
	
	static Logger logger = Logger.getLogger(ConnectionManager.class);
	

	public ConnectionManager() {
	
	}
	
	
	/**
	 * 啟動 connection
	 * @return
	 */
	public static Connection getConnection() {

        Connection conn = null;
        Properties prop = new Properties();

        try {
        	
        	prop.load(ConnectionManager.class.getClassLoader().getResourceAsStream("credentials.properties"));
		    String dbhost = prop.getProperty("dbhost");
		    String dbname = prop.getProperty("dbname");
		    String user = prop.getProperty("user");
		    String pass = prop.getProperty("pass");
		    String environment = prop.getProperty("environment");
		    
		    String CONNURL = "jdbc:mysql://" + dbhost + ":3306/" + dbname + "?useUnicode=true&characterEncoding=utf-8&sessionVariables=time_zone='%2B8:00'";
		    if(environment.trim().equals("development")){
		    	CONNURL += "&useSSL=false";
		    }
        	Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(CONNURL, user, pass);
        	
        } catch (IOException e) {
			logger.error("### IOException: "+e);
		    e.printStackTrace();
		} catch(OutOfMemoryError e) {
        	logger.error("### OutOfMemoryError: "+e);
        	e.printStackTrace();
        } catch (Exception e) {
        	logger.error("### Connection Exception: "+e);
        	e.printStackTrace();
        }
        return conn;
    }

	
	/**
	 * 關閉 connection
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn == null)
            return;
		
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("*** Connection Close Exception: " + e);
		}
	}
	
}

