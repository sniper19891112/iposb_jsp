package com.iposb.consignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.iposb.code.CodeDataModel;
import com.iposb.i18n.Resource;
import com.iposb.log.LogBusinessModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.notification.NotificationBusinessModel;
import com.iposb.pricing.PricingBusinessModel;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.Mailer;
import com.iposb.utils.UtilsBusinessModel;


public class ConsignmentBusinessModel {
	
	static Logger logger = Logger.getLogger(ConsignmentBusinessModel.class);
	static Logger activityLogger = Logger.getLogger("activity");
	static DecimalFormat FORMAT = new DecimalFormat("#,###,###0.00");
	
	private ConsignmentBusinessModel(){
		
	}
		
	
	
	protected static ArrayList<ConsignmentDataModel> setRequestData(HttpServletRequest request) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;

		try {
			
	    	obj = new ConsignmentDataModel();
	    	obj.setCid(Integer.parseInt(request.getParameter("cid") == null ? "0" : request.getParameter("cid")));
	    	obj.setConsignmentNo(request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo"));
	    	obj.setSerial(Integer.parseInt(request.getParameter("serial") == null ? "0" : request.getParameter("serial")));
	    	obj.setGeneralCargoNo(request.getParameter("generalCargoNo") == null ? "" : request.getParameter("generalCargoNo"));
	    	obj.setUserId(request.getParameter("userId") == null ? "" : request.getParameter("userId"));
	    	obj.setPartnerId(Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId")));
	    	obj.setAgentId(Integer.parseInt(request.getParameter("agentId") == null ? "0" : request.getParameter("agentId")));
	    	obj.setSenderName(request.getParameter("senderName") == null ? "" : request.getParameter("senderName"));
	    	obj.setSenderAddress1(request.getParameter("senderAddress1") == null ? "" : request.getParameter("senderAddress1"));
	    	obj.setSenderAddress2(request.getParameter("senderAddress2") == null ? "" : request.getParameter("senderAddress2"));
	    	obj.setSenderAddress3(request.getParameter("senderAddress3") == null ? "" : request.getParameter("senderAddress3"));
	    	obj.setSenderPostcode(request.getParameter("senderPostcode") == null ? "" : request.getParameter("senderPostcode"));
	    	obj.setSenderZone(Integer.parseInt(request.getParameter("senderZone") == null ? "0" : request.getParameter("senderZone")));
	    	obj.setSenderPhone(request.getParameter("senderPhone") == null ? "" : request.getParameter("senderPhone"));
	    	obj.setSenderArea(Integer.parseInt(request.getParameter("senderArea") == null ? "0" : request.getParameter("senderArea")));
	    	obj.setSenderCountry(request.getParameter("senderCountry") == null ? "" : request.getParameter("senderCountry"));
	    	obj.setSenderIC(request.getParameter("senderIC") == null ? "" : request.getParameter("senderIC"));
	    	obj.setReceiverName(request.getParameter("receiverName") == null ? "" : request.getParameter("receiverName"));
	    	obj.setReceiverAttn(request.getParameter("receiverAttn") == null ? "" : request.getParameter("receiverAttn"));
	    	obj.setReceiverAddress1(request.getParameter("receiverAddress1") == null ? "" : request.getParameter("receiverAddress1"));
	    	obj.setReceiverAddress2(request.getParameter("receiverAddress2") == null ? "" : request.getParameter("receiverAddress2"));
	    	obj.setReceiverAddress3(request.getParameter("receiverAddress3") == null ? "" : request.getParameter("receiverAddress3"));
	    	obj.setReceiverPostcode(request.getParameter("receiverPostcode") == null ? "" : request.getParameter("receiverPostcode"));
	    	obj.setReceiverZone(Integer.parseInt(request.getParameter("receiverZone") == null ? "0" : request.getParameter("receiverZone")));
	    	obj.setReceiverPhone(request.getParameter("receiverPhone") == null ? "" : request.getParameter("receiverPhone"));
	    	obj.setReceiverArea(Integer.parseInt(request.getParameter("receiverArea") == null ? "0" : request.getParameter("receiverArea")));
	    	obj.setReceiverCountry(request.getParameter("receiverCountry") == null ? "" : request.getParameter("receiverCountry"));
	    	obj.setHelps(request.getParameter("helps") == null ? "" : request.getParameter("helps"));
	    	obj.setTickItem(request.getParameter("tickItem") == null ? "" : request.getParameter("tickItem"));
			obj.setShipmentMethod(Integer.parseInt(request.getParameter("shipmentMethod") == null ? "0" : request.getParameter("shipmentMethod")));
	    	obj.setShipmentType(Integer.parseInt(request.getParameter("shipmentType") == null ? "0" : request.getParameter("shipmentType")));
//	    	obj.setFreightType(Integer.parseInt(request.getParameter("freightType") == null ? "0" : request.getParameter("freightType")));
	    	obj.setPricing(Integer.parseInt(request.getParameter("pricing") == null ? "0" : request.getParameter("pricing")));
	    	obj.setQuantity(Integer.parseInt(request.getParameter("quantity") == null ? "0" : request.getParameter("quantity")));
	    	obj.setWeight(Double.parseDouble(request.getParameter("weight") == null ? "0" : request.getParameter("weight")));
	    	obj.setLength(Double.parseDouble(request.getParameter("length") == null ? "0" : request.getParameter("length")));
	    	obj.setWidth(Double.parseDouble(request.getParameter("width") == null ? "0" : request.getParameter("width")));
	    	obj.setHeight(Double.parseDouble(request.getParameter("height") == null ? "0" : request.getParameter("height")));
	    	obj.setAmount(Double.parseDouble(request.getParameter("amount") == null ? "0" : request.getParameter("amount")));
	    	obj.setDiscountReason(request.getParameter("discountReason") == null ? "" : request.getParameter("discountReason"));
	    	obj.setDeposit(Integer.parseInt(request.getParameter("deposit") == null ? "0" : request.getParameter("deposit")));
	    	obj.setPayMethod(Integer.parseInt(request.getParameter("payMethod") == null ? "0" : request.getParameter("payMethod")));
	    	obj.setPayStatus(Integer.parseInt(request.getParameter("payStatus") == null ? "0" : request.getParameter("payStatus")));
	    	obj.setTerms(request.getParameter("terms") == null ? "" : request.getParameter("terms"));
	    	obj.setUseLocale(request.getParameter("useLocale") == null ? "" : request.getParameter("useLocale"));
	    	obj.setVerify(request.getParameter("verify") == null ? "" : request.getParameter("verify"));
	    	data.add(obj);

		      
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}

		return data;
	}
	

	
	/**
	 * consignment管理 - 供管理員查詢
	 * @param pageNo - 頁
	 * @param status - 99: 不指定; 3: pending shipment
	 * @return
	 */
	protected static ArrayList<ConsignmentDataModel> consignmentList(int pageNo, int status, int aid) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		int page = 0;
		int numberPerPage = 5; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,10; 第二頁,則10,10;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			String sqlCondition = " WHERE status <> 1"; //不顯示已取消
			
//			if(status != 99) { //admin速查哪些訂單要處理
//				if(status == 0){
//					sqlCondition += " AND status = 0 OR status = 2 OR status = 3 OR status = 4 OR status = 6";
//				} else {
//					sqlCondition += " AND status =" + status;
//				}
//			}
			
			if(status == 3) {
				sqlCondition += " AND status = " + status;
			}
			if(aid > 0) sqlCondition += " AND (senderArea ="+aid+" OR receiverArea ="+aid+")";

			
			sql = "SELECT a.*," +
			
				" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
				" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
				" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
				" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
				" (SELECT b.privilege FROM memberlist b WHERE b.userId = a.userId) AS privilege," +
				" (SELECT b.accNo FROM memberlist b WHERE b.userId = a.userId) AS accNo," +
				" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.senderZone) AS senderZonename," +
				" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.receiverZone) AS receiverZonename," +
				
				" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.partnerId) AS abbreviationPartner," +
				" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.agentId) AS abbreviationAgent," +
				
				" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
				" FROM consignment a " + sqlCondition + " ORDER BY a.createDT DESC LIMIT " + page + ", " + numberPerPage;
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderZonename(rs.getString("senderZonename") == null ? "&nbsp;" : rs.getString("senderZonename").toString());
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverZonename(rs.getString("receiverZonename") == null ? "&nbsp;" : rs.getString("receiverZonename").toString());
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setFlightNum(rs.getString("flightNum").toString());
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum").toString());
		    	obj.setNatureGood(rs.getString("natureGood").toString());
		    	obj.setReceiptNum(rs.getString("receiptNum").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "-1" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege") == null ? "0" : rs.getString("privilege").toString()));
		    	obj.setAccNo(rs.getString("accNo") == null ? "" : rs.getString("accNo").toString());
		    	obj.setAbbreviationPartner(rs.getString("abbreviationPartner") == null ? "" : rs.getString("abbreviationPartner").toString());
		    	obj.setAbbreviationAgent(rs.getString("abbreviationAgent") == null ? "" : rs.getString("abbreviationAgent").toString());
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
	 * consignment管理 - 供agent / partner查詢
	 * @param partnerType
	 * @param partnerId
	 * @param pageNo - 頁
	 * @param status - 99: 不指定; 3: pending shipment
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> consignmentList4PartnerView (String partnerType, int partnerId, int pageNo, int status) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

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
			
			String sqlCondition = " WHERE agentId = "+partnerId+" AND status <> 1"; //不顯示已取消
			if(partnerType.equals("partner")) {
				sqlCondition = " WHERE partnerId = "+partnerId+" AND status <> 1";
			}
			
			if(status == 3) {
				sqlCondition += " AND status = " + status;
			}

			
			sql = "SELECT a.*," +
			
				" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
				" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
				" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
				" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
				" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.senderZone) AS senderZonename," +
				" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.receiverZone) AS receiverZonename," +
				
//				" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.partnerId) AS abbreviationPartner," +
//				" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.agentId) AS abbreviationAgent," +
				
				" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
				" FROM consignment a " + sqlCondition + " ORDER BY a.createDT DESC LIMIT " + page + ", " + numberPerPage;
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderZonename(rs.getString("senderZonename") == null ? "&nbsp;" : rs.getString("senderZonename").toString());
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverZonename(rs.getString("receiverZonename") == null ? "&nbsp;" : rs.getString("receiverZonename").toString());
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setFlightNum(rs.getString("flightNum").toString());
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum").toString());
		    	obj.setNatureGood(rs.getString("natureGood").toString());
		    	obj.setReceiptNum(rs.getString("receiptNum").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "-1" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname").toString());
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
	 * HQ/agent搜尋consignment
	 * 
	 * @param consignmentNo
	 * @param agentId
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> searchConsignment (String consignmentNo, int agentId) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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
			
			String sqlCondition = " WHERE (consignmentNo LIKE '%"+consignmentNo+"%' OR generalCargoNo LIKE '%"+consignmentNo+"%')";
			if(agentId > 0) {
				sqlCondition += " AND agentId = " + agentId;
			}

			
			sql = "SELECT a.*," +
			
			" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
			" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
			" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
			" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
			" (SELECT b.privilege FROM memberlist b WHERE b.userId = a.userId) AS privilege," +
			" (SELECT b.accNo FROM memberlist b WHERE b.userId = a.userId) AS accNo," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.senderZone) AS senderZonename," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.receiverZone) AS receiverZonename," +
			
			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.partnerId) AS abbreviationPartner," +
			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.agentId) AS abbreviationAgent," +
			
			//取得TAX INVOICE
//			" (SELECT c.iid FROM invoice c WHERE c.valid = 1 AND c.consignmentNo = a.consignmentNo) AS invoiceNo," +
			
			" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
			" FROM consignment a" + sqlCondition + " ORDER BY a.createDT DESC ";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderZonename(rs.getString("senderZonename") == null ? "&nbsp;" : rs.getString("senderZonename").toString());
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverZonename(rs.getString("receiverZonename") == null ? "&nbsp;" : rs.getString("receiverZonename").toString());
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setFlightNum(rs.getString("flightNum").toString());
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum").toString());
		    	obj.setNatureGood(rs.getString("natureGood").toString());
		    	obj.setReceiptNum(rs.getString("receiptNum").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "-1" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege") == null ? "0" : rs.getString("privilege").toString()));
		    	obj.setAccNo(rs.getString("accNo") == null ? "" : rs.getString("accNo").toString());
		    	obj.setAbbreviationPartner(rs.getString("abbreviationPartner") == null ? "" : rs.getString("abbreviationPartner").toString());
		    	obj.setAbbreviationAgent(rs.getString("abbreviationAgent") == null ? "" : rs.getString("abbreviationAgent").toString());
//		    	obj.setInvoiceNo(rs.getString("invoiceNo") == null ? "" : rs.getString("invoiceNo").toString());
		    	
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
	 * 管理員搜尋一個 partner 的 consignment
	 * 
	 * @param partnerType
	 * @param partnerId
	 * @param consignmentNo
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> partnerSearchConsignment (String partnerType, int partnerId, String consignmentNo) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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
			
			String sqlCondition = " WHERE (consignmentNo LIKE '%"+consignmentNo+"%' OR generalCargoNo LIKE '%"+consignmentNo+"%') AND agentId = "+partnerId;
			if(partnerType.equals("partner")) {
				sqlCondition = " WHERE (consignmentNo LIKE '%"+consignmentNo+"%' OR generalCargoNo LIKE '%"+consignmentNo+"%') AND partnerId = "+partnerId;
			}

			
			sql = "SELECT a.*," +
			
			" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
			" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
			" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
			" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
			" (SELECT b.privilege FROM memberlist b WHERE b.userId = a.userId) AS privilege," +
			" (SELECT b.accNo FROM memberlist b WHERE b.userId = a.userId) AS accNo," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.senderZone) AS senderZonename," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.receiverZone) AS receiverZonename," +
			
//			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.partnerId) AS abbreviationPartner," +
//			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.agentId) AS abbreviationAgent," +
			
			//取得TAX INVOICE
//			" (SELECT c.iid FROM invoice c WHERE c.valid = 1 AND c.consignmentNo = a.consignmentNo) AS invoiceNo," +
			
			" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
			" FROM consignment a" + sqlCondition + " ORDER BY a.createDT DESC ";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderZonename(rs.getString("senderZonename") == null ? "&nbsp;" : rs.getString("senderZonename").toString());
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverZonename(rs.getString("receiverZonename") == null ? "&nbsp;" : rs.getString("receiverZonename").toString());
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setFlightNum(rs.getString("flightNum").toString());
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum").toString());
		    	obj.setNatureGood(rs.getString("natureGood").toString());
		    	obj.setReceiptNum(rs.getString("receiptNum").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "-1" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege") == null ? "0" : rs.getString("privilege").toString()));
		    	obj.setAccNo(rs.getString("accNo") == null ? "" : rs.getString("accNo").toString());
//		    	obj.setAbbreviationPartner(rs.getString("abbreviationPartner") == null ? "" : rs.getString("abbreviationPartner").toString());
//		    	obj.setAbbreviationAgent(rs.getString("abbreviationAgent") == null ? "" : rs.getString("abbreviationAgent").toString());
//		    	obj.setInvoiceNo(rs.getString("invoiceNo") == null ? "" : rs.getString("invoiceNo").toString());
		    	
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
	 * 搜尋某 Partner 的 Consignment
	 * 
	 * @param partnerId
	 * @return
	 */
	protected static ArrayList<ConsignmentDataModel> searchPartnerConsignment (int partnerId, int pageNo) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		int page = 0;
		int numberPerPage = 5; //每頁數量
		page = (pageNo - 1) * numberPerPage; //eg: 第一頁,則0,10; 第二頁,則10,10;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }

			stmt = conn.createStatement();
			
			String sqlCondition = " WHERE partnerId = "+partnerId;

			
			sql = "SELECT a.*," +
			
			" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
			" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
			" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
			" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.senderZone) AS senderZonename," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.receiverZone) AS receiverZonename," +
			
			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.partnerId) AS abbreviationPartner," +
			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.agentId) AS abbreviationAgent," +
			
			//取得TAX INVOICE
			" (SELECT c.iid FROM invoice c WHERE c.valid = 1 AND c.consignmentNo = a.consignmentNo) AS invoiceNo," +
			
			" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
			" FROM consignment a" + sqlCondition + " ORDER BY a.createDT DESC  LIMIT " + page + ", " + numberPerPage;
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderZonename(rs.getString("senderZonename") == null ? "&nbsp;" : rs.getString("senderZonename").toString());
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverZonename(rs.getString("receiverZonename") == null ? "&nbsp;" : rs.getString("receiverZonename").toString());
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setFlightNum(rs.getString("flightNum").toString());
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum").toString());
		    	obj.setNatureGood(rs.getString("natureGood").toString());
		    	obj.setReceiptNum(rs.getString("receiptNum").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "-1" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname").toString());
		    	obj.setAbbreviationPartner(rs.getString("abbreviationPartner") == null ? "" : rs.getString("abbreviationPartner").toString());
		    	obj.setAbbreviationAgent(rs.getString("abbreviationAgent") == null ? "" : rs.getString("abbreviationAgent").toString());
		    	obj.setInvoiceNo(rs.getString("invoiceNo") == null ? "" : rs.getString("invoiceNo").toString());
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
	 * 管理員搜尋consignment
	 * 
	 * @param createDT
	 * @param station
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> searchConsignmentByDay (String createDT, int station) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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
			
			String sqlCondition = " WHERE SUBSTRING(createDT, 1, 10) = '"+createDT+"' AND receiverArea = " + station;

			
			sql = "SELECT a.*," +
			
			" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
			" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
			" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
			" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.senderZone) AS senderZonename," +
			" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.receiverZone) AS receiverZonename," +
			
			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.partnerId) AS abbreviationPartner," +
			" (SELECT e.cname FROM partnerlist e WHERE e.pid = a.agentId) AS abbreviationAgent," +
			
			//取得TAX INVOICE
