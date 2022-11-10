package com.iposb.code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.logon.LogonDataModel;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.IPTool;
import com.iposb.utils.Mailer;
import com.iposb.utils.UtilsBusinessModel;

public class CodeBusinessModel {
	
	
	static Logger logger = Logger.getLogger(CodeBusinessModel.class);
	
	private CodeBusinessModel(){
	}
	
	
	protected static ArrayList<CodeDataModel> setRequestData(HttpServletRequest request) {
		
		CodeDataModel obj = null;
		ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();

		try {
	    	obj = new CodeDataModel();
	    	obj.setCode(request.getParameter("code") == null ? "" : request.getParameter("code"));
	    	obj.setDiscount(Integer.parseInt(request.getParameter("discount") == null ? "0" : request.getParameter("discount").toString()));
	    	obj.setAllowTimes(Integer.parseInt(request.getParameter("allowTimes") == null ? "0" : request.getParameter("allowTimes")));
	    	obj.setPeriodStart(request.getParameter("periodStart") == null ? "" : request.getParameter("periodStart"));  
	    	obj.setPeriodEnd(request.getParameter("periodEnd") == null ? "" : request.getParameter("periodEnd")); 
	    	obj.setRemark(request.getParameter("remark") == null ? "" : request.getParameter("remark"));
	    	obj.setIsAllow(Integer.parseInt(request.getParameter("isAllow") == null ? "0" : request.getParameter("isAllow").equals("") ? "0" : request.getParameter("isAllow").equals("on") ? "1" : "0"));
	    	obj.setDiscountType(Integer.parseInt(request.getParameter("discountType") == null ? "0" : request.getParameter("discountType")));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return data;
	}
	

	/**
	 * 驗證優惠碼的有效性
	 * @param promoCode
	 * @return
	 */
	protected static ArrayList<CodeDataModel> verifyCode(String promoCode, HttpServletRequest request) {

		CodeDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();

		try {
			
//			if (IPTool.getUserIP(request).equals("202.111.30.6") || IPTool.getUserIP(request).equals("111.165.43.251")) {
//		    	logger.info("Rejected promotion code ("+promoCode+") checking from " + IPTool.getUserIP(request) + " (" + UtilsBusinessModel.timeNow() + ")");
//		    	return null;
//		    } else {
		    	logger.info("---User (IP: "+IPTool.getUserIP(request)+") verifying Code: " + promoCode + " (" + UtilsBusinessModel.timeNow() + ")");
//		    }
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			stmt = conn.createStatement();

		    sql = "SELECT * FROM code WHERE code='" + promoCode + "'";

		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new CodeDataModel();
		    	obj.setCode(rs.getString("code"));
		    	obj.setDiscount(Integer.parseInt(rs.getString("discount").toString()));
		    	obj.setAllowTimes(Integer.parseInt(rs.getString("allowTimes").toString()));
		    	obj.setUsed(Integer.parseInt(rs.getString("used").toString()));
		    	obj.setPeriodStart(rs.getString("periodStart"));
		    	obj.setPeriodEnd(rs.getString("periodEnd"));
		    	obj.setRemark(rs.getString("remark"));
		    	obj.setLastUsedDT(rs.getString("lastUsedDT"));
		    	obj.setIsAllow(Integer.parseInt(rs.getString("isAllow").toString()));
		    	obj.setDiscountType(Integer.parseInt(rs.getString("discountType").toString()));
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
	 * 客人下單時，同步 update 優惠碼
	 * @param promoCode
	 * @return
	 */
	protected static String updateCode(String promoCode, String bookingCode) {

		String usedToBook = "";
		CodeDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		String result = "";
		ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			//先查詢用usedToBook的欄位
			sql = "SELECT usedToBook FROM code WHERE code='" + promoCode + "'";
			
			stmt = conn.createStatement();
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new CodeDataModel();
		    	obj.setCode(rs.getString("usedToBook")==null ? "" : rs.getString("usedToBook").toString());
		    	data.add(obj);
		    }
		    
		    CodeDataModel codeData = (CodeDataModel)data.get(0);
		    usedToBook = codeData.getUsedToBook().toString().trim();
		    
		    usedToBook += bookingCode + ",";

		    //再來update
			stmt = conn.createStatement();
//			sql = "UPDATE code SET used = used + 1, allowTimes = allowTimes - 1, lastUsedDT=DATE_ADD(NOW(), INTERVAL 13 HOUR)," +
			sql = "UPDATE code SET used = used + 1, allowTimes = allowTimes - 1, lastUsedDT=NOW()," +
				  " usedToBook='"+usedToBook+"' WHERE code='" +promoCode+"'";
			stmt.executeUpdate(sql);
		
			result = "OK";
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
		return result;
	}
	
	/**
	 * 檢查是否已經存在
	 * 
	 * @return
	 */
	protected static String checkCode(String code) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
//		CodeDataModel codeData = new CodeDataModel();
//		if(data != null && data.size() > 0){ //如果有資料
//			codeData = (CodeDataModel)data.get(0);

//			if(Integer.toString(codeData.getDiscount()) == "" ||codeData.getDiscount()==0){
//				return "noDiscount";
//			} else if(codeData.getDiscount() < 0){
//				return "negativeDiscount";
//			}
//			if(Integer.toString(codeData.getAllowTimes()) ==""){
//				return "noAllowTimes";
//			} else if(codeData.getAllowTimes() < 0){
//				return "negativeAllowTimes";
//			}
//			if(codeData.getPeriodStart()==""){
//				return "noPeriodStart";
//			}
//			if(codeData.getPeriodEnd()==""){
//				return "noPeriodEnd";
//			}
			
			try {
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();  
	
			    sql = "SELECT code FROM code WHERE code ='" + code +"'";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	String tmpCode = rs.getString("code");
			    	if(tmpCode != "" || tmpCode != null){
			    		return "exist";
			    	}
			    }
			    
			    
			}catch (Exception ex) {
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
//		}

		return "OK";
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	protected static ArrayList<CodeDataModel> searchCode(String code) {

		CodeDataModel obj = null;
		ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		String sqlCond = " WHERE code LIKE '%"+code+"%'";
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT * FROM code "+ sqlCond ;
			
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new CodeDataModel();
		    	obj.setCid(Integer.parseInt(rs.getString("cid")));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
		    	obj.setDiscount(Integer.parseInt(rs.getString("discount") == null ? "0" : rs.getString("discount").toString()));
		    	obj.setAllowTimes(Integer.parseInt(rs.getString("allowTimes") == null ? "0" : rs.getString("allowTimes")));
		    	obj.setUsed(Integer.parseInt(rs.getString("used") == null ? "0" : rs.getString("used")));
		    	obj.setPeriodStart(rs.getString("periodStart") == null ? "" : rs.getString("periodStart"));  
		    	obj.setPeriodEnd(rs.getString("periodEnd") == null ? "" : rs.getString("periodEnd"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));     
		    	obj.setLastUsedDT(rs.getString("lastUsedDT") == null ? "" : rs.getString("lastUsedDT"));      
		    	obj.setUsedToBook(rs.getString("usedToBook") == null ? "" : rs.getString("usedToBook"));
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark"));
		    	obj.setIsAllow(Integer.parseInt(rs.getString("isAllow") == null ? "0" : rs.getString("isAllow")));
		    	obj.setDiscountType(Integer.parseInt(rs.getString("discountType") == null ? "0" : rs.getString("discountType")));
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
	 * 所有優惠碼的清單 - 供管理員查詢
	 * @param orderby
	 * @param pageNo
	 * @return
	 */
	protected static ArrayList<CodeDataModel> codeList(String orderby, int pageNo) {

		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
		
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

		    sql = "SELECT cid, code, discount, allowTimes, used, periodStart, periodEnd, createDT, lastUsedDT, usedToBook, remark, isAllow, discountType, " +
		    		" (SELECT COUNT(code) FROM code) AS total " +
		    		" FROM code ";
		    
		    if(orderby!=null && !orderby.equals("")){
		    	sql += " ORDER BY " + orderby + " ASC";
		    }
		    
		    sql += " LIMIT " + page + ", " + numberPerPage;
		    
		    rs = stmt.executeQuery(sql);
		    
		    data = setObjValue(rs);
		      
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
	 * Get Code detail by consignment promoCode
	 * @param code
	 * @return
	 */
	public static ArrayList<CodeDataModel> codeDetails(String code) {

		CodeDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

		    sql = "SELECT * FROM code WHERE code = '" + code + "'";
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new CodeDataModel();
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
		    	obj.setDiscount(Integer.parseInt(rs.getString("discount") == null ? "0" : rs.getString("discount").toString()));
		    	obj.setAllowTimes(Integer.parseInt(rs.getString("allowTimes") == null ? "0" : rs.getString("allowTimes")));
		    	obj.setUsed(Integer.parseInt(rs.getString("used") == null ? "0" : rs.getString("used")));
		    	obj.setPeriodStart(rs.getString("periodStart") == null ? "" : rs.getString("periodStart"));  
		    	obj.setPeriodEnd(rs.getString("periodEnd") == null ? "" : rs.getString("periodEnd"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));     
		    	obj.setLastUsedDT(rs.getString("lastUsedDT") == null ? "" : rs.getString("lastUsedDT"));      
		    	obj.setUsedToBook(rs.getString("usedToBook") == null ? "" : rs.getString("usedToBook"));
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark"));
		    	obj.setIsAllow(Integer.parseInt(rs.getString("isAllow") == null ? "0" : rs.getString("isAllow")));
		    	obj.setDiscountType(Integer.parseInt(rs.getString("discountType") == null ? "0" : rs.getString("discountType")));
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
	 * 新增優惠碼
	 * @param data 基本資料
	 * @return
	 */
	protected static String addNewCode(ArrayList<CodeDataModel> data) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		CodeDataModel codeData = new CodeDataModel();
		
		if(data != null && data.size() > 0){ //如果有資料
			codeData = (CodeDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO code(code, discount, allowTimes, periodStart, periodEnd, createDT, remark, isAllow, discountType)" +
						" VALUE " + "(?, ?, ?, ?, ?, NOW(), ?, ?, ? )");
	
				pstmt.setString(1, codeData.getCode());
				pstmt.setInt(2, codeData.getDiscount());
				pstmt.setInt(3, codeData.getAllowTimes());
				pstmt.setString(4, codeData.getPeriodStart());
				pstmt.setString(5, codeData.getPeriodEnd());
				pstmt.setString(6, codeData.getRemark());
				pstmt.setInt(7, codeData.getIsAllow());
				pstmt.setInt(8, codeData.getDiscountType());

				pstmt.executeUpdate();
				pstmt.clearParameters();
			
			}
			catch(Exception ex){
				logger.error(ex.toString());
				ex.printStackTrace();
				return ex.toString();
			} finally {
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
	 * 寄送生日優惠碼
	 * 
	 * @param userId
	 * @return
	 */
	public static String sendVoucher(ArrayList<LogonDataModel> data){
		
		LogonDataModel eData = new LogonDataModel();
		String email = "";
		String userIdtoSend = "";
		String ename = "";
		String voucherCode = "";
		String result = "";
		String addCodeResult ="";
		String checkCode = "";
		
		
		if(data != null && !data.isEmpty()){

    		try {
				for(int i = 0; i < data.size(); i++){
					eData = data.get(i);
					
					//get username
					userIdtoSend = eData.getUserId();
					ename = eData.getEname();
					
					//get email
					if(eData.getEmail().trim() != "" && eData.getEmail().trim().length() > 0){
						email = eData.getEmail();
					} else {
						email = eData.getUserId();
					}
					
					//generate voucher code
					do{
						voucherCode = genPromoCode();
						checkCode = checkCode(voucherCode); //檢查是否已經存在

						Thread.sleep(1000); //先等待1秒鐘再進行下一個
					}
					while(checkCode != "OK");
					
					//add voucher code to database
					addCodeResult = addBirthdayCode(voucherCode, userIdtoSend);
					
					if(addCodeResult != "OK"){
						logger.info("Failed to add Voucher code to database: " + voucherCode);
						
					} else {
						result = Mailer.sendVoucherEmail(ename, email, voucherCode); //Send voucher code for all users on their birthday
						Thread.sleep(1000); //先等待1秒鐘再進行下一個
						
						if(result == "OK") {
							updateCodeSent(userIdtoSend);
						}
						
					}
					Thread.sleep(5000); //先等待5秒鐘再進行下一個
				}
				
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    			logger.info(e.toString());
    		} 
    		
		}
		
		return "OK";
	}
	
	/**
	 * 
	 * @param voucherCode
	 * @param userId
	 * 
	 * @return
	 */
	protected static String addBirthdayCode(String voucherCode, String userId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		Date dtNow = new Date();
		Calendar c = Calendar.getInstance(); 
		
		c.setTime(dt); 
		c.add(Calendar.DATE, 183);//半年
		dt = c.getTime();
		String periodStart = dateFormat.format(dtNow);
		String periodEnd = dateFormat.format(dt);
		
		
		if(voucherCode != "" && voucherCode.trim().length() > 0){ //如果有資料

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO code(code, discount, allowTimes, periodStart, periodEnd, createDT, remark, isAllow, discountType)" +
						" VALUE " + "(?, ?, ?, ?, ?, NOW(), ?, ?, ? )");
	
				pstmt.setString(1, voucherCode);
				pstmt.setInt(2, 10);
				pstmt.setInt(3, 1);
				pstmt.setString(4, periodStart);
				pstmt.setString(5, periodEnd);
				pstmt.setString(6, "Birthday Voucher Code for " + userId);
				pstmt.setInt(7, 1);
				pstmt.setInt(8, 0);

				pstmt.executeUpdate();
				pstmt.clearParameters();
			
			}
			catch(Exception ex){
				logger.error(ex.toString());
				ex.printStackTrace();
				return ex.toString();
			} finally {
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
			return "noCode"; //可能是換語系造成的
		}

		return "OK";
	}
	
	
	/**
	 * 更新優惠碼內容
	 * 
	 * @param data
	 * @return
	 */
	protected static String updateCode(ArrayList<CodeDataModel> data) {
		
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String result = "";
		CodeDataModel codeData = new CodeDataModel();
		
		if(data != null && data.size() > 0){ //如果有資料
			codeData = (CodeDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE code SET discount="+codeData.getDiscount()+", allowTimes="+codeData.getAllowTimes()+"," +
					  " periodStart='"+codeData.getPeriodStart()+"', periodEnd='"+codeData.getPeriodEnd()+"'," +
					  " remark='"+codeData.getRemark()+"', isAllow="+codeData.getIsAllow()+ ", " + 
					  " discountType = " + codeData.getDiscountType() + 
					  " WHERE code='" +codeData.getCode()+"'";
				stmt.executeUpdate(sql);
				
				result = "OK";
				
				Thread.sleep(3000); //先等待3秒鐘再繼續，以便 read replica 碟有足夠時間 update
				
			}
			catch(Exception ex){
				logger.error(ex.toString());
				ex.printStackTrace();
				result = ex.toString();
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

		return result;
	}

	/**
	 * update Birthday code is sent or not
	 * 
	 * @return 
	 */
	protected static void updateCodeSent(String userId) {
		
		String sql = "";
		Statement stmt = null;
		Connection conn = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return;
	        }

			stmt = conn.createStatement();
			sql = "UPDATE memberlist SET codeSent = 1, ModifyDT=NOW() WHERE userId = '"+userId+"'";
			
		    stmt.executeUpdate(sql);

		}
		catch(Exception ex){
			ex.printStackTrace();
			return;
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
		
		return;
	}
	
	/**
	 * Reset birthday code sent
	 * 
	 * @return
	 */
	public static void resetCodeSent() {
		
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return;
	        }

			stmt = conn.createStatement();
			sql = "UPDATE memberlist SET codeSent=0";
			stmt.executeUpdate(sql);
			
		}
		catch(Exception ex){
			logger.error(ex.toString());
			ex.printStackTrace();
			return;
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

		return;
	}
	
	
	/**
	 * 填寫意見回饋表所產生的優惠碼
	 * @param BookingCode
	 * @param feedbackDT
	 * @return
	 */
	protected static String generateNewCode4Feedback(String bookingCode) {

		String codeExpire = "";
		Connection conn = null;
		PreparedStatement pstmt = null;

		
		if(!bookingCode.equals("")){ //如果有資料

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
				
				String code = UtilsBusinessModel.randomCode(6);
				int discount = 25; //RM25
				int allowTimes = 1;
				String periodStart = UtilsBusinessModel.todayDate();
				String periodEnd = UtilsBusinessModel.dateAdd(periodStart, 183); //有效期半年
				String remark = bookingCode;
				int isAllow = 1;
				
			
				
				codeExpire = code + periodEnd;
				
	
				pstmt = conn.prepareStatement("INSERT INTO code(code, discount, allowTimes, periodStart, periodEnd, createDT, remark, isAllow, discountType )" +
						" VALUE " + "(?, ?, ?, ?, ?, NOW(), ?, ?, ? )");
	
				pstmt.setString(1, code);
				pstmt.setInt(2, discount);
				pstmt.setInt(3, allowTimes);
				pstmt.setString(4, periodStart);
				pstmt.setString(5, periodEnd);
				pstmt.setString(6, remark);
				pstmt.setInt(7, isAllow);
				pstmt.setInt(8, 0);

				pstmt.executeUpdate();
				pstmt.clearParameters();
			
			}
			catch(Exception ex){
				logger.error(ex.toString());
				ex.printStackTrace();
				return ex.toString();
			} finally {
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

		return codeExpire;
	}
	
	
	
	/**
	 * 把資料庫得到的值設入 data 裡
	 * @param rs
	 * @return
	 */
	private static ArrayList<CodeDataModel> setObjValue(ResultSet rs) {

		CodeDataModel obj = null;
		ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
		
	    try {
			
		    while(rs.next()){
		    	obj = new CodeDataModel();
		    	obj.setCid(Integer.parseInt(rs.getString("cid")));
		    	obj.setCode(rs.getString("code") == null ? "" : rs.getString("code"));
		    	obj.setDiscount(Integer.parseInt(rs.getString("discount") == null ? "0" : rs.getString("discount").toString()));
		    	obj.setAllowTimes(Integer.parseInt(rs.getString("allowTimes") == null ? "0" : rs.getString("allowTimes")));
		    	obj.setUsed(Integer.parseInt(rs.getString("used") == null ? "0" : rs.getString("used")));
		    	obj.setPeriodStart(rs.getString("periodStart") == null ? "" : rs.getString("periodStart"));  
		    	obj.setPeriodEnd(rs.getString("periodEnd") == null ? "" : rs.getString("periodEnd"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));     
		    	obj.setLastUsedDT(rs.getString("lastUsedDT") == null ? "" : rs.getString("lastUsedDT"));      
		    	obj.setUsedToBook(rs.getString("usedToBook") == null ? "" : rs.getString("usedToBook"));
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark"));
		    	obj.setIsAllow(Integer.parseInt(rs.getString("isAllow") == null ? "0" : rs.getString("isAllow")));
		    	obj.setDiscountType(Integer.parseInt(rs.getString("discountType") == null ? "0" : rs.getString("discountType")));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
		    	data.add(obj);
		    }
		    
		} catch (NumberFormatException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	    
	    return data;
	}
	
	/**
	 * 
	 * @return
	 */
	protected static String genPromoCode(){
		
		final String alphabet = "1234567890qazwsxedcrfvtgbyhnujmikolp";
		
		StringBuffer code = new StringBuffer();
        for(int i = 0; i <  10 ; i++){
        	int number = (int)(Math.random()*26);
            char ch = alphabet.charAt(number);
            code.append(ch);
        }

    	return code.toString();
	}
	
	
}