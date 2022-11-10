package com.iposb.log;

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
import com.iposb.utils.FixEncoding;
import com.iposb.utils.Tracking;



public class LogController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String SEARCHLOG = "searchlog";
	public static final String CPCONSIGNMENTLOG = "cpconsignmentlog";
	public static final String CPSTAGELOG = "cpstagelog";
	public static final String CPBOOKINGTRACK = "cpbookingtrack";
	public static final String CPCONSIGNMENTINTERNALNOTE = "cpconsignmentinternalnote";
	public static final String COUNTINTERNALNOTE = "countinternalnote";
	public static final String SAVENOTE = "savenote";
	public static final String UPDATENOTE = "updatenote";
	public static final String CHANGEAMOUNT = "changeAmount";

	static Logger logger = Logger.getLogger(LogController.class);
  
  public void init() throws ServletException {
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request,response);
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
    	
    	String url = "./cp/log.jsp";
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
    } 

    else if(actionType.equals(SEARCHLOG) == true){
    	String orderBy = request.getParameter("orderBy") == null ? "searchDT" : request.getParameter("orderBy").toString();
		  
	    	
		String url = "./cp/log_search.jsp";
		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		  
		data = LogBusinessModel.querySearchLog(request, orderBy);

	    if(data != null && !data.isEmpty()){
	    	request.setAttribute(OBJECTDATA, data);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}
    
    else if(actionType.equals(CPCONSIGNMENTLOG) == true){
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
	    	
		String url = "./cp/log_cpconsignment.jsp";
		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		 
		data = LogBusinessModel.queryCPConsignmentLog(consignmentNo);

	    if(data != null && !data.isEmpty()){
	    	request.setAttribute(OBJECTDATA, data);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}
    
    else if(actionType.equals(CPSTAGELOG) == true){
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
	    	
		String url = "./cp/log_cpstage.jsp";
		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		 
		data = LogBusinessModel.queryCPStageLog(consignmentNo);

	    if(data != null && !data.isEmpty()){
	    	request.setAttribute(OBJECTDATA, data);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}

    else if(actionType.equals(CPBOOKINGTRACK) == true){
		String bookingCode = request.getParameter("bookingCode") == null ? "" : request.getParameter("bookingCode").toString();
		String bookBy = request.getParameter("bookBy") == null ? "" : request.getParameter("bookBy").toString();
	    	
		String url = "./cp/log_track.jsp";
		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		  
		data = Tracking.queryDBTrack(bookingCode, bookBy);

	    if(data != null && !data.isEmpty()){
	    	request.setAttribute(OBJECTDATA, data);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}
    
    
    else if(actionType.equals(CPCONSIGNMENTINTERNALNOTE) == true){
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
		  
		String url = "./cp/log_internalnote.jsp?consignmentNo=" + consignmentNo;
		ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
		  
		data = LogBusinessModel.queryInternalNote(consignmentNo);

	    if(data != null && !data.isEmpty()){
	    	request.setAttribute(OBJECTDATA, data);
	    }
	    	
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    return;
	}
    
    else if(actionType.equals(COUNTINTERNALNOTE) == true){
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();

		int total = LogBusinessModel.countInternalNote(consignmentNo);
	    out.print(total);
	}
    
    else if(actionType.equals(SAVENOTE) == true){
    	
    	if (request.getCharacterEncoding() == null) {
    	    request.setCharacterEncoding("UTF-8");
    	    logger.info("request.getCharacterEncoding() == null");
    	}
    	
    	String noteStr = "";
    	
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
		String remark = new String(request.getParameter("remark").getBytes("ISO-8859-1"), "UTF-8");
		String creator = request.getParameter("creator") == null ? "" : request.getParameter("creator").toString();
		String locale = request.getParameter("locale") == null ? "" : request.getParameter("locale").toString();
		String arg = FixEncoding.fix(request.getParameter("remark").toString());

		String result = LogBusinessModel.insertNewNote(consignmentNo, arg, creator);

		if(result.equals("OK")){ //寫入成功，再把所有評註讀出
			ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
			data = LogBusinessModel.queryInternalNote(consignmentNo);
			noteStr = LogBusinessModel.organiseNote(data, locale);
		}
//logger.info(noteStr);		
		out.print(noteStr); //ajax回傳文字
	}
    
    else if(actionType.equals(UPDATENOTE) == true){
    	
    	String noteStr = "";
    	
		String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
		int nid = Integer.parseInt(request.getParameter("nid") == null ? "0" : request.getParameter("nid").toString());
		String modifier = request.getParameter("modifier") == null ? "" : request.getParameter("modifier").toString();
		String locale = request.getParameter("locale") == null ? "" : request.getParameter("locale").toString();
		  
		String result = LogBusinessModel.updateNote(nid, modifier); //update
		  
		if(result.equals("OK")){ //寫入成功，再把所有評註讀出
			ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
			data = LogBusinessModel.queryInternalNote(consignmentNo);
			noteStr = LogBusinessModel.organiseNote(data, locale);
		}
	    	
	    out.print(noteStr); //ajax回傳文字
	}
    
    else if (actionType.equals(CHANGEAMOUNT)  == true){
    	

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
    	
    	ArrayList<LogDataModel> data = new ArrayList<LogDataModel>();
        data = LogBusinessModel.allAmountLog(pageNo);

        if(data != null && !data.isEmpty()){
        	request.setAttribute(OBJECTDATA, data);
        }
    	
        
    	String url = "./cp/log_changeamount.jsp";
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
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
	  	String url = "./stafflogin?returnUrl=./log";
  	
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