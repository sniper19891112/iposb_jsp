package com.iposb.term;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.iposb.sys.ConnectionManager;

import org.apache.log4j.Logger;



public class TermBusinessModel {

	
	static Logger logger = Logger.getLogger(TermBusinessModel.class);
	
	private TermBusinessModel(){
	}
	
	
	public static ArrayList<TermDataModel> setRequestData(HttpServletRequest request, String userId) {

		ArrayList<TermDataModel> data = new ArrayList<TermDataModel>();
		TermDataModel obj = null;

		try {
			
	    	obj = new TermDataModel();
	    	obj.setTid(1);
	    	obj.setContent_enUS(request.getParameter("content_enUS") == null ? "" : request.getParameter("content_enUS"));
	    	obj.setContent_msMY(request.getParameter("content_msMY") == null ? "" : request.getParameter("content_msMY"));
	    	obj.setContent_zhCN(request.getParameter("content_zhCN") == null ? "" : request.getParameter("content_zhCN"));
	    	obj.setContent_zhTW(request.getParameter("content_zhTW") == null ? "" : request.getParameter("content_zhTW"));
	    	obj.setModifier(userId);
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error("---Error About Us (" + ex.toString() + ")");
			ex.printStackTrace();
		}

		return data;
	}
	
	
	
	
	/**
	 * 訪客查看內容
	 * 
	 * @return
	 */
	public static ArrayList<TermDataModel> viewTerm() {

		ArrayList<TermDataModel> data = new ArrayList<TermDataModel>();
		TermDataModel obj = null;
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

		    sql = "SELECT * FROM term";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new TermDataModel();
		    	obj.setTid(Integer.parseInt(rs.getString("tid") == null ? "0" : rs.getString("tid")));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS")); 
		    	obj.setContent_msMY(rs.getString("content_msMY") == null ? "" : rs.getString("content_msMY")); 
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN")); 
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));  
		    	data.add(obj);
		    }
		    
		} catch (Exception ex) {
			logger.error("---Error Terms (" + ex.toString() + ")");
			ex.printStackTrace();
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){
					logger.error("---Error Terms (" + ex.toString() + ")");
				}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){
					logger.error("---Error Terms (" + ex.toString() + ")");
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
	protected static ArrayList<TermDataModel> termDetails() {

		ArrayList<TermDataModel> data = new ArrayList<TermDataModel>();
		TermDataModel obj = null;
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

			sql = "SELECT * FROM term";
		    
		    rs = stmt.executeQuery(sql);
		    

		    while(rs.next()){
		    	obj = new TermDataModel();
		    	obj.setTid(Integer.parseInt(rs.getString("tid") == null ? "0" : rs.getString("tid")));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS")); 
		    	obj.setContent_msMY(rs.getString("content_msMY") == null ? "" : rs.getString("content_msMY")); 
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN")); 
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW")); 
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	data.add(obj);
		    }
		} catch (Exception ex) {
			logger.error("---Error Terms (" + ex.toString() + ")");
			ex.printStackTrace();
		} finally {
			if(rs != null){
				try{ rs.close(); } catch(Exception ex){
					logger.error("---Error Terms (" + ex.toString() + ")");
				}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){
					logger.error("---Error Terms (" + ex.toString() + ")");
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
	protected static String updateTerm(ArrayList data) {
		
		String sql = null;
		String result = "";
		Connection conn = null;
		Statement stmt = null;
		TermDataModel termDataData = new TermDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			termDataData = (TermDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				stmt = conn.createStatement();
				sql = "UPDATE term SET " +
					  " content_enUS='"+termDataData.getContent_enUS()+"', " +
					  " content_msMY='"+termDataData.getContent_msMY()+"', " +
					  " content_zhCN='"+termDataData.getContent_zhCN()+"', " +
					  " content_zhTW='"+termDataData.getContent_zhTW()+"', " +
					  " ModifyDT=NOW(), modifier='"+termDataData.getModifier()+"' " +
			  		  " WHERE tid = 1";
//				logger.error(">>>>"+sql);
				stmt.executeUpdate(sql);

				result = "OK";
			}
			catch(Exception ex){
				logger.error("---Error About Us (" + ex.toString() + ")");
				ex.printStackTrace();
				result = ex.toString();
			}
			finally {
				if(stmt != null){
					try{ stmt.close(); } catch(Exception ex){
						logger.error("---Error About Us (" + ex.toString() + ")");
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
