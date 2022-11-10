package com.iposb.account;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iposb.resource.ResourceBusinessModel;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.UtilsBusinessModel;


public class AccountBusinessModel {
	
	static Logger logger = Logger.getLogger(AccountBusinessModel.class);
	
	static NumberFormat formatter = new DecimalFormat("#,###,##0.00");
	static NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
	
	private AccountBusinessModel(){
	}
	

	/**
	 * 帳目管理 - 供管理員查詢
	 * @param paymethod
	 * @param origin
	 * @param destination
	 * @param yy - 年
	 * @param MM -月
	 * @param dd - 日
	 * @return
	 */
	protected static ArrayList<AccountDataModel> allAccount(String payMethod, int origin, int destination, String yyyy, String MM, String dd) {

		AccountDataModel obj = null;
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCondition = "";
		ResultSet rs = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			String DT = "SUBSTRING(c.accDT, 1, 4) = '"+yyyy+"'";
			
			if(MM.length()==2) { //有指定月份
				DT = "SUBSTRING(c.accDT, 1, 7) = '"+yyyy+"-"+MM+"'";
			}
			if(dd.length()==2) { //有指定日子
				DT = "c.accDT = '"+yyyy+"-"+MM+"-"+dd+"'";
			}
			
			sqlCondition = " AND (a.senderArea = " + origin + " OR a.creditArea= " + origin + ")";
			
			
			if(destination > 0) {
				sqlCondition += " AND a.receiverArea = " + destination;
			}
			if(payMethod != "" && Integer.parseInt(payMethod) >= 0) {
				sqlCondition += " AND a.payMethod = " + payMethod;
			}

			stmt = conn.createStatement();
		
			if(!payMethod.equals("") && AccountController.isNumeric(payMethod) && Integer.parseInt(payMethod) >= 0){ //按付款方式列出
				
				sql = "SELECT a.*," +
						" (SELECT b.name_enUS FROM area b WHERE b.aid = a.senderArea) AS senderAreaname," +
						" (SELECT b.name_enUS FROM area b WHERE b.aid = a.receiverArea) AS receiverAreaname," +
						" c.accDT, c.remark AS accRemark" +
						" FROM consignment a" + 
						" LEFT JOIN account c ON a.consignmentNo = c.consignmentNo" +
						" WHERE "+DT+
						" AND a.status <> 1" + //1: cancelled
						sqlCondition +
						" ORDER BY createDT ASC";
				
			    rs = stmt.executeQuery(sql);

			    int total = 1;
			    
			    while(rs.next()){
			    	obj = new AccountDataModel();
			    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT").toString());
			    	obj.setSenderName(rs.getString("senderName") == null ? "" : rs.getString("senderName"));
			    	obj.setSenderAreaname(rs.getString("senderAreaname") == null ? "" : rs.getString("senderAreaname"));
			    	obj.setReceiverAreaname(rs.getString("receiverAreaname") == null ? "" : rs.getString("receiverAreaname"));
			    	obj.setQuantity(Integer.parseInt(rs.getString("quantity") == null ? "0" : rs.getString("quantity").toString()));
			    	obj.setAmount(Double.parseDouble(rs.getString("amount") == null ? "0.0" : rs.getString("amount").toString()));
			    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
			    	obj.setAccDT(rs.getString("accDT") == null ? "" : rs.getString("accDT").toString());
//			    	obj.setStatMonth(rs.getString("statMonth") == null ? "" : rs.getString("statMonth").toString());
			    	obj.setRemark(rs.getString("accRemark") == null ? "" : rs.getString("accRemark").toString());
			    	obj.setStatus(Integer.parseInt(rs.getString("status") == null ? "-1" : rs.getString("status").toString()));
			    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod") == null ? "-1" : rs.getString("payMethod").toString()));
			    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
			    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus") == null ? "-1" : rs.getString("payStatus").toString()));
			    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea") == null ? "0" : rs.getString("senderArea").toString()));
			    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea") == null ? "0" : rs.getString("receiverArea").toString()));
			    	obj.setTotal(total++);
			    	data.add(obj);
			    }
			} else { //列出所有
				
				sql = "SELECT payMethod, count(*) AS total, SUM(amount) AS turnover FROM" +
						" (" +
						"   SELECT a.cid, a.consignmentNo, a.status, a.amount, a.payMethod, a.creditArea, c.accDT FROM consignment a" +
						"   LEFT JOIN account c ON a.consignmentNo = c.consignmentNo" +
						"   WHERE "+DT+" AND a.status <> 1" + sqlCondition +
						" ) as N" +
						" GROUP BY payMethod ORDER BY payMethod ASC";
				
				rs = stmt.executeQuery(sql);

			    while(rs.next()){
			    	obj = new AccountDataModel();
			    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod") == null ? "0" : rs.getString("payMethod").toString()));
			    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total").toString()));
			    	obj.setTurnover(Double.parseDouble(rs.getString("turnover") == null ? "0" : rs.getString("turnover").toString()));
			    	data.add(obj);
			    }
			}
			
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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
	 * Profit管理 - 供管理員查詢
	 * @param payMethod
	 * @param station
	 * @param yy - 年
	 * @param MM - 月
	 * @pram dd - 日
	 * @return
	 */
	protected static ArrayList<AccountDataModel> profitMaintain(String payMethod, int station, String yyyy, String MM, String dd) {

		AccountDataModel obj = null;
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCondition = "";
		ResultSet rs = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			String DT = "SUBSTRING(c.accDT, 1, 7) = '"+yyyy+"-"+MM+"'";
			if(dd.length()==2) { //有指定日子
				DT = "c.accDT = '"+yyyy+"-"+MM+"-"+dd+"'";
			}
			
			if(!payMethod.trim().equals("")) {
				sqlCondition = " AND a.payMethod = " + payMethod;
			}
			
			if(station > 0) {
				sqlCondition += " AND a.senderArea = " + station;
			}

			stmt = conn.createStatement();
			
			sql = "SELECT a.*," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.senderArea) AS senderAreaname," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.receiverArea) AS receiverAreaname," +
					" c.accDT, c.remark AS accRemark" +
					" FROM consignment a" + 
					" LEFT JOIN account c ON a.consignmentNo = c.consignmentNo" +
					" WHERE "+DT+
					" AND a.status <> 1" + //1: cancelled
					sqlCondition +
					" ORDER BY a.createDT ASC";

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new AccountDataModel();
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT").toString());
		    	obj.setSenderName(rs.getString("senderName") == null ? "" : rs.getString("senderName"));
		    	obj.setSenderAreaname(rs.getString("senderAreaname") == null ? "" : rs.getString("senderAreaname"));
		    	obj.setReceiverAreaname(rs.getString("receiverAreaname") == null ? "" : rs.getString("receiverAreaname"));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity") == null ? "0" : rs.getString("quantity").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount") == null ? "0.0" : rs.getString("amount").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
		    	obj.setAccDT(rs.getString("accDT") == null ? "" : rs.getString("accDT").toString());
//		    	obj.setAccMonth(rs.getString("accMonth") == null ? "" : rs.getString("accMonth").toString());
//		    	obj.setStatMonth(rs.getString("statMonth") == null ? "" : rs.getString("statMonth").toString());
		    	obj.setRemark(rs.getString("accRemark") == null ? "" : rs.getString("accRemark").toString());
		    	obj.setStatus(Integer.parseInt(rs.getString("status") == null ? "-1" : rs.getString("status").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod") == null ? "-1" : rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus") == null ? "-1" : rs.getString("payStatus").toString()));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea") == null ? "0" : rs.getString("senderArea").toString()));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea") == null ? "0" : rs.getString("receiverArea").toString()));
		    	data.add(obj);
		    }

		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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
	 * 更新 accDT, statMonth, remark
	 * @return
	 */
	protected static String updateProfit(String consignmentNo, String accDT, String remark) {
		
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
			
			sql = "UPDATE account SET accDT='"+accDT+"', remark='"+remark+"' WHERE consignmentNo='" +consignmentNo + "'";
			
			stmt.executeUpdate(sql);
			
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
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
	
	
	/**
	 * 客人付錢成功後，update當下月份到 accountMonth（除了Transfer的訂單）
	 * 
	 * @param bookingType
	 * @param bookingCode
	 * @return
	 */
	public static String updateAccountMonth(String bookingType, String bookingCode) {
		
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
			
			String accMonth = "";
			String DT = UtilsBusinessModel.todayDate();
			if(DT.length() == 10){
				DT = DT.replace("-", ""); //先去除 -
				accMonth = DT.substring(0, 6); //在截取 yyyyMM
				logger.info("update accMonth>>"+accMonth);
			}

			stmt = conn.createStatement();
			sql = "UPDATE account_"+bookingType+" SET accMonth='"+accMonth+"' WHERE bookingCode='" +bookingCode + "'";
			stmt.executeUpdate(sql);
			
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
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
	
	
	protected static String updateFilename (String bookingType, String bookingCode, String buttonType, String filename) {
		
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
			sql = "UPDATE account_"+bookingType+" SET "+buttonType+"Proof='"+filename+"' WHERE bookingCode='" +bookingCode + "'";
			
			stmt.executeUpdate(sql);
			
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.toString());
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
	
	
	/**
	 * 查詢資料供產生 Sales Report
	 * 
	 * @param accDT
	 * @param station
	 * @param destination
	 * @param payMethod
	 * @return
	 */
	public static ArrayList<AccountDataModel> salesreport(String accDT, int origin, int destination, int payMethod) {

		AccountDataModel obj = null;
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCond = "";
		ResultSet rs = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			
			if(accDT.trim().length() >= 21){
				sqlCond += "AND DATE(c.accDT) BETWEEN DATE('"+accDT.substring(0, 10)+"') AND DATE('"+accDT.substring(accDT.length()-10, accDT.length())+"')";
			}
			
			if(destination > 0) {
				sqlCond += " AND a.receiverArea = " + destination;
			}
			if(payMethod > -1) {
				sqlCond += " AND a.payMethod = " + payMethod;
			}
			
			

			stmt = conn.createStatement();
				
			sql = "SELECT a.*," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.senderArea) AS senderAreaname," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.receiverArea) AS receiverAreaname," +
					" c.accDT, c.remark AS accRemark" +
					" FROM consignment a" + 
					" LEFT JOIN account c ON a.consignmentNo = c.consignmentNo" +
					" WHERE (a.senderArea = " + origin + " OR a.creditArea= " + origin + ")" +
					sqlCond +
					" AND a.status <> 1"; //1: cancelled
//					" ORDER BY createDT ASC";  //remove this can improve efficiency
			
			rs = stmt.executeQuery(sql);

		    int total = 1;
		    
		    while(rs.next()){
		    	obj = new AccountDataModel();
		    	obj.setAccDT(accDT);
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT") == null ? "" : rs.getString("dispatchDT").toString());
		    	obj.setSenderName(rs.getString("senderName") == null ? "" : rs.getString("senderName"));
		    	obj.setSenderAreaname(rs.getString("senderAreaname") == null ? "" : rs.getString("senderAreaname"));
		    	obj.setReceiverAreaname(rs.getString("receiverAreaname") == null ? "" : rs.getString("receiverAreaname"));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity") == null ? "0" : rs.getString("quantity").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount") == null ? "0.0" : rs.getString("amount").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo") == null ? "" : rs.getString("generalCargoNo"));
		    	obj.setRemark(rs.getString("accRemark") == null ? "" : rs.getString("accRemark").toString());
		    	obj.setStatus(Integer.parseInt(rs.getString("status") == null ? "-1" : rs.getString("status").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod") == null ? "-1" : rs.getString("payMethod").toString()));
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus") == null ? "-1" : rs.getString("payStatus").toString()));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea") == null ? "0" : rs.getString("senderArea").toString()));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea") == null ? "0" : rs.getString("receiverArea").toString()));
		    	obj.setTotal(total++);
		    	data.add(obj);
		    }

			
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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
	 * 
	 * @param accDT
	 * @param userId
	 * @return
	 */
	public static ArrayList<AccountDataModel> memberReport( String accDT, String userId) {

		AccountDataModel obj = null;
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCond1 = "";
		String sqlCond2 = "";
		ResultSet rs = null;
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			if(accDT.length() > 0){
				sqlCond1 = " AND DATE(c.accDT) BETWEEN DATE('"+accDT.substring(0, 10)+"') AND DATE('"+accDT.substring(accDT.length()-10, accDT.length())+"')";
			}
			
			if(userId != "" && userId.length() > 0){
				sqlCond2 = " AND a.userId = '" + userId + "'";
			}

			stmt = conn.createStatement();
				
			sql = "SELECT a.*," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.senderArea) AS senderAreaname," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.receiverArea) AS receiverAreaname," +
					" c.accDT, c.remark AS accRemark" +
					" FROM consignment a" + 
					" LEFT JOIN account c ON a.consignmentNo = c.consignmentNo" +
					" WHERE a.status <> 1" + //1: cancelled
					sqlCond1 + sqlCond2 +
					" ORDER BY createDT ASC";
			
			rs = stmt.executeQuery(sql);

		    int total = 1;
		    
		    while(rs.next()){
		    	obj = new AccountDataModel();
		    	obj.setAccDT(accDT);
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT") == null ? "" : rs.getString("dispatchDT").toString());
		    	obj.setSenderName(rs.getString("senderName") == null ? "" : rs.getString("senderName"));
		    	obj.setSenderAreaname(rs.getString("senderAreaname") == null ? "" : rs.getString("senderAreaname"));
		    	obj.setReceiverAreaname(rs.getString("receiverAreaname") == null ? "" : rs.getString("receiverAreaname"));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity") == null ? "0" : rs.getString("quantity").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount") == null ? "0.0" : rs.getString("amount").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo") == null ? "" : rs.getString("generalCargoNo"));
		    	obj.setRemark(rs.getString("accRemark") == null ? "" : rs.getString("accRemark").toString());
		    	obj.setStatus(Integer.parseInt(rs.getString("status") == null ? "-1" : rs.getString("status").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod") == null ? "-1" : rs.getString("payMethod").toString()));
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus") == null ? "-1" : rs.getString("payStatus").toString()));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea") == null ? "0" : rs.getString("senderArea").toString()));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea") == null ? "0" : rs.getString("receiverArea").toString()));
		    	obj.setTotal(total++);
		    	data.add(obj);
		    }

			
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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
	 * 
	 * @param pageNo
	 * @param accDT
	 * @param userId
	 * @return
	 */
	public static ArrayList<AccountDataModel> searchMemberReport(int pageNo, String accDT, String userId) {

		AccountDataModel obj = null;
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCond = "";
		ResultSet rs = null;
		
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
			
			if(accDT != "" && accDT.length() > 0){
				sqlCond = "DATE(c.accDT) BETWEEN DATE('"+accDT.substring(0, 10)+"') AND DATE('"+accDT.substring(accDT.length()-10, accDT.length())+"') ";
				
				if(userId != "" && userId.length() > 0){
					sqlCond += "AND userId = '" + userId + "' AND status <> 1";
				}
			} else {
				if(userId != "" && userId.length() > 0){
					sqlCond = "userId = '" + userId + "' AND status <> 1";
				}
			}
			

			stmt = conn.createStatement();
				
			sql = "SELECT a.*," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.senderArea) AS senderAreaname," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.receiverArea) AS receiverAreaname," +
					" c.accDT, c.remark AS accRemark," +
					" (SELECT COUNT(cid) FROM consignment WHERE "+ sqlCond +") AS total" +
					" FROM consignment a" + 
					" LEFT JOIN account c ON a.consignmentNo = c.consignmentNo" +
					" WHERE " + sqlCond +
