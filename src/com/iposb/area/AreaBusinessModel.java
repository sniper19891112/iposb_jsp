package com.iposb.area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.i18n.Resource;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.UtilsBusinessModel;

public class AreaBusinessModel {
	

	static Logger logger = Logger.getLogger(AreaBusinessModel.class);
	
	private AreaBusinessModel(){
	}
	
	
	protected static ArrayList<AreaDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		AreaDataModel obj = null;

		try {
			
	    	obj = new AreaDataModel();
	    	obj.setAid(Integer.parseInt(request.getParameter("aid") == null ? "0" : request.getParameter("aid")));
	    	obj.setZid(Integer.parseInt(request.getParameter("zid") == null ? "0" : request.getParameter("zid")));
	    	obj.setName_enUS(request.getParameter("name_enUS") == null ? "" : request.getParameter("name_enUS"));
	    	obj.setName_zhCN(request.getParameter("name_zhCN") == null ? "" : request.getParameter("name_zhCN"));
	    	obj.setName_zhTW(request.getParameter("name_zhTW") == null ? "" : request.getParameter("name_zhTW"));
	    	obj.setCode(request.getParameter("code") == null ? "" : request.getParameter("code"));
//	    	obj.setFreightType(Integer.parseInt(request.getParameter("freightType") == null ? "0" : request.getParameter("freightType")));
	    	obj.setCutoff(request.getParameter("cutoff") == null ? "" : request.getParameter("cutoff"));
	    	obj.setIsMajor(Integer.parseInt(request.getParameter("isMajor") == null ? "0" : request.getParameter("isMajor").equals("on") ? "1" : "0"));
	    	obj.setBelongArea(Integer.parseInt(request.getParameter("belongArea") == null ? "0" : request.getParameter("belongArea") == "" ? "0" : request.getParameter("belongArea")));
	    	obj.setState(request.getParameter("state") == null ? "" : request.getParameter("state"));
	    	obj.setIsShow(Integer.parseInt(request.getParameter("isShow") == null ? "0" : request.getParameter("isShow").equals("on") ? "1" : "0"));
//	    	obj.setCreator(request.getParameter("creator") == null ? "" : request.getParameter("creator"));
//	    	obj.setModifier(request.getParameter("modifier") == null ? "" : request.getParameter("modifier"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}

		return data;
	}
	
	
	