//			" (SELECT c.iid FROM invoice c WHERE c.valid = 1 AND c.consignmentNo = a.consignmentNo) AS invoiceNo," +
			
			" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
			" FROM consignment a" + sqlCondition + " ORDER BY a.createDT DESC ";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderZonename(rs.getString("senderZonename") == null ? "&nbsp;" : rs.getString("senderZonename").toString());
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverZonename(rs.getString("receiverZonename") == null ? "&nbsp;" : rs.getString("receiverZonename").toString());
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setFlightNum(rs.getString("flightNum").toString());
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum").toString());
		    	obj.setNatureGood(rs.getString("natureGood").toString());
		    	obj.setReceiptNum(rs.getString("receiptNum").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "-1" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname").toString());
		    	obj.setAbbreviationPartner(rs.getString("abbreviationPartner") == null ? "" : rs.getString("abbreviationPartner").toString());
		    	obj.setAbbreviationAgent(rs.getString("abbreviationAgent") == null ? "" : rs.getString("abbreviationAgent").toString());
//		    	obj.setInvoiceNo(rs.getString("invoiceNo") == null ? "" : rs.getString("invoiceNo").toString());
		    	
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
	 * 解析 status
	 * @param status
	 * @param locale
	 * @return
	 */
	public static String parseStatus(int status, String locale){
		
		String text = "";
		
		if(status == 0){
			text = Resource.getString("ID_LABEL_STATUS0",locale);
		} else if(status == 1){
			text = Resource.getString("ID_LABEL_STATUS1",locale);
		} else if(status == 2){
			text = Resource.getString("ID_LABEL_STATUS2",locale);
		} else if(status == 3){
			text = Resource.getString("ID_LABEL_STATUS3",locale);
		} else if(status == 4){
			text = Resource.getString("ID_LABEL_STATUS4",locale);
		}  else if(status == 99){
			text = "";
		}
		
		return text;
	} 
	
	
	/**
	 * 解析 Pay Method
	 * 
	 * @param payMethod
	 * @param locale
	 * @return
	 */
	public static String parsePayMethod (int payMethod, String locale){
		
		String text = "";
		
		if(payMethod == 0){
			text = Resource.getString("ID_LABEL_UNKNOWN",locale);
		} else if(payMethod == 1){
			text = Resource.getString("ID_LABEL_PAYBYCASH",locale);
		} else if(payMethod == 2){
			text = Resource.getString("ID_LABEL_PAYBYONLINE",locale);
		} else if(payMethod == 3){
			text = Resource.getString("ID_LABEL_PAYBYBANKIN",locale);
		} else if(payMethod == 4){
			text = Resource.getString("ID_LABEL_PAYBYCREDIT",locale);
		} else if(payMethod == 5){
			text = Resource.getString("ID_LABEL_PAYBYCOD",locale);
		} else if(payMethod == 6){
			text = Resource.getString("ID_LABEL_FREIGHTCHARGES",locale);
		} else if(payMethod == 7){
			text = Resource.getString("ID_LABEL_COMPANYMAIL",locale);
		}
		
		return text;
	} 
	
	

	/**
	 * 解析 Pay Status
	 * @param payStatus
	 * @param payDT
	 * @param locale
	 * @return
	 */
	public static String parsePayStatus(int payStatus, String locale){
		
		String text = "";
		
		if(payStatus == 0){
			text = Resource.getString("ID_LABEL_NOTPAID", locale);
		} else if(payStatus == 1){
			text = Resource.getString("ID_LABEL_PAID50", locale);
		} else if(payStatus == 2){
			text = Resource.getString("ID_LABEL_PAID100", locale);
		} else if(payStatus == 3){
			text = Resource.getString("ID_LABEL_PAID1NIGHT", locale);
		} else if(payStatus == 4){
			text = Resource.getString("ID_LABEL_PAIDDEPOSIT", locale);
		} else if(payStatus == 9){
			text = Resource.getString("ID_LABEL_FREE", locale);
		}
		
		return text;
	} 
	
	
	/**
	 * 統計該user共有幾個訂單
	 * 
	 * @param userId
	 * @param type: member & partner
	 * @return
	 */
	public static int countTotalConsignment(String userId) {

		int total = 0;
		String sql = "";
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			
			if(userId.trim().length() > 0){
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return 0;
		        }
	
				stmt = conn.createStatement();    
				
				sql = " SELECT a.userId, COUNT(*) AS totalBooking" +
					" FROM consignment a WHERE a.userId = '" + userId + "' AND staffCreate = 0";
				
			    rs = stmt.executeQuery(sql);
	
			    while(rs.next()){
			    	total = Integer.parseInt(rs.getString("totalBooking") == null ? "0" : rs.getString("totalBooking"));
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
	 * 根據 consignmentNo 找出consignment的詳細資料 - 供詳細資料查詢/產生PDF
	 * @param bookingCode
	 * @param bookingType
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> getRecordByConsignmentNo (String consignmentNo) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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

			
			sql = "SELECT a.*," +
				" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
				" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
				" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
				" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
				" (SELECT b.privilege FROM memberlist b WHERE b.userId = a.userId) AS privilege," +
				" (SELECT b.accNo FROM memberlist b WHERE b.userId = a.userId) AS accNo," +
				" (SELECT d.serial FROM zone d WHERE d.zid = a.senderZone) AS senderZoneserial," +
				" (SELECT d.serial FROM zone d WHERE d.zid = a.receiverZone) AS receiverZoneserial," +
				
				//取得TAX INVOICE
				" (SELECT c.iid FROM invoice c WHERE c.valid = 1 AND c.consignmentNo = a.consignmentNo) AS invoiceNo" +
				
				" FROM consignment a WHERE a.consignmentNo = '"+consignmentNo+"'";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone") == null ? "0" : rs.getString("senderZone").toString()));
		    	obj.setSenderZoneserial(Integer.parseInt(rs.getString("senderZoneserial") == null ? "0" : rs.getString("senderZoneserial").toString()));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone") == null ? "0" : rs.getString("receiverZone").toString()));
		    	obj.setReceiverZoneserial(Integer.parseInt(rs.getString("receiverZoneserial") == null ? "0" : rs.getString("receiverZoneserial").toString()));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setFlightNum(rs.getString("flightNum").toString());
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum").toString());
		    	obj.setNatureGood(rs.getString("natureGood").toString());
		    	obj.setReceiptNum(rs.getString("receiptNum").toString());
		    	obj.setPromoCode(rs.getString("promoCode").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "0" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("cname") == null ? "" : rs.getString("cname").toString());
		    	obj.setPrivilege(Integer.parseInt(rs.getString("privilege") == null ? "0" : rs.getString("privilege").toString()));
		    	obj.setAccNo(rs.getString("accNo") == null ? "" : rs.getString("accNo").toString());
		    	//obj.setInvoiceNo(rs.getString("invoiceNo") == null ? "" : rs.getString("invoiceNo").toString());
		    	
		    	data.add(obj);
		    }
			
			
//			logger.info("-- sql:" + sql);
		      
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
	 * 根據 generalCargoNo 找出consignment的詳細資料 - 供詳細資料查詢/產生PDF
	 * @param bookingCode
	 * @param bookingType
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> getRecordByGeneralCargoNo (String generalCargoNo) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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

			
//			sql = "SELECT a.*," +
//				" (SELECT b.email FROM memberlist b WHERE b.userId = a.userId) AS email," +
//				" (SELECT b.gender FROM memberlist b WHERE b.userId = a.userId) AS gender," +
//				" (SELECT b.ename FROM memberlist b WHERE b.userId = a.userId) AS ename," +
//				" (SELECT b.cname FROM memberlist b WHERE b.userId = a.userId) AS cname," +
//				" (SELECT d.serial FROM zone d WHERE d.zid = a.senderZone) AS senderZoneserial," +
//				" (SELECT d.serial FROM zone d WHERE d.zid = a.receiverZone) AS receiverZoneserial," +
//				
//				//取得TAX INVOICE
//				" (SELECT c.iid FROM invoice c WHERE c.valid = 1 AND c.consignmentNo = a.consignmentNo) AS invoiceNo" +
//				
//				" FROM consignment a WHERE a.generalCargoNo = '"+generalCargoNo+"'";
			
			sql = "SELECT a.*, b.*, c.cname AS partnerShortName, " +
					" (SELECT d.serial FROM zone d WHERE d.zid = a.senderZone) AS senderZoneserial," +
					" (SELECT d.serial FROM zone d WHERE d.zid = a.receiverZone) AS receiverZoneserial," +
					
					//取得TAX INVOICE
					" (SELECT c.iid FROM invoice c WHERE c.valid = 1 AND c.consignmentNo = a.consignmentNo) AS invoiceNo" +
					
					" FROM consignment a" +
					" LEFT JOIN memberlist b ON b.userId = a.userId" +
					" LEFT JOIN partnerlist c ON c.pid = a.partnerId" +
					" WHERE a.generalCargoNo = '"+generalCargoNo+"'";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone") == null ? "0" : rs.getString("senderZone").toString()));
		    	obj.setSenderZoneserial(Integer.parseInt(rs.getString("senderZoneserial") == null ? "0" : rs.getString("senderZoneserial").toString()));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone") == null ? "0" : rs.getString("receiverZone").toString()));
		    	obj.setReceiverZoneserial(Integer.parseInt(rs.getString("receiverZoneserial") == null ? "0" : rs.getString("receiverZoneserial").toString()));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEmail(rs.getString("email") == null ? "" : rs.getString("email").toString());
		    	obj.setGender(Integer.parseInt(rs.getString("gender") == null ? "0" : rs.getString("gender").toString()));
		    	obj.setEname(rs.getString("ename") == null ? "" : rs.getString("ename").toString());
		    	obj.setCname(rs.getString("partnerShortName") == null ? "" : rs.getString("partnerShortName").toString());
		    	obj.setInvoiceNo(rs.getString("invoiceNo") == null ? "" : rs.getString("invoiceNo").toString());
		    	
		    	data.add(obj);
		    }
			
			
//			logger.info("-- sql:" + sql);
		      
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
	 * 找出剛上傳的資料
	 * 
	 * @param lastuploadedCN
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> lastuploaded(String lastuploadedCN) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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
			
			sql = "SELECT * FROM consignment WHERE generalCargoNo IN ("+lastuploadedCN+") ORDER BY cid ASC";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
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
	 * 處理 Stages
	 * 
	 * @param currentstage
	 * @param consignmentNo
	 * @param generalCargoNo
	 * @param userId
	 * @param stage 目前該 consignment 的 stage
	 * @return
	 */
	protected static String handleStage(int currentstage, String consignmentNo, String generalCargoNo, String userId, int stage, HttpServletRequest request) {

		String result = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		String sql = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			String cn = "";
			String gn = "";
			int serial = 0;
			boolean isGeneralCargo = false;
			
			if(consignmentNo.replaceAll("\\s","").length() > 9) { //consignmentNo + serial
				cn = consignmentNo.replaceAll("\\s","").substring(0, 9);
				serial = Integer.parseInt(consignmentNo.replaceAll("\\s","").substring(9,12));
			} else {
				cn = consignmentNo;
			}
			
			if(generalCargoNo.replaceAll("\\s","").length() > 0) {
				gn = generalCargoNo;
				isGeneralCargo = true;
			}
			
			if(currentstage == 1) { //Pickup
				
//				if(stage > 1) {
//					return "InvalidScan";
//				}
				
				pstmt = conn.prepareStatement("INSERT INTO log_stage1 (consignmentNo, serial, userId, createDT) VALUE " + "(?, ?, ?, NOW())");
				logger.info("--- Driver ("+userId+") scan " + (gn.equals("") ? cn : gn) + " @ Stage 1 ---");
				
			} else if(currentstage == 2) { //Station
				
//				if(stage > 2) {
//					return "InvalidScan";
//				}
				
				pstmt = conn.prepareStatement("INSERT INTO log_stage2 (consignmentNo, serial, userId, createDT) VALUE " + "(?, ?, ?, NOW())");
				logger.info("--- Station ("+userId+") scan " + (gn.equals("") ? cn +" (#"+serial+")" : gn) + " @ Stage 2 ---");
				
			} else if(currentstage == 3) { //Load
				
//				if( (stage > 3) || (stage < 2) ) { //已經下一步了 或 還沒經過 station
//					return "InvalidScan";
//				}
				
//				if(!isGeneralCargo) { //不是 general Cargo 的 CN 時才檢查
//					if(serial==0) { //不是scan每箱的barcode
//						return "PlsScanBarcodeWithSerialNumber";
//					}
//				}
				
					
				pstmt = conn.prepareStatement("INSERT INTO log_stage3 (consignmentNo, serial, userId, createDT) VALUE " + "(?, ?, ?, NOW())");
				logger.info("--- Driver ("+userId+") scan " + (gn.equals("") ? cn +" (#"+serial+")" : gn) + " @ Stage 3 ---");
				
			} else if(currentstage == 4) { //Exchange
				
//				if( (stage > 4) || (stage < 3) ) { //已經下一步了 或 還沒load
//					return "InvalidScan";
//				}
				
				if(!isGeneralCargo) { //不是 general Cargo 的 CN 時才檢查
					if(serial==0) { //不是scan每箱的barcode
						return "PlsScanBarcodeWithSerialNumber";
					}
				}
					
				pstmt = conn.prepareStatement("INSERT INTO log_stage4 (consignmentNo, serial, userId, createDT) VALUE " + "(?, ?, ?, NOW())");
				logger.info("--- Driver ("+userId+") scan " + (gn.equals("") ? cn +" (#"+serial+")" : gn) + " @ Stage 4 ---");
				
			} else if(currentstage == 5) { //Unload
				
//				if( (stage > 5) || (stage < 3) ) { //已經下一步了 或 還沒load或exchange
//					return "InvalidScan";
//				}
				
//				if(!isGeneralCargo) { //不是 general Cargo 的 CN 時才檢查
//					if(serial==0) { //不是scan每箱的barcode
//						return "PlsScanBarcodeWithSerialNumber";
//					}
//				}
					
				pstmt = conn.prepareStatement("INSERT INTO log_stage5 (consignmentNo, serial, userId, createDT) VALUE " + "(?, ?, ?, NOW())");
				logger.info("--- Driver ("+userId+") scan " + (gn.equals("") ? cn +" (#"+serial+")" : gn) + " @ Stage 5 ---");
				
			} else if(currentstage == 6) { //Distribute
				
//				if( (stage > 6) || (stage < 5) ) { //已經下一步了 或 還沒unload
//					return "InvalidScan";
//				}
					
				pstmt = conn.prepareStatement("INSERT INTO log_stage6 (consignmentNo, serial, userId, createDT) VALUE " + "(?, ?, ?, NOW())");
				logger.info("--- Driver ("+userId+") scan " + (gn.equals("") ? cn : gn) + " @ Stage 6 ---");
				
			} else if(currentstage == 7) { //Deliver
				
//				if( (stage > 7) || (stage < 6) ) { //已經 closed 了 或 還沒 distribute
//					return "InvalidScan";
//				}
				
				pstmt = conn.prepareStatement("INSERT INTO log_stage7 (consignmentNo, serial, userId, createDT) VALUE " + "(?, ?, ?, NOW())");
				logger.info("--- Driver ("+userId+") scan " + (gn.equals("") ? cn : gn) + " @ Stage 7 ---");
			}

			pstmt.setString(1, cn);
			pstmt.setInt(2, serial);
			pstmt.setString(3, userId);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			
			stmt = conn.createStatement();
			
			
			//update stage & status
			
			if(currentstage == 1) { //Pickup
				sql = "UPDATE consignment SET stage = 1, status = 0, pickupDriver = '"+userId+"', pickupDT = NOW() WHERE consignmentNo = '" +cn+"'";
			} else if(currentstage == 2) { //Station
				sql = "UPDATE consignment SET stage = 2, status = 0 WHERE consignmentNo = '" +cn+"'";	
				
			} else if(currentstage == 3) { //Load
				sql = "UPDATE consignment SET stage = 3, status = 0, dispatchDT = '"+UtilsBusinessModel.todayDate()+"' WHERE consignmentNo = '" +cn+"'";
				
			} else if(currentstage == 4) { //Exchange
				sql = "UPDATE consignment SET stage = 4, status = 0 WHERE consignmentNo = '" +cn+"'";
				
			} else if(currentstage == 5) { //Unload
				sql = "UPDATE consignment SET stage = 5, status = 0 WHERE consignmentNo = '" +cn+"'"; //記錄配送日期
				
			} else if(currentstage == 6) { //Out of Delivery
				sql = "UPDATE consignment SET stage = 6, status = 0, deliveryDriver = '"+userId+"', distributeDT = '"+UtilsBusinessModel.todayDate()+"' WHERE consignmentNo = '" +cn+"'";
				
			} else if(currentstage == 7) { //Delivered
				sql = "UPDATE consignment SET stage = 7, status = 4, deliveryDriver = '"+userId+"', deliveryDT = NOW() WHERE consignmentNo = '" +cn+"'";
			}
			
//			logger.info(sql);
			stmt.executeUpdate(sql);
			
			
			
			//如果是 load，則記錄日期到 account 的 accDT
			if(currentstage == 3) {
				sql = "UPDATE account SET accDT = '"+UtilsBusinessModel.todayDate()+"' WHERE consignmentNo = '" +cn+"'";
				stmt.executeUpdate(sql);
			}
			
//			if(currentstage==7) { //Reached Receiver
//				sendParcelReachedEmail(consignmentNo, request);
//			}

			
			result = "OK";
			if(isGeneralCargo) {
				result = "OK"+consignmentNo;
			}
		
		}
		catch(Exception ex){
			result = "SystemError: "+ex;
			ex.printStackTrace();
			logger.error(ex.toString());
		}
		finally {
			if(pstmt != null){
				try{ pstmt.close(); } catch(Exception ex){}
				pstmt = null;
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
	 * App 更新 consignment 的尺寸和重量，順便算得價格
	 * 
	 * @param consignmentNo
	 * @param generalCargoNo
	 * @param userId
	 * @param senderArea
	 * @param receiverArea
	 * @param shipmentType
	 * @param weight
	 * @param quantity
	 * @param memberType
	 * @param accNo
	 * @param request
	 * @return
	 */
	protected static String appUpdateConsignment(String consignmentNo, String generalCargoNo, String userId, String senderArea, String receiverArea, int shipmentType, double weight, int quantity, String memberType, String accNo, HttpServletRequest request, ArrayList<CodeDataModel> cData) {
		
		String sql = "";
		String result = "";
		String amountString = "";
		double codeDiscountAmount = 0.0;
		double pricingAmount = 0.0;
		double originalAmount = 0.0;
		Statement stmt = null;
		Connection conn = null;
		CodeDataModel codeDetail = null;
		
		int discount = 0;
		int discountType = -1;
		
		if(cData.size() == 1){
			codeDetail = cData.get(0);
			if(codeDetail.getIsAllow() == 1){ // code is allowed for use
				int limit = codeDetail.getAllowTimes();
				int used = codeDetail.getUsed();
				String periodStart = codeDetail.getPeriodStart();
				String periodEnd = codeDetail.getPeriodEnd();
				
//				Calendar start = Calendar.getInstance();
//				Calendar end = Calendar.getInstance();
				
//				String startYear = periodStart.substring(0, periodStart.indexOf("-", 0));
//				String startMonth = periodStart.substring(periodStart.indexOf("-", 0)+1, periodStart.indexOf("-", 5));
//				String startDay = periodStart.substring(periodStart.indexOf("-", 5)+1, periodStart.length());
//				
//				String endYear = periodEnd.substring(0, periodEnd.indexOf("-", 0));
//				String endMonth = periodEnd.substring(periodEnd.indexOf("-", 0)+1, periodEnd.indexOf("-", 5));
//				String endDay = periodEnd.substring(periodEnd.indexOf("-", 5)+1, periodEnd.length());
//
//				start.set(Integer.parseInt(startYear), Integer.parseInt(startMonth), Integer.parseInt(startDay));
//				end.set(Integer.parseInt(endYear), Integer.parseInt(endMonth), Integer.parseInt(endDay));

//				Date dt_periodStart = start.getTime();
//				Date dt_periodEnd = end.getTime();
//				Date nowDate = new Date();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
				Date dt_periodStart = null, dt_periodEnd = null;
				
				try {
					dt_periodStart = sdf.parse(periodStart);
					dt_periodEnd = sdf.parse(periodEnd);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date nowDate = new Date();
				
						
				if(nowDate.after(dt_periodStart) && nowDate.before(dt_periodEnd) ){ // within usable date
					if(limit > used){ // within use limit
						discount = codeDetail.getDiscount();
						discountType = codeDetail.getDiscountType();
					}
				}
			}
			
		}
		
		try {
			
			if(memberType.equals("normal")) {
				
				if(shipmentType == 1) { //Air Freight
					amountString = PricingBusinessModel.calculate_normal_document(senderArea, receiverArea, weight, discount, discountType);
				} else if(shipmentType == 2) { //Land Transport
					amountString = PricingBusinessModel.calculate_normal_parcel(senderArea, receiverArea, weight, discount, discountType, quantity);
				}
				
			} else if(memberType.equals("credit")) {
				
				String discPack = "";
				
				if(accNo.trim().length() > 0) { //有 accNo
					discPack = LogonBusinessModel.checkDiscPack(accNo, "accNo");
//				} else if (userId.length() > 0) { //如果有登入
//					discPack = LogonBusinessModel.checkDiscPack(userId, "userId");
				}
				
				if(shipmentType == 1) { //Air Freight
					amountString = PricingBusinessModel.calculate_credit_document(senderArea, receiverArea, weight, discount, discountType, discPack);
				} else if(shipmentType == 2) { //Land Transport
					amountString = PricingBusinessModel.calculate_credit_parcel(senderArea, receiverArea, weight, discount, discountType, quantity, discPack);
				}
				
			}
			
			int firstLength = amountString.substring(0, amountString.indexOf("|")).length(); // disAmount
			int secondLength = amountString.substring(0, amountString.indexOf("|", firstLength+2)).length(); // disAmount|pricingAmount
			int thirdLength = amountString.length(); // disAmount|pricingAmount|originalAmount
			
			codeDiscountAmount = Double.parseDouble(amountString.substring(0, firstLength));
			pricingAmount = Double.parseDouble(amountString.substring(firstLength + 1, secondLength ));
			originalAmount = Double.parseDouble(amountString.substring(secondLength + 1, thirdLength ));

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();
			
			sql = "UPDATE consignment SET shipmentType="+shipmentType+", weight="+weight+", quantity="+quantity+", amount="+codeDiscountAmount+" WHERE consignmentNo='" +consignmentNo+"'";
			
			stmt.executeUpdate(sql);
					
			LogBusinessModel.insertCPConsignmentLog(consignmentNo, 99, userId, sql); //記錄管理員在處理訂單的過程
		
			result = "OK-"+ FORMAT.format(pricingAmount) +"|"+  FORMAT.format(codeDiscountAmount) ;
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
	 * 找出需要發送郵件寄達通知的consignment
	 * 
	 * @param consignmentNo
	 * @param request
	 */
	public static void sendParcelReachedEmail(String consignmentNo, HttpServletRequest request) {
		
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

			sql = "SELECT a.* FROM consignment a WHERE a.consignmentNo = '" +consignmentNo+ "' AND a.staffCreate = 0"; //由user自己建立的 consignment 才要send通知email
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	
		    	String toEmail = rs.getString("userId").toString();
		    	String locale = rs.getString("useLocale").toString();
		    	
		    	Mailer.sendParcelReachedEmail(consignmentNo, toEmail, locale, request); //發送email通知寄件者
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
	 * 處理 Pending
	 * 
	 * @param consignmentNo
	 * @param generalCargoNo
	 * @param reason
	 * @param userId
	 * @return
	 */
	protected static String handleStagePending(String consignmentNo, String generalCargoNo, String reasonPending, String reasonText, String userId) {

		String result = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		String sql = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			String cn = "";
			String gn = "";
			int serial = 0;
			boolean isGeneralCargo = false;
			
			if(consignmentNo.replaceAll("\\s","").length() > 9) { //consignmentNo + serial
				cn = consignmentNo.replaceAll("\\s","").substring(0, 9);
				serial = Integer.parseInt(consignmentNo.replaceAll("\\s","").substring(9,12));
			} else {
				cn = consignmentNo;
			}
			
			if(generalCargoNo.replaceAll("\\s","").length() > 0) {
				gn = generalCargoNo;
				isGeneralCargo = true;
			}
			
			
			pstmt = conn.prepareStatement("INSERT INTO log_stagePending (consignmentNo, serial, reasonPending, reasonText, userId, createDT) VALUE " + "(?, ?, ?, ?, ?, NOW())");
//			logger.info("--- Station / Driver ("+userId+") scan " + (gn.equals("") ? cn : gn) + " for Pending Shipment (Reason: "+reasonPending+") ---");
			

			pstmt.setString(1, cn);
			pstmt.setInt(2, serial);
			pstmt.setString(3, reasonPending);
			pstmt.setString(4, reasonText);
			pstmt.setString(5, userId);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			
			stmt = conn.createStatement();
			
			
			//update status
			sql = "UPDATE consignment SET status = 3, stage = 9, reasonPending = '" + reasonPending + "', reasonText = '" + reasonText + "' WHERE consignmentNo = '" + cn + "'";	
			
//			logger.info(sql);
			stmt.executeUpdate(sql);
			
			result = "OK";
			if(isGeneralCargo) {
				result = "OK"+consignmentNo;
			}
		
		}
		catch(Exception ex){
			result = "SystemError: "+ex;
			ex.printStackTrace();
			logger.error(ex.toString());
		}
		finally {
			if(pstmt != null){
				try{ pstmt.close(); } catch(Exception ex){}
				pstmt = null;
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
	 * 產生視圖
	 * 
	 * @param currentstage
	 * @param consignmentNo
	 * @return
	 */
	protected static String visualCheck(int currentstage, String consignmentNo) {

		String sql = "";
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String result = "";
		int quantity = 0;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "noConnection";
	        }

			stmt = conn.createStatement();
			
			//1.先找出總數
			sql = "SELECT quantity FROM consignment WHERE consignmentNo = '" + consignmentNo + "'";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				quantity = Integer.parseInt(rs.getString("quantity").toString());
			}
			
			if(quantity > 0) {
				
				int total = 0;
				int serial = 0;	
				
				sql = "SELECT serial, (SELECT COUNT(DISTINCT serial) FROM log_stage"+currentstage+" WHERE consignmentNo = '" + consignmentNo + "') AS total FROM log_stage"+currentstage+" WHERE consignmentNo = '" + consignmentNo + "' GROUP BY serial";
				rs = stmt.executeQuery(sql);
				
//				while(rs.next()){
//					total = Integer.parseInt(rs.getString("total").toString());
//					serial = Integer.parseInt(rs.getString("serial").toString());
//					
//					if(serial == 1) {
//						result += "<i class=\"ace-icon fa fa-cube orange\"></i>1 &nbsp;";
//					} else if(serial == 2) {
//						result += "<i class=\"ace-icon fa fa-cube orange\"></i>2 &nbsp;";
//					}
//				}
				
						
//				for(int i = 1; i <= quantity; i++) {
//					serial = Integer.parseInt(rs.getString("serial").toString());
//					
//					if(serial == i) {
//						result += "<i class=\"ace-icon fa fa-cube orange\"></i>"+i+" &nbsp;";
//					} else {
//						result += "<i class=\"ace-icon fa fa-cube grey\"></i>"+i+" &nbsp;";
//					}
//
//				}
				
				
				ArrayList<String> all = new ArrayList<String>();
				
				for(int i=1; i <= quantity; i++) {
					all.add(String.valueOf(i));
				}

			    ArrayList<String> scanned = new ArrayList<String>();
			    
			    while(rs.next()){
			    	scanned.add(rs.getString("serial").toString());
			    }


			    List<Integer> comparingList = new ArrayList<Integer>();
			    // adding default values as one
			    for (int a = 0; a < all.size(); a++) {
			        comparingList.add(0);
			    }

			    for (int counter = 0; counter < all.size(); counter++) {
			        if (scanned.contains(all.get(counter))) {
			            comparingList.set(counter, 1);
			        }
			    }
			    


//			    System.out.println(">>>>>>>>> "+comparingList);

		        String tmp = String.valueOf(comparingList);
		        result = tmp;
				
			}

			

		}
		catch(Exception ex){
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
	 * 檢查該 consignment 所處階段
	 * 
	 * @param consignmentNo
	 * @return
	 */
	protected static int checkStage(String consignmentNo) {

		String sql = "";
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int stage = -1;

		try {
			
			if(!consignmentNo.trim().equals("")) {
				
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return -2;
		        }

				stmt = conn.createStatement();

				sql = "SELECT a.consignmentNo, a.generalCargoNo, a.stage FROM consignment a WHERE a.consignmentNo = '" + consignmentNo + "' OR a.generalCargoNo = '" + consignmentNo + "'";

				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					stage = Integer.parseInt(rs.getString("stage").toString());
				}
				
			}
		}
		catch(Exception ex){
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

		return stage;
	}
	
	
	/**
	 * 檢查該 consignmentNo 是否是 generalCargo 的
	 * 
	 * @param consignmentNo
	 * @return
	 */
	protected static boolean isGeneralCargoNo(String consignmentNo) {

		String sql = "";
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean isGeneralCargoNo = false;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return false;
	        }

			stmt = conn.createStatement();

			sql = "SELECT a.generalCargoNo FROM consignment a WHERE a.generalCargoNo = '" + consignmentNo + "'";

			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				isGeneralCargoNo = true;
			}

		}
		catch(Exception ex){
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

		return isGeneralCargoNo;
	}
	
	
	/**
	 * 更改Consignment的狀態
	 * 
	 * @param consignmentNo
	 * @param status
	 * @param payDeadline
	 * @param reasonPending
	 * @param modifier
	 * @param request
	 * @return
	 */
	protected static String updateStatus(String consignmentNo, int status, String payDeadline, String reasonPending, String reasonText, String modifier, HttpServletRequest request) {
		
		String sql = "";
		String result = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }

			stmt = conn.createStatement();
			
			if(status != 3 && reasonPending != ""){
				reasonPending = "";
				reasonText = "";
			} else if(status != 3 && reasonPending == ""){
				reasonText = "";
			} else if(status == 3 && reasonPending != ""){
				reasonText = "";
			}
			
			if(status == 1){ //狀態為：取消，要記錄取消的日期 , cancelBy=1代表是由管理員取消的
				sql = "UPDATE consignment SET status=1, cancelDT=NOW(), cancelBy=1 WHERE consignmentNo='" +consignmentNo+"'";
			} else if(status == 2){ //狀態為：等待付款
				sql = "UPDATE consignment SET status=2, payDeadline='"+payDeadline+"' WHERE consignmentNo='" +consignmentNo+"'";
			} else if(status == 3){ //狀態為：Pending Shipment
				sql = "UPDATE consignment SET status=3, stage=9, reasonPending='"+reasonPending+"', reasonText='"+reasonText+"' WHERE consignmentNo='" +consignmentNo+"'";
			} else {
				sql = "UPDATE consignment SET status="+status+" WHERE consignmentNo='" +consignmentNo+"'";
			}
			
			stmt.executeUpdate(sql);
			
			
			if(status == 1){ //狀態為：取消，要删除已产生的Reservation Receipt, 以及發送通知給booking的人
				
				//刪除 Amazon S3 上的 PDF
//				ResourceBusinessModel.deleteFile("pdf/"+bookingType+"-"+bookingCode+"-SabahBooking.pdf");
				
				//發送通知給booking人
//				sendCancelNotificationToUser(consignmentNo); //TODO: 要做
				
			} else if (status == 3) { //pending shipment
				
				pstmt = conn.prepareStatement("INSERT INTO log_stagePending (consignmentNo, reasonPending, reasonText, userId, createDT) VALUE " + "(?, ?, ?, ?, NOW())");
					
				pstmt.setString(1, consignmentNo);
				pstmt.setString(2, reasonPending);
				pstmt.setString(3, reasonText);
				pstmt.setString(4, modifier);
				
				pstmt.executeUpdate();
				pstmt.clearParameters();

			}
			
			LogBusinessModel.insertCPConsignmentLog(consignmentNo, status, modifier, sql); //記錄管理員在處理訂單的過程
		
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
	 * 
	 * @param value
	 * @param consignmentNo
	 * @return
	 */
	public static double getCurrentAmount(String consignmentNo) {
		
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		double amount = 0.0;
		
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0.0;
	        }

			stmt = conn.createStatement();
			
			sql = "SELECT amount FROM consignment WHERE consignmentNo = '"+ consignmentNo + "'";
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	amount = Double.parseDouble(rs.getString("amount").toString());
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
		
		return amount;
	}
	
	/**
	 * 
	 * @param discount
	 * @param currentAmount
	 * @return
	 */
	public static double discountForAmount(int discount, double currentAmount) {
		double result = 0.0;
		double number = 0.0;
		
		if(discount == 5){
			number = 0.95;
		} else if(discount == 10){
			number = 0.9;
		} else if(discount == 20){
			number = 0.8;
		} else if(discount == 30){
			number = 0.7;
		} 
		
		if(currentAmount != 0.00){
			result = currentAmount * number;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param value
	 * @param discountReason
	 * @param consignmentNo
	 * @param modifier
	 * @param agentId
	 * @return
	 */
	public static String changeAmount(double value, String discountReason, String consignmentNo, String modifier, int agentId) {

		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		String sqlCond = "";
		String result = "";
		
		try {
			
			if(agentId > 0) {
				sqlCond = " AND agentId = " + agentId;
			}

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			stmt = conn.createStatement();
			sql = "UPDATE consignment SET amount = " + value + ", discountReason = '"+ discountReason +"' WHERE consignmentNo = '" + consignmentNo + "'" + sqlCond;			
			stmt.executeUpdate(sql);
			
			LogBusinessModel.insertCPConsignmentLog(consignmentNo, 99, modifier, sql);
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
	 * HQ assign consignment to Agent in 'Pickup Request'
	 * 
	 * @param consignmentNo
	 * @param agentId
	 */
	public static void assignAgent(String consignmentNo, int agentId) {
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			stmt = conn.createStatement();
			sql = "UPDATE consignment SET agentId = " + agentId + " WHERE consignmentNo = '" + consignmentNo + "'";			
			stmt.executeUpdate(sql);

		}
		catch(Exception ex){
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

	}
	
	
	/** 
	 * 管理員在後台直接編輯更新booking內容
	 * 
	 * @param field
	 * @param value
	 * @param consignmentNo
	 * @param dataType
	 * @param modifier
	 * @return
	 */
	public static String inlineUpdate(String field, String value, String consignmentNo, String dataType, String modifier, double oriAmount) {

		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		String result = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "";
	        }
			
			String sqlCond = "";
			if(dataType.equals("txt")){
				sqlCond = field + " = '"+value+"'";
			} else if(dataType.equals("num")){
				sqlCond = field + " = "+value.replaceAll("[^0-9.]", "");
			}
			
			//empty discountReason
//			if(field.equals("amount")){
//				sqlCond += ", discountReason = '' ";
//			}
			
			stmt = conn.createStatement();
			sql = "UPDATE consignment SET " + sqlCond + " WHERE consignmentNo = '" + consignmentNo + "'";
			stmt.executeUpdate(sql);
			
			LogBusinessModel.insertCPConsignmentLog(consignmentNo, 99, modifier, sql);
			
			
			//如果有改到 amount，則要通知 account 那裡要重算
//			if(field.equals("amount")) {
//				sql = "UPDATE account SET changed = 1 WHERE consignmentNo = '" + consignmentNo + "'";			
//				stmt.executeUpdate(sql);
				
				//add to change amount log
//				LogBusinessModel.insertChangeAmountLog(consignmentNo, oriAmount, 0, "Price had changed to RM "+ value, modifier);
//			}
			
			if(field.equals("agentId")) {
				NotificationBusinessModel.insertNewAgentNotification("consignment", "New Consignment", consignmentNo, value);
			}
			
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
	 * 根據 UserId 取得該使用者的所有 consignment（不包含替公司產生的consignment）
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> getRecordByUserId(String userId, int pageNo) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

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
			
			String sqlCondition = " WHERE userId = '"+userId+"' AND staffCreate = 0";
			
			sql = "SELECT a.*, (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.senderArea) AS senderAreaname," +
					" (SELECT b.name_enUS FROM area b WHERE b.aid = a.receiverArea) AS receiverAreaname" +
					" FROM consignment a" + sqlCondition + " ORDER BY a.createDT DESC LIMIT " + page + ", " + numberPerPage;
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderAreaname(rs.getString("senderAreaname") == null ? "(unknown)" : rs.getString("senderAreaname").toString());
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setReceiverAreaname(rs.getString("receiverAreaname") == null ? "(unknown)" : rs.getString("receiverAreaname").toString());
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setPromoCode(rs.getString("promoCode").toString());
		    	obj.setDiscountReason(rs.getString("discountReason") == null ? "" : rs.getString("discountReason").toString());
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString().length() > 10 ? rs.getString("createDT").toString().substring(0, 10) : rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setTotal(Integer.parseInt(rs.getString("total").toString()));
//		    	obj.setInvoiceNo(rs.getString("invoiceNo") == null ? "" : rs.getString("invoiceNo").toString());
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
	 * 根據 userId 取得該使用者的所有 address book
	 * 
	 * @param userId
	 * @param bookType: sender, receiver
	 * @param locale
	 * @return
	 */
	protected static String getAddressbookByUserId (String userId, String bookType, String locale) {

		String title = Resource.getString("ID_LABEL_RECEIVERNAME", locale);
		String sql = "";
		String result = "";
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			
			if(userId.trim().length() > 0){
			
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "noConnection";
		        }
	
				stmt = conn.createStatement();
				
				sql = "SELECT a.* FROM addressbook a" +
						" LEFT JOIN memberlist b ON a.mid = b.mid WHERE b.userId = '" + userId + "'" +
						" ORDER BY a.aid ASC";
				
			    rs = stmt.executeQuery(sql);
			    
			    if(bookType.equals("sender")) {
			    	title = Resource.getString("ID_LABEL_SENDERNAME", locale);
			    }
			    
			    result += "<table class=\"table table-striped\">";
			    result += " <thead>";
			    result += " 	 <tr>";
			    result += "	 	<th>"+Resource.getString("ID_LABEL_NUM", locale)+"</th>";
			    result += "	 	<th>"+title+"</th>";
			    result += "		<th>&nbsp;</th>";
			    result += "	 </tr>";
			    result += " </thead>";
			    result += "<tbody>";
			    
			    int i = 1;
	
			    while(rs.next()){
			    	String aid = rs.getString("aid").toString();
			    	
			    	if(bookType.equals("sender")) {
			    		
			    		result += "<tr>";
				    	result += "	<td>"+i+".</td>";
				    	result += "	<td><a style=\"cursor:pointer\" type=\"button\" data-dismiss=\"modal\" onClick=\"insertAddressDetail('"+aid+"', 'sender')\">"+rs.getString("receiverName").toString()+"</a></td>";
				    	result += "	<td>";
				    	result += "     <a style=\"cursor:pointer\" type=\"button\" data-dismiss=\"modal\" onClick=\"insertAddressDetail('"+aid+"', 'sender')\">Choose This</a>";
				    	result += "     <input type=\"hidden\" id=\"senderName_"+aid+"\" name=\"senderName_"+aid+"\" value=\""+rs.getString("receiverName").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderAddress1_"+aid+"\" name=\"senderAddress1_"+aid+"\" value=\""+rs.getString("receiverAddress1").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderAddress2_"+aid+"\" name=\"senderAddress2_"+aid+"\" value=\""+rs.getString("receiverAddress2").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderAddress3_"+aid+"\" name=\"senderAddress3_"+aid+"\" value=\""+rs.getString("receiverAddress3").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderPostcode_"+aid+"\" name=\"senderPostcode_"+aid+"\" value=\""+rs.getString("receiverPostcode").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderZone_"+aid+"\" name=\"senderZone_"+aid+"\" value=\""+rs.getString("receiverZone").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderPhone_"+aid+"\" name=\"senderPhone_"+aid+"\" value=\""+rs.getString("receiverPhone").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderArea_"+aid+"\" name=\"senderArea_"+aid+"\" value=\""+rs.getString("receiverArea").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"senderCountry_"+aid+"\" name=\"senderCountry_"+aid+"\" value=\""+rs.getString("receiverCountry").toString()+"\" />";
				    	result += "  </td>";
				    	result += "</tr>";
			    		
			    	} else { //receiver
			    		
			    		result += "<tr>";
				    	result += "	<td>"+i+".</td>";
				    	result += "	<td><a style=\"cursor:pointer\" type=\"button\" data-dismiss=\"modal\" onClick=\"insertAddressDetail('"+aid+"', 'receiver')\">"+rs.getString("receiverName").toString()+"</a></td>";
				    	result += "	<td>";
				    	result += "     <a style=\"cursor:pointer\" type=\"button\" data-dismiss=\"modal\" onClick=\"insertAddressDetail('"+aid+"', 'receiver')\">Choose This</a>";
				    	result += "     <input type=\"hidden\" id=\"receiverName_"+aid+"\" name=\"receiverName_"+aid+"\" value=\""+rs.getString("receiverName").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverAttn_"+aid+"\" name=\"receiverAttn_"+aid+"\" value=\""+rs.getString("receiverAttn").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverAddress1_"+aid+"\" name=\"receiverAddress1_"+aid+"\" value=\""+rs.getString("receiverAddress1").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverAddress2_"+aid+"\" name=\"receiverAddress2_"+aid+"\" value=\""+rs.getString("receiverAddress2").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverAddress3_"+aid+"\" name=\"receiverAddress3_"+aid+"\" value=\""+rs.getString("receiverAddress3").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverPostcode_"+aid+"\" name=\"receiverPostcode_"+aid+"\" value=\""+rs.getString("receiverPostcode").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverZone_"+aid+"\" name=\"receiverZone_"+aid+"\" value=\""+rs.getString("receiverZone").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverPhone_"+aid+"\" name=\"receiverPhone_"+aid+"\" value=\""+rs.getString("receiverPhone").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverArea_"+aid+"\" name=\"receiverArea_"+aid+"\" value=\""+rs.getString("receiverArea").toString()+"\" />";
				    	result += "     <input type=\"hidden\" id=\"receiverCountry_"+aid+"\" name=\"receiverCountry_"+aid+"\" value=\""+rs.getString("receiverCountry").toString()+"\" />";
				    	result += "  </td>";
				    	result += "</tr>";
				    	
			    	}
			    	
			    	i++;
			    }
			    
			    result += " </tbody>";
			    result += "</table>";
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
	 * 公眾在 trace 包裹
	 * 
	 * @param consignmentNo
	 * @param locale
	 * @return
	 */
	protected static String trace (String consignmentNo, String locale) {

		String sql = "";
		String result = "";
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		String stage0 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-info\"><i class=\"fa fa-laptop\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE0", locale)+"</h4></td></tr>";
		String stage1 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-cube\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE1", locale)+"</h4></td></tr>";
		String stage2 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-home\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE2", locale)+"</h4></td></tr>";
		String stage3 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-upload\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE3", locale)+"</h4></td></tr>";
		String stage4 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-refresh\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE4", locale)+"</h4></td></tr>";
		String stage5 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-download\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE5", locale)+"</h4></td></tr>";
		String stage6 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-truck\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE6", locale)+"</h4></td></tr>";
		String stage7 = "<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-check-square\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE7", locale)+"</h4></td></tr>";
		String stage8 = "";//"<tr><td>&nbsp;</td>	<td><h4><button class=\"btn btn-app btn-xs btn-grey\"><i class=\"fa fa-check-square\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE8", locale)+"</h4></td></tr>";
		String stage9 = "";

		try {

				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "noConnection";
		        }
	
				stmt = conn.createStatement();
				
				sql = "SELECT '1' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, NULL as reasonPending, NULL AS reasonText FROM log_stage1 a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stage1 GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'" +
						"" +
						" UNION" +
						"" +
						" SELECT '2' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, NULL as reasonPending, NULL AS reasonText FROM log_stage2 a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stage2 GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'" +
						"" +
						" UNION" +
						"" +
						" SELECT '3' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, NULL as reasonPending, NULL AS reasonText FROM log_stage3 a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stage3 GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'" +
						"" +
						" UNION" +
						"" +
						" SELECT '4' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, NULL as reasonPending, NULL AS reasonText FROM log_stage4 a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stage4 GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'" +
						"" +
						" UNION" +
						"" +
						" SELECT '5' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, NULL as reasonPending, NULL AS reasonText FROM log_stage5 a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stage5 GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'" +
						"" +
						" UNION" +
						"" +
						" SELECT '6' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, NULL as reasonPending, NULL AS reasonText FROM log_stage6 a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stage6 GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'" +
						"" +
						" UNION" +
						"" +
						" SELECT '7' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, NULL as reasonPending, NULL AS reasonText FROM log_stage7 a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stage7 GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'" +
						"" +
						" UNION" +
						"" +
						" SELECT '9' AS stage, a.lid, a.consignmentNo, a.serial, a.userId, a.createDT, a.reasonPending, a.reasonText FROM log_stagePending a" +
						" INNER JOIN" +
						"     (SELECT consignmentNo, MAX(createDT) AS MaxCreateDT FROM log_stagePending GROUP BY consignmentNo) b" +
						" ON a.consignmentNo = b.consignmentNo AND a.createDT = b.MaxCreateDT" +
						" WHERE a.consignmentNo = '"+consignmentNo+"'";			
				
				
			    rs = stmt.executeQuery(sql);
			    
			    result += "<table class=\"table table-striped\">";
			    result += " <thead>";
			    result += " 	 <tr>";
			    result += "	 	<th>"+Resource.getString("ID_LABEL_DATE", locale)+"</th>";
			    result += "	 	<th>"+Resource.getString("ID_LABEL_DESCRIPTION", locale)+"</th>";
			    result += "	 </tr>";
			    result += " </thead>";
			    result += "<tbody>";
			    result += "<tr>";
			    
			    while(rs.next()){
			    	String dt = rs.getString("createDT").toString();
			    	int stage = Integer.parseInt(rs.getString("stage").toString());

			    	if(stage==1) {
			    		stage1 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-info\"><i class=\"fa fa-cube\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE1", locale)+"</h4></td></tr>";
			    	} else if(stage==2) {
			    		stage2 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-success\"><i class=\"fa fa-home\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE2", locale)+"</h4></td></tr>";
			    	} else if(stage==3) {
			    		stage3 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-danger\"><i class=\"fa fa-upload\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE3", locale)+"</h4></td></tr>";
			    	} else if(stage==4) {
			    		stage4 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-warning\"><i class=\"fa fa-refresh\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE4", locale)+"</h4></td></tr>";
			    	} else if(stage==5) {
			    		stage5 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-rose\"><i class=\"fa fa-download\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE5", locale)+"</h4></td></tr>";
			    	} else if(stage==6) {
			    		stage6 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-primary\"><i class=\"fa fa-truck\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE6", locale)+"</h4></td></tr>";
			    	} else if(stage==7) {
			    		stage7 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-purple\"><i class=\"fa fa-check-square\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE7", locale)+"</h4></td></tr>";
			    	} else if(stage==8) {
			    		//stage8 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-purple\"><i class=\"fa fa-check-square\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE8", locale)+"</h4></td></tr>";
			    	} else if(stage==9) {
			    		String reason = "";
			    		String reasonPending = rs.getString("reasonPending").toString();
			    		if(reasonPending.equals("MD")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONMD",locale);
						} else if(reasonPending.equals("NA")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONNA",locale);
						} else if(reasonPending.equals("NP")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONNP",locale);
						} else if(reasonPending.equals("PH")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONPH",locale);
						} else if(reasonPending.equals("RF")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONRF",locale);
						} else if(reasonPending.equals("SH")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONSH",locale);
						} else if(reasonPending.equals("ST")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONST",locale);
						} else if(reasonPending.equals("UL")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONUL",locale);
						} else if(reasonPending.equals("VB")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONVB",locale);
						} else if(reasonPending.equals("WA")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONWA",locale);
						} else if(reasonPending.equals("WR")) {
							reason = Resource.getString("ID_LABEL_PENDINGREASONWR",locale);
						} else if(reasonPending.equals("")) {
							reason = rs.getString("reasonText");
						}
			    		stage9 = "<tr style=\"background-color: #FFFBE5\"><td>"+dt+"</td>	<td><h4><button class=\"btn btn-app btn-xs btn-danger\"><i class=\"fa fa-exclamation-triangle\"></i></button> " +Resource.getString("ID_LABEL_TRACESTAGE9", locale)+"</h4><span style=\"margin-left: 45px\">"+reason+"</span></td></tr>";
			    	}
					
			    }
			    
			    result += stage0 + stage1 + stage2 + stage3 + stage4 + stage5 + stage6 + stage9 + stage7;
			    
			    result += "</tr>";
			    result += " </tbody>";
			    result += "</table>";

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
	 * 新增 Consignment 資料
	 * 
	 * @param data
	 * @param consignmentNo
	 * @return
	 */
	public static String insertConsignment(HashMap<String, Object> data, String consignmentNo) {
		
		String result = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		Connection conn2 = null;
		
		if(data != null && (data.size() > 0)){ //如果有資料

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				
				String userId = data.get("userId") == null ? "" : data.get("userId").toString();
				int staffCreate = Integer.parseInt(data.get("staffCreate") == null ? "0" : data.get("staffCreate").toString());
				String howtocreate = data.get("howtocreate") == null ? "Customer" : data.get("howtocreate").toString();
				
				//1. 先 insert 到 consignment
				pstmt = conn.prepareStatement("INSERT INTO consignment (consignmentNo, generalCargoNo, userId, partnerId, agentId, senderName, senderAddress1, senderAddress2, senderAddress3, senderPostcode, senderZone, senderPhone, senderArea, senderIC," +
						" receiverName, receiverAttn, receiverAddress1, receiverAddress2, receiverAddress3, receiverPostcode, receiverZone, receiverPhone, receiverArea, helps, tickItem, shipmentMethod, shipmentType, flightNum, airwaybillNum, natureGood, receiptNum, pricing, quantity, weight, amount, payMethod, creditArea, stage, stationRemark, terms, useLocale, verify, createDT, staffCreate)" +
						" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)");
	
				pstmt.setString(1, consignmentNo);
				pstmt.setString(2, data.get("generalCargoNo") == null ? "" : data.get("generalCargoNo").toString().replaceAll("\\s","")); //remove all whitespace and non visible characters such as tab, \n
				pstmt.setString(3, userId);
				pstmt.setInt(4, Integer.parseInt(data.get("partnerId") == null ? "0" : data.get("partnerId").toString()));
				pstmt.setInt(5, Integer.parseInt(data.get("agentId") == null ? "0" : data.get("agentId").toString()));
				pstmt.setString(6, data.get("senderName") == null ? "" : data.get("senderName").toString());
				pstmt.setString(7, data.get("senderAddress1") == null ? "" : data.get("senderAddress1").toString());
				pstmt.setString(8, data.get("senderAddress2") == null ? "" : data.get("senderAddress2").toString());
				pstmt.setString(9, data.get("senderAddress3") == null ? "" : data.get("senderAddress3").toString());
				pstmt.setString(10, data.get("senderPostcode") == null ? "" : data.get("senderPostcode").toString());
				pstmt.setString(11, data.get("senderZone") == null ? "0" : data.get("senderZone") .equals("") ? "0" : data.get("senderZone").toString());
				pstmt.setString(12, data.get("senderPhone") == null ? "" : data.get("senderPhone").toString());
				pstmt.setInt(13, Integer.parseInt(data.get("senderArea") == null ? "0" : data.get("senderArea").toString()));
				pstmt.setString(14, data.get("senderIC") == null ? "" : data.get("senderIC").toString());
				pstmt.setString(15, data.get("receiverName") == null ? "" : data.get("receiverName").toString());
				pstmt.setString(16, data.get("receiverAttn") == null ? "" : data.get("receiverAttn").toString());
				pstmt.setString(17, data.get("receiverAddress1") == null ? "" : data.get("receiverAddress1").toString());
				pstmt.setString(18, data.get("receiverAddress2") == null ? "" : data.get("receiverAddress2").toString());
				pstmt.setString(19, data.get("receiverAddress3") == null ? "" : data.get("receiverAddress3").toString());
				pstmt.setString(20, data.get("receiverPostcode") == null ? "" : data.get("receiverPostcode").toString());
				pstmt.setString(21, data.get("receiverZone") == null ? "0" : data.get("receiverZone") .equals("") ? "0" : data.get("receiverZone").toString());
				pstmt.setString(22, data.get("receiverPhone") == null ? "" : data.get("receiverPhone").toString());
				pstmt.setInt(23, Integer.parseInt(data.get("receiverArea") == null ? "0" : data.get("receiverArea").toString()));
				pstmt.setString(24, data.get("helps") == null ? "" : data.get("helps").toString());
				pstmt.setString(25, data.get("tickItem") == null ? "" : data.get("tickItem").toString());
				pstmt.setInt(26, Integer.parseInt(data.get("shipmentMethod") == null ? "0" : data.get("shipmentMethod").toString()));
				pstmt.setInt(27, Integer.parseInt(data.get("shipmentType") == null ? "0" : data.get("shipmentType").toString()));
				
				pstmt.setString(28, data.get("flightNum") == null ? "" : data.get("flightNum").toString());
				pstmt.setString(29, data.get("airwaybillNum") == null ? "" : data.get("airwaybillNum").toString());
				pstmt.setString(30, data.get("natureGood") == null ? "" : data.get("natureGood").toString());
				pstmt.setString(31, data.get("receiptNum") == null ? "" : data.get("receiptNum").toString());
				
				pstmt.setInt(32, Integer.parseInt(data.get("pricing") == null ? "0" : data.get("pricing").toString()));
				pstmt.setInt(33, Integer.parseInt(data.get("quantity") == null ? "0" : data.get("quantity").toString()));
				pstmt.setDouble(34, Double.parseDouble(data.get("weight") == null ? "0.0" : data.get("weight").toString()));
				pstmt.setDouble(35, Double.parseDouble(data.get("amount") == null ? "0.0" : data.get("amount").toString()));
				pstmt.setInt(36, Integer.parseInt(data.get("payMethod") == null ? "0" : data.get("payMethod").toString()));
				pstmt.setInt(37, Integer.parseInt(data.get("creditArea") == null ? "0" : data.get("creditArea").toString()));
				pstmt.setInt(38, Integer.parseInt(data.get("stage") == null ? "0" : data.get("stage").toString()));
				pstmt.setString(39, data.get("stationRemark") == null ? "" : data.get("stationRemark").toString());
				pstmt.setString(40, data.get("terms") == null ? "" : data.get("terms").toString());
				pstmt.setString(41, data.get("useLocale") == null ? "en_US" : data.get("useLocale").toString());
				pstmt.setString(42, data.get("verify") == null ? "0000000000" : data.get("verify").toString());
				pstmt.setInt(43, staffCreate);
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				String accDT = "";
				String accMonth = "";
				String yyyyMMdd = UtilsBusinessModel.todayDate();
				if(yyyyMMdd.length() == 10) { //ie. 2011-09-07
					accDT = yyyyMMdd;
					accMonth = yyyyMMdd.substring(0, 4) + yyyyMMdd.substring(5, 7);
				}

				
				//2. 然後再 insert 到 account
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				pstmt = conn.prepareStatement("INSERT INTO account(consignmentNo, accDT, accMonth, statMonth, inputMonth, outputMonth) VALUE (?, ?, ?, ?, ?, ?)");
	
				pstmt.setString(1, consignmentNo);
				pstmt.setString(2, accDT);
				pstmt.setString(3, accMonth);
				pstmt.setString(4, accMonth);
				pstmt.setString(5, accMonth);
				pstmt.setString(6, accMonth);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				result = "OK";
				
				activityLogger.info("- New Consignment ("+howtocreate+"): " + consignmentNo + " @ " + UtilsBusinessModel.todayDate() + " " + UtilsBusinessModel.timeNow());
				
				if(staffCreate==1) { //由員工產生的記錄
					LogBusinessModel.insertCPConsignmentLog(consignmentNo, 0, userId, "New Consignment ("+howtocreate+")");
				} else {
					LogBusinessModel.insertCPConsignmentLog(consignmentNo, 0, "guest", "New Consignment");
				}
			
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.toString());
				result = ex.toString();
			}
			finally {
				if(pstmt != null){
					try{ pstmt.close(); } catch(Exception ex){}
					pstmt = null;
				}
				if(stmt != null){
					try{ stmt.close(); } catch(Exception ex){}
					stmt = null;
				}
				if(conn != null){
					ConnectionManager.closeConnection(conn);
					conn = null;
				}
				if(conn2 != null){
					ConnectionManager.closeConnection(conn2);
					conn2 = null;
				}
			}
		}

		return result;
	}
	

	/** 
	 * Add promocode to existing consignment
	 * 
	 * @param promoCode
	 * @param consignmentNo
	 * @return
	 */
	public static String addPromoCode(String promoCode, String consignmentNo) {

		Statement stmt = null;
		Connection conn = null;
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
			sql = "UPDATE consignment SET promoCode = '"+promoCode+"' WHERE consignmentNo = '" + consignmentNo + "'";
			stmt.executeUpdate(sql);
			
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
	 * Get promoCode of consignment
	 * 
	 * @param consignmentNo
	 * @return
	 */
	protected static String getPromoCodeByCN(String consignmentNo) {

		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "";
		String promoCode = "";
		
		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return promoCode;
	        }
			
			stmt = conn.createStatement();
			sql = "SELECT promoCode from consignment WHERE consignmentNo = '" + consignmentNo + "'";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
		    	promoCode = rs.getString("promoCode") == null ? "" : rs.getString("promoCode").toString();
			}
			
		}
		catch(Exception ex){
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

		return promoCode;
	}
	
	/**
	 * 批次新增 Consignment 資料（例如透過 excel 上傳）
	 * 
	 * @param data
	 * @param userId
	 * @return
	 */
	public static String batchInsertConsignment(ArrayList<ConsignmentDataModel> data, String userId) {
		
		String result = "";
		String lastuploadedCN = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		Connection conn2 = null;
		ConsignmentDataModel consignmentData = new ConsignmentDataModel();
		
		if(data != null && (data.size() > 0)){ //如果有資料

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				
				//快速預先產生同數量的 consignment no.，並放在 arraylist 裡備用
				int cid = getMaxCid();
				ArrayList<ConsignmentDataModel> cnData = new ArrayList<ConsignmentDataModel>();
				ConsignmentDataModel cnObj = new ConsignmentDataModel();
				
				for (int i = 0; i < data.size(); i++) {
					String tmp = fastGenerateConsignmentNo(cid+i);
					cnObj = new ConsignmentDataModel();
					cnObj.setConsignmentNo(tmp);
					cnData.add(cnObj);
				}
				
				
				
				for (int i = 0; i < data.size(); i++) {

					consignmentData = (ConsignmentDataModel) data.get(i);
					String consignmentNo = cnData.get(i).getConsignmentNo();
					String generalCargoNo = consignmentData.getGeneralCargoNo().toString().replaceAll("\\s",""); //remove all whitespace and non visible characters such as tab, \n

					lastuploadedCN += "'" + generalCargoNo + "',";
					
					//1. 先 insert 到 consignment
//					pstmt = conn.prepareStatement("INSERT INTO consignment (consignmentNo, generalCargoNo, userId, partnerId, senderName, senderAddress1, senderAddress2, senderAddress3, senderPostcode, senderZone, senderPhone, senderArea, senderIC," +
//							" receiverName, receiverAttn, receiverAddress1, receiverAddress2, receiverAddress3, receiverPostcode, receiverZone, receiverPhone, receiverArea, helps, tickItem, shipmentType, freightType, pricing, quantity, weight, amount, payMethod, creditArea, stage, terms, useLocale, verify, createDT, staffCreate)" +
//							" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)" +
//							" SELECT generalCargoNo FROM consignment WHERE NOT EXISTS (SELECT generalCargoNo FROM consignment WHERE generalCargoNo = '"+generalCargoNo+"')");
		
					pstmt = conn.prepareStatement("INSERT INTO consignment (consignmentNo, generalCargoNo, userId, partnerId, senderName, senderAddress1, senderAddress2, senderAddress3, senderPostcode, senderZone, senderPhone, senderArea, senderIC," +
							" receiverName, receiverAttn, receiverAddress1, receiverAddress2, receiverAddress3, receiverPostcode, receiverZone, receiverPhone, receiverArea, helps, tickItem, shipmentType, pricing, quantity, weight, amount, payMethod, creditArea, stage, terms, useLocale, verify, createDT, staffCreate)" +
							" SELECT * FROM (SELECT '"+consignmentNo+"' AS consignmentNo, '"+generalCargoNo+"' AS generalCargoNo, '"+userId+"' AS userId, "+consignmentData.getPartnerId()+" AS partnerId, '"+consignmentData.getSenderName()+"' AS senderName, '"+consignmentData.getSenderAddress1()+"' AS senderAddress1, '"+consignmentData.getSenderAddress2()+"' AS senderAddress2, '"+consignmentData.getSenderAddress3()+"' AS senderAddress3, '"+consignmentData.getSenderPostcode()+"' AS senderPostcode," +
							" 						"+consignmentData.getSenderZone()+" AS senderZone, '"+consignmentData.getSenderPhone()+"' AS senderPhone, "+consignmentData.getSenderArea()+" AS senderArea, '"+consignmentData.getSenderIC()+"' AS senderIC, '"+consignmentData.getReceiverName()+"' AS receiverName, '"+consignmentData.getReceiverAttn()+"' AS receiverAttn, '"+consignmentData.getReceiverAddress1()+"' AS receiverAddress1, '"+consignmentData.getReceiverAddress2()+"' AS receiverAddress2," +
							" 						'"+consignmentData.getReceiverAddress3()+"' AS receiverAddress3, '"+consignmentData.getReceiverPostcode()+"' AS receiverPostcode, "+consignmentData.getReceiverZone()+" AS receiverZone, '"+consignmentData.getReceiverPhone()+"' AS receiverPhone, "+consignmentData.getReceiverArea()+" AS receiverArea, '"+consignmentData.getHelps()+"' AS helps, '"+consignmentData.getTickItem()+"' AS tickItem, "+consignmentData.getShipmentType()+" AS shipmentType," +
							"						"+consignmentData.getPricing()+" AS pricing, "+consignmentData.getQuantity()+" AS quantity, "+consignmentData.getWeight()+" AS weight, "+consignmentData.getAmount()+" AS amount, "+consignmentData.getPayMethod()+" AS payMethod, "+consignmentData.getCreditArea()+" AS creditArea, "+consignmentData.getStage()+" AS stage, '"+consignmentData.getTerms()+"' AS terms," +
							"						'en_US' AS useLocale, '"+consignmentData.getVerify()+"' AS verify, NOW() AS createDT, "+consignmentData.getStaffCreate()+" AS staffCreate) AS tmp" +
							" WHERE NOT EXISTS (" +
							"    SELECT generalCargoNo FROM consignment WHERE generalCargoNo = '"+generalCargoNo+"'" +
							" ) LIMIT 1;");
					
//					pstmt = conn.prepareStatement("INSERT INTO consignment (consignmentNo, generalCargoNo, userId, partnerId, senderName, senderAddress1, senderAddress2, senderAddress3, senderPostcode, senderZone, senderPhone, senderArea, senderIC," +
//							" receiverName, receiverAttn, receiverAddress1, receiverAddress2, receiverAddress3, receiverPostcode, receiverZone, receiverPhone, receiverArea, helps, tickItem, shipmentType, freightType, pricing, quantity, weight, amount, payMethod, creditArea, stage, terms, useLocale, verify, createDT, staffCreate)" +
//							" SELECT * FROM (SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?) AS tmp" +
//							" WHERE NOT EXISTS (" +
//							"    SELECT generalCargoNo FROM consignment WHERE generalCargoNo = '"+generalCargoNo+"'" +
//							" ) LIMIT 1;");
//					
//					pstmt.setString(1, consignmentNo);
//					pstmt.setString(2, generalCargoNo);
//					pstmt.setString(3, userId);
//					pstmt.setInt(4, consignmentData.getPartnerId());
//					pstmt.setString(5, consignmentData.getSenderName());
//					pstmt.setString(6, consignmentData.getSenderAddress1());
//					pstmt.setString(7, consignmentData.getSenderAddress2());
//					pstmt.setString(8, consignmentData.getSenderAddress3());
//					pstmt.setString(9, consignmentData.getSenderPostcode());
//					pstmt.setInt(10, consignmentData.getSenderZone());
//					pstmt.setString(11, consignmentData.getSenderPhone());
//					pstmt.setInt(12, consignmentData.getSenderArea());
//					pstmt.setString(13, consignmentData.getSenderIC());
//					pstmt.setString(14, consignmentData.getReceiverName());
//					pstmt.setString(15, consignmentData.getReceiverAttn());
//					pstmt.setString(16, consignmentData.getReceiverAddress1());
//					pstmt.setString(17, consignmentData.getReceiverAddress2());
//					pstmt.setString(18, consignmentData.getReceiverAddress3());
//					pstmt.setString(19, consignmentData.getReceiverPostcode());
//					pstmt.setInt(20, consignmentData.getReceiverZone());
//					pstmt.setString(21, consignmentData.getReceiverPhone());
//					pstmt.setInt(22, consignmentData.getReceiverArea());
//					pstmt.setString(23, consignmentData.getHelps());
//					pstmt.setString(24, consignmentData.getTickItem());
//					pstmt.setInt(25, consignmentData.getShipmentType());
//					pstmt.setInt(26, consignmentData.getFreightType());
//					pstmt.setInt(27, consignmentData.getPricing());
//					pstmt.setInt(28, consignmentData.getQuantity());
//					pstmt.setDouble(29, consignmentData.getWeight());
//					pstmt.setDouble(30, consignmentData.getAmount());
//					pstmt.setInt(31, consignmentData.getPayMethod());
//					pstmt.setInt(32, consignmentData.getCreditArea());
//					pstmt.setInt(33, consignmentData.getStage());
//					pstmt.setString(34, consignmentData.getTerms());
//					pstmt.setString(35, "en_US");
//					pstmt.setString(36, consignmentData.getVerify());
//					pstmt.setInt(37, consignmentData.getStaffCreate());
					
					pstmt.executeUpdate();
					pstmt.clearParameters();

					activityLogger.info("- New Consignment ("+consignmentData.getHowtocreate()+"): " + consignmentData.getGeneralCargoNo().toString().replaceAll("\\s","") + " @ " + UtilsBusinessModel.todayDate() + " " + UtilsBusinessModel.timeNow());
					
					LogBusinessModel.insertCPConsignmentLog(consignmentNo, 0, userId, "New Consignment ("+consignmentData.getHowtocreate()+")");
				
				}
				
				result = "OK-" + lastuploadedCN.substring(0, lastuploadedCN.length()-1); //去除掉最後一個逗號
			
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.toString());
				result = ex.toString();
			}
			finally {
				if(pstmt != null){
					try{ pstmt.close(); } catch(Exception ex){}
					pstmt = null;
				}
				if(stmt != null){
					try{ stmt.close(); } catch(Exception ex){}
					stmt = null;
				}
				if(conn != null){
					ConnectionManager.closeConnection(conn);
					conn = null;
				}
				if(conn2 != null){
					ConnectionManager.closeConnection(conn2);
					conn2 = null;
				}
			}
		}

		return result;
	}
	
	

	/**
	 * 若不曾產生過consigment，則新增一個空白的
	 * 
	 * @param consignmentNo
	 * @param weight
	 * @param length
	 * @param width
	 * @param height
	 * @param quantity
	 * @param userId
	 * @param verify
	 * @param staffCreate
	 * @return
	 */
	protected static String scanCreateConsignment (String consignmentNo, double weight, double length, double width, double height, int quantity, String userId, String verify, int staffCreate) {
		
		String result = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		Connection conn2 = null;

		if((consignmentNo.trim().length() == 9) && (consignmentNo.substring(0, 1).equals("A")) ){ //第一碼必須是 A

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				
				//1. 先 insert 到 consignment
				pstmt = conn.prepareStatement("INSERT INTO consignment (consignmentNo, weight, length, width, height, quantity, userId, verify, staffCreate, createDT)" +
						" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())");
	
				pstmt.setString(1, consignmentNo);
				pstmt.setDouble(2, weight);
				pstmt.setDouble(3, length);
				pstmt.setDouble(4, width);
				pstmt.setDouble(5, height);
				pstmt.setInt(6, quantity);
				pstmt.setString(7, userId);
				pstmt.setString(8, verify);
				pstmt.setInt(9, staffCreate);
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				//2. 準備好資料
//				String bookingCode = data.get("bookingCode") == null ? "" : data.get("bookingCode").toString();
//				
//				double agentRate = 0.0;
//				double agentRate_adult = 0.0;
//				double agentRate_child = 0.0;
//				double inputTax = 0.0;
//				int inclGST = -1;
//				
//
//				conn2 = ConnectionManager.getConnection();
//				if(conn2 == null) {
//					logger.fatal("*** NO connection");
//					return "";
//		        }
//				
//				stmt = conn2.createStatement();
//				
//				sql = "SELECT contractRate_adult, contractRate_child, inclGST FROM agentrate_tour WHERE aid = " + data.get("tid").toString();
//				rs = stmt.executeQuery(sql);
//
//			    while(rs.next()){
//			    	agentRate_adult = Double.parseDouble(rs.getString("contractRate_adult") == null ? "0" : rs.getString("contractRate_adult").toString());
//			    	agentRate_child = Double.parseDouble(rs.getString("contractRate_child") == null ? "0" : rs.getString("contractRate_child").toString());
//			    	inclGST = Integer.parseInt(rs.getString("inclGST") == null ? "-1" : rs.getString("inclGST").toString());
//			    }
//			    
//			    if(inclGST == 0) { //haven't included GST
//			    	agentRate_adult = agentRate_adult * 1.06;
//			    	agentRate_child = agentRate_child * 1.06;
//			    }
//			    
//			    agentRate = (agentRate_adult * Integer.parseInt(data.get("numAdult") == null ? "0" : data.get("numAdult").toString())) + 
//			    			(agentRate_child * Integer.parseInt(data.get("numChild") == null ? "0" : data.get("numChild").toString()));
////				logger.info("agent rate: " +agentRate);
//			    
//			    
				String accDT = "";
				String accMonth = "";
				String yyyyMMdd = UtilsBusinessModel.todayDate();
				if(yyyyMMdd.length() == 10) { //ie. 2011-09-07
					accDT = yyyyMMdd;
					accMonth = yyyyMMdd.substring(0, 4) + yyyyMMdd.substring(5, 7);
				}
//				
//				if( (inclGST == -1) || (inclGST == 2) ) { //Not Set Yet or No GST
//					inputTax = 0;
//				} else {
//					inputTax = agentRate - (agentRate / 1.06);
//				}
				
				
				//3. 然後再 insert 到 account
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				pstmt = conn.prepareStatement("INSERT INTO account(consignmentNo, accDT, accMonth, statMonth, inputMonth, outputMonth) VALUE (?, ?, ?, ?, ?, ?)");
	
				pstmt.setString(1, consignmentNo);
				pstmt.setString(2, accDT);
				pstmt.setString(3, accMonth);
				pstmt.setString(4, accMonth);
				pstmt.setString(5, accMonth);
				pstmt.setString(6, accMonth);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				result = "Created";
				
				activityLogger.info("- New Consignment: " + consignmentNo + " @ " + UtilsBusinessModel.todayDate() + " " + UtilsBusinessModel.timeNow());
				LogBusinessModel.insertCPConsignmentLog(consignmentNo, 0, "staff", "New Consignment");
			
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.toString());
				result = ex.toString();
			}
			finally {
				if(pstmt != null){
					try{ pstmt.close(); } catch(Exception ex){}
					pstmt = null;
				}
				if(stmt != null){
					try{ stmt.close(); } catch(Exception ex){}
					stmt = null;
				}
				if(conn != null){
					ConnectionManager.closeConnection(conn);
					conn = null;
				}
				if(conn2 != null){
					ConnectionManager.closeConnection(conn2);
					conn2 = null;
				}
			}
		} else {
			result = "NoSuchConsignment";
		}

		return result;
	}
	
	
	
	/**
	 * 把 Consignment 資料存到 draft
	 * 
	 * @param data
	 * @param consignmentNo
	 * @return
	 */
	protected static String insertDraft(HashMap<String, Object> data) {
		
		String result = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		Connection conn2 = null;
		
		if(data != null && (data.size() > 0)){ //如果有資料

			try {
	
				//DB connection
				conn = ConnectionManager.getConnection();
				if(conn == null) {
					logger.fatal("*** NO connection");
					return "";
		        }
				

				pstmt = conn.prepareStatement("INSERT INTO draft (userId, senderName, senderAddress1, senderAddress2, senderAddress3, senderPostcode, senderZone, senderPhone, senderArea, senderIC," +
						" receiverName, receiverAttn, receiverAddress1, receiverAddress2, receiverAddress3, receiverPostcode, receiverZone, receiverPhone, receiverArea, helps, tickItem, shipmentMethod, shipmentType, quantity, payMethod, creditArea, useLocale, verify, modifyDT)" +
						" VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())");
	
				pstmt.setString(1, data.get("userId") == null ? "" : data.get("userId").toString());
				pstmt.setString(2, data.get("senderName") == null ? "" : data.get("senderName").toString());
				pstmt.setString(3, data.get("senderAddress1") == null ? "" : data.get("senderAddress1").toString());
				pstmt.setString(4, data.get("senderAddress2") == null ? "" : data.get("senderAddress2").toString());
				pstmt.setString(5, data.get("senderAddress3") == null ? "" : data.get("senderAddress3").toString());
				pstmt.setString(6, data.get("senderPostcode") == null ? "" : data.get("senderPostcode").toString());
				pstmt.setInt(7, Integer.parseInt(data.get("senderZone") == null ? "0" : data.get("senderZone").equals("") ? "0" : data.get("senderZone").toString()));
				pstmt.setString(8, data.get("senderPhone") == null ? "" : data.get("senderPhone").toString());
				pstmt.setInt(9, Integer.parseInt(data.get("senderArea") == null ? "0" : data.get("senderArea").equals("") ? "0" : data.get("senderArea").toString()));
				pstmt.setString(10, data.get("senderIC") == null ? "" : data.get("senderIC").toString());
				pstmt.setString(11, data.get("receiverName") == null ? "" : data.get("receiverName").toString());
				pstmt.setString(12, data.get("receiverAttn") == null ? "" : data.get("receiverAttn").toString());
				pstmt.setString(13, data.get("receiverAddress1") == null ? "" : data.get("receiverAddress1").toString());
				pstmt.setString(14, data.get("receiverAddress2") == null ? "" : data.get("receiverAddress2").toString());
				pstmt.setString(15, data.get("receiverAddress3") == null ? "" : data.get("receiverAddress3").toString());
				pstmt.setString(16, data.get("receiverPostcode") == null ? "" : data.get("receiverPostcode").toString());
				pstmt.setInt(17, Integer.parseInt(data.get("receiverZone") == null ? "0" : data.get("receiverZone").equals("") ? "0" : data.get("receiverZone").toString()));
				pstmt.setString(18, data.get("receiverPhone") == null ? "" : data.get("receiverPhone").toString());
				pstmt.setInt(19, Integer.parseInt(data.get("receiverArea") == null ? "0" : data.get("receiverArea").equals("") ? "0" : data.get("receiverArea").toString()));
				pstmt.setString(20, data.get("helps") == null ? "" : data.get("helps").toString());
				pstmt.setString(21, data.get("tickItem") == null ? "" : data.get("tickItem").toString());
				pstmt.setInt(22, Integer.parseInt(data.get("shipmentMethod") == null ? "0" : data.get("shipmentMethod").toString()));
				pstmt.setInt(23, Integer.parseInt(data.get("shipmentType") == null ? "0" : data.get("shipmentType").toString()));
				pstmt.setInt(24, Integer.parseInt(data.get("quantity") == null ? "0" : data.get("quantity").toString()));
				pstmt.setInt(25, Integer.parseInt(data.get("payMethod") == null ? "0" : data.get("payMethod").toString()));
				pstmt.setInt(26, Integer.parseInt(data.get("creditArea") == null ? "0" : data.get("creditArea").toString()));
				pstmt.setString(27, data.get("useLocale") == null ? "" : data.get("useLocale").toString());
				pstmt.setString(28, data.get("verify") == null ? ConsignmentBusinessModel.generateVerifyCode() : data.get("verify").toString());
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
			
				result = "OK";
				
				activityLogger.info("- New Draft by '" + data.get("userId").toString() + "' @ " + UtilsBusinessModel.todayDate() + " " + UtilsBusinessModel.timeNow());
			
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.toString());
				result = ex.toString();
			}
			finally {
				if(pstmt != null){
					try{ pstmt.close(); } catch(Exception ex){}
					pstmt = null;
				}
				if(stmt != null){
					try{ stmt.close(); } catch(Exception ex){}
					stmt = null;
				}
				if(conn != null){
					ConnectionManager.closeConnection(conn);
					conn = null;
				}
				if(conn2 != null){
					ConnectionManager.closeConnection(conn2);
					conn2 = null;
				}
			}
		}

		return result;
	}
	
	/**
	 * 修改並儲存 draft
	 * 
	 * @param data
	 * @return
	 */
	protected static String updateDraft (HashMap<String, Object> data) {
		
		String sql = "";
		String result = "";
		Statement stmt = null;
		Connection conn = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "NO connection";
	        }

			stmt = conn.createStatement();	
			
			int senderArea = Integer.parseInt(data.get("senderArea") == null ? "0" : data.get("senderArea").equals("") ? "0" : data.get("senderArea").toString());
			int senderZone = Integer.parseInt(data.get("senderZone") == null ? "0" : data.get("senderZone").equals("") ? "0" : data.get("senderZone").toString());
			int receiverArea = Integer.parseInt(data.get("receiverArea") == null ? "0" : data.get("receiverArea").equals("") ? "0" : data.get("receiverArea").toString());
			int receiverZone = Integer.parseInt(data.get("receiverZone") == null ? "0" : data.get("receiverZone").equals("") ? "0" : data.get("receiverZone").toString());
			int creditArea = Integer.parseInt(data.get("creditArea") == null ? "0" : data.get("creditArea").equals("") ? "0" : data.get("creditArea").toString());
			
			sql = "UPDATE draft SET senderName='"+data.get("senderName").toString()+"', senderAddress1='"+data.get("senderAddress1").toString()+"'," +
					" senderAddress2='"+data.get("senderAddress2").toString()+"', senderAddress3='"+data.get("senderAddress3").toString()+"'," +
					" senderPostcode='"+data.get("senderPostcode").toString()+"', senderZone="+senderZone+"," +
					" senderPhone='"+data.get("senderPhone").toString()+"'," +
					" senderArea="+senderArea+", senderIC='"+data.get("senderIC").toString()+"'," +
					" receiverName='"+data.get("receiverName").toString()+"', receiverAttn='"+data.get("receiverAttn").toString()+"'," +
					" receiverAddress1='"+data.get("receiverAddress1").toString()+"', receiverAddress2='"+data.get("receiverAddress2").toString()+"'," +
					" receiverAddress3='"+data.get("receiverAddress3").toString()+"', receiverPostcode='"+data.get("receiverPostcode").toString()+"'," +
					" receiverZone="+receiverZone+", receiverPhone='"+data.get("receiverPhone").toString()+"'," +
					" helps='"+data.get("helps").toString()+"', tickItem='"+data.get("tickItem").toString()+"'," +
					" receiverArea="+receiverArea+", shipmentMethod="+data.get("shipmentMethod").toString()+"," +
					" shipmentType="+data.get("shipmentType").toString()+", quantity="+data.get("quantity").toString()+"," +
					" payMethod="+data.get("payMethod").toString()+", creditArea="+creditArea+"," +
					" useLocale='"+data.get("useLocale").toString()+"', modifyDT=NOW()" +
					" WHERE verify='" +data.get("verify").toString()+"' AND userId='"+data.get("userId").toString()+"'";
			
			stmt.executeUpdate(sql);
		
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
	 * 把 draft 標註 isDel = 1 (刪除的意思)
	 * 
	 * @param verify
	 * @param userId
	 * @return
	 */
	protected static String deleteDraft (String verify, String userId) {
		
		String sql = "";
		String result = "";
		Statement stmt = null;
		Connection conn = null;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return "NO connection";
	        }

			stmt = conn.createStatement();	
			
			sql = "UPDATE draft SET isDel=1, modifyDT=NOW()" +
					" WHERE verify='" +verify+"' AND userId='"+userId+"'";
			
			stmt.executeUpdate(sql);
		
			result = "OK";
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = ex.toString();
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
	 * 取得該使用者的 draft
	 * 
	 * @param userId
	 * @return
	 */
	protected static ArrayList<ConsignmentDataModel> draftList(String userId) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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

			String sqlCondition = " WHERE userId = '"+userId+"' AND isDel <> 1"; //不顯示已刪除
			
			sql = "SELECT a.*," +
			
			" (SELECT COUNT(did) FROM draft "+sqlCondition+") AS total FROM draft a" + sqlCondition +
			" ORDER BY a.modifyDT DESC";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("did").toString()));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea").toString()));
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setModifyDT(rs.getString("modifyDT").toString());
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
	 * 根據 verify 碼取回 draft 內容
	 * 
	 * @param userId
	 * @return
	 */
	protected static ArrayList<ConsignmentDataModel> getDraft (String verify) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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
			
			sql = "SELECT a.* FROM draft a WHERE a.verify = '"+verify+"' AND a.isDel <> 1";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("did").toString()));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea").toString()));
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setModifyDT(rs.getString("modifyDT").toString());
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
	 * 找出所有今天的 Manifest 供產生 PDF
	 * 
	 * @param fromStation
	 * @param toStation
	 * @param manifestDT
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> getTodayManifest(String fromStation, String toStation, String flightNum, String dispatchDT) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		String sql2 = "";
		Statement stmt2 = null;
		ResultSet rs2 = null;
		Connection conn = null;
		boolean isPosAviation = false;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			
			if(fromStation.trim().equals("") && toStation.trim().equals("")){
				isPosAviation = true;
			}
			
			String stage = "3"; // stage = 4: in transit; stage = 3: 已經load上車了的
			String sqlCondition = "";
			if(isPosAviation){
				sqlCondition = " WHERE dispatchDT = '"+dispatchDT+"' AND flightNum = '"+flightNum+"'"; 
			}else{ // Cargo Manifest/Manifest
				if(!flightNum.equals("")){ // is normal manifest / NOT Cargo Manifest
					stage = "4";
				}
				sqlCondition = " WHERE senderArea = "+fromStation+" AND receiverArea = "+toStation+" AND dispatchDT = '"+dispatchDT+"' AND flightNum = '"+flightNum+"' AND stage = "+stage; 
			}
			
			sql = "SELECT a.*, b.ename," +
					" (SELECT c.name_enUS FROM area c WHERE c.aid = a.receiverArea) AS receiverAreaName," +
					" (SELECT c.name_enUS FROM area c WHERE c.aid = a.senderArea) AS senderAreaName," +
					" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.receiverZone) AS receiverZoneName," +
