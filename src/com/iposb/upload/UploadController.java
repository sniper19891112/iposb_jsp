package com.iposb.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.consignment.ConsignmentDataModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.pdf.PdfBusinessModel;

public class UploadController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	// Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String OBJECTDATA2 = "objectData2"; // driver
	public static final String OBJECTDATA3 = "objectData3"; // destination

	public static final String GENERALCARGOAMBER = "generalcargoAmber";
	public static final String GENERALCARGOFSK = "generalcargoFSK";
	public static final String ACCOUNTSLIP = "accountSlip";

	static Logger logger = Logger.getLogger(UploadController.class);

	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		return;
	}

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

		if (actionType == null) {

			long time = System.currentTimeMillis();
			boolean isSuccess = false;
			String actionUpload = request.getParameter("actionUpload") == null ? "" : request.getParameter("actionUpload").toString();
			String stage = request.getParameter("stage") == null ? "0" : request.getParameter("stage").toString();
			String path = request.getParameter("path") == null ? "" : request.getParameter("path").toString();
			int id = Integer.parseInt(request.getParameter("id") == null ? "0" : request.getParameter("id").toString()); // mid/pid/sid
			String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
			
			/*if(!path.trim().equals("")){
				logger.info("**** Path: "+ path);
				out.print("uploadSuccess"); out.flush(); out.close();
				return;
			}*/
			
			
			//測試 scan 的是否為 generalCargo 的 CN
			String matchConsignmentNo = ConsignmentBusinessModel.matchGeneralCargoNo(consignmentNo);
		
			String cn = "";
			if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
				cn = matchConsignmentNo;
			} else {
				if(consignmentNo.replaceAll("\\s","").length() > 9) { //consignmentNo + serial
					cn = consignmentNo.replaceAll("\\s","").substring(0, 9);
				} else {
					cn = consignmentNo;
				}
			}
			
			
			if (!ServletFileUpload.isMultipartContent(request)) {
				logger.error("---Error Not \"multipart/form-data\" Be Careful Hacker");
				throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
			}

			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = null;
			String tempPath = request.getSession().getServletContext().getRealPath("/")	+ "temp/";

			try {
				items = uploadHandler.parseRequest(request);
				isSuccess = UploadBusinessModel.uploadAppPics(items, tempPath, path, id, actionUpload, time, stage, cn);
				
				if(isSuccess) { 
					if(actionUpload.equals("avatar")){
						int result = LogonBusinessModel.setModifyDT(userId, privilege);
						
						if(result==1){
							currentUser.getSession().setAttribute("modifyDT",
									!LogonBusinessModel.getDataById("modifyDT", "", userId).equals("") ?
											LogonBusinessModel.getDataById("modifyDT", "", userId).trim().replace(" ", "_") : ""
									);
						}
					}else if(Integer.parseInt(stage) == 7){ //delivered了，就要發送email通知sender，並附上圖片
						ConsignmentBusinessModel.sendParcelReachedEmail(cn, request);
					}
				}
				
			} catch (FileUploadException e) {
				e.printStackTrace();
				logger.error("*** UploadController \"取不到temp裡圖檔\" " + e.toString() + " " + new Date());
			}

			

			if (isSuccess) {
				out.print("uploadSuccess");
			} else {
				out.print("uploadFailed");
			}
			out.flush();
			out.close();
			return;

		}

		else if (actionType.equals(GENERALCARGOAMBER) == true) {

			String result = "";
			String url = "./cp/cpStation.jsp?result=amberuploadfailed"; //預設為失敗

			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();

			data = UploadBusinessModel.parseAmberExcel(request);

			if (data != null && !data.isEmpty()) {
				
				result = ConsignmentBusinessModel.batchInsertConsignment(data, userId);// 寫入資料庫

				if(result.substring(0, 2).equals("OK")) {
						String lastuploadedCN = result.substring(3, result.length());
						ArrayList<ConsignmentDataModel> alldata = new ArrayList<ConsignmentDataModel>();
						alldata = ConsignmentBusinessModel.lastuploaded(lastuploadedCN); // 查詢剛上傳的資料
		
						// 每個 generalCargoNo 各產生 一個stickers，全部放到同一個 pdf 檔案裡
						String filename = PdfBusinessModel.generatePartnerSticker(request, alldata); // 產生PDF
		
						ConsignmentDataModel obj = (ConsignmentDataModel) alldata.get(0);
						obj.setVerify(filename); // 暫借 verify 來放檔名
						alldata.set(0, obj); // 再設入
						request.setAttribute(OBJECTDATA, alldata);
		
						url = "./cp/cpStation.jsp?result=amberuploadsuccess";
		
					}
				
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}
		
		//OLD
		/*
		else if (actionType.equals(GENERALCARGOAMBER_OLD) == true) {

			String result = "";
			String lastuploadedCN = "";
			String url = "./cp/cpStation.jsp";

			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
			ConsignmentDataModel newData = new ConsignmentDataModel();

			data = UploadBusinessModel.parseAmberExcel(request);

			if (data != null && !data.isEmpty()) {

				for (int i = 0; i < data.size(); i++) {

					newData = (ConsignmentDataModel) data.get(i);

					HashMap<String, Object> consignmentData = new HashMap<String, Object>();
					consignmentData.put("userId", loginCookie == null ? "" : loginCookie.getValue().toString());
					consignmentData.put("partnerId", "1"); // Amber
					consignmentData.put("senderName", newData.getSenderName());
					consignmentData.put("generalCargoNo", newData.getGeneralCargoNo());
					consignmentData.put("senderAddress1", newData.getSenderAddress1());
					consignmentData.put("senderAddress2", newData.getSenderAddress2());
					consignmentData.put("senderAddress3", newData.getSenderAddress3());
					consignmentData.put("senderPhone", newData.getSenderPhone());
					consignmentData.put("senderArea", "4"); // KUL
					consignmentData.put("senderIC", newData.getSenderIC());
					consignmentData.put("receiverName", newData.getReceiverName());
					consignmentData.put("receiverAttn", newData.getReceiverAttn());
					consignmentData.put("receiverAddress1", newData.getReceiverAddress1());
					consignmentData.put("receiverAddress2", newData.getReceiverAddress2());
					consignmentData.put("receiverAddress3", newData.getReceiverAddress3());
					consignmentData.put("receiverPostcode", newData.getReceiverPostcode());
					consignmentData.put("receiverZone", newData.getReceiverZone());
					consignmentData.put("receiverPhone", newData.getReceiverPhone());
					consignmentData.put("receiverArea", newData.getReceiverArea());
					consignmentData.put("helps", newData.getHelps());
					consignmentData.put("shipmentType", newData.getShipmentType());
					consignmentData.put("quantity", newData.getQuantity());
					consignmentData.put("weight", newData.getWeight());
					consignmentData.put("length", newData.getLength());
					consignmentData.put("width", newData.getWidth());
					consignmentData.put("height", newData.getHeight());
					consignmentData.put("amount", newData.getAmount());
					consignmentData.put("payMethod", "6"); // freight charges
					consignmentData.put("stage", "4"); //In transit
					consignmentData.put("terms", newData.getTerms());
					consignmentData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
					consignmentData.put("staffCreate", "1");// 1: 由員工產生
					consignmentData.put("howtocreate", "Upload Excel");
					String consignmentNo = ConsignmentBusinessModel.generateConsignmentNo();
					result = ConsignmentBusinessModel.insertConsignment(consignmentData, consignmentNo);// 寫入資料庫

					if (i == data.size() - 1) { // 最後一筆
						lastuploadedCN += "'" + consignmentNo + "'";
					} else {
						lastuploadedCN += "'" + consignmentNo + "',";
					}

					if (result.equals("OK")) { // 成功

						// 產生PDF(PdfController和ConsignmentController也有一個actionType,寫法和這裡一樣,如修改以下程式必須也要修改那裡)
						// ArrayList<ConsignmentDataModel> nData = new
						// ArrayList<ConsignmentDataModel>();
						// nData =
						// ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo);
						// //重新查詢
						//
						// PdfBusinessModel.generateConsignmentNote(request,
						// nData); //產生PDF

						try {
							logger.info(">>> Sleep 0.5s: " + UtilsBusinessModel.timeNow());
							Thread.sleep(500); // 先等待0.5秒鐘再進行下一個
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}

				}

				ArrayList<ConsignmentDataModel> alldata = new ArrayList<ConsignmentDataModel>();
				alldata = ConsignmentBusinessModel.lastuploaded(lastuploadedCN); // 查詢剛上傳的資料

				// 每個 generalCargoNo 各產生 一個stickers，全部放到同一個 pdf 檔案裡
				String filename = PdfBusinessModel.generatePartnerSticker(request, alldata); // 產生PDF

				ConsignmentDataModel obj = (ConsignmentDataModel) alldata.get(0);
				obj.setVerify(filename); // 暫借 verify 來放檔名
				alldata.set(0, obj); // 再設入
				request.setAttribute(OBJECTDATA, alldata);

				url = "./cp/cpStation.jsp?result=amberuploadsuccess";

			} else {
				url = "./cp/cpStation.jsp?result=amberuploadfailed";
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}
		*/

		else if (actionType.equals(GENERALCARGOFSK) == true) {

			String result = "";
			String lastuploadedCN = "";
			String url = "./cp/cpStation.jsp";

			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
			ConsignmentDataModel newData = new ConsignmentDataModel();

			data = UploadBusinessModel.parseFSKExcel(request);

			if (data != null && !data.isEmpty()) {

				for (int i = 0; i < data.size(); i++) {

					newData = (ConsignmentDataModel) data.get(i);

					HashMap<String, Object> consignmentData = new HashMap<String, Object>();
					consignmentData.put("userId", userId);
					consignmentData.put("partnerId", "2"); // FSK
					consignmentData.put("senderName", newData.getSenderName());
					consignmentData.put("generalCargoNo", newData.getGeneralCargoNo());
					consignmentData.put("senderAddress1", newData.getSenderAddress1());
					consignmentData.put("senderAddress2", newData.getSenderAddress2());
					consignmentData.put("senderAddress3", newData.getSenderAddress3());
					consignmentData.put("senderPhone", newData.getSenderPhone());
					consignmentData.put("senderArea", "1"); // BKI
					consignmentData.put("senderIC", newData.getSenderIC());
					consignmentData.put("receiverName", newData.getReceiverName());
					consignmentData.put("receiverAttn", newData.getReceiverAttn());
					consignmentData.put("receiverAddress1", newData.getReceiverAddress1());
					consignmentData.put("receiverAddress2", newData.getReceiverAddress2());
					consignmentData.put("receiverAddress3", newData.getReceiverAddress3());
					consignmentData.put("receiverPostcode", newData.getReceiverPostcode());
					consignmentData.put("receiverZone", newData.getReceiverZone());
					consignmentData.put("receiverPhone", newData.getReceiverPhone());
					consignmentData.put("receiverArea", newData.getReceiverArea());
					consignmentData.put("helps", newData.getHelps());
					consignmentData.put("shipmentType", newData.getShipmentType());
					consignmentData.put("quantity", newData.getQuantity());
					consignmentData.put("weight", newData.getWeight());
					consignmentData.put("length", newData.getLength());
					consignmentData.put("width", newData.getWidth());
					consignmentData.put("height", newData.getHeight());
					consignmentData.put("amount", newData.getAmount());
					consignmentData.put("payMethod", newData.getPayMethod());
					consignmentData.put("stage", "4"); //In transit
					consignmentData.put("terms", newData.getTerms());
					consignmentData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
					consignmentData.put("staffCreate", "1");// 1: 由員工產生
					consignmentData.put("howtocreate", "Upload Excel");
					String consignmentNo = ConsignmentBusinessModel.generateConsignmentNo();
					result = ConsignmentBusinessModel.insertConsignment(consignmentData, consignmentNo);// 寫入資料庫

					if (i == data.size() - 1) { // 最後一筆
						lastuploadedCN += "'" + consignmentNo + "'";
					} else {
						lastuploadedCN += "'" + consignmentNo + "',";
					}

					if (result.equals("OK")) { // 成功

						// 產生PDF(PdfController和ConsignmentController也有一個actionType,寫法和這裡一樣,如修改以下程式必須也要修改那裡)
						// ArrayList<ConsignmentDataModel> nData = new
						// ArrayList<ConsignmentDataModel>();
						// nData =
						// ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo);
						// //重新查詢
						//
						// PdfBusinessModel.generateConsignmentNote(request,
						// nData); //產生PDF

						try {
							Thread.sleep(500); // 先等待0.5秒鐘再進行下一個
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}

				}

				ArrayList<ConsignmentDataModel> alldata = new ArrayList<ConsignmentDataModel>();
				alldata = ConsignmentBusinessModel.lastuploaded(lastuploadedCN); // 查詢剛上傳的資料

				// 每個 generalCargoNo 各產生 一個stickers，全部放到同一個 pdf 檔案裡
				String filename = PdfBusinessModel.generatePartnerSticker(request, alldata); // 產生PDF

				ConsignmentDataModel obj = (ConsignmentDataModel) alldata.get(0);
				obj.setVerify(filename); // 暫借 verify 來放檔名
				alldata.set(0, obj); // 再設入
				request.setAttribute(OBJECTDATA, alldata);

				url = "./cp/cpStation.jsp?result=fskuploadsuccess";

			} else {
				url = "./cp/cpStation.jsp?result=fskuploadfailed";
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}

		else if (actionType.equals(ACCOUNTSLIP) == true) {

			String settlementDT = request.getParameter("settlementDT") == null ? "" : request.getParameter("settlementDT").toString();
			double amount = Double.parseDouble(request.getParameter("amount") == null ? "0" : request.getParameter("amount").equals("") ? "0" : request.getParameter("amount").toString());
			int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").equals("") ? "0" : request.getParameter("station").toString());
			String remark = request.getParameter("remark") == null ? "" : request.getParameter("remark").toString();
			String targetPath = "accountSlip";

			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = null;
			String tempPath = request.getSession().getServletContext().getRealPath("/")	+ "temp/";
			try {
				items = uploadHandler.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
				logger.error("*** UploadController \"取不到temp裡圖檔\" " + e.toString() + " " + new Date());
			}

			String result = UploadBusinessModel.uploadSlip(items, settlementDT,	station, amount, remark, tempPath, targetPath, userId);

			out.print(result);
			out.flush();
			out.close();
			return;

		}

	}

}