	/**
	 * 後台所有area的清單 - 供管理員查看
	 * 
	 * @return
	 */
	public static ArrayList<AreaDataModel> allArea() {
		
		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		AreaDataModel obj = null;
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

		    sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.aid) FROM area b) AS total" +
		    		" FROM area a ORDER BY a.aid DESC";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new AreaDataModel();
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType") == null ? "0" : rs.getString("freightType")));
		    	obj.setIsMajor(Integer.parseInt(rs.getString("isMajor") == null ? "0" : rs.getString("isMajor")));
		    	obj.setBelongArea(Integer.parseInt(rs.getString("belongArea") == null ? "0" : rs.getString("belongArea")));
		    	obj.setState(rs.getString("state") == null ? "" : rs.getString("state"));
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
	 * 做成下拉式選單 - 不分頁、不顯示隱藏的
	 * 
	 * @return
	 */
	public static ArrayList<AreaDataModel> areaList() {

		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		AreaDataModel obj = null;
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

			sql = "SELECT * FROM area WHERE isShow = 1 ORDER BY name_enUS ASC";
		    
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new AreaDataModel();
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
		    	obj.setIsMajor(Integer.parseInt(rs.getString("isMajor") == null ? "0" : rs.getString("isMajor")));
		    	obj.setBelongArea(Integer.parseInt(rs.getString("belongArea") == null ? "0" : rs.getString("belongArea")));
		    	obj.setState(rs.getString("state") == null ? "" : rs.getString("state"));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
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
	 * @param aid
	 * @return
	 */
	public static ArrayList<AreaDataModel> areaDetails(String aid) {

		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		AreaDataModel obj = null;
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

			sql = "SELECT a.* FROM area a WHERE a.aid = " + aid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new AreaDataModel();
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType") == null ? "0" : rs.getString("freightType")));
		    	obj.setIsMajor(Integer.parseInt(rs.getString("isMajor") == null ? "0" : rs.getString("isMajor")));
		    	obj.setBelongArea(Integer.parseInt(rs.getString("belongArea") == null ? "0" : rs.getString("belongArea")));
		    	obj.setState(rs.getString("state") == null ? "" : rs.getString("state"));
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
	 * 新增 Area
	 * @param data
	 * @return
	 */
	public static String addNewArea(ArrayList<AreaDataModel> data, String userId) {

		
		AreaDataModel areaData = new AreaDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			areaData = (AreaDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO area (name_enUS, name_zhCN, name_zhTW, code, isMajor, belongArea, state, isShow, createDT, creator) " +
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setString(1, areaData.getName_enUS());
				pstmt.setString(2, areaData.getName_zhCN());
				pstmt.setString(3, areaData.getName_zhTW());
				pstmt.setString(4, areaData.getCode());
				pstmt.setInt(5, areaData.getIsMajor());
				pstmt.setInt(6, areaData.getBelongArea());
				pstmt.setString(7, areaData.getState());
				pstmt.setInt(8, areaData.getIsShow());
				pstmt.setString(9, userId);	
				
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
	public static String updateArea(ArrayList<AreaDataModel> data, String userId) {

		String result = "";
		
		AreaDataModel areaData = new AreaDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			areaData = (AreaDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE area SET name_enUS='"+areaData.getName_enUS()+"'," +
					  " name_zhCN='"+areaData.getName_zhCN()+"', name_zhTW='"+areaData.getName_zhTW()+"', code='"+areaData.getCode()+"'," +
					  " isMajor="+ areaData.getIsMajor() +", belongArea="+ areaData.getBelongArea() +", state='"+areaData.getState()+"'," +
					  " isShow="+areaData.getIsShow()+", ModifyDT=NOW(), modifier='"+userId+"' WHERE aid=" +areaData.getAid();
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
	 * 取得最大(最新)的AID
	 * @return
	 */
	public static int getMaxAid() {

		int maxAid = 0;
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

		    sql = "SELECT MAX(aid) AS aid FROM area";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxAid = Integer.parseInt(rs.getString("aid") == null ? "1" : rs.getString("aid").toString());
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

		return maxAid;
	}
	


	/**
	 * 解析代碼，找到對應的編號
	 * 
	 * @param txt
	 * @return
	 */
	public static int parseAreaAid(String txt) {

		int aid = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return -1;
	        }

			stmt = conn.createStatement();

			sql = "SELECT aid FROM area WHERE code = '" + txt + "'";

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	aid = Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid"));
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

		return aid;
		
	}
	
	
	/**
	 * 解析編號，找到對應的代碼
	 * 
	 * @param txt
	 * @return
	 */
	public static String parseAreaCode(int aid) {

		String code = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();

			sql = "SELECT code FROM area WHERE aid = " + aid;

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	code = rs.getString("code") == null ? "" : rs.getString("code");
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

		return code;
		
	}
	
	
	/**
	 * 解析編號，找到對應的文字
	 * 
	 * @param txt
	 * @return
	 */
	public static String parseAreaName(int aid) {

		String areaname = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();

			sql = "SELECT name_enUS FROM area WHERE aid = " + aid;

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	areaname = rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS");
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

		return areaname;
		
	}
	
	
	/**
	 * 同時解析2組編號，找到對應的文字
	 * 
	 * @param txt
	 * @return
	 */
	public static String parse2AreaName(int from, int to) {

		String areaname = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();

			sql = "SELECT (SELECT name_enUS FROM area WHERE aid = "+from+") AS origin, (SELECT name_enUS FROM area WHERE aid = "+to+") AS destination";

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	String origin = rs.getString("origin") == null ? "?" : rs.getString("origin");
		    	String destination = rs.getString("destination") == null ? "?" : rs.getString("destination");
		    	areaname = origin + " - " + destination;
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

		return areaname;
		
	}
	
	
	
	/**
	 * 同時解析2組編號，找到對應的代碼
	 * 
	 * @param txt
	 * @return
	 */
	public static String parse2AreaCode(int from, int to) {

		String areacode = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();

			sql = "SELECT (SELECT code FROM area WHERE aid = "+from+") AS origin, (SELECT code FROM area WHERE aid = "+to+") AS destination";

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	String origin = rs.getString("origin") == null ? "?" : rs.getString("origin");
		    	String destination = rs.getString("destination") == null ? "?" : rs.getString("destination");
		    	areacode = origin + " - " + destination;
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

		return areacode;
		
	}
	
	

	/**
	 * 後台所有zone的清單 - 供管理員查看
	 * 
	 * @return
	 */
	public static ArrayList<AreaDataModel> allZone() {
		
		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		AreaDataModel obj = null;
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

		    sql = "SELECT a.*," +
		    		" (SELECT b.name_enUS FROM area b WHERE b.aid = a.aid) AS areaName," +
		    		" (SELECT b.code FROM area b WHERE b.aid = a.aid) AS areaCode," +
		    		" (SELECT COUNT(b.zid) FROM zone b) AS total" +
		    		" FROM zone a " +
		    		" LEFT JOIN area b ON b.aid = a.aid" +
		    		" ORDER BY a.aid ASC";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new AreaDataModel();
		    	obj.setZid(Integer.parseInt(rs.getString("zid") == null ? "0" : rs.getString("zid")));
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setSerial(Integer.parseInt(rs.getString("serial") == null ? "0" : rs.getString("serial")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
		    	obj.setCutoff(rs.getString("cutoff") == null ? "" : rs.getString("cutoff"));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setAreaName(rs.getString("areaName") == null ? "" : rs.getString("areaName"));
		    	obj.setAreaCode(rs.getString("areaCode") == null ? "" : rs.getString("areaCode"));
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
	 * 根據所屬地區，做成下拉式選單 - 不分頁、不顯示隱藏的
	 * 
	 * @return
	 */
	public static ArrayList<AreaDataModel> zoneList(int aid) {

		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		AreaDataModel obj = null;
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

			sql = "SELECT * FROM zone WHERE aid = "+aid+" AND isShow = 1 ORDER BY name_enUS ASC";
		    
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new AreaDataModel();
		    	obj.setZid(Integer.parseInt(rs.getString("zid") == null ? "0" : rs.getString("zid")));
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setSerial(Integer.parseInt(rs.getString("serial") == null ? "0" : rs.getString("serial")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
		    	obj.setCutoff(rs.getString("cutoff") == null ? "" : rs.getString("cutoff"));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
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
	 * @param zid
	 * @return
	 */
	public static ArrayList<AreaDataModel> zoneDetails(String zid) {

		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		AreaDataModel obj = null;
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

			sql = "SELECT a.* FROM zone a WHERE a.zid = " + zid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new AreaDataModel();
		    	obj.setZid(Integer.parseInt(rs.getString("zid") == null ? "0" : rs.getString("zid")));
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setSerial(Integer.parseInt(rs.getString("serial") == null ? "0" : rs.getString("serial")));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS"));
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN"));  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW"));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
		    	obj.setCutoff(rs.getString("cutoff") == null ? "" : rs.getString("cutoff"));
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
	 * 新增 Zone
	 * @param data
	 * @param serial
	 * @return
	 */
	public static String addNewZone(ArrayList<AreaDataModel> data, String userId, int serial) {

		
		AreaDataModel areaData = new AreaDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			areaData = (AreaDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO zone (aid, serial, name_enUS, name_zhCN, name_zhTW, code, cutoff, isShow, createDT, creator) " +
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setInt(1, areaData.getAid());
				pstmt.setInt(2, serial);
				pstmt.setString(3, areaData.getName_enUS());
				pstmt.setString(4, areaData.getName_zhCN());
				pstmt.setString(5, areaData.getName_zhTW());
				pstmt.setString(6, areaData.getCode());
				pstmt.setString(7, areaData.getCutoff());
				pstmt.setInt(8, areaData.getIsShow());
				pstmt.setString(9, userId);	
				
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
	public static String updateZone(ArrayList<AreaDataModel> data, String userId) {

		String result = "";
		
		AreaDataModel areaData = new AreaDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			areaData = (AreaDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE zone SET aid="+areaData.getAid()+", name_enUS='"+areaData.getName_enUS()+"'," +
					  " name_zhCN='"+areaData.getName_zhCN()+"', name_zhTW='"+areaData.getName_zhTW()+"'," +
					  " code='"+areaData.getCode()+"', cutoff='"+areaData.getCutoff()+"', isShow="+areaData.getIsShow()+"," +
					  " modifyDT=NOW(), modifier='"+userId+"' WHERE zid=" +areaData.getZid();
				
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
	 * 取得最大(最新)的ZID
	 * @return
	 */
	public static int getMaxZid() {

		int maxZid = 0;
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

		    sql = "SELECT MAX(zid) AS zid FROM zone";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxZid = Integer.parseInt(rs.getString("zid") == null ? "1" : rs.getString("zid").toString());
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

		return maxZid;
	}
	
	
	/**
	 * 取得最大(最新)的Zone下 area的serial
	 * 
	 * @param aid 
	 * @return
	 */
	public static int getMaxAreaSerial(int aid) {

		int maxSerial = 0;
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

		    sql = "SELECT MAX(serial+1) AS serial FROM zone WHERE aid = " + aid;
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxSerial = Integer.parseInt(rs.getString("serial") == null ? "1" : rs.getString("serial").toString());
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

		return maxSerial;
	}
	

//
//	/**
//	 * 解析代碼，找到對應的編號
//	 * 
//	 * @param txt
//	 * @return
//	 */
//	public static int parseZoneZid(String txt) {
//
//		int zid = 0;
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		String sql = null;
//		
//
//		try {
//			
//			//DB connection
//			conn = ConnectionManager.getConnection();
//			if(conn == null) {
//				logger.fatal("*** NO connection");
//				return -1;
//	        }
//
//			stmt = conn.createStatement();
//
//			sql = "SELECT zid FROM zone WHERE code = '" + txt + "'";
//
//		    rs = stmt.executeQuery(sql);
//
//		    while(rs.next()){
//		    	zid = Integer.parseInt(rs.getString("zid") == null ? "0" : rs.getString("zid"));
//		    }
//		      
//		} catch (Exception ex) {
//			logger.error(ex.toString());
//			ex.printStackTrace();
//		} finally {
//			if(rs != null){
//				try{ rs.close(); } catch(Exception ex){}
//				rs = null;
//	        }
//			if(stmt != null){
//				try{ stmt.close(); } catch(Exception ex){}
//				stmt = null;
//			}
//			if(conn != null){
//				ConnectionManager.closeConnection(conn);
//				conn = null;
//			}
//		}
//
//		return zid;
//		
//	}
//	
//	
//	/**
//	 * 解析編號，找到對應的代碼
//	 * 
//	 * @param txt
//	 * @return
//	 */
//	public static String parseZoneCode(int zid) {
//
//		String code = "";
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		String sql = null;
//		
//
//		try {
//			
//			//DB connection
//			conn = ConnectionManager.getConnection();
//			if(conn == null) {
//				logger.fatal("*** NO connection");
//				return "";
//	        }
//
//			stmt = conn.createStatement();
//
//			sql = "SELECT code FROM zone WHERE zid = " + zid;
//
//		    rs = stmt.executeQuery(sql);
//
//		    while(rs.next()){
//		    	code = rs.getString("code") == null ? "" : rs.getString("code");
//		    }
//		      
//		} catch (Exception ex) {
//			logger.error(ex.toString());
//			ex.printStackTrace();
//		} finally {
//			if(rs != null){
//				try{ rs.close(); } catch(Exception ex){}
//				rs = null;
//	        }
//			if(stmt != null){
//				try{ stmt.close(); } catch(Exception ex){}
//				stmt = null;
//			}
//			if(conn != null){
//				ConnectionManager.closeConnection(conn);
//				conn = null;
//			}
//		}
//
//		return code;
//		
//	}
	
	
	/**
	 * 解析編號，找到對應的文字
	 * 
	 * @param txt
	 * @return
	 */
	public static String parseZoneName(int zid) {

		String areaname = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();

			sql = "SELECT name_enUS FROM zone WHERE zid = " + zid;

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	areaname = rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS");
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

		return areaname;
		
	}
	
	
	/**
	 * 根據 aid 取得該地區下的所有 zone
	 * 
	 * @param aid
	 * @param locale
	 * @return
	 */
	protected static String getZoneDropdownlistByArea (int aid, String locale) {

		String sql = "";
		String result = "";
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "noConnection";
	        }

			stmt = conn.createStatement();
			
			sql = "SELECT a.* FROM zone a WHERE a.aid = " + aid + " AND a.isShow = 1 ORDER BY a.name_enUS ASC";
			
		    rs = stmt.executeQuery(sql);
		    
		    result = "<option value=\"\" cutoff=\"\"></option>";

		    while(rs.next()){
		    	String zid = rs.getString("zid").toString();
		    	String zonename = rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS").toString();
		    	String cutoff = rs.getString("cutoff") == null ? "" : rs.getString("cutoff").toString();
		    	result += "<option value=\""+zid+"\" cutoff=\""+cutoff+"\">"+zonename+"</option>";
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
	 * 依據aid查詢該area所屬的zone
	 * 
	 * @param aid
	 * @return
	 */
	public static ArrayList<AreaDataModel> getZoneByAid(String aid) {

		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		ResultSet rs = null;
		AreaDataModel obj = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.* FROM zone a WHERE a.aid = " + aid + " AND a.isShow = 1 ORDER BY a.name_enUS ASC";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new AreaDataModel();
		    	obj.setZid(Integer.parseInt(rs.getString("zid") == null ? "0" : rs.getString("zid").toString()));
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid").toString()));
		    	obj.setSerial(Integer.parseInt(rs.getString("serial") == null ? "0" : rs.getString("serial").toString()));
		    	obj.setName_enUS(rs.getString("name_enUS") == null ? "" : rs.getString("name_enUS").toString());
		    	obj.setName_zhCN(rs.getString("name_zhCN") == null ? "" : rs.getString("name_zhCN").toString());  
		    	obj.setName_zhTW(rs.getString("name_zhTW") == null ? "" : rs.getString("name_zhTW").toString());
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
	 * consignment 管理 - 更改 zone
	 * 
	 * @param aid
	 * @return
	 */
	public static String getEditableOption(String aid){
		
		String option = "[";
		
		ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
    	data = getZoneByAid(aid);

    	AreaDataModel areaData = new AreaDataModel();
    	if(data != null){
    		
    		for(int i = 0; i < data.size(); i++){
    			areaData = data.get(i);
    			String zoneName = areaData.getName_enUS();
    			
    			if(!zoneName.equals("")){
        			option += "{value: '" + areaData.getZid() + "', text: '" + zoneName + "'},";
    			}
    		}
    		
    	}
    	option += "]";
		return option;
	}
	
	
	
	  
	
}