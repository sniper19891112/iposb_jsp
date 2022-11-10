package com.iposb.notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.iposb.i18n.Resource;
import com.iposb.sys.ConnectionManager;


public class NotificationBusinessModel {

	
	private NotificationBusinessModel(){
	}
	
	static Logger logger = Logger.getLogger(NotificationBusinessModel.class);
	
	
	/**
	 * 列出所有 notification 供 admin 查閱
	 * 
	 * @param pageNo
	 * @param table
	 * @return
	 */
	protected static ArrayList<NotificationDataModel> allNotifications(int pageNo) {

		NotificationDataModel obj = null;
		ArrayList<NotificationDataModel> data = new ArrayList<NotificationDataModel>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();    
	
			    sql = "SELECT a.*, (SELECT COUNT(b.nid) FROM notification_staff b) AS total FROM notification_staff a ORDER BY a.createDT DESC";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	obj = new NotificationDataModel();
			    	obj.setNid(Integer.parseInt(rs.getString("nid").toString()));
			    	obj.setNotifyType(rs.getString("notifyType")==null ? "" : rs.getString("notifyType").toString());
			    	obj.setContent(rs.getString("content")==null ? "" : rs.getString("content").toString());
			    	obj.setParam(rs.getString("param")==null ? "" : rs.getString("param").toString());
			    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
			    	obj.setIsRead(Integer.parseInt(rs.getString("isRead")==null ? "0" : rs.getString("isRead").toString()));
			    	obj.setReadDT(rs.getString("readDT")==null ? "" : rs.getString("readDT").toString());
			    	obj.setReadBy(rs.getString("readBy")==null ? "" : rs.getString("readBy").toString());
			    	obj.setTotal(Integer.parseInt(rs.getString("total")==null ? "0" : rs.getString("total").toString()));
			    	data.add(obj);
			    }


		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return data;
	}
	
	
	/**
	 * 列出所有 notification 供 Agent admin 查閱
	 * 
	 * @param pageNo
	 * @param table
	 * @return
	 */
	protected static ArrayList<NotificationDataModel> allAgentNotifications(int pageNo, int agentId) {

		NotificationDataModel obj = null;
		ArrayList<NotificationDataModel> data = new ArrayList<NotificationDataModel>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();    
	
			    sql = "SELECT a.*, (SELECT COUNT(b.nid) FROM notification_agent b) AS total FROM notification_agent a WHERE a.agentId = "+agentId+" ORDER BY a.createDT DESC";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	obj = new NotificationDataModel();
			    	obj.setNid(Integer.parseInt(rs.getString("nid").toString()));
			    	obj.setAgentId(Integer.parseInt(rs.getString("agentId").toString()));
			    	obj.setNotifyType(rs.getString("notifyType")==null ? "" : rs.getString("notifyType").toString());
			    	obj.setContent(rs.getString("content")==null ? "" : rs.getString("content").toString());
			    	obj.setParam(rs.getString("param")==null ? "" : rs.getString("param").toString());
			    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
			    	obj.setIsRead(Integer.parseInt(rs.getString("isRead")==null ? "0" : rs.getString("isRead").toString()));
			    	obj.setReadDT(rs.getString("readDT")==null ? "" : rs.getString("readDT").toString());
			    	obj.setReadBy(rs.getString("readBy")==null ? "" : rs.getString("readBy").toString());
			    	obj.setTotal(Integer.parseInt(rs.getString("total")==null ? "0" : rs.getString("total").toString()));
			    	data.add(obj);
			    }


		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return data;
	}
	
	
	/**
	 * HQ 看看共有幾個notification未讀
	 * 
	 * @return
	 */
	protected static int countNotification() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int total = 0;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }

			stmt = conn.createStatement();    

		    sql = "SELECT COUNT(nid) AS total FROM notification_staff WHERE isRead = 0";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	total = Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total"));
		    }
			    
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return total;
	}
	
	
	/**
	 * Agent 看看共有幾個notification未讀
	 * 
	 * @param agentId
	 * @return
	 */
	protected static int countAgentNotification(int agentId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int total = 0;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }

			stmt = conn.createStatement();    

		    sql = "SELECT COUNT(nid) AS total FROM notification_agent WHERE agentId = "+agentId+" AND isRead = 0";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	total = Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total"));
		    }
			    
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return total;
	}
	
	/**
	 * Add new notification for Staff
	 * 
	 * @param notifyType
	 * @param content
	 * @param param
	 */
	public static String insertNewStaffNotification (String notifyType, String content, String param) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String result = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			pstmt = conn.prepareStatement("INSERT INTO notification_staff (notifyType, content, param, createDT) " +
					"VALUE " + "(?, ?, ?, NOW())");

			pstmt.setString(1, notifyType);
			pstmt.setString(2, content);
			pstmt.setString(3, param);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			result = "OK";

		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
			result = "-1";
		}
		finally {
			if(pstmt != null){
				try{ pstmt.close(); } catch(Exception ex){}
				pstmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return result;
	}
	
	
	/**
	 * Add new notification for Agent
	 * 
	 * @param notifyType
	 * @param content
	 * @param param
	 * @param agentId
	 */
	public static String insertNewAgentNotification (String notifyType, String content, String param, String agentId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String result = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			pstmt = conn.prepareStatement("INSERT INTO notification_agent (agentId, notifyType, content, param, createDT) " +
					"VALUE " + "(?, ?, ?, ?, NOW())");

			pstmt.setString(1, agentId);
			pstmt.setString(2, notifyType);
			pstmt.setString(3, content);
			pstmt.setString(4, param);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			result = "OK";

		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
			result = "-1";
		}
		finally {
			if(pstmt != null){
				try{ pstmt.close(); } catch(Exception ex){}
				pstmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return result;
	}
	
	
	/**
	 * 找出未讀並組合好 notification 的內容供 HQ staff 讀取
	 * 
	 * @param locale
	 * @return
	 */
	protected static String queryNotification(String locale) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String notificationStr = "";

		try {
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();    
	
			    sql = "SELECT a.*," +
			    		" (SELECT COUNT(b.nid) FROM notification_staff b WHERE b.isRead = 0) AS total" +
			    		" FROM notification_staff a WHERE a.isRead = 0 ORDER BY a.createDT ASC";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){

			    	String icon = "";
			    	String nid = rs.getString("nid") == null ? "0" : rs.getString("nid");
			    	String notifyType = rs.getString("notifyType") == null ? "" : rs.getString("notifyType");
			    	String content = rs.getString("content") == null ? "" : rs.getString("content");
			    	String param = rs.getString("param") == null ? "" : rs.getString("param");
			    	String time = rs.getString("createDT") == null ? "" : rs.getString("createDT").length() > 10 ? rs.getString("createDT").substring(11, 16) : "";
			    	
			    	if(notifyType.equals("consignment")) {
			    		icon = "fa fa-file-text";
			    		param = "./consignment?actionType=searchConsignment&consignmentNo="+param;
			    	}
			    	
			    	notificationStr += "<li onClick=\"openNotification(\'"+nid+"\', '"+param+"')\"><a href=\"#\"><div class=\"clearfix\"><span class=\"pull-left\"><i class=\"btn btn-xs no-hover btn-success icon-caret-right\"></i>"+content+"</span><span class=\"pull-right\"><i class=\"icon-time\"></i> "+time+"</span></div></a></li>";
			    }
			    
			    notificationStr += "<li><a href=\"./notification\">"+Resource.getString("ID_LABEL_SEEALLNOTIFICATION",locale)+"<i class=\"icon-arrow-right\"></i></a></li>";

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return notificationStr;
	}
	
	/**
	 * 找出未讀並組合好 notification 的內容供 Agent staff 讀取
	 * 
	 * @param locale
	 * @param agentId
	 * @return
	 */
	protected static String queryAgentNotification(String locale, int agentId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String notificationStr = "";

		try {
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();    
	
			    sql = "SELECT a.*," +
			    		" (SELECT COUNT(b.nid) FROM notification_agent b WHERE b.agentId = "+agentId+" AND b.isRead = 0) AS total" +
			    		" FROM notification_agent a WHERE a.agentId = "+agentId+" AND a.isRead = 0 ORDER BY a.createDT ASC";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){

			    	String icon = "";
			    	String nid = rs.getString("nid") == null ? "0" : rs.getString("nid");
			    	String notifyType = rs.getString("notifyType") == null ? "" : rs.getString("notifyType");
			    	String content = rs.getString("content") == null ? "" : rs.getString("content");
			    	String param = rs.getString("param") == null ? "" : rs.getString("param");
			    	String time = rs.getString("createDT") == null ? "" : rs.getString("createDT").length() > 10 ? rs.getString("createDT").substring(11, 16) : "";
			    	
			    	if(notifyType.equals("consignment")) {
			    		icon = "fa fa-file-text";
			    		param = "./partner?actionType=searchAgentConsignment&consignmentNo="+param;
			    	}
			    	
			    	notificationStr += "<li onClick=\"openNotification(\'"+nid+"\', '"+param+"')\"><a href=\"#\"><div class=\"clearfix\"><span class=\"pull-left\"><i class=\"btn btn-xs no-hover btn-success icon-caret-right\"></i>"+content+"</span><span class=\"pull-right\"><i class=\"icon-time\"></i> "+time+"</span></div></a></li>";
			    }
			    
			    //notificationStr += "<li><a href=\"./notification\">"+Resource.getString("ID_LABEL_SEEALLNOTIFICATION",locale)+"<i class=\"icon-arrow-right\"></i></a></li>";

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return notificationStr;
	}
	
	
	/**
	 * 標註 staff 已讀取
	 * 
	 * @param nid
	 * @param readBy
	 * @return
	 */
	protected static void markRead(String nid, String readBy) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();
			
			sql = "UPDATE notification_staff SET isRead = 1, readBy='" + readBy + "', readDT=NOW() WHERE nid=" +nid;

			stmt.executeUpdate(sql);

		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
		}
		finally {
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

	}
	
	
	
	/**
	 * 標註 agent 已讀取
	 * 
	 * @param nid
	 * @param readBy
	 * @param agentId
	 * @return
	 */
	protected static void markAgentRead(String nid, String readBy, int agentId) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();
			
			sql = "UPDATE notification_agent SET isRead = 1, readBy='" + readBy + "', readDT=NOW() WHERE agentId = "+agentId+" AND nid=" +nid;

			stmt.executeUpdate(sql);

		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
		}
		finally {
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

	}
	
	
	
}