//					" AND a.status <> 1" + //1: cancelled
					" ORDER BY createDT DESC LIMIT " + page + ", " + numberPerPage;
			
			rs = stmt.executeQuery(sql);

//		    int total = 1;
		    
		    while(rs.next()){
		    	obj = new AccountDataModel();
		    	obj.setAccDT(accDT);
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT") == null ? "" : rs.getString("dispatchDT").toString());
		    	obj.setSenderName(rs.getString("senderName") == null ? "" : rs.getString("senderName"));
		    	obj.setSenderAreaname(rs.getString("senderAreaname") == null ? "" : rs.getString("senderAreaname"));
		    	obj.setReceiverAreaname(rs.getString("receiverAreaname") == null ? "" : rs.getString("receiverAreaname"));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity") == null ? "0" : rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight") == null ? "0.0" : rs.getString("weight").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount") == null ? "0.0" : rs.getString("amount").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo") == null ? "" : rs.getString("generalCargoNo"));
		    	obj.setRemark(rs.getString("accRemark") == null ? "" : rs.getString("accRemark").toString());
		    	obj.setStatus(Integer.parseInt(rs.getString("status") == null ? "-1" : rs.getString("status").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod") == null ? "-1" : rs.getString("payMethod").toString()));
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus") == null ? "-1" : rs.getString("payStatus").toString()));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea") == null ? "0" : rs.getString("senderArea").toString()));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea") == null ? "0" : rs.getString("receiverArea").toString()));
//		    	obj.setTotal(total++);
		    	obj.setTotal(Integer.parseInt(rs.getString("total").toString()));
		    	data.add(obj);
		    }

			
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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
	 * 記錄 account 上傳的 bankslip
	 * 
	 * @param settlementDT
	 * @param station
	 * @param bankin
	 * @param remark
	 * @param filename
	 * @param userId
	 * @return
	 */
	public static String insertSettlement(String settlementDT, int station, double bankin, String remark, String filename, String userId) {

		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			pstmt = conn.prepareStatement("INSERT INTO settlement (settlementDT, station, bankin, remark, filename, creator, createDT) " +
					"VALUE " + "(?, ?, ?, ?, ?, ?, NOW())");

			pstmt.setString(1, settlementDT);
			pstmt.setInt(2, station);
			pstmt.setDouble(3, bankin);
			pstmt.setString(4, remark);
			pstmt.setString(5, filename);
			pstmt.setString(6, userId);
			
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

		return "OK";
	}

	
	/**
	 * 產生 Account Settlement 的 calendar
	 * 
	 * @param year
	 * @param month
	 * @param station
	 * @return
	 */
	public static String getSettlementRecordString4Calendar(String year, String month, int station) {

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		String sql = "";
		String eventString = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
				
			sql = "SELECT * FROM settlement WHERE SUBSTRING(settlementDT, 1, 7) = '"+year+"-"+month+"' AND station = " + station +
					" ORDER BY settlementDT ASC";
		    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){

		    	String DT = rs.getString("settlementDT").toString().substring(8, 10);
		    	String bankin = formatter2.format(Double.parseDouble(rs.getString("bankin").toString()));
		    	String RM = rs.getString("remark").toString();
		    	String filename = rs.getString("filename").toString();
		    	
		    	eventString += DT + "|" + filename + "|" + bankin + " - " + RM + ";";
		    }
