package com.iposb.faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.sys.ConnectionManager;

public class FaqBusinessModel {
	

	static Logger logger = Logger.getLogger(FaqBusinessModel.class);
	
	private FaqBusinessModel(){
	}
	
	
	public static ArrayList<FaqDataModel> setRequestData(HttpServletRequest request) {

		FaqDataModel obj = null;
		ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();

		try {
			
	    	obj = new FaqDataModel();
	    	obj.setFid(Integer.parseInt(request.getParameter("fid") == null ? "0" : request.getParameter("fid")));
	    	obj.setCategory(request.getParameter("category") == null ? "" : request.getParameter("category"));
	    	obj.setTitle_enUS(request.getParameter("title_enUS") == null ? "" : request.getParameter("title_enUS"));
	    	obj.setTitle_zhCN(request.getParameter("title_zhCN") == null ? "" : request.getParameter("title_zhCN"));
	    	obj.setTitle_zhTW(request.getParameter("title_zhTW") == null ? "" : request.getParameter("title_zhTW"));
	    	obj.setContent_enUS(request.getParameter("content_enUS") == null ? "" : request.getParameter("content_enUS"));
	    	obj.setContent_zhCN(request.getParameter("content_zhCN") == null ? "" : request.getParameter("content_zhCN"));
	    	obj.setContent_zhTW(request.getParameter("content_zhTW") == null ? "" : request.getParameter("content_zhTW"));
	    	obj.setView(Integer.parseInt(request.getParameter("view") == null ? "0" : request.getParameter("view")));
	    	obj.setIsShow(Integer.parseInt(request.getParameter("isShow") == null ? "0" : request.getParameter("isShow").equals("on") ? "1" : "0"));
	    	obj.setCreator(request.getParameter("creator") == null ? "" : request.getParameter("creator"));
	    	obj.setModifier(request.getParameter("modifier") == null ? "" : request.getParameter("modifier"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return data;
	}
	
	
	public static ArrayList<FaqDataModel> searchFaq(String orderBy, int pageNo) {

		FaqDataModel obj = null;
		ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		String sqlCondition = "";
		ResultSet rs = null;
		
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

		    sql = "SELECT a.fid, a.category, a.title_enUS, a.title_zhCN, a.title_zhTW, a.content_enUS," +
		    		" a.content_zhCN, a.content_zhTW, a.view, a.isShow," +
		    		" (SELECT COUNT(b.fid) FROM faq b " + sqlCondition + ") AS total" +
		    		" FROM faq a" + sqlCondition + 
		    		" ORDER BY a." + orderBy + " DESC, a.fid ASC LIMIT " + page + ", " + numberPerPage;
		    
//		    logger.info("---User search FAQ");
		    
		    rs = stmt.executeQuery(sql);
		    
//		    data = setObjValue(rs);

		    while(rs.next()){
		    	obj = new FaqDataModel();
		    	obj.setFid(Integer.parseInt(rs.getString("fid") == null ? "0" : rs.getString("fid")));
		    	obj.setCategory(rs.getString("category") == null ? "" : rs.getString("category"));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setTitle_zhCN(rs.getString("title_zhCN") == null ? "" : rs.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rs.getString("title_zhTW") == null ? "" : rs.getString("title_zhTW"));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS"));     
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN"));      
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));
		    	obj.setView(Integer.parseInt(rs.getString("view") == null ? "0" : rs.getString("view")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
		    	data.add(obj);
		    }
//		    stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
//			obj = new FaqDataModel();
//			obj.setErrmsg(ex.toString());
//			data.add(obj);
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
	 * 管理員查看Faq清單
	 * @param orderby
	 * @param pageNo
	 * @param category
	 * @return
	 */
	public static ArrayList<FaqDataModel> faqList(String orderby, int pageNo, String category) {

		FaqDataModel obj = null;
		ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		String sqlCondition = "";

		
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

			sql = "SELECT a.fid, a.category, a.title_enUS, a.title_zhCN, a.title_zhTW, a.content_enUS," +
		    		" a.content_zhCN, a.content_zhTW, a.view, a.isShow, a.createDT, a.creator, a.modifyDT, a.modifier," +
		    		" (SELECT COUNT(b.fid) FROM faq b " + sqlCondition + ") AS total" +
		    		" FROM faq a " + sqlCondition;
		    
		    if(orderby!=null && !orderby.equals("")){
		    	sql += " ORDER BY a." + orderby + " ASC";
		    }
		    
//		    sql += " LIMIT " + page + ", " + numberPerPage;
		    
//		    logger.info("---Admin browse FaqList: " + sql);
		    
		    rs = stmt.executeQuery(sql);
		    
//		    data = setObjValue(rs);

		    while(rs.next()){
		    	obj = new FaqDataModel();
		    	obj.setFid(Integer.parseInt(rs.getString("fid") == null ? "0" : rs.getString("fid")));
		    	obj.setCategory(rs.getString("category") == null ? "" : rs.getString("category"));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setTitle_zhCN(rs.getString("title_zhCN") == null ? "" : rs.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rs.getString("title_zhTW") == null ? "" : rs.getString("title_zhTW"));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS"));     
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN"));      
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));
		    	obj.setView(Integer.parseInt(rs.getString("view") == null ? "0" : rs.getString("view")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
		    	data.add(obj);
		    }
		    
//		    stmt.close();
		      
		} catch (Exception ex) {
			ex.printStackTrace();
//			obj = new FaqDataModel();
//			obj.setErrmsg(ex.toString());
//			data.add(obj);
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
	 * @param fid
	 * @return
	 */
	public static ArrayList<FaqDataModel> faqDetails(String fid) {

		ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		FaqDataModel obj = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.fid, a.category, a.title_enUS, a.title_zhCN, a.title_zhTW, a.content_enUS," +
    			  " a.content_zhCN, a.content_zhTW, a.view, a.isShow, a.createDT, a.creator, a.modifyDT, a.modifier" +
		    	  " FROM faq a";
		    
		    if(fid != null && fid != ""){
		    	sql += " WHERE a.fid = " + fid;
		    }
		    
		    rs = stmt.executeQuery(sql);
		    
//		    data = setObjValue(rs);

		    while(rs.next()){
		    	obj = new FaqDataModel();
		    	obj.setFid(Integer.parseInt(rs.getString("fid") == null ? "0" : rs.getString("fid")));
		    	obj.setCategory(rs.getString("category") == null ? "" : rs.getString("category"));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setTitle_zhCN(rs.getString("title_zhCN") == null ? "" : rs.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rs.getString("title_zhTW") == null ? "" : rs.getString("title_zhTW"));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS"));     
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN"));      
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));
		    	obj.setView(Integer.parseInt(rs.getString("view") == null ? "0" : rs.getString("view")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	data.add(obj);
		    }
//		    stmt.close();
		      
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
	

	
	public static String addNewFaq(ArrayList data) {

		Connection conn = null;
		PreparedStatement pstmt = null;	
		
		FaqDataModel faqData = new FaqDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			faqData = (FaqDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO faq(category, title_enUS, title_zhCN, title_zhTW," +
						" content_enUS, content_zhCN, content_zhTW, view, isShow, createDT, creator) " +
//						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, DATE_ADD(NOW(), INTERVAL 13 HOUR), ?)");
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setString(1, faqData.getCategory());
				pstmt.setString(2, faqData.getTitle_enUS());
				pstmt.setString(3, faqData.getTitle_zhCN());
				pstmt.setString(4, faqData.getTitle_zhTW());
				pstmt.setString(5, faqData.getContent_enUS());
				pstmt.setString(6, faqData.getContent_zhCN());
				pstmt.setString(7, faqData.getContent_zhTW());
				pstmt.setInt(8, faqData.getView());
				pstmt.setInt(9, faqData.getIsShow());
				pstmt.setString(10, faqData.getCreator());	
				
				pstmt.executeUpdate();
				pstmt.clearParameters();				
//				pstmt.close();
			
			}
			catch(Exception ex){
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

	
	
	public static String updateFaq(ArrayList data) {

		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		String result = "";
		
		FaqDataModel faqData = new FaqDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			faqData = (FaqDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE faq SET category='"+faqData.getCategory()+"', title_enUS='"+faqData.getTitle_enUS()+"'," +
					  " title_zhCN='"+faqData.getTitle_zhCN()+"', title_zhTW='"+faqData.getTitle_zhTW()+"'," +
					  " content_enUS='"+faqData.getContent_enUS()+"', content_zhCN='"+faqData.getContent_zhCN()+"', content_zhTW='"+faqData.getContent_zhTW()+"'," +
					  " view="+faqData.getView()+", isShow="+faqData.getIsShow()+"," +
//					  " ModifyDT=DATE_ADD(NOW(), INTERVAL 13 HOUR), modifier='"+faqData.getModifier()+"' WHERE fid=" +faqData.getFid();
					  " ModifyDT=NOW(), modifier='"+faqData.getModifier()+"' WHERE fid=" +faqData.getFid();
				stmt.executeUpdate(sql);

				result = "OK";
			}
			catch(Exception ex){
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
	 * 取得最大(最新)的TID
	 * @return
	 */
	public static int getMaxFid() {
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		int maxFid = 0;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }
			
			stmt = conn.createStatement();

		    sql = "SELECT MAX(fid) AS fid FROM faq";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxFid = Integer.parseInt(rs.getString("fid") == null ? "1" : rs.getString("fid").toString());
		    }
//		    stmt.close();
		      
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

		return maxFid;
	}
	  
	
	/**
	 * 把資料庫得到的值設入 data 裡
	 * @param rs
	 * @return
	 */
	/*
	private static ArrayList<FaqDataModel> setObjValue(ResultSet rs) {
//		FaqDataModel obj = null;
//		ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		
		data = new ArrayList<FaqDataModel>();
		
	    try {
			
	    	while(rs.next()){
		    	obj = new FaqDataModel();
		    	obj.setFid(Integer.parseInt(rs.getString("fid") == null ? "0" : rs.getString("fid")));
		    	obj.setCategory(rs.getString("category") == null ? "" : rs.getString("category"));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setTitle_zhCN(rs.getString("title_zhCN") == null ? "" : rs.getString("title_zhCN"));  
		    	obj.setTitle_zhTW(rs.getString("title_zhTW") == null ? "" : rs.getString("title_zhTW"));
		    	obj.setContent_enUS(rs.getString("content_enUS") == null ? "" : rs.getString("content_enUS"));     
		    	obj.setContent_zhCN(rs.getString("content_zhCN") == null ? "" : rs.getString("content_zhCN"));      
		    	obj.setContent_zhTW(rs.getString("content_zhTW") == null ? "" : rs.getString("content_zhTW"));
		    	obj.setView(Integer.parseInt(rs.getString("view") == null ? "0" : rs.getString("view")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
		    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	data.add(obj);
		    }
		    
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    return data;
	}
	*/
	
}