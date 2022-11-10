package com.iposb.pricing;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.iposb.area.AreaBusinessModel;
import com.iposb.area.AreaDataModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.privilege.PrivilegeBusinessModel;
import com.iposb.utils.UtilsBusinessModel;

public class PricingController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	// Initialize global variables
	public static final String OBJECTDATA = "objectData";

	public static final String CHECKPRICE = "checkprice";
	public static final String CALCULATE = "calculate";
	
	public static final String CPPRICINGPARTNER = "cpPricing_partner"; // 導到清單頁
	public static final String CPPRICINGPARTNER_NEW = "cpPricing_partner_new"; // 導到空白頁
	public static final String CPPRICINGPARTNER_INSERT = "cpPricing_partner_insert"; // 寫入資料庫
	public static final String CPPRICINGPARTNER_EDIT = "cpPricing_partner_edit"; // 導到編輯頁
	public static final String CPPRICINGPARTNER_UPDATE = "cpPricing_partner_update"; // 更新資料庫
	
	public static final String CPPRICINGNORMALPARCEL = "cpPricing_normal_parcel"; // 導到清單頁
	public static final String CPPRICINGNORMALPARCEL_NEW = "cpPricing_normal_parcel_new"; // 導到空白頁
	public static final String CPPRICINGNORMALPARCEL_INSERT = "cpPricing_normal_parcel_insert"; // 寫入資料庫
	public static final String CPPRICINGNORMALPARCEL_EDIT = "cpPricing_normal_parcel_edit"; // 導到編輯頁
	public static final String CPPRICINGNORMALPARCEL_UPDATE = "cpPricing_normal_parcel_update"; // 更新資料庫

	public static final String CPPRICINGNORMALDOCUMENT = "cpPricing_normal_document"; // 導到清單頁
	public static final String CPPRICINGNORMALDOCUMENT_EDIT = "cpPricing_normal_document_edit"; // 導到編輯頁
	public static final String CPPRICINGNORMALDOCUMENT_UPDATE = "cpPricing_normal_document_update"; // 更新資料庫
	
	public static final String CPPRICINGCREDITPARCEL = "cpPricing_credit_parcel"; // 導到清單頁
	public static final String CPPRICINGCREDITPARCEL_NEW = "cpPricing_credit_parcel_new"; // 導到空白頁
	public static final String CPPRICINGCREDITPARCEL_INSERT = "cpPricing_credit_parcel_insert"; // 寫入資料庫
	public static final String CPPRICINGCREDITPARCEL_EDIT = "cpPricing_credit_parcel_edit"; // 導到編輯頁
	public static final String CPPRICINGCREDITPARCEL_UPDATE = "cpPricing_credit_parcel_update"; // 更新資料庫
	
	public static final String CPPRICINGCREDITDOCUMENT = "cpPricing_credit_document"; // 導到清單頁
	public static final String CPPRICINGCREDITDOCUMENT_EDIT = "cpPricing_credit_document_edit"; // 導到編輯頁
	public static final String CPPRICINGCREDITDOCUMENT_UPDATE = "cpPricing_credit_document_update"; // 更新資料庫


	private static String RESULT = "";

	static Logger logger = Logger.getLogger(PricingController.class);
	static DecimalFormat FORMAT = new DecimalFormat("#,###,###.00");

	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		return;
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding("UTF-8");

		String userId = "";
		int privilege = -9;
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
			userId = currentUser.getPrincipal().toString();

			if (currentUser.hasRole("99")) {
				privilege = 99;
			} else if (currentUser.hasRole("9")) {
				privilege = 9;
			} else if (currentUser.hasRole("8")) {
				privilege = 8;
			} else if (currentUser.hasRole("7")) {
				privilege = 7;
			} else if (currentUser.hasRole("6")) {
				privilege = 6;
			} else if (currentUser.hasRole("5")) {
				privilege = 5;
			} else if (currentUser.hasRole("4")) {
				privilege = 4;
			} else if (currentUser.hasRole("3")) {
				privilege = 3;
			} else if (currentUser.hasRole("2")) {
				privilege = 2;
			} else if (currentUser.hasRole("1")) {
				privilege = 1;
			} else if (currentUser.hasRole("0")) {
				privilege = 0;
			}
		}

		PrintWriter out = response.getWriter();
		String actionType = request.getParameter("actionType");

		if (actionType == null || actionType.equals("")) {

			// 防止user亂輸入頁碼
			int pageNo = 0;
			String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
			boolean isNum = isNumeric(page);

			if (isNum) {
				pageNo = Integer.parseInt(page);
			}
			if (!(pageNo > 0) || !(isNum)) {
				pageNo = 1;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.searchPricing_partner("category", pageNo);

			if (data != null && !data.isEmpty()) {
				request.setAttribute(OBJECTDATA, data);
			}

			logger.info("---User is browsing Pricing of Partner (" + UtilsBusinessModel.timeNow() + ")");

			String url = "./pricinginfo.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		else if (actionType.equals(CHECKPRICE) == true) {

			//Area List
	        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	        area = AreaBusinessModel.areaList();
	        request.setAttribute("area", area);
	        
			String url = "./price.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		else if (actionType.equals(CALCULATE) == true) {
			
			String senderArea = request.getParameter("senderArea") == null ? "" : request.getParameter("senderArea").toString();
			String receiverArea = request.getParameter("receiverArea") == null ? "" : request.getParameter("receiverArea").toString();
			int shipmentType = Integer.parseInt(request.getParameter("shipmentType") == null ? "1" : request.getParameter("shipmentType").toString().trim().equals("") ? "1" : request.getParameter("shipmentType").toString());
			double weight = Double.parseDouble(request.getParameter("weight") == null ? "0" : request.getParameter("weight").toString().trim().equals("") ? "0" : request.getParameter("weight").toString());
			int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "1" : request.getParameter("quantity").toString().trim().equals("") ? "1" : request.getParameter("quantity").toString());
			String memberType = request.getParameter("memberType") == null ? "normal" : request.getParameter("memberType").equals("") ? "normal" : request.getParameter("memberType").toString();
			String accNo = request.getParameter("accNo") == null ? "" : request.getParameter("accNo").toString();
	        
			String result = "";
			
			if(memberType.equals("normal")) {
				
				if(shipmentType == 1) { //document
					result = PricingBusinessModel.calculate_normal_document(senderArea, receiverArea, weight, 0, 0);
				} else if(shipmentType == 2) { //parcel
					result = PricingBusinessModel.calculate_normal_parcel(senderArea, receiverArea, weight, 0, 0, quantity);
				}
				
			} else if(memberType.equals("credit")) {
				
				String discPack = "";
				
				if(accNo.trim().length() > 0) { //有 accNo
					discPack = LogonBusinessModel.checkDiscPack(accNo, "accNo");
				} else if (userId.length() > 0) { //如果有登入
					discPack = LogonBusinessModel.checkDiscPack(userId, "userId");
				}
				
				if(shipmentType == 1) { //document
					result = PricingBusinessModel.calculate_credit_document(senderArea, receiverArea, weight, 0, 0, discPack);
				} else if(shipmentType == 2) { //parcel
					result = PricingBusinessModel.calculate_credit_parcel(senderArea, receiverArea, weight, 0, 0, quantity, discPack);
				}
				
			}
			

			out.print(result);
			
		}

		else if (actionType.equals(CPPRICINGPARTNER_NEW) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			String url = "./cp/cpPricing_partner.jsp?actionType=cpPricing_partner_insert";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGPARTNER_INSERT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.addNewPricing_partner(data, userId);
			}

			String pid = String.valueOf(PricingBusinessModel.getMaxPid_partner());

			if (returnValue.equals("OK")) { // 成功
				result = "insertSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./pricing?actionType=cpPricing_partner_edit&pid=" + pid;
			response.sendRedirect(url);
			return;
		}

		else if (actionType.equals(CPPRICINGPARTNER_EDIT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			String pid = request.getParameter("pid");

			String url = "./cp/cpPricing_partner.jsp?actionType=cpPricing_partner";
			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();

			if (isNumeric(pid)) {
				data = PricingBusinessModel.pricingDetails_partner(pid);
			}

			if (data != null && !data.isEmpty()) {

				// 如果RESULT有值代表儲存DB後再進入這裡
				if (!RESULT.equals("")) {
					PricingDataModel obj = (PricingDataModel) data.get(0);
					obj.setErrmsg(RESULT); // 加入訊息的標籤
					data.set(0, obj); // 再設入
					RESULT = ""; // 清除掉記錄
				}

				request.setAttribute(OBJECTDATA, data);
				url = "./cp/cpPricing_partner.jsp?actionType=cpPricing_partner_update";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGPARTNER_UPDATE) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.updatePricing_partner(data, userId); // 寫入資料庫
			}

			// 重新查詢最新的資料
			String pid = request.getParameter("pid");

			if (returnValue.equals("OK")) { // 成功
				result = "updateSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./pricing?actionType=cpPricing_partner_edit&pid=" + pid;
			response.sendRedirect(url);
			return;
		}

		else if (actionType.equals(CPPRICINGPARTNER) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.pricingList_partner("pid", 0);

			if (data != null && !data.isEmpty()) {
				request.setAttribute(OBJECTDATA, data);
			}

			String url = "./cp/cpPricing_partner.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		
		/************************ Normal User (Parcel) **************************/
		
		else if (actionType.equals(CPPRICINGNORMALPARCEL_NEW) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}
			
			//Area List
	        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	        area = AreaBusinessModel.areaList();
	        request.setAttribute("area", area);

			String url = "./cp/cpPricing_normal_parcel.jsp?actionType=cpPricing_normal_parcel_insert";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGNORMALPARCEL_INSERT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.addNewPricing_normal_parcel(data, userId);
			}

			if (returnValue.equals("OK")) { // 成功
				result = "insertSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./cpPricing_normal_parcel";
			response.sendRedirect(url);
			return;
		}

		else if (actionType.equals(CPPRICINGNORMALPARCEL_EDIT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			String pid = request.getParameter("pid");

			String url = "./cp/cpPricing_normal_parcel.jsp?actionType=cpPricing_normal_parcel";
			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();

			if (isNumeric(pid)) {
				data = PricingBusinessModel.pricingDetails_normal_parcel(pid);
			}

			if (data != null && !data.isEmpty()) {

				// 如果RESULT有值代表儲存DB後再進入這裡
				if (!RESULT.equals("")) {
					PricingDataModel obj = (PricingDataModel) data.get(0);
					obj.setErrmsg(RESULT); // 加入訊息的標籤
					data.set(0, obj); // 再設入
					RESULT = ""; // 清除掉記錄
				}

				request.setAttribute(OBJECTDATA, data);
				
				//Area List
		        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		        area = AreaBusinessModel.areaList();
		        request.setAttribute("area", area);
		        
				url = "./cp/cpPricing_normal_parcel.jsp?actionType=cpPricing_normal_parcel_update";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGNORMALPARCEL_UPDATE) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.updatePricing_normal_parcel(data, userId); // 寫入資料庫
			}

			// 重新查詢最新的資料
			String pid = request.getParameter("pid");

			if (returnValue.equals("OK")) { // 成功
				result = "updateSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./pricing?actionType=cpPricing_normal_parcel_edit&pid=" + pid;
			response.sendRedirect(url);
			return;
		}
		
		else if (actionType.equals(CPPRICINGNORMALPARCEL) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.pricingList_normal_parcel();
			
			//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	PricingDataModel obj = (PricingDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	            
			request.setAttribute(OBJECTDATA, data);

			String url = "./cp/cpPricing_normal_parcel.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
				
		
		/************************ Normal User (Document) **************************/
		
		else if (actionType.equals(CPPRICINGNORMALDOCUMENT_EDIT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			String pid = request.getParameter("pid");

			String url = "./cp/cpPricing_normal_document.jsp?actionType=cpPricing_normal_document";
			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();

			if (isNumeric(pid)) {
				data = PricingBusinessModel.pricingDetails_normal_document(pid);
			}

			if (data != null && !data.isEmpty()) {

				// 如果RESULT有值代表儲存DB後再進入這裡
				if (!RESULT.equals("")) {
					PricingDataModel obj = (PricingDataModel) data.get(0);
					obj.setErrmsg(RESULT); // 加入訊息的標籤
					data.set(0, obj); // 再設入
					RESULT = ""; // 清除掉記錄
				}

				request.setAttribute(OBJECTDATA, data);

				url = "./cp/cpPricing_normal_document.jsp?actionType=cpPricing_normal_document_update";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGNORMALDOCUMENT_UPDATE) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.updatePricing_normal_document(data, userId); // 寫入資料庫
			}

			// 重新查詢最新的資料
			String pid = request.getParameter("pid");

			if (returnValue.equals("OK")) { // 成功
				result = "updateSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./pricing?actionType=cpPricing_normal_document_edit&pid=" + pid;
			response.sendRedirect(url);
			return;
		}
		
		else if (actionType.equals(CPPRICINGNORMALDOCUMENT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.pricingList_normal_document();
			
			//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	PricingDataModel obj = (PricingDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	            
			request.setAttribute(OBJECTDATA, data);

			String url = "./cp/cpPricing_normal_document.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		

		
		/************************ Credit User (Parcel) **************************/
		
		else if (actionType.equals(CPPRICINGCREDITPARCEL_NEW) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}
			
			//Area List
	        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	        area = AreaBusinessModel.areaList();
	        request.setAttribute("area", area);

			String url = "./cp/cpPricing_credit_parcel.jsp?actionType=cpPricing_credit_parcel_insert";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGCREDITPARCEL_INSERT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.addNewPricing_credit_parcel(data, userId);
			}

			if (returnValue.equals("OK")) { // 成功
				result = "insertSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./cpPricing_credit_parcel";
			response.sendRedirect(url);
			return;
		}

		else if (actionType.equals(CPPRICINGCREDITPARCEL_EDIT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			String pid = request.getParameter("pid");

			String url = "./cp/cpPricing_credit_parcel.jsp?actionType=cpPricing_credit_parcel";
			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();

			if (isNumeric(pid)) {
				data = PricingBusinessModel.pricingDetails_credit_parcel(pid);
			}

			if (data != null && !data.isEmpty()) {

				// 如果RESULT有值代表儲存DB後再進入這裡
				if (!RESULT.equals("")) {
					PricingDataModel obj = (PricingDataModel) data.get(0);
					obj.setErrmsg(RESULT); // 加入訊息的標籤
					data.set(0, obj); // 再設入
					RESULT = ""; // 清除掉記錄
				}

				request.setAttribute(OBJECTDATA, data);
				
				//Area List
		        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		        area = AreaBusinessModel.areaList();
		        request.setAttribute("area", area);
		        
				url = "./cp/cpPricing_credit_parcel.jsp?actionType=cpPricing_credit_parcel_update";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGCREDITPARCEL_UPDATE) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.updatePricing_credit_parcel(data, userId); // 寫入資料庫
			}

			// 重新查詢最新的資料
			String pid = request.getParameter("pid");

			if (returnValue.equals("OK")) { // 成功
				result = "updateSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./pricing?actionType=cpPricing_credit_parcel_edit&pid=" + pid;
			response.sendRedirect(url);
			return;
		}
		
		else if (actionType.equals(CPPRICINGCREDITPARCEL) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.pricingList_credit_parcel();
			
			//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	PricingDataModel obj = (PricingDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	            
			request.setAttribute(OBJECTDATA, data);

			String url = "./cp/cpPricing_credit_parcel.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		
		/************************ Credit Term User (Document) **************************/
		
		else if (actionType.equals(CPPRICINGCREDITDOCUMENT_EDIT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			String pid = request.getParameter("pid");

			String url = "./cp/cpPricing_credit_document.jsp?actionType=cpPricing_credit_document";
			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();

			if (isNumeric(pid)) {
				data = PricingBusinessModel.pricingDetails_credit_document(pid);
			}

			if (data != null && !data.isEmpty()) {

				// 如果RESULT有值代表儲存DB後再進入這裡
				if (!RESULT.equals("")) {
					PricingDataModel obj = (PricingDataModel) data.get(0);
					obj.setErrmsg(RESULT); // 加入訊息的標籤
					data.set(0, obj); // 再設入
					RESULT = ""; // 清除掉記錄
				}

				request.setAttribute(OBJECTDATA, data);

				url = "./cp/cpPricing_credit_document.jsp?actionType=cpPricing_credit_document_update";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPPRICINGCREDITDOCUMENT_UPDATE) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.setRequestData(request);

			String returnValue = "";
			String result = "";
			if(userId.length() > 0) {
				returnValue = PricingBusinessModel.updatePricing_credit_document(data, userId); // 寫入資料庫
			}

			// 重新查詢最新的資料
			String pid = request.getParameter("pid");

			if (returnValue.equals("OK")) { // 成功
				result = "updateSuccess";
			}
			else if (returnValue.equals("")) { // 沒有連線
				result = "noConnection";
			}
			else { // 失敗
				result = returnValue;
			}

			RESULT = ""; // 先清除
			RESULT = result;

			String url = "./pricing?actionType=cpPricing_credit_document_edit&pid=" + pid;
			response.sendRedirect(url);
			return;
		}
		
		else if (actionType.equals(CPPRICINGCREDITDOCUMENT) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<PricingDataModel> data = new ArrayList<PricingDataModel>();
			data = PricingBusinessModel.pricingList_credit_document();
			
			//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	PricingDataModel obj = (PricingDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	            
			request.setAttribute(OBJECTDATA, data);

			String url = "./cp/cpPricing_credit_document.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		
		
	
		

	}

	// 檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
		int iid = 20; // 必須自行手動設定，對應DB的privilege
		String url = "./stafflogin?returnUrl=./cpPricing";

		if (PrivilegeBusinessModel.determinePrivilege(iid, privilege)) {
			return true;
		}else {
			response.sendRedirect(url);
			return false;
		}
	}

	// 以正規表達式判斷輸入的字串是否為數字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	// Clean up resources
	public void destroy() {
	}
}