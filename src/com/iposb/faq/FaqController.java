package com.iposb.faq;

import java.io.IOException;
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



public class FaqController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  
  	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String CPFAQ = "cpfaq"; //導到清單頁
	public static final String CPFAQ_NEW = "cpfaq_new"; //導到空白頁
	public static final String CPFAQ_INSERT = "cpfaq_insert"; //寫入資料庫
	public static final String CPFAQ_EDIT = "cpfaq_edit"; //導到編輯頁
	public static final String CPFAQ_UPDATE = "cpfaq_update"; //更新資料庫
	
	private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(FaqController.class);
  
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
		  		  
		  ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		  data = FaqBusinessModel.searchFaq("category", pageNo);
        

		  if(data != null && !data.isEmpty()){
			  request.setAttribute(OBJECTDATA, data);
		  }
		  
		  logger.info("---User is reading FAQ");
    	
		  String url = "./faq.jsp";
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  else if(actionType.equals(CPFAQ) == true){
		  
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
    	
		  ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		  data = FaqBusinessModel.faqList("fid", pageNo, "");

		  if(data != null && !data.isEmpty()){
			  request.setAttribute(OBJECTDATA, data);
		  }
    	
		  String url = "./cp/cpFaq.jsp";
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  else if(actionType.equals(CPFAQ_NEW) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
	    	String url = "./cp/cpFaq.jsp?actionType=cpfaq_insert";
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    }
	  
	  
	  else if(actionType.equals(CPFAQ_INSERT) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
	    	ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
	    	data = FaqBusinessModel.setRequestData(request);
	        
	    	String returnValue = "";
	    	String result = "";
	    	returnValue = FaqBusinessModel.addNewFaq(data);
	    	
	    	String fid = String.valueOf(FaqBusinessModel.getMaxFid());
			
			if(returnValue.equals("OK")){ //成功
				result = "insertSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
			
			String url = "./faq?actionType=cpfaq_edit&fid=" + fid;
			response.sendRedirect(url);
	        return;
	    }
	  

	  else if(actionType.equals(CPFAQ_EDIT) == true){
		  
		  if(!checkPrivilege(response, privilege)){
	    		return;
		  }
		  
		  String fid = request.getParameter("fid");
		  
		  logger.info("---Admin  is editing FAQ #"+fid);
    	
		  String url = "./cp/cpFaq.jsp?actionType=cpfaq";
		  ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		  
		  if(isNumeric(fid)){
			  data = FaqBusinessModel.faqDetails(fid);
		  }

		  if(data != null && !data.isEmpty()){
			  
			//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	FaqDataModel obj = (FaqDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
	            
			  request.setAttribute(OBJECTDATA, data);
			  url = "./cp/cpFaq.jsp?actionType=cpfaq_update";
		  }
		  RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  dispatcher.forward(request, response);
		  return;
	  }
	  
	  
	  else if(actionType.equals(CPFAQ_UPDATE) == true){
		  
		  	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
		  
		  	ArrayList<FaqDataModel> data = new ArrayList<FaqDataModel>();
		  	ArrayList<FaqDataModel> data_new = new ArrayList<FaqDataModel>();
		  	data = FaqBusinessModel.setRequestData(request);
	        
		  	String returnValue = "";
	    	String result = "";
	    	returnValue = FaqBusinessModel.updateFaq(data); //寫入資料庫
			
			//重新查詢最新的資料
			String fid = request.getParameter("fid");
			
			if(returnValue.equals("OK")){ //成功
				result = "updateSuccess";
			} else if(returnValue.equals("")){ //沒有連線
				result = "noConnection";
			} else { //失敗
				result = returnValue;
			}
			
			RESULT = ""; //先清除
			RESULT = result;
	        
			String url = "./faq?actionType=cpfaq_edit&fid=" + fid;
			response.sendRedirect(url);
	        return;
	    }

        
  }
  
  	//檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
	  	int iid = 21; //必須自行手動設定，對應DB的privilege
	  	String url = "./stafflogin?returnUrl=./cpFaq";
	  	
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