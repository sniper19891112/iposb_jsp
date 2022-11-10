package com.iposb.howitworks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.iposb.sys.ConnectionManager;

import org.apache.log4j.Logger;



public class HowitworksBusinessModel {

	
	static Logger logger = Logger.getLogger(HowitworksBusinessModel.class);
	
	private HowitworksBusinessModel(){
	}
	
	
	public static ArrayList<HowitworksDataModel> setRequestData(HttpServletRequest request, String userId) {

		ArrayList<HowitworksDataModel> data = new ArrayList<HowitworksDataModel>();
		HowitworksDataModel obj = null;

		try {
			
	    	obj = new HowitworksDataModel();
	    	obj.setHid(1);
	    	obj.setContent_enUS(request.getParameter("content_enUS") == null ? "" : request.getParameter("content_enUS"));
	    	obj.setContent_msMY(request.getParameter("content_msMY") == null ? "" : request.getParameter("content_msMY"));
	    	obj.setContent_zhCN(request.getParameter("content_zhCN") == null ? "" : request.getParameter("content_zhCN"));
	    	obj.setContent_zhTW(request.getParameter("content_zhTW") == null ? "" : request.getParameter("content_zhTW"));
	    	obj.setModifier(userId);
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error("---Error Howitworks (" + ex.toString() + ")");
			ex.printStackTrace();
		}

		return data;
	}
	
	
	
	
	/**
	 * 訪客查看內容
	 * 
	 * @return
	 */
	public static ArrayList<HowitworksDataModel> viewHowitworks() {

		ArrayList<HowitworksDataModel> data = new ArrayList<HowitworksDataModel>();
		HowitworksDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();	    

		    sql = "SELECT * FROM howitworks";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new HowitworksDataModel();
		    	obj.setHid(Integer.parseInt(rs.getString("hid") == null ? "0" : rs.getString("hid")));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS")); 
		    	obj.setContent_msMY(rs.getString("content_msMY") == null ? "" : rs.getString("content_msMY")); 
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN")); 
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));  
		    	data.add(obj);
		    }
		    
		} catch (Exception ex) {
			logger.error("---Error howitworks (" + ex.toString() + ")");
			ex.printStackTrace();
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){
					logger.error("---Error howitworks (" + ex.toString() + ")");
				}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){
					logger.error("---Error howitworks (" + ex.toString() + ")");
				}
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
	 * 管理員查看的內容
	 * 
	 * @return
	 */
	protected static ArrayList<HowitworksDataModel> howitworksDetails() {

		ArrayList<HowitworksDataModel> data = new ArrayList<HowitworksDataModel>();
		HowitworksDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT * FROM howitworks";
		    
		    rs = stmt.executeQuery(sql);
		    

		    while(rs.next()){
		    	obj = new HowitworksDataModel();
		    	obj.setHid(Integer.parseInt(rs.getString("hid") == null ? "0" : rs.getString("hid")));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS")); 
		    	obj.setContent_msMY(rs.getString("content_msMY") == null ? "" : rs.getString("content_msMY")); 
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN")); 
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW")); 
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	data.add(obj);
		    }
		} catch (Exception ex) {
			logger.error("---Error howitworks (" + ex.toString() + ")");
			ex.printStackTrace();
		} finally {
			if(rs != null){
				try{ rs.close(); } catch(Exception ex){
					logger.error("---Error howitworks (" + ex.toString() + ")");
				}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){
					logger.error("---Error howitworks (" + ex.toString() + ")");
				}
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
	 * 更新
	 * 
	 * @param data
	 * @return
	 */
	protected static String updateHowitworks(ArrayList data) {
		
		String sql = null;
		String result = "";
		Connection conn = null;
		Statement stmt = null;
		HowitworksDataModel howitworksDataData = new HowitworksDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			howitworksDataData = (HowitworksDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				stmt = conn.createStatement();
				sql = "UPDATE howitworks SET " +
					  " content_enUS='"+howitworksDataData.getContent_enUS()+"', " +
					  " content_msMY='"+howitworksDataData.getContent_msMY()+"', " +
					  " content_zhCN='"+howitworksDataData.getContent_zhCN()+"', " +
					  " content_zhTW='"+howitworksDataData.getContent_zhTW()+"', " +
					  " ModifyDT=NOW(), modifier='"+howitworksDataData.getModifier()+"' " +
			  		  " WHERE hid = 1";
//				logger.error(">>>>"+sql);
				stmt.executeUpdate(sql);

				result = "OK";
			}
			catch(Exception ex){
				logger.error("---Error Howitworks (" + ex.toString() + ")");
				ex.printStackTrace();
				result = ex.toString();
			}
			finally {
				if(stmt != null){
					try{ stmt.close(); } catch(Exception ex){
						logger.error("---Error Howitworks (" + ex.toString() + ")");
					}
					stmt = null;
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
