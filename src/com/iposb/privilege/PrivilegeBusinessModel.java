package com.iposb.privilege;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.sys.ConnectionManager;

public class PrivilegeBusinessModel {
	
	
	static Logger logger = Logger.getLogger(PrivilegeBusinessModel.class);
	
	private PrivilegeBusinessModel(){
	}
	
	
	public static ArrayList<PrivilegeDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		PrivilegeDataModel obj = null;

		try {
			
	    	obj = new PrivilegeDataModel();
	    	obj.setIid(Integer.parseInt(request.getParameter("iid") == null ? "0" : request.getParameter("iid")));
	    	obj.setName_enUS(request.getParameter("name_enUS") == null ? "" : request.getParameter("name_enUS"));
	    	obj.setName_zhCN(request.getParameter("name_zhCN") == null ? "" : request.getParameter("name_zhCN"));
	    	obj.setName_zhTW(request.getParameter("name_zhTW") == null ? "" : request.getParameter("name_zhTW"));
	    	obj.setIcon(request.getParameter("icon") == null ? "" : request.getParameter("icon"));
	    	obj.setLink(request.getParameter("link") == null ? "" : request.getParameter("link"));
	    	obj.setPriv0(Integer.parseInt(request.getParameter("priv0") == null ? "0" : request.getParameter("priv0").equals("") ? "0" : request.getParameter("priv0").equals("on") ? "1" : "0"));
	    	obj.setPriv1(Integer.parseInt(request.getParameter("priv1") == null ? "0" : request.getParameter("priv1").equals("") ? "0" : request.getParameter("priv1").equals("on") ? "1" : "0"));
	    	obj.setPriv2(Integer.parseInt(request.getParameter("priv2") == null ? "0" : request.getParameter("priv2").equals("") ? "0" : request.getParameter("priv2").equals("on") ? "1" : "0"));
	    	obj.setPriv3(Integer.parseInt(request.getParameter("priv3") == null ? "0" : request.getParameter("priv3").equals("") ? "0" : request.getParameter("priv3").equals("on") ? "1" : "0"));
	    	obj.setPriv4(Integer.parseInt(request.getParameter("priv4") == null ? "0" : request.getParameter("priv4").equals("") ? "0" : request.getParameter("priv4").equals("on") ? "1" : "0"));
	    	obj.setPriv5(Integer.parseInt(request.getParameter("priv5") == null ? "0" : request.getParameter("priv5").equals("") ? "0" : request.getParameter("priv5").equals("on") ? "1" : "0"));
	    	obj.setPriv6(Integer.parseInt(request.getParameter("priv6") == null ? "0" : request.getParameter("priv6").equals("") ? "0" : request.getParameter("priv6").equals("on") ? "1" : "0"));
	    	obj.setPriv7(Integer.parseInt(request.getParameter("priv7") == null ? "0" : request.getParameter("priv7").equals("") ? "0" : request.getParameter("priv7").equals("on") ? "1" : "0"));
	    	obj.setPriv8(Integer.parseInt(request.getParameter("priv8") == null ? "0" : request.getParameter("priv8").equals("") ? "0" : request.getParameter("priv8").equals("on") ? "1" : "0"));
	    	obj.setPriv9(Integer.parseInt(request.getParameter("priv9") == null ? "0" : request.getParameter("priv9").equals("") ? "0" : request.getParameter("priv9").equals("on") ? "1" : "0"));
	    	obj.setPriv99(Integer.parseInt(request.getParameter("priv99") == null ? "0" : request.getParameter("priv99").equals("") ? "0" : request.getParameter("priv99").equals("on") ? "1" : "0"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return data;
	}

	
	
	/**
	 * 管理員查看Privilege清單
	 * @param orderby
	 * @param pageNo
	 * @param category
	 * @return
	 */
	public static ArrayList<PrivilegeDataModel> privilegeList() {
		
		ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		PrivilegeDataModel obj = null;
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
			
			sql = "SELECT a.iid, a.name_enUS, a.name_zhCN, a.name_zhTW, a.icon, a.link," +
		    		" a.priv0, a.priv1, a.priv2, a.priv3, a.priv4, a.priv5, a.priv6, a.priv7, a.priv8, a.priv9, a.priv99," +
		    		" (SELECT COUNT(b.iid) FROM privilege b) AS total" +
		    		" FROM privilege a ORDER BY a.reorder ASC";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PrivilegeDataModel();
		    	obj.setIid(Integer.parseInt(rs.getString("iid") == null ? "0" : rs.getString("iid")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setIcon(rs.getString("icon") == null ? "" : rs.getString("icon"));     
		    	obj.setLink(rs.getString("link") == null ? "" : rs.getString("link"));      
		    	obj.setPriv0(Integer.parseInt(rs.getString("priv0") == null ? "0" : rs.getString("priv0")));
		    	obj.setPriv1(Integer.parseInt(rs.getString("priv1") == null ? "0" : rs.getString("priv1")));
		    	obj.setPriv2(Integer.parseInt(rs.getString("priv2") == null ? "0" : rs.getString("priv2")));
		    	obj.setPriv3(Integer.parseInt(rs.getString("priv3") == null ? "0" : rs.getString("priv3")));
		    	obj.setPriv4(Integer.parseInt(rs.getString("priv4") == null ? "0" : rs.getString("priv4")));
		    	obj.setPriv5(Integer.parseInt(rs.getString("priv5") == null ? "0" : rs.getString("priv5")));
		    	obj.setPriv6(Integer.parseInt(rs.getString("priv6") == null ? "0" : rs.getString("priv6")));
		    	obj.setPriv7(Integer.parseInt(rs.getString("priv7") == null ? "0" : rs.getString("priv7")));
		    	obj.setPriv8(Integer.parseInt(rs.getString("priv8") == null ? "0" : rs.getString("priv8")));
		    	obj.setPriv9(Integer.parseInt(rs.getString("priv9") == null ? "0" : rs.getString("priv9")));
		    	obj.setPriv99(Integer.parseInt(rs.getString("priv99") == null ? "0" : rs.getString("priv99")));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
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
	 * 詳情
	 * @param iid
	 * @return
	 */
	public static ArrayList<PrivilegeDataModel> privilegeDetails(String iid) {

		ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		PrivilegeDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
//		logger.info("---Admin is checking PRIVILEGE: " + iid + " (" + UtilsBusinessModel.timeNow() + ")");

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.iid, a.name_enUS, a.name_zhCN, a.name_zhTW, a.icon, a.link," +
		    		" a.priv0, a.priv1, a.priv2, a.priv3, a.priv4, a.priv5, a.priv6, a.priv7, a.priv8, a.priv9, a.priv99" +
		    		" FROM privilege a";
		    
		    if(iid != null && iid != ""){
		    	sql += " WHERE a.iid = " + iid;
		    }
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PrivilegeDataModel();
		    	obj.setIid(Integer.parseInt(rs.getString("iid") == null ? "0" : rs.getString("iid")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setIcon(rs.getString("icon") == null ? "" : rs.getString("icon"));     
		    	obj.setLink(rs.getString("link") == null ? "" : rs.getString("link"));      
		    	obj.setPriv0(Integer.parseInt(rs.getString("priv0") == null ? "0" : rs.getString("priv0")));
		    	obj.setPriv1(Integer.parseInt(rs.getString("priv1") == null ? "0" : rs.getString("priv1")));
		    	obj.setPriv2(Integer.parseInt(rs.getString("priv2") == null ? "0" : rs.getString("priv2")));
		    	obj.setPriv3(Integer.parseInt(rs.getString("priv3") == null ? "0" : rs.getString("priv3")));
		    	obj.setPriv4(Integer.parseInt(rs.getString("priv4") == null ? "0" : rs.getString("priv4")));
		    	obj.setPriv5(Integer.parseInt(rs.getString("priv5") == null ? "0" : rs.getString("priv5")));
		    	obj.setPriv6(Integer.parseInt(rs.getString("priv6") == null ? "0" : rs.getString("priv6")));
		    	obj.setPriv7(Integer.parseInt(rs.getString("priv7") == null ? "0" : rs.getString("priv7")));
		    	obj.setPriv8(Integer.parseInt(rs.getString("priv8") == null ? "0" : rs.getString("priv8")));
		    	obj.setPriv9(Integer.parseInt(rs.getString("priv9") == null ? "0" : rs.getString("priv9")));
		    	obj.setPriv99(Integer.parseInt(rs.getString("priv99") == null ? "0" : rs.getString("priv99")));
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
	 * 新增 privilege
	 * @param data
	 * @return
	 */
	public static String addNewPrivilege(ArrayList<PrivilegeDataModel> data) {

		
		PrivilegeDataModel privilegeData = new PrivilegeDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			privilegeData = (PrivilegeDataModel)data.get(0);
			
			int reorder = getMaxIid();
			reorder = reorder + 1;
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO privilege(name_enUS, name_zhCN, name_zhTW, icon, link," +
						" priv0, priv1, priv2, priv3, priv4, priv5, priv6, priv7, priv8, priv9, priv99, reorder) " +
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	
				pstmt.setString(1, privilegeData.getName_enUS());
				pstmt.setString(2, privilegeData.getName_zhCN());
				pstmt.setString(3, privilegeData.getName_zhTW());
				pstmt.setString(4, privilegeData.getIcon());
				pstmt.setString(5, privilegeData.getLink());
				pstmt.setInt(6, privilegeData.getPriv0());
				pstmt.setInt(7, privilegeData.getPriv1());
				pstmt.setInt(8, privilegeData.getPriv2());
				pstmt.setInt(9, privilegeData.getPriv3());
				pstmt.setInt(10, privilegeData.getPriv4());
				pstmt.setInt(11, privilegeData.getPriv5());
				pstmt.setInt(12, privilegeData.getPriv6());
				pstmt.setInt(13, privilegeData.getPriv7());
				pstmt.setInt(14, privilegeData.getPriv8());
				pstmt.setInt(15, privilegeData.getPriv9());
				pstmt.setInt(16, privilegeData.getPriv99());
				pstmt.setInt(17, reorder);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			
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

	
	
	/**
	 * 更新內容
	 * @param data
	 * @return
	 */
	public static String updatePrivilege(ArrayList<PrivilegeDataModel> data) {

		String result = "";
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		PrivilegeDataModel privilegeData = new PrivilegeDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			privilegeData = (PrivilegeDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE privilege SET name_enUS='"+privilegeData.getName_enUS()+"'," +
						" name_zhCN='"+privilegeData.getName_zhCN()+"', name_zhTW='"+privilegeData.getName_zhTW()+"'," +
						" icon='"+privilegeData.getIcon()+"', link='"+privilegeData.getLink()+"'," +
						" priv0="+privilegeData.getPriv0()+", priv1="+privilegeData.getPriv1()+"," +
						" priv2="+privilegeData.getPriv2()+", priv3="+privilegeData.getPriv3()+"," +
						" priv4="+privilegeData.getPriv4()+", priv5="+privilegeData.getPriv5()+"," +
						" priv6="+privilegeData.getPriv6()+", priv7="+privilegeData.getPriv7()+"," +
						" priv8="+privilegeData.getPriv8()+", priv9="+privilegeData.getPriv9()+", priv99="+privilegeData.getPriv99()+
						" WHERE iid=" +privilegeData.getIid();
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
	 * 取得最大(最新)的IID
	 * @return
	 */
	public static int getMaxIid() {

		int maxIid = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }
			
			stmt = conn.createStatement();

		    sql = "SELECT MAX(iid) AS iid FROM privilege";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxIid = Integer.parseInt(rs.getString("iid") == null ? "1" : rs.getString("iid").toString());
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

		return maxIid;
	}
	
	
	/**
	 * DB Control Panel 根據privilege顯示 item
	 * @param privilege
	 * @return
	 */
	public static ArrayList<PrivilegeDataModel> getItemsByPrivilege(int privilege) {
		
		ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		PrivilegeDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			sql = "SELECT a.iid, a.name_enUS, a.name_zhCN, a.name_zhTW, a.icon, a.link," +
		    		" a.priv0, a.priv1, a.priv2, a.priv3, a.priv4, a.priv5, a.priv6, a.priv7, a.priv8, a.priv9, a.priv99" +
		    		" FROM privilege a WHERE a.priv"+privilege+" = 1 ORDER BY a.reorder ASC";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PrivilegeDataModel();
		    	obj.setIid(Integer.parseInt(rs.getString("iid") == null ? "0" : rs.getString("iid")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setIcon(rs.getString("icon") == null ? "" : rs.getString("icon"));     
		    	obj.setLink(rs.getString("link") == null ? "" : rs.getString("link"));      
		    	obj.setPriv0(Integer.parseInt(rs.getString("priv0") == null ? "0" : rs.getString("priv0")));
		    	obj.setPriv1(Integer.parseInt(rs.getString("priv1") == null ? "0" : rs.getString("priv1")));
		    	obj.setPriv2(Integer.parseInt(rs.getString("priv2") == null ? "0" : rs.getString("priv2")));
		    	obj.setPriv3(Integer.parseInt(rs.getString("priv3") == null ? "0" : rs.getString("priv3")));
		    	obj.setPriv4(Integer.parseInt(rs.getString("priv4") == null ? "0" : rs.getString("priv4")));
		    	obj.setPriv5(Integer.parseInt(rs.getString("priv5") == null ? "0" : rs.getString("priv5")));
		    	obj.setPriv6(Integer.parseInt(rs.getString("priv6") == null ? "0" : rs.getString("priv6")));
		    	obj.setPriv7(Integer.parseInt(rs.getString("priv7") == null ? "0" : rs.getString("priv7")));
		    	obj.setPriv8(Integer.parseInt(rs.getString("priv8") == null ? "0" : rs.getString("priv8")));
		    	obj.setPriv9(Integer.parseInt(rs.getString("priv9") == null ? "0" : rs.getString("priv9")));
		    	obj.setPriv99(Integer.parseInt(rs.getString("priv99") == null ? "0" : rs.getString("priv99")));
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
	 * 檢查是否有該項目的使用權限
	 * @param iid
	 * @param privilege
	 * @return
	 */
	public static boolean determinePrivilege(int iid, int privilege) {
		if(privilege<1 || privilege>99) return false;
		
		ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		boolean priv = false; 
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return false;
	        }

			stmt = conn.createStatement();
			
			sql = "SELECT a.iid, a.name_enUS FROM privilege a WHERE a.priv"+privilege+" = 1 AND a.iid = " + iid;
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	priv = true;
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

		return priv;
	}
	
	
	/**
	 * 更新內容
	 * @param data
	 * @return
	 */
	public static String ajaxUpdateReorder(String reorder) {

		String result = "";
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		String[] split;
		String row = "1";
		String iid = "0";
		
		

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			stmt = conn.createStatement();
			
			split = reorder.split(",");
			
			for(int i = 0; i < split.length; i++){
				
				row = split[i].substring(0, split[i].indexOf("-"));
				iid = split[i].substring(split[i].indexOf("-") +1, split[i].length());
				
				sql = " UPDATE privilege SET reorder=" + row +
					  " WHERE iid=" + iid;
				stmt.executeUpdate(sql);
			}
			
			

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
		
		return result;
	}


}