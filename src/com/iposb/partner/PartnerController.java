package com.iposb.partner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.simple.parser.ParseException;

import com.iposb.area.AreaBusinessModel;
import com.iposb.area.AreaDataModel;
import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.consignment.ConsignmentDataModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.logon.LogonDataModel;
import com.iposb.pricing.PricingBusinessModel;
import com.iposb.pricing.PricingDataModel;
import com.iposb.privilege.PrivilegeBusinessModel;
import com.iposb.utils.GoogleRecaptcha;
import com.iposb.utils.IPTool;
import com.iposb.utils.Mailer;



public class PartnerController extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

  	//Initialize global variables
  	public static final String OBJECTDATA = "objectData";
  	public static final String OBJECTDATA2 = "objectData2";
  	public static final String OBJECTDATA3 = "objectData3";
  	public static final String REGISTERPARTNER = "registerPartner";
	public static final String REGISTER = "register";
  	public static final String PARTNERLOGIN = "partnerlogin";
  	public static final String PARTNERLOGOUT = "partnerlogout";
    public static final String SENDNEWPASSWORD = "sendNewPassword";
    
    public static final String COMPANYPROFILE = "companyprofile";
    public static final String UPDATEPROFILE = "updateProfile";
    public static final String CHANGEPASS = "changepass";
    public static final String UPDATEPASS = "updatepass";
    
    public static final String ISEXIST = "isExist";
    public static final String CONTROLPANEL = "controlpanel"; //partner control panel
    public static final String CPPARTNER = "cppartner";
    public static final String CPPARTNER_EDIT = "cppartner_edit";
    public static final String CPPARTNER_UPDATE = "cppartner_update";
    
    public static final String PARTNERCONSIGNMENT = "partnerConsignment";
    public static final String PENDING = "pending";
    public static final String SEARCHAGENTCONSIGNMENT = "searchAgentConsignment"; //agent search consignment
    public static final String SEARCHPARTNERCONSIGNMENT = "searchPartnerConsignment"; //partner search consignment
    
    private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(PartnerController.class);
	static String resPath = "https://static.iposb.com";
  
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
    
    PrintWriter out = response.getWriter();
    
    HttpSession session = request.getSession(true);
    
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
    String locale = request.getParameter("lang");
 
    
    if(actionType == null){
    	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl");
    	String url = "./partnerLogin?returnUrl="+returnUrl;
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
    } 

    
    else if(actionType.equals(PARTNERLOGIN) == true){
    	String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
    	String passwd = request.getParameter("passwd") == null ? "" : request.getParameter("passwd");
    	String stayloggedin = request.getParameter("stayloggedin") == null ? "" : request.getParameter("stayloggedin");
    	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl");
    	String IP = IPTool.getUserIP(request);
    	String url = "./partnerCP";
		boolean loginSucess = false;
		int priv = -9;
    	
		// Shiro Login
		if (!currentUser.isAuthenticated()) {

			if (currentUser.isRemembered()) {
				currentUser.logout(); // 清除 session
			}
			
			boolean rememberMe = false;
			if (stayloggedin.equals("on")) {
				rememberMe = true;
			}
			// create UsernamePasswordToken
			UsernamePasswordToken token = new UsernamePasswordToken(loginId, passwd);
			token.setRememberMe(rememberMe);
			
			try {
				currentUser.login(token); // 執行shiro.ini裡的查詢
				token.clear();
				
				
				if(rememberMe) { //TODO: 因為 rememberMe 無效，所以暫時手動設 session 為1個月
					currentUser.getSession().setTimeout(30 * 24 * 60 * 60 * 1000);
				}


				if (currentUser.hasRole("99")) {
					priv = 99;
				} else if (currentUser.hasRole("9")) {
					priv = 9;
				} else if (currentUser.hasRole("8")) {
					priv = 8;
				} else if (currentUser.hasRole("7")) {
					priv = 7;
				} else if (currentUser.hasRole("6")) {
					priv = 6;
				} else if (currentUser.hasRole("5")) {
					priv = 5;
				} else if (currentUser.hasRole("4")) {
					priv = 4;
				} else if (currentUser.hasRole("3")) {
					priv = 3;
				} else if (currentUser.hasRole("2")) {
					priv = 2;
				} else if (currentUser.hasRole("1")) {
					priv = 1;
				} else if (currentUser.hasRole("0")) { // Needs Activate
					priv = 0;
				} else if (currentUser.hasRole("-9")) { //disabled
					priv = -9;
				}
				
				String modifyDT = LogonBusinessModel.getDataById("modifyDT", "partnerlist", loginId);
				currentUser.getSession().setAttribute("modifyDT", !modifyDT.equals("") ?modifyDT.trim().replace(" ", "_") : "");
				currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", "partnerlist", loginId));
				currentUser.getSession().setAttribute("id", LogonBusinessModel.getDataById("id", "partnerlist", loginId));
	        	PartnerBusinessModel.updateIP(loginId, IP);

				loginSucess = true;

			} catch (UnknownAccountException uae) {
				logger.info("There is no user with userId of " + token.getPrincipal());
			} catch (IncorrectCredentialsException ice) {
				logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
			} catch (LockedAccountException lae) {
				logger.info("The account for userId " + token.getPrincipal() + " is locked.  " + "Please contact your administrator to unlock it.");
			} catch (AuthenticationException ae) {
				logger.error("ERROR: " + ae);
			}

			if (loginSucess) {

				if (returnUrl.trim().equals("")) {
					if  ( (priv == 5)||(priv == 6)||(priv == 7)||(priv == 8)||(priv == 9)||(priv == 99) ) {
						url = "./cp";
						logger.info("--- Staff login success thru Partner Login Page: " + loginId);
					} else if (priv == 4) {
						url = "./partnerCP";
						logger.info("--- Partner login success thru Partner Login Page: " + loginId);
					} else if (priv == 3) {
						url = "./agentCP";
						logger.info("--- Agent login success thru Partner Login Page: " + loginId);
					} else if ((priv == 1)||(priv == 2) ) {
						url = "./my";
						logger.info("--- Member login success thru Partner Login Page: " + loginId);
					}
				} else {
					url = returnUrl;
				}
				
			} else {
				logger.info("--- Partner login failed: " + loginId);
				session.setAttribute("sessionMsg", "loginFailed");

				url = "./partnerlogin";
			}
			
		}
		if (currentUser.hasRole("0")) url = "./activateAccount.jsp"; // wait management to approve
		
		response.sendRedirect(url);
		return;
    }
    
    else if(actionType.equals(PARTNERLOGOUT) == true){
    	
		if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
	    	logger.info("--- Partner logout: " + userId);
			currentUser.logout();
		}
		
		session = request.getSession(true);
		session.setAttribute("sessionMsg", "logoutSuccess");
    	
		String url = "./partnerlogin";
		
		response.sendRedirect(url);
		return;
        
    }
    
    
    else if(actionType.equals(REGISTERPARTNER) == true){
  		String url = "/registerPartner.jsp";
  		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
  		dispatcher.forward(request, response);
  		return;
  	} 
    
	else if(actionType.equals(REGISTER) == true){
	  	
		ArrayList<PartnerDataModel> userData = new ArrayList<PartnerDataModel>();
	    userData = PartnerBusinessModel.setRequestData(request);
   
	  	String result = "";
	  	String url = "";
	  	boolean isValid;
	  	
	  	String captchatoken = request.getParameter("captchatoken") == null ? "" : request.getParameter("captchatoken");
	  	
		try {
			
			isValid = GoogleRecaptcha.isValid(captchatoken);
			
			if (isValid) {
				result = PartnerBusinessModel.addNewPartner(userData);				
				if(result.equals("OK")){ //成功
					PartnerDataModel obj = (PartnerDataModel)userData.get(0);//拿回出來用
					obj.setErrmsg("registerSuccess");
		        	userData.set(0, obj); //加入錯誤訊息的標籤
					request.setAttribute(OBJECTDATA, userData);
					Mailer.sendEmailToAdmin("New Partner Signup", "admin@iposb.com"); //發送通知給管理員
					url = "./registerPartner.jsp";
				} else { //失敗
					PartnerDataModel obj = (PartnerDataModel)userData.get(0);//拿回出來用
		        	obj.setErrmsg(result);
		        	userData.set(0, obj); //加入錯誤訊息的標籤
					request.setAttribute(OBJECTDATA, userData);
					url = "./registerPartner.jsp";
				}
		        
			} else {

				if(userData.size() > 0){
		        	PartnerDataModel obj = (PartnerDataModel)userData.get(0);//拿回出來用
		        	logger.info("********* Secure Code is NULL, please contact the partner about something wrong to the system and try it later: " + obj.getEmail());
		        	
		        	obj.setErrmsg("secureCodeNull");
		        	userData.set(0, obj); //加入錯誤訊息的標籤
					request.setAttribute(OBJECTDATA, userData);
				}
				url = "./registerPartner.jsp";
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
	  	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
	}
    
	  
	else if (actionType.equals(SENDNEWPASSWORD) == true) {

		String email = request.getParameter("email") == null ? "" : request.getParameter("email").toString().trim();
		String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString().trim();

		String result = "";
		ArrayList<PartnerDataModel> userData = new ArrayList<PartnerDataModel>();
		if (role.equals("partner")) {
			userData = PartnerBusinessModel.getPartnerData(email);
		}

		if (userData != null && userData.size() == 1) {
			String pw = Mailer.getPassword(4);
			int upd = 0;
			if (role.equals("partner")) {
				upd = LogonBusinessModel.updatePartnerPassword(email, pw);
			}

			if (upd == 1) {
				result = Mailer.sendNewPasswordEmail(pw, email, locale);
				if(result.equals("OK")) {
					logger.info("--- reset sucessfully & mailed to user: "+email);
				} else {
					logger.info("--- reset sucessfully but mailed failed: "+email);
				}
			} else {
				result = "--- updateFailed";
				logger.info("--- reset failed: "+email);
			}
		} else {
			logger.info("--- Found no data... Reset failed: "+email);
		}

		out.print(result);
	}
    
    
	else if (actionType.equals(COMPANYPROFILE) == true) {

		String url = "";

		if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
			ArrayList<PartnerDataModel> userData = new ArrayList<PartnerDataModel>();
			
			userData = PartnerBusinessModel.getPartnerData(userId);

			if (userData != null && !userData.isEmpty()) {
				request.setAttribute(OBJECTDATA, userData);
				url = "./profilePartner.jsp?role=partner";
			} else { // 沒有資料
				PartnerDataModel obj = new PartnerDataModel();
				obj.setErrmsg("queryFailed");
				userData = new ArrayList<PartnerDataModel>();
				// userData.set(0, obj); //加入錯誤訊息的標籤
				request.setAttribute(OBJECTDATA, userData);
				url = "./partnerlogin?returnUrl=./companyprofile";
			}

		} else { // cookie失效
			url = "./partnerlogin?returnUrl=./companyprofile";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		return;
	}
    
    
	else if(actionType.equals(UPDATEPROFILE) == true){ //Logon 那裡有類似的寫法，改這裡要順便改那裡
	  	
		ArrayList<PartnerDataModel> userData = new ArrayList<PartnerDataModel>();
	    userData = PartnerBusinessModel.setRequestData(request);
	      
	  	int result = 0;
	  	
	  	logger.info("--- Partner is updating profile: " + userId);
	  	
	  	String url = "";

		// Shiro Login
		if (currentUser.isAuthenticated()) {
			result = PartnerBusinessModel.updatePartner(userId, userData);
		}
		
		if(result == 1) { //update 成功
			String modifyDT = LogonBusinessModel.getDataById("modifyDT", "partnerlist", userId);
			currentUser.getSession().setAttribute("modifyDT", !modifyDT.equals("") ?modifyDT.trim().replace(" ", "_") : "");
			currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", "partnerlist", userId)); // Get new username
			currentUser.getSession().setAttribute("id", LogonBusinessModel.getDataById("id", "partnerlist", userId));
			
			url = "./profilePartner.jsp";
			userData = PartnerBusinessModel.getPartnerData(userId); //重新再取資料
			PartnerDataModel obj = (PartnerDataModel)userData.get(0);
			obj.setErrmsg("accountUpdated");
			
			userData.set(0, obj); //加入訊息的標籤
			request.setAttribute(OBJECTDATA, userData);
			
		} else {
						
			url = "./profilePartner.jsp";
			PartnerDataModel obj = (PartnerDataModel)userData.get(0);
			
			if(result == 0){ //失敗
				obj.setErrmsg("updateFailed");
			} else if(result == -1){ //密碼不正確
				obj.setErrmsg("wrongPassword");
			} else if(result == -2){ //帳號或電郵已被使用
				obj.setErrmsg("userIdEmailExist");
				logger.info("--- profile userId / Email Exist");;
			}
			
			userData.set(0, obj); //加入錯誤訊息的標籤
			request.setAttribute(OBJECTDATA, userData);
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;

	}
    
    
else if(actionType.equals(CHANGEPASS) == true){
    	
    	String url = "";

		// Shiro Login
		if (currentUser.isAuthenticated()) {
	    	ArrayList<PartnerDataModel> userData = new ArrayList<PartnerDataModel>();
	    	userData = PartnerBusinessModel.getPartnerData(userId);
	
	        if(userData != null && !userData.isEmpty()){
				request.setAttribute(OBJECTDATA, userData);
				url = "./partnerPassword.jsp";
	        } else { //沒有資料
	        	PartnerDataModel obj = new PartnerDataModel();
	        	obj.setErrmsg("queryFailed");
	        	userData = new ArrayList<PartnerDataModel>();
//			        	userData.set(0, obj); //加入錯誤訊息的標籤
				request.setAttribute(OBJECTDATA, userData);
				url = "./loginPartner.jsp?returnUrl=./changepass";
	        }
	        
    	} else { //cookie失效
    		url = "./loginPartner.jsp?returnUrl=./changepass";
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
    
	else if(actionType.equals(UPDATEPASS) == true){ //Logon 那裡有類似的寫法，改這裡要順便改那裡
    	
        ArrayList<PartnerDataModel> userData = new ArrayList<PartnerDataModel>();
        userData = PartnerBusinessModel.setRequestData(request);
        
    	int result = 0;
    	
    	logger.info("--- Partner is updating password: " + userId);
    	
    	
    	String url = "";

		// Shiro Login
		if (currentUser.isAuthenticated()) {
			result = PartnerBusinessModel.updatePassword(userData, userId);
		}
		
			
		url = "./partnerPassword.jsp";
		PartnerDataModel obj = (PartnerDataModel)userData.get(0);
		
		if(result == 0){ //失敗
			obj.setErrmsg("updateFailed");
			logger.info("--- update profile Failed");;
		} else if(result == 1){ //成功
			obj.setErrmsg("updateSuccess");
			logger.info("--- update success");;
		} else if(result == -1){ //密碼不正確
			obj.setErrmsg("wrongPassword");
			logger.info("--- profile wrong Password");;
		} else if(result == -2){ //帳號或電郵已被使用
			obj.setErrmsg("userIdEmailExist");
			logger.info("--- profile userId / Email Exist");;
		}
		
		userData.set(0, obj); //加入錯誤訊息的標籤
		request.setAttribute(OBJECTDATA, userData);

    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;

    }
    	  

	else if(actionType.equals(CPPARTNER) == true){
	  	
		
		if(!checkPrivilege(response, privilege)){
    		return;
    	}
	  	
	  	ArrayList<PartnerDataModel> data = new ArrayList<PartnerDataModel>();
	  	data = PartnerBusinessModel.agentPartnerList("all");
	
	      if(data != null && !data.isEmpty()){
	      	request.setAttribute(OBJECTDATA, data);
	      }
	  	
	  	String url = "./cp/cpPartner.jsp";
	  	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	  	dispatcher.forward(request, response);
	  	return;
	}

	  
	else if(actionType.equals(ISEXIST) == true){
	  	String check_userId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString().trim();
	  	
	  	String isExist = "N";
	  	if(PartnerBusinessModel.checkExist(check_userId)){
	  		isExist = "Y";
	  	}
	      out.print(isExist);
	}
    
	else if(actionType.equals(CONTROLPANEL) == true){
	  	String url = "./partner.jsp";
	  	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	} 
    
    
	else if(actionType.equals(CPPARTNER_EDIT) == true){
	  	
		if(!checkPrivilege(response, privilege)){
    		return;
    	}
		
		String url = "./cpPartner";
	  	String email = request.getParameter("email") == null ? "" : request.getParameter("email").toString();

	  	ArrayList <PartnerDataModel> data = new ArrayList <PartnerDataModel>();
	  	data = PartnerBusinessModel.getPartnerData(email);
	  	
	  	int category = 0;
	  	if(data != null && !data.isEmpty()){
	  		
	  		if (data.get(0).getPrivilege()==3) {//agent
		  		category = 1;
		  	} else if (data.get(0).getPrivilege()==4) {//partner
		  		category = 2;
		  	}
	  		
	  		//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	PartnerDataModel obj = (PartnerDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	  		url = "./cp/cpPartner.jsp?actionType=cppartner_update";
	  		request.setAttribute(OBJECTDATA, data);
			  
			ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
			area = AreaBusinessModel.areaList();

		    if(area != null && !area.isEmpty()){
		      	request.setAttribute("area", area);
		    }
	  	}
	  	
	  	//Pricing List
        ArrayList<PricingDataModel> pricing = new ArrayList<PricingDataModel>();
        pricing = PricingBusinessModel.pricingList_partner("pid", category);
        request.setAttribute("pricing", pricing);
	  	
	  	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	} 
    

    
	else if(actionType.equals(CPPARTNER_UPDATE) == true){
	  	
		if(!checkPrivilege(response, privilege)){
    		return;
    	}
		
		RESULT = "";
		String url = "";
		String returnValue = "";
		String email = request.getParameter("email") == null ? "" : request.getParameter("email").toString();
		
		ArrayList <PartnerDataModel> data = new ArrayList <PartnerDataModel>();
	  	data = PartnerBusinessModel.setRequestData(request);

	  	if(data != null && !data.isEmpty()){
	  		returnValue = PartnerBusinessModel.adminUpdatePartner(data, privilege, locale);
	  		
	  		if(returnValue.equals("OK")){
	  			RESULT = "updateSuccess";
	  			url = "./partner?actionType=cppartner_edit&email=" + email;
	  			
	  		}
	  		
	  	}
	  	
	  	response.sendRedirect(url);
	  	return;
	}
    
    
	else if(actionType.equals(PARTNERCONSIGNMENT) == true || actionType.equals(PENDING)){
    	
    	String url = "./partner/cpConsignment_agent.jsp";
    	
    	if( (privilege != 3)&&(privilege != 4) ) { //neither agent nor partner
			url = "./partnerlogin?returnUrl=./partnerCpConsignment";
			response.sendRedirect(url);
			return;
		}
    	
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
    	
    	String partnerType = "agent";
    	if(privilege == 4) { //partner
    		partnerType = "partner";
    		url = "./partner/cpConsignment_partner.jsp";
    	}
    	
    	int partnerId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    	
    	ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
    	data = ConsignmentBusinessModel.consignmentList4PartnerView(partnerType, partnerId, pageNo, status); //show related consignment to partner / agent

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
        
        if(privilege == 3) { //agent only
        	
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
        
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
    }
    
    
	else if(actionType.equals(SEARCHAGENTCONSIGNMENT) == true){
    	
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").trim().toString();
		String url = "./partner/cpConsignment_agent.jsp?actionType=searchAgentConsignment&consignmentNo="+consignmentNo;
		
		
		if( (privilege != 3)) {
			url = "./partnerlogin?returnUrl=./agentCpConsignment";
			response.sendRedirect(url);
			return;
		}

    	int partnerId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    	
    	ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
    	if(consignmentNo.trim().length() > 0){

    		data = ConsignmentBusinessModel.partnerSearchConsignment("agent", partnerId, consignmentNo); //搜尋consignment
    		
    		if(data != null && !data.isEmpty()){
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
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
	else if(actionType.equals(SEARCHPARTNERCONSIGNMENT) == true){
    	
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").trim().toString();
		String url = "./partner/cpConsignment_partner.jsp?actionType=searchPartnerConsignment&consignmentNo="+consignmentNo;
		
		
		if(privilege != 4) {
			url = "./partnerlogin?returnUrl=./partnerCpConsignment";
			response.sendRedirect(url);
			return;
		}

    	int partnerId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    	
    	ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
    	if(consignmentNo.trim().length() > 0){
    		
    		data = ConsignmentBusinessModel.partnerSearchConsignment("partner", partnerId, consignmentNo); //搜尋consignment
    		
    		if(data != null && !data.isEmpty()){
            	request.setAttribute(OBJECTDATA, data);
            }
 
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
  }
  
  
  
  	//檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
	  	int iid = 4; //必須自行手動設定，對應DB的privilege
	  	String url = "./stafflogin?returnUrl=./cpPartner";
	  	
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