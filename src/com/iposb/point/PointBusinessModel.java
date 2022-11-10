package com.iposb.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.sys.ConnectionManager;


public class PointBusinessModel {

	
	private PointBusinessModel(){
	}
	
	static Logger logger = Logger.getLogger(PointBusinessModel.class);
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static ArrayList<PointDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<PointDataModel> data = new ArrayList<PointDataModel>();
		PointDataModel obj = null;

		try {
			
	    	obj = new PointDataModel();
	    	obj.setUserId(request.getParameter("userId") == null ? "" : request.getParameter("userId"));
	    	obj.setPoints(Integer.parseInt(request.getParameter("points") == null ? "0" : request.getParameter("points")));
	    	obj.setPurpose(Integer.parseInt(request.getParameter("purpose") == null ? "0" : request.getParameter("purpose")));
	    	obj.setRemark(request.getParameter("remark") == null ? "" : request.getParameter("remark"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error("---Error Service (" + ex.toString() + ")");
			ex.printStackTrace();
		}

		return data;
	}
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	protected static ArrayList<PointDataModel> getPointWithUserId(String userId){
		
		PointDataModel obj = null;
		ArrayList<PointDataModel> data = new ArrayList<PointDataModel>();
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

		    sql = "SELECT * FROM point WHERE userId = "+ userId;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PointDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid").toString()));
		    	obj.setUserId(rs.getString("userId")==null ? "" : rs.getString("userId").toString());
		    	obj.setPoints(Integer.parseInt(rs.getString("points")==null ? "" : rs.getString("points").toString()));
		    	obj.setPurpose(Integer.parseInt(rs.getString("purpose")==null ? "" : rs.getString("purpose").toString()));
		    	obj.setRemark(rs.getString("remark")==null ? "" : rs.getString("remark").toString());
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
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
	 * 
	 * @param data
	 * @return
	 */
	public static String checkEmail(ArrayList<PointDataModel> data){
		
		PointDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String result = "";
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			PointDataModel pointData = new PointDataModel();
			if(data != null && data.size() > 0){ //如果有資料
				pointData = (PointDataModel)data.get(0);
				
				stmt = conn.createStatement();    
	
			    sql = "SELECT createDT FROM memberlist WHERE userId = '"+ pointData.getUserId()+"'";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	String createDT = rs.getString("createDT") == null ? "" : rs.getString("createDT").toString();
			    	
			    	if(createDT.trim() != ""){
			    		result = "exists";
			    	}
			    	
			    }
			    
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
		
		
		return result;
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String addPoint(ArrayList<PointDataModel> data){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		PointDataModel pointData = new PointDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			pointData = (PointDataModel)data.get(0);
		
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
		        }
				
	
				pstmt = conn.prepareStatement("INSERT INTO point (userId, points, purpose, remark, createDT) " +
						"VALUE " + "(?, ?, ?, ?, NOW())");
	
				pstmt.setString(1, pointData.getUserId());
				pstmt.setInt(2, pointData.getPoints());
				pstmt.setInt(3, pointData.getPurpose());
				pstmt.setString(4, pointData.getRemark());
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				result = "OK";
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.toString());
				result = "error";
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
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String consignmentAddPoint(ArrayList<PointDataModel> data){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		PointDataModel pointData = new PointDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			pointData = (PointDataModel)data.get(0);
		
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
		        }
				
	
				pstmt = conn.prepareStatement("INSERT INTO point (userId, points, purpose, remark, createDT) " +
						"VALUE " + "(?, ?, ?, ?, NOW())");
	
				pstmt.setString(1, pointData.getUserId());
				pstmt.setInt(2, 50);
				pstmt.setInt(3, 4);
				pstmt.setString(4, "");
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				result = "OK";
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.toString());
				result = "error";
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
		}
		
		return result;
	}
	
	
}