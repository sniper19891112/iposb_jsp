package com.iposb.logon;

import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.iposb.code.CodeBusinessModel;
import com.iposb.i18n.Resource;
import com.iposb.resource.ResourceBusinessModel;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.EmailValidator;
import com.iposb.utils.IPTool;
import com.iposb.utils.Mailer;


public class LogonBusinessModel {

	
	private LogonBusinessModel(){
	}
	
	static Logger logger = Logger.getLogger(LogonBusinessModel.class);
	
	public static ArrayList<LogonDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		LogonDataModel obj = null;

		try {
			
	    	obj = new LogonDataModel();
	    	obj.setUserId(request.getParameter("userId") == null ? request.getParameter("email") : request.getParameter("userId").equals("") ? request.getParameter("email") : request.getParameter("userId"));//如果客人沒有設userId，則把email帶入成 userId，因為目前訂單還是靠userId搜尋
	    	obj.setUserId_Current(request.getParameter("userId_Current") == null ? "" : request.getParameter("userId_Current"));
	    	obj.setPasswd(request.getParameter("passwd") == null ? "" : request.getParameter("passwd"));
	    	obj.setPasswd2(request.getParameter("passwd2") == null ? "" : request.getParameter("passwd2"));
	    	obj.setPasswdOld(request.getParameter("passwdOld") == null ? "" : request.getParameter("passwdOld"));
	    	obj.setPasswdNew(request.getParameter("passwdNew") == null ? "" : request.getParameter("passwdNew"));
	    	obj.setEmail(request.getParameter("email") == null ? "" : request.getParameter("email"));
	    	obj.setEmail_Current(request.getParameter("email_Current") == null ? "" : request.getParameter("email_Current"));
	    	obj.setEname(request.getParameter("ename") == null ? "" : request.getParameter("ename"));
	    	obj.setCname(request.getParameter("cname") == null ? "" : request.getParameter("cname"));
	    	obj.setGender(Integer.parseInt(request.getParameter("gender") == null ? "0" : request.getParameter("gender").equals("") ? "0" : request.getParameter("gender").toString()));
	    	obj.setNationality(request.getParameter("nationality") == null ? "" : request.getParameter("nationality"));
	    	obj.setCountryCode(request.getParameter("countryCode") == null ? "" : request.getParameter("countryCode"));
	    	obj.setPhone(request.getParameter("phone") == null ? "" : request.getParameter("phone"));
	    	obj.setDob(request.getParameter("dob") == null ? "" : request.getParameter("dob"));
	    	obj.setIC(request.getParameter("IC") == null ? "" : request.getParameter("IC"));
	    	obj.setAid(Integer.parseInt(request.getParameter("aid") == null ? "0" : request.getParameter("aid").toString()));
	    	obj.setFbid(request.getParameter("fbid") == null ? "" : request.getParameter("fbid"));
	    	obj.setNewsLetter(Integer.parseInt(request.getParameter("newsletter") == null ? "0" : request.getParameter("newsletter").equals("on") ? "1" : "0"));
	    	obj.setVerify(request.getParameter("verify") == null ? "" : request.getParameter("verify"));
	    	obj.setPrivilege(Integer.parseInt(request.getParameter("privilege") == null ? "-9" : request.getParameter("privilege").equals("") ? "-9" : request.getParameter("privilege")));
	    	obj.setAccNo(request.getParameter("accNo") == null ? "" : request.getParameter("accNo"));
	    	obj.setDiscPack(request.getParameter("discPack") == null ? "" : request.getParameter("discPack"));
	    	obj.setStage1(Integer.parseInt(request.getParameter("stage1") == null ? "0" : request.getParameter("stage1").equals("on") ? "1" : "0"));
	    	obj.setStage2(Integer.parseInt(request.getParameter("stage2") == null ? "0" : request.getParameter("stage2").equals("on") ? "1" : "0"));
	    	obj.setStage3(Integer.parseInt(request.getParameter("stage3") == null ? "0" : request.getParameter("stage3").equals("on") ? "1" : "0"));
	    	obj.setStage4(Integer.parseInt(request.getParameter("stage4") == null ? "0" : request.getParameter("stage4").equals("on") ? "1" : "0"));
	    	obj.setStage5(Integer.parseInt(request.getParameter("stage5") == null ? "0" : request.getParameter("stage5").equals("on") ? "1" : "0"));
	    	obj.setStage6(Integer.parseInt(request.getParameter("stage6") == null ? "0" : request.getParameter("stage6").equals("on") ? "1" : "0"));
	    	obj.setStage7(Integer.parseInt(request.getParameter("stage7") == null ? "0" : request.getParameter("stage7").equals("on") ? "1" : "0"));
	    	obj.setLoginTimes(Integer.parseInt(request.getParameter("loginTimes") == null ? "1" : request.getParameter("loginTimes").equals("") ? "1" : request.getParameter("loginTimes").toString()));
	    	obj.setLastIP(IPTool.getUserIP(request));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return data;
	}
	

	/**
	 * 新增會員
	 * @param data
	 * @return
	 */
	public static String addNewMember(ArrayList<LogonDataModel> data, String verify){

		String MD5Password = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			//第二次防護
			if(checkExist(userData.getEmail())){
				return "userIdEmailExist";
			}
			if(userData.getUserId().trim().length()>0) { //由於userid非必要，故使用者有輸入時才檢查
				if(checkExist(userData.getUserId())){ 
					return "userIdEmailExist";
				}
			}
			
		
			MD5Password = getMD5(userData.getPasswd());
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO memberlist(userId, passwd, email, " +
						" ename, cname, gender, nationality, countryCode, phone, dob, IC, verify, privilege, createDT, registerIP, loginTimes) " +
						" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)");
	
				pstmt.setString(1, userData.getUserId().trim());
				pstmt.setString(2, MD5Password);
				pstmt.setString(3, userData.getEmail().trim());
				pstmt.setString(4, userData.getEname().trim());
				pstmt.setString(5, userData.getCname().trim());
				pstmt.setInt(6, userData.getGender());
				pstmt.setString(7, userData.getNationality().trim());
				pstmt.setString(8, userData.getCountryCode().trim());
				pstmt.setString(9, userData.getPhone().trim());
				pstmt.setString(10, userData.getDob().trim());
				pstmt.setString(11, userData.getIC().trim());
				pstmt.setString(12, verify.trim());
				pstmt.setInt(13, 0);
				pstmt.setString(14, userData.getLastIP());
				pstmt.setInt(15, 1);
	
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
		}

