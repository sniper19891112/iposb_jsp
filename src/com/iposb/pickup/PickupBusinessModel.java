package com.iposb.pickup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.iposb.sys.ConnectionManager;


public class PickupBusinessModel {

	
	private PickupBusinessModel(){
	}
	
	static Logger logger = Logger.getLogger(PickupBusinessModel.class);
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static ArrayList<PickupDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<PickupDataModel> data = new ArrayList<PickupDataModel>();
		PickupDataModel obj = null;

		try {
			
	    	obj = new PickupDataModel();
	    	obj.setPid(Integer.parseInt(request.getParameter("pid") == null ? "0" : request.getParameter("pid")));
	    	obj.setConsignmentNo(request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo"));
	    	obj.setDriver(request.getParameter("driver") == null ? "" : request.getParameter("driver"));
	    	obj.setStatus(Integer.parseInt(request.getParameter("status") == null ? "0" : request.getParameter("status")));
	    	data.add(obj);
		      
		} catch (Exception ex) {
			logger.error("---Error Service (" + ex.toString() + ")");
			ex.printStackTrace();
		}

		return data;
	}
	

	/**
	 * Get list of pickup requests
	 * 
	 * @param orderBy
	 * @param pageNo
	 * @param consignmentNo
	 * @return
	 */
	public static ArrayList<PickupDataModel> pickuplist(String orderBy, int pageNo) {

		PickupDataModel obj = null;
		ArrayList<PickupDataModel> data = new ArrayList<PickupDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCond = "";

		int page = 0;
		int numberPerPage = 30; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,20; 第二頁,則20,20;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			sqlCond = " WHERE isMonitored = 0";

			stmt = conn.createStatement();

			sql = "SELECT p.*, s.ename," + " c.senderZone, c.senderArea," +
					" (c.createDT) AS consignmentDT," +
					" (z.name_enUS) AS zoneName_enUS," +
					" (a.name_enUS) AS areaName_enUS," +
					" (SELECT COUNT(p.pid) FROM pickup_request p "+sqlCond+") AS total" +
					" FROM pickup_request p" +
					" LEFT JOIN stafflist s ON s.userId = p.driver" +
					" LEFT JOIN consignment c ON c.consignmentNo = p.consignmentNo" +
					" LEFT JOIN zone z ON z.zid = c.senderZone" +
					" LEFT JOIN area a ON a.aid = c.senderArea" +
					sqlCond +
					" ORDER BY consignmentDT DESC, p.status LIMIT " + page + ", " + numberPerPage;

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PickupDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo")==null ? "" : rs.getString("consignmentNo").toString());
		    	obj.setZoneName_enUS(rs.getString("zoneName_enUS")==null ? "" : rs.getString("zoneName_enUS").toString());
		    	obj.setAreaName_enUS(rs.getString("areaName_enUS")==null ? "" : rs.getString("areaName_enUS").toString());
		    	obj.setDriver(rs.getString("driver")==null ? "" : rs.getString("driver").toString());
		    	obj.setDriverName(rs.getString("ename")==null ? "" : rs.getString("ename").toString());
		    	obj.setAgent(rs.getString("agent")==null ? "" : rs.getString("agent").toString());
		    	obj.setStatus(Integer.parseInt(rs.getString("status")==null ? "0" : rs.getString("status").toString()));
		    	obj.setIsMonitored(Integer.parseInt(rs.getString("isMonitored")==null ? "0" : rs.getString("isMonitored").toString()));
		    	obj.setAssignDT(rs.getString("assignDT")==null ? "" : rs.getString("assignDT").toString());
		    	obj.setDeliveryDT(rs.getString("deliveryDT")==null ? "" : rs.getString("deliveryDT").toString());
		    	obj.setAssigner(rs.getString("assigner")==null ? "" : rs.getString("assigner").toString());
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setTotal(Integer.parseInt(rs.getString("total")==null ? "0" : rs.getString("total").toString()));
		    	data.add(obj);
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(rs != null){
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
	 * Get list of pickup requests by userid for app
	 * 
	 * @param userId
	 * @return
	 */
	public static String apppickuplist(String userId) {
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCond = "";
		JSONArray jsonArray = new JSONArray();
		String jsonText = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			if(userId.trim().equals("")){
				userId = "-"; // to prevent getting unassigned request
			}
			
			sqlCond = " WHERE isMonitored = 0 AND driver = '"+userId+"'";
			sqlCond += " AND (p.status = 0 OR p.status = 1)";

			stmt = conn.createStatement();

			sql = "SELECT p.*, c.senderZone, c.senderArea, " +
					" c.senderName, c.senderIC, c.senderAddress1, c.senderAddress2, c.senderAddress3, c.senderPhone, " +
					
					" (c.createDT) AS consignmentDT," +
					" (z.name_enUS) AS zoneName_enUS," +
					" (a.name_enUS) AS areaName_enUS," +
					" (SELECT COUNT(p.pid) FROM pickup_request p "+sqlCond+") AS total" +
					" FROM pickup_request p" +
					" LEFT JOIN consignment c ON c.consignmentNo = p.consignmentNo" +
					" LEFT JOIN zone z ON z.zid = c.senderZone" +
					" LEFT JOIN area a ON a.aid = c.senderArea" +
					sqlCond +
					" ORDER BY consignmentDT DESC";

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	JSONObject jsonObj = new JSONObject();
				jsonObj.put("pid", rs.getString("pid") == null ? "0" : rs.getString("pid").toString());
				jsonObj.put("consignmentNo", rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo").toString());
				jsonObj.put("zoneName_enUS", rs.getString("zoneName_enUS") == null ? "" : rs.getString("zoneName_enUS").toString());
				jsonObj.put("areaName_enUS", rs.getString("areaName_enUS") == null ? "" : rs.getString("areaName_enUS").toString());
//				jsonObj.put("driver", rs.getString("driver") == null ? "" : rs.getString("driver").toString());
//				jsonObj.put("isMonitored", rs.getString("isMonitored") == null ? "0" : rs.getString("isMonitored").toString());
				jsonObj.put("status", rs.getString("status") == null ? "0" : rs.getString("status").toString());
				
				jsonObj.put("senderName", rs.getString("senderName") == null ? "" : rs.getString("senderName").toString());
				jsonObj.put("senderIC", rs.getString("senderIC") == null ? "" : rs.getString("senderIC").toString());
				jsonObj.put("senderAddress1", rs.getString("senderAddress1") == null ? "" : rs.getString("senderAddress1").toString());
				jsonObj.put("senderAddress2", rs.getString("senderAddress2") == null ? "" : rs.getString("senderAddress2").toString());
				jsonObj.put("senderAddress3", rs.getString("senderAddress3") == null ? "" : rs.getString("senderAddress3").toString());
				jsonObj.put("senderPhone", rs.getString("senderPhone") == null ? "" : rs.getString("senderPhone").toString());
				
				jsonObj.put("assignDT", rs.getString("assignDT") == null ? "" : rs.getString("assignDT").toString());
				jsonObj.put("assigner", rs.getString("assigner") == null ? "" : rs.getString("assigner").toString());
				jsonObj.put("deliveryDT", rs.getString("deliveryDT") == null ? "" : rs.getString("deliveryDT").toString());
				jsonObj.put("createDT", rs.getString("createDT") == null ? "" : rs.getString("createDT").toString());
				jsonObj.put("total", rs.getString("total") == null ? "0" : rs.getString("total").toString());
				jsonArray.add(jsonObj);
		    }

		    if(jsonArray != null && jsonArray.size() > 0){
			    jsonText = "{\"pickupdata\":"+JSONValue.toJSONString(jsonArray)+"}";
		    }else{
			    jsonText = "{\"error\":\"noData\"}";
		    }
		    
		} catch (Exception ex) {
			ex.printStackTrace();
			return "{\"error\":\""+ex.toString()+"\"}";
		} finally {
			if(rs != null){
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

		return jsonText;
	}

	
	/**
	 * Search list of pickup requests
	 * 
	 * @param orderBy
	 * @param pageNo
	 * @param consignmentNo
	 * @return
	 */
	public static ArrayList<PickupDataModel> searchPickuplist(String orderBy, int pageNo, String consignmentNo) {

		PickupDataModel obj = null;
		ArrayList<PickupDataModel> data = new ArrayList<PickupDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCond = "";

		int page = 0;
		int numberPerPage = 30; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,20; 第二頁,則20,20;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			if(!consignmentNo.equals("")){
				sqlCond = " WHERE p.consignmentNo LIKE '%"+consignmentNo+"%' AND p.isMonitored = 0";
			}

			stmt = conn.createStatement();

			sql = "SELECT p.*, s.ename," + " c.senderZone, c.senderArea," +
					" (c.createDT) AS consignmentDT," +
					" (z.name_enUS) AS zoneName_enUS," +
					" (a.name_enUS) AS areaName_enUS," +
					" (SELECT COUNT(p.pid) FROM pickup_request p "+sqlCond+") AS total" +
					" FROM pickup_request p" +
					" LEFT JOIN stafflist s ON s.userId = p.driver" +
					" LEFT JOIN consignment c ON c.consignmentNo = p.consignmentNo" +
					" LEFT JOIN zone z ON z.zid = c.senderZone" +
					" LEFT JOIN area a ON a.aid = c.senderArea" +
					sqlCond +
					" ORDER BY consignmentDT DESC, p.status LIMIT " + page + ", " + numberPerPage;

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PickupDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo")==null ? "" : rs.getString("consignmentNo").toString());
		    	obj.setZoneName_enUS(rs.getString("zoneName_enUS")==null ? "" : rs.getString("zoneName_enUS").toString());
		    	obj.setAreaName_enUS(rs.getString("areaName_enUS")==null ? "" : rs.getString("areaName_enUS").toString());
		    	obj.setDriver(rs.getString("driver")==null ? "" : rs.getString("driver").toString());
		    	obj.setDriverName(rs.getString("ename")==null ? "" : rs.getString("ename").toString());
		    	obj.setAgent(rs.getString("agent")==null ? "" : rs.getString("agent").toString());
		    	obj.setStatus(Integer.parseInt(rs.getString("status")==null ? "0" : rs.getString("status").toString()));
		    	obj.setIsMonitored(Integer.parseInt(rs.getString("isMonitored")==null ? "0" : rs.getString("isMonitored").toString()));
		    	obj.setAssignDT(rs.getString("assignDT")==null ? "" : rs.getString("assignDT").toString());
		    	obj.setAssigner(rs.getString("assigner")==null ? "" : rs.getString("assigner").toString());
		    	obj.setDeliveryDT(rs.getString("deliveryDT")==null ? "" : rs.getString("deliveryDT").toString());
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setTotal(Integer.parseInt(rs.getString("total")==null ? "0" : rs.getString("total").toString()));
		    	data.add(obj);
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(rs != null){
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
	 * Insert new pickup request
	 * 
	 * @param consignmentNo
	 * @param driver
	 * @param creator
	 * @return
	 */
	public static String insertRequest(String consignmentNo, String driver, String assigner) {
		
		String result = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			String assignDT = assigner.equals("") ? "''" : "NOW()";
			
			pstmt = conn.prepareStatement("INSERT INTO pickup_request (consignmentNo, driver, assignDT, assigner, createDT) VALUE " + "(?, ?, "+assignDT+", ?, NOW())");

			pstmt.setString(1, consignmentNo);
			pstmt.setString(2, driver);
			pstmt.setString(3, assigner);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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

		return result;
	}
	
	/**
	 * Update new pickup request's driver
	 * 
	 * @param consignmentNo
	 * @param driver
	 * @param agent
	 * @param deliveryDT
	 * @param assigner
	 * @param oldDriver
	 * @return
	 */
	public static String updateRequest(String consignmentNo, String driver, String agent, String deliveryDT, String assigner) {
		
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		
		String result = "";

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			stmt = conn.createStatement();
		    
			sql = "UPDATE pickup_request SET driver = '"+driver+"', agent = '"+agent+"', status = 0, deliveryDT='"+deliveryDT+"', assigner = '"+assigner+"', assignDT = NOW() " +
					"WHERE consignmentNo='" +consignmentNo+"'";
			
			stmt.executeUpdate(sql);
			result = "OK";
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
			return ex.toString();
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

		return result;
	}


	/**
	 * Accept or reject request from app
	 * @param userId
	 * @param status
	 * @param consignmentNo
	 * @return
	 */
	public static boolean answerRequest(String userId, int status, String consignmentNo) {
		
		Statement stmt = null;
		Connection conn = null;
		String sql = "";

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return false;
	        }
			
			stmt = conn.createStatement();
		    
			sql = "UPDATE pickup_request SET status = '"+status+"' WHERE consignmentNo='" +consignmentNo+"' AND driver='"+userId+"'";
			stmt.executeUpdate(sql);
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
			return false;
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

		return true;
	}
	
	/**
	 * Get new request Id and set it to old request
	 * 
	 * @param zid
	 * @param driver
	 * @param pickupDT
	 * @param creator
	 * @return
	 *//*
	public static String setReassignPid(int zid, String driver, String creator, int oldPid) {
		
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		
		String result = "";
		int pid = 0;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			stmt = conn.createStatement();
		    
	    	sql = "SELECT pid FROM pickup_request WHERE zid = "+zid+" AND driver ='"+driver+"' AND creator ='"+creator+"'";
	    	
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	pid = rs.getInt("pid");
		    }
		    
		    if(pid != 0){
				sql = "UPDATE pickup_request SET reassignPid = "+pid+" WHERE pid='" +oldPid+"'";
				stmt.executeUpdate(sql);
				result = "updateSuccess";
		    }
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
			return ex.toString();
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

		return result;
	}*/
	
	/**
	 * Set isMonitored to 1 for all same consignmentNo request
	 * 
	 * @param consignmentNo
	 * @return
	 */
	public static String setIsMonitored(String consignmentNo) {
		
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		
		String result = "";

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			stmt = conn.createStatement();
		    
			sql = "UPDATE pickup_request SET isMonitored = 1 WHERE consignmentNo='" +consignmentNo+"'";
			stmt.executeUpdate(sql);
			result = "updateSuccess";
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
			return ex.toString();
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

		return result;
	}
	
}