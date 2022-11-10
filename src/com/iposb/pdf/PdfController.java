package com.iposb.pdf;

/**
 * Source From : https://www.85flash.com/Get/wangyebiancheng/JSP/2005-9-8/059816571318626.htm
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.consignment.ConsignmentDataModel;
import com.iposb.log.LogBusinessModel;
import com.iposb.resource.ResourceBusinessModel;

public class PdfController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	static Logger logger = Logger.getLogger(PdfController.class);

	// Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String CONSIGNMENTNOTE = "consignmentNote";
	public static final String DELETECONSIGNMENTNOTE = "deleteConsignmentNote";
	public static final String CONSIGNMENTNOTESTICKER = "consignmentNoteSticker";
	public static final String PARTNERSTICKER = "partnerSticker";
	public static final String MANIFEST = "manifest";
	public static final String POSAVIATION = "posAviation";
	public static final String RUNSHEET = "runsheet";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		return;
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

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

		String actionType = request.getParameter("actionType");
		// String locale = request.getParameter("lang");

		if (actionType == null || actionType.equals("")) {

			String url = "./index.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}
		

		else if (actionType.equals(CONSIGNMENTNOTE) == true) {

			// 產生PDF(ConsignmentController和UploadController也有一個actionType,寫法和這裡一樣,如修改以下程式必須也要修改那裡)
			String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";

			if (consignmentNo.length() == 9) {

				consignmentData = ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo); // 重新查詢

				if (consignmentData != null && consignmentData.size() == 1) { // 必須只有1筆
					result = PdfBusinessModel.generateConsignmentNote(request, consignmentData); // 產生PDF
					
					//記錄管理員在處理訂單的過程
					LogBusinessModel.insertCPConsignmentLog(consignmentNo, 99, userId, "Generate Consignment Note");
				}
			}

			out.print(result);
		}
		
		
		else if (actionType.equals(DELETECONSIGNMENTNOTE) == true) {

			String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
			boolean success = false;

			String filename = "pdf/"+consignmentNo+"-IPOSB.pdf";
	    	success = ResourceBusinessModel.deleteFile(filename);
	    	
	    	//記錄管理員在處理訂單的過程
			LogBusinessModel.insertCPConsignmentLog(consignmentNo, 99, userId, "Delete Consignment Note");
			
			if(success){
				out.print("deleted success");
			} else {
				out.print("deleted failed");
			}

		}
		

		else if (actionType.equals(CONSIGNMENTNOTESTICKER) == true) {

			String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";

			if (consignmentNo.length() == 9) {

				consignmentData = ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo); // 重新查詢

				if (consignmentData != null && consignmentData.size() == 1) { // 必須只有1筆
					result = PdfBusinessModel.generateSticker(request, consignmentData); // 產生PDF
					
					//記錄管理員在處理訂單的過程
					LogBusinessModel.insertCPConsignmentLog(consignmentNo, 99, userId, "Generate Sticker");
				}
			}

			out.print(result);
		}

		
		else if (actionType.equals(PARTNERSTICKER) == true) {

			String generalCargoNo = request.getParameter("generalCargoNo") == null ? "" : request.getParameter("generalCargoNo").toString();
			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";

			if (generalCargoNo.length() > 0) {

				consignmentData = ConsignmentBusinessModel.getRecordByGeneralCargoNo(generalCargoNo); // 重新查詢
				result = PdfBusinessModel.generatePartnerSticker(request, consignmentData); // 產生PDF
			}

			out.print(result);
		}

		
		else if (actionType.equals(MANIFEST) == true) {

			String fromStation = request.getParameter("fromStation") == null ? "" : request.getParameter("fromStation").toString();
			String toStation = request.getParameter("toStation") == null ? "" : request.getParameter("toStation").toString();
			String flightNum = request.getParameter("flightNum") == null ? "" : request.getParameter("flightNum").toString();
			String dispatchDT = request.getParameter("dispatchDT") == null ? "" : request.getParameter("dispatchDT").toString();
			
			boolean isCargoManifest = !flightNum.equals("");

			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";
			
			consignmentData = ConsignmentBusinessModel.getTodayManifest(fromStation, toStation, flightNum, dispatchDT);
			
			if (consignmentData != null && consignmentData.size() > 0) {
				result = PdfBusinessModel.generateManifest(request, consignmentData, userId, isCargoManifest); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}

		else if (actionType.equals(POSAVIATION) == true) {

			String flightNum = request.getParameter("flightNum") == null ? "" : request.getParameter("flightNum").toString();
			String dispatchDT = request.getParameter("dispatchDT") == null ? "" : request.getParameter("dispatchDT").toString();

			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";
			
			consignmentData = ConsignmentBusinessModel.getTodayManifest("", "", flightNum, dispatchDT);
			
			if (consignmentData != null && consignmentData.size() > 0) {
				result = PdfBusinessModel.generatePosAviationManifest(request, consignmentData, userId); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}
		
		else if (actionType.equals(RUNSHEET) == true) {

			String area = request.getParameter("area") == null ? "" : request.getParameter("area").toString();
			String driver = request.getParameter("driver") == null ? "" : request.getParameter("driver").toString();
			String attendee1 = request.getParameter("attendee1") == null ? "" : request.getParameter("attendee1").toString();
			String attendee2 = request.getParameter("attendee2") == null ? "" : request.getParameter("attendee2").toString();
			String distributeDT = request.getParameter("distributeDT") == null ? "" : request.getParameter("distributeDT").toString();

			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";

			consignmentData = ConsignmentBusinessModel.getTodayRunsheet(area, driver, attendee1, attendee2, distributeDT);

			if (consignmentData != null && consignmentData.size() > 0) {
				result = PdfBusinessModel.generateRunsheet(request, consignmentData, userId); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}
		
		

	}

}
