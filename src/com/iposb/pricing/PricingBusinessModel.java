package com.iposb.pricing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.iposb.sys.ConnectionManager;

public class PricingBusinessModel {
	

	static Logger logger = Logger.getLogger(PricingBusinessModel.class);
	
	private PricingBusinessModel(){
	}
	
	
	public static ArrayList<PricingDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;

		try {
			
	    	obj = new PricingDataModel();
	    	obj.setPid(Integer.parseInt(request.getParameter("pid") == null ? "0" : request.getParameter("pid")));
	    	obj.setCategory(Integer.parseInt(request.getParameter("category") == null ? "0" : request.getParameter("category").equals("") ? "0" : request.getParameter("category")));
	    	obj.setTitle_enUS(request.getParameter("title_enUS") == null ? "" : request.getParameter("title_enUS"));
	    	obj.setFromArea(Integer.parseInt(request.getParameter("fromArea") == null ? "0" : request.getParameter("fromArea")));
	    	obj.setToArea(Integer.parseInt(request.getParameter("toArea") == null ? "0" : request.getParameter("toArea")));
	    	obj.setFirst(Double.parseDouble(request.getParameter("first") == null ? "0" : request.getParameter("first").equals("") ? "0" : request.getParameter("first")));
	    	obj.setFirstPrice(Double.parseDouble(request.getParameter("firstPrice") == null ? "0" : request.getParameter("firstPrice").equals("") ? "0" : request.getParameter("firstPrice")));
	    	obj.setAddition(Double.parseDouble(request.getParameter("addition") == null ? "0" : request.getParameter("addition").equals("") ? "0" : request.getParameter("addition")));
	    	obj.setAdditionPrice(Double.parseDouble(request.getParameter("additionPrice") == null ? "0" : request.getParameter("additionPrice").equals("") ? "0" : request.getParameter("additionPrice")));
	    	obj.setWeightEach(Double.parseDouble(request.getParameter("weightEach") == null ? "0" : request.getParameter("weightEach").equals("") ? "0" : request.getParameter("weightEach")));
	    	obj.setAdditionCharge(Double.parseDouble(request.getParameter("additionCharge") == null ? "0" : request.getParameter("additionCharge").equals("") ? "0" : request.getParameter("additionCharge")));
	    	obj.setHandling(Integer.parseInt(request.getParameter("handling") == null ? "0" : request.getParameter("handling").equals("") ? "0" : request.getParameter("handling")));
	    	obj.setFuel(Integer.parseInt(request.getParameter("fuel") == null ? "0" : request.getParameter("fuel").equals("") ? "0" : request.getParameter("fuel")));
	    	obj.setFirstRange(Integer.parseInt(request.getParameter("firstRange") == null ? "0" : request.getParameter("firstRange").equals("") ? "0" : request.getParameter("firstRange")));
	    	obj.setFirstPrice(Double.parseDouble(request.getParameter("firstPrice") == null ? "0" : request.getParameter("firstPrice").equals("") ? "0" : request.getParameter("firstPrice")));
	    	obj.setSecondRange(Integer.parseInt(request.getParameter("secondRange") == null ? "0" : request.getParameter("secondRange").equals("") ? "0" : request.getParameter("secondRange")));
	    	obj.setSecondPrice(Double.parseDouble(request.getParameter("secondPrice") == null ? "0" : request.getParameter("secondPrice").equals("") ? "0" : request.getParameter("secondPrice")));
	    	obj.setThirdRange(Integer.parseInt(request.getParameter("thirdRange") == null ? "0" : request.getParameter("thirdRange").equals("") ? "0" : request.getParameter("thirdRange")));
	    	obj.setThirdPrice(Double.parseDouble(request.getParameter("thirdPrice") == null ? "0" : request.getParameter("thirdPrice").equals("") ? "0" : request.getParameter("thirdPrice")));
	    	obj.setForthRange(Integer.parseInt(request.getParameter("forthRange") == null ? "0" : request.getParameter("forthRange").equals("") ? "0" : request.getParameter("forthRange")));
	    	obj.setForthPrice(Double.parseDouble(request.getParameter("forthPrice") == null ? "0" : request.getParameter("forthPrice").equals("") ? "0" : request.getParameter("forthPrice")));
	    	obj.setFifthPrice(Double.parseDouble(request.getParameter("fifthPrice") == null ? "0" : request.getParameter("fifthPrice").equals("") ? "0" : request.getParameter("fifthPrice")));
	    	obj.setCodPrice(Double.parseDouble(request.getParameter("codPrice") == null ? "0" : request.getParameter("codPrice").equals("") ? "0" : request.getParameter("codPrice")));
	    	obj.setDiscountFirstPriceA(Double.parseDouble(request.getParameter("discountFirstPriceA") == null ? "0" : request.getParameter("discountFirstPriceA").equals("") ? "0" : request.getParameter("discountFirstPriceA")));
	    	obj.setDiscountAdditionPriceA(Double.parseDouble(request.getParameter("discountAdditionPriceA") == null ? "0" : request.getParameter("discountAdditionPriceA").equals("") ? "0" : request.getParameter("discountAdditionPriceA")));
	    	obj.setDiscountFirstPriceB(Double.parseDouble(request.getParameter("discountFirstPriceB") == null ? "0" : request.getParameter("discountFirstPriceB").equals("") ? "0" : request.getParameter("discountFirstPriceB")));
	    	obj.setDiscountAdditionPriceB(Double.parseDouble(request.getParameter("discountAdditionPriceB") == null ? "0" : request.getParameter("discountAdditionPriceB").equals("") ? "0" : request.getParameter("discountAdditionPriceB")));
	    	obj.setDiscountFirstPriceC(Double.parseDouble(request.getParameter("discountFirstPriceC") == null ? "0" : request.getParameter("discountFirstPriceC").equals("") ? "0" : request.getParameter("discountFirstPriceC")));
	    	obj.setDiscountAdditionPriceC(Double.parseDouble(request.getParameter("discountAdditionPriceC") == null ? "0" : request.getParameter("discountAdditionPriceC").equals("") ? "0" : request.getParameter("discountAdditionPriceC")));
	    	obj.setDiscountFirstPriceD(Double.parseDouble(request.getParameter("discountFirstPriceD") == null ? "0" : request.getParameter("discountFirstPriceD").equals("") ? "0" : request.getParameter("discountFirstPriceD")));
	    	obj.setDiscountAdditionPriceD(Double.parseDouble(request.getParameter("discountAdditionPriceD") == null ? "0" : request.getParameter("discountAdditionPriceD").equals("") ? "0" : request.getParameter("discountAdditionPriceD")));
	    	obj.setIsShow(Integer.parseInt(request.getParameter("isShow") == null ? "0" : request.getParameter("isShow").equals("on") ? "1" : "0"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}

		return data;
	}
	
	
	/**
	 * 前台查詢 Pricing_partner 列表
	 * 
	 * @param orderBy
	 * @param pageNo
	 * @return
	 */
	protected static ArrayList<PricingDataModel> searchPricing_partner(String orderBy, int pageNo) {

		String sqlCondition = "";
		
		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
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

		    sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.pid) FROM pricing_partner b " + sqlCondition + ") AS total" +
		    		" FROM pricing_partner a" + sqlCondition + 
		    		" ORDER BY a." + orderBy + " DESC, a.pid ASC LIMIT " + page + ", " + numberPerPage;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setCategory(Integer.parseInt(rs.getString("category") == null ? "0" : rs.getString("category")));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
		    	obj.setCodPrice(Double.parseDouble(rs.getString("codPrice") == null ? "0" : rs.getString("codPrice")));
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
	 * 管理員查看Pricing_partner清單
	 * @param orderby
	 * @param pageNo
	 * @param category
	 * @return
	 */
	public static ArrayList<PricingDataModel> pricingList_partner(String orderby, int category) {

		String sqlCondition = "";
		
		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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
			
			if(category > 0){
		    	sqlCondition += " WHERE category = " + category;
		    }

			sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.pid) FROM pricing_partner b " + sqlCondition + ") AS total" +
		    		" FROM pricing_partner a " + sqlCondition;
		    
		    if(orderby!=null && !orderby.equals("")){
		    	sql += " ORDER BY a." + orderby + " DESC";
		    }
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setCategory(Integer.parseInt(rs.getString("category") == null ? "0" : rs.getString("category")));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
		    	obj.setCodPrice(Double.parseDouble(rs.getString("codPrice") == null ? "0" : rs.getString("codPrice")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
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
	 * @param pid
	 * @return
	 */
	protected static ArrayList<PricingDataModel> pricingDetails_partner(String pid) {

		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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

			sql = "SELECT a.* FROM pricing_partner a WHERE a.pid = " + pid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setCategory(Integer.parseInt(rs.getString("category") == null ? "0" : rs.getString("category")));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
		    	obj.setCodPrice(Double.parseDouble(rs.getString("codPrice") == null ? "0" : rs.getString("codPrice")));
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
	 * 新增 pricing_partner
	 * @param data
	 * @param userId
	 * @return
	 */
	protected static String addNewPricing_partner (ArrayList<PricingDataModel> data, String userId) {

		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO pricing_partner(category, title_enUS, first, firstPrice, addition," +
						" additionPrice, codPrice, isShow, createDT, creator) " +
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setInt(1, pricingData.getCategory());
				pstmt.setString(2, pricingData.getTitle_enUS());
				pstmt.setDouble(3, pricingData.getFirst());
				pstmt.setDouble(4, pricingData.getFirstPrice());
				pstmt.setDouble(5, pricingData.getAddition());
				pstmt.setDouble(6, pricingData.getAdditionPrice());
				pstmt.setDouble(7, pricingData.getCodPrice());
				pstmt.setInt(8, pricingData.getIsShow());
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
	 * @param userId
	 * @return
	 */
	protected static String updatePricing_partner(ArrayList<PricingDataModel> data, String userId) {

		String result = "";
		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE pricing_partner SET category="+pricingData.getCategory()+", title_enUS='"+pricingData.getTitle_enUS()+"'," +
						" first="+pricingData.getFirst()+", firstPrice="+pricingData.getFirstPrice()+"," +
						" addition="+pricingData.getAddition()+", additionPrice="+pricingData.getAdditionPrice()+"," +
						" codPrice="+pricingData.getCodPrice()+", isShow="+pricingData.getIsShow()+"," +
						" modifyDT=NOW(), modifier='"+userId+"' WHERE pid=" +pricingData.getPid();
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
	 * 取得最大(最新)的BID
	 * @return
	 */
	protected static int getMaxPid_partner() {

		int maxPid = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }
			
			stmt = conn.createStatement();

		    sql = "SELECT MAX(pid) AS pid FROM pricing_partner";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxPid = Integer.parseInt(rs.getString("pid") == null ? "1" : rs.getString("pid").toString());
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

		return maxPid;
	}
	
	
	
	
	/************************ Normal User (Parcel) **************************/
	
	
	
	/**
	 * 前台查詢 Pricing_normal 列表
	 * 
	 * @param orderBy
	 * @param pageNo
	 * @return
	 */
	/*
	protected static ArrayList<PricingDataModel> searchPricing_normal(String orderBy, int pageNo) {

		String sqlCondition = "";
		
		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
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

		    sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.pid) FROM pricing_normal_parcel b " + sqlCondition + ") AS total" +
		    		" FROM pricing_normal_parcel a" + sqlCondition + 
		    		" ORDER BY a." + orderBy + " DESC, a.pid ASC LIMIT " + page + ", " + numberPerPage;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setFromArea(Integer.parseInt(rs.getString("fromArea") == null ? "0" : rs.getString("fromArea")));
		    	obj.setToArea(Integer.parseInt(rs.getString("toArea") == null ? "0" : rs.getString("toArea")));
		    	obj.setWeightEach(Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach")));
		    	obj.setAdditionCharge(Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge")));
		    	obj.setHandling(Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling")));
		    	obj.setFuel(Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel")));
		    	obj.setFirstRange(Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setSecondRange(Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange")));
		    	obj.setSecondPrice(Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice")));
		    	obj.setThirdRange(Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange")));
		    	obj.setThirdPrice(Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice")));
		    	obj.setForthRange(Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange")));
		    	obj.setForthPrice(Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice")));
		    	obj.setFifthPrice(Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice")));
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
	*/
	
	
	/**
	 * 管理員查看Pricing_normal_parcel清單
	 * 
	 * @return
	 */
	public static ArrayList<PricingDataModel> pricingList_normal_parcel() {

		String sqlCondition = "";
		
		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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
			
			sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.pid) FROM pricing_normal_parcel b " + sqlCondition + ") AS total" +
		    		" FROM pricing_normal_parcel a " + sqlCondition +
		    		" ORDER BY a.pid ASC";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setFromArea(Integer.parseInt(rs.getString("fromArea") == null ? "0" : rs.getString("fromArea")));
		    	obj.setToArea(Integer.parseInt(rs.getString("toArea") == null ? "0" : rs.getString("toArea")));
		    	obj.setWeightEach(Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach")));
		    	obj.setAdditionCharge(Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge")));
		    	obj.setHandling(Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling")));
		    	obj.setFuel(Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel")));
		    	obj.setFirstRange(Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setSecondRange(Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange")));
		    	obj.setSecondPrice(Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice")));
		    	obj.setThirdRange(Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange")));
		    	obj.setThirdPrice(Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice")));
		    	obj.setForthRange(Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange")));
		    	obj.setForthPrice(Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice")));
		    	obj.setFifthPrice(Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
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
	 * @param pid
	 * @return
	 */
	protected static ArrayList<PricingDataModel> pricingDetails_normal_parcel(String pid) {

		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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

			sql = "SELECT a.* FROM pricing_normal_parcel a WHERE a.pid = " + pid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setFromArea(Integer.parseInt(rs.getString("fromArea") == null ? "0" : rs.getString("fromArea")));
		    	obj.setToArea(Integer.parseInt(rs.getString("toArea") == null ? "0" : rs.getString("toArea")));
		    	obj.setWeightEach(Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach")));
		    	obj.setAdditionCharge(Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge")));
		    	obj.setHandling(Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling")));
		    	obj.setFuel(Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel")));
		    	obj.setFirstRange(Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setSecondRange(Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange")));
		    	obj.setSecondPrice(Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice")));
		    	obj.setThirdRange(Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange")));
		    	obj.setThirdPrice(Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice")));
		    	obj.setForthRange(Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange")));
		    	obj.setForthPrice(Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice")));
		    	obj.setFifthPrice(Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
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
	 * 新增 pricing_normal
	 * @param data
	 * @param userId
	 * @return
	 */
	protected static String addNewPricing_normal_parcel(ArrayList<PricingDataModel> data, String userId) {

		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO pricing_normal_parcel(fromArea, toArea, weightEach, additionCharge, handling, fuel, firstRange, firstPrice, secondRange, secondPrice," +
						" thirdRange, thirdPrice, forthRange, forthPrice, fifthPrice, isShow, createDT, creator) " +
						"VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setInt(1, pricingData.getFromArea());
				pstmt.setInt(2, pricingData.getToArea());
				pstmt.setDouble(3, pricingData.getWeightEach());
				pstmt.setDouble(4, pricingData.getAdditionCharge());
				pstmt.setInt(5, pricingData.getHandling());
				pstmt.setInt(6, pricingData.getFuel());
				pstmt.setInt(7, pricingData.getFirstRange());
				pstmt.setDouble(8, pricingData.getFirstPrice());
				pstmt.setInt(9, pricingData.getSecondRange());
				pstmt.setDouble(10, pricingData.getSecondPrice());
				pstmt.setInt(11, pricingData.getThirdRange());
				pstmt.setDouble(12, pricingData.getThirdPrice());
				pstmt.setInt(13, pricingData.getForthRange());
				pstmt.setDouble(14, pricingData.getForthPrice());
				pstmt.setDouble(15, pricingData.getFifthPrice());
				pstmt.setInt(16, pricingData.getIsShow());
				pstmt.setString(17, userId);	
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
	 * @param userId
	 * @return
	 */
	protected static String updatePricing_normal_parcel(ArrayList<PricingDataModel> data, String userId) {

		String result = "";
		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE pricing_normal_parcel SET fromArea="+pricingData.getFromArea()+", toArea="+pricingData.getToArea() +"," +
						" weightEach="+pricingData.getWeightEach()+", additionCharge="+pricingData.getAdditionCharge()+"," +
						" handling="+pricingData.getHandling()+", fuel="+pricingData.getFuel()+"," +
						" firstRange="+pricingData.getFirstRange()+", firstPrice="+pricingData.getFirstPrice()+"," +
						" secondRange="+pricingData.getSecondRange()+", secondPrice="+pricingData.getSecondPrice()+"," +
						" thirdRange="+pricingData.getThirdRange()+", thirdPrice="+pricingData.getThirdPrice()+"," +
						" forthRange="+pricingData.getForthRange()+", forthPrice="+pricingData.getForthPrice()+"," +
						" fifthPrice="+pricingData.getCodPrice()+", isShow="+pricingData.getIsShow()+"," +
						" modifyDT=NOW(), modifier='"+userId+"' WHERE pid=" +pricingData.getPid();
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
	 * 取得最大(最新)的 PID
	 * @param shipmentType
	 * @return
	 */
	/*
	protected static int getMaxPid_normal(String shipmentType) {

		int maxPid = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }
			
			stmt = conn.createStatement();

		    sql = "SELECT MAX(pid) AS pid FROM pricing_normal_"+shipmentType;
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxPid = Integer.parseInt(rs.getString("pid") == null ? "1" : rs.getString("pid").toString());
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

		return maxPid;
	}
	*/
	
	
	
	/**
	 * 計算總額
	 * 
	 * 同時計算實際價格 & 原價
	 * 
	 * @param senderArea
	 * @param receiverArea
	 * @param weight
	 * @param quantity
	 * @return
	 */
	public static String calculate_normal_parcel (String senderArea, String receiverArea, double weight, int discount, int discountType,  int quantity) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int fromArea = 0;
		int toArea = 0;
		int fromAreaBelongArea = 0;
		int toAreaBelongArea = 0;
		boolean sameArea = false;

		double disAmount = 0;
		double amount = 0;
		double originalAmount = 0;
		
		//major town
		int pid = 0;
		double weightEach = 0.0;
		double additionCharge = 0.0;
		int handling = 0;
		int fuel = 0;
		int firstRange = 0;
		double firstPrice = 0.0;
		int secondRange = 0;
		double secondPrice = 0.0;
		int thirdRange = 0;
		double thirdPrice = 0.0;
		int forthRange = 0;
		double forthPrice = 0.0;
		double fifthPrice = 0.0;
		double subtotal = 0.0;
		
		//minor town 1
		double weightEach_1m = 0.0;
		double additionCharge_1m = 0.0;
		int handling_1m = 0;
		int fuel_1m = 0;
		int firstRange_1m = 0;
		double firstPrice_1m = 0.0;
		int secondRange_1m = 0;
		double secondPrice_1m = 0.0;
		int thirdRange_1m = 0;
		double thirdPrice_1m = 0.0;
		int forthRange_1m = 0;
		double forthPrice_1m = 0.0;
		double fifthPrice_1m = 0.0;
		double subtotal_1m = 0.0;
		
		//minor town 2
		double weightEach_2m = 0.0;
		double additionCharge_2m = 0.0;
		int handling_2m = 0;
		int fuel_2m = 0;
		int firstRange_2m = 0;
		double firstPrice_2m = 0.0;
		int secondRange_2m = 0;
		double secondPrice_2m = 0.0;
		int thirdRange_2m = 0;
		double thirdPrice_2m = 0.0;
		int forthRange_2m = 0;
		double forthPrice_2m = 0.0;
		double fifthPrice_2m = 0.0;
		double subtotal_2m = 0.0;

		
		boolean isFromAreaMajor = false;
		boolean isToAreaMajor = false;
		
		try {
			
			if( (senderArea.length()>1)&&(receiverArea.length()>1) ) {
				
				fromArea = Integer.parseInt(senderArea.substring(0, 6));
				fromAreaBelongArea = Integer.parseInt(senderArea.substring(7, 13));
				toArea = Integer.parseInt(receiverArea.substring(0, 6));
				toAreaBelongArea = Integer.parseInt(receiverArea.substring(7, 13));
				
				if(senderArea.substring(6, 7).equals("A")) { //第7碼為 A
					isFromAreaMajor = true;
				}
				
				if(receiverArea.substring(6, 7).equals("A")) { //第7碼為 A
					isToAreaMajor = true;
				}

				
				if(senderArea.substring(6, 7).equals("B")) { //第7碼為 B
					if(fromAreaBelongArea == toArea) { //minor town 往屬於自己的 major town 移動
						sameArea = true;
					}
				}
				
				if(receiverArea.substring(6, 7).equals("B")) { //第7碼為 B
					if(toAreaBelongArea == fromArea) { //major town 往屬於自己的 minor town 移動
						sameArea = true;
					}
				}
				
				
			}
			
			/************************ 如果兩個都是 major town，或者是 same area ************************/
			if( (isFromAreaMajor && isToAreaMajor)||(sameArea)) {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+fromArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+fromArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    //先計算實際價格
			    subtotal = calculateTotal_normal(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice, secondRange, secondPrice, thirdRange, thirdPrice, forthRange, forthPrice, fifthPrice);
			    amount = subtotal;
			    
			    //再計算原價
			    subtotal = calculateOriginalPrice(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice);
			    originalAmount = subtotal;
			} 
			
			/************************ 其中一個是 major town ************************/
			else if (isFromAreaMajor && !isToAreaMajor) { //toArea 是 Minor town，所以要找出其所屬的 Major town
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+fromArea+" AND a.toArea = "+toAreaBelongArea+") OR (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+fromArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    
			    //Major town < > Minor town
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+toAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_1m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge_1m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_1m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_1m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange_1m = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice_1m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange_1m = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice_1m = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange_1m = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice_1m = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange_1m = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice_1m = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice_1m = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    //先計算實際價格
			    subtotal = calculateTotal_normal(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice, secondRange, secondPrice, thirdRange, thirdPrice, forthRange, forthPrice, fifthPrice);
			    subtotal_1m = calculateTotal_normal(weight, quantity, weightEach_1m, additionCharge_1m, handling_1m, fuel_1m, firstRange_1m, firstPrice_1m, secondRange_1m, secondPrice_1m, thirdRange_1m, thirdPrice_1m, forthRange_1m, forthPrice_1m, fifthPrice_1m);
			    amount = subtotal + subtotal_1m;
			    
			    //再計算原價
			    subtotal = calculateOriginalPrice(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice);
			    subtotal_1m = calculateOriginalPrice(weight, quantity, weightEach_1m, additionCharge_1m, handling_1m, fuel_1m, firstRange_1m, firstPrice_1m);
			    originalAmount = subtotal + subtotal_1m;
			    
			} 
			
			/************************ 其中一個是 major town ************************/
			else if(!isFromAreaMajor && isToAreaMajor) { //fromArea 是 Minor town，所以要找出其所屬的 Major town
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    
			    //Major town < > Minor town
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+fromArea+") OR (a.fromArea = "+fromArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_1m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge_1m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_1m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_1m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange_1m = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice_1m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange_1m = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice_1m = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange_1m = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice_1m = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange_1m = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice_1m = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice_1m = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    //先計算實際價格
			    subtotal = calculateTotal_normal(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice, secondRange, secondPrice, thirdRange, thirdPrice, forthRange, forthPrice, fifthPrice);
			    subtotal_1m = calculateTotal_normal(weight, quantity, weightEach_1m, additionCharge_1m, handling_1m, fuel_1m, firstRange_1m, firstPrice_1m, secondRange_1m, secondPrice_1m, thirdRange_1m, thirdPrice_1m, forthRange_1m, forthPrice_1m, fifthPrice_1m);
			    amount = subtotal + subtotal_1m;
			    
			    //再計算原價
			    subtotal = calculateOriginalPrice(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice);
			    subtotal_1m = calculateOriginalPrice(weight, quantity, weightEach_1m, additionCharge_1m, handling_1m, fuel_1m, firstRange_1m, firstPrice_1m);
			    originalAmount = subtotal + subtotal_1m;
			    
			} 
			
			/************************ 兩個都不是 major town ************************/
			else {
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+toAreaBelongArea+") OR (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    
			    //Major town < > Minor town 1
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+fromArea+") OR (a.fromArea = "+fromArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_1m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge_1m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_1m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_1m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange_1m = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice_1m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange_1m = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice_1m = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange_1m = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice_1m = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange_1m = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice_1m = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice_1m = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    
			    //Major town < > Minor town 2
				sql = "SELECT a.* FROM pricing_normal_parcel a WHERE (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+toAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_2m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	additionCharge_2m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_2m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_2m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	firstRange_2m = Integer.parseInt(rs.getString("firstRange") == null ? "0" : rs.getString("firstRange").toString());
			    	firstPrice_2m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	secondRange_2m = Integer.parseInt(rs.getString("secondRange") == null ? "0" : rs.getString("secondRange").toString());
			    	secondPrice_2m = Double.parseDouble(rs.getString("secondPrice") == null ? "0" : rs.getString("secondPrice").toString());
			    	thirdRange_2m = Integer.parseInt(rs.getString("thirdRange") == null ? "0" : rs.getString("thirdRange").toString());
			    	thirdPrice_2m = Double.parseDouble(rs.getString("thirdPrice") == null ? "0" : rs.getString("thirdPrice").toString());
			    	forthRange_2m = Integer.parseInt(rs.getString("forthRange") == null ? "0" : rs.getString("forthRange").toString());
			    	forthPrice_2m = Double.parseDouble(rs.getString("forthPrice") == null ? "0" : rs.getString("forthPrice").toString());
			    	fifthPrice_2m = Double.parseDouble(rs.getString("fifthPrice") == null ? "0" : rs.getString("fifthPrice").toString());
			    }
			    
			    
			    //先計算實際價格
			    subtotal = calculateTotal_normal(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice, secondRange, secondPrice, thirdRange, thirdPrice, forthRange, forthPrice, fifthPrice);
			    subtotal_1m = calculateTotal_normal(weight, quantity, weightEach_1m, additionCharge_1m, handling_1m, fuel_1m, firstRange_1m, firstPrice_1m, secondRange_1m, secondPrice_1m, thirdRange_1m, thirdPrice_1m, forthRange_1m, forthPrice_1m, fifthPrice_1m);
			    subtotal_2m = calculateTotal_normal(weight, quantity, weightEach_2m, additionCharge_2m, handling_2m, fuel_2m, firstRange_2m, firstPrice_2m, secondRange_2m, secondPrice_2m, thirdRange_2m, thirdPrice_2m, forthRange_2m, forthPrice_2m, fifthPrice_2m);
			    amount = subtotal + subtotal_1m + subtotal_2m;
			    
			    //再計算原價
			    subtotal = calculateOriginalPrice(weight, quantity, weightEach, additionCharge, handling, fuel, firstRange, firstPrice);
			    subtotal_1m = calculateOriginalPrice(weight, quantity, weightEach_1m, additionCharge_1m, handling_1m, fuel_1m, firstRange_1m, firstPrice_1m);
			    subtotal_2m = calculateOriginalPrice(weight, quantity, weightEach_2m, additionCharge_2m, handling_2m, fuel_2m, firstRange_2m, firstPrice_2m);
			    originalAmount = subtotal + subtotal_1m + subtotal_2m;			    
			}


		    if(discountType ==0){ // RM
		    	disAmount = amount - Double.parseDouble( Integer.toString(discount) );
		    }else if(discountType == 1){ // percentage
		    	disAmount = amount - ( (amount/100.0) * Double.parseDouble( Integer.toString(discount) ) );
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
	
		return disAmount + "|" + amount + "|" + originalAmount;

		    
	}
	
	/**
	 * 逐項計算（實價）
	 * 
	 * @param weight
	 * @param quantity
	 * @param weightEach
	 * @param additionCharge
	 * @param handling
	 * @param fuel
	 * @param firstRange
	 * @param firstPrice
	 * @param secondRange
	 * @param secondPrice
	 * @param thirdRange
	 * @param thirdPrice
	 * @param forthRange
	 * @param forthPrice
	 * @param fifthPrice
	 * @return
	 */
	private static double calculateTotal_normal(double weight, int quantity, double weightEach, double additionCharge, int handling, int fuel, int firstRange, double firstPrice, int secondRange, double secondPrice, int thirdRange, double thirdPrice,
			int forthRange, double forthPrice, double fifthPrice) {

		double amount = 0.0;
		double price1 = 0.0;
		double price2 = 0.0;
		double handlingCharge = 0.0;
		double fuelCharge = 0.0;
		
		if(weight <= (weightEach * quantity)) { //如果沒有超過基礎的公斤數
	    	
			//計算金額
	    	if(quantity <= firstRange) {
	    		amount = quantity * firstPrice;
	    	} else if (quantity > firstRange && quantity <= secondRange) {
	    		amount = quantity * secondPrice;
	    	} else if (quantity > secondRange && quantity <= thirdRange) {
	    		amount = quantity * thirdPrice;
	    	} else if (quantity > thirdRange && quantity <= forthRange) {
	    		amount = quantity * forthPrice;
	    	} else if (quantity > forthRange) {
	    		amount = quantity * fifthPrice;
	    	}

	    	
	    	//計算 handling charge 及 fuel surcharge
			if(handling > 0) {
				handlingCharge = (handling/100.0) * amount; //務必要加上 .0，否則返回的是整數
			}
			if(fuel > 0) {
				fuelCharge = (fuel/100.0) * amount; //務必要加上 .0，否則返回的是整數
			}
			
			//加總
			amount = amount + handlingCharge + fuelCharge;
	    	
	    } else {
	    	
	    	//計算第一部分的價格（件數）
	    	if(quantity <= firstRange) {
	    		price1 = quantity * firstPrice;
	    	} else if (quantity > firstRange && quantity <= secondRange) {
	    		price1 = quantity * secondPrice;
	    	} else if (quantity > secondRange && quantity <= thirdRange) {
	    		price1 = quantity * thirdPrice;
	    	} else if (quantity > thirdRange && quantity <= forthRange) {
	    		price1 = quantity * forthPrice;
	    	} else if (quantity > forthRange) {
	    		price1 = quantity * fifthPrice;
	    	}
	    	
	    	//計算第二部分的價格（超出的重量）
		    double additionWeight = weight - (weightEach * quantity);
			if(additionWeight > 0) {
				price2 = additionWeight * additionCharge;
			}
			
			//計算 handling charge 及 fuel surcharge
			double subtotal = price1 + price2;
			
			if(handling > 0) {
				handlingCharge = (handling/100.0) * subtotal; //務必要加上 .0，否則返回的是整數
			}
			if(fuel > 0) {
				fuelCharge = (fuel/100.0) * subtotal; //務必要加上 .0，否則返回的是整數
			}
			
			//加總
			amount = subtotal + handlingCharge + fuelCharge;
			
	    }

		return amount;
		
	}
	
	
	
	/**
	 * 逐項計算（原價）
	 * 
	 * @param weight
	 * @param quantity
	 * @param weightEach
	 * @param additionCharge
	 * @param handling
	 * @param fuel
	 * @param firstRange
	 * @param firstPrice
	 * @param secondRange
	 * @param secondPrice
	 * @param thirdRange
	 * @param thirdPrice
	 * @param forthRange
	 * @param forthPrice
	 * @param fifthPrice
	 * @return
	 */
	private static double calculateOriginalPrice(double weight, int quantity, double weightEach, double additionCharge, int handling, int fuel, int firstRange, double firstPrice) {

		double amount = 0.0;
		double price1 = 0.0;
		double price2 = 0.0;
		double handlingCharge = 0.0;
		double fuelCharge = 0.0;
		
		if(weight <= (weightEach * quantity)) { //如果沒有超過基礎的公斤數

			//計算金額
	    	amount = quantity * firstPrice;

	    	//計算 handling charge 及 fuel surcharge
			if(handling > 0) {
				handlingCharge = (handling/100.0) * amount; //務必要加上 .0，否則返回的是整數
			}
			if(fuel > 0) {
				fuelCharge = (fuel/100.0) * amount; //務必要加上 .0，否則返回的是整數
			}
			
			//加總
			amount = amount + handlingCharge + fuelCharge;
	    	
	    } else {
	    	
	    	//計算第一部分的價格（件數）
	    	price1 = quantity * firstPrice;
	    	
	    	//計算第二部分的價格（超出的重量）
		    double additionWeight = weight - (weightEach * quantity);
			if(additionWeight > 0) {
				price2 = additionWeight * additionCharge;
			}

			
			//計算 handling charge 及 fuel surcharge
			double subtotal = price1 + price2;
			
			if(handling > 0) {
				handlingCharge = (handling/100.0) * subtotal; //務必要加上 .0，否則返回的是整數
			}
			if(fuel > 0) {
				fuelCharge = (fuel/100.0) * subtotal; //務必要加上 .0，否則返回的是整數
			}
			
			//計算金額
			amount = subtotal + handlingCharge + fuelCharge;
			
	    }

		return amount;
		
	}

	
	/************************ Normal User (Document) **************************/
	
	
	/**
	 * 管理員查看Pricing_normal_document清單
	 * 
	 * @return
	 */
	public static ArrayList<PricingDataModel> pricingList_normal_document() {

		String sqlCondition = "";
		
		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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
			
			sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.pid) FROM pricing_normal_document b " + sqlCondition + ") AS total" +
		    		" FROM pricing_normal_document a " + sqlCondition +
		    		" ORDER BY a.pid ASC";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
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
	 * @param pid
	 * @return
	 */
	protected static ArrayList<PricingDataModel> pricingDetails_normal_document(String pid) {

		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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

			sql = "SELECT a.* FROM pricing_normal_document a WHERE a.pid = " + pid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
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
	 * 更新內容
	 * @param data
	 * @param userId
	 * @return
	 */
	protected static String updatePricing_normal_document(ArrayList<PricingDataModel> data, String userId) {

		String result = "";
		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE pricing_normal_document SET first="+pricingData.getFirst()+", firstPrice="+pricingData.getFirstPrice()+"," +
						" addition="+pricingData.getAddition()+", additionPrice="+pricingData.getAdditionPrice()+"," +
						" modifyDT=NOW(), modifier='"+userId+"' WHERE pid=" +pricingData.getPid();
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
	 * 計算 document 價格
	 * @return
	 */
	public static String calculate_normal_document(String senderArea, String receiverArea, double weight, int discount, int discountType) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		double first = 0.0;
		double firstPrice = 0.0;
		double addition = 0.0;
		double additionPrice = 0.0;
		String fromState = "";
		String toState = "";
		int pid = 0; //1: Within Sabah; 2: Sarawak & W'Msia
		double amount = 0.0;
		double disAmount = 0.0;

		try {
			
			
			if( (senderArea.length()>1)&&(receiverArea.length()>1) ) {
				
				fromState = senderArea.length() == 16 ? senderArea.substring(13, 16) : "";
				toState =  receiverArea.length() == 16 ? receiverArea.substring(13, 16) :  "";

				if(fromState.equals("SBH")) {
					if(toState.equals("SBH")) { // Within Sabah
						pid = 1;
					} else { //Sarawak & W'Msia
						pid = 2;
					}
				} else {
					pid = 2;
				}
				
			}

			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.* FROM pricing_normal_document a WHERE a.pid = " + pid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	first = Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first"));
		    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice"));
		    	addition = Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition"));
		    	additionPrice = Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice"));
		    }
		    
		    if(weight <= first) { //沒有超過 first weight
		    	
		    	amount = firstPrice;
		    	
		    } else if(weight > first) { //超過 first weight
		    	
		    	double subtotal1 = firstPrice;
		    	double subtotal2 = 0.0;
		    	double additionWeight = weight - first;
		    	subtotal2 = (additionWeight / addition) * additionPrice;
		    	
		    	amount = subtotal1 + subtotal2;
		    }

		    if(discountType ==0){ // RM
		    	disAmount = amount - Double.parseDouble( Integer.toString(discount) );
		    }else if(discountType == 1){ // percentage
		    	disAmount = amount - ( (amount/100.0) * Double.parseDouble( Integer.toString(discount) ) );
		    	// amount = amount - ( (amount/100.0) * discount );
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

		return disAmount + "|" + amount + "|" + amount;
	}
	
	

	
/************************ Credit Term User (Parcel) **************************/

	/**
	 * 管理員查看Pricing_credit_parcel清單
	 * 
	 * @return
	 */
	public static ArrayList<PricingDataModel> pricingList_credit_parcel() {

		String sqlCondition = "";
		
		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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
			
			sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.pid) FROM pricing_credit_parcel b " + sqlCondition + ") AS total" +
		    		" FROM pricing_credit_parcel a " + sqlCondition +
		    		" ORDER BY a.pid ASC";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setFromArea(Integer.parseInt(rs.getString("fromArea") == null ? "0" : rs.getString("fromArea")));
		    	obj.setToArea(Integer.parseInt(rs.getString("toArea") == null ? "0" : rs.getString("toArea")));
		    	obj.setWeightEach(Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAdditionCharge(Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge")));
		    	obj.setHandling(Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling")));
		    	obj.setFuel(Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel")));
		    	obj.setDiscountFirstPriceA(Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA")));
		    	obj.setDiscountAdditionPriceA(Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA")));
		    	obj.setDiscountFirstPriceB(Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB")));
		    	obj.setDiscountAdditionPriceB(Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB")));
		    	obj.setDiscountFirstPriceC(Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC")));
		    	obj.setDiscountAdditionPriceC(Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC")));
		    	obj.setDiscountFirstPriceD(Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD")));
		    	obj.setDiscountAdditionPriceD(Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
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
	 * @param pid
	 * @return
	 */
	protected static ArrayList<PricingDataModel> pricingDetails_credit_parcel(String pid) {

		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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

			sql = "SELECT a.* FROM pricing_credit_parcel a WHERE a.pid = " + pid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setFromArea(Integer.parseInt(rs.getString("fromArea") == null ? "0" : rs.getString("fromArea")));
		    	obj.setToArea(Integer.parseInt(rs.getString("toArea") == null ? "0" : rs.getString("toArea")));
		    	obj.setWeightEach(Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAdditionCharge(Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge")));
		    	obj.setHandling(Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling")));
		    	obj.setFuel(Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel")));
		    	obj.setDiscountFirstPriceA(Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA")));
		    	obj.setDiscountAdditionPriceA(Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA")));
		    	obj.setDiscountFirstPriceB(Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB")));
		    	obj.setDiscountAdditionPriceB(Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB")));
		    	obj.setDiscountFirstPriceC(Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC")));
		    	obj.setDiscountAdditionPriceC(Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC")));
		    	obj.setDiscountFirstPriceD(Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD")));
		    	obj.setDiscountAdditionPriceD(Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD")));
		    	obj.setIsShow(Integer.parseInt(rs.getString("isShow") == null ? "0" : rs.getString("isShow")));
		    	obj.setCreator(rs.getString("creator") == null ? "" : rs.getString("creator"));
		    	obj.setCreateDT(rs.getString("createDT") == null ? "" : rs.getString("createDT"));
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
	 * 新增 pricing_credit
	 * @param data
	 * @param userId
	 * @return
	 */
	protected static String addNewPricing_credit_parcel(ArrayList<PricingDataModel> data, String userId) {

		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return null;
		        }
	
				pstmt = conn.prepareStatement("INSERT INTO pricing_credit_parcel(fromArea, toArea, weightEach, firstPrice, additionCharge, handling, fuel," +
						" discountFirstPriceA, discountAdditionPriceA, discountFirstPriceB, discountAdditionPriceB," +
						" discountFirstPriceC, discountAdditionPriceC, discountFirstPriceD, discountAdditionPriceD," +
						" isShow, createDT, creator) " +
						" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setInt(1, pricingData.getFromArea());
				pstmt.setInt(2, pricingData.getToArea());
				pstmt.setDouble(3, pricingData.getWeightEach());
				pstmt.setDouble(4, pricingData.getFirstPrice());
				pstmt.setDouble(5, pricingData.getAdditionCharge());
				pstmt.setInt(6, pricingData.getHandling());
				pstmt.setInt(7, pricingData.getFuel());
				pstmt.setDouble(8, pricingData.getDiscountFirstPriceA());
				pstmt.setDouble(9, pricingData.getDiscountAdditionPriceA());
				pstmt.setDouble(10, pricingData.getDiscountFirstPriceB());
				pstmt.setDouble(11, pricingData.getDiscountAdditionPriceB());
				pstmt.setDouble(12, pricingData.getDiscountFirstPriceC());
				pstmt.setDouble(13, pricingData.getDiscountAdditionPriceC());
				pstmt.setDouble(14, pricingData.getDiscountFirstPriceD());
				pstmt.setDouble(15, pricingData.getDiscountAdditionPriceD());
				pstmt.setInt(16, pricingData.getIsShow());
				pstmt.setString(17, userId);	
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
	 * @param userId
	 * @return
	 */
	protected static String updatePricing_credit_parcel(ArrayList<PricingDataModel> data, String userId) {

		String result = "";
		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE pricing_credit_parcel SET fromArea="+pricingData.getFromArea()+", toArea="+pricingData.getToArea() +"," +
						" weightEach="+pricingData.getWeightEach()+", firstPrice="+pricingData.getFirstPrice()+", additionCharge="+pricingData.getAdditionCharge()+"," +
						" handling="+pricingData.getHandling()+", fuel="+pricingData.getFuel()+"," +
						" discountFirstPriceA="+pricingData.getDiscountFirstPriceA()+", discountAdditionPriceA="+pricingData.getDiscountAdditionPriceA()+"," +
						" discountFirstPriceB="+pricingData.getDiscountFirstPriceB()+", discountAdditionPriceB="+pricingData.getDiscountAdditionPriceB()+"," +
						" discountFirstPriceC="+pricingData.getDiscountFirstPriceC()+", discountAdditionPriceC="+pricingData.getDiscountAdditionPriceC()+"," +
						" discountFirstPriceD="+pricingData.getDiscountFirstPriceD()+", discountAdditionPriceD="+pricingData.getDiscountAdditionPriceD()+"," +
						" isShow="+pricingData.getIsShow()+", modifyDT=NOW(), modifier='"+userId+"' WHERE pid=" +pricingData.getPid();
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
	 * 計算 credit term user 總額
	 * 
	 * @param senderArea
	 * @param receiverArea
	 * @param weight
	 * @param quantity
	 * @param discPack
	 * @return
	 */
	public static String calculate_credit_parcel (String senderArea, String receiverArea, double weight, int discount, int discountType, int quantity, String discPack) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int fromArea = 0;
		int toArea = 0;
		int fromAreaBelongArea = 0;
		int toAreaBelongArea = 0;
		boolean sameArea = false;

		double disAmount = 0;
		double amount = 0;
		double originalAmount = 0;
		
		//major town
		int pid = 0;
		double weightEach = 0.0;
		double firstPrice = 0.0;
		double additionCharge = 0.0;
		int handling = 0;
		int fuel = 0;
		double discountFirstPriceA = 0.0;
		double discountAdditionPriceA = 0.0;
		double discountFirstPriceB = 0.0;
		double discountAdditionPriceB = 0.0;
		double discountFirstPriceC = 0.0;
		double discountAdditionPriceC = 0.0;
		double discountFirstPriceD = 0.0;
		double discountAdditionPriceD = 0.0;
		double subtotal = 0.0;
		
		//minor town 1
		double weightEach_1m = 0.0;
		double firstPrice_1m = 0.0;
		double additionCharge_1m = 0.0;
		int handling_1m = 0;
		int fuel_1m = 0;
		double discountFirstPriceA_1m = 0.0;
		double discountAdditionPriceA_1m = 0.0;
		double discountFirstPriceB_1m = 0.0;
		double discountAdditionPriceB_1m = 0.0;
		double discountFirstPriceC_1m = 0.0;
		double discountAdditionPriceC_1m = 0.0;
		double discountFirstPriceD_1m = 0.0;
		double discountAdditionPriceD_1m = 0.0;
		double subtotal_1m = 0.0;
		
		//minor town 2
		double weightEach_2m = 0.0;
		double firstPrice_2m = 0.0;
		double additionCharge_2m = 0.0;
		int handling_2m = 0;
		int fuel_2m = 0;		
		double discountFirstPriceA_2m = 0.0;
		double discountAdditionPriceA_2m = 0.0;
		double discountFirstPriceB_2m = 0.0;
		double discountAdditionPriceB_2m = 0.0;
		double discountFirstPriceC_2m = 0.0;
		double discountAdditionPriceC_2m = 0.0;
		double discountFirstPriceD_2m = 0.0;
		double discountAdditionPriceD_2m = 0.0;
		double subtotal_2m = 0.0;

		
		boolean isFromAreaMajor = false;
		boolean isToAreaMajor = false;
		
		try {
			
			if( (senderArea.length()>1)&&(receiverArea.length()>1) ) {
				
				fromArea = Integer.parseInt(senderArea.substring(0, 6));
				fromAreaBelongArea = Integer.parseInt(senderArea.substring(7, 13));
				toArea = Integer.parseInt(receiverArea.substring(0, 6));
				toAreaBelongArea = Integer.parseInt(receiverArea.substring(7, 13));
				
				if(senderArea.substring(6, 7).equals("A")) { //第7碼為 A
					isFromAreaMajor = true;
				}
				
				if(receiverArea.substring(6, 7).equals("A")) { //第7碼為 A
					isToAreaMajor = true;
				}

				
				if(senderArea.substring(6, 7).equals("B")) { //第7碼為 B
					if(fromAreaBelongArea == toArea) { //minor town 往屬於自己的 major town 移動
						sameArea = true;
					}
				}
				
				if(receiverArea.substring(6, 7).equals("B")) { //第7碼為 B
					if(toAreaBelongArea == fromArea) { //major town 往屬於自己的 minor town 移動
						sameArea = true;
					}
				}
				
				
			}
			
			/************************ 如果兩個都是 major town，或者是 same area ************************/
			if( (isFromAreaMajor && isToAreaMajor)||(sameArea)) {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+fromArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+fromArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    //先計算實際價格
			    subtotal = calculateTotal_credit(weight, quantity, weightEach, firstPrice, additionCharge, handling, fuel, discountFirstPriceA, discountAdditionPriceA, discountFirstPriceB, discountAdditionPriceB, discountFirstPriceC, discountAdditionPriceC, discountFirstPriceD, discountAdditionPriceD, discPack);
			    amount = subtotal;
			} 
			
			/************************ 其中一個是 major town ************************/
			else if (isFromAreaMajor && !isToAreaMajor) { //toArea 是 Minor town，所以要找出其所屬的 Major town
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+fromArea+" AND a.toArea = "+toAreaBelongArea+") OR (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+fromArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    
			    //Major town < > Minor town
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+toAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_1m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice_1m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge_1m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_1m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_1m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA_1m = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA_1m = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB_1m = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB_1m = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC_1m = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC_1m = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD_1m = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD_1m = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    //計算實際價格
			    subtotal = calculateTotal_credit(weight, quantity, weightEach, firstPrice, additionCharge, handling, fuel, discountFirstPriceA, discountAdditionPriceA, discountFirstPriceB, discountAdditionPriceB, discountFirstPriceC, discountAdditionPriceC, discountFirstPriceD, discountAdditionPriceD, discPack);
			    subtotal_1m = calculateTotal_credit(weight, quantity, weightEach_1m, firstPrice_1m, additionCharge_1m, handling_1m, fuel_1m, discountFirstPriceA_1m, discountAdditionPriceA_1m, discountFirstPriceB_1m, discountAdditionPriceB_1m, discountFirstPriceC_1m, discountAdditionPriceC_1m, discountFirstPriceD_1m, discountAdditionPriceD_1m, discPack);
			    amount = subtotal + subtotal_1m;
			    
			} 
			
			/************************ 其中一個是 major town ************************/
			else if(!isFromAreaMajor && isToAreaMajor) { //fromArea 是 Minor town，所以要找出其所屬的 Major town
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    
			    //Major town < > Minor town
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+fromArea+") OR (a.fromArea = "+fromArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_1m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice_1m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge_1m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_1m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_1m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA_1m = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA_1m = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB_1m = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB_1m = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC_1m = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC_1m = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD_1m = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD_1m = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    //計算實際價格
			    subtotal = calculateTotal_credit(weight, quantity, weightEach, firstPrice, additionCharge, handling, fuel, discountFirstPriceA, discountAdditionPriceA, discountFirstPriceB, discountAdditionPriceB, discountFirstPriceC, discountAdditionPriceC, discountFirstPriceD, discountAdditionPriceD, discPack);
			    subtotal_1m = calculateTotal_credit(weight, quantity, weightEach_1m, firstPrice_1m, additionCharge_1m, handling_1m, fuel_1m, discountFirstPriceA_1m, discountAdditionPriceA_1m, discountFirstPriceB_1m, discountAdditionPriceB_1m, discountFirstPriceC_1m, discountAdditionPriceC_1m, discountFirstPriceD_1m, discountAdditionPriceD_1m, discPack);
			    amount = subtotal + subtotal_1m;
			    
			} 
			
			/************************ 兩個都不是 major town ************************/
			else {
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "No Connection";
		        }
		
				stmt = conn.createStatement();
				
				//Major town < > Major town
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+toAreaBelongArea+") OR (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    
			    //Major town < > Minor town 1
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+fromAreaBelongArea+" AND a.toArea = "+fromArea+") OR (a.fromArea = "+fromArea+" AND a.toArea = "+fromAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_1m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice_1m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge_1m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_1m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_1m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA_1m = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA_1m = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB_1m = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB_1m = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC_1m = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC_1m = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD_1m = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD_1m = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    
			    //Major town < > Minor town 2
				sql = "SELECT a.* FROM pricing_credit_parcel a WHERE (a.fromArea = "+toAreaBelongArea+" AND a.toArea = "+toArea+") OR (a.fromArea = "+toArea+" AND a.toArea = "+toAreaBelongArea+") AND a.isShow = 1 LIMIT 1";
			    
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	weightEach_2m = Double.parseDouble(rs.getString("weightEach") == null ? "0" : rs.getString("weightEach").toString());
			    	firstPrice_2m = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice").toString());
			    	additionCharge_2m = Double.parseDouble(rs.getString("additionCharge") == null ? "0" : rs.getString("additionCharge").toString());
			    	handling_2m = Integer.parseInt(rs.getString("handling") == null ? "0" : rs.getString("handling").toString());
			    	fuel_2m = Integer.parseInt(rs.getString("fuel") == null ? "0" : rs.getString("fuel").toString());
			    	discountFirstPriceA_2m = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
			    	discountAdditionPriceA_2m = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
			    	discountFirstPriceB_2m = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
			    	discountAdditionPriceB_2m = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
			    	discountFirstPriceC_2m = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
			    	discountAdditionPriceC_2m = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
			    	discountFirstPriceD_2m = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
			    	discountAdditionPriceD_2m = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
			    }
			    
			    
			    //計算實際價格
			    subtotal = calculateTotal_credit(weight, quantity, weightEach, firstPrice, additionCharge, handling, fuel, discountFirstPriceA, discountAdditionPriceA, discountFirstPriceB, discountAdditionPriceB, discountFirstPriceC, discountAdditionPriceC, discountFirstPriceD, discountAdditionPriceD, discPack);
			    subtotal_1m = calculateTotal_credit(weight, quantity, weightEach_1m, firstPrice_1m, additionCharge_1m, handling_1m, fuel_1m, discountFirstPriceA_1m, discountAdditionPriceA_1m, discountFirstPriceB_1m, discountAdditionPriceB_1m, discountFirstPriceC_1m, discountAdditionPriceC_1m, discountFirstPriceD_1m, discountAdditionPriceD_1m, discPack);
			    subtotal_2m = calculateTotal_credit(weight, quantity, weightEach_2m, firstPrice_2m, additionCharge_2m, handling_2m, fuel_2m, discountFirstPriceA_2m, discountAdditionPriceA_2m, discountFirstPriceB_2m, discountAdditionPriceB_2m, discountFirstPriceC_2m, discountAdditionPriceC_2m, discountFirstPriceD_2m, discountAdditionPriceD_2m, discPack);
			    amount = subtotal + subtotal_1m + subtotal_2m;
	    
			}


		    if(discountType ==0){ // RM
		    	disAmount = amount - Double.parseDouble( Integer.toString(discount) );
		    }else if(discountType == 1){ // percentage
		    	disAmount = amount - ( (amount/100.0) * Double.parseDouble( Integer.toString(discount) ) );
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
	
		return disAmount + "|" + amount + "|" + amount;

		    
	}
	

	/**
	 * 逐項計算（實價）
	 * 
	 * @param weight
	 * @param quantity
	 * @param weightEach
	 * @param firstPrice
	 * @param additionCharge
	 * @param handling
	 * @param fuel
	 * @param discountFirstPriceA
	 * @param discountAdditionPriceA
	 * @param discountFirstPriceB
	 * @param discountAdditionPriceB
	 * @param discountFirstPriceC
	 * @param discountAdditionPriceC
	 * @param discountFirstPriceD
	 * @param discountAdditionPriceD
	 * @return
	 */
	private static double calculateTotal_credit(double weight, int quantity, double weightEach, double firstPrice, double additionCharge, int handling, int fuel, 
			double discountFirstPriceA, double discountAdditionPriceA, double discountFirstPriceB, double discountAdditionPriceB, double discountFirstPriceC, double discountAdditionPriceC, double discountFirstPriceD, double discountAdditionPriceD, String discPack) {

		double discountFirst = 0.0;
		double discountAddition = 0.0;
		double amount = 0.0;
		double price1 = 0.0;
		double price2 = 0.0;
		double handlingCharge = 0.0;
		double fuelCharge = 0.0;
		
		if (discPack.equals("A")) {
	    	discountFirst = discountFirstPriceA;
	    	discountAddition = discountAdditionPriceA;
	    } else if (discPack.equals("B")) {
	    	discountFirst = discountFirstPriceB;
	    	discountAddition = discountAdditionPriceB;
	    } else if (discPack.equals("C")) {
	    	discountFirst = discountFirstPriceC;
	    	discountAddition = discountAdditionPriceC;
	    } else if (discPack.equals("D")) {
	    	discountFirst = discountFirstPriceD;
	    	discountAddition = discountAdditionPriceD;
	    }
		
		if(weight <= (weightEach * quantity)) { //如果沒有超過基礎的公斤數
	    	
			//計算金額
	    	amount = quantity * (firstPrice - discountFirst);

	    	
	    	//計算 handling charge 及 fuel surcharge
			if(handling > 0) {
				handlingCharge = (handling/100.0) * amount; //務必要加上 .0，否則返回的是整數
			}
			if(fuel > 0) {
				fuelCharge = (fuel/100.0) * amount; //務必要加上 .0，否則返回的是整數
			}
			
			//加總
			amount = amount + handlingCharge + fuelCharge;
	    	
	    } else {
	    	
	    	//計算第一部分的價格（件數）
	    	price1 = quantity * (firstPrice - discountFirst);
	    	
	    	//計算第二部分的價格（超出的重量）
		    double additionWeight = weight - (weightEach * quantity);
			if(additionWeight > 0) {
				price2 = additionWeight * (additionCharge - discountAddition);
			}
			
			//計算 handling charge 及 fuel surcharge
			double subtotal = price1 + price2;
			
			if(handling > 0) {
				handlingCharge = (handling/100.0) * subtotal; //務必要加上 .0，否則返回的是整數
			}
			if(fuel > 0) {
				fuelCharge = (fuel/100.0) * subtotal; //務必要加上 .0，否則返回的是整數
			}
			
			//加總
			amount = subtotal + handlingCharge + fuelCharge;
			
	    }

		return amount;
		
	}
	

	
/************************ Credit Term User (Document) **************************/
	
	
	/**
	 * 管理員查看Pricing_credit_document清單
	 * 
	 * @return
	 */
	public static ArrayList<PricingDataModel> pricingList_credit_document() {

		String sqlCondition = "";
		
		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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
			
			sql = "SELECT a.*," +
		    		" (SELECT COUNT(b.pid) FROM pricing_credit_document b " + sqlCondition + ") AS total" +
		    		" FROM pricing_credit_document a " + sqlCondition +
		    		" ORDER BY a.pid ASC";
		    
		    rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
		    	obj.setDiscountFirstPriceA(Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA")));
		    	obj.setDiscountAdditionPriceA(Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA")));
		    	obj.setDiscountFirstPriceB(Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB")));
		    	obj.setDiscountAdditionPriceB(Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB")));
		    	obj.setDiscountFirstPriceC(Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC")));
		    	obj.setDiscountAdditionPriceC(Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC")));
		    	obj.setDiscountFirstPriceD(Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD")));
		    	obj.setDiscountAdditionPriceD(Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD")));
		    	obj.setModifier(rs.getString("modifier") == null ? "" : rs.getString("modifier"));
		    	obj.setModifyDT(rs.getString("modifyDT") == null ? "" : rs.getString("modifyDT"));
		    	obj.setTotal(Integer.parseInt(rs.getString("total") == null ? "0" : rs.getString("total")));
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
	 * @param pid
	 * @return
	 */
	protected static ArrayList<PricingDataModel> pricingDetails_credit_document(String pid) {

		ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
		PricingDataModel obj = null;
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

			sql = "SELECT a.* FROM pricing_credit_document a WHERE a.pid = " + pid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	obj = new PricingDataModel();
		    	obj.setPid(Integer.parseInt(rs.getString("pid") == null ? "0" : rs.getString("pid")));
		    	obj.setTitle_enUS(rs.getString("title_enUS") == null ? "" : rs.getString("title_enUS"));
		    	obj.setFirst(Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first")));
		    	obj.setFirstPrice(Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice")));
		    	obj.setAddition(Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition")));
		    	obj.setAdditionPrice(Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice")));
		    	obj.setDiscountFirstPriceA(Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA")));
		    	obj.setDiscountAdditionPriceA(Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA")));
		    	obj.setDiscountFirstPriceB(Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB")));
		    	obj.setDiscountAdditionPriceB(Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB")));
		    	obj.setDiscountFirstPriceC(Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC")));
		    	obj.setDiscountAdditionPriceC(Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC")));
		    	obj.setDiscountFirstPriceD(Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD")));
		    	obj.setDiscountAdditionPriceD(Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD")));
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
	 * 更新內容
	 * @param data
	 * @param userId
	 * @return
	 */
	protected static String updatePricing_credit_document(ArrayList<PricingDataModel> data, String userId) {

		String result = "";
		
		PricingDataModel pricingData = new PricingDataModel();
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		if(data != null && data.size() > 0){ //如果有資料
			pricingData = (PricingDataModel)data.get(0);

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
	
				stmt = conn.createStatement();
				sql = "UPDATE pricing_credit_document SET first="+pricingData.getFirst()+", firstPrice="+pricingData.getFirstPrice()+"," +
						" addition="+pricingData.getAddition()+", additionPrice="+pricingData.getAdditionPrice()+"," +
						" discountFirstPriceA="+pricingData.getDiscountFirstPriceA()+", discountAdditionPriceA="+pricingData.getDiscountAdditionPriceA()+"," +
						" discountFirstPriceB="+pricingData.getDiscountFirstPriceB()+", discountAdditionPriceB="+pricingData.getDiscountAdditionPriceB()+"," +
						" discountFirstPriceC="+pricingData.getDiscountFirstPriceC()+", discountAdditionPriceC="+pricingData.getDiscountAdditionPriceC()+"," +
						" discountFirstPriceD="+pricingData.getDiscountFirstPriceD()+", discountAdditionPriceD="+pricingData.getDiscountAdditionPriceD()+"," +
						" modifyDT=NOW(), modifier='"+userId+"' WHERE pid=" +pricingData.getPid();
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
	 * 計算 document 價格
	 * @return
	 */
	public static String calculate_credit_document(String senderArea, String receiverArea, double weight, int discount, int discountType, String discPack) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		double first = 0.0;
		double firstPrice = 0.0;
		double addition = 0.0;
		double additionPrice = 0.0;
		double discountFirstPriceA = 0.0;
		double discountAdditionPriceA = 0.0;
		double discountFirstPriceB = 0.0;
		double discountAdditionPriceB = 0.0;
		double discountFirstPriceC = 0.0;
		double discountAdditionPriceC = 0.0;
		double discountFirstPriceD = 0.0;
		double discountAdditionPriceD = 0.0;
		double discountFirst = 0.0;
		double discountAddition = 0.0;
		String fromState = "";
		String toState = "";
		int pid = 0; //1: Within Sabah; 2: Sarawak & W'Msia
		double amount = 0.0;
		double disAmount = 0.0;

		try {
			
			
			if( (senderArea.length()>1)&&(receiverArea.length()>1) ) {
				
				fromState = senderArea.substring(13, 16);
				toState = receiverArea.substring(13, 16);
				
				if(fromState.equals("SBH")) {
					if(toState.equals("SBH")) { // Within Sabah
						pid = 1;
					} else { //Sarawak & W'Msia
						pid = 2;
					}
				} else {
					pid = 2;
				}
				
			}

			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.* FROM pricing_credit_document a WHERE a.pid = " + pid;
		    
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	first = Double.parseDouble(rs.getString("first") == null ? "0" : rs.getString("first"));
		    	firstPrice = Double.parseDouble(rs.getString("firstPrice") == null ? "0" : rs.getString("firstPrice"));
		    	addition = Double.parseDouble(rs.getString("addition") == null ? "0" : rs.getString("addition"));
		    	additionPrice = Double.parseDouble(rs.getString("additionPrice") == null ? "0" : rs.getString("additionPrice"));
		    	discountFirstPriceA = Double.parseDouble(rs.getString("discountFirstPriceA") == null ? "0" : rs.getString("discountFirstPriceA"));
		    	discountAdditionPriceA = Double.parseDouble(rs.getString("discountAdditionPriceA") == null ? "0" : rs.getString("discountAdditionPriceA"));
		    	discountFirstPriceB = Double.parseDouble(rs.getString("discountFirstPriceB") == null ? "0" : rs.getString("discountFirstPriceB"));
		    	discountAdditionPriceB = Double.parseDouble(rs.getString("discountAdditionPriceB") == null ? "0" : rs.getString("discountAdditionPriceB"));
		    	discountFirstPriceC = Double.parseDouble(rs.getString("discountFirstPriceC") == null ? "0" : rs.getString("discountFirstPriceC"));
		    	discountAdditionPriceC = Double.parseDouble(rs.getString("discountAdditionPriceC") == null ? "0" : rs.getString("discountAdditionPriceC"));
		    	discountFirstPriceD = Double.parseDouble(rs.getString("discountFirstPriceD") == null ? "0" : rs.getString("discountFirstPriceD"));
		    	discountAdditionPriceD = Double.parseDouble(rs.getString("discountAdditionPriceD") == null ? "0" : rs.getString("discountAdditionPriceD"));
		    }
		    
		    if (discPack.equals("A")) {
		    	discountFirst = discountFirstPriceA;
		    	discountAddition = discountAdditionPriceA;
		    } else if (discPack.equals("B")) {
		    	discountFirst = discountFirstPriceB;
		    	discountAddition = discountAdditionPriceB;
		    } else if (discPack.equals("C")) {
		    	discountFirst = discountFirstPriceC;
		    	discountAddition = discountAdditionPriceC;
		    } else if (discPack.equals("D")) {
		    	discountFirst = discountFirstPriceD;
		    	discountAddition = discountAdditionPriceD;
		    }


		    
		    if(weight <= first) { //沒有超過 first weight
		    	
		    	amount = firstPrice - discountFirst;
		    	
		    } else if(weight > first) { //超過 first weight
		    	
		    	double subtotal1 = firstPrice - discountFirst;
		    	double subtotal2 = 0.0;
		    	double additionWeight = weight - first;
		    	subtotal2 = (additionWeight / addition) * (additionPrice - discountAddition);
		    	
		    	amount = subtotal1 + subtotal2;
		    }
		    
		    
		    if(discountType ==0){ // RM
		    	disAmount = amount - Double.parseDouble( Integer.toString(discount) );
		    }else if(discountType == 1){ // percentage
		    	disAmount = amount - ( (amount/100.0) * Double.parseDouble( Integer.toString(discount) ) );
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

		return disAmount + "|" + amount + "|" + amount;
	}
	
	
	
	
}