package com.iposb.partner;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.i18n.Resource;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.IPTool;

public class PartnerBusinessModel {
	
	
	
	static Logger logger = Logger.getLogger(PartnerBusinessModel.class);
	
	private PartnerBusinessModel(){
	}
	
	
	public static ArrayList<PartnerDataModel> setRequestData(HttpServletRequest request) {
		
		ArrayList<PartnerDataModel> data = new ArrayList<PartnerDataModel>();
		PartnerDataModel obj = null;
		
		try {
	    	obj = new PartnerDataModel();
	    	obj.setUserId(request.getParameter("email") == null ? "" : request.getParameter("email"));
	    	obj.setUserId_Current(request.getParameter("email_Current") == null ? "" : request.getParameter("email_Current"));
	    	obj.setPasswd(request.getParameter("passwd") == null ? "" : request.getParameter("passwd"));
	    	obj.setPasswd2(request.getParameter("passwd2") == null ? "" : request.getParameter("passwd2"));
	    	obj.setPasswdOld(request.getParameter("passwdOld") == null ? "" : request.getParameter("passwdOld"));
	    	obj.setPasswdNew(request.getParameter("passwdNew") == null ? "" : request.getParameter("passwdNew"));
	    	obj.setEmail(request.getParameter("email") == null ? "" : request.getParameter("email"));
	    	obj.setEmail_Current(request.getParameter("email_Current") == null ? "" : request.getParameter("email_Current"));
	    	obj.setContactPerson(request.getParameter("contactPerson") == null ? "" : request.getParameter("contactPerson"));
	    	obj.setEname(request.getParameter("ename") == null ? "" : request.getParameter("ename"));
	    	obj.setCname(request.getParameter("cname") == null ? "" : request.getParameter("cname"));
	    	obj.setWebsite(request.getParameter("website") == null ? "" : request.getParameter("website"));
	    	obj.setOfficialEmail(request.getParameter("officialEmail") == null ? "" : request.getParameter("officialEmail"));
	    	obj.setPhone(request.getParameter("phone") == null ? "" : request.getParameter("phone"));
	    	obj.setAddress(request.getParameter("address") == null ? "" : request.getParameter("address"));
	    	obj.setCompanyLicense(request.getParameter("companyLicense") == null ? "" : request.getParameter("companyLicense"));
	    	obj.setGst(request.getParameter("gst") == null ? "" : request.getParameter("gst"));
	    	obj.setCompanyDesc_enUS(request.getParameter("companyDesc_enUS") == null ? "" : request.getParameter("companyDesc_enUS"));
	    	obj.setCompanyDesc_zhCN(request.getParameter("companyDesc_zhCN") == null ? "" : request.getParameter("companyDesc_zhCN"));
	    	obj.setCompanyDesc_zhTW(request.getParameter("companyDesc_zhTW") == null ? "" : request.getParameter("companyDesc_zhTW"));
	    	obj.setLoginTimes(Integer.parseInt(request.getParameter("loginTimes") == null ? "0" : request.getParameter("loginTimes").equals("") ? "0" : request.getParameter("loginTimes").toString()));
	    	obj.setPrivilege(Integer.parseInt(request.getParameter("privilege") == null ? "1" : request.getParameter("privilege").equals("") ? "1" : request.getParameter("privilege").toString()));
	    	obj.setPricingPolicy(Integer.parseInt(request.getParameter("pricingPolicy") == null ? "0" : request.getParameter("pricingPolicy").equals("") ? "0" : request.getParameter("pricingPolicy").toString()));
	    	obj.setPid(Integer.parseInt(request.getParameter("pid") == null ? "0" : request.getParameter("pid").equals("") ? "0" : request.getParameter("pid").toString()));
	    	obj.setAid(Integer.parseInt(request.getParameter("aid") == null ? "0" : request.getParameter("aid").equals("") ? "0" : request.getParameter("aid").toString()));
	    	obj.setLastIP(IPTool.getUserIP(request));
	    	data.add(obj);
		      
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return data;
	}
	

	
	/**
	 * 所有申請成為合作夥伴的清單 - 供管理員查詢
	 * @param orderby
	 * @param pageNo
	 * @return
	 */
	public static ArrayList<PartnerDataModel> tobepartnerList(String orderby, int pageNo) {
		
		ArrayList<PartnerDataModel> data = new ArrayList<PartnerDataModel>();
		PartnerDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
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

			stmt = conn.createStatement();

		    sql = "SELECT category, companyName, contactPerson, contactNumber, contactEmail, message, requestDT, IP," +
		    		" (SELECT COUNT(companyName) FROM tobepartner) AS total" +
		    		" FROM tobepartner";
		    
		    if(orderby!=null && !orderby.equals("")){
		    	sql += " ORDER BY " + orderby + " DESC";
		    }
		    
		    sql += " LIMIT " + page + ", " + numberPerPage;
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PartnerDataModel();
		    	obj.setCategory(Integer.parseInt(rs.getString("category") == null ? "0" : rs.getString("category")));
		    	obj.setCompanyName(rs.getString("companyName") == null ? "" : rs.getString("companyName"));
		    	obj.setContactPerson(rs.getString("contactPeRSon") == null ? "" : rs.getString("contactPeRSon"));
		    	obj.setContactNumber(rs.getString("contactNumber") == null ? "" : rs.getString("contactNumber"));
		    	obj.setContactEmail(rs.getString("contactEmail") == null ? "" : rs.getString("contactEmail"));
		    	obj.setMessage(rs.getString("message") == null ? "" : rs.getString("message"));
		    	obj.setRequestDT(rs.getString("requestDT") == null ? "" : rs.getString("requestDT"));
		    	obj.setIP(rs.getString("IP") == null ? "" : rs.getString("IP"));
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
	 * 新增夥伴
	 * @param data
	 * @return
	 */
	public static String addNewPartner(ArrayList<PartnerDataModel> data){

		String MD5Password = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;	
		PartnerDataModel userData = new PartnerDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (PartnerDataModel)data.get(0);
			
			if(checkExist(userData.getUserId())){ //第二次防護
				return "userId Exist...";
			}
		
			MD5Password = getMD5(userData.getPasswd());
			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO partnerlist(userId, passwd, email, contactPerson, ename, cname, website, officialEmail, phone, address," +
						" companyLicense, gst, companyDesc_enUS, companyDesc_zhCN, companyDesc_zhTW, createDT, registerIP, loginTimes, privilege) " +
						" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?)");
	
				pstmt.setString(1, userData.getUserId());
				pstmt.setString(2, MD5Password);
				pstmt.setString(3, userData.getEmail());
				pstmt.setString(4, userData.getContactPerson());
				pstmt.setString(5, userData.getEname());
				pstmt.setString(6, userData.getCname());
				pstmt.setString(7, userData.getWebsite());
				pstmt.setString(8, userData.getOfficialEmail());
				pstmt.setString(9, userData.getPhone());
				pstmt.setString(10, userData.getAddress());
				pstmt.setString(11, userData.getCompanyLicense());
				pstmt.setString(12, userData.getGst());	
				pstmt.setString(13, userData.getCompanyDesc_enUS());	
				pstmt.setString(14, userData.getCompanyDesc_zhCN());
				pstmt.setString(15, userData.getCompanyDesc_zhTW());
				pstmt.setString(16, userData.getLastIP());
				pstmt.setInt(17, 1);
				pstmt.setInt(18, 0);
	
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
	 * 更新夥伴資料
	 * 
	 * @param userId
	 * @param data
	 * @return
	 */
	protected static int updatePartner(String userId, ArrayList<PartnerDataModel> data){

		Connection conn = null;
		Statement stmt = null;
		String sql = null;

		PartnerDataModel userData = new PartnerDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (PartnerDataModel)data.get(0);
			
			//第二次防護
			if(!userData.getUserId().equals(userId)){ //不是正在用著的才要檢查
				if(checkExist(userData.getUserId())){ 
					return -2; //Email or userId exist...
				}
			}
			
			if(!userData.getEmail().equals(userData.getEmail_Current())){ //不是正在用著的才要檢查
				if(checkExist(userData.getEmail())){
					return -2; //Email or userId exist...
				}
			}
			
			if(userId.trim().equals("")) { //不填入userId
				userId = userData.getEmail();
			}

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE partnerlist SET contactPerson='"+userData.getContactPerson()+"'," +
						" ename='"+userData.getEname()+"', cname='"+userData.getCname()+"'," +
					    " website='"+userData.getWebsite()+"', officialEmail='"+userData.getOfficialEmail()+"'," +
					    " phone='"+userData.getPhone()+"', address='"+userData.getAddress()+"'," +
					    " companyLicense='"+userData.getCompanyLicense()+"', gst='"+userData.getGst()+"'," +
					    " companyDesc_enUS='"+userData.getCompanyDesc_enUS()+"', companyDesc_zhCN='"+userData.getCompanyDesc_zhCN()+"'," +
					    " companyDesc_zhTW='"+userData.getCompanyDesc_zhTW()+"', ModifyDT=NOW()" +
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
	 * Partner 更改密碼
	 * 
	 * @param data
	 * @param userId - 用session裡面的userId，以策安全
	 * @return
	 */
	protected static int updatePassword(ArrayList<PartnerDataModel> data, String userId){

		String MD5Password = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		
		PartnerDataModel userData = new PartnerDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (PartnerDataModel)data.get(0);
			
			
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
				sql = "UPDATE partnerlist SET passwd=N'"+MD5Password+"', ModifyDT=NOW() WHERE userId = '" +userId+"'";
				
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
	 * 檢查是否已被使用
	 * @param userId
	 * @return
	 */
	public static boolean checkExist(String userId) {

		boolean exist = false;

		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return true;
	        }

			stmt = conn.createStatement();

		    sql = "SELECT userId FROM partnerlist WHERE userId='" + userId + "'";
		    
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
	 * 檢查密碼
	 * @param userId
	 * @return
	 */
	public static String checkPassword(String userId) {

		String passwd = "";

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

		    sql = "SELECT passwd FROM partnerlist";
		    
		    if(userId!=null && userId!=""){
		    	sql += " WHERE userId='" + userId + "'";
		    }
		    
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
	 * 檢查夥伴的權限
	 * 0: 無權限  1： 正常
	 * @param userId
	 * @param passwd
	 * @return
	 */
	public static ArrayList<PartnerDataModel> verifyMember(String userId, String passwd) {

		PartnerDataModel obj = null;
		ArrayList<PartnerDataModel> data = new ArrayList<PartnerDataModel>();
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
		    
		    String passInput = getMD5(passwd);
		    
		    if(userId.trim().length()> 0 && !userId.trim().equals("")){
		    	sql = "SELECT pid, userId, ename, privilege FROM partnerlist WHERE userId='" + userId + "' AND passwd='" + passInput + "'";
		    	
			    rs = stmt.executeQuery(sql);

			    while(rs.next()){
			    	obj = new PartnerDataModel();
			    	obj.setPid(Integer.parseInt(rs.getString("pid")== null ? "0" : rs.getString("pid").toString()));
			    	obj.setUserId(rs.getString("userId"));
			    	obj.setEname(rs.getString("ename"));
			    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")== null ? "0" : rs.getString("privilege").toString()));
			    	data.add(obj);
			    }
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
	 * Get pid by userId and privilege
	 * 
	 * @param userId
	 * @param privilege
	 * @return
	 */
	public static int getPidByIdPriv(String userId, int privilege) {

		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		int priv = -9;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return -9;
	        }

			stmt = conn.createStatement();
		    
		    if(userId.trim().length()> 0 && !userId.trim().equals("")){
		    	sql = "SELECT pid FROM partnerlist WHERE userId='" + userId + "' AND privilege=" + privilege;
		    	
			    rs = stmt.executeQuery(sql);

			    while(rs.next()){
			    	priv = Integer.parseInt(rs.getString("pid")== null ? "0" : rs.getString("pid").toString());
			    }
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
	 * 取得夥伴資料
	 * @param email
	 * @return
	 */
	public static ArrayList<PartnerDataModel> getPartnerData(String email) {

		PartnerDataModel obj = null;
		ArrayList<PartnerDataModel> data = new ArrayList<PartnerDataModel>();
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

		    sql = "SELECT * FROM partnerlist WHERE email = '"+email+"'";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PartnerDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid")== null ? "0" : rs.getString("pid").toString()));
		    	obj.setAid(Integer.parseInt(rs.getString("aid")== null ? "0" : rs.getString("aid").toString()));
		    	obj.setUserId(rs.getString("userId")== null ? "" : rs.getString("userId").toString());
		    	obj.setEmail(rs.getString("email")== null ? "" : rs.getString("email").toString());
		    	obj.setUserId_Current(rs.getString("userId")== null ? "" : rs.getString("userId").toString());
		    	obj.setEmail_Current(rs.getString("email")== null ? "" : rs.getString("email").toString());
		    	obj.setContactPerson(rs.getString("contactPerson")== null ? "" : rs.getString("contactPerson").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")== null ? "" : rs.getString("cname").toString());
		    	obj.setWebsite(rs.getString("website")== null ? "" : rs.getString("website").toString());
		    	obj.setOfficialEmail(rs.getString("officialEmail")== null ? "" : rs.getString("officialEmail").toString());
		    	obj.setPhone(rs.getString("phone")== null ? "0" : rs.getString("phone").toString());
		    	obj.setAddress(rs.getString("address")== null ? "" : rs.getString("address").toString());
		    	obj.setCompanyLicense(rs.getString("companyLicense")== null ? "" : rs.getString("companyLicense").toString());
		    	obj.setGst(rs.getString("gst")== null ? "" : rs.getString("gst").toString());
		    	obj.setCompanyDesc_enUS(rs.getString("companyDesc_enUS")== null ? "" : rs.getString("companyDesc_enUS").toString());
		    	obj.setCompanyDesc_zhCN(rs.getString("companyDesc_zhCN")== null ? "" : rs.getString("companyDesc_zhCN").toString());
		    	obj.setCompanyDesc_zhTW(rs.getString("companyDesc_zhTW")== null ? "" : rs.getString("companyDesc_zhTW").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")== null ? "0" : rs.getString("privilege").toString()));
		    	obj.setPricingPolicy(Integer.parseInt(rs.getString("pricingPolicy")== null ? "0" : rs.getString("pricingPolicy").toString()));
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")== null ? "0" : rs.getString("loginTimes").toString()));
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
	 * 更新user的最後登入IP & 日期 & 登入次数
	 * @param userId
	 * @param lastIP
	 */
	public static void updateIP(String userId, String lastIP) {

		
		Connection conn = null;
		Statement stmt = null;
		String sql = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();

			sql = "UPDATE partnerlist SET lastIP = '"+lastIP+"', lastLoginDT=NOW(), loginTimes = loginTimes + 1 WHERE userId='" +userId+"'";

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
	 * 檢查 partner 看訂單的權限
	 * @param userId
	 * @return
	 */
	public static ArrayList<PartnerDataModel> checkPrivilege(String userId) {
		
		PartnerDataModel obj = null;
		ArrayList<PartnerDataModel> data = new ArrayList<PartnerDataModel>();
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
		    
		    if(userId.trim().length()> 0 && !userId.trim().equals("")){
		    	sql = "SELECT userId, privilege FROM partnerlist WHERE userId='" + userId + "'";
		    	
			    rs = stmt.executeQuery(sql);

			    while(rs.next()){
			    	obj = new PartnerDataModel();
			    	obj.setUserId(rs.getString("userId"));
			    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")== null ? "0" : rs.getString("privilege").toString()));
			    	data.add(obj);
			    }
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
	 * 夥伴管理 - 供管理員查詢
	 * @param partnerType:    agent, partner
	 * @return
	 */
	
	public static ArrayList<PartnerDataModel> agentPartnerList(String partnerType) {

		PartnerDataModel obj = null;
		ArrayList<PartnerDataModel> data = new ArrayList<PartnerDataModel>();
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
			
			if(partnerType.equals("agent")) {
				sqlCond = " WHERE privilege = 3";
			} else if(partnerType.equals("partner")) {
				sqlCond = " WHERE privilege = 4";
			}

			stmt = conn.createStatement();

			sql = "SELECT a.*, c.name_enUS AS areaName, (SELECT COUNT(b.pid) FROM partnerlist b ) AS total FROM partnerlist a" +
					" LEFT JOIN area c ON c.aid = a.aid" +
					sqlCond+" ORDER BY a.cname ASC";

		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PartnerDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid")== null ? "0" : rs.getString("pid").toString()));
		    	obj.setUserId(rs.getString("userId")==null ? "" : rs.getString("userId").toString());
		    	obj.setContactPerson(rs.getString("contactPerson")==null ? "" : rs.getString("contactPerson").toString());
		    	obj.setEmail(rs.getString("email")==null ? "" : rs.getString("email").toString());
		    	obj.setEname(rs.getString("ename")== null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname")==null ? "" : rs.getString("cname").toString());
		    	obj.setWebsite(rs.getString("website")== null ? "" : rs.getString("website").toString());
		    	obj.setOfficialEmail(rs.getString("officialEmail")== null ? "" : rs.getString("officialEmail").toString());
		    	obj.setPhone(rs.getString("phone")==null ? "" : rs.getString("phone").toString());
		    	obj.setAddress(rs.getString("address")== null ? "" : rs.getString("address").toString());
		    	obj.setCompanyLicense(rs.getString("companyLicense")== null ? "" : rs.getString("companyLicense").toString());
		    	obj.setGst(rs.getString("gst")== null ? "" : rs.getString("gst").toString());
		    	obj.setCompanyDesc_enUS(rs.getString("companyDesc_enUS")== null ? "" : rs.getString("companyDesc_enUS").toString());
		    	obj.setCompanyDesc_zhCN(rs.getString("companyDesc_zhCN")== null ? "" : rs.getString("companyDesc_zhCN").toString());
		    	obj.setCompanyDesc_zhTW(rs.getString("companyDesc_zhTW")== null ? "" : rs.getString("companyDesc_zhTW").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege")==null ? "0" : rs.getString("privilege").toString()));
		    	obj.setPricingPolicy(Integer.parseInt(rs.getString("pricingPolicy")== null ? "0" : rs.getString("pricingPolicy").toString()));
		    	obj.setCreateDT(rs.getString("createDT")==null ? "" : rs.getString("createDT").toString());
		    	obj.setModifyDT(rs.getString("modifyDT")==null ? "" : rs.getString("modifyDT").toString());
		    	obj.setRegisterIP(rs.getString("registerIP")==null ? "" : rs.getString("registerIP").toString());
		    	obj.setLastIP(rs.getString("lastIP")==null ? "" : rs.getString("lastIP").toString());
		    	obj.setLastLoginDT(rs.getString("lastLoginDT")==null ? "" : rs.getString("lastLoginDT").toString());
		    	obj.setLoginTimes(Integer.parseInt(rs.getString("loginTimes")== null ? "0" : rs.getString("loginTimes").toString()));
		    	obj.setAid(Integer.parseInt(rs.getString("aid")== null ? "0" : rs.getString("aid").toString()));
		    	obj.setAreaName(rs.getString("areaName")== null ? "" : rs.getString("areaName").toString());
		    	obj.setTotal(Integer.parseInt(rs.getString("total").toString()));
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
	 * 管理员更新partner资料
	 * @param data
	 * @return
	 */
	public static String adminUpdatePartner(ArrayList<PartnerDataModel> data, int privilege, String locale){
		
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		PartnerDataModel userData = new PartnerDataModel();
		if(data != null && data.size() > 0){ //如果有資料
			userData = (PartnerDataModel)data.get(0);
		
			//第二次防護
			if(!userData.getUserId().equals(userData.getUserId_Current())){ //不是正在用著的才要檢查
				if(checkExist(userData.getUserId())){ 
					return Resource.getString("ID_MSG_USERIDEMAILEXIST",locale); //Email or userId exist...
				}
			}
			
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
					return "noConnection";
		        }
				
				stmt = conn.createStatement();
				sql = "UPDATE partnerlist SET aid="+userData.getAid()+", email='"+userData.getEmail()+"', contactPerson='"+userData.getContactPerson()+"'," +
						" ename='"+userData.getEname()+"', cname='"+userData.getCname()+"'," +
					    " website='"+userData.getWebsite()+"', officialEmail='"+userData.getOfficialEmail()+"'," +
					    " phone='"+userData.getPhone()+"', address='"+userData.getAddress()+"'," +
					    " companyLicense='"+userData.getCompanyLicense()+"'," +
					    " companyDesc_enUS='"+userData.getCompanyDesc_enUS()+"', companyDesc_zhCN='"+userData.getCompanyDesc_zhCN()+"'," +
					    " companyDesc_zhTW='"+userData.getCompanyDesc_zhTW()+"', ModifyDT=NOW()";
				
				if(privilege == 99){ // 2次防御是否管理员修改权限
					sql += ", privilege = " + userData.getPrivilege() + ", pricingPolicy = " + userData.getPricingPolicy();
				}
				//sql += " WHERE email = '" +userData.getEmail_Current()+"' AND userId = '" +userData.getUserId_Current()+"'"; //雙重防護
				sql += " WHERE pid = " + userData.getPid();
				
				stmt.executeUpdate(sql);			
			}
			catch(Exception ex){
				ex.printStackTrace();
				return "-1";
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


	
	
}