//					" (SELECT d.name_enUS FROM zone d WHERE d.zid = a.senderZone) AS senderZoneName," +
					" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
					" FROM consignment a " +
					" LEFT JOIN partnerlist b ON b.pid = a.partnerId" +
 					sqlCondition +" ORDER BY a.createDT ASC";
			
    
		    rs = stmt.executeQuery(sql);
//		    logger.info(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	String conNo = rs.getString("consignmentNo");
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(conNo);
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
//		    	obj.setSenderZonename(rs.getString("senderZoneName") == null ? "" : rs.getString("senderZoneName"));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderAreaname(rs.getString("senderAreaName") == null ? "" : rs.getString("senderAreaName"));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverZonename(rs.getString("receiverZoneName") == null ? "" : rs.getString("receiverZoneName"));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setReceiverAreaname(rs.getString("receiverAreaName") == null ? "" : rs.getString("receiverAreaName"));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setFlightNum(rs.getString("flightNum") == null ? "" : rs.getString("flightNum"));
		    	obj.setAirwaybillNum(rs.getString("airwaybillNum") == null ? "" : rs.getString("airwaybillNum"));
		    	obj.setNatureGood(rs.getString("natureGood") == null ? "" : rs.getString("natureGood"));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEname(rs.getString("ename"));
		    	obj.setTotal(Integer.parseInt(rs.getString("total").toString()));
		    	
		    	String scannedSerials = "";
		    	sql2 = "SELECT consignmentNo, serial FROM log_stage3 WHERE consignmentNo = '"+rs.getString("consignmentNo")+"'"
		    			+ " GROUP BY serial";

			    rs2 = stmt2.executeQuery(sql2);
			    while(rs2.next()){
			    	scannedSerials += rs2.getString("serial") + ",";
			    }
		    	JSONObject serialJSON = new JSONObject();
		    	serialJSON.put(conNo, scannedSerials);
		    	
		    	obj.setSerials(serialJSON.toString());
		    	
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
	 * 根據司機 ID 找出所有今天的記錄供產生 RUNSHEET
	 * 
	 * @param area
	 * @param driver
	 * @param attendee1
	 * @param attendee2
	 * @param distributeDT
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> getTodayRunsheet(String area, String driver, String attendee1, String attendee2, String distributeDT) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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
			
			String sqlCondition = " WHERE receiverArea = "+area+" AND deliveryDriver = '"+driver+"' AND SUBSTRING(distributeDT, 1, 10) = '"+distributeDT+"' AND stage = 6"; //stage = 6: 司機scan過準備出去 distribute 了

			
			sql = "SELECT a.*, b.sid, b.ename, b.IC, c.cname AS partnerShortName," +
					" (SELECT ename FROM stafflist WHERE userId = '"+attendee1+"') AS attendee1," +
					" (SELECT IC FROM stafflist WHERE userId = '"+attendee1+"') AS attendee1IC," +
					" (SELECT ename FROM stafflist WHERE userId = '"+attendee2+"') AS attendee2," +
					" (SELECT IC FROM stafflist WHERE userId = '"+attendee2+"') AS attendee2IC," +
					" (SELECT COUNT(cid) FROM consignment "+sqlCondition+") AS total" +
					" FROM consignment a" +
					" LEFT JOIN stafflist b ON a.deliveryDriver = b.userId" +
					" LEFT JOIN partnerlist c ON a.partnerId = c.pid" +	sqlCondition +
					" ORDER BY a.createDT ASC";
    
		    rs = stmt.executeQuery(sql);
