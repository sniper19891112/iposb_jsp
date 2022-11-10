package com.iposb.pickup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;

import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.log.LogBusinessModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.notification.NotificationBusinessModel;
import com.iposb.privilege.PrivilegeBusinessModel;
import com.iposb.utils.FirebaseCloudMessaging;



public class PickupController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String SEARCHREQUEST = "searchRequest";
	public static final String AJAXREASSIGN = "ajaxReassign";
	public static final String APPGETREQUEST = "appGetPickupRequest";
	public static final String NEWAPPGETREQUEST = "newAppGetPickupRequest";
	public static final String APPANSWERREQUEST = "appAnswerRequest"; // accept/reject request from app
	public static final String NEWAPPANSWERREQUEST = "newAppAnswerRequest"; // accept/reject request from app
	
	static Logger logger = Logger.getLogger(PickupController.class);
  
  public void init() throws ServletException {
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
      return;
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
//    request.setCharacterEncoding("UTF-8");
    
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
    String locale = request.getParameter("lang");
    
    if(actionType == null){
		if (!checkPrivilege(response, privilege)) {
			return;
		}
		
		// 防止user亂輸入頁碼
		int pageNo = 0;
		String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
		boolean isNum = isNumeric(page);
		String orderBy = request.getParameter("orderBy") == null ? "createDT" : request.getParameter("orderBy").equals("") ? "createDT" : request.getParameter("orderBy").toString();

		if (isNum) {
			pageNo = Integer.parseInt(page);
		}
		if (!(pageNo > 0) || !(isNum)) {
			pageNo = 1;
		}
		
		
    	ArrayList<PickupDataModel> data = new ArrayList<PickupDataModel>();
    	data = PickupBusinessModel.pickuplist(orderBy, pageNo);
    	
        //Driver List
        String driversdata = "";
        driversdata = LogonBusinessModel.driverListJSON();
        request.setAttribute("driver", driversdata);
    	
        //Partner List
        String partnerdata = "";
        partnerdata = LogonBusinessModel.partnerListJSON();
        request.setAttribute("partner", partnerdata);
    	
    	String url = "";
    	
    	if(data != null && data.size() > 0){
    		request.setAttribute(OBJECTDATA, data);
    		url = "./cp/cpPickupRequest.jsp";
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		return;
    }
    
    else if(actionType.equals(SEARCHREQUEST) == true){
    	if (!checkPrivilege(response, privilege)) {
			return;
		}
		
		// 防止user亂輸入頁碼
		int pageNo = 0;
		String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
		boolean isNum = isNumeric(page);
		String orderBy = request.getParameter("orderBy") == null ? "createDT" : request.getParameter("orderBy").equals("") ? "createDT" : request.getParameter("orderBy").toString();

		if (isNum) {
			pageNo = Integer.parseInt(page);
		}
		if (!(pageNo > 0) || !(isNum)) {
			pageNo = 1;
		}
		
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
		
    	ArrayList<PickupDataModel> data = new ArrayList<PickupDataModel>();
    	data = PickupBusinessModel.searchPickuplist(orderBy, pageNo, consignmentNo);
    	
        //Driver List
        String driversdata = "";
        driversdata = LogonBusinessModel.driverListJSON();
        request.setAttribute("driver", driversdata);
    	
        //Partner List
        String partnerdata = "";
        partnerdata = LogonBusinessModel.partnerListJSON();
        request.setAttribute("partner", partnerdata);
    	
		request.setAttribute(OBJECTDATA, data);
		String url = "./cp/cpPickupRequest.jsp";
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		return;
    }
    
    else if(actionType.equals(AJAXREASSIGN) == true){
    	if (!checkPrivilege(response, privilege)) {
			return;
		}
    	String result = "";
    	String returnVal = "";

		//String oldDriverUserId = request.getParameter("oldDriverUserId") == null ? "" : request.getParameter("oldDriverUserId").toString();
		int newDriverSid = Integer.parseInt(request.getParameter("newDriverSid") == null ? "0" : request.getParameter("newDriverSid").toString()); // new driver's sid (staff id)
		int agentId = Integer.parseInt(request.getParameter("agent") == null ? "0" : request.getParameter("agent").toString());
		String deliveryDT = request.getParameter("deliveryDT") == null ? "" : request.getParameter("deliveryDT").toString();
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
    	
    	// driver's userId
		String driver = LogonBusinessModel.getStaffIdById(newDriverSid);
		String agent = LogonBusinessModel.getPartnerIdById(agentId);
		
		result = PickupBusinessModel.updateRequest(consignmentNo, driver, agent, deliveryDT, userId);
		
		ConsignmentBusinessModel.assignAgent(consignmentNo, agentId);
		NotificationBusinessModel.insertNewAgentNotification("consignment", "New Consignment", consignmentNo, String.valueOf(agentId));

		String tokenFCM = LogonBusinessModel.getDataById("tokenFCM", "stafflist", driver);
		if(!tokenFCM.equals("")){
			FirebaseCloudMessaging.notifyIndividual("New Pickup Request", "Tap to view request", tokenFCM);
		}
		
		returnVal = result.equals("OK") ? "success" : "failed";
		
    	out.print(returnVal);
    	out.flush();
    	return;
    }
    
    else if(actionType.equals(APPGETREQUEST) == true){
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*"); //要加這行，apps 才收得到 header
    	
		String appUserId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
		String tokenFCM = request.getParameter("tokenFCM") == null ? "" : request.getParameter("tokenFCM").toString();
		String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");
		
		if(appUserId.trim().equals("")){
			appUserId = LogonBusinessModel.getDataByToken("userId", "stafflist", tokenFCM);
		}
		
    	String jsonText = PickupBusinessModel.apppickuplist(appUserId);

		out.print(callback + "(" + jsonText + ")");
    }

    else if(actionType.equals(APPANSWERREQUEST) == true){
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*"); //要加這行，apps 才收得到 header
    	
		String appUserId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
		int status = Integer.parseInt(request.getParameter("status") == null ? "0" : request.getParameter("status").toString());
		String tokenFCM = request.getParameter("tokenFCM") == null ? "" : request.getParameter("tokenFCM").toString();
		String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");

		if(appUserId.trim().equals("")){
			appUserId = LogonBusinessModel.getDataByToken("userId", "stafflist", tokenFCM);
		}
		
    	boolean result = PickupBusinessModel.answerRequest(appUserId, status, consignmentNo);

		if(result == true){
			String action = status == 1 ? "Accepted" : "Rejected";
			
			NotificationBusinessModel.insertNewStaffNotification("pickup request", "Pickup request "+ action, "./pickup?actionType=searchRequest&consignmentNo="+consignmentNo);
			
			LogBusinessModel.insertCPConsignmentLog(consignmentNo, 0, appUserId, "Pickup request "+action);
		}
		
    	String jsonText = "{\"success\":"+result+"}";
		out.print(callback + "(" + jsonText + ")");
    }
    
    else if(actionType.equals(NEWAPPGETREQUEST) == true){
        //response.setContentType("application/json");
        //response.addHeader("Access-Control-Allow-Origin", "*"); //要加這行，apps 才收得到 header
		
    	BufferedReader br = request.getReader();
		String d = br.readLine();
		JSONObject sdobj = new JSONObject(d);
    	String appUserId = sdobj.getString("userId");
    	String tokenFCM = sdobj.getString("tokenFCM");
		
		if(appUserId.trim().equals("")){
			appUserId = LogonBusinessModel.getDataByToken("userId", "stafflist", tokenFCM);
		}
		
    	String jsonText = PickupBusinessModel.apppickuplist(appUserId);

		out.print(jsonText);
    }

    else if(actionType.equals(NEWAPPANSWERREQUEST) == true){
//        response.setContentType("application/json");
//        response.addHeader("Access-Control-Allow-Origin", "*"); //要加這行，apps 才收得到 header
    	
//		String appUserId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
//		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
//		int status = Integer.parseInt(request.getParameter("status") == null ? "0" : request.getParameter("status").toString());
//		String tokenFCM = request.getParameter("tokenFCM") == null ? "" : request.getParameter("tokenFCM").toString();
//		String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");
		
    	BufferedReader br = request.getReader();
		String d = br.readLine();
		JSONObject sdobj = new JSONObject(d);
    	String appUserId = sdobj.getString("userId");
    	String consignmentNo = sdobj.getString("consignmentNo");
    	int status = Integer.parseInt(sdobj.getString("status"));
    	String tokenFCM = sdobj.getString("tokenFCM");

		if(appUserId.trim().equals("")){
			appUserId = LogonBusinessModel.getDataByToken("userId", "stafflist", tokenFCM);
		}
		
    	boolean result = PickupBusinessModel.answerRequest(appUserId, status, consignmentNo);

		if(result == true){
			String action = status == 1 ? "Accepted" : "Rejected";
			
			NotificationBusinessModel.insertNewStaffNotification("pickup request", "Pickup request "+ action, "./pickup?actionType=searchRequest&consignmentNo="+consignmentNo);
			
			LogBusinessModel.insertCPConsignmentLog(consignmentNo, 0, appUserId, "Pickup request "+action);
		}
		
    	String jsonText = "{\"success\":"+result+"}";
		out.print(jsonText);
    }
    
//    else if(actionType.equals(CPPICKUP) == true){
//
//		if (!checkPrivilege(response, loginCookie)) {
//			return;
//		}
//    	
//		// 防止user亂輸入頁碼
//		int pageNo = 0;
//		String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
//		boolean isNum = isNumeric(page);
//
//		String orderBy = request.getParameter("orderBy") == null ? "status" : request.getParameter("orderBy").equals("") ? "status" : request.getParameter("orderBy").toString();
//
//		if (isNum) {
//			pageNo = Integer.parseInt(page);
//		}
//		if (!(pageNo > 0) || !(isNum)) {
//			pageNo = 1;
//		}
//    	
//    	ArrayList<PickupDataModel> data = new ArrayList<PickupDataModel>();
//    	data = PickupBusinessModel.pickuplist(orderBy, pageNo);
//    	
//    	String url = "";
//    	
//    	if(data != null || data.size() > 0){
//    		request.setAttribute(OBJECTDATA, data);
//    		url = "./cpPickup";
//    	}
//    	
//    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//		dispatcher.forward(request, response);
//		return;
//    }
    
   }

	// 檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
		int iid = 5; // 必須自行手動設定，對應DB的privilege
		String url = "./stafflogin?returnUrl=./cpPickup";

		if (PrivilegeBusinessModel.determinePrivilege(iid, privilege)) {
			return true;
		} else {
			response.sendRedirect(url);
			return false;
		}
	}
	
  
  //以正規表達式判斷輸入的字串是否為數字
  public static boolean isNumeric(String str){
	  Pattern pattern = Pattern.compile("[0-9]*");
	  return pattern.matcher(str).matches();   
  }
  
  //Clean up resources
  public void destroy() {
  }
}