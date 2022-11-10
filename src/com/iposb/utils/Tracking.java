package com.iposb.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.iposb.log.LogDataModel;
import com.iposb.sys.ConnectionManager;


public class Tracking {
	
	private static LogDataModel OBJ = null;
	private static ArrayList<LogDataModel> DATA = new ArrayList<LogDataModel>();
	
	private static Connection CONN = null;
	private static Statement STMT = null;
	private static PreparedStatement PSTMT = null;
	private static ResultSet RS = null;
	private static String SQL = "";
	private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(Tracking.class);

	public Tracking(){
	}
	
	
	
	/**
	 * 寫入資料庫
	 */
	public static String update(String trackType, String bookingType, String bookingCode, String IP) {

		try {

			//DB connection
			CONN = ConnectionManager.getConnection();
			if(CONN == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			PSTMT = CONN.prepareStatement("INSERT INTO log_tracking(trackType, bookingType, bookingCode, trackDate, IP)" +
					"VALUE " + "(?, ?, ?, NOW(), ?)");

			PSTMT.setString(1, trackType);
			PSTMT.setString(2, bookingType);
			PSTMT.setString(3, bookingCode);
			PSTMT.setString(4, IP);
			
			PSTMT.executeUpdate();
			PSTMT.clearParameters();
			
			RESULT = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			return ex.toString();
		}
		finally {
			if(PSTMT != null){
				try{ PSTMT.close(); } catch(Exception ex){}
				PSTMT = null;
			}
			if(CONN != null){
				ConnectionManager.closeConnection(CONN);
				CONN = null;
			}
		}

		return RESULT;
	}
	
	
	/**
	 * 查詢關於該booking的所有追踪記錄
	 * 
	 * @param bookingCode
	 * @param bookBy
	 * @return
	 */
	public static ArrayList<LogDataModel> queryDBTrack(String bookingCode, String bookBy) {

		DATA.clear();
		DATA = new ArrayList<LogDataModel>();
		String table = "";

		try {
			
			if(bookingCode.trim().length() > 0){
			
				//DB connection
				CONN = ConnectionManager.getConnection();
				if(CONN == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
				
				if(bookBy.equals("b2b")) {
					table = "log_trackingB2B";
				} else {
					table = "log_tracking";
				}
	
				STMT = CONN.createStatement();    
	
			    SQL = "SELECT a.trackDate, a.IP FROM "+table+" a WHERE a.bookingCode = '"+bookingCode+"' ORDER BY a.trackDate ASC";
			    
			    RS = STMT.executeQuery(SQL);
	
			    while(RS.next()){
			    	OBJ = new LogDataModel();
			    	OBJ.setTrackDate(RS.getString("trackDate") == null ? "" : RS.getString("trackDate"));
			    	OBJ.setIP(RS.getString("IP") == null ? "" : RS.getString("IP"));
			    	DATA.add(OBJ);
			    }
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		} finally {
			if( RS != null){
				try{ RS.close(); } catch(Exception ex){}
				RS = null;
	        }
			if(STMT != null){
				try{ STMT.close(); } catch(Exception ex){}
				STMT = null;
			}
			if(CONN != null){
				ConnectionManager.closeConnection(CONN);
				CONN = null;
			}
		}

		return DATA;
	}

}
