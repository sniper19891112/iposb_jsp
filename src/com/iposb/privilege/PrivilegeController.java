package com.iposb.privilege;

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

import com.iposb.logon.LogonBusinessModel;
import com.iposb.logon.LogonDataModel;
import com.iposb.utils.UtilsBusinessModel;



public class PrivilegeController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  
  	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String DETAILS = "details";
	public static final String CPPRIVILEGE = "cpprivilege"; //導到清單頁
	public static final String CPPRIVILEGE_NEW = "cpprivilege_new"; //導到空白頁
	public static final String CPPRIVILEGE_INSERT = "cpprivilege_insert"; //寫入資料庫
	public static final String CPPRIVILEGE_EDIT = "cpprivilege_edit"; //導到編輯頁
	public static final String CPPRIVILEGE_UPDATE = "cpprivilege_update"; //更新資料庫
	public static final String AJAXUPDATEREORDER = "ajaxUpdateReorder"; //Ajax更新Reorder
	
	private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(PrivilegeController.class);
  
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

	  if (actionType == null) { //Control Panel
			String url = "./stafflogin?returnUrl=./cp";
			//if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
				if (privilege >= 5) {
					
					ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
					userData = LogonBusinessModel.getStaffData(userId);
					
					if(userData != null && !userData.isEmpty()){
						request.setAttribute(OBJECTDATA, userData);
					}
			        
					url = "./cp/index.jsp";

					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
					return;
				}
			//}

			response.sendRedirect(url);
			return;
	  }
	  
	  
	  else if(actionType.equals(DETAILS) == true){
		  
		  if(!checkPrivilege(response, privilege)){
	    		return;
	      }
		  
		  String iid = request.getParameter("iid");
	    	
		  String url = "./cp/cpPrivilege.jsp";
		  ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		  
		  if(isNumeric(iid)){
			  data = PrivilegeBusinessModel.privilegeDetails(iid);
		  }
	      
	      if(data != null && !data.isEmpty()){
	    	  request.setAttribute(OBJECTDATA, data);	        	
	    	  url = "./privilegeinfo.jsp";
	    	  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		      dispatcher.forward(request, response);
	      }

	      return;
	  }
	  
	  
	  else if(actionType.equals(CPPRIVILEGE_NEW) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
	    	String url = "./cp/cpPrivilege.jsp?actionType=cpprivilege_insert";
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    }
	  
	  
	  else if(actionType.equals(CPPRIVILEGE_INSERT) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
	    	ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
	    	data = PrivilegeBusinessModel.setRequestData(request);
	        
	    	String returnValue = "";
	    	String result = "";
	    	returnValue = PrivilegeBusinessModel.addNewPrivilege(data);
	    	
	    	String iid = String.valueOf(PrivilegeBusinessModel.getMaxIid());
			
			if(returnValue.equals("OK")){ //成功
				result = "insertSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
			
			String url = "./privilege?actionType=cpprivilege_edit&iid=" + iid;
			response.sendRedirect(url);
	        return;
	    }
	  

	  else if(actionType.equals(CPPRIVILEGE_EDIT) == true){
		  
		  if(!checkPrivilege(response, privilege)){
	    		return;
		  }
		  
		  String iid = request.getParameter("iid");
    	
		  String url = "./cp/cpPrivilege.jsp?actionType=cpprivilege";
		  ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		  
		  if(isNumeric(iid)){
			  data = PrivilegeBusinessModel.privilegeDetails(iid);
		  }

		  if(data != null && !data.isEmpty()){
			  
			//如果RESULT有值代表儲存CP後再進入這裡
            if(!RESULT.equals("")){
            	PrivilegeDataModel obj = (PrivilegeDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	            
            	request.setAttribute(OBJECTDATA, data);
            	url = "./cp/cpPrivilege.jsp?actionType=cpprivilege_update";
		  }
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  else if(actionType.equals(CPPRIVILEGE_UPDATE) == true){
		  
		    if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
		  
		  	ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		  	data = PrivilegeBusinessModel.setRequestData(request);
	        
		  	String returnValue = "";
	    	String result = "";
	    	returnValue = PrivilegeBusinessModel.updatePrivilege(data); //寫入資料庫
			
			//重新查詢最新的資料
			String iid = request.getParameter("iid");
	    	
			if(returnValue.equals("OK")){ //成功
				result = "updateSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
	        
			String url = "./privilege?actionType=cpprivilege_edit&iid=" + iid;
			response.sendRedirect(url);
	        return;
	    }
	  
	  
	  else if(actionType.equals(CPPRIVILEGE) == true){
		  
		  if(!checkPrivilege(response, privilege)){
	    		return;
	      }

		  ArrayList<PrivilegeDataModel> data = new ArrayList<PrivilegeDataModel>();
		  data = PrivilegeBusinessModel.privilegeList();

		  if(data != null && !data.isEmpty()){
			  request.setAttribute(OBJECTDATA, data);
		  }
    	
		  String url = "./cp/cpPrivilege.jsp";
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  
	  else if(actionType.equals(AJAXUPDATEREORDER) == true){
		  
		  if(!checkPrivilege(response, privilege)){
	    		return;
	      }
		  
		  String reorder = request.getParameter("reorder") == null ? "" : request.getParameter("reorder").toString();
		  
		  String returnValue = PrivilegeBusinessModel.ajaxUpdateReorder(reorder);
		  out.print(returnValue);
		  return;
	  }
    
        
  }
  
  
  	//檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
	  	int iid = 10; //必須自行手動設定，對應DB的privilege
	  	String url = "./stafflogin?returnUrl=./cpPrivilege";
	  	
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