		return "OK";
	}
	
	
	
	/**
	 * 新增職員
	 * @param data
	 * @return
	 */
	public static String addNewStaff(ArrayList<LogonDataModel> data){

		String MD5Password = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			//第二次防護
			if(checkExistStaff(userData.getEmail())){
				return "userIdEmailExist";
			}
			if(userData.getUserId().trim().length()>0) { //由於userid非必要，故使用者有輸入時才檢查
				if(checkExistStaff(userData.getUserId())){ 
					return "userIdEmailExist";
				}
			}
			
		
			MD5Password = getMD5(userData.getPasswd());
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO stafflist (userId, passwd, email, " +
						" ename, cname, gender, nationality, countryCode, phone, dob, IC, privilege, createDT, registerIP, loginTimes) " +
						" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)");
	
				pstmt.setString(1, userData.getUserId().trim());
				pstmt.setString(2, MD5Password);
				pstmt.setString(3, userData.getEmail().trim());
				pstmt.setString(4, userData.getEname().trim());
				pstmt.setString(5, userData.getCname().trim());
				pstmt.setInt(6, userData.getGender());
				pstmt.setString(7, userData.getNationality().trim());
				pstmt.setString(8, userData.getCountryCode().trim());
				pstmt.setString(9, userData.getPhone().trim());
				pstmt.setString(10, userData.getDob().trim());
				pstmt.setString(11, userData.getIC().trim());
				pstmt.setInt(12, 0);
				pstmt.setString(13, userData.getLastIP());
				pstmt.setInt(14, 1);
	
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
		}

		return "OK";
	}
	
	/**
	 * 
	 * @param email
	 * @param verify
	 * @return
	 */
	public static String checkVerifyCode(String email, String verify) {
		
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();
			sql = "SELECT verify, privilege FROM memberlist WHERE email='"+email+"'";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				String verifyCode = rs.getString("verify").toString();
				int privilege = Integer.parseInt(rs.getString("privilege").toString());
				
				if(privilege != 0){
					return "alreadyVerified";
					
				} else if(privilege == 0) {
					if(!verify.trim().equals(verifyCode.trim())){
						return "";
					} else {
						
					}
				} 
				
				
			}
		
		}
		catch(Exception ex){
			ex.printStackTrace();
			return ex.toString();
		}
		finally {
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
		
		return "OK";
	}
	
	/**
	 * 
	 * @param email
	 */
	public static String activateAccount(String email) {
		
		Connection conn = null;
		Statement stmt = null;
		String sql = "";


		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			stmt = conn.createStatement();
			sql = "UPDATE memberlist SET privilege = 1 WHERE email ='"+email+"'";
			
			stmt.executeUpdate(sql);
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			return "";
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
		
		return "success";

	}
	
	
	/**
	 * 更新會員資料
	 * @param data
	 * @param userId
	 * @return
	 */
	protected static int updateMember(ArrayList<LogonDataModel> data, String userId, int priv){

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String column = "";
		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			
			//第二次防護
			if(!userData.getEmail().equals(userData.getEmail_Current())){ //不是正在用著的才要檢查
				if(checkExist(userData.getEmail())){
					return -2; //Email or userId exist...
				}
			}
		
			
			if(priv == 0){
				column = " userId='"+userData.getEmail()+"', email='"+userData.getEmail()+"',";
			}
			
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE memberlist SET " +
					  column +
					  " ename='"+userData.getEname()+"', cname='"+userData.getCname()+"', gender="+userData.getGender()+"," +
					  " nationality='"+userData.getNationality()+"', countryCode='"+userData.getCountryCode()+"', phone='"+userData.getPhone()+"'," +
					  " dob='"+userData.getDob()+"', IC='"+userData.getIC()+"', fbid='"+userData.getFbid()+"' , newsletter='"+userData.getNewsLetter()+"' , ModifyDT=NOW()" +
					  " WHERE email = '" +userData.getEmail_Current()+"' AND userId = '" +userId+"'"; //雙重防護
				
				stmt.executeUpdate(sql);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				return 0;
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

		return 1;
	}
	
	
	
	/**
	 * 更新員工資料
	 * @param data
	 * @param userId
	 * @return
	 */
	protected static int updateStaff(ArrayList<LogonDataModel> data, String userId){

		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			//第二次防護
			if(!userData.getEmail().equals(userData.getEmail_Current())){ //不是正在用著的才要檢查
				if(checkExistStaff(userData.getEmail())){
					return -2; //Email or userId exist...
				}
			}
		


			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE stafflist SET " +
					  " ename='"+userData.getEname()+"', cname='"+userData.getCname()+"', gender="+userData.getGender()+"," +
					  " nationality='"+userData.getNationality()+"', countryCode='"+userData.getCountryCode()+"', phone='"+userData.getPhone()+"'," +
					  " dob='"+userData.getDob()+"', IC='"+userData.getIC()+"', ModifyDT=NOW()" +
					  " WHERE email = '" +userData.getEmail_Current()+"' AND userId = '" +userId+"'"; //雙重防護
				
				stmt.executeUpdate(sql);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				return 0;
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

		return 1;
	}
	
	
	
	/**
	 * 會員更改密碼
	 * 
	 * @param data
	 * @param userId - 用session裡面的userId，以策安全
	 * @return
	 */
	protected static int updatePassword(ArrayList<LogonDataModel> data, String userId){

		String MD5Password = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			
			if(!getMD5(userData.getPasswdOld().trim()).equals(checkPassword(userId))){
				return -1; //舊密碼不符
			}

			MD5Password = getMD5(userData.getPasswd());
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE memberlist SET passwd=N'"+MD5Password+"', modifyDT=NOW() WHERE userId = '" +userId+"'";
				
				stmt.executeUpdate(sql);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				return 0;
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

		return 1;
	}
	
	
	
	/**
	 * 員工更改密碼
	 * 
	 * @param data
	 * @param userId - 用session裡面的userId，以策安全
	 * @return
	 */
	protected static int updateStaffPassword(ArrayList<LogonDataModel> data, String userId){

		String MD5Password = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			
			if(!getMD5(userData.getPasswdOld().trim()).equals(checkStaffPassword(userId))){
				return -1; //舊密碼不符
			}

			MD5Password = getMD5(userData.getPasswd());
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE stafflist SET passwd=N'"+MD5Password+"', modifyDT=NOW() WHERE userId = '" +userId+"'";
				
				stmt.executeUpdate(sql);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				return 0;
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

		return 1;
	}
	

	/**
	 * Update modifyDT (For when updated profile pic)
	 * 
	 * @param userId
	 * @param privilege
	 * @return
	 */
	public static int setModifyDT(String userId, int privilege){

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String[] tablelist = {"memberlist", "partnerlist", "stafflist"};
		int i =  (privilege>=5 && privilege<10 || (privilege==99)) ? 2 : (privilege>=3 && privilege<5) ? 1 : 0;
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }
			stmt = conn.createStatement();
			sql = "UPDATE "+tablelist[i]+" SET modifyDT=NOW() WHERE userId = '" +userId+"'";
			
			stmt.executeUpdate(sql);
		    
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.info("ERROR "+ ex.toString());
			return -1;
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
		
		return 1;
	}
	
	/**
	 * 檢查帳號或email是否已被使用，同時檢查 stafflist，已避免有人魚目混珠
	 * @param input
	 * @return
	 */
	public static boolean checkExist(String input) {

		boolean exist = false;
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return true;
	        }

			stmt = conn.createStatement();
			
			if (EmailValidator.validate(input)) { //是email
				sql = "SELECT a.email FROM memberlist a WHERE a.email = '" + input + "' UNION ALL SELECT b.email FROM stafflist b WHERE b.email = '" + input + "'";
			} else {
				sql = "SELECT a.userId FROM memberlist a WHERE a.userId = '" + input + "' UNION ALL SELECT b.userId FROM stafflist b WHERE b.userId = '" + input + "'";
			}


		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	exist = true;
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

		return exist;
	}
	
	
	/**
	 * 檢查員工帳號或email是否已被使用
	 * @param input
	 * @return
	 */
	public static boolean checkExistStaff(String input) {

		boolean exist = false;
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return true;
	        }

			stmt = conn.createStatement();
			
			if (EmailValidator.validate(input)) { //是email
				sql = "SELECT email FROM stafflist WHERE email = '" + input + "'";
			} else {
				sql = "SELECT userId FROM stafflist WHERE userId = '" + input + "'";
			}


		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	exist = true;
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

		return exist;
	}
	
	
	/**
	 * 檢查會員目前密碼
	 * @param userId
	 * @return
	 */
	private static String checkPassword(String userId) {

		String passwd = "";
		String sql = "";
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

		    sql = "SELECT passwd FROM memberlist WHERE userId='" + userId + "'";

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	passwd = rs.getString("passwd").toString();
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

		return passwd;
	}
	
	
	
	/**
	 * 檢查員工目前密碼
	 * @param userId
	 * @return
	 */
	private static String checkStaffPassword(String userId) {

		String passwd = "";
		String sql = "";
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

		    sql = "SELECT passwd FROM stafflist WHERE userId='" + userId + "'";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	passwd = rs.getString("passwd").toString();
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

		return passwd;
	}
	
	

	/**
	 * 更新會員的密碼
	 * @param email
	 * @param password
	 * @return
	 */
	public static int updateMemberPassword(String userEmail, String password) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String MD5Password = null;
		MD5Password = getMD5(password);

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }

			stmt = conn.createStatement();

			sql = "UPDATE memberlist SET passwd=N'"+MD5Password+"' WHERE email='" +userEmail+"'";

			stmt.executeUpdate(sql);

		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		} finally {
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return 1;
	}
	
	
	/**
	 * 更新員工的密碼
	 * @param email
	 * @param password
	 * @return
	 */
	public static int updateStaffPassword(String userEmail, String password) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String MD5Password = null;
		MD5Password = getMD5(password);

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }

			stmt = conn.createStatement();

			sql = "UPDATE stafflist SET passwd=N'"+MD5Password+"' WHERE email='" +userEmail+"'";

			stmt.executeUpdate(sql);

		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		} finally {
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return 1;
	}

	/**
	 * 更新 partner 的密碼
	 * @param email
	 * @param password
	 * @return
	 */
	public static int updatePartnerPassword(String userEmail, String password) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String MD5Password = null;
		MD5Password = getMD5(password);

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }

			stmt = conn.createStatement();

			sql = "UPDATE partnerlist SET passwd=N'"+MD5Password+"' WHERE email='" +userEmail+"'";

			stmt.executeUpdate(sql);

		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		} finally {
			if(stmt != null){
				try{ stmt.close(); } catch(Exception ex){}
				stmt = null;
			}
			if(conn != null){
				ConnectionManager.closeConnection(conn);
				conn = null;
			}
		}

		return 1;
	}
	
	/**
	 * 登入檢查（會員）
	 * @param user
	 * @param passwd
	 * @return
	 */
	protected static ArrayList<LogonDataModel> verifyMember(String user, String passwd) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
		    String passInput = getMD5(passwd);
		    
		    if(user.trim().length()> 0 && !user.trim().equals("")){
		    	sql = "SELECT mid, ename, cname, userId, email, privilege, stage1, stage2, stage3, stage4, stage5, stage6, stage7 FROM memberlist WHERE email='" + user + "' AND passwd='" + passInput + "'";
		    	
			    rs = stmt.executeQuery(sql);

			    while(rs.next()){
			    	obj = new LogonDataModel();
			    	obj.setMid(Integer.parseInt(rs.getString("mid")== null ? "0" : rs.getString("mid").toString()));
			    	obj.setEname(rs.getString("ename"));
			    	obj.setCname(rs.getString("cname"));
			    	obj.setUserId(rs.getString("userId"));
			    	obj.setEmail(rs.getString("email"));
			    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")== null ? "0" : rs.getString("privilege").toString()));
			    	obj.setStage1(Integer.parseInt(rs.getString("stage1")== null ? "0" : rs.getString("stage1").toString()));
			    	obj.setStage2(Integer.parseInt(rs.getString("stage2")== null ? "0" : rs.getString("stage2").toString()));
			    	obj.setStage3(Integer.parseInt(rs.getString("stage3")== null ? "0" : rs.getString("stage3").toString()));
			    	obj.setStage4(Integer.parseInt(rs.getString("stage4")== null ? "0" : rs.getString("stage4").toString()));
			    	obj.setStage5(Integer.parseInt(rs.getString("stage5")== null ? "0" : rs.getString("stage5").toString()));
			    	obj.setStage6(Integer.parseInt(rs.getString("stage6")== null ? "0" : rs.getString("stage6").toString()));
			    	obj.setStage7(Integer.parseInt(rs.getString("stage7")== null ? "0" : rs.getString("stage7").toString()));
			    	data.add(obj);
			    }
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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
	 * 登入檢查（員工）
	 * @param user
	 * @param passwd
	 * @return
	 */
	protected static ArrayList<LogonDataModel> verifyStaff(String user, String passwd) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
		    String passInput = getMD5(passwd);
		    
		    if(user.trim().length()> 0 && !user.trim().equals("")){
		    	sql = "SELECT * FROM stafflist WHERE email='" + user + "' AND passwd='" + passInput + "'";
		    	
			    rs = stmt.executeQuery(sql);

			    while(rs.next()){
			    	obj = new LogonDataModel();
			    	obj.setSid(Integer.parseInt(rs.getString("sid")== null ? "0" : rs.getString("sid").toString()));
			    	obj.setEname(rs.getString("ename"));
			    	obj.setCname(rs.getString("cname"));
			    	obj.setUserId(rs.getString("userId"));
			    	obj.setEmail(rs.getString("email"));
			    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")== null ? "0" : rs.getString("privilege").toString()));
			    	obj.setStage1(Integer.parseInt(rs.getString("stage1")== null ? "0" : rs.getString("stage1").toString()));
			    	obj.setStage2(Integer.parseInt(rs.getString("stage2")== null ? "0" : rs.getString("stage2").toString()));
			    	obj.setStage3(Integer.parseInt(rs.getString("stage3")== null ? "0" : rs.getString("stage3").toString()));
			    	obj.setStage4(Integer.parseInt(rs.getString("stage4")== null ? "0" : rs.getString("stage4").toString()));
			    	obj.setStage5(Integer.parseInt(rs.getString("stage5")== null ? "0" : rs.getString("stage5").toString()));
			    	obj.setStage6(Integer.parseInt(rs.getString("stage6")== null ? "0" : rs.getString("stage6").toString()));
			    	obj.setStage7(Integer.parseInt(rs.getString("stage7")== null ? "0" : rs.getString("stage7").toString()));
			    	data.add(obj);
			    }
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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
	 * get partner list in JSON string
	 * 
	 * @return
	 */
	public static String partnerListJSON() {

		JSONArray jsonArray = new JSONArray();
		String jsonText = "";
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sqlCond = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
			sqlCond = "WHERE privilege IN (3)"; // 3:agent user; 4:general cargo user
		    
	    	sql = "SELECT pid, userId, ename, cname FROM partnerlist "+sqlCond+" ORDER BY cname, ename ASC";
	    	
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	JSONObject jsonObj = new JSONObject();
				jsonObj.put("pid", Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid").toString()));
				jsonObj.put("userId", rs.getString("userId") == null ? "" : rs.getString("userId").toString());
				jsonObj.put("ename", rs.getString("ename") == null ? "" : rs.getString("ename").toString());
				jsonObj.put("cname", rs.getString("cname") == null ? "" : rs.getString("cname").toString());
				jsonArray.add(jsonObj);
		    }
		    
		    if(jsonArray != null && jsonArray.size() > 0){
		    	jsonText = "{\"partnerdata\":"+ JSONValue.toJSONString(jsonArray) +"}";
		    }else{
			    jsonText = "noData";
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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

		return jsonText;
	}
	
	
	
	/**
	 * 找出所有在職司機供產生清單
	 * 
	 * @return
	 */
	public static ArrayList<LogonDataModel> driverList() {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
		    
	    	sql = "SELECT sid, userId, ename, cname FROM stafflist WHERE privilege = 5 ORDER BY cname, ename ASC";
	    	
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setSid(Integer.parseInt(rs.getString("sid").toString()));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setEname(rs.getString("ename"));
		    	obj.setCname(rs.getString("cname"));
		    	data.add(obj);
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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
	 * 找出所有在職司機供產生清單 (JSON)
	 * 
	 * @return
	 */
	public static String driverListJSON() {

		JSONArray jsonArray = new JSONArray();
		String jsonText = "";
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
		    
	    	sql = "SELECT sid, userId, ename, cname FROM stafflist WHERE privilege = 5 ORDER BY cname, ename ASC";
	    	
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	JSONObject jsonObj = new JSONObject();
				jsonObj.put("sid", Integer.parseInt(rs.getString("sid") == null ? "0" : rs.getString("sid").toString()));
				jsonObj.put("userId", rs.getString("userId") == null ? "" : rs.getString("userId").toString());
				jsonObj.put("ename", rs.getString("ename") == null ? "" : rs.getString("ename").toString());
				jsonObj.put("cname", rs.getString("cname") == null ? "" : rs.getString("cname").toString());
				jsonArray.add(jsonObj);
		    }
		    
		    if(jsonArray != null && jsonArray.size() > 0){
		    	jsonText = "{\"driversdata\":"+ JSONValue.toJSONString(jsonArray) +"}";
		    }else{
			    jsonText = "noData";
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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

		return jsonText;
	}
	
	
	/**
	 * 找出所有在職職員供產生清單
	 * 
	 * @return
	 */
	public static ArrayList<LogonDataModel> staffList() {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data1 = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
		    
	    	sql = "SELECT sid, userId, ename, cname FROM stafflist WHERE privilege >= 5 ORDER BY cname, ename ASC";
	    	
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setSid(Integer.parseInt(rs.getString("sid").toString()));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setEname(rs.getString("ename"));
		    	obj.setCname(rs.getString("cname"));
		    	data1.add(obj);
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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

		return data1;
	}
	
	/**
	 * Find Staff userId by SID
	 * 
	 * @param sid
	 * @return
	 */
	public static String getStaffIdById(int sid) {

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String userId = "noData";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
		    
	    	sql = "SELECT userId FROM stafflist WHERE sid = "+sid;
	    	
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	userId = rs.getString("userId").toString();
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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

		return userId;
	}
	
	/**
	 * Find Partner userId by PID
	 * 
	 * @param sid
	 * @return
	 */
	public static String getPartnerIdById(int pid) {

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String userId = "noData";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
		    
		    
	    	sql = "SELECT userId FROM partnerlist WHERE pid = "+pid;
	    	
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	userId = rs.getString("userId").toString();
		    }
		      
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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

		return userId;
	}
	
	
	/**
	 * 依據userId取得使用者等級
	 * 9: administrator
	 * 8: system engineer
	 * 7: customer service
	 * 6: part timer
	 * 5: partner
	 * 4: reseller
	 * 3: (reserved)
	 * 2: (reserved)
	 * 1: member
	 * 0: waiting for verification
	 * -9: visitors/suspended account
	 * @param userId
	 * @return
	 */
	public static int getPrivilegeByUserId(String userId) {

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		int privilege = -9; //找不到 或為未註冊

		if(!userId.equals("")){

			try {
//				logger.info("... check privilege : " + userId);
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return -9;
		        }

				stmt = conn.createStatement();
			    sql = "SELECT privilege FROM memberlist WHERE userId='" + userId + "' OR email='" + userId + "'";
			    rs = stmt.executeQuery(sql);
	
			    if(rs.next()){
			    	privilege = Integer.parseInt(rs.getString("privilege")== null ? "-9" : rs.getString("privilege").toString());
			    }

			} catch (Exception ex) {
				ex.printStackTrace();
				return -9;
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
		}

		return privilege;
	}
	
	
	/**
	 * 依據userId取得職員的等級
	 * 9: administrator
	 * 8: system engineer
	 * 7: customer service
	 * 6: part timer
	 * 5: partner
	 * 4: reseller
	 * 3: (reserved)
	 * 2: (reserved)
	 * 1: member
	 * 0: waiting for verification
	 * -9: visitor/suspended account
	 * @param userId
	 * @return
	 */
	public static int getStaffPrivilegeByUserId(String userId) {

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		int privilege = -9; //找不到 或為未註冊

		if(!userId.equals("")){

			try {
//				logger.info("... check privilege : " + userId);
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }

				stmt = conn.createStatement();
			    sql = "SELECT privilege FROM stafflist WHERE userId='" + userId + "' OR email='" + userId + "'";
			    rs = stmt.executeQuery(sql);
	
			    if(rs.next()){
			    	privilege = Integer.parseInt(rs.getString("privilege")== null ? "-9" : rs.getString("privilege").toString());
			    }

			} catch (Exception ex) {
				ex.printStackTrace();
				return 0;
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
		}

		return privilege;
	}

	
	/**
	 * Get any info by ID (from member, partner, staff list)
	 * @param column
	 * @param table
	 * @param userId
	 * @return
	 */
	public static String getDataById(String column, String table, String userId) {

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String[] tableList = {"memberlist", "partnerlist", "stafflist"};
		String returnData = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			if(!table.trim().equals("")){
				
				if(column.trim().equals("id") || column.trim().equals("mid") || column.trim().equals("pid") || column.trim().equals("sid")){
					column = table.substring(0,1) + "id";
				}
				
			    sql = "SELECT "+column+" FROM "+table+" WHERE userId='" + userId + "' OR email='" + userId + "'";
			    rs = stmt.executeQuery(sql);
	
			    if(rs.next()){
			    	returnData = rs.getString(column) == null ? "" : rs.getString(column).toString();
			    }
				
			}else{
				for(int i=0;i< tableList.length ;i++){
					if(returnData.equals("")){
						
						if(column.trim().equals("id") || column.trim().equals("mid") || column.trim().equals("pid") || column.trim().equals("sid")){
							column = tableList[i].substring(0,1) + "id";
						}
						
					    sql = "SELECT "+column+" FROM "+tableList[i]+" WHERE userId='" + userId + "' OR email='" + userId + "'";
					    rs = stmt.executeQuery(sql);
			
					    if(rs.next()){
					    	returnData = rs.getString(column) == null ? "" : rs.getString(column).toString();
					    }
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			return ex.toString();
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

		return returnData;
	}

	
	/**
	 * Get any info by firebase token (from member, partner, staff list)
	 * @param column
	 * @param table
	 * @param userId
	 * @return
	 */
	public static String getDataByToken(String column, String table, String token) {

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String returnData = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			if(!table.trim().equals("")){
				
				if(column.trim().equals("id") || column.trim().equals("mid") || column.trim().equals("pid") || column.trim().equals("sid")){
					column = table.substring(0,1) + "id";
				}
				
			    sql = "SELECT "+column+" FROM "+table+" WHERE tokenFCM='" + token + "'";
			    rs = stmt.executeQuery(sql);
	
			    if(rs.next()){
			    	returnData = rs.getString(column) == null ? "" : rs.getString(column).toString();
			    }
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			return ex.toString();
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

		return returnData;
	}
	
	
	/**
	 * 取得會員資料
	 * @param userId
	 * @param email
	 * @return
	 */
	public static ArrayList<LogonDataModel> getMemberData(String user) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String column = "userId";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			if (EmailValidator.validate(user)) { //是email
				column = "email";
			}
			
			sql = "SELECT * FROM memberlist WHERE "+column+" = '" + user + "'";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setUserId(rs.getString("userId")== null ? "" : rs.getString("userId").toString());
		    	obj.setUserId_Current(rs.getString("userId")== null ? "" : rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email")== null ? "" : rs.getString("email").toString());
		    	obj.setEmail_Current(rs.getString("email")== null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")== null ? "" : rs.getString("cname").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender")== null ? "0" : rs.getString("gender").toString()));
		    	obj.setNationality(rs.getString("nationality")== null ? "" : rs.getString("nationality").toString());
		    	obj.setCountryCode(rs.getString("countryCode")== null ? "" : rs.getString("countryCode").toString());
		    	obj.setPhone(rs.getString("phone")== null ? "" : rs.getString("phone").toString());
		    	obj.setDob(rs.getString("dob")== null ? "" : rs.getString("dob").toString());
		    	obj.setIC(rs.getString("IC")== null ? "" : rs.getString("IC").toString());
		    	obj.setFbid(rs.getString("fbid") == null ? "" : rs.getString("fbid"));
		    	obj.setNewsLetter(Integer.parseInt(rs.getString("newsletter") == null ? "0" : rs.getString("newsletter").toString()));
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")== null ? "-9" : rs.getString("privilege").toString()));
		    	obj.setAccNo(rs.getString("accNo") == null ? "" : rs.getString("accNo"));
		    	obj.setDiscPack(rs.getString("discPack") == null ? "" : rs.getString("discPack"));
		    	obj.setStage1(Integer.parseInt(rs.getString("stage1")== null ? "0" : rs.getString("stage1").toString()));
		    	obj.setStage2(Integer.parseInt(rs.getString("stage2")== null ? "0" : rs.getString("stage2").toString()));
		    	obj.setStage3(Integer.parseInt(rs.getString("stage3")== null ? "0" : rs.getString("stage3").toString()));
		    	obj.setStage4(Integer.parseInt(rs.getString("stage4")== null ? "0" : rs.getString("stage4").toString()));
		    	obj.setStage5(Integer.parseInt(rs.getString("stage5")== null ? "0" : rs.getString("stage5").toString()));
		    	obj.setStage6(Integer.parseInt(rs.getString("stage6")== null ? "0" : rs.getString("stage6").toString()));
		    	obj.setStage7(Integer.parseInt(rs.getString("stage7")== null ? "0" : rs.getString("stage7").toString()));
		    	obj.setLastLoginDT(rs.getString("lastLoginDT")==null ? "" : rs.getString("lastLoginDT").toString());
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
	 * 取得員工資料
	 * @param userId
	 * @param email
	 * @return
	 */
	public static ArrayList<LogonDataModel> getStaffData(String user) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String column = "userId";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			if (EmailValidator.validate(user)) { //是email
				column = "email";
			}
			
			sql = "SELECT userId, email, ename, cname, gender, nationality, countryCode, phone, dob, IC, privilege," +
					" stage1, stage2, stage3, stage4, stage5, stage6, stage7 FROM stafflist WHERE "+column+" = '" + user + "'";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setUserId(rs.getString("userId")== null ? "" : rs.getString("userId").toString());
		    	obj.setUserId_Current(rs.getString("userId")== null ? "" : rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email")== null ? "" : rs.getString("email").toString());
		    	obj.setEmail_Current(rs.getString("email")== null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")== null ? "" : rs.getString("cname").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender")== null ? "0" : rs.getString("gender").toString()));
		    	obj.setNationality(rs.getString("nationality")== null ? "" : rs.getString("nationality").toString());
		    	obj.setCountryCode(rs.getString("countryCode")== null ? "" : rs.getString("countryCode").toString());
		    	obj.setPhone(rs.getString("phone")== null ? "" : rs.getString("phone").toString());
		    	obj.setDob(rs.getString("dob")== null ? "" : rs.getString("dob").toString());
		    	obj.setIC(rs.getString("IC")== null ? "" : rs.getString("IC").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")== null ? "-9" : rs.getString("privilege").toString()));
		    	obj.setStage1(Integer.parseInt(rs.getString("stage1")== null ? "0" : rs.getString("stage1").toString()));
		    	obj.setStage2(Integer.parseInt(rs.getString("stage2")== null ? "0" : rs.getString("stage2").toString()));
		    	obj.setStage3(Integer.parseInt(rs.getString("stage3")== null ? "0" : rs.getString("stage3").toString()));
		    	obj.setStage4(Integer.parseInt(rs.getString("stage4")== null ? "0" : rs.getString("stage4").toString()));
		    	obj.setStage5(Integer.parseInt(rs.getString("stage5")== null ? "0" : rs.getString("stage5").toString()));
		    	obj.setStage6(Integer.parseInt(rs.getString("stage6")== null ? "0" : rs.getString("stage6").toString()));
		    	obj.setStage7(Integer.parseInt(rs.getString("stage7")== null ? "0" : rs.getString("stage7").toString()));
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
	
	
	
	public static String getMD5(String str){
		String MD5Password = null;

	    try{
	    	MessageDigest md = MessageDigest.getInstance("MD5");
	    	md.update(str.getBytes());
	    	byte[] digest = md.digest(); 
	    	
	    	final StringBuffer buffer = new StringBuffer();
	    	for (int i = 0; i < digest.length; ++i)
	    	{
	    	    final byte b = digest[i];
	    	    final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
	    	    buffer.append(value < 16 ? "0" : "");
	    	    buffer.append(Integer.toHexString(value));
	    	}
	    	//logger.info(buffer.toString());
	    	MD5Password = buffer.toString();
	    }
	    catch(Exception e){
	    	
	    }
	    return MD5Password;
		
	}

	
	/**
	 * 更新user的最後登入IP & 日期
	 * @param user - 可以是 userId 或 email
	 * @param lastIP
	 */
	public static void updateIP(String user, String lastIP) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();

			sql = "UPDATE memberlist SET lastIP = '"+lastIP+"', lastLoginDT=NOW(), loginTimes = loginTimes + 1 WHERE userId='" +user+"' OR email='" + user + "'";

			stmt.executeUpdate(sql);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
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
	
	
	
	/**
	 * 更新staff的最後登入IP & 日期
	 * @param user - 可以是 userId 或 email
	 * @param lastIP
	 */
	public static void updateStaffIP(String user, String lastIP) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();

			sql = "UPDATE stafflist SET lastIP = '"+lastIP+"', lastLoginDT=NOW(), loginTimes = loginTimes + 1 WHERE userId='" +user+"' OR email='" + user + "'";

			stmt.executeUpdate(sql);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
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
	
	

	/**
	 * 會員管理 - 供管理員查詢
	 * @return
	 */
	public static ArrayList<LogonDataModel> memberList(String orderBy, int pageNo) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		int page = 0;
		int numberPerPage = 20; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,20; 第二頁,則20,20;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.*, " +
					
					" (SELECT COUNT(mid) FROM memberlist) AS total, b.totalBooking" +
					" FROM memberlist a LEFT JOIN (" +
					" SELECT userId, SUM(subtotal) AS totalBooking" +
					"   FROM ( SELECT userId, COUNT(*) AS subtotal FROM consignment GROUP BY userId) AS d" +
					"   GROUP BY userId" +
					" ) as b ON a.userId = b.userId";
					
					if(orderBy.equals("totalBooking")){
						sql += " ORDER BY b.totalBooking DESC LIMIT " + page + ", " + numberPerPage;
					} else {
						sql += " ORDER BY a." + orderBy + " DESC LIMIT " + page + ", " + numberPerPage;
					}

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setMid(Integer.parseInt(rs.getString("mid").toString()));
		    	obj.setUserId(rs.getString("userId")==null ? "" : rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email")==null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")==null ? "" : rs.getString("cname").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender")==null ? "0" : rs.getString("gender").toString()));
		    	obj.setNationality(rs.getString("nationality")== null ? "" : rs.getString("nationality").toString());
		    	obj.setCountryCode(rs.getString("countryCode")== null ? "" : rs.getString("countryCode").toString());
		    	obj.setPhone(rs.getString("phone")==null ? "" : rs.getString("phone").toString());
		    	obj.setDob(rs.getString("dob")== null ? "" : rs.getString("dob").toString());
		    	obj.setIC(rs.getString("IC")== null ? "" : rs.getString("IC").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")==null ? "0" : rs.getString("privilege").toString()));
		    	obj.setAccNo(rs.getString("accNo") == null ? "" : rs.getString("accNo"));
		    	obj.setDiscPack(rs.getString("discPack") == null ? "" : rs.getString("discPack"));
		    	obj.setStage1(Integer.parseInt(rs.getString("stage1")==null ? "0" : rs.getString("stage1").toString()));
		    	obj.setStage2(Integer.parseInt(rs.getString("stage2")==null ? "0" : rs.getString("stage2").toString()));
		    	obj.setStage3(Integer.parseInt(rs.getString("stage3")==null ? "0" : rs.getString("stage3").toString()));
		    	obj.setStage4(Integer.parseInt(rs.getString("stage4")==null ? "0" : rs.getString("stage4").toString()));
		    	obj.setStage5(Integer.parseInt(rs.getString("stage5")==null ? "0" : rs.getString("stage5").toString()));
		    	obj.setStage6(Integer.parseInt(rs.getString("stage6")==null ? "0" : rs.getString("stage6").toString()));
		    	obj.setStage7(Integer.parseInt(rs.getString("stage7")==null ? "0" : rs.getString("stage7").toString()));
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setModifyDT(rs.getString("modifyDT")==null ? "" : rs.getString("modifyDT").toString());
		    	obj.setRegisterIP(rs.getString("registerIP")==null ? "" : rs.getString("registerIP").toString());
		    	obj.setLastIP(rs.getString("lastIP")==null ? "" : rs.getString("lastIP").toString());
		    	obj.setLastLoginDT(rs.getString("lastLoginDT")==null ? "" : rs.getString("lastLoginDT").toString());
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")==null ? "1" : rs.getString("loginTimes").toString()));
		    	obj.setTotal(Integer.parseInt(rs.getString("total").toString()));
		    	obj.setTotalBooking(Integer.parseInt(rs.getString("totalBooking")==null ? "0" : rs.getString("totalBooking").toString()));
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
	 * 員工管理 - 供管理員查詢
	 * @return
	 */
	public static ArrayList<LogonDataModel> staffList(String orderBy, int pageNo) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		int page = 0;
		int numberPerPage = 20; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,20; 第二頁,則20,20;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.*, " +

					" (SELECT name_enUS FROM area WHERE aid = a.aid) AS areaName," +
					" (SELECT COUNT(sid) FROM stafflist) AS total, b.totalBooking" +
					" FROM stafflist a LEFT JOIN (" +
					" SELECT userId, SUM(subtotal) AS totalBooking" +
					"   FROM ( SELECT userId, COUNT(*) AS subtotal FROM consignment GROUP BY userId) AS d" +
					"   GROUP BY userId" +
					" ) as b ON a.userId = b.userId";
					
					if(orderBy.equals("totalBooking")){
						sql += " ORDER BY b.totalBooking DESC LIMIT " + page + ", " + numberPerPage;
					} else {
						sql += " ORDER BY a." + orderBy + " DESC LIMIT " + page + ", " + numberPerPage;
					}

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setSid(Integer.parseInt(rs.getString("sid").toString()));
		    	obj.setUserId(rs.getString("userId")==null ? "" : rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email")==null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")==null ? "" : rs.getString("cname").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender")==null ? "0" : rs.getString("gender").toString()));
		    	obj.setNationality(rs.getString("nationality")== null ? "" : rs.getString("nationality").toString());
		    	obj.setCountryCode(rs.getString("countryCode")== null ? "" : rs.getString("countryCode").toString());
		    	obj.setPhone(rs.getString("phone")==null ? "" : rs.getString("phone").toString());
		    	obj.setDob(rs.getString("dob")== null ? "" : rs.getString("dob").toString());
		    	obj.setIC(rs.getString("IC")== null ? "" : rs.getString("IC").toString());
		    	obj.setAid(Integer.parseInt(rs.getString("aid")== null ? "0" : rs.getString("aid").toString()));
		    	obj.setAreaName(rs.getString("areaName")== null ? "" : rs.getString("areaName").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")==null ? "0" : rs.getString("privilege").toString()));
		    	obj.setStage1(Integer.parseInt(rs.getString("stage1")==null ? "0" : rs.getString("stage1").toString()));
		    	obj.setStage2(Integer.parseInt(rs.getString("stage2")==null ? "0" : rs.getString("stage2").toString()));
		    	obj.setStage3(Integer.parseInt(rs.getString("stage3")==null ? "0" : rs.getString("stage3").toString()));
		    	obj.setStage4(Integer.parseInt(rs.getString("stage4")==null ? "0" : rs.getString("stage4").toString()));
		    	obj.setStage5(Integer.parseInt(rs.getString("stage5")==null ? "0" : rs.getString("stage5").toString()));
		    	obj.setStage6(Integer.parseInt(rs.getString("stage6")==null ? "0" : rs.getString("stage6").toString()));
		    	obj.setStage7(Integer.parseInt(rs.getString("stage7")==null ? "0" : rs.getString("stage7").toString()));
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setModifyDT(rs.getString("modifyDT")==null ? "" : rs.getString("modifyDT").toString());
		    	obj.setRegisterIP(rs.getString("registerIP")==null ? "" : rs.getString("registerIP").toString());
		    	obj.setLastIP(rs.getString("lastIP")==null ? "" : rs.getString("lastIP").toString());
		    	obj.setLastLoginDT(rs.getString("lastLoginDT")==null ? "" : rs.getString("lastLoginDT").toString());
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")==null ? "1" : rs.getString("loginTimes").toString()));
		    	obj.setTotal(Integer.parseInt(rs.getString("total").toString()));
		    	obj.setTotalBooking(Integer.parseInt(rs.getString("totalBooking")==null ? "0" : rs.getString("totalBooking").toString()));
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
	 * 供管理員搜尋會員
	 * @param searchObj
	 * @return
	 */
	protected static ArrayList<LogonDataModel> searchMember(String searchObj) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String sqlCond = " WHERE a.userId LIKE '%"+searchObj+"%' OR a.ename LIKE '%"+searchObj+"%' OR a.email LIKE '%"+searchObj+"%' OR a.phone LIKE '%"+searchObj+"%'";
		
		//如果是email，則搜尋email欄位
		String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		CharSequence inputStr = searchObj;  
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(inputStr);  

		if(matcher.matches()){
			sqlCond = "a.email = '"+searchObj+"'";
		}

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.*, " +
					" (SELECT COUNT(mid) FROM memberlist) AS total, b.totalBooking" +
					" FROM memberlist a LEFT JOIN (" +
					" SELECT userId, SUM(subtotal) AS totalBooking" +
					"   FROM ( SELECT userId, COUNT(*) AS subtotal FROM consignment GROUP BY userId) AS d" +
					"  GROUP BY userId" +
					" ) as b ON a.userId = b.userId" + sqlCond + " ORDER BY a.mid DESC";
			
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setMid(Integer.parseInt(rs.getString("mid").toString()));
		    	obj.setUserId(rs.getString("userId")==null ? "" : rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email")==null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")==null ? "" : rs.getString("cname").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender")==null ? "0" : rs.getString("gender").toString()));
		    	obj.setNationality(rs.getString("nationality")== null ? "" : rs.getString("nationality").toString());
		    	obj.setCountryCode(rs.getString("countryCode")== null ? "" : rs.getString("countryCode").toString());
		    	obj.setPhone(rs.getString("phone")==null ? "" : rs.getString("phone").toString());
		    	obj.setDob(rs.getString("dob")== null ? "" : rs.getString("dob").toString());
		    	obj.setIC(rs.getString("IC")== null ? "" : rs.getString("IC").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")==null ? "0" : rs.getString("privilege").toString()));
		    	obj.setAccNo(rs.getString("accNo")== null ? "" : rs.getString("accNo").toString());
		    	obj.setDiscPack(rs.getString("discPack") == null ? "" : rs.getString("discPack"));
		    	obj.setStage1(Integer.parseInt(rs.getString("stage1")==null ? "0" : rs.getString("stage1").toString()));
		    	obj.setStage2(Integer.parseInt(rs.getString("stage2")==null ? "0" : rs.getString("stage2").toString()));
		    	obj.setStage3(Integer.parseInt(rs.getString("stage3")==null ? "0" : rs.getString("stage3").toString()));
		    	obj.setStage4(Integer.parseInt(rs.getString("stage4")==null ? "0" : rs.getString("stage4").toString()));
		    	obj.setStage5(Integer.parseInt(rs.getString("stage5")==null ? "0" : rs.getString("stage5").toString()));
		    	obj.setStage6(Integer.parseInt(rs.getString("stage6")==null ? "0" : rs.getString("stage6").toString()));
		    	obj.setStage7(Integer.parseInt(rs.getString("stage7")==null ? "0" : rs.getString("stage7").toString()));
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setModifyDT(rs.getString("modifyDT")==null ? "" : rs.getString("modifyDT").toString());
		    	obj.setRegisterIP(rs.getString("registerIP")==null ? "" : rs.getString("registerIP").toString());
		    	obj.setLastIP(rs.getString("lastIP")==null ? "" : rs.getString("lastIP").toString());
		    	obj.setLastLoginDT(rs.getString("lastLoginDT")==null ? "" : rs.getString("lastLoginDT").toString());
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")==null ? "1" : rs.getString("loginTimes").toString()));
		    	obj.setTotalBooking(Integer.parseInt(rs.getString("totalBooking")==null ? "0" : rs.getString("totalBooking").toString()));
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
	 * 供管理員搜尋職員
	 * @param searchObj
	 * @return
	 */
	protected static ArrayList<LogonDataModel> searchStaff(String searchObj) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String sqlCond = " WHERE a.userId LIKE '%"+searchObj+"%' OR a.ename LIKE '%"+searchObj+"%' OR a.email LIKE '%"+searchObj+"%' OR a.phone LIKE '%"+searchObj+"%'";
		
		//如果是email，則搜尋email欄位
		String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		CharSequence inputStr = searchObj;  
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(inputStr);  

		if(matcher.matches()){
			sqlCond = "a.email = '"+searchObj+"'";
		}

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.*, (SELECT COUNT(mid) FROM memberlist) AS total FROM stafflist a" + sqlCond + " ORDER BY a.sid DESC";
			
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setSid(Integer.parseInt(rs.getString("sid").toString()));
		    	obj.setAid(Integer.parseInt(rs.getString("aid").toString()));
		    	obj.setUserId(rs.getString("userId")==null ? "" : rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email")==null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")==null ? "" : rs.getString("cname").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender")==null ? "0" : rs.getString("gender").toString()));
		    	obj.setNationality(rs.getString("nationality")== null ? "" : rs.getString("nationality").toString());
		    	obj.setCountryCode(rs.getString("countryCode")== null ? "" : rs.getString("countryCode").toString());
		    	obj.setPhone(rs.getString("phone")==null ? "" : rs.getString("phone").toString());
		    	obj.setDob(rs.getString("dob")== null ? "" : rs.getString("dob").toString());
		    	obj.setIC(rs.getString("IC")== null ? "" : rs.getString("IC").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")==null ? "0" : rs.getString("privilege").toString()));
		    	obj.setStage1(Integer.parseInt(rs.getString("stage1")==null ? "0" : rs.getString("stage1").toString()));
		    	obj.setStage2(Integer.parseInt(rs.getString("stage2")==null ? "0" : rs.getString("stage2").toString()));
		    	obj.setStage3(Integer.parseInt(rs.getString("stage3")==null ? "0" : rs.getString("stage3").toString()));
		    	obj.setStage4(Integer.parseInt(rs.getString("stage4")==null ? "0" : rs.getString("stage4").toString()));
		    	obj.setStage5(Integer.parseInt(rs.getString("stage5")==null ? "0" : rs.getString("stage5").toString()));
		    	obj.setStage6(Integer.parseInt(rs.getString("stage6")==null ? "0" : rs.getString("stage6").toString()));
		    	obj.setStage7(Integer.parseInt(rs.getString("stage7")==null ? "0" : rs.getString("stage7").toString()));
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setModifyDT(rs.getString("modifyDT")==null ? "" : rs.getString("modifyDT").toString());
		    	obj.setRegisterIP(rs.getString("registerIP")==null ? "" : rs.getString("registerIP").toString());
		    	obj.setLastIP(rs.getString("lastIP")==null ? "" : rs.getString("lastIP").toString());
		    	obj.setLastLoginDT(rs.getString("lastLoginDT")==null ? "" : rs.getString("lastLoginDT").toString());
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")==null ? "1" : rs.getString("loginTimes").toString()));
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
	 * 供管理員根據 pid 搜尋 partner
	 * @param pid
	 * @return
	 */
	public static ArrayList<LogonDataModel> searchPartnerByPid (int pid) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.* FROM partnerlist a WHERE a.pid = " + pid;
			
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid").toString()));
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setUserId(rs.getString("userId")==null ? "" : rs.getString("userId").toString());
		    	obj.setContactPerson(rs.getString("contactPerson")==null ? "" : rs.getString("contactPerson").toString());
		    	obj.setEmail(rs.getString("email")==null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")==null ? "" : rs.getString("cname").toString());
		    	obj.setWebsite(rs.getString("website")==null ? "" : rs.getString("website").toString());
		    	obj.setOfficialEmail(rs.getString("officialEmail")==null ? "" : rs.getString("officialEmail").toString());
		    	obj.setPhone(rs.getString("phone")==null ? "" : rs.getString("phone").toString());
		    	obj.setAddress(rs.getString("address")==null ? "" : rs.getString("address").toString());
		    	obj.setCompanyLicense(rs.getString("companyLicense")==null ? "" : rs.getString("companyLicense").toString());
		    	obj.setGst(rs.getString("gst")==null ? "" : rs.getString("gst").toString());
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setModifyDT(rs.getString("modifyDT")==null ? "" : rs.getString("modifyDT").toString());
		    	obj.setRegisterIP(rs.getString("registerIP")==null ? "" : rs.getString("registerIP").toString());
		    	obj.setLastIP(rs.getString("lastIP")==null ? "" : rs.getString("lastIP").toString());
		    	obj.setLastLoginDT(rs.getString("lastLoginDT")==null ? "" : rs.getString("lastLoginDT").toString());
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")==null ? "1" : rs.getString("loginTimes").toString()));
//		    	obj.setTotalBooking(Integer.parseInt(rs.getString("totalBooking")==null ? "0" : rs.getString("totalBooking").toString()));
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
	 * 這是做啥用
	 * 
	 * @param privFilter
	 * @return
	 */
	public static ArrayList<LogonDataModel> searchPrivMember(int privFilter) {

		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		LogonDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			sql = "SELECT * FROM memberlist WHERE privilege =" + privFilter;
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	
		    	obj.setMid(Integer.parseInt(rs.getString("mid").toString()));
		    	obj.setEname(rs.getString("ename"));
		    	obj.setCname(rs.getString("cname"));
		    	obj.setEmail(rs.getString("email"));
		    	obj.setPhone(rs.getString("phone"));
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege").toString()));
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")));
		    	
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
	
	
	public static ArrayList<LogonDataModel> checkMemberList(int privFilter) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
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
			if(privFilter == -1){
				stmt = conn.createStatement();
				
				sql = "SELECT * FROM memberlist";
						
				rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	obj = new LogonDataModel();
			    	obj.setUserId(rs.getString("userId") == null ? "" : rs.getString("userId"));
			    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email"));
			    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename"));
			    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname"));
			    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "0" : rs.getString("gender").toString()));
			    	obj.setNationality(rs.getString("nationality") == null ? "" : rs.getString("nationality").toString());
			    	obj.setCountryCode(rs.getString("countryCode") == null ? "" : rs.getString("countryCode").toString());
			    	obj.setPhone(rs.getString("phone") == null ? "" : rs.getString("phone").toString());
			    	obj.setDob(rs.getString("dob") == null ? "" : rs.getString("dob").toString());
			    	obj.setIC(rs.getString("IC") == null ? "" : rs.getString("IC").toString());
			    	obj.setFbid(rs.getString("fbid") == null ? "" : rs.getString("fbid").toString());
			    	obj.setNewsLetter(Integer.parseInt(rs.getString("newsletter") == null ? "1" : rs.getString("newsletter").toString()));
			    	
			    	data.add(obj);
			    }
				
			}else{
				stmt = conn.createStatement();
					
				sql = "SELECT * FROM memberlist WHERE privilege =" + privFilter;
						
				rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	obj = new LogonDataModel();
			    	obj.setUserId(rs.getString("userId") == null ? "" : rs.getString("userId"));
			    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email"));
			    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename"));
			    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname"));
			    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "0" : rs.getString("gender").toString()));
			    	obj.setNationality(rs.getString("nationality") == null ? "" : rs.getString("nationality").toString());
			    	obj.setCountryCode(rs.getString("countryCode") == null ? "" : rs.getString("countryCode").toString());
			    	obj.setPhone(rs.getString("phone") == null ? "" : rs.getString("phone").toString());
			    	obj.setDob(rs.getString("dob") == null ? "" : rs.getString("dob").toString());
			    	obj.setIC(rs.getString("IC") == null ? "" : rs.getString("IC").toString());
			    	obj.setFbid(rs.getString("fbid") == null ? "" : rs.getString("fbid").toString());
			    	obj.setNewsLetter(Integer.parseInt(rs.getString("newsletter") == null ? "1" : rs.getString("newsletter").toString()));
			    	
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
     * 產生 Excel 檔案
     * 
     * @param request
     * @param accountData
     * @param extension: xls, xlsx
     * @return
     * @throws Exception
     */
    public static String exportMemberList (HttpServletRequest request, ArrayList<LogonDataModel> data, String extension) {

    	String result = "";
    	Workbook wb;

        if(extension.equals("xls")) {
        	wb = new HSSFWorkbook();
        } else {
        	wb = new XSSFWorkbook();
        }
        
        try {
        	

	        Map<String, CellStyle> styles = createStyles(wb);
	        Sheet sheet = wb.createSheet("Member1"); //Sheet名稱
	        sheet.setDisplayGridlines(true);
	
	        sheet.setFitToPage(false);
	        sheet.setHorizontallyCenter(false);
	        sheet.setMargin(Sheet.TopMargin, 0.3 /* inches */ );
	        sheet.setMargin(Sheet.BottomMargin, 0.3 /* inches */ );
	        sheet.setMargin(Sheet.LeftMargin, 0.2 /* inches */ );
	        sheet.setMargin(Sheet.RightMargin, 0.1 /* inches */ );
	        
	        sheet.setColumnWidth(0, 5*256); //No.
	        sheet.setColumnWidth(1, 25*256); //User ID
	        sheet.setColumnWidth(2, 25*256); //Email
	        sheet.setColumnWidth(3, 15*256); //Name
	        sheet.setColumnWidth(4, 8*256); //Nickname
	        sheet.setColumnWidth(5, 8*256); //Gender
	        sheet.setColumnWidth(6, 13*256); //Nationality
	        sheet.setColumnWidth(7, 14*256); //Country Code
	        sheet.setColumnWidth(8, 14*256); //Phone Number
	        sheet.setColumnWidth(9, 15*256); //Date of Birth
	        sheet.setColumnWidth(10, 15*256); //IC
	        sheet.setColumnWidth(11, 13*256); //Facebook ID
	        sheet.setColumnWidth(12, 12*256); //Newsletter
	        
	        
	        LogonDataModel logonData = new LogonDataModel();
	        if(data != null && data.size() > 0){
	        	logonData = (LogonDataModel)data.get(0);
	        }
	        
	        String[] titles = {
	                "No.", "User ID", "Email", "Name", "Nickname", "Gender", "Nationality", "Country Code", "Phone Number", "Date of Birth",
	                "IC", "Facebook ID", "Newsletter"
	        };
	        
	        
            
	        //item row
	        Row itemRow = sheet.createRow(1);
	        itemRow.setHeightInPoints(35);
	        Cell itemCell;
	        for (int i = 0; i < titles.length; i++) {
	        	itemCell = itemRow.createCell(i);
	        	itemCell.setCellValue(titles[i]);
	        	itemCell.setCellStyle(styles.get("item"));
	        }
	
	        int rownum = 2;
	        int num = 0;
	        String numTxt = "";
			
	        logonData = new LogonDataModel();
        	if(data != null && data.size() > 0){
        		for (int i = 0; i < data.size(); i++) {
        			logonData = (LogonDataModel)data.get(i);
        			
        			num++;
					numTxt = String.valueOf(num);
					
        			
            		Row row = sheet.createRow(rownum++);
            		row.setHeightInPoints(15);
            		
                    for (int j = 0; j < titles.length; j++) {
                        Cell cell = row.createCell(j);
                        if(j == 0) {
                        	cell.setCellValue(numTxt);
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 1) {
                	        cell.setCellValue(logonData.getUserId());
                	        cell.setCellStyle(styles.get("text_left"));
                        }
                        
                        else if(j == 2) {
                	        cell.setCellValue(logonData.getEmail());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 3) {
                	        cell.setCellValue(logonData.getEname());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 4) {
                	        cell.setCellValue(logonData.getCname());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 5 && logonData.getGender() == 1) {
                        	cell.setCellValue("Male");
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 5 && logonData.getGender() == 2) {
                        	cell.setCellValue("Female");
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 6) {
                        	cell.setCellValue(logonData.getNationality());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 7) {
                        	cell.setCellValue(logonData.getCountryCode());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 8) {
                        	cell.setCellValue(logonData.getPhone());
                	        cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 9) {
                            cell.setCellValue(logonData.getDob());
                            cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 10) {
                            cell.setCellValue(logonData.getIC());
                            cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 11) {
                            cell.setCellValue(logonData.getFbid());
                            cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else if(j == 12) {
                            cell.setCellValue(logonData.getNewsLetter());
                            cell.setCellStyle(styles.get("text_center"));
                        }
                        
                        else {
                            cell.setCellStyle(styles.get("text_center"));
                        }
                        
                    }
                                                    		
        		}

        	}
        	
        	 Row lastRow = sheet.createRow(rownum++);
        	 Cell cell;
        	 
             for (int i = 0; i < 13; i++) {
                 cell = lastRow.createCell(i);
                 cell.setCellValue("");
                 cell.setCellStyle(styles.get("top_border"));
             }
             
	        // Write the output to a file
	        String file = request.getSession().getServletContext().getRealPath("/")+"temp/memberlist."+extension;
	        FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
	        
	        
	        //upload to Amazon S3
			String targetDir = "memberlist";
			boolean isUploaded = ResourceBusinessModel.uploadFile(file, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			} else {
				result = "memberlist."+extension; //傳回檔名
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
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_NONE);
        style.setBorderLeft(CellStyle.BORDER_NONE);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_NONE);
        style.setVerticalAlignment(CellStyle.ALIGN_FILL);
        style.setFont(formFont);
        styles.put("top_border", style);
        
        
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
	 * 管理員更新會員資料
	 * @param data
	 * @param privilege
	 * @param accNo
	 * @return
	 */
	public static String adminUpdateMember(ArrayList<LogonDataModel> data, int privilege, String accNo, String locale){

		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			//第二次防護			
			if(!userData.getEmail().equals(userData.getEmail_Current())){ //不是正在用著的才要檢查
				if(checkExist(userData.getEmail())){
					return Resource.getString("ID_MSG_USERIDEMAILEXIST",locale); //Email or userId exist...
				}
			}
			
			if(userData.getPrivilege() == 2){
				if(accNo.length() <= 5){
					return "Please select an area";
				}
			}
		
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "*** NO connection ***";
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE memberlist SET email='"+userData.getEmail()+"', " + 
					   " ename='"+userData.getEname()+"', cname='"+userData.getCname()+"', gender="+userData.getGender()+"," +
					   " nationality='"+userData.getNationality()+"', phone='"+userData.getPhone()+"', dob='"+userData.getDob()+"'," +
					   " privilege="+userData.getPrivilege()+", accNo='"+accNo+"', discPack='"+userData.getDiscPack()+"', ModifyDT=NOW()";
				sql += " WHERE email = '" +userData.getEmail_Current()+"'"; //雙重防護
				
				stmt.executeUpdate(sql);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				return ex.toString();
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

		return "OK";
	}
	
	
	/**
	 * 管理员更新職員資料
	 * @param data
	 * @param userId
	 * @return
	 */
	public static String adminUpdateStaff(ArrayList<LogonDataModel> data, int privilege, String locale){

		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		
		LogonDataModel userData = new LogonDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (LogonDataModel)data.get(0);
			
			//第二次防護			
			if(!userData.getEmail().equals(userData.getEmail_Current())){ //不是正在用著的才要檢查
				if(checkExist(userData.getEmail())){
					return Resource.getString("ID_MSG_USERIDEMAILEXIST",locale); //Email or userId exist...
				}
			}
		
			
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "*** NO connection ***";
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE stafflist SET email='"+userData.getEmail()+"', " + "aid="+userData.getAid()+", " +
					  " ename='"+userData.getEname()+"', cname='"+userData.getCname()+"', gender="+userData.getGender()+"," +
					  " nationality='"+userData.getNationality()+"', countryCode='"+userData.getCountryCode()+"', phone='"+userData.getPhone()+"', dob='"+userData.getDob()+"', IC='"+userData.getIC()+"', ModifyDT=NOW()";
				
				if(privilege == 99){ // 2次防御是否管理员修改权限
					sql += ", privilege = " + userData.getPrivilege() + ", stage1 = " + userData.getStage1() + ", stage2 = " + userData.getStage2() + ", stage3 = " + userData.getStage3() +
							 ", stage4 = " + userData.getStage4()  + ", stage5 = " + userData.getStage5()  + ", stage6 = " + userData.getStage6()  + ", stage7 = " + userData.getStage7();
				}
				sql += " WHERE email = '" +userData.getEmail_Current()+"'"; //雙重防護
				
				stmt.executeUpdate(sql);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				return ex.toString();
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

		return "OK";
	}
	
	/**
	 * 系統自動檢查會員的生日日期
	 * 
	 * @return
	 */
	public static ArrayList<LogonDataModel> checkBirthday() {
		
		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd");
		Date today = new Date();
		String todayDate = dateFormat.format(today);

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();

			sql = "SELECT userId, email, ename FROM memberlist WHERE SUBSTRING(dob, 6, LENGTH(dob)) = '" +todayDate+ "' AND codeSent=0";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	obj.setUserId(rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename").toString() == null ? rs.getString("email").toString() : rs.getString("ename").toString());
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
	 * Check firebase token from staff/member list
	 * 
	 * @return boolean
	 */
	public static boolean tokenIsSame(String email, String token, String role) {

		boolean returnVal = false;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		String table = "memberlist";
		if(role.equals("staff")){
			table = "stafflist";
		}

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();

			sql = "SELECT tokenFCM FROM "+table+" WHERE email = '" +email+ "'";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	returnVal = token.trim().equals( (rs.getString("tokenFCM").toString() == null ? "" : rs.getString("tokenFCM").toString()) );
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
		
		return returnVal;
	}
	
	/**
	 * Update tokenFCM of member/stafflist
	 * 
	 * @return String
	 */
	public static String updateToken(String email, String token, String role) {

		String returnVal = "";
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		String table = "memberlist";
		if(role.equals("staff")){
			table = "stafflist";
		}

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();
			sql = "UPDATE "+table+" SET tokenFCM='"+token+"', ModifyDT=NOW() WHERE email = '"+email+"'";
			
			stmt.executeUpdate(sql);
			returnVal = "OK";
		      
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
		
		return returnVal;
	}
	
	/**
	 * 寄送電郵驗證 email (普通会员帐号)
	 * 
	 * @param userId
	 */
	public static void sendVerifyEmailNormal(String userId) {
		
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();

			sql = "SELECT email, verify FROM memberlist WHERE userId = '" +userId+ "'";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	
		    	String email = rs.getString("email").toString();
		    	String verify = rs.getString("verify").toString();
		    	
		    	Mailer.sendVerifyEmail(email, verify); //Send verification code
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
		
	}
	
	
	
	/**
	 * 
	 * @param areaCode
	 * @return
	 */
	public static String generateAccNo(String areaCode) {

		LogonDataModel obj = null;
		ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String accNumber = "";
		ArrayList<Integer> arraylist = new ArrayList<Integer>();

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			sql = "SELECT * FROM memberlist WHERE accNo <> ''";
		    
		    rs = stmt.executeQuery(sql);

		    
		    while(rs.next()){
		    	obj = new LogonDataModel();
		    	String tmpAccNo = rs.getString("accNo") == null ? "" : rs.getString("accNo");
		    	if(tmpAccNo.length() > 5) {
		    		String areaStr = tmpAccNo.substring(0, 3);
		    		if(areaStr.equals(areaCode)) {
			    		int accNo = Integer.parseInt(tmpAccNo.substring(tmpAccNo.length()-5, tmpAccNo.length()));
			    		arraylist.add(accNo);
		    		}
		    	}
		    }
		    
		    Collections.sort(arraylist);

		    int maxNo = 0;
		    if(arraylist.size() > 0) {
		    	maxNo = arraylist.get(arraylist.size()-1);
		    }
		    
		    accNumber = areaCode + lpad(5, maxNo + 1);
		      
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

		return accNumber;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String generateAccountVerifyCode(){
    	
    	final String alphabet = "1234567890qazwsxedcrfvtgbyhnujmikolp";
    	
    	StringBuffer code = new StringBuffer();
        for(int i = 0; i <  40 ; i++){
        	int number = (int)(Math.random()*26);
            char ch = alphabet.charAt(number);
            code.append(ch);
        }

    	return code.toString();
    }
	
	/**
	 * 查看該 credit term user 是否有 discount package
	 * 
	 * @param val
	 * @param table
	 * @return
	 */
	public static String checkDiscPack(String val, String table) {

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String discPack = "";

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();
			if (table.equals("accNo")) {
				sql = "SELECT discPack FROM memberlist WHERE privilege = 2 AND accNo='" + val + "'";
			} else if (table.equals("userId")) {
				sql = "SELECT discPack FROM memberlist WHERE privilege = 2 AND (userId='" + val + "' OR email='" + val + "')";
			}
		    rs = stmt.executeQuery(sql);

		    if(rs.next()){
		    	discPack = rs.getString("discPack")== null ? "" : rs.getString("discPack").toString();
		    }

		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.toString();
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

		return discPack;
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