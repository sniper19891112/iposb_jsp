package com.iposb.consignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
import org.json.simple.JSONValue;

import com.iposb.area.AreaBusinessModel;
import com.iposb.area.AreaDataModel;
import com.iposb.code.CodeBusinessModel;
import com.iposb.code.CodeDataModel;
import com.iposb.i18n.Resource;
import com.iposb.log.LogBusinessModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.logon.LogonDataModel;
import com.iposb.my.MyBusinessModel;
import com.iposb.my.MyDataModel;
import com.iposb.notification.NotificationBusinessModel;
import com.iposb.partner.PartnerBusinessModel;
import com.iposb.partner.PartnerDataModel;
import com.iposb.pdf.PdfBusinessModel;
import com.iposb.pickup.PickupBusinessModel;
import com.iposb.pricing.PricingBusinessModel;
import com.iposb.privilege.PrivilegeBusinessModel;


public class ConsignmentController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  
	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String OBJECTDATA2 = "objectData2";
	
	public static final String CALENDARVIEW = "calendarView";
	public static final String JOINCONSIGNMENT = "joinConsignment";
	
	public static final String PENDING = "pending";
	public static final String CREATEJOINCONSIGNMENT = "createJoinConsignment";
	public static final String APPCREATEJOINCONSIGNMENT = "appCreateJoinConsignment";
	public static final String SEARCHCONSIGNMENT = "searchConsignment"; //search in control panel
	public static final String SEARCHCONSIGNMENTBYDAY = "searchConsignmentByDay";
	public static final String FILTERPARTNERCN = "filterPartnerCN"; //only show certain Partner's consignment
	public static final String FASTSEARCH = "fastSearch";
	public static final String HANDLINGSTAGE = "handlingStage"; //scan stages in control panel
	public static final String APPHANDLINGSTAGE = "appHandlingStage";
	public static final String NEWAPPHANDLINGSTAGE = "newAppHandlingStage";
	public static final String APPHANDLINGPENDING = "appHandlingPending";
	public static final String NEWAPPHANDLINGPENDING = "newAppHandlingPending";
	public static final String APPUPDATECONSIGNMENT = "appUpdateConsignment";
	public static final String NEWAPPUPDATECONSIGNMENT = "newAppUpdateConsignment";
	public static final String APPCREATECONSIGNMENT = "appCreateConsignment";
	public static final String VISUALCHECK = "visualCheck";
	public static final String CHANGESTATUS = "changeStatus";
	public static final String STATION = "station";
	
	public static final String AGENTJOINCONSIGNMENT = "agentJoinConsignment";
	public static final String AGENTSTATION = "agentStation";
	public static final String AGENTCREATEJOINCONSIGNMENT = "agentCreateJoinConsignment";
	
	public static final String USERCANCEL = "userCancel";
	public static final String EMAILCANCEL = "emailCancel";
	public static final String EMAILCANCELCONFIRM = "emailCancelConfirm";
	public static final String EMAILRECONFIRM = "emailReconfirm";
	public static final String TRANSACTION = "transaction";
	public static final String INLINEUPDATE = "inlineUpdate";
	public static final String CHANGEAMOUNT ="changeAmount";
	public static final String SUBMITDISCOUNT ="submitDiscount";
	public static final String PENDINGCONSIGNMENT = "pendingConsignment";
	public static final String LISTPENDING = "listPending";
	public static final String GENERATEPDF = "generatePDF";
	public static final String DELETEPDF = "deletePDF";
	public static final String COUNTTOTALCONSIGNMENT = "counttotalconsignment";
	public static final String CPCONSIGNMENTCALENDAR = "cpConsignmentCalendar";
	public static final String MISC = "misc";
	public static final String CPFASTSEARCH = "cpFastSearch";
	public static final String EMAILCHECKAVAILABILITY = "emailCheckAvailability";
	public static final String SENDEMAIL = "sendEmail";
	public static final String ADMINCHECKUSER = "adminCheckUser"; // 管理員查詢客人所有訂單
	
	public static final String CREATE = "create"; //客人準備要 create
	public static final String CREATECONSIGNMENT = "createConsignment";
	public static final String DRAFT = "draft";
	public static final String SAVEDRAFT = "savedraft";
	public static final String UPDATEDRAFT = "updatedraft";
	public static final String EDITDRAFT = "editdraft";
	public static final String AJAXGETADDRESSBOOK = "ajaxGetAddressBook";
	public static final String TRACE = "trace"; //公眾查詢包裹狀態

	private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(ConsignmentController.class);
  
  public void init() throws ServletException {
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request,response);
      return;
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    request.setCharacterEncoding("UTF-8");
    
    String userId = "";
	int privilege = -9;
	Subject currentUser = SecurityUtils.getSubject();
