package com.iposb.my;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.sys.ConnectionManager;

public class MyBusinessModel {
	

	static Logger logger = Logger.getLogger(MyBusinessModel.class);
	
	private MyBusinessModel(){
	}
	
	
	public static ArrayList<MyDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		MyDataModel obj = null;

		try {
			
	    	obj = new MyDataModel();
	    	obj.setAid(Integer.parseInt(request.getParameter("aid") == null ? "0" : request.getParameter("aid")));
	    	obj.setMid(Integer.parseInt(request.getParameter("mid") == null ? "0" : request.getParameter("mid")));
	    	obj.setReceiverName(request.getParameter("receiverName") == null ? "" : request.getParameter("receiverName"));
	    	obj.setReceiverAttn(request.getParameter("receiverAttn") == null ? "" : request.getParameter("receiverAttn"));
	    	obj.setReceiverAddress1(request.getParameter("receiverAddress1") == null ? "" : request.getParameter("receiverAddress1"));
	    	obj.setReceiverAddress2(request.getParameter("receiverAddress2") == null ? "" : request.getParameter("receiverAddress2"));
	    	obj.setReceiverAddress3(request.getParameter("receiverAddress3") == null ? "" : request.getParameter("receiverAddress3"));
	    	obj.setReceiverPostcode(request.getParameter("receiverPostcode") == null ? "" : request.getParameter("receiverPostcode"));
	    	obj.setReceiverPhone(request.getParameter("receiverPhone") == null ? "" : request.getParameter("receiverPhone"));
	    	obj.setReceiverZone(Integer.parseInt(request.getParameter("receiverZone") == null ? "0" : request.getParameter("receiverZone")));
	    	obj.setReceiverArea(Integer.parseInt(request.getParameter("receiverArea") == null ? "0" : request.getParameter("receiverArea")));
	    	obj.setReceiverCountry(request.getParameter("receiverCountry") == null ? "" : request.getParameter("receiverCountry"));
	    	obj.setVerify(request.getParameter("verify") == null ? "" : request.getParameter("verify"));
	    	obj.setCid(Integer.parseInt(request.getParameter("cid") == null ? "0" : request.getParameter("cid")));
	    	obj.setContactName(request.getParameter("contactName") == null ? "" : request.getParameter("contactName"));
	    	obj.setContactPhone(request.getParameter("contactPhone") == null ? "" : request.getParameter("contactPhone"));
	    	obj.setContactAddress(request.getParameter("contactAddress") == null ? "" : request.getParameter("contactAddress"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}

		return data;
	}
	
	
	
	/**
	 * user查看自己的 Addressbook 清單
	 * @param mid
	 * @return
	 */
	public static ArrayList<MyDataModel> addressbookList(int mid) {
		
		ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		MyDataModel obj = null;
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

			sql = "SELECT * FROM addressbook WHERE mid = " + mid + " ORDER BY aid ASC";

		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new MyDataModel();
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setMid(Integer.parseInt(rs.getString("mid") == null ? "0" : rs.getString("mid")));
		    	obj.setReceiverName(rs.getString("receiverName") == null ? "" : rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn") == null ? "" : rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1") == null ? "" : rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2") == null ? "" : rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3") == null ? "" : rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode") == null ? "" : rs.getString("receiverPostcode"));  
		    	obj.setReceiverPhone(rs.getString("receiverPhone") == null ? "" : rs.getString("receiverPhone"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone") == null ? "0" : rs.getString("receiverZone")));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea") == null ? "0" : rs.getString("receiverArea")));
		    	obj.setReceiverCountry(rs.getString("receiverCountry") == null ? "" : rs.getString("receiverCountry"));
		    	obj.setVerify(rs.getString("verify") == null ? "" : rs.getString("verify"));
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
	 * address book 詳情
	 * @param verify
	 * @param mid
	 * @return
	 */
	public static ArrayList<MyDataModel> addressbookDetails(String verify, int mid) {

		ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		MyDataModel obj = null;
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

			sql = "SELECT * FROM addressbook WHERE verify = '" + verify + "' AND mid = " + mid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new MyDataModel();
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setMid(Integer.parseInt(rs.getString("mid") == null ? "0" : rs.getString("mid")));
		    	obj.setReceiverName(rs.getString("receiverName") == null ? "" : rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn") == null ? "" : rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1") == null ? "" : rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2") == null ? "" : rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3") == null ? "" : rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode") == null ? "" : rs.getString("receiverPostcode"));  
		    	obj.setReceiverPhone(rs.getString("receiverPhone") == null ? "" : rs.getString("receiverPhone"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone") == null ? "0" : rs.getString("receiverZone")));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea") == null ? "0" : rs.getString("receiverArea")));
		    	obj.setReceiverCountry(rs.getString("receiverCountry") == null ? "" : rs.getString("receiverCountry"));
		    	obj.setVerify(rs.getString("verify") == null ? "" : rs.getString("verify"));
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
	 * 新增 Addressbook
	 * @param data
	 * @param mid
	 * @return
	 */
	public static String addNewAddressbook(ArrayList<MyDataModel> data, int mid) {

		
		MyDataModel myData = new MyDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			myData = (MyDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
				
				String verify = ConsignmentBusinessModel.generateVerifyCode();
	
				pstmt = conn.prepareStatement("INSERT INTO addressbook (mid, receiverName, receiverAttn, receiverAddress1, receiverAddress2, receiverAddress3, receiverPostcode, receiverPhone, receiverZone, receiverArea, receiverCountry, verify) " +
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	
				pstmt.setInt(1, mid);
				pstmt.setString(2, myData.getReceiverName());
				pstmt.setString(3, myData.getReceiverAttn());
				pstmt.setString(4, myData.getReceiverAddress1());
				pstmt.setString(5, myData.getReceiverAddress2());
				pstmt.setString(6, myData.getReceiverAddress3());
				pstmt.setString(7, myData.getReceiverPostcode());
				pstmt.setString(8, myData.getReceiverPhone());
				pstmt.setInt(9, myData.getReceiverZone());
				pstmt.setInt(10, myData.getReceiverArea());
				pstmt.setString(11, myData.getReceiverCountry());
				pstmt.setString(12, verify);
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
	 * 更新 address book 內容
	 * @param data
	 * @param mid
	 * @return
	 */
	public static String updateAddressbook(ArrayList<MyDataModel> data, int mid) {

		String result = "";
		
		MyDataModel myData = new MyDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			myData = (MyDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE addressbook SET receiverName='"+myData.getReceiverName()+"', receiverAttn='"+myData.getReceiverAttn()+"', receiverAddress1='"+myData.getReceiverAddress1()+"'," +
					  " receiverAddress2='"+myData.getReceiverAddress2()+"', receiverAddress3='"+myData.getReceiverAddress3()+"'," +
					  " receiverPostcode='"+myData.getReceiverPostcode()+"', receiverPhone='"+myData.getReceiverPhone()+"'," +
					  " receiverZone="+myData.getReceiverZone()+", receiverArea="+myData.getReceiverArea()+", receiverCountry='"+myData.getReceiverCountry()+"' WHERE verify='" +myData.getVerify()+ "' AND mid=" + mid;
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
	 * 根據 UserId 取得 mid
	 * 
	 * @param userId
	 * @return
	 */
	public static int getMidByUserId(String userId) {

		int mid = 0;
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

		    sql = "SELECT mid FROM memberlist WHERE userId = '" +userId+ "'";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	mid = Integer.parseInt(rs.getString("mid") == null ? "0" : rs.getString("mid").toString());
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

		return mid;
	}
	
	
	
	/**
	 * credit user查看自己的 credit account 清單
	 * @param mid
	 * @return
	 */
	public static ArrayList<MyDataModel> creditaccountList(int mid) {
		
		ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		MyDataModel obj = null;
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

			sql = "SELECT * FROM creditaccount WHERE mid = " + mid + " ORDER BY cid ASC";

		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new MyDataModel();
		    	obj.setCid(Integer.parseInt(rs.getString("cid") == null ? "0" : rs.getString("cid")));
		    	obj.setMid(Integer.parseInt(rs.getString("mid") == null ? "0" : rs.getString("mid")));
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setContactName(rs.getString("contactName") == null ? "" : rs.getString("contactName"));
		    	obj.setContactPhone(rs.getString("contactPhone") == null ? "" : rs.getString("contactPhone"));
		    	obj.setContactAddress(rs.getString("contactAddress") == null ? "" : rs.getString("contactAddress"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	obj.setVerify(rs.getString("verify") == null ? "" : rs.getString("verify"));
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
	 * 新增 Credit Account
	 * @param data
	 * @param mid
	 * @return
	 */
	public static String addNewCreditaccount(ArrayList<MyDataModel> data, int mid) {

		
		MyDataModel myData = new MyDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			myData = (MyDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
				
				String verify = ConsignmentBusinessModel.generateVerifyCode();
	
				pstmt = conn.prepareStatement("INSERT INTO creditaccount (mid, aid, contactName, contactPhone, contactAddress, createDT, verify) " +
						"VALUE " + "(?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setInt(1, mid);
				pstmt.setInt(2, myData.getAid());
				pstmt.setString(3, myData.getContactName());
				pstmt.setString(4, myData.getContactPhone());
				pstmt.setString(5, myData.getContactAddress());
				pstmt.setString(6, verify);
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
	 * 更新 credit account 內容
	 * @param data
	 * @param mid
	 * @return
	 */
	public static String updateCreditaccount (ArrayList<MyDataModel> data, int mid) {

		String result = "";
		
		MyDataModel myData = new MyDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			myData = (MyDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE creditaccount SET aid="+myData.getAid()+", contactName='"+myData.getContactName()+"', contactPhone='"+myData.getContactPhone()+"'," +
					  " contactAddress='"+myData.getContactAddress()+"', modifyDT=NOW() WHERE verify='" +myData.getVerify()+ "' AND mid=" + mid;
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
	 * credit account 詳情
	 * @param verify
	 * @param mid
	 * @return
	 */
	public static ArrayList<MyDataModel> creditaccountDetails(String verify, int mid) {

		ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		MyDataModel obj = null;
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

			sql = "SELECT * FROM creditaccount WHERE verify = '" + verify + "' AND mid = " + mid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new MyDataModel();
		    	obj.setCid(Integer.parseInt(rs.getString("cid") == null ? "0" : rs.getString("cid")));
		    	obj.setMid(Integer.parseInt(rs.getString("mid") == null ? "0" : rs.getString("mid")));
		    	obj.setAid(Integer.parseInt(rs.getString("aid") == null ? "0" : rs.getString("aid")));
		    	obj.setContactName(rs.getString("contactName") == null ? "" : rs.getString("contactName"));
		    	obj.setContactPhone(rs.getString("contactPhone") == null ? "" : rs.getString("contactPhone"));
		    	obj.setContactAddress(rs.getString("contactAddress") == null ? "" : rs.getString("contactAddress"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	obj.setVerify(rs.getString("verify") == null ? "" : rs.getString("verify"));
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
	 * 取得最大(最新)的BID
	 * @return
	 */
	protected static int getMaxBid() {

		int maxBid = 0;
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

		    sql = "SELECT MAX(bid) AS bid FROM bulletins";
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
	
	
}