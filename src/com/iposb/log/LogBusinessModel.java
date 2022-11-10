package com.iposb.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.i18n.Resource;
import com.iposb.notification.NotificationDataModel;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.IPTool;


public class LogBusinessModel {

	
	private LogBusinessModel(){
	}
	
	static Logger logger = Logger.getLogger(LogBusinessModel.class);
	
	
	/**
	 * 查看訪客搜尋過的關鍵字
	 * @return
	 */
	public static ArrayList<LogDataModel> querySearchLog(HttpServletRequest request, String orderBy) {

		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		LogDataModel obj = null;
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

		    sql = "SELECT a.keyword, a.IP, a.searchDT" +
		    		" FROM log_search a ORDER BY a." + orderBy + " DESC";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogDataModel();
		    	
		    	String IP = rs.getString("IP") == null ? "" : rs.getString("IP");
		    	String flagStr = IPTool.countryFlag(request, IP);
		    	
		    	obj.setKeyword(rs.getString("keyword") == null ? "" : rs.getString("keyword"));
		    	obj.setIP(IP);
		    	obj.setCountry(flagStr);
		    	obj.setSearchDT(rs.getString("searchDT") == null ? "" : rs.getString("searchDT"));
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
	 * 把訪客搜尋的關鍵字儲存到DB內
	 * @param keyword
	 * @param IP
	 * @return
	 */
	public static void insertSearchLog(String keyword, HttpServletRequest request) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			String IP = IPTool.getUserIP(request);

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			pstmt = conn.prepareStatement("INSERT INTO log_search (keyword, IP, searchDT) " +
					"VALUE " + "(?, ?, NOW())");

			pstmt.setString(1, keyword);
			pstmt.setString(2, IP);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			logger.info("*** User search keyword: " + keyword);
		
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
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
	
	
	/**
	 * 查詢關於該consignmentNo的所有admin處理記錄
	 * 
	 * @param bookingCode
	 * @param bookBy
	 * @return
	 */
	protected static ArrayList<LogDataModel> queryCPConsignmentLog(String consignmentNo) {

		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		LogDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			
			if(consignmentNo.trim().length() > 0){
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();
	
			    sql = "SELECT a.*, b.stage FROM log_cpconsignment a LEFT JOIN consignment b ON a.consignmentNo = b.consignmentNo"
			    		+ " WHERE a.consignmentNo = '"+consignmentNo+"' ORDER BY a.modifyDT ASC";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	obj = new LogDataModel();
			    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
			    	obj.setStatus(Integer.parseInt(rs.getString("status") == null ? "-1" : rs.getString("status")));
			    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark"));
			    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
			    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
			    	obj.setStage(Integer.parseInt(rs.getString("stage") == null ? "-1" : rs.getString("stage")));
			    	data.add(obj);
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

		return data;
	}
	
	
	/**
	 * 查詢該consignment下每個stage的log
	 * 
	 * @param consignmentNo
	 * @return
	 */
	protected static ArrayList<LogDataModel> queryCPStageLog(String consignmentNo) {

		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		LogDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			
			if(consignmentNo.trim().length() > 0){
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();
	
			    sql = "SELECT consignmentNo, serial, '' AS reasonPending, '' AS reasonText, userId, createDT, 1 AS stage FROM log_stage1 WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" UNION ALL" +
			    		" SELECT consignmentNo, serial, '' AS reasonPending, '' AS reasonText, userId, createDT, 2 AS stage FROM log_stage2 WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" UNION ALL" +
			    		" SELECT consignmentNo, serial, '' AS reasonPending, '' AS reasonText, userId, createDT, 3 AS stage FROM log_stage3 WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" UNION ALL" +
			    		" SELECT consignmentNo, serial, '' AS reasonPending, '' AS reasonText, userId, createDT, 4 AS stage FROM log_stage4 WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" UNION ALL" +
			    		" SELECT consignmentNo, serial, '' AS reasonPending, '' AS reasonText, userId, createDT, 5 AS stage FROM log_stage5 WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" UNION ALL" +
			    		" SELECT consignmentNo, serial, '' AS reasonPending, '' AS reasonText, userId, createDT, 6 AS stage FROM log_stage6 WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" UNION ALL" +
			    		" SELECT consignmentNo, serial, '' AS reasonPending, '' AS reasonText, userId, createDT, 7 AS stage FROM log_stage7 WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" UNION ALL" +
			    		" SELECT consignmentNo, serial, reasonPending, reasonText, userId, createDT, 9 AS stage FROM log_stagePending WHERE consignmentNo = '"+consignmentNo+"'" +
			    		" ORDER BY createDT ASC";
				rs = stmt.executeQuery(sql);

			    while(rs.next()){
			    	obj = new LogDataModel();
			    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
			    	obj.setSerial(Integer.parseInt(rs.getString("serial") == null ? "-1" : rs.getString("serial")));
			    	obj.setReasonPending(rs.getString("reasonPending") == null ? "" : rs.getString("reasonPending"));
			    	obj.setReasonText(rs.getString("reasonText") == null ? "" : rs.getString("reasonText"));
			    	obj.setUserId(rs.getString("userId") == null ? "" : rs.getString("userId"));
			    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
			    	obj.setStage(Integer.parseInt(rs.getString("stage") == null ? "-1" : rs.getString("stage")));
			    	data.add(obj);
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

		return data;
	}
	
	
	/**
	 * 記錄管理員在處理訂單的過程
	 * 
	 * @param consignmentNo
	 * @param status
	 * @param modifier
	 */
	public static void insertCPConsignmentLog(String consignmentNo, int status, String modifier, String remark) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			pstmt = conn.prepareStatement("INSERT INTO log_cpconsignment (consignmentNo, status, remark, modifier, modifyDT) VALUE " + "(?, ?, ?, ?, NOW())");


			pstmt.setString(1, consignmentNo);
			pstmt.setInt(2, status);
			pstmt.setString(3, remark);
			pstmt.setString(4, modifier);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
		
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
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
	
	/**
	 * 
	 * @param consignmentNo
	 * @param amount
	 * @param discount
	 * @param discountReason
	 * @param modifier
	 */
	public static void insertChangeAmountLog(String consignmentNo, double amount, int discount, String discountReason, String modifier) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			pstmt = conn.prepareStatement("INSERT INTO log_changeamount (consignmentNo, originalPrice , discountRate, remark, creator, createDT) VALUE " + "(?, ?, ?, ?, ?, NOW())");


			pstmt.setString(1, consignmentNo);
			pstmt.setDouble(2, amount);
			pstmt.setInt(3, discount);
			pstmt.setString(4, discountReason);
			pstmt.setString(5, modifier);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
		
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
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
	
	/**
	 * 
	 * @param pageNo
	 * @return
	 */
	protected static ArrayList<LogDataModel> allAmountLog(int pageNo) {

		LogDataModel obj = null;
		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int total = 0;

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
	
			    sql = "SELECT a.*, (SELECT COUNT(b.lid) FROM log_changeamount b) AS total FROM log_changeamount a ORDER BY a.createDT DESC LIMIT " + page + ", " + numberPerPage;  
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	obj = new LogDataModel();
			    	obj.setLid(Integer.parseInt(rs.getString("lid").toString()));
			    	obj.setConsignmentNo(rs.getString("consignmentNo")== null ? "" : rs.getString("consignmentNo").toString());
			    	obj.setOriginalPrice(Double.parseDouble(rs.getString("originalPrice")== null ? "0.0" : rs.getString("originalPrice").toString()));
			    	obj.setDiscountRate(Integer.parseInt(rs.getString("discountRate")== null ? "0" : rs.getString("discountRate").toString()));
			    	obj.setRemark(rs.getString("remark")== null ? "" : rs.getString("remark").toString());
			    	obj.setCreator(rs.getString("creator")== null ? "" : rs.getString("creator").toString());
			    	obj.setCreateDT(rs.getString("createDT")== null ? "" : rs.getString("createDT").toString());
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
	 * 查詢關於該consignment的所有內部評註
	 * 
	 * @param consignmentNo
	 * @return
	 */
	public static ArrayList<LogDataModel> queryInternalNote(String consignmentNo) {

		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		LogDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			
			if(consignmentNo.trim().length() > 0){
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();    
	
			    sql = "SELECT a.nid, a.consignmentNo, a.remark, a.creator, a.createDT, a.isDel, a.modifier, a.modifyDT," +
			    		" (SELECT COUNT(b.consignmentNo) FROM internalnote b WHERE b.consignmentNo = '"+consignmentNo+"') AS totalNote" +
			    		" FROM internalnote a WHERE consignmentNo = '"+consignmentNo+"' ORDER BY a.createDT ASC";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	obj = new LogDataModel();
			    	obj.setNid(Integer.parseInt(rs.getString("nid") == null ? "" : rs.getString("nid")));
			    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
			    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark"));
			    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
			    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
			    	obj.setIsDel(Integer.parseInt(rs.getString("isDel") == null ? "-1" : rs.getString("isDel")));
			    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
			    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
			    	obj.setTotalNote(Integer.parseInt(rs.getString("totalNote") == null ? "0" : rs.getString("totalNote")));
			    	data.add(obj);
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

		return data;
	}
	
	/**
	 * 統計共有幾個內部評註
	 * 
	 * @param consignmentNo
	 * @return
	 */
	public static int countInternalNote(String consignmentNo) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int total = 0;

		try {
			
			if(consignmentNo.trim().length() > 0){
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }
	
				stmt = conn.createStatement();    
	
			    sql = "SELECT COUNT(b.consignmentNo) AS totalNote FROM internalnote b WHERE b.consignmentNo = '"+consignmentNo+"'";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	total = Integer.parseInt(rs.getString("totalNote") == null ? "0" : rs.getString("totalNote"));
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

		return total;
	}
	
	
	/**
	 * 新增內部評註
	 * 
	 * @param consignmentNo
	 * @param remark
	 * @param request
	 */
	protected static String insertNewNote(String consignmentNo, String remark, String creator) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String result = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			pstmt = conn.prepareStatement("INSERT INTO internalnote (consignmentNo, remark, creator, createDT) " +
					"VALUE " + "(?, ?, ?, NOW())");

			pstmt.setString(1, consignmentNo);
			pstmt.setString(2, remark);
			pstmt.setString(3, creator);
			
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
	 * 更新內部評註狀態
	 * 
	 * @param nid
	 * @param modifier
	 * @return
	 */
	public static String updateNote(int nid, String modifier) {

		Connection conn = null;
		Statement stmt = null;
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
			
			sql = "UPDATE internalnote SET" +
					" isDel = (CASE isDel" +
					" WHEN 0 THEN 1" +
					" WHEN 1 THEN 0 END)," +
					" modifier='" + modifier + "', modifyDT=NOW() WHERE nid=" +nid;

			stmt.executeUpdate(sql);

			result = "OK";
		}
		catch(Exception ex){
			result = "-1";
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

		return result;
	}


	/**
	 * 把顯示在頁面上的內容先整理好
	 * 
	 * @param data
	 * @return
	 */
	public static String organiseNote(ArrayList<LogDataModel> data, String locale) {
		String noteStr = "";
		LogDataModel logData = new LogDataModel();
		
		if(data != null && data.size() > 0){
			for(int i = 0; i < data.size(); i++){
				logData = (LogDataModel)data.get(i);
				if(logData.getIsDel()==1){ //deleted
					noteStr += "<div style='background-color:#E7FCFF'><font color=\"gray\"><strike>" + logData.getRemark().replaceAll("\n", "<br/> ") + "</strike></font> &nbsp; <i class=\"fa fa-trash\" style=\"cursor:pointer;\" title=\"un-delete\" rel=\"tooltip\" onclick=\"updateNote('"+logData.getNid()+"')\"></i></div>";
				} else {
					noteStr += "<div style='background-color:#E7FCFF'>" + logData.getRemark().replaceAll("\n", "<br/> ") + " &nbsp; <i class=\"fa fa-trash\" style=\"cursor:pointer;\" title=\"delete\" rel=\"tooltip\" onclick=\"updateNote('"+logData.getNid()+"')\"></i></div>";
				}
				noteStr += "<img src=\""+Resource.getString("ID_IMG_ARR_HISTORY",locale)+"\" alt=\"\" /><i><font size='1'>" + logData.getCreateDT() + " ";
				if(logData.getModifyDT() != null && logData.getModifyDT().length() > 0){
					noteStr += " &nbsp; " + Resource.getString("ID_LABEL_LASTUPDATE",locale)+Resource.getString("ID_LABEL_COLON",locale) + logData.getModifier();
				} else { //new keyin
					noteStr += " &nbsp; " + Resource.getString("ID_LABEL_CREATEBY",locale)+Resource.getString("ID_LABEL_COLON",locale) + logData.getCreator();
				}
				noteStr += "</font></i><br /><br />";
			}
		}
		
		return noteStr;
	}

	
}