//	logger.info(currentUser.getSession().getAttribute("username"));
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
    
    if(actionType == null || actionType.equals(PENDING)){

    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	String url = "./cp/cpConsignment.jsp";
    	
    	int pageNo = 0;
    	String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
    	boolean isNum = isNumeric(page);
    	
    	//防止user亂輸入頁碼
    	if(isNum){
    		pageNo = Integer.parseInt(page);
    	} else {
    		return;
    	}
    	if( !(pageNo > 0) || !(isNum) ){
    		pageNo = 1;
    	}
    	
    	int status = 99; //all status of consignment
    	if (actionType != null && actionType.equals("pending")) {
    		status = 3; //pending shipment
    		url += "?conType=pending";
    	}
    	int aid = 0;
    	
    	if(privilege != 99){
    		String xid = LogonBusinessModel.getDataById("aid", "stafflist", userId);
    		aid = Integer.parseInt(xid.equals("")? "0" : xid);
    	}
    	
    	ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
    	data = ConsignmentBusinessModel.consignmentList(pageNo, status, aid); //查詢所有consignment給管理員看

        if(data != null && !data.isEmpty()){
        	
        	//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	ConsignmentDataModel obj = (ConsignmentDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
            
        	request.setAttribute(OBJECTDATA, data);
        }
        
        //Driver List
        ArrayList<LogonDataModel> driver = new ArrayList<LogonDataModel>();
        driver = LogonBusinessModel.driverList();
        request.setAttribute("driver", driver);
        
        //Area List
        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
        area = AreaBusinessModel.areaList();
        request.setAttribute("area", area);
        
        //Partner List
        ArrayList<PartnerDataModel> partner = new ArrayList<PartnerDataModel>();
	    partner = PartnerBusinessModel.agentPartnerList("partner");
	    request.setAttribute("partner", partner);
	    
	    //Agent List
        ArrayList<PartnerDataModel> agent = new ArrayList<PartnerDataModel>();
        agent = PartnerBusinessModel.agentPartnerList("agent");
	    request.setAttribute("agent", agent);
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
    }
    
    
    else if(actionType.equals(CALENDARVIEW)){

    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	//Area List
        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
        area = AreaBusinessModel.areaList();
        request.setAttribute("area", area);
        
    	String url = "./cp/cpCalendar.jsp";

    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
    }
    
    
    else if(actionType.equals(JOINCONSIGNMENT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
		String url = "./cp/cpJoinconsignment.jsp";
		
		ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		area = AreaBusinessModel.areaList();

	    if(area != null && !area.isEmpty()){
	    	request.setAttribute("area", area);
	    }
	    
	    ArrayList<PartnerDataModel> partner = new ArrayList<PartnerDataModel>();
	    partner = PartnerBusinessModel.agentPartnerList("partner");

	    if(partner != null && !partner.isEmpty()){
	    	request.setAttribute("partner", partner);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}
    
	else if(actionType.equals(AGENTJOINCONSIGNMENT) == true){
    	
    	if(privilege != 3){ //not agent
    		return;
    	}
    	
		String url = "./partner/cpJoinconsignment.jsp";
		
		ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		area = AreaBusinessModel.areaList();

	    if(area != null && !area.isEmpty()){
	    	request.setAttribute("area", area);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}
    
    else if(actionType.equals(CREATEJOINCONSIGNMENT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
	       
    	String result = "";
    	String url = "";
    	String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
    	
    	if(verify.trim().equals("")){
    		verify = ConsignmentBusinessModel.generateVerifyCode();
    	}
    	
    	HashMap<String, Object> consignmentData = new HashMap<String, Object>();
    	Enumeration<String> paramNames = request.getParameterNames();
    	
        while(paramNames.hasMoreElements()) {
        	String paramName = (String)paramNames.nextElement();
        	String[] paramValues = request.getParameterValues(paramName);

	        for(int i=0; i<paramValues.length; i++) {
	        	consignmentData.put(paramName, paramValues[i]);
	        }
        }
        
    	consignmentData.put("userId", userId);
    	consignmentData.put("verify", verify);
    	consignmentData.put("stage", "4"); //In transit
    	consignmentData.put("staffCreate", "1");
    	consignmentData.put("howtocreate", "Join Consignment");
                
        String consignmentNo = ConsignmentBusinessModel.generateConsignmentNo();
		result = ConsignmentBusinessModel.insertConsignment(consignmentData, consignmentNo);//寫入資料庫
        	
		if(result.equals("OK")){ //成功
			
			url = "./joinconsignment?result=createSuccess";
			
			response.sendRedirect(url);
			
		} else { //失敗, 導回填寫資料頁
			
	        consignmentData.put("errmsg", result);
			request.setAttribute(OBJECTDATA2, consignmentData);
        	
        	url = "./cp/cpJoinconsignment.jsp?source=edit&result=createFailed";
        	
        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
		}
		
        return;
    }
    
    
    else if(actionType.equals(AGENTCREATEJOINCONSIGNMENT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
	       
    	String result = "";
    	String url = "";
    	String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
    	
    	if(verify.trim().equals("")){
    		verify = ConsignmentBusinessModel.generateVerifyCode();
    	}
    	
    	HashMap<String, Object> consignmentData = new HashMap<String, Object>();
    	Enumeration<String> paramNames = request.getParameterNames();
    	
        while(paramNames.hasMoreElements()) {
        	String paramName = (String)paramNames.nextElement();
        	String[] paramValues = request.getParameterValues(paramName);

	        for(int i=0; i<paramValues.length; i++) {
	        	consignmentData.put(paramName, paramValues[i]);
	        }
        }
        
        int agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
        
    	consignmentData.put("userId", userId);
    	consignmentData.put("verify", verify);
    	consignmentData.put("stage", "4"); //In transit
    	consignmentData.put("staffCreate", "1");
    	consignmentData.put("howtocreate", "Join Consignment");
    	consignmentData.put("agentId", agentId);
                
        String consignmentNo = ConsignmentBusinessModel.generateConsignmentNo();
		result = ConsignmentBusinessModel.insertConsignment(consignmentData, consignmentNo);//寫入資料庫
        	
		if(result.equals("OK")){ //成功

			url = "./agentJoinConsignment?result=createSuccess";
			
			response.sendRedirect(url);
			
		} else { //失敗, 導回填寫資料頁
			
	        consignmentData.put("errmsg", result);
			request.setAttribute(OBJECTDATA2, consignmentData);
        	
        	url = "./partner/cpJoinconsignment.jsp?source=edit&result=createFailed";
        	
        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
		}
		
        return;
    }
    
    else if(actionType.equals(SEARCHCONSIGNMENT) == true){
    	
    	if(!checkPrivilege(response, privilege) && (privilege != 3)){ //not authorized & not agent
    		return;
    	}
    	
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").trim().toString();
    	String url = "./cp/cpConsignment.jsp?actionType=searchConsignment&consignmentNo="+consignmentNo;
    	
    	ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
    	if(consignmentNo.trim().length() > 0){
    		int agentId = 0;
    		if(privilege == 3){ //agent
    			agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    			url = "./partner/cpConsignment_agent.jsp?actionType=searchConsignment&consignmentNo="+consignmentNo;
    		}
    		data = ConsignmentBusinessModel.searchConsignment(consignmentNo, agentId); //搜尋consignment
    		
    		if(data != null && !data.isEmpty()){
            	request.setAttribute(OBJECTDATA, data);
            	
                //Driver List
                ArrayList<LogonDataModel> driver = new ArrayList<LogonDataModel>();
                driver = LogonBusinessModel.driverList();
                request.setAttribute("driver", driver);
                
                //Area List
                ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
                area = AreaBusinessModel.areaList();
                request.setAttribute("area", area);
                
                //Partner List
                ArrayList<PartnerDataModel> partner = new ArrayList<PartnerDataModel>();
        	    partner = PartnerBusinessModel.agentPartnerList("partner");
        	    request.setAttribute("partner", partner);
                ArrayList<PartnerDataModel> agent = new ArrayList<PartnerDataModel>();
                agent = PartnerBusinessModel.agentPartnerList("agent");
        	    request.setAttribute("agent", agent);

            }
            
    		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
    	}

        return;
    }
    
    
    
    else if(actionType.equals(SEARCHCONSIGNMENTBYDAY) == true){ //顯示當天的所有訂單
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	String DT = request.getParameter("DT") == null ? "" : request.getParameter("DT").trim().toString();
    	int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").trim().toString());

    	ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
    	if(DT.length() > 0){
    		data = ConsignmentBusinessModel.searchConsignmentByDay(DT, station); //搜尋consignment
    		
    		if(data != null && !data.isEmpty()){
            	request.setAttribute(OBJECTDATA, data);
            	
                //Driver List
                ArrayList<LogonDataModel> driver = new ArrayList<LogonDataModel>();
                driver = LogonBusinessModel.driverList();
                request.setAttribute("driver", driver);
                
                //Area List
                ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
                area = AreaBusinessModel.areaList();
                request.setAttribute("area", area);
                
                //Partner List
                ArrayList<PartnerDataModel> partner = new ArrayList<PartnerDataModel>();
        	    partner = PartnerBusinessModel.agentPartnerList("partner");
        	    request.setAttribute("partner", partner);

            }
    	} else {
    		response.sendRedirect("./consignment");
    		return;
    	}
        
    	
    	String url = "./cp/cpConsignment.jsp?actionType=searchBookingByDay&DT="+DT;
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
    
    
    else if(actionType.equals(FILTERPARTNERCN) == true){ 
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	
    	int partnerId = Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId").toString());
    	String url = "./cp/cpConsignment.jsp?actionType=filterPartnerCN&partnerId="+partnerId;
    	
    	int pageNo = 0;
    	String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
    	boolean isNum = isNumeric(page);
    	
    	//防止user亂輸入頁碼
    	if(isNum){
    		pageNo = Integer.parseInt(page);
    	} else {
    		return;
    	}
    	if( !(pageNo > 0) || !(isNum) ){
    		pageNo = 1;
    	}

    	
    	ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
    	if(partnerId > 0){
    		data = ConsignmentBusinessModel.searchPartnerConsignment(partnerId, pageNo); //搜尋 Partner 的 consignment
    		
    		if(data != null && !data.isEmpty()){
//            	ConsignmentDataModel obj = (ConsignmentDataModel)data.get(0);
            	request.setAttribute(OBJECTDATA, data);
            	
            	//Driver List
                ArrayList<LogonDataModel> driver = new ArrayList<LogonDataModel>();
                driver = LogonBusinessModel.driverList();
                request.setAttribute("driver", driver);
                
                //Area List
                ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
                area = AreaBusinessModel.areaList();
                request.setAttribute("area", area);
                
                //Partner List
                ArrayList<PartnerDataModel> partner = new ArrayList<PartnerDataModel>();
        	    partner = PartnerBusinessModel.agentPartnerList("partner");
        	    request.setAttribute("partner", partner);

            }
            
    		
            
    	} else { //全部
    		
    		url = "./consignment";
    		
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);

        return;
    }
    
    
    
    else if(actionType.equals(COUNTTOTALCONSIGNMENT) == true){
    	String countTotal_userId = new String(request.getParameter("userId").getBytes("ISO-8859-1"), "UTF-8");

		int total = ConsignmentBusinessModel.countTotalConsignment(countTotal_userId);
	    out.print(total);
	}
    
    
    else if(actionType.equals(MISC) == true){
		
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
	    	
		String url = "./cp/cpMisc.jsp";
		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		 
		data = ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo);

		

	    if(data != null && !data.isEmpty()){
	    	request.setAttribute(OBJECTDATA, data);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}
    
    
    else if(actionType.equals(HANDLINGSTAGE) == true){
    	int currentstage = Integer.parseInt(request.getParameter("currentstage") == null ? "-1" : request.getParameter("currentstage").equals("") ? "-1" : request.getParameter("currentstage").toString());
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();

//    	double weight = Double.parseDouble(request.getParameter("weight") == null ? "0.0" : request.getParameter("weight").toString().trim().equals("") ? "0.0" : request.getParameter("weight").toString());
//    	double length = Double.parseDouble(request.getParameter("length") == null ? "0.0" : request.getParameter("length").toString().trim().equals("") ? "0.0" : request.getParameter("length").toString());
//    	double width = Double.parseDouble(request.getParameter("width") == null ? "0.0" : request.getParameter("width").toString().trim().equals("") ? "0.0" : request.getParameter("width").toString());
//    	double height = Double.parseDouble(request.getParameter("height") == null ? "0.0" : request.getParameter("height").toString().trim().equals("") ? "0.0" : request.getParameter("height").toString());
//    	int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "0" : request.getParameter("quantity").toString().trim().equals("") ? "0" : request.getParameter("quantity").toString());
    	
		String result = "";
		
		if(privilege == 3 || privilege >= 5) { //agent or staff
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
			
			//if agent scan, check first if the agent owned that consignment
			if(privilege == 3) {
				int agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
				if (!ConsignmentBusinessModel.accessibleConsignment(consignmentNo, agentId)) {
					result = "NoSuchConsignment";
				}
			}
			
			//verify the scan is generalCargo's CN
			String matchConsignmentNo = ConsignmentBusinessModel.matchGeneralCargoNo(consignmentNo);
		
			String cn = "";
			if (matchConsignmentNo.trim().length() > 0) { //find relevant consignmentNo, it means the scan was generalCargo's CN
				cn = matchConsignmentNo;
			} else {
				if(consignmentNo.replaceAll("\\s","").length() > 9) { //consignmentNo + serial
					cn = consignmentNo.replaceAll("\\s","").substring(0, 9);
				} else {
					cn = consignmentNo;
				}
			}

				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效
				
				if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
					result = ConsignmentBusinessModel.handleStage(currentstage, matchConsignmentNo, consignmentNo, userId, stage, request);
				} else { //scan 的是 consignmentNo
					result = ConsignmentBusinessModel.handleStage(currentstage, consignmentNo, "", userId, stage, request);
				}
				
				if(result.length() >= 2){
					if(result.substring(0, 2).equals("OK")){
						PickupBusinessModel.setIsMonitored(consignmentNo);
					}
				}
				
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
			} else { // -1

				if(currentstage == 1) { //由於是 pickup，所以沒有找到 cn 的話，就新增一筆

			    	HashMap<String, Object> newData = new HashMap<String, Object>();
			        
			    	newData.put("userId", userId);
			    	newData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
			    	newData.put("stage", "1"); //Picking Up
			    	newData.put("staffCreate", "1");
			    	newData.put("howtocreate", "Apps Scan");

					result = ConsignmentBusinessModel.insertConsignment(newData, consignmentNo);//寫入資料庫
					ConsignmentBusinessModel.handleStage(currentstage, consignmentNo, "", userId, stage, request);
					
					result = "InsertSuccess";
					logger.info("--- New Consignment: " + consignmentNo);
					
				} else { //其他 stage
					result = "NoSuchConsignment";
					logger.info("--- No Such Consignment: " + consignmentNo);
				}
			}
			
			//如果同一個stage已經scan過又scan的話，同樣要記錄，但也要alert告知已經scan過
			if (result.equals("OK")) {
				if(stage == currentstage) {
					result = "RescanOK";
				}
			}

			
    	}
		
	    out.print(result);
	}
    
    
    else if(actionType.equals(APPHANDLINGSTAGE) == true){
    	int currentstage = Integer.parseInt(request.getParameter("currentstage") == null ? "-1" : request.getParameter("currentstage").equals("") ? "-1" : request.getParameter("currentstage").toString());
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
    	String appUserId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
    	String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");

    	logger.info("*** Incoming scan of consignment (" + consignmentNo+") for Stage " +currentstage + " by "+appUserId);
    	
		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
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
			
//			logger.info("--- Incoming barcode scanning ("+userId+"): " + consignmentNo + " @ Stage "+currentstage+" ---");
			
				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效

				if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
					result = ConsignmentBusinessModel.handleStage(currentstage, matchConsignmentNo, consignmentNo, appUserId, stage, request);
				} else { //scan 的是 consignmentNo
					result = ConsignmentBusinessModel.handleStage(currentstage, consignmentNo, "", appUserId, stage, request);
				}
				
				if(result.length() >= 2){
					if(result.substring(0, 2).equals("OK")){
						PickupBusinessModel.setIsMonitored(consignmentNo);
					}
				}
				
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
				logger.info("--- Consignment is Closed: " + cn);
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
				logger.info("--- No Connection");
			} else { // -1
				
				if(currentstage == 1) { //由於是 pickup，所以沒有找到 cn 的話，就新增一筆

			    	HashMap<String, Object> newData = new HashMap<String, Object>();
			        
			    	newData.put("userId", appUserId);
			    	newData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
			    	newData.put("stage", "1"); //Picking Up
			    	newData.put("staffCreate", "1");
			    	newData.put("howtocreate", "Apps Scan");
			    	
			    	if(consignmentNo.length() > 9) { //根據 DB structure，不可多於 9 字元
			    		consignmentNo = consignmentNo.substring(0, 9); 
			    	}

					result = ConsignmentBusinessModel.insertConsignment(newData, consignmentNo);//寫入資料庫
					ConsignmentBusinessModel.handleStage(currentstage, consignmentNo, "", appUserId, stage, request);
					
					result = "InsertSuccess";
					logger.info("--- New Consignment: " + consignmentNo);
					
				} else { //其他 stage
					result = "NoSuchConsignment";
					logger.info("--- No Such Consignment: " + consignmentNo);
				}
				
				
			}
			
			//如果同一個stage已經scan過又scan的話，同樣要記錄，但也要alert告知已經scan過
			if (result.equals("OK")) {
				if(stage == currentstage) {
					result = "RescanOK";
				}
			}
			
			jsonObj.put("result", result);
			
			
    	}else{
    		jsonObj.put("result", "Access Denied, please re-login and try again.");
    	}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(callback+"("+jsonText+")");
	}
    
    
    else if(actionType.equals(NEWAPPHANDLINGSTAGE) == true){
    	//String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");

		BufferedReader br = request.getReader();
		String stagedata = br.readLine();
		JSONObject sdobj = new JSONObject(stagedata);
    	int currentstage = sdobj.getInt("currentstage");
    	String consignmentNo = sdobj.getString("consignmentNo");
    	String appUserId = sdobj.getString("userId");
		
    	logger.info("*** Incoming APP scan of consignment (" + consignmentNo+") for Stage " +currentstage + " by "+appUserId);
    	
		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		//if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
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
			
//			logger.info("--- Incoming barcode scanning ("+userId+"): " + consignmentNo + " @ Stage "+currentstage+" ---");
			
				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效

				if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
					result = ConsignmentBusinessModel.handleStage(currentstage, matchConsignmentNo, consignmentNo, appUserId, stage, request);
				} else { //scan 的是 consignmentNo
					result = ConsignmentBusinessModel.handleStage(currentstage, consignmentNo, "", appUserId, stage, request);
				}
				
				if(result.length() >= 2){
					if(result.substring(0, 2).equals("OK")){
						PickupBusinessModel.setIsMonitored(consignmentNo);
					}
				}
				
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
				logger.info("--- Consignment is Closed: " + cn);
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
				logger.info("--- No Connection");
			} else { // -1
				
//				if(currentstage == 1) { //由於是 pickup，所以沒有找到 cn 的話，就新增一筆
//
//			    	HashMap<String, Object> newData = new HashMap<String, Object>();
//			        
//			    	newData.put("userId", appUserId);
//			    	newData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
//			    	newData.put("stage", "1"); //Picking Up
//			    	newData.put("staffCreate", "1");
//			    	newData.put("howtocreate", "Apps Scan");
//			    	
//			    	if(consignmentNo.length() > 9) { //根據 DB structure，不可多於 9 字元
//			    		consignmentNo = consignmentNo.substring(0, 9); 
//			    	}
//
//					result = ConsignmentBusinessModel.insertConsignment(newData, consignmentNo);//寫入資料庫
//					ConsignmentBusinessModel.handleStage(currentstage, consignmentNo, "", appUserId, stage, request);
//					
//					result = "InsertSuccess";
//					logger.info("--- New Consignment: " + consignmentNo);
//					
//				} else { //其他 stage
					result = "NoSuchConsignment";
					logger.info("--- No Such Consignment: " + consignmentNo);
//				}
				
				
			}
			
			//如果同一個stage已經scan過又scan的話，同樣要記錄，但也要alert告知已經scan過
			if (result.equals("OK")) {
				if(stage == currentstage) {
					result = "RescanOK";
				}
			}
			
			jsonObj.put("result", result);
			
			
    	//}else{
    	//	jsonObj.put("result", "Access Denied, please re-login and try again.");
    	//}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(jsonText);
	}

    
    
    else if(actionType.equals(APPCREATECONSIGNMENT) == true){
    	String cn = "";
		BufferedReader br = request.getReader();
		String stagedata = br.readLine();
		JSONObject sdobj = new JSONObject(stagedata);
		String generalCargoNo = sdobj.getString("generalCargoNo") == null ? "" : sdobj.getString("generalCargoNo").trim();
//    	String consignmentNo = sdobj.getString("consignmentNo") == null ? "" : sdobj.getString("consignmentNo").trim();
    	String appUserId = sdobj.getString("userId") == null ? "" : sdobj.getString("userId").trim();
    	String senderName = sdobj.getString("senderName") == null ? "" : sdobj.getString("senderName").trim();
    	String senderAddress1 = sdobj.getString("senderAddress1") == null ? "" : sdobj.getString("senderAddress1").trim();
    	String senderAddress2 = sdobj.getString("senderAddress2") == null ? "" : sdobj.getString("senderAddress2").trim();
    	String senderPostcode = sdobj.getString("senderPostcode") == null ? "" : sdobj.getString("senderPostcode").trim();
    	String senderArea = sdobj.getString("senderArea") == null ? "" : sdobj.getString("senderArea").trim();
    	String senderPhone = sdobj.getString("senderPhone") == null ? "" : sdobj.getString("senderPhone").trim();
    	String receiverName = sdobj.getString("receiverName") == null ? "" : sdobj.getString("receiverName").trim();
    	String receiverAddress1 = sdobj.getString("receiverAddress1") == null ? "" : sdobj.getString("receiverAddress1").trim();
    	String receiverAddress2 = sdobj.getString("receiverAddress2") == null ? "" : sdobj.getString("receiverAddress2").trim();
    	String receiverPostcode = sdobj.getString("receiverPostcode") == null ? "" : sdobj.getString("receiverPostcode").trim();
    	String receiverArea = sdobj.getString("receiverArea") == null ? "" : sdobj.getString("receiverArea").trim();
    	String receiverPhone = sdobj.getString("receiverPhone") == null ? "" : sdobj.getString("receiverPhone").trim();
    	String quantity = sdobj.getString("quantity") == null ? "0" : sdobj.getString("quantity").trim().equals("") ? "0" : sdobj.getString("quantity").trim();
    	String weight = sdobj.getString("weight") == null ? "0" : sdobj.getString("weight").trim().equals("") ? "0" : sdobj.getString("weight").trim();
    	String accountNumber = sdobj.getString("accountNumber") == null ? "" : sdobj.getString("accountNumber").trim();
		
    	logger.info("*** Creating new consignment from app by " + appUserId);
    	
		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		String consignmentNo = ConsignmentBusinessModel.generateConsignmentNo(); //change to auto-generated by system
		
		//if(privilege >= 5) { //TODO: check if this is 內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
			//test if the scan is CN belong to generalCargo
			if(!generalCargoNo.equals("")) {
				
				String matchConsignmentNo = ConsignmentBusinessModel.matchGeneralCargoNo(generalCargoNo);
				
				
				if (matchConsignmentNo.trim().length() > 0) { //found match consignmentNo, it shows that this scan is generalCargo's CN
					cn = matchConsignmentNo;
				} else {
					if(consignmentNo.replaceAll("\\s","").length() > 9) { //consignmentNo + serial
						cn = consignmentNo.replaceAll("\\s","").substring(0, 9);
					} else {
						cn = consignmentNo;
					}
				}
				
//			logger.info("--- Incoming barcode scanning ("+userId+"): " + consignmentNo + " @ Stage "+currentstage+" ---");
				
			}


			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if(stage == -1){

		    	HashMap<String, Object> newData = new HashMap<String, Object>();
		        
		    	newData.put("userId", appUserId);
		    	newData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
		    	newData.put("generalCargoNo", generalCargoNo);
		    	newData.put("senderName", senderName);
		    	newData.put("senderAddress1", senderAddress1);
		    	newData.put("senderAddress2", senderAddress2);
		    	newData.put("senderPostcode", senderPostcode);
		    	newData.put("senderArea", senderArea);
		    	newData.put("senderPhone", senderPhone);
		    	newData.put("receiverName", receiverName);
		    	newData.put("receiverAddress1", receiverAddress1);
		    	newData.put("receiverAddress2", receiverAddress2);
		    	newData.put("receiverPostcode", receiverPostcode);
		    	newData.put("receiverArea", receiverArea);
		    	newData.put("receiverPhone", receiverPhone);
		    	newData.put("quantity", quantity);
		    	newData.put("weight", weight);
		    	newData.put("accountNumber", accountNumber);
		    	newData.put("stage", "1"); //Picking Up
		    	newData.put("staffCreate", "1");
		    	newData.put("howtocreate", "Created by App");
		    	
		    	if(consignmentNo.length() > 9) { //according to DB structure, it cannot be more than 9 characters
		    		consignmentNo = consignmentNo.substring(0, 9); 
		    	}
		    	
		    	if(consignmentNo.trim().length()==0) {
		    		consignmentNo = ConsignmentBusinessModel.generateConsignmentNo();
		    	}

				String insertResult = ConsignmentBusinessModel.insertConsignment(newData, consignmentNo);//write to DB
				ConsignmentBusinessModel.handleStage(1, consignmentNo, "", appUserId, stage, request);
				
				if(insertResult.equals("OK")) {
					result = "InsertSuccess";
					logger.info("--- New Consignment: " + consignmentNo);
				}
				
			} else if(stage == 1) {
				result = "RescanOK";
			} else {
				result = "CannotScanHere";
			}
			
			jsonObj.put("result", result);
			
			
    	//}else{
    	//	jsonObj.put("result", "Access Denied, please re-login and try again.");
    	//}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(jsonText);
	}
    
    
    
    else if(actionType.equals(APPCREATECONSIGNMENT) == true){
    	//String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");

		BufferedReader br = request.getReader();
		String stagedata = br.readLine();
		JSONObject sdobj = new JSONObject(stagedata);
    	String consignmentNo = sdobj.getString("consignmentNo");
    	String appUserId = sdobj.getString("userId");
		
    	logger.info("*** Creating new consignment from app (" + consignmentNo+") by "+appUserId);
    	
		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		//if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
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
			
//			logger.info("--- Incoming barcode scanning ("+userId+"): " + consignmentNo + " @ Stage "+currentstage+" ---");
			
				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if(stage == -1){

		    	HashMap<String, Object> newData = new HashMap<String, Object>();
		        
		    	newData.put("userId", appUserId);
		    	newData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
		    	newData.put("stage", "1"); //Picking Up
		    	newData.put("staffCreate", "1");
		    	newData.put("howtocreate", "Apps Create");
		    	
		    	if(consignmentNo.length() > 9) { //根據 DB structure，不可多於 9 字元
		    		consignmentNo = consignmentNo.substring(0, 9); 
		    	}

				result = ConsignmentBusinessModel.insertConsignment(newData, consignmentNo);//寫入資料庫
				ConsignmentBusinessModel.handleStage(1, consignmentNo, "", appUserId, stage, request);
				
				result = "InsertSuccess";
				logger.info("--- New Consignment: " + consignmentNo);
				
				
			}
			
			if(stage == 1) {
				result = "RescanOK";
			}
			
			jsonObj.put("result", result);
			
			
    	//}else{
    	//	jsonObj.put("result", "Access Denied, please re-login and try again.");
    	//}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(jsonText);
	}
    
    else if(actionType.equals(APPCREATEJOINCONSIGNMENT) == true){

		BufferedReader br = request.getReader();
		String stagedata = br.readLine();
		JSONObject sdobj = new JSONObject(stagedata);
    	String generalCargoNo = sdobj.getString("generalCargoNo");
    	String senderName = sdobj.getString("senderName");
    	String receiverName = sdobj.getString("receiverName");
    	String quantity = sdobj.getString("quantity");
//    	String accountNo = sdobj.getString("accountNo");
    	String appUserId = sdobj.getString("userId");
    	//String verify = sdobj.getString("verify");
    	String verify = "";
    	
    	String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
    	
    	if(verify.trim().equals("")){
    		verify = ConsignmentBusinessModel.generateVerifyCode();
    	}
    	
    	HashMap<String, Object> consignmentData = new HashMap<String, Object>();

    	consignmentData.put("generalCargoNo", generalCargoNo);
    	consignmentData.put("senderName", senderName);
    	consignmentData.put("receiverName", receiverName);
    	consignmentData.put("quantity", quantity);
    	consignmentData.put("userId", appUserId);
    	consignmentData.put("verify", verify);
    	consignmentData.put("stage", "1"); //pick up
    	consignmentData.put("staffCreate", "1");
    	consignmentData.put("howtocreate", "Apps Create");
                
        String consignmentNo = ConsignmentBusinessModel.generateConsignmentNo();
		
    	logger.info("*** Creating new consignment from app (" + consignmentNo+") by "+appUserId);
    	
		result = ConsignmentBusinessModel.insertConsignment(consignmentData, consignmentNo);//寫入資料庫
        	
		if(result.equals("OK")){ //成功
			
			result += consignmentNo;
			logger.info("--- New Consignment: " + consignmentNo);
			
		}
		
		jsonObj.put("result", result);

		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(jsonText);
    }
    
    
    
    else if(actionType.equals(APPHANDLINGPENDING) == true){
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
    	String reasonPending = request.getParameter("reasonPending") == null ? "" : request.getParameter("reasonPending").toString();
    	String reasonText = request.getParameter("reasonText") == null ? "" : request.getParameter("reasonText").toString();
    	String appUserId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
    	String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");
    	
    	logger.info("*** Incoming scan of consignment (" + consignmentNo+") for Pending (" +reasonPending+reasonText+ ") by "+userId);

		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
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
			
//			logger.info("--- Incoming barcode scanning ("+userId+") for Pending Shipment (Reason: "+reasonPending+"): " + consignmentNo + " ---");
			
				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效

				if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
					result = ConsignmentBusinessModel.handleStagePending(matchConsignmentNo, consignmentNo, reasonPending, reasonText, userId);
				} else { //scan 的是 consignmentNo
					result = ConsignmentBusinessModel.handleStagePending(consignmentNo, "", reasonPending, reasonText, userId);
				}
				
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
				logger.info("--- Consignment is Closed: " + cn);
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
				logger.info("--- No Connection");
			} else { // -1
				result = "NoSuchConsignment";
				logger.info("--- No Such Consignment: " + consignmentNo);
			}

			
			jsonObj.put("result", result);
			
    	}else{
    		jsonObj.put("result", "Access Denied, please re-login and try again.");
    	}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(callback+"("+jsonText+")");
	}
    
    else if(actionType.equals(NEWAPPHANDLINGPENDING) == true){
    	
    	BufferedReader br = request.getReader();
		String stagedata = br.readLine();
		JSONObject sdobj = new JSONObject(stagedata);
    	String consignmentNo = sdobj.getString("consignmentNo");
    	String appUserId = sdobj.getString("userId");
    	String reasonPending = sdobj.getString("reasonPending");
    	String reasonText = sdobj.getString("reasonText");
    	
    	logger.info("*** Incoming scan of consignment (" + consignmentNo+") for Pending (" +reasonPending+reasonText+ ") by "+appUserId);

		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		//if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
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
			
//			logger.info("--- Incoming barcode scanning ("+userId+") for Pending Shipment (Reason: "+reasonPending+"): " + consignmentNo + " ---");
			
				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效

				if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
					result = ConsignmentBusinessModel.handleStagePending(matchConsignmentNo, consignmentNo, reasonPending, reasonText, appUserId);
				} else { //scan 的是 consignmentNo
					result = ConsignmentBusinessModel.handleStagePending(consignmentNo, "", reasonPending, reasonText, appUserId);
				}
				
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
				logger.info("--- Consignment is Closed: " + cn);
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
				logger.info("--- No Connection");
			} else { // -1
				result = "NoSuchConsignment";
				logger.info("--- No Such Consignment: " + consignmentNo);
			}

			
			jsonObj.put("result", result);
			
    	//}else{
    	//	jsonObj.put("result", "Access Denied, please re-login and try again.");
    	//}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(jsonText);
	}
    
    
    else if(actionType.equals(APPUPDATECONSIGNMENT) == true){

//    	int shipmentType = Integer.parseInt(request.getParameter("shipmentType") == null ? "0" : request.getParameter("shipmentType").toString().trim().equals("") ? "0" : request.getParameter("shipmentType").toString());
//    	int freightType = Integer.parseInt(request.getParameter("freightType") == null ? "0" : request.getParameter("freightType").toString().trim().equals("") ? "0" : request.getParameter("freightType").toString());
    	
//    	String senderArea = request.getParameter("senderArea") == null ? "" : request.getParameter("senderArea").toString();
//    	String receiverArea = request.getParameter("toArea") == null ? "" : request.getParameter("receiverArea").toString();
//    	double weight = Double.parseDouble(request.getParameter("weight") == null ? "0.0" : request.getParameter("weight").toString().trim().equals("") ? "0.0" : request.getParameter("weight").toString());
//    	int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "0" : request.getParameter("quantity").toString().trim().equals("") ? "0" : request.getParameter("quantity").toString());
    	
    	String promoCode = "";
    	
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
    	String appUserId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
    	String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");
    	String senderArea = request.getParameter("senderArea") == null ? "" : request.getParameter("senderArea").toString();
		String receiverArea = request.getParameter("receiverArea") == null ? "" : request.getParameter("receiverArea").toString();
		int shipmentType = Integer.parseInt(request.getParameter("shipmentType") == null ? "1" : request.getParameter("shipmentType").toString().trim().equals("") ? "1" : request.getParameter("shipmentType").toString());
		double weight = Double.parseDouble(request.getParameter("weight") == null ? "0" : request.getParameter("weight").toString().trim().equals("") ? "0" : request.getParameter("weight").toString());
		int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "1" : request.getParameter("quantity").toString().trim().equals("") ? "1" : request.getParameter("quantity").toString());
		String memberType = request.getParameter("memberType") == null ? "normal" : request.getParameter("memberType").equals("") ? "normal" : request.getParameter("memberType").toString();
		String accNo = request.getParameter("accNo") == null ? "" : request.getParameter("accNo").toString();
		
    	
    	logger.info("*** Incoming scan of consignment (" + consignmentNo+") for update by "+userId);
    	
		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
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
				
				promoCode = ConsignmentBusinessModel.getPromoCodeByCN(cn);
			}
			
			ArrayList<CodeDataModel> cData = CodeBusinessModel.codeDetails(promoCode); 
				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效

				if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