//		    logger.info(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setTotal(Integer.parseInt(rs.getString("total").toString()));
		    	obj.setSid(Integer.parseInt(rs.getString("sid").toString()));
		    	obj.setEname(rs.getString("ename"));
		    	obj.setIC(rs.getString("IC"));
		    	obj.setAttendee1(rs.getString("attendee1") == null ? "" : rs.getString("attendee1").toString());
		    	obj.setAttendee1IC(rs.getString("attendee1IC") == null ? "" : rs.getString("attendee1IC").toString());
		    	obj.setAttendee2(rs.getString("attendee2") == null ? "" : rs.getString("attendee2").toString());
		    	obj.setAttendee2IC(rs.getString("attendee2IC") == null ? "" : rs.getString("attendee2IC").toString());
		    	obj.setDriverID(driver);
		    	obj.setAttendee1ID(attendee1);
		    	obj.setAttendee2ID(attendee2);
		    	obj.setCname(rs.getString("partnerShortName") == null ? "" : rs.getString("partnerShortName").toString());
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
	 * 根據 runsheet 記錄產生報表
	 * 
	 * @param area
	 * @param driver
	 * @param distributeDT
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> getRunsheetRecord(int area, String driver, String distributeDT) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		String attendee1 = "";
		String attendee2 = "";
		boolean hasRunsheet = false;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return null;
	        }
			
			//先找到 attendee1 和 attendee2
			stmt = conn.createStatement();
			if(driver.equals("")) {
				sql = "SELECT * FROM runsheet WHERE area = "+area+" AND runsheetDT = '"+distributeDT+"' ORDER BY createDT DESC LIMIT 1";
			} else {
				sql = "SELECT * FROM runsheet WHERE area = "+area+" AND driver = '"+driver+"' AND runsheetDT = '"+distributeDT+"' ORDER BY createDT DESC LIMIT 1";
			}
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				attendee1 = rs.getString("attendee1") == null ? "" : rs.getString("attendee1").toString();
				attendee2 = rs.getString("attendee2") == null ? "" : rs.getString("attendee2").toString();
				hasRunsheet = true;
		    }
			
			if(!hasRunsheet) { //如果沒有找到對應的 runsheet 則返回
				return null;
			}
			

			//再來找所有送貨記錄
			if(stmt == null){
				stmt = conn.createStatement();
			}
			
			if(driver.equals("")) { //不指定driver，要全區的
				
				String sqlCondition = " WHERE receiverArea = "+area+" AND SUBSTRING(distributeDT, 1, 10) = '"+distributeDT+"'";

				
				sql = "SELECT a.*, CASE (a.deliveryDT) WHEN '' THEN 'na' ELSE a.deliveryDT END AS deliveryDT, b.cname AS partnerShortName," +
						" NULL AS driver," +
						" NULL AS attendee1," +
						" NULL AS attendee2" +
						" FROM consignment a" +
						" LEFT JOIN partnerlist b ON a.partnerId = b.pid" +
						sqlCondition+
						" ORDER BY a.deliveryDT ASC";
				
			} else {
				
				String sqlCondition = " WHERE receiverArea = "+area+" AND deliveryDriver = '"+driver+"' AND SUBSTRING(distributeDT, 1, 10) = '"+distributeDT+"'";

				
				sql = "SELECT a.*, CASE (a.deliveryDT) WHEN '' THEN 'na' ELSE a.deliveryDT END AS deliveryDT, b.cname AS partnerShortName," +
						" (SELECT ename FROM stafflist WHERE userId = '"+driver+"') AS driver," +
						" (SELECT ename FROM stafflist WHERE userId = '"+attendee1+"') AS attendee1," +
						" (SELECT ename FROM stafflist WHERE userId = '"+attendee2+"') AS attendee2" +
						" FROM consignment a" +
						" LEFT JOIN partnerlist b ON a.partnerId = b.pid" +
						sqlCondition+
						" ORDER BY a.deliveryDT ASC";
				
			}
			
			
			
    
		    rs = stmt.executeQuery(sql);
