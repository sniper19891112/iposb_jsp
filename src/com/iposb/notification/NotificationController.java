package com.iposb.notification;

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

import com.iposb.partner.PartnerBusinessModel;
import com.iposb.privilege.PrivilegeBusinessModel;



public class NotificationController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String COUNTUNREAD = "countUnread";
	public static final String CHECKUNREAD = "checkUnread";
	public static final String MARKREAD = "markRead";

	static Logger logger = Logger.getLogger(NotificationController.class);
  
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
    
    
    if(actionType == null){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	//防止user亂輸入頁碼
    	int pageNo = 0;
    	String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
    	boolean isNum = isNumeric(page);
    	
    	if(isNum){
    		pageNo = Integer.parseInt(page);
    	}
    	if( !(pageNo > 0) || !(isNum) ){
    		pageNo = 1;
    	}
    	
    	ArrayList<NotificationDataModel> data = new ArrayList<NotificationDataModel>();
    	
    	if(privilege == 3) { //agent
    		int agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    		data = NotificationBusinessModel.allAgentNotifications(pageNo, agentId);
		} else if (privilege > 5) { //not included driver
			data = NotificationBusinessModel.allNotifications(pageNo);
		}

        if(data != null && !data.isEmpty()){
        	request.setAttribute(OBJECTDATA, data);
        }
    	
    	String url = "./cp/cpNotification.jsp";
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
    } 
    
    else if(actionType.equals(COUNTUNREAD) == true){
		
    	int num = 0; 
    			
    	if(privilege == 3) { //agent
    		int agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    		num = NotificationBusinessModel.countAgentNotification(agentId);
		} else if (privilege > 5) { //not included driver
			num = NotificationBusinessModel.countNotification();
		}
	    out.print(num); //ajax send back text
	}

    else if(actionType.equals(CHECKUNREAD) == true){

		String locale = request.getParameter("locale") == null ? "" : request.getParameter("locale").toString();
		
		String notificationStr = "";
		
		if(privilege == 3) { //agent
			int agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
			notificationStr = NotificationBusinessModel.queryAgentNotification(locale, agentId);
		} else if (privilege > 5) { //not included driver
			notificationStr = NotificationBusinessModel.queryNotification(locale);
		}

	    out.print(notificationStr); //ajax send back text
	}
    
    else if(actionType.equals(MARKREAD) == true){
		  
    	String nid = request.getParameter("nid") == null ? "0" : request.getParameter("nid").toString();
    	
    	if(privilege == 3) { //agent
    		int agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    		NotificationBusinessModel.markAgentRead(nid, userId, agentId);
		} else if (privilege > 5) { //not included driver
			NotificationBusinessModel.markRead(nid, userId);
		}
    	
	}
    
    
    else
    {}
    out.flush();
    out.close();
    return;
  }
  
  
  	//檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
	  	int iid = 13; //必須自行手動設定，對應DB的privilege
	  	String url = "./stafflogin?returnUrl=./notification";
	  	
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