package com.iposb.point;

import java.io.IOException;
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



public class PointController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String GETPOINT = "getPoint";
	public static final String ADDPOINT = "addPoint";
	public static final String CONSIGNMENTADDPOINT = "consignmentAddPoint";
	
	static Logger logger = Logger.getLogger(PointController.class);
  
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
	
    String actionType = request.getParameter("actionType");
    String locale = request.getParameter("lang");
    
    if(actionType == null){
    	
//    	if(!checkPrivilege(response, loginCookie)){
//    		return;
//    	}
    	
    }
    
    else if(actionType.equals(GETPOINT) == true){
    	
    	String memberId = request.getParameter("userId") == null ? "" : request .getParameter("userId");
    	
    	ArrayList<PointDataModel> data = new ArrayList<PointDataModel>();
    	data = PointBusinessModel.getPointWithUserId(memberId);
    	
    	String url = "";
    	
    	if(data != null || data.size() > 0){
    		request.setAttribute(OBJECTDATA, data);
    		url = "./profile";
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		return;
    }
    
    else if(actionType.equals(ADDPOINT) == true){
    	
    	ArrayList<PointDataModel> data = new ArrayList<PointDataModel>();
		data = PointBusinessModel.setRequestData(request);
		
    	String url = "";
    	String checkEmail = "";
    	String result = "";
    	
    	checkEmail = PointBusinessModel.checkEmail(data);
    	
    	if(checkEmail == "exists"){
    		
	    	result = PointBusinessModel.addPoint(data);
	    	
	    	if (result.equals("OK")) {
	    		logger.info("*********Add point success*********");
	    		url = "./cp";
	    	} else {
	    		logger.error("*********Add point failed*********");
	    		url = "./cp";
	    	}
	    	
    	} else {
    		logger.info("*********Add point failed: email doesn't exists*********");
    		url = "./cp";
    	}
    	
    	response.sendRedirect(url);
    	return;
    }
    
    else if(actionType.equals(CONSIGNMENTADDPOINT) == true){
    	
    	ArrayList<PointDataModel> data = new ArrayList<PointDataModel>();
		data = PointBusinessModel.setRequestData(request);
		
    	String checkEmail = "";
    	String result = "";
    	
    	checkEmail = PointBusinessModel.checkEmail(data);
    	
    	if(checkEmail == "exists"){
    		
	    	result = PointBusinessModel.consignmentAddPoint(data);
	    	
	    	if (result.equals("OK")) {
	    		logger.info("*********Add point success*********");
	    	} else {
	    		logger.error("*********Add point failed*********");
	    	}
	    	
    	} else {
    		logger.info("*********Add point failed: email doesn't exists*********");
    	}
    	
    }
    
   }
  
//  	//檢查是否具備足夠權限使用本項目
//	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
//	  	int iid = 8; //必須自行手動設定，對應DB的privilege
//	  	String url = "./login?returnUrl=./profile";
//	  	
//	  	if(PrivilegeBusinessModel.determinePrivilege(iid, privilege)){
//	        return true;
//	    } else {
//	    	response.sendRedirect(url);
//	    	return false;
//	    }
//	}
  
  
  //以正規表達式判斷輸入的字串是否為數字
  public static boolean isNumeric(String str){
	  Pattern pattern = Pattern.compile("[0-9]*");
	  return pattern.matcher(str).matches();   
  }
  
  //Clean up resources
  public void destroy() {
  }
}