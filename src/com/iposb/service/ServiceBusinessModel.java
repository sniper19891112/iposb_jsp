package com.iposb.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.iposb.sys.ConnectionManager;

import org.apache.log4j.Logger;



public class ServiceBusinessModel {

	
	static Logger logger = Logger.getLogger(ServiceBusinessModel.class);
	
	private ServiceBusinessModel(){
	}
	
	
	public static ArrayList<ServiceDataModel> setRequestData(HttpServletRequest request, String userId) {

		ArrayList<ServiceDataModel> data = new ArrayList<ServiceDataModel>();
		ServiceDataModel obj = null;

		try {
			
	    	obj = new ServiceDataModel();
	    	obj.setSid(1);
	    	obj.setTitle1(request.getParameter("title1") == null ? "" : request.getParameter("title1"));
	    	obj.setIntro1(request.getParameter("intro1") == null ? "" : request.getParameter("intro1"));
	    	obj.setContent1(request.getParameter("content1") == null ? "" : request.getParameter("content1"));
	    	obj.setTitle2(request.getParameter("title2") == null ? "" : request.getParameter("title2"));
	    	obj.setIntro2(request.getParameter("intro2") == null ? "" : request.getParameter("intro2"));
	    	obj.setContent2(request.getParameter("content2") == null ? "" : request.getParameter("content2"));
	    	obj.setTitle3(request.getParameter("title3") == null ? "" : request.getParameter("title3"));
	    	obj.setIntro3(request.getParameter("intro3") == null ? "" : request.getParameter("intro3"));
	    	obj.setContent3(request.getParameter("content3") == null ? "" : request.getParameter("content3"));
	    	obj.setModifier(userId);
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error("---Error Service (" + ex.toString() + ")");
			ex.printStackTrace();
		}

		return data;
	}
	
	
	
	
	/**
	 * 訪客查看內容
	 * 
	 * @return
	 */
	public static ArrayList<ServiceDataModel> viewService() {

		ArrayList<ServiceDataModel> data = new ArrayList<ServiceDataModel>();
		ServiceDataModel obj = null;
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

		    sql = "SELECT * FROM service";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new ServiceDataModel();
		    	obj.setSid(Integer.parseInt(rs.getString("sid") == null ? "0" : rs.getString("sid")));
		    	obj.setTitle1(rs.getString("title1") == null ? "" : rs.getString("title1")); 
		    	obj.setIntro1(rs.getString("intro1") == null ? "" : rs.getString("intro1")); 
		    	obj.setContent1(rs.getString("content1") == null ? "" : rs.getString("content1")); 
		    	obj.setTitle2(rs.getString("title2") == null ? "" : rs.getString("title2")); 
		    	obj.setIntro2(rs.getString("intro2") == null ? "" : rs.getString("intro2")); 
		    	obj.setContent2(rs.getString("content2") == null ? "" : rs.getString("content2")); 
		    	obj.setTitle3(rs.getString("title3") == null ? "" : rs.getString("title3")); 
		    	obj.setIntro3(rs.getString("intro3") == null ? "" : rs.getString("intro3")); 
		    	obj.setContent3(rs.getString("content3") == null ? "" : rs.getString("content3")); 
		    	data.add(obj);
		    }
		    
		} catch (Exception ex) {
			logger.error("---Error Service (" + ex.toString() + ")");
			ex.printStackTrace();
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){
					logger.error("---Error Service (" + ex.toString() + ")");
				}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){
					logger.error("---Error Service (" + ex.toString() + ")");
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
	 * Header's service
	 * @return
	 */
	public static ArrayList<ServiceDataModel> viewServiceForHeader() {

		ArrayList<ServiceDataModel> data = new ArrayList<ServiceDataModel>();
		ServiceDataModel obj = null;
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

		    sql = "SELECT title1, intro1, title2, intro2, title3, intro3 FROM service";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new ServiceDataModel();
		    	obj.setTitle1(rs.getString("title1") == null ? "" : rs.getString("title1"));
		    	obj.setIntro1(rs.getString("intro1") == null ? "" : rs.getString("intro1"));
		    	obj.setTitle2(rs.getString("title2") == null ? "" : rs.getString("title2"));
		    	obj.setIntro2(rs.getString("intro2") == null ? "" : rs.getString("intro2"));
		    	obj.setTitle3(rs.getString("title3") == null ? "" : rs.getString("title3"));
		    	obj.setIntro3(rs.getString("intro3") == null ? "" : rs.getString("intro3"));
		    	data.add(obj);
		    }
		    
		} catch (Exception ex) {
			logger.error("---Error Service (" + ex.toString() + ")");
			ex.printStackTrace();
		} finally {
			if( rs != null){
				try{ rs.close(); } catch(Exception ex){
					logger.error("---Error Service (" + ex.toString() + ")");
				}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){
					logger.error("---Error Service (" + ex.toString() + ")");
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
	protected static ArrayList<ServiceDataModel> serviceDetails() {

		ArrayList<ServiceDataModel> data = new ArrayList<ServiceDataModel>();
		ServiceDataModel obj = null;
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

			sql = "SELECT * FROM service";
		    
		    rs = stmt.executeQuery(sql);
		    

		    while(rs.next()){
		    	obj = new ServiceDataModel();
		    	obj.setSid(Integer.parseInt(rs.getString("sid") == null ? "0" : rs.getString("sid")));
		    	obj.setTitle1(rs.getString("title1") == null ? "" : rs.getString("title1")); 
		    	obj.setIntro1(rs.getString("intro1") == null ? "" : rs.getString("intro1")); 
		    	obj.setContent1(rs.getString("content1") == null ? "" : rs.getString("content1")); 
		    	obj.setTitle2(rs.getString("title2") == null ? "" : rs.getString("title2")); 
		    	obj.setIntro2(rs.getString("intro2") == null ? "" : rs.getString("intro2")); 
		    	obj.setContent2(rs.getString("content2") == null ? "" : rs.getString("content2")); 
		    	obj.setTitle3(rs.getString("title3") == null ? "" : rs.getString("title3")); 
		    	obj.setIntro3(rs.getString("intro3") == null ? "" : rs.getString("intro3")); 
		    	obj.setContent3(rs.getString("content3") == null ? "" : rs.getString("content3"));
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	data.add(obj);
		    }
		} catch (Exception ex) {
			logger.error("---Error Service (" + ex.toString() + ")");
			ex.printStackTrace();
		} finally {
			if(rs != null){
				try{ rs.close(); } catch(Exception ex){
					logger.error("---Error Service (" + ex.toString() + ")");
				}
				rs = null;
	        }
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){
					logger.error("---Error Service (" + ex.toString() + ")");
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
	protected static String updateService(ArrayList data) {
		
		String sql = null;
		String result = "";
		Connection conn = null;
		Statement stmt = null;
		ServiceDataModel serviceDataData = new ServiceDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			serviceDataData = (ServiceDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				stmt = conn.createStatement();
				sql = "UPDATE service SET " +
					  " title1='"+serviceDataData.getTitle1()+"', " +
					  " intro1='"+serviceDataData.getIntro1()+"', " +
					  " content1='"+serviceDataData.getContent1()+"', " +
					  " title2='"+serviceDataData.getTitle2()+"', " +
					  " intro2='"+serviceDataData.getIntro2()+"', " +
					  " content2='"+serviceDataData.getContent2()+"', " +
					  " title3='"+serviceDataData.getTitle3()+"', " +
					  " intro3='"+serviceDataData.getIntro3()+"', " +
					  " content3='"+serviceDataData.getContent3()+"', " +
					  " ModifyDT=NOW(), modifier='"+serviceDataData.getModifier()+"' " +
			  		  " WHERE sid = 1";
//				logger.error(">>>>"+sql);
				stmt.executeUpdate(sql);

				result = "OK";
			}
			catch(Exception ex){
				logger.error("---Error Service (" + ex.toString() + ")");
				ex.printStackTrace();
				result = ex.toString();
			}
			finally {
				if(stmt != null){
					try{ stmt.close(); } catch(Exception ex){
						logger.error("---Error Service (" + ex.toString() + ")");
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