//		    logger.info(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setCname(rs.getString("partnerShortName") == null ? "" : rs.getString("partnerShortName").toString());
		    	obj.setDriver(rs.getString("driver") == null ? "" : rs.getString("driver").toString());
		    	obj.setAttendee1(rs.getString("attendee1") == null ? "n/a" : rs.getString("attendee1").toString());
		    	obj.setAttendee2(rs.getString("attendee2") == null ? "n/a" : rs.getString("attendee2").toString());
		    	
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
	 * 找出所有當天的 Third Party Consignment 的 status 供產生 PDF
	 * 
	 * @param createDT
	 * @param station
	 * @param partnerId
	 * @param stage
	 * @return
	 */
	public static ArrayList<ConsignmentDataModel> getPartnerReport(String createDT, int station, int partnerId, int stage) {

		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		ConsignmentDataModel obj = null;
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

			String sqlCondition = " WHERE SUBSTRING(a.createDT, 1, 10) = '"+createDT+"' AND partnerId = "+partnerId;
			
			if(station > 0) {
				sqlCondition += " AND receiverArea = "+station;
			}
			
			if(stage > 0) {
				sqlCondition += " AND stage = " + stage;
			}
			
			sql = "SELECT a.*, b.ename" +
					" FROM consignment a LEFT JOIN partnerlist b ON b.pid = a.partnerId" + sqlCondition +
					" ORDER BY a.createDT ASC";
			
		    rs = stmt.executeQuery(sql);
//		    logger.info(sql);
	
		    while(rs.next()){
		    	obj = new ConsignmentDataModel();
		    	
		    	obj.setCid(Integer.parseInt(rs.getString("cid").toString()));
		    	obj.setConsignmentNo(rs.getString("consignmentNo"));
		    	obj.setGeneralCargoNo(rs.getString("generalCargoNo"));
		    	obj.setUserId(rs.getString("userId"));
		    	obj.setPartnerId(Integer.parseInt(rs.getString("partnerId").toString()));
		    	obj.setSenderName(rs.getString("senderName"));
		    	obj.setSenderAddress1(rs.getString("senderAddress1"));
		    	obj.setSenderAddress2(rs.getString("senderAddress2"));
		    	obj.setSenderAddress3(rs.getString("senderAddress3"));
		    	obj.setSenderPostcode(rs.getString("senderPostcode"));
		    	obj.setSenderZone(Integer.parseInt(rs.getString("senderZone")));
		    	obj.setSenderPhone(rs.getString("senderPhone"));
		    	obj.setSenderArea(Integer.parseInt(rs.getString("senderArea")));
		    	obj.setSenderIC(rs.getString("senderIC"));
		    	obj.setReceiverName(rs.getString("receiverName"));
		    	obj.setReceiverAttn(rs.getString("receiverAttn"));
		    	obj.setReceiverAddress1(rs.getString("receiverAddress1"));
		    	obj.setReceiverAddress2(rs.getString("receiverAddress2"));
		    	obj.setReceiverAddress3(rs.getString("receiverAddress3"));
		    	obj.setReceiverPostcode(rs.getString("receiverPostcode"));
		    	obj.setReceiverZone(Integer.parseInt(rs.getString("receiverZone")));
		    	obj.setReceiverPhone(rs.getString("receiverPhone"));
		    	obj.setReceiverArea(Integer.parseInt(rs.getString("receiverArea")));
		    	obj.setHelps(rs.getString("helps") == null ? "" : rs.getString("helps"));
		    	obj.setTickItem(rs.getString("tickItem") == null ? "" : rs.getString("tickItem"));
				obj.setShipmentMethod(Integer.parseInt(rs.getString("shipmentMethod").toString()));
		    	obj.setShipmentType(Integer.parseInt(rs.getString("shipmentType").toString()));
//		    	obj.setFreightType(Integer.parseInt(rs.getString("freightType").toString()));
		    	obj.setPricing(Integer.parseInt(rs.getString("pricing").toString()));
		    	obj.setQuantity(Integer.parseInt(rs.getString("quantity").toString()));
		    	obj.setWeight(Double.parseDouble(rs.getString("weight").toString()));
		    	obj.setLength(Double.parseDouble(rs.getString("length").toString()));
		    	obj.setWidth(Double.parseDouble(rs.getString("width").toString()));
		    	obj.setHeight(Double.parseDouble(rs.getString("height").toString()));
		    	obj.setAmount(Double.parseDouble(rs.getString("amount").toString()));
		    	obj.setDeposit(Integer.parseInt(rs.getString("deposit").toString()));
		    	obj.setPayMethod(Integer.parseInt(rs.getString("payMethod").toString()));
		    	obj.setPayStatus(Integer.parseInt(rs.getString("payStatus").toString()));
		    	obj.setPayDT(rs.getString("payDT").toString());
		    	obj.setPayDeadline(rs.getString("payDeadline").toString());
		    	obj.setCreditArea(Integer.parseInt(rs.getString("creditArea") == null ? "0" : rs.getString("creditArea").toString()));
		    	obj.setStatus(Integer.parseInt(rs.getString("status").toString()));
		    	obj.setStage(Integer.parseInt(rs.getString("stage").toString()));
		    	obj.setPickupDriver(rs.getString("pickupDriver"));
		    	obj.setPickupDT(rs.getString("pickupDT"));
		    	obj.setDeliveryDriver(rs.getString("deliveryDriver"));
		    	obj.setDeliveryDT(rs.getString("deliveryDT"));
		    	obj.setRemarkEmail(rs.getString("remarkEmail") == null ? "" : rs.getString("remarkEmail").toString());
		    	obj.setRemarkNotify(rs.getString("remarkNotify") == null ? "" : rs.getString("remarkNotify").toString());
		    	obj.setRemark(rs.getString("remark") == null ? "" : rs.getString("remark").toString());
		    	obj.setStationRemark(rs.getString("stationRemark") == null ? "" : rs.getString("stationRemark").toString());
		    	obj.setTerms(rs.getString("terms").toString());
		    	obj.setUseLocale(rs.getString("useLocale").toString());
		    	obj.setVerify(rs.getString("verify").toString());
		    	obj.setCreateDT(rs.getString("createDT").toString());
		    	obj.setDispatchDT(rs.getString("dispatchDT").toString());
		    	obj.setDistributeDT(rs.getString("distributeDT").toString());
		    	obj.setCancelDT(rs.getString("cancelDT").toString());
		    	obj.setCancelBy(Integer.parseInt(rs.getString("cancelBy").toString()));
		    	obj.setReason(rs.getString("reason").toString());
		    	obj.setReasonPending(rs.getString("reasonPending").toString());
		    	obj.setReasonText(rs.getString("reasonText").toString());
		    	obj.setStaffCreate(Integer.parseInt(rs.getString("staffCreate") == null ? "-1" : rs.getString("staffCreate").toString()));
		    	obj.setEname(rs.getString("ename"));
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
	 * 檢查是否有這樣的 consignment number
	 * 
	 * @param consignmentNo
	 * @return
	 */
	protected static boolean isValidConsignmentNo(String consignmentNo) {

		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return false;
	        }

			stmt = conn.createStatement();

			sql = "SELECT consignmentNo FROM consignment WHERE consignmentNo = '"+consignmentNo+"'";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	result = true;
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
		
		return result;

	}
	
	
	/**
	 * 根據 generalCargo 的 CN 找到對應的 consignmentNo
	 * 
	 * @param cn
	 * @return
	 */
	public static String matchGeneralCargoNo(String cn) {

		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		String result = "";

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }

			stmt = conn.createStatement();

			sql = "SELECT consignmentNo FROM consignment WHERE generalCargoNo = '"+cn+"'";
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	result = rs.getString("consignmentNo") == null ? "" : rs.getString("consignmentNo").toString();
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
		
		return result;

	}
	
	
	
	/**
	 * 產生 Consignment 的 calendar
	 * 
	 * @param year
	 * @param month
	 * @param station
	 * @return
	 */
	public static String getConsignmentString4Calendar(String year, String month, int station) {

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
			
				
			sql = "SELECT * FROM consignment WHERE SUBSTRING(createDT, 1, 7) = '"+year+"-"+month+"' AND senderArea = " + station + " AND staffCreate = 0" +
					" ORDER BY createDT ASC";
		    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){

		    	String DT = rs.getString("createDT").toString().substring(8, 10);
		    	String consignmentNo = rs.getString("consignmentNo").toString();
		    	String status = rs.getString("status").toString();
		    	String generalCargoNo = rs.getString("generalCargoNo").equals("") ? "" : "(" +rs.getString("generalCargoNo") + ")";
		    	
		    	eventString += DT + "|" + status + "|" + consignmentNo + "|" + generalCargoNo + ";";
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
	 * Check if the the consignment belong to this agent
	 * 
	 * @param consignmentNo
	 * @param agentId
	 * @return
	 */
	protected static boolean accessibleConsignment(String consignmentNo, int agentId) {

		String sql = "";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;

		try {

			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return false;
	        }

			stmt = conn.createStatement();

			sql = "SELECT consignmentNo FROM consignment WHERE consignmentNo = '"+consignmentNo+"' AND agentId = " + agentId;
    
		    rs = stmt.executeQuery(sql);
	
		    while(rs.next()){
		    	result = true;
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
		
		return result;

	}
	
	
	/**
	 * 取得最大(最新)的cid
	 * @return
	 */
	public static int getMaxCid() {

		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		int maxCid = 0;

		try {
			
			//DB connection
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
				return 0;
	        }
			
			stmt = conn.createStatement();

		    sql = "SELECT MAX(cid)+1 AS cid FROM consignment";
		    rs = stmt.executeQuery(sql);

		    while(rs.next()){
		    	maxCid = Integer.parseInt(rs.getString("cid") == null ? "1" : rs.getString("cid").toString());
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

		return maxCid;
	}
	
	
	/**
     * 產生 Consignment Number
     * 
     * @return
     */
    public static String generateConsignmentNo(){
    	
    	final String alphabet = "345689ABCDEFGHJKLMNPQRTUVWXY";
    	String consignmentNo = "";
    	
    	StringBuffer tmp = new StringBuffer();
        for(int i = 0; i <  2; i++){
        	int number = (int)(Math.random()*26);
            char ch = alphabet.charAt(number);
            tmp.append(ch);
        }
        
        String serial = lpad(6, getMaxCid());
        
        consignmentNo = "A" + tmp + serial;

    	return consignmentNo.toString();
    }
    
    
    /**
     * 產生 Consignment Number（for batch upload）
     * 
     * @return
     */
    public static String fastGenerateConsignmentNo(int lastnum){
    	
    	final String alphabet = "345689ABCDEFGHJKLMNPQRTUVWXY";
    	String consignmentNo = "";
    	
    	StringBuffer tmp = new StringBuffer();
        for(int i = 0; i <  2; i++){
        	int number = (int)(Math.random()*26);
            char ch = alphabet.charAt(number);
            tmp.append(ch);
        }
        
        String serial = lpad(6, lastnum);
        
        consignmentNo = "A" + tmp + serial;

    	return consignmentNo.toString();
    }
	
    
    /**
     * 產生 Verify 碼
     * 
     * @return
     */
    public static String generateVerifyCode(){
    	
    	final String alphabet = "1234567890qazwsxedcrfvtgbyhnujmikolp";
    	
    	StringBuffer code = new StringBuffer();
        for(int i = 0; i <  10 ; i++){
        	int number = (int)(Math.random()*26);
            char ch = alphabet.charAt(number);
            code.append(ch);
        }

    	return code.toString();
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