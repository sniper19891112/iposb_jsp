package com.iposb.area;

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

import com.iposb.privilege.PrivilegeBusinessModel;



public class AreaController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  
  	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String DBAREA_NEW = "dbarea_new"; //導到空白頁
	public static final String DBAREA_INSERT = "dbarea_insert"; //寫入資料庫
	public static final String DBAREA_EDIT = "dbarea_edit"; //導到編輯頁
	public static final String DBAREA_UPDATE = "dbarea_update"; //更新資料庫
	
	public static final String DBZONE = "dbzone"; //導到清單頁
	public static final String DBZONE_NEW = "dbzone_new"; //導到空白頁
	public static final String DBZONE_INSERT = "dbzone_insert"; //寫入資料庫
	public static final String DBZONE_EDIT = "dbzone_edit"; //導到編輯頁
	public static final String DBZONE_UPDATE = "dbzone_update"; //更新資料庫
	
	public static final String AJAXGETZONE = "ajaxGetZone";
	
	private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(AreaController.class);
  
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

	    if(actionType == null || actionType.equals("")){
		  
		  ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		  data = AreaBusinessModel.allArea();
        
		  if(data != null && !data.isEmpty()){
			  request.setAttribute(OBJECTDATA, data);
		  }

		  String url = "./cp/cpArea.jsp";
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }

	  
	  else if(actionType.equals(DBAREA_NEW) == true){
	    	
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
		  
	    	String url = "./cp/cpArea.jsp?actionType=dbarea_insert";
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    }
	  
	  
	  else if(actionType.equals(DBAREA_INSERT) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
	    	ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
	    	data = AreaBusinessModel.setRequestData(request);
	        
	    	String returnValue = "";
	    	String result = "";
	    	returnValue = AreaBusinessModel.addNewArea(data, userId);
	    	
	    	String aid = String.valueOf(AreaBusinessModel.getMaxAid());
			
			if(returnValue.equals("OK")){ //成功
				result = "insertSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
			
			String url = "./area?actionType=dbarea_edit&aid=" + aid;
			response.sendRedirect(url);
	        return;
	    }
	  

	  else if(actionType.equals(DBAREA_EDIT) == true){
		  
		  if(!checkPrivilege(response, privilege)){
			  return;
		  }
		  
		  String aid = request.getParameter("aid");
    	
		  String url = "./cp/cpArea.jsp?actionType=dbarea_update";
		  ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		  
		  if(isNumeric(aid)){
			  data = AreaBusinessModel.areaDetails(aid);
		  }

		  if(data != null && !data.isEmpty()){
			  
			//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	AreaDataModel obj = (AreaDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	            
			  request.setAttribute(OBJECTDATA, data);
			  url = "./cp/cpArea.jsp?actionType=dbarea_update";
		  }
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  else if(actionType.equals(DBAREA_UPDATE) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
		  
		  	ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		  	data = AreaBusinessModel.setRequestData(request);
	        
		  	String returnValue = "";
	    	String result = "";
	    	returnValue = AreaBusinessModel.updateArea(data, userId); //寫入資料庫
			
			//重新查詢最新的資料
			String aid = request.getParameter("aid");
	    	
			if(returnValue.equals("OK")){ //成功
				result = "updateSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
	        
			String url = "./area?actionType=dbarea_edit&aid=" + aid;
			response.sendRedirect(url);
	        return;
	    }
	  
	  
	  else if(actionType.equals(DBZONE) == true){
		  
		  if(!checkPrivilege(response, privilege)){
			  return;
		  }
    	
		  ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		  data = AreaBusinessModel.allZone();

		  if(data != null && !data.isEmpty()){
			  request.setAttribute(OBJECTDATA, data);
		  }
    	
		  String url = "./cp/cpZone.jsp";
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  else if(actionType.equals(DBZONE_NEW) == true){
	    	
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
		  	
		  	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
			area = AreaBusinessModel.areaList();

		    if(area != null && !area.isEmpty()){
		      	request.setAttribute("area", area);
		    }
		  
	    	String url = "./cp/cpZone.jsp?actionType=dbzone_insert";
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    }
	  
	  
	  else if(actionType.equals(DBZONE_INSERT) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
		  	int aid = Integer.parseInt(request.getParameter("aid") == null ? "0" : request.getParameter("aid").toString());

	    	ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
	    	data = AreaBusinessModel.setRequestData(request);
	        
	    	String returnValue = "";
	    	String result = "";
	    	int serial = AreaBusinessModel.getMaxAreaSerial(aid);
	    	returnValue = AreaBusinessModel.addNewZone(data, userId, serial);
	    	
	    	String zid = String.valueOf(AreaBusinessModel.getMaxZid());
			
			if(returnValue.equals("OK")){ //成功
				result = "insertSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
			
			String url = "./area?actionType=dbzone_edit&zid=" + zid;
			response.sendRedirect(url);
	        return;
	    }
	  

	  else if(actionType.equals(DBZONE_EDIT) == true){
		  
		  if(!checkPrivilege(response, privilege)){
			  return;
		  }
		  
		  String zid = request.getParameter("zid");
  	
		  String url = "./cp/cpZone.jsp?actionType=dbzone_update";
		  ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		  
		  if(isNumeric(zid)){
			  data = AreaBusinessModel.zoneDetails(zid);
		  }

		  if(data != null && !data.isEmpty()){
			  
		   //如果RESULT有值代表儲存DB後再進入這裡
	       if(!RESULT.equals("")){
	          	AreaDataModel obj = (AreaDataModel)data.get(0);
	      		obj.setErrmsg(RESULT); //加入訊息的標籤
	      		data.set(0, obj); //再設入
	      		RESULT = ""; //清除掉記錄
	        }
	            
			request.setAttribute(OBJECTDATA, data);
			  
			ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
			area = AreaBusinessModel.areaList();

		    if(area != null && !area.isEmpty()){
		      	request.setAttribute("area", area);
		    }
			    
			  url = "./cp/cpZone.jsp?actionType=dbzone_update";
		  }
		  
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  else if(actionType.equals(DBZONE_UPDATE) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
		  
		  	ArrayList<AreaDataModel> data = new ArrayList<AreaDataModel>();
		  	data = AreaBusinessModel.setRequestData(request);
	        
		  	String returnValue = "";
	    	String result = "";
	    	returnValue = AreaBusinessModel.updateZone(data, userId); //寫入資料庫
			
			//重新查詢最新的資料
			String zid = request.getParameter("zid");
	    	
			if(returnValue.equals("OK")){ //成功
				result = "updateSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
	        
			String url = "./area?actionType=dbzone_edit&zid=" + zid;
			response.sendRedirect(url);
	        return;
	  }
	  
	  
	  else if(actionType.equals(AJAXGETZONE) == true){

			String result = "";
			int aid = Integer.parseInt(request.getParameter("aid") == null ? "0" : request.getParameter("aid").equals("") ? "0" : request.getParameter("aid").toString());
			String locale = request.getParameter("locale") == null ? "en_US" : request.getParameter("locale").toString();
			
			result = AreaBusinessModel.getZoneDropdownlistByArea(aid, locale);
			
		    out.print(result);
	  }
    
        
  }
  
  
  	//檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
	  	int iid = 9; //必須自行手動設定，對應DB的privilege
	  	String url = "./stafflogin?returnUrl=./area";
	  	
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