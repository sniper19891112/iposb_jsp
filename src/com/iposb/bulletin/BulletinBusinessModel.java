package com.iposb.bulletin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.sys.ConnectionManager;
import com.iposb.utils.UtilsBusinessModel;

public class BulletinBusinessModel {
	

	static Logger logger = Logger.getLogger(BulletinBusinessModel.class);
	
	private BulletinBusinessModel(){
	}
	
	
	public static ArrayList<BulletinDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<BulletinDataModel> data = new ArrayList<BulletinDataModel>();
		BulletinDataModel obj = null;

		try {
			
	    	obj = new BulletinDataModel();
	    	obj.setBid(Integer.parseInt(request.getParameter("bid") == null ? "0" : request.getParameter("bid")));
	    	obj.setCategory(request.getParameter("category") == null ? "" : request.getParameter("category"));
	    	obj.setTitle_enUS(request.getParameter("title_enUS") == null ? "" : request.getParameter("title_enUS"));
	    	obj.setTitle_zhCN(request.getParameter("title_zhCN") == null ? "" : request.getParameter("title_zhCN"));
	    	obj.setTitle_zhTW(request.getParameter("title_zhTW") == null ? "" : request.getParameter("title_zhTW"));
	    	obj.setContent_enUS(request.getParameter("content_enUS") == null ? "" : request.getParameter("content_enUS"));
	    	obj.setContent_zhCN(request.getParameter("content_zhCN") == null ? "" : request.getParameter("content_zhCN"));
	    	obj.setContent_zhTW(request.getParameter("content_zhTW") == null ? "" : request.getParameter("content_zhTW"));
	    	obj.setUrl(request.getParameter("url") == null ? "" : request.getParameter("url"));
	    	obj.setIsShow(Integer.parseInt(request.getParameter("isShow") == null ? "0" : request.getParameter("isShow").equals("on") ? "1" : "0"));
	    	obj.setCreator(request.getParameter("creator") == null ? "" : request.getParameter("creator"));
	    	obj.setModifier(request.getParameter("modifier") == null ? "" : request.getParameter("modifier"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}

		return data;
	}
	
	
	public static ArrayList<BulletinDataModel> searchBulletin(String orderBy, int pageNo) {

		String sqlCondition = "";
		
		ArrayList<BulletinDataModel> data = new ArrayList<BulletinDataModel>();
		BulletinDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int page = 0;
		int numberPerPage = 10; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,10; 第二頁,則10,10;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			sqlCondition = " WHERE a.isShow = 1";		    

		    sql = "SELECT a.bid, a.category, a.title_enUS, a.title_zhCN, a.title_zhTW, a.content_enUS," +
		    		" a.content_zhCN, a.content_zhTW, a.url, a.view, a.isShow," +
		    		" (SELECT COUNT(b.bid) FROM bulletin b " + sqlCondition + ") AS total" +
		    		" FROM bulletin a" + sqlCondition + 
		    		" ORDER BY a." + orderBy + " DESC, a.bid ASC LIMIT " + page + ", " + numberPerPage;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new BulletinDataModel();
		    	obj.setBid(Integer.parseInt(rs.getString("bid") == null ? "0" : rs.getString("bid")));
		    	obj.setCategory(rs.getString("category") == null ? "" : rs.getString("category"));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setTitle_zhCN(rs.getString("title_zhCN") == null ? "" : rs.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rs.getString("title_zhTW") == null ? "" : rs.getString("title_zhTW"));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS"));     
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN"));      
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));
		    	obj.setUrl(rs.getString("url") == null ? "" : rs.getString("url"));
		    	obj.setView(Integer.parseInt(rs.getString("view") == null ? "0" : rs.getString("view")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
		    	data.add(obj);
		    }

		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
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
	 * 管理員查看Bulletin清單
	 * @param orderby
	 * @param pageNo
	 * @param category
	 * @return
	 */
	public static ArrayList<BulletinDataModel> bulletinList(String orderby, int pageNo, String category) {

		String sqlCondition = "";
		
		ArrayList<BulletinDataModel> data = new ArrayList<BulletinDataModel>();
		BulletinDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int page = 0;
		int numberPerPage = 20; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,10; 第二頁,則10,10;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			if(category != null && !category.equals("")){
		    	sqlCondition += " WHERE category = '" + category + "'";
		    }

			sql = "SELECT a.bid, a.category, a.title_enUS, a.title_zhCN, a.title_zhTW, a.content_enUS," +
		    		" a.content_zhCN, a.content_zhTW, a.url, a.view, a.isShow, a.createDT, a.creator, a.modifyDT, a.modifier," +
		    		" (SELECT COUNT(b.bid) FROM bulletin b " + sqlCondition + ") AS total" +
		    		" FROM bulletin a " + sqlCondition;
		    
		    if(orderby!=null && !orderby.equals("")){
		    	sql += " ORDER BY a." + orderby + " DESC";
		    }
		    
//		    sql += " LIMIT " + page + ", " + numberPerPage;
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new BulletinDataModel();
		    	obj.setBid(Integer.parseInt(rs.getString("bid") == null ? "0" : rs.getString("bid")));
		    	obj.setCategory(rs.getString("category") == null ? "" : rs.getString("category"));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setTitle_zhCN(rs.getString("title_zhCN") == null ? "" : rs.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rs.getString("title_zhTW") == null ? "" : rs.getString("title_zhTW"));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS"));     
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN"));      
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));
		    	obj.setUrl(rs.getString("url") == null ? "" : rs.getString("url"));
		    	obj.setView(Integer.parseInt(rs.getString("view") == null ? "0" : rs.getString("view")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
		    	data.add(obj);
		    }
		    
		} catch (Exception ex) {
			logger.error(ex.toString());
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
	 * 詳情
	 * @param bid
	 * @return
	 */
	public static ArrayList<BulletinDataModel> bulletinDetails(String bid) {

		ArrayList<BulletinDataModel> data = new ArrayList<BulletinDataModel>();
		BulletinDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		logger.info("---User is reading BULLETIN: " + bid + " (" + UtilsBusinessModel.timeNow() + ")");

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.bid, a.category, a.title_enUS, a.title_zhCN, a.title_zhTW, a.content_enUS," +
    			  " a.content_zhCN, a.content_zhTW, a.url, a.view, a.isShow, a.createDT, a.creator, a.modifyDT, a.modifier" +
		    	  " FROM bulletin a";
		    
		    if(bid != null && bid != ""){
		    	sql += " WHERE a.bid = " + bid;
		    }
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new BulletinDataModel();
		    	obj.setBid(Integer.parseInt(rs.getString("bid") == null ? "0" : rs.getString("bid")));
		    	obj.setCategory(rs.getString("category") == null ? "" : rs.getString("category"));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setTitle_zhCN(rs.getString("title_zhCN") == null ? "" : rs.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rs.getString("title_zhTW") == null ? "" : rs.getString("title_zhTW"));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS"));     
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN"));      
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));
		    	obj.setUrl(rs.getString("url") == null ? "" : rs.getString("url"));
		    	obj.setView(Integer.parseInt(rs.getString("view") == null ? "0" : rs.getString("view")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	data.add(obj);
		    }
		      
		} catch (Exception ex) {
			logger.error(ex.toString());
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
	 * 新增 bulletin
	 * @param data
	 * @return
	 */
	public static String addNewBulletin(ArrayList<BulletinDataModel> data) {

		
		BulletinDataModel bulletinData = new BulletinDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			bulletinData = (BulletinDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO bulletin(category, title_enUS, title_zhCN, title_zhTW," +
						" content_enUS, content_zhCN, content_zhTW, url, view, isShow, createDT, creator, modifyDT) " +
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW())");
	
				pstmt.setString(1, bulletinData.getCategory());
				pstmt.setString(2, bulletinData.getTitle_enUS());
				pstmt.setString(3, bulletinData.getTitle_zhCN());
				pstmt.setString(4, bulletinData.getTitle_zhTW());
				pstmt.setString(5, bulletinData.getContent_enUS());
				pstmt.setString(6, bulletinData.getContent_zhCN());
				pstmt.setString(7, bulletinData.getContent_zhTW());
				pstmt.setString(8, bulletinData.getUrl());
				pstmt.setInt(9, bulletinData.getView());
				pstmt.setInt(10, bulletinData.getIsShow());
				pstmt.setString(11, bulletinData.getCreator());	
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
			
			}
			catch(Exception ex){
				logger.error(ex.toString());
				ex.printStackTrace();
				return ex.toString();
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
		} else {
			return "noData"; //可能是換語系造成的
		}

		return "OK";
	}

	
	
	/**
	 * 更新內容
	 * @param data
	 * @return
	 */
	public static String updateBulletin(ArrayList<BulletinDataModel> data) {

		String result = "";
		
		BulletinDataModel bulletinData = new BulletinDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		if(data != null && data.size() > 0){ //如果有資料
			bulletinData = (BulletinDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE bulletin SET category='"+bulletinData.getCategory()+"', title_enUS='"+bulletinData.getTitle_enUS()+"'," +
					  " title_zhCN='"+bulletinData.getTitle_zhCN()+"', title_zhTW='"+bulletinData.getTitle_zhTW()+"'," +
					  " content_enUS='"+bulletinData.getContent_enUS()+"', content_zhCN='"+bulletinData.getContent_zhCN()+"'," +
					  " content_zhTW='"+bulletinData.getContent_zhTW()+"', url='"+bulletinData.getUrl()+"', isShow="+bulletinData.getIsShow()+"," +
					  " ModifyDT=NOW(), modifier='"+bulletinData.getModifier()+"' WHERE bid=" +bulletinData.getBid();
				stmt.executeUpdate(sql);

				result = "OK";
			}
			catch(Exception ex){
				logger.error(ex.toString());
				ex.printStackTrace();
				result = ex.toString();
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

		return result;
	}
	
	
	/**
	 * 取得最大(最新)的BID
	 * @return
	 */
	public static int getMaxBid() {

		int maxBid = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }
			
			stmt = conn.createStatement();

		    sql = "SELECT MAX(bid) AS bid FROM bulletin";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxBid = Integer.parseInt(rs.getString("bid") == null ? "1" : rs.getString("bid").toString());
		    }
		      
		} catch (Exception ex) {
			logger.error(ex.toString());
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

		return maxBid;
	}
	
	
	
	/**
	 * 首頁上顯示最新的公告
	 * @return
	 */
	public static ArrayList<BulletinDataModel> latestBulletin() {

		ResultSet rsC = null;
		ArrayList<BulletinDataModel> dataC = new ArrayList<BulletinDataModel>();
		BulletinDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		Connection conn = null;
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();		    
		    
			sql = "SELECT a.bid, a.title_enUS, a.title_zhCN, a.title_zhTW, a.modifyDT FROM bulletin a" +
		    		" WHERE isShow = 1 ORDER BY a.modifyDT DESC LIMIT 0, 5";
		    
		    rsC = stmt.executeQuery(sql);
		
		    while(rsC.next()){
		    	obj = new BulletinDataModel();
		    	obj.setBid(Integer.parseInt(rsC.getString("bid") == null ? "0" : rsC.getString("bid")));
		    	obj.setTitle_enUS(rsC.getString("title_enUS") == null ? "" : rsC.getString("title_enUS"));
		    	obj.setTitle_zhCN(rsC.getString("title_zhCN") == null ? "" : rsC.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rsC.getString("title_zhTW") == null ? "" : rsC.getString("title_zhTW"));
		    	obj.setModifyDT(rsC.getString("modifyDT") == null ? "" : rsC.getString("modifyDT"));
		    	dataC.add(obj);
		    }
		      
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		} finally {
			if(rsC != null){
				try{ rsC.close(); } catch(Exception ex){}
				rsC = null;
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

		return dataC;
	}
	  
	
}