//					result = ConsignmentBusinessModel.appUpdateConsignment(matchConsignmentNo, consignmentNo, userId, shipmentType, freightType, senderArea, receiverArea, weight, quantity, request);
					result = ConsignmentBusinessModel.appUpdateConsignment(matchConsignmentNo, consignmentNo, userId, senderArea, receiverArea, shipmentType, weight, quantity, memberType, accNo, request, cData);
				} else { //scan 的是 consignmentNo
//					result = ConsignmentBusinessModel.appUpdateConsignment(consignmentNo, "", userId, shipmentType, freightType, senderArea, receiverArea, weight, quantity, request);
					result = ConsignmentBusinessModel.appUpdateConsignment(consignmentNo, "", userId, senderArea, receiverArea, shipmentType, weight, quantity, memberType, accNo, request, cData);
				}
				
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
				logger.info("--- Consignment is Closed: " + cn);
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
				logger.info("--- No Connection");
			} else { // -1
				result = "NoSuchConsignment";
				logger.info("--- No Such Consignment: " + consignmentNo);
			}
			
			logger.info("*** Result: " + result);
			
			jsonObj.put("result", result);
				
			
    	}else{
    		jsonObj.put("result", "Access Denied, please re-login and try again.");
    	}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(callback+"("+jsonText+")");
	}
    
    else if(actionType.equals(NEWAPPUPDATECONSIGNMENT) == true){
    	
    	String promoCode = "";
    			
		BufferedReader br = request.getReader();
		String stagedata = br.readLine();
		JSONObject sdobj = new JSONObject(stagedata);
    	String consignmentNo = sdobj.getString("consignmentNo");
    	String appUserId = sdobj.getString("userId");
    	String senderArea = sdobj.getString("senderArea");
    	String receiverArea = sdobj.getString("receiverArea");
    	int shipmentType = sdobj.getInt("shipmentType");
    	double weight = sdobj.getDouble("weight");
    	int quantity = sdobj.getInt("quantity");
    	String memberType = sdobj.getString("memberType");
    	String accNo = sdobj.getString("accNo");
    	
    	logger.info("*** Incoming scan of consignment (" + consignmentNo+") for update by "+appUserId);
    	
		String result = "";
		
		Map<String, String> jsonObj = new LinkedHashMap<String, String>();
		
		//if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
		
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
				
				promoCode = ConsignmentBusinessModel.getPromoCodeByCN(cn);
			}
			
			ArrayList<CodeDataModel> cData = CodeBusinessModel.codeDetails(promoCode); 
				
			int stage = ConsignmentBusinessModel.checkStage(cn);
			
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效

				if (matchConsignmentNo.trim().length() > 0) { //有找到對應的 consignmentNo，證明scan的是 generalCargo的 CN
					result = ConsignmentBusinessModel.appUpdateConsignment(matchConsignmentNo, consignmentNo, appUserId, senderArea, receiverArea, shipmentType, weight, quantity, memberType, accNo, request, cData);
				} else { //scan 的是 consignmentNo
					result = ConsignmentBusinessModel.appUpdateConsignment(consignmentNo, "", appUserId, senderArea, receiverArea, shipmentType, weight, quantity, memberType, accNo, request, cData);
				}
				
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
				logger.info("--- Consignment is Closed: " + cn);
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
				logger.info("--- No Connection");
			} else { // -1
				result = "NoSuchConsignment";
				logger.info("--- No Such Consignment: " + consignmentNo);
			}
			
			logger.info("*** Result: " + result);
			
			jsonObj.put("result", result);
				
			
    	//}else{
    	//	jsonObj.put("result", "Access Denied, please re-login and try again.");
    	//}
		
		String jsonText = JSONValue.toJSONString(jsonObj);
		out.print(jsonText);
	}
    
    else if(actionType.equals(VISUALCHECK) == true){
    	int currentstage = Integer.parseInt(request.getParameter("currentstage") == null ? "-1" : request.getParameter("currentstage").equals("") ? "-1" : request.getParameter("currentstage").toString());
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();

		String result = "";
		
		if(privilege >= 5) { //內部員工
			
			//TODO: 如果可以，再檢查該員工是否有該stage的權限（雙重保護）
			
			
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
			
			int stage = ConsignmentBusinessModel.checkStage(cn);
		
			if( (stage >= 0 && stage < 8) || (stage == 9) ) { //仍然有效
				result = ConsignmentBusinessModel.visualCheck(currentstage, cn);
			} else if(stage == 8) { //已完成遞交
				result = "Closed";
			}  else if(stage == -2) { //No Connection
				result = "NoConnection";
			} else { // -1
				result = "NoSuchConsignment";
			}
    	}
		
	    out.print(result);
	}

    
    else if(actionType.equals(CHANGESTATUS) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
 
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString(); //只處理當下處理的那一筆訂位
    	int status = Integer.parseInt(request.getParameter("status") == null ? "-1" : request.getParameter("status").toString().trim());
    	String payDeadline = request.getParameter("payDeadline") == null ? "" : request.getParameter("payDeadline").toString().trim();
    	String reasonPending = request.getParameter("reasonPending") == null ? "" : request.getParameter("reasonPending").toString().trim();
    	String reasonText = request.getParameter("reasonText") == null ? "" : request.getParameter("reasonText").toString();
    	String result = "";
    	String returnValue = "";
    	
    	if(consignmentNo.length() == 9){
			try {
				returnValue = ConsignmentBusinessModel.updateStatus(consignmentNo, status, payDeadline, reasonPending, reasonText, userId, request); //update consignment
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				returnValue = e.toString();
			}

			if(returnValue.equals("OK")){
				result = "updateSuccess";
			} else {
				result = returnValue;
			}
    		
    		
    		if(returnValue.equals("OK") && status == 1){ //狀態：已確認, 將發送訂位成功的email
    			
    			//產生PDF(PdfController也有一個actionType,寫法和這裡一樣,如修改以下程式必須也要修改那裡)
    			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
    			consignmentData = ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo);
				  
				if(consignmentData != null && consignmentData.size() == 1){ //必須只有1筆
//					String generateResult = PdfBusinessModel.generatePDF(request, consignmentData); //TODO: 產生PDF
					
					String generateResult = "OK";
					
	    			//寄送email+pdf給訂戶
//					String sendResult = ConsignmentBusinessModel.sendConfirmEmail(request, consignmentData);
//					if(generateResult.equals("OK") && sendResult.equals("OK")){
//						result = "updateNemail";
//					} else if(sendResult.equals("noData")){
//						result = "noData";
//					} else if(!sendResult.equals("OK")){
//						result = "emailFailed";
//					} else {
//						result = returnValue;
//					}
					
				}
				else if(consignmentData != null && consignmentData.size() > 1){ 
					logger.error("*** Identical Consignment Notes are found... No email sent.***");
					result = "emailFailed";
				}
				else {
					result = "noData";
				}
				
    		}
    		
    		else if(returnValue.equals("OK") && status == 5){ //狀態：等待付款, 將發送通知email
    			
//    			//產生PDF(PdfController也有一個actionType,寫法和這裡一樣,如修改以下程式必須也要修改那裡)
//    			ArrayList<BookingDataModel> bookingData = new ArrayList<BookingDataModel>();
//    			if(bookBy.equals("b2b")) {
//    				bookingData = BookingBusinessModel.getB2BRecordByBookingCode(bookingCode, bookingType);
//    			} else {
//    				bookingData = BookingBusinessModel.getRecordByBookingCode(bookingCode, bookingType);
//    			}
//				  
//				if(bookingData != null && bookingData.size() == 1){ //必須只有1筆
//					
//	    			//寄送通知email給訂戶
//					String sendResult = BookingBusinessModel.sendAwaitingPaymentEmail(request, bookingData, bookingType);
//					if(sendResult.equals("OK")){
//						result = "updateNemail";
//					} else if(sendResult.equals("noData")){
//						result = "noData";
//					} else if(!sendResult.equals("OK")){
//						result = "emailFailed";
//					} else {
//						result = returnValue;
//					}
//					
//				}
//				else if(bookingData != null && bookingData.size() > 1){ 
//					logger.error("*** Identical Booking Codes are found... No email sent.***");
//					result = "emailFailed";
//				}
//				else {
//					result = "noData";
//				}
				
    		}


    		//狀態：Reached Receiver, 將發送 email 通知寄件者
    		//這個功能做在 UploadController.java  line 132:   else if(Integer.parseInt(stage) == 7)

    	}
    	
    	out.print(result);
    }
    
    
    else if(actionType.equals(STATION) == true){ 
    	
    	if(!checkPrivilege(response, privilege)){ //not authorized
    		return;
    	}
		
    	//Driver List
        ArrayList<LogonDataModel> driver = new ArrayList<LogonDataModel>();
        driver = LogonBusinessModel.driverList();
        request.setAttribute("driver", driver);
        
        //Staff List
        ArrayList<LogonDataModel> staff = new ArrayList<LogonDataModel>();
        staff = LogonBusinessModel.staffList();
        request.setAttribute("staff", staff);
        
        //Area List
        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
        area = AreaBusinessModel.areaList();
        request.setAttribute("area", area);

		String url = "./cp/cpStation.jsp";
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
    
    else if(actionType.equals(AGENTSTATION) == true){ 
    	
    	if(privilege != 3){ //not agent
    		return;
    	}

    	//Driver List
        ArrayList<LogonDataModel> driver = new ArrayList<LogonDataModel>();
        driver = LogonBusinessModel.driverList();
        request.setAttribute("driver", driver);
        
        //Staff List
        ArrayList<LogonDataModel> staff = new ArrayList<LogonDataModel>();
        staff = LogonBusinessModel.staffList();
        request.setAttribute("staff", staff);
        
        //Area List
        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
        area = AreaBusinessModel.areaList();
        request.setAttribute("area", area);

        String url = "./partner/cpStation.jsp";
			
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);

        return;
    }
    
    
    
    else if(actionType.equals(INLINEUPDATE) == true){
    	
    	if(!checkPrivilege(response, privilege)&&(privilege != 3)){ //if no privilege or not an agent
    		return;
    	}
    	
    	String field = request.getParameter("field") == null ? "" : request.getParameter("field").toString();
    	String value = request.getParameter("value") == null ? "" : request.getParameter("value").trim().replaceAll("\\u00A0", "").toString(); //u00A0 is &nbsp;
    	String consignmentNo = request.getParameter("name") == null ? "" : request.getParameter("name").toString();
    	String dataType = request.getParameter("dataType") == null ? "" : request.getParameter("dataType").toString();
//    	String discount = request.getParameter("discount") == null ? "" : request.getParameter("discount").toString();
    	double oriAmount = Double.parseDouble(request.getParameter("oriAmount") == null ? "0.0" : request.getParameter("oriAmount").toString());
    	String result = "";

    	if(consignmentNo.indexOf("_") != 0){
    		consignmentNo = consignmentNo.substring(consignmentNo.indexOf("_") + 1, consignmentNo.length());
    	}
    	
//    	if(discount != "" || discount == "yes"){
//    		value = ConsignmentBusinessModel.discountForAmount(value, consignmentNo);
//    	}
    	
    	result = ConsignmentBusinessModel.inlineUpdate(field, value, consignmentNo, dataType, userId, oriAmount);
    	out.print(result);
    }
    
    else if(actionType.equals(CHANGEAMOUNT) == true){
    	
    	if(!checkPrivilege(response, privilege) && (privilege != 3)){ //not authorized & not agent
    		return;
    	}
    	
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
    	String result = "";
    	
    	double currentAmount = ConsignmentBusinessModel.getCurrentAmount(consignmentNo);
    	double amount = Double.parseDouble(request.getParameter("amount") == null ? "0.0" : request.getParameter("amount").toString());
    	
    	if(privilege==9 || privilege==99 || privilege==3){// Accountant OR Admin OR agent
        	if(currentAmount != amount){
        		
        		int agentId = 0;
        		if(privilege == 3){ //agent
        			agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
        		}
        		
        		result = ConsignmentBusinessModel.changeAmount(amount, "Price had been changed to RM "+amount, consignmentNo, userId, agentId);
        		if(result == "OK"){
        			LogBusinessModel.insertChangeAmountLog(consignmentNo, currentAmount, 0, "Price had been changed to RM "+amount, userId);
//        			NotificationBusinessModel.insertNewStaffNotification("consignment", "Consignment Discount", consignmentNo);
        		}
        		
        	}
    	}
		
    	out.print(result);
    }
    
    else if(actionType.equals(SUBMITDISCOUNT) == true){

    	if(!checkPrivilege(response, privilege) && (privilege != 3)){ //not authorized & not agent
    		return;
    	}

    	int discount = Integer.parseInt(request.getParameter("discount") == null ? "0" : request.getParameter("discount") == "" ? "0" : request.getParameter("discount").toString());
    	String discountReason = request.getParameter("discountReason") == null ? "" : request.getParameter("discountReason").toString();
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
    	double discounted = 0.0;
    	String result = "";
    	
    	double currentAmount = ConsignmentBusinessModel.getCurrentAmount(consignmentNo);
    	//double amount = Double.parseDouble(request.getParameter("amount") == null ? "0.0" : request.getParameter("amount").toString());

    	if (discount != 0 && discountReason != "") {
			
    		discounted = ConsignmentBusinessModel.discountForAmount(discount, currentAmount);
    		
    		int agentId = 0;
    		if(privilege == 3){ //agent
    			agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    		}
    		
    		if(discounted != 0.0){
	    		result = ConsignmentBusinessModel.changeAmount(discounted, discountReason, consignmentNo, userId, agentId);
	    		if(result == "OK"){
	    			LogBusinessModel.insertChangeAmountLog(consignmentNo, currentAmount, discount, discountReason, userId);
//					NotificationBusinessModel.insertNewStaffNotification("consignment", "Consignment Discount", consignmentNo);
	    		}
	        	out.print(result);
	        	
    		}else {
    			out.print("Amount is 0.0");
    		}
    		
    	} else {
    		out.print("noData");
    	}
    	
    }
    
    else if(actionType.equals(CREATE) == true){
    	String url = "";

        if(currentUser.isAuthenticated()) { //還有 session
        	
			url = "./create.jsp";
			
			//area
			ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
			area = AreaBusinessModel.areaList();
		    request.setAttribute("area", area);
		    	
		    if(privilege == 2) { //credit user
		    	
		    	int mid = MyBusinessModel.getMidByUserId(userId);
		    	
			    //credit account
		  		ArrayList<MyDataModel> creditaccount = new ArrayList<MyDataModel>();
		  		creditaccount = MyBusinessModel.creditaccountList(mid);
		  	    request.setAttribute("creditaccount", creditaccount);
		    }
		  	    
		    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		    dispatcher.forward(request, response);
        }else{
        	url = "./login.jsp?returnUrl=./create";
    		response.sendRedirect(url);
    	}
        return;
	}
    
    
    else if(actionType.equals(CREATECONSIGNMENT) == true){
	       
    	String result = "";
    	String url = "";
    	String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
//    	int freightType =Integer.parseInt(request.getParameter("freightType") == null ? "0" : request.getParameter("freightType").toString());
    	
    	//if(userId.length() > 0) { //還有 session
        if(currentUser.isAuthenticated()) { //還有 session
    	
	    	if(verify.trim().equals("")){
	    		verify = ConsignmentBusinessModel.generateVerifyCode();
	    	}
	    	
//	    	//取得目前使用中的 Pricing
//	    	int pricing = PricingBusinessModel.getCurrentPricing(freightType);
	    	
	    	HashMap<String, Object> consignmentData = new HashMap<String, Object>();
	    	Enumeration<String> paramNames = request.getParameterNames();
	    	consignmentData.put("userId", userId);
	    	consignmentData.put("verify", verify);
	    	consignmentData.put("staffCreate", "0");
//	    	consignmentData.put("pricing", pricing);
	    	
	        while(paramNames.hasMoreElements()) {
	        	String paramName = (String)paramNames.nextElement();
	        	String[] paramValues = request.getParameterValues(paramName);
	
		        for(int i=0; i<paramValues.length; i++) {
		        	consignmentData.put(paramName, paramValues[i]);
		        }
	        }
	        
	        String paymentType = Integer.parseInt((String) consignmentData.get("payMethod")) == 4 ? "credit" : "normal";
	        int shipmentType = Integer.parseInt((String) consignmentData.get("shipmentType"));
	        double weight = Double.parseDouble((String) consignmentData.get("weight"));
	        int quantity = Integer.parseInt((String) consignmentData.get("quantity"));
	        String senderAid = (String) consignmentData.get("senderArea");
	        String receiverAid = (String) consignmentData.get("receiverArea");
	        String accNo = (String)(consignmentData.get("accNo") == null ? "" : consignmentData.get("accNo"));
	        
	        String senderAreaCode = "";
	        ArrayList<AreaDataModel> senderAreaData = AreaBusinessModel.areaDetails(senderAid);
	        if(!senderAreaData.isEmpty()){
	        	AreaDataModel aData = new AreaDataModel();
	        	aData = senderAreaData.get(0);
				
				String aid = String.format("%06d", aData.getAid());//補足6碼
				if(aData.getIsMajor()==1){
					aid += "A000000";
				}else{
					aid += "B" + String.format("%06d", aData.getBelongArea());//補足6碼
				}
				aid += aData.getState();//州屬
				senderAreaCode = aid;
	        }
	        
	        String receiverAreaCode = "";
	        ArrayList<AreaDataModel> receiverAreaData = AreaBusinessModel.areaDetails(receiverAid);
	        if(!receiverAreaData.isEmpty()){
	        	AreaDataModel aData = new AreaDataModel();
	        	aData = receiverAreaData.get(0);
				
				String aid = String.format("%06d", aData.getAid());//補足6碼
				if(aData.getIsMajor()==1){
					aid += "A000000";
				}else{
					aid += "B" + String.format("%06d", aData.getBelongArea());//補足6碼
				}
				aid += aData.getState();//州屬
				receiverAreaCode = aid;
	        }
	        
	        String calcResult = "";
        	if(paymentType.equals("normal")) {
				
				if(shipmentType == 1) { //document
					calcResult = PricingBusinessModel.calculate_normal_document(senderAreaCode, receiverAreaCode, weight, 0, 0);
				} else if(shipmentType == 2) { //parcel
					calcResult = PricingBusinessModel.calculate_normal_parcel(senderAreaCode, receiverAreaCode, weight, 0, 0, quantity);
				} else if(shipmentType == 3) { //
					calcResult = PricingBusinessModel.calculate_normal_parcel(senderAreaCode, receiverAreaCode, weight, 0, 0, quantity);
				}
				
			} else if(paymentType.equals("credit")) {
				
				String discPack = "";
				
				if(accNo.trim().length() > 0) { //有 accNo
					discPack = LogonBusinessModel.checkDiscPack(accNo, "accNo");
				} else if (userId.length() > 0) { //如果有登入
					discPack = LogonBusinessModel.checkDiscPack(userId, "userId");
				}
				
				if(shipmentType == 1) { //document
					calcResult = PricingBusinessModel.calculate_credit_document(senderAreaCode, receiverAreaCode, weight, 0, 0, discPack);
				} else if(shipmentType == 2) { //parcel
					calcResult = PricingBusinessModel.calculate_credit_parcel(senderAreaCode, receiverAreaCode, weight, 0, 0, quantity, discPack);
				}
			}
//        	logger.info("calcResult ready to be split: " +calcResult);
//        	String[] amount = calcResult.split("|", 3); // doesnt work as expected for some reason
//        	logger.info("split calcResult length: " + amount.length);
        	String disAmount = calcResult.substring(0, calcResult.indexOf("|"));
        	if(disAmount.length() > 0){
//        		logger.info(disAmount);
            	consignmentData.put("amount", disAmount);
        	}
        	
	                
	        String consignmentNo = ConsignmentBusinessModel.generateConsignmentNo();
			result = ConsignmentBusinessModel.insertConsignment(consignmentData, consignmentNo);//寫入資料庫
	        	
			if(result.equals("OK")){ //success
	
				NotificationBusinessModel.insertNewStaffNotification("consignment", "New Consignment", consignmentNo);
				
				if(consignmentData.get("promoCode") != null && !(consignmentData.get("promoCode").toString()).equals("")){
					String result2 = ConsignmentBusinessModel.addPromoCode(consignmentData.get("promoCode").toString(), consignmentNo);//Add promoCode
					
					// TODO: Do discount stuff
					//if(result2.equals("OK")){ }
				}
				
				//產生PDF(PdfController和UploadController也有一個actionType,寫法和這裡一樣,如修改以下程式必須也要修改那裡)
				ArrayList<ConsignmentDataModel> newData = new ArrayList<ConsignmentDataModel>();
				newData = ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo); //重新查詢
				
				String generateResult = PdfBusinessModel.generateConsignmentNote(request, newData); //產生PDF
				
				if(generateResult.equals("OK")){
					result = "generated";
				} else {
					result = generateResult;
				}

				// Generate new PICKUP REQUEST
				PickupBusinessModel.insertRequest(consignmentNo, "", "");// leave driver & assigner empty
				
				//檢查是否有draft，有的話要標註 isDel = 1
				ConsignmentBusinessModel.deleteDraft(verify, userId);
				
				url = "./my?result=createSuccess";
				
				response.sendRedirect(url);
				
			} else { //失敗, 導回填寫資料頁
				
				//area
				ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
				area = AreaBusinessModel.areaList();
	
			    if(area != null && !area.isEmpty()){
			    	request.setAttribute("area", area);
			    }
				
		        consignmentData.put("errmsg", result);
				request.setAttribute(OBJECTDATA2, consignmentData);
	        	
	        	url = "./create.jsp?source=draft&result=createFailed";
	        	
	        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	            dispatcher.forward(request, response);
			}
			
    	} else {
    		url = "./login.jsp?returnUrl=./create";
    		response.sendRedirect(url);
    	}
		
        return;
    }
    
    
    else if(actionType.equals(SAVEDRAFT) == true){
	       
    	String result = "";
    	String url = "";
    	HashMap<String, Object> consignmentData = new HashMap<String, Object>();
    	
    	Enumeration<String> paramNames = request.getParameterNames();
    	
        while(paramNames.hasMoreElements()) {
        	String paramName = (String)paramNames.nextElement();
        	String[] paramValues = request.getParameterValues(paramName);

	        for(int i=0; i<paramValues.length; i++) {
	        	consignmentData.put(paramName, paramValues[i]);
	        }
        }
        
        consignmentData.put("userId", userId);
    	consignmentData.put("verify", ConsignmentBusinessModel.generateVerifyCode());
                
		result = ConsignmentBusinessModel.insertDraft(consignmentData);//寫入資料庫
        	
		if(result.equals("OK")){ //成功
			
			url = "./draft?result=savedraftSuccess";
			response.sendRedirect(url);
			
		} else { //失敗, 導回填寫資料頁
			
			//area
			ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
			area = AreaBusinessModel.areaList();

		    if(area != null && !area.isEmpty()){
		    	request.setAttribute("area", area);
		    }
			
	        consignmentData.put("errmsg", result);
			request.setAttribute(OBJECTDATA2, consignmentData);
        	
        	url = "./create.jsp?source=draft&result=savedraftFailed";
        	
        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
		}
		
        return;
    }
    
    
    
    else if(actionType.equals(UPDATEDRAFT) == true){
	       
    	String result = "";
    	String url = "";
    	HashMap<String, Object> consignmentData = new HashMap<String, Object>();
    	
    	Enumeration<String> paramNames = request.getParameterNames();
    	consignmentData.put("userId", userId);
    	
        while(paramNames.hasMoreElements()) {
        	String paramName = (String)paramNames.nextElement();
        	String[] paramValues = request.getParameterValues(paramName);

	        for(int i=0; i<paramValues.length; i++) {
	        	consignmentData.put(paramName, paramValues[i]);
	        }
        }
                
		result = ConsignmentBusinessModel.updateDraft(consignmentData);//更改資料庫
        	
		if(result.equals("OK")){ //成功
			
			url = "./draft?result=updatedraftSuccess";
			response.sendRedirect(url);
			
		} else { //失敗, 導回填寫資料頁
			
			//area
			ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
			area = AreaBusinessModel.areaList();

		    if(area != null && !area.isEmpty()){
		    	request.setAttribute("area", area);
		    }
			
	        consignmentData.put("errmsg", result);
			request.setAttribute(OBJECTDATA2, consignmentData);
        	
        	url = "./create.jsp?source=draft&result=updatedraftFailed";
        	
        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
		}
		
        return;
    }
    
    
    
    else if (actionType.equals(DRAFT) == true) {
    	
    	String url = "";
    	
    	if(!userId.trim().equals("")) { //有登入
    		
    		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
        	data = ConsignmentBusinessModel.draftList(userId); //查詢所有draft

            if(data != null && !data.isEmpty()){
            	
            	//如果RESULT有值代表儲存DB後再進入這裡
                if(!RESULT.equals("")){
                	ConsignmentDataModel obj = (ConsignmentDataModel)data.get(0);
            		obj.setErrmsg(RESULT); //加入訊息的標籤
            		data.set(0, obj); //再設入
            		RESULT = ""; //清除掉記錄
                }
                
            	request.setAttribute(OBJECTDATA, data);
            }

    		url = "./myDraft.jsp";
    		
    	} else {
    		url = "./login.jsp?returnUrl=./draft";
    	}

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		return;
	}
    
    
    
	else if (actionType.equals(EDITDRAFT) == true) {
    	
    	String url = "";
    	String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
    	
    	if(!userId.trim().equals("")) { //有登入
    		
    		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
        	data = ConsignmentBusinessModel.getDraft(verify); //找出對應的draft
        	
        	if(data != null && !data.isEmpty()){
        		ConsignmentDataModel obj = (ConsignmentDataModel)data.get(0);
        		
        		HashMap<String, Object> consignmentData = new HashMap<String, Object>();
            	consignmentData.put("senderName", obj.getSenderName());
            	consignmentData.put("senderAddress1", obj.getSenderAddress1());
            	consignmentData.put("senderAddress2", obj.getSenderAddress2());
            	consignmentData.put("senderAddress3", obj.getSenderAddress3());
            	consignmentData.put("senderPhone", obj.getSenderPhone());
//            	consignmentData.put("senderPersonalName", obj.getSenderPersonalName());
            	consignmentData.put("senderIC", obj.getSenderIC());
            	consignmentData.put("receiverName", obj.getReceiverName());
            	consignmentData.put("receiverAddress1", obj.getReceiverAddress1());
            	consignmentData.put("receiverAddress2", obj.getReceiverAddress2());
            	consignmentData.put("receiverAddress3", obj.getReceiverAddress3());
            	consignmentData.put("receiverPostcode", obj.getReceiverPostcode());
            	consignmentData.put("receiverZone", obj.getReceiverZone());
            	consignmentData.put("receiverPhone", obj.getReceiverPhone());
            	consignmentData.put("receiverArea", obj.getReceiverArea());
            	consignmentData.put("helps", obj.getHelps());
            	consignmentData.put("tickItem", obj.getTickItem());
            	consignmentData.put("shipmentType", obj.getShipmentType());
            	consignmentData.put("quantity", obj.getQuantity());
            	consignmentData.put("payMethod", obj.getPayMethod());
            	consignmentData.put("creditArea", obj.getCreditArea());
            	consignmentData.put("useLocale", obj.getUseLocale());
            	consignmentData.put("verify", obj.getVerify());
            	
            	request.setAttribute(OBJECTDATA2, consignmentData);
            	
            	//組成 area dropdown list
            	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
        		area = AreaBusinessModel.areaList();
        	    request.setAttribute("area", area);

        	    
        	    //credit account
        	    if(privilege == 2) { //credit user
        	    	
        	    	int mid = MyBusinessModel.getMidByUserId(userId);
        	  		ArrayList<MyDataModel> creditaccount = new ArrayList<MyDataModel>();
        	  		creditaccount = MyBusinessModel.creditaccountList(mid);
        	  	    request.setAttribute("creditaccount", creditaccount);
        	    }
            	
            	url = "./create.jsp?source=draft";
        	}

    	} else {
    		url = "./login.jsp?returnUrl=./draft";
    	}

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		return;
	}
    
    
    else if(actionType.equals(AJAXGETADDRESSBOOK) == true){

		String result = "";
		String bookType = request.getParameter("bookType") == null ? "receiver" : request.getParameter("bookType").toString();
		String locale = request.getParameter("locale") == null ? "en_US" : request.getParameter("locale").toString();
		
		if(privilege > 0) { //是會員
			result = ConsignmentBusinessModel.getAddressbookByUserId(userId, bookType, locale);
    	}
		
	    out.print(result);
	}
    
    
    else if(actionType.equals(TRACE) == true){

		String result = "";
		String consignmentNo = "";
		String cn = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
		String locale = request.getParameter("locale") == null ? "en_US" : request.getParameter("locale").toString();
		
		logger.info("Guest is tracing: " + cn);

		boolean ownConsignment = ConsignmentBusinessModel.isValidConsignmentNo(cn);
		
		if(ownConsignment) {
			consignmentNo = cn;
		} else { //可能是 generalCargo 的
			consignmentNo = ConsignmentBusinessModel.matchGeneralCargoNo(cn);
		}

		if(consignmentNo.trim().length() == 9) {
			result = ConsignmentBusinessModel.trace(consignmentNo, locale);
		} else {
			result = "<p class=\"text-center\"><i class=\"fa fa-times red\"></i> <font color=\"red\">" +Resource.getString("ID_MSG_INVALIDCN", locale) + "</font></p>";
		}
		
	    out.print(result);
	}  
    
    
    
    
  }

   
  	
    
    //檢查是否具備足夠權限使用本項目
    private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
    	int iid = 1; //必須自行手動設定，對應CP的privilege
    	String url = "./stafflogin?returnUrl=./consignment";
	
  		if(PrivilegeBusinessModel.determinePrivilege(iid, privilege)){
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