//				logger.info("sql: "+sql);
//				logger.info("eventString: "+eventString);
			    
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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

		return eventString;
	}
	
	
	/**
	 * 查詢供產生 Invoice 給 Partner 的數據
	 * 
	 * @param airwaybillNum
	 * @param partnerId
	 * @return
	 */
	protected static ArrayList<AccountDataModel> checkPartnerInvoice(String invoiceDateRange, int partnerId) {

		AccountDataModel obj = null;
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		String startDT = invoiceDateRange.substring(0, 10);
		String endDT = invoiceDateRange.substring(invoiceDateRange.length()-10, invoiceDateRange.length());

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			stmt = conn.createStatement();
				
			sql = "SELECT a.*, b.ename, b.address, b.pricingPolicy, c.*" +
					" FROM consignment a" + 
					" LEFT JOIN partnerlist b ON a.partnerId = b.pid" +
					" LEFT JOIN pricing_partner c ON b.pricingPolicy = c.pid" +
					" WHERE a.partnerId = "+partnerId+
					" AND DATE(a.createDT) BETWEEN DATE('"+startDT+"') AND DATE('"+endDT+"')" +
					" ORDER BY a.createDT ASC";
	
			rs = stmt.executeQuery(sql);

		    int total = 1;
		    
		    while(rs.next()){
		    	obj = new AccountDataModel();
		    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo") == null ? "" : rs.getString("generalCargoNo"));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity") == null ? "0" : rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight") == null ? "0.0" : rs.getString("weight").toString()));
		    	obj.setTerms(rs.getString("terms") == null ? "" : rs.getString("terms"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT").length() > 10 ? rs.getString("createDT").substring(0, 10) : rs.getString("createDT").toString());
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename"));
		    	obj.setAddress(rs.getString("address") == null ? "" : rs.getString("address"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
		    	obj.setCodPrice(Double.parseDouble(rs.getString("codPrice") == null ? "0" : rs.getString("codPrice")));
		    	obj.setTotal(total++);
		    	data.add(obj);
		    }

			
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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
	 * Check Pos Statement
	 * 
	 * @param airwaybillNum
	 * @return
	 */
	protected static ArrayList<AccountDataModel> checkPosStatement(String airwaybillNum) {

		AccountDataModel obj = null;
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			stmt = conn.createStatement();
				
			sql = "SELECT a.*" +
					" FROM consignment a" + 
					" WHERE a.airwaybillNum LIKE '%"+airwaybillNum+"%'"+
					" ORDER BY a.createDT ASC";
	
			rs = stmt.executeQuery(sql);

		    int total = 1;
		    
		    while(rs.next()){
		    	obj = new AccountDataModel();
		    	obj.setConsignmentNo(rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo") == null ? "" : rs.getString("generalCargoNo"));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity") == null ? "0" : rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight") == null ? "0.0" : rs.getString("weight").toString()));
		    	obj.setTerms(rs.getString("terms") == null ? "" : rs.getString("terms"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT").length() > 10 ? rs.getString("createDT").substring(0, 10) : rs.getString("createDT").toString());
		    	obj.setTotal(total++);
		    	data.add(obj);
		    }

			
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
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
     * 產生 Excel 檔案
     * 
     * @param request
     * @param accountData
     * @param extension: xls, xlsx
     * @return
     * @throws Exception
     */
    public static String generatePartnerInvoicel (HttpServletRequest request, ArrayList<AccountDataModel> data, String extension) {

    	String result = "";
    	Workbook wb;

        if(extension.equals("xls")) {
        	wb = new HSSFWorkbook();
        } else {
        	wb = new XSSFWorkbook();
        }
        
        try {
        	
        	

	        Map<String, CellStyle> styles = createStyles(wb);
	        Sheet sheet = wb.createSheet("Sheet1"); //Sheet名稱
	        sheet.setDisplayGridlines(true);
	
	        sheet.setFitToPage(false);
	        sheet.setHorizontallyCenter(false);
	        sheet.setMargin(Sheet.TopMargin, 0.3 /* inches */ );
	        sheet.setMargin(Sheet.BottomMargin, 0.3 /* inches */ );
	        sheet.setMargin(Sheet.LeftMargin, 0.2 /* inches */ );
	        sheet.setMargin(Sheet.RightMargin, 0.1 /* inches */ );
	        
	        sheet.setColumnWidth(0, 5*256); //No.
	        sheet.setColumnWidth(1, 23*256); //Date / Awaybil No. / Flight
	        sheet.setColumnWidth(2, 13*256); //Consignment No.
	        sheet.setColumnWidth(3, 6*256); //Qty
	        sheet.setColumnWidth(4, 9*256); //Weight (KG)
	        sheet.setColumnWidth(5, 9*256); //TC 0.20
	        sheet.setColumnWidth(6, 9*256); //First 0.0 KG
	        sheet.setColumnWidth(7, 9*256); //Add RM0.0/KG
	        sheet.setColumnWidth(8, 9*256); //Collection C.O.D.
	        sheet.setColumnWidth(9, 10*256); //Total (RM)
	
	        
	        
	        AccountDataModel accountData = new AccountDataModel();
	        if(data != null && data.size() > 0){
	        	accountData = (AccountDataModel)data.get(0);
	        }
	        
	        String firstWeight = formatter2.format(accountData.getFirst());
	        double additionPrice = accountData.getAdditionPrice();

	        String[] titles = {
	                "No.", "Date / Awaybil No. / Flight", "Consignment No.", "Qty", "Weight (KG)", "TC 0.20", "First "+firstWeight+" KG", "Add RM"+additionPrice+"/KG", "Collection C.O.D.", "Total (RM)"
	        };
	        
	        
	        InputStream inputStream = new FileInputStream(request.getSession().getServletContext().getRealPath("/")+"etc/logo.png");
            /* Convert picture to be added into a byte array */
            byte[] bytes = IOUtils.toByteArray(inputStream);
            /* Add Picture to Workbook, Specify picture type as PNG and Get an Index */
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            /* Close the InputStream. We are ready to attach the image to workbook now */
            inputStream.close();                
            
            //Returns an object that handles instantiating concrete classes
            CreationHelper helper = wb.getCreationHelper();
            
            /* Create the drawing container */
            Drawing drawing = sheet.createDrawingPatriarch();
            //Create an anchor that is attached to the worksheet
            ClientAnchor anchor = helper.createClientAnchor();
            /* Define top left corner, and we can resize picture suitable from there */
            anchor.setCol1(0);
            anchor.setRow1(0);           
            /* Invoke createPicture and pass the anchor point and Idx */
            Picture logo = drawing.createPicture(anchor, pictureIdx);
            /* Call resize method, which resizes the image */
            logo.resize(5.5, 2.0);//自己組合放大比例（寬, 高）
            
	        
	        //header row
	        Row headerRow = sheet.createRow(0);
	        headerRow.setHeightInPoints(28);
	        Cell headerCell = headerRow.createCell(2);
	        headerCell.setCellValue("iPosb Logistic Sdn. Bhd.");
	        headerCell.setCellStyle(styles.get("companyName"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$1:$J$1"));
	        
	        headerRow = sheet.createRow(1);
	        headerRow.setHeightInPoints(55);
	        headerCell = headerRow.createCell(2);
	        headerCell.setCellValue("Shop No. 31, Jalan BU 2/1, Block B4, Ground Floor Bandar Utama IJM, Sandakan, Sabah.\r\nTEL: 089-271863 / 017 890 9811     FAX: 089-271863     WEB: iposb.com");
	        headerCell.setCellStyle(styles.get("header"));
	        
	        //為了畫底線，每個cell都有set同樣的style
	        headerCell = headerRow.createCell(0);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(1);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(3);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(4);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(5);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(6);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(7);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(8);
	        headerCell.setCellStyle(styles.get("header"));
	        headerCell = headerRow.createCell(9);
	        headerCell.setCellStyle(styles.get("header"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$2:$J$2"));
	        
	        //Attn row (company name)
	        Row attnRow = sheet.createRow(3);
	        attnRow.setHeightInPoints(30);
	        Cell attnCell = attnRow.createCell(0);
	        attnCell.setCellValue(accountData.getEname());
	        attnCell.setCellStyle(styles.get("attn"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$4:$E$4"));
	        
	        attnCell = attnRow.createCell(7);
	        attnCell.setCellValue("INVOICE");
	        attnCell.setCellStyle(styles.get("attn"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$H$4:$J$4"));
	        
	        //address
	        Row addressRow = sheet.createRow(4);
	        addressRow.setHeightInPoints(15);
	        Cell addressCell = addressRow.createCell(0);
	        addressCell.setCellValue(accountData.getAddress());
	        addressCell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$5:$E$7"));
	        
	        addressCell = addressRow.createCell(7);
	        addressCell.setCellValue("NO. :");
	        addressCell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$H$5:$H$5"));
	        
	        addressCell = addressRow.createCell(8);
	        addressCell.setCellValue("");
	        addressCell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$I$5:$J$5"));
	        
	        addressRow = sheet.createRow(5);
	        addressCell = addressRow.createCell(7);
	        addressCell.setCellValue("DATE :");
	        addressCell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$H$6:$H$6"));
	        
	        addressCell = addressRow.createCell(8);
	        addressCell.setCellValue(UtilsBusinessModel.todayDate());
	        addressCell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$I$6:$J$6"));
	        
	        addressRow = sheet.createRow(6);
	        addressCell = addressRow.createCell(7);
	        addressCell.setCellValue("REMARK :");
	        addressCell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$H$7:$H$7"));
	        
	        addressCell = addressRow.createCell(8);
	        addressCell.setCellValue("");
	        addressCell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$I$7:$J$7"));
	        

	        //item row
	        Row itemRow = sheet.createRow(8);
	        itemRow.setHeightInPoints(35);
	        Cell itemCell;
	        for (int i = 0; i < titles.length; i++) {
	        	itemCell = itemRow.createCell(i);
	        	itemCell.setCellValue(titles[i]);
	        	itemCell.setCellStyle(styles.get("item"));
	        }
	
	        int rownum = 9;
	        int num = 0;
	        String numTxt = "";
			String thisDT = "";
			String dateTxt = "";
			int totalCustomCharge = 0;
			
	        accountData = new AccountDataModel();
        	if(data != null && data.size() > 0){
        		for (int i = 0; i < data.size(); i++) {
        			accountData = (AccountDataModel)data.get(i);
        			
        			if(!thisDT.equals(accountData.getCreateDT())){
						num++;
						numTxt = String.valueOf(num);
						dateTxt = accountData.getCreateDT()+"\r\nAwaybil No. \r\nFlight     ";
						
						sheet.addMergedRegion(CellRangeAddress.valueOf("$B$"+(rownum+1)+":$B$"+(rownum+3)+"")); //組合三行空白
						
					} else {
						numTxt = "";
						dateTxt = "";
					}
        			
            		Row row = sheet.createRow(rownum++);
            		row.setHeightInPoints(15);
            		
                    for (int j = 0; j < titles.length; j++) {
                        Cell cell = row.createCell(j);
                        if(j == 0) {
                        	cell.setCellValue(numTxt);
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 1) {
                	        cell.setCellValue(dateTxt);
                	        cell.setCellStyle(styles.get("text_left"));
                        }
                        
                        else if(j == 2) {
                	        cell.setCellValue(accountData.getGeneralCargoNo());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 3) {
                	        cell.setCellValue(accountData.getQuantity());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 4) {
                	        cell.setCellValue(accountData.getWeight());
                	        cell.setCellStyle(styles.get("money"));
                        }
                        
                        else if(j == 5) {
                        	double tc = accountData.getWeight() * 0.2;
                        	cell.setCellValue(tc);
                	        cell.setCellStyle(styles.get("money"));
                        }
                        
                        else if(j == 6) {
                        	cell.setCellValue(accountData.getFirstPrice());
                	        cell.setCellStyle(styles.get("money"));
                        }
                        
                        else if(j == 7) {
                        	double numVal = 0;
                        	double additionWeight = accountData.getWeight() - accountData.getFirst();
							additionPrice = accountData.getAdditionPrice();
							if(additionWeight > 0) {
								numVal = additionWeight * additionPrice;
								cell.setCellValue(numVal);
	                	        cell.setCellStyle(styles.get("money"));
							} else {
								cell.setCellValue("-");
	                	        cell.setCellStyle(styles.get("text_right"));
							}

                        }
                        
                        else if(j == 8) {
                        	
                        	double numVal = 0;
                        	if( (accountData.getTerms().equals("CC")) || (accountData.getPayMethod()==5) || (accountData.getPayMethod()==6) ) {
                        		numVal = accountData.getCodPrice();
                        		cell.setCellValue(numVal);
                    	        cell.setCellStyle(styles.get("money"));
							} else {
								cell.setCellValue("-");
	                	        cell.setCellStyle(styles.get("text_right"));
							}
                        	
                        }
                        
                        else if(j == 9) {                        	
                        	String ref = "F" +rownum+ ":I" + rownum;
                            cell.setCellFormula("SUM(" + ref + ")");
                            cell.setCellStyle(styles.get("subtotal_right"));
                        }
                        
                        else {
                            cell.setCellStyle(styles.get("cell"));
                        }
                        
                    }
                    
                    thisDT = accountData.getCreateDT();//為了下一筆資料的比對
                    
                    //先知道下一筆資料，是否是另一組的
                    String nextDT = "";
                    if(i < data.size()-1) {
	                    AccountDataModel dataNext = (AccountDataModel)data.get(i+1);
	                    nextDT = dataNext.getCreateDT();
                    }
                    
                    if(!nextDT.equals(thisDT)) { //不是同一組，也就是將出現新一組資料
                    	row = sheet.createRow(rownum++);
                		row.setHeightInPoints(15);
                		
                		for (int j = 0; j < titles.length; j++) {
                            Cell cell = row.createCell(j);
                		
	                		if(j == 1) {
	                	        cell.setCellValue("Custom Clearance Charges");
	                	        cell.setCellStyle(styles.get("custom"));
	                        } else if(j == 9) {
	                	        cell.setCellStyle(styles.get("subtotal_right_custom"));
	                        } else {
	                            cell.setCellStyle(styles.get("custom"));
	                        }
                		}
                		
                		totalCustomCharge++;
                    }
                    
            		
        		}

        	}
        	
        	
        	//row with totals at bottom
            Row sumRow = sheet.createRow(rownum++);
            sumRow.setHeightInPoints(25);
            Cell cell;
            
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+rownum+":$C$"+rownum+""));

            for (int i = 0; i < 5; i++) {
                cell = sumRow.createCell(i);
                cell.setCellValue("Grand - Total : ");
                cell.setCellStyle(styles.get("subtotal_right_custom"));
            }
            
            
            int total = accountData.getTotal() + totalCustomCharge + 9; //前面9行不加總
            
            for (int j = 3; j < 10; j++) {
                cell = sumRow.createCell(j);
                String ref = (char)('A' + j) + "7:" + (char)('A' + j) + total;
                cell.setCellFormula("SUM(" + ref + ")");
                
                if(j == 3) {
                	cell.setCellStyle(styles.get("quantity"));
                } else if(j == 9) {
                	cell.setCellStyle(styles.get("subtotal_bottom_total"));
                } else {
                	cell.setCellStyle(styles.get("subtotal_bottom"));
                }
                
            }
            
            
            rownum += 3; //空3行
            
                
            //Ringgit Malaysia row
            Row row = sheet.createRow(rownum++);
	        row.setHeightInPoints(21);
	        cell = row.createCell(0);
	        cell.setCellValue("RINGGIT MALAYSIA: ");
	        cell.setCellStyle(styles.get("ringgit"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+rownum+":$J$"+rownum+""));
	        
	        
	        rownum += 1;
            
            //Bank Details row	        
	        row = sheet.createRow(rownum++);
	        row.setHeightInPoints(21);
	        cell = row.createCell(0);
	        cell.setCellValue("* All cheques crossed and payable to");
	        cell.setCellStyle(styles.get("normal_right"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+rownum+":$C$"+rownum+""));
	        
	        cell = row.createCell(3);
	        cell.setCellValue("iPosb Logistic Sdn. Bhd.");
	        cell.setCellStyle(styles.get("ringgit"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$D$"+rownum+":$J$"+rownum+""));
	        
	        row = sheet.createRow(rownum++);
	        row.setHeightInPoints(21);
	        cell = row.createCell(0);
	        cell.setCellValue("CIMB BANK : ");
	        cell.setCellStyle(styles.get("normal_right"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+rownum+":$C$"+rownum+""));
	        
	        cell = row.createCell(3);
	        cell.setCellValue("10100 0010 91056");
	        cell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$D$"+rownum+":$J$"+rownum+""));	        
	        
	        rownum += 2;
            
	        
            //Thank you row
            row = sheet.createRow(rownum++);
	        row.setHeightInPoints(21);
	        cell = row.createCell(0);
	        cell.setCellValue("Thank you.");
	        cell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+rownum+":$D$"+rownum+""));
	        
	        row = sheet.createRow(rownum++);
	        row.setHeightInPoints(21);
	        cell = row.createCell(0);
	        cell.setCellValue("NO CONSIGNMENT ATTACH");
	        cell.setCellStyle(styles.get("normal_left"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+rownum+":$D$"+rownum+""));
	        
	        //Signature row
            row = sheet.createRow(rownum++);
	        row.setHeightInPoints(21);
	        cell = row.createCell(6);
	        cell.setCellValue("for iPosb Logistic Sdn. Bhd.");
	        cell.setCellStyle(styles.get("normal_center"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$G$"+rownum+":$J$"+rownum+""));
	        
	        row = sheet.createRow(rownum++);
	        row.setHeightInPoints(30);
	        cell = row.createCell(6);
	        cell.setCellValue("William Chong");
	        cell.setCellStyle(styles.get("signature"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$G$"+rownum+":$J$"+rownum+""));
	        
	        row = sheet.createRow(rownum++);
	        row.setHeightInPoints(12);
	        cell = row.createCell(6);
	        cell.setCellValue("_______________________________");
	        cell.setCellStyle(styles.get("normal_center"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$G$"+rownum+":$J$"+rownum+""));
	        
	        row = sheet.createRow(rownum++);
	        row.setHeightInPoints(21);
	        cell = row.createCell(6);
	        cell.setCellValue("WILLIAM CHONG (DIRECTOR)");
	        cell.setCellStyle(styles.get("normal_center_bold"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$G$"+rownum+":$J$"+rownum+""));

	        
	        // Write the output to a file
	        String file = request.getSession().getServletContext().getRealPath("/")+"temp/partnerInvoice."+extension;
	        FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
	        
	        
	        //upload to Amazon S3
			String targetDir = "partnerInvoice";
			boolean isUploaded = ResourceBusinessModel.uploadFile(file, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			} else {
				result = "partnerInvoice."+extension; //傳回檔名
			}
	        
	        
        
        } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    }
        
		return result;
		
    }
    
    
    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
    	
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        
        org.apache.poi.ss.usermodel.Font companyFont = wb.createFont();
        companyFont.setFontHeightInPoints((short)20);
        companyFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(companyFont);
        style.setWrapText(true);
        styles.put("companyName", style);
        
        org.apache.poi.ss.usermodel.Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short)10);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setFont(headerFont);
        style.setWrapText(true);
        styles.put("header", style);
        
        org.apache.poi.ss.usermodel.Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)20);
        titleFont.setUnderline(org.apache.poi.ss.usermodel.Font.U_SINGLE);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        styles.put("subject", style);
        
        org.apache.poi.ss.usermodel.Font attnFont = wb.createFont();
        attnFont.setFontHeightInPoints((short)14);
        attnFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(attnFont);
        style.setWrapText(true);
        styles.put("attn", style);
        
        org.apache.poi.ss.usermodel.Font ringgitFont = wb.createFont();
        ringgitFont.setFontHeightInPoints((short)12);
        ringgitFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(ringgitFont);
        style.setWrapText(true);
        styles.put("ringgit", style);


        org.apache.poi.ss.usermodel.Font itemFont = wb.createFont();
        itemFont.setFontHeightInPoints((short)9);
        itemFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setFont(itemFont);
        style.setWrapText(true);
        styles.put("item", style);

        org.apache.poi.ss.usermodel.Font formFont = wb.createFont();
        formFont.setFontHeightInPoints((short)9);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_NONE);
        style.setBorderBottom(CellStyle.BORDER_NONE);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("text_center", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_NONE);
        style.setBorderBottom(CellStyle.BORDER_NONE);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("text_left", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_NONE);
        style.setBorderBottom(CellStyle.BORDER_NONE);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("text_right", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setDataFormat(wb.createDataFormat().getFormat("#,###,###;(#,###,###)"));
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("quantity", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_NONE);
        style.setBorderBottom(CellStyle.BORDER_NONE);
        style.setDataFormat(wb.createDataFormat().getFormat("#,###,##0.00;(#,###,##0.00)"));
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("money", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setDataFormat(wb.createDataFormat().getFormat("#,###,##0.00;(#,###,##0.00)"));
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("subtotal_bottom", style);
        
        org.apache.poi.ss.usermodel.Font formFontBold = wb.createFont();
        formFontBold.setFontHeightInPoints((short)9);
        formFontBold.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setDataFormat(wb.createDataFormat().getFormat("#,###,##0.00;(#,###,##0.00)"));
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFontBold);
        styles.put("subtotal_bottom_total", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_NONE);
        style.setBorderBottom(CellStyle.BORDER_NONE);
        style.setDataFormat(wb.createDataFormat().getFormat("#,###,##0.00;(#,###,##0.00)"));
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFontBold);
        styles.put("subtotal_right", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_NONE);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setDataFormat(wb.createDataFormat().getFormat("#,###,##0.00;(#,###,##0.00)"));
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFontBold);
        styles.put("subtotal_right_custom", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_NONE);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("custom", style);
        
        
        org.apache.poi.ss.usermodel.Font bodyFont = wb.createFont();
        bodyFont.setFontHeightInPoints((short)10);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(bodyFont);
        styles.put("normal_left", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(bodyFont);
        styles.put("normal_center", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(bodyFont);
        styles.put("normal_right", style);
        
        org.apache.poi.ss.usermodel.Font bodyFontBold = wb.createFont();
        bodyFontBold.setFontHeightInPoints((short)10);
        bodyFontBold.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(bodyFontBold);
        styles.put("normal_center_bold", style);

        
        org.apache.poi.ss.usermodel.Font signFont = wb.createFont();
        signFont.setFontHeightInPoints((short)10);
        signFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        signFont.setFontName("Lucida Handwriting");
        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(signFont);
        styles.put("signature", style);
        

        return styles;
    }
	

    
	
	/**
     * 補齊不足長度
     * @param length 長度
     * @param number 數字
     * @return
     */
    private static String lpad (int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }
    
	
}