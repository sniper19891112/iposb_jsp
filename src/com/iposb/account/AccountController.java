package com.iposb.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.iposb.area.AreaBusinessModel;
import com.iposb.area.AreaDataModel;
import com.iposb.partner.PartnerBusinessModel;
import com.iposb.partner.PartnerDataModel;
import com.iposb.privilege.PrivilegeBusinessModel;
import com.iposb.account.AccountBusinessModel;



public class AccountController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  
	//Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String ACCOUNTMAINTAIN = "accountMaintain"; //修改agent rate, extras, accMonth...
	public static final String UPDATEPROFIT = "updateProfit"; //儲存 agent rate, extras, accMonth
	public static final String UPDATEFILENAME = "updateFilename"; //記錄上傳的檔案名稱
	public static final String SETTLEMENT = "settlement";
	public static final String INVOICE = "invoice";
	public static final String POS_STATEMENT = "posStatement";
	public static final String GENPARTNERINVOICE = "genPartnerInvoice";
	public static final String GST = "gst";


	private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(AccountController.class);
  
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
	Date date = new Date();
	SimpleDateFormat sdf1, sdf2, sdf3;
	String todayYear, todayMonth, todayDay;
	sdf1 = new SimpleDateFormat("yyyy");
	todayYear = sdf1.format(date);
	sdf2 = new SimpleDateFormat("MM");
	todayMonth = sdf2.format(date);
	sdf3 = new SimpleDateFormat("dd");
	todayDay = sdf3.format(date);
	
    if(actionType == null){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	    	
    	String payMethod = request.getParameter("payMethod") == null ? "" : request.getParameter("payMethod").toString();
    	int origin = Integer.parseInt(request.getParameter("origin") == null ? "2" : request.getParameter("origin").equals("") ? "2" : request.getParameter("origin").toString());
		int destination = Integer.parseInt(request.getParameter("destination") == null ? "0" : request.getParameter("destination").equals("") ? "0" : request.getParameter("destination").toString());
    	String yyyy = request.getParameter("year") == null ? "" : request.getParameter("year").equals("") ? "" : request.getParameter("year").toString();
    	String MM = request.getParameter("month") == null ? "" : request.getParameter("month").equals("") ? "" : request.getParameter("month").toString();
    	String dd = request.getParameter("day") == null ? "" : request.getParameter("day").equals("") ? "" : request.getParameter("day").toString();
    	String filter = request.getParameter("filter") == null ? "" : request.getParameter("filter").toString();

    	if(MM.length()==1){
    		MM = "0" + MM; //避免大意只輸入一個數字，幫加0在前面
    	}
    	if(dd.length()==1){
    		dd = "0" + dd; //避免大意只輸入一個數字，幫加0在前面
    	}
    	if(yyyy.equals("")){
    		yyyy = todayYear;
    	}
    	
    	ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
    	data = AccountBusinessModel.allAccount(payMethod, origin, destination, yyyy, MM, dd); //根據種類、年月份查詢所有訂單

        
        if(data != null && !data.isEmpty()){
        	
        	//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	AccountDataModel obj = (AccountDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
            
        	request.setAttribute(OBJECTDATA, data);
  
        }
        
        //area
    	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		area = AreaBusinessModel.areaList();
	    request.setAttribute("area", area);
	    
    	
    	String url = "./cp/cpAccount.jsp?filter="+filter;
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
    
    else if(actionType.equals(ACCOUNTMAINTAIN) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}

    	String payMethod = request.getParameter("payMethod") == null ? "" : request.getParameter("payMethod").toString();
    	int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").toString());
    	String yyyy = request.getParameter("year") == null ? "" : request.getParameter("year").equals("") ? "" : request.getParameter("year").toString();
    	String MM = request.getParameter("month") == null ? "" : request.getParameter("month").equals("") ? "" : request.getParameter("month").toString();
    	String dd = request.getParameter("day") == null ? "" : request.getParameter("day").equals("") ? "" : request.getParameter("day").toString();

    	if(MM.length()==1){
    		MM = "0" + MM; //避免大意只輸入一個數字，幫加0在前面
    	}
    	if(dd.length()==1){
    		dd = "0" + dd; //避免大意只輸入一個數字，幫加0在前面
    	}
    	
    	if(yyyy.equals("")){
    		yyyy = todayYear;
    	}
    	if(MM.equals("")){
    		MM = todayMonth;
    	}
    	

    	ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
    	if((yyyy.length() == 4) && (MM.length() == 2)) {
    		data = AccountBusinessModel.profitMaintain(payMethod, station, yyyy, MM, dd); //根據種類、年月份查詢所有consignment
    	}
    	
        if(data != null && !data.isEmpty()){
        	
        	//如果RESULT有值代表儲存DB後再進入這裡
            if(!RESULT.equals("")){
            	AccountDataModel obj = (AccountDataModel)data.get(0);
        		obj.setErrmsg(RESULT); //加入訊息的標籤
        		data.set(0, obj); //再設入
        		RESULT = ""; //清除掉記錄
            }
            
        	request.setAttribute(OBJECTDATA, data);
        }
        
        
      	//area
    	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		area = AreaBusinessModel.areaList();

	    if(area != null && !area.isEmpty()){
	      	request.setAttribute("area", area);
	    }
	    
 
    	String url = "./cp/cpAccountMaintain.jsp";
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
	      
	}
    
    
    else if(actionType.equals(UPDATEPROFIT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
//    	String accMonth = request.getParameter("accMonth") == null ? "" : request.getParameter("accMonth").toString();
    	String accDT = request.getParameter("accDT") == null ? "" : request.getParameter("accDT").toString();
    	String remark = request.getParameter("remark") == null ? "" : request.getParameter("remark").toString();
    	String year = request.getParameter("year") == null ? "" : request.getParameter("year").toString();
    	String month = request.getParameter("month") == null ? "" : request.getParameter("month").toString();
    	String day = request.getParameter("day") == null ? "" : request.getParameter("day").toString();
    	int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").toString());
    	String payMethod = request.getParameter("payMethod") == null ? "" : request.getParameter("payMethod").toString();
    	
    	String returnValue = "";
    	String result = "";
    	returnValue = AccountBusinessModel.updateProfit(consignmentNo, accDT, remark); //寫入資料庫

		if(returnValue.equals("OK")){ //成功
			result = "updateSuccess";
		} else if(returnValue.equals("")){ //沒有連線
			result = "noConnection";
		} else { //失敗
			result = returnValue;
		}
        
		RESULT = ""; //先清除
		RESULT = result;
    	
    	String url = "./accountMaintain?year="+year+"&month="+month+"&day="+day+"&station="+station+"&payMethod="+payMethod;
    	response.sendRedirect(url);
        return;
	      
	}
    
    
    else if(actionType.equals(SETTLEMENT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    		
		//area
    	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		area = AreaBusinessModel.areaList();
	    request.setAttribute("area", area);
	    
		String url = "./cp/cpAccountSettle.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    	
	}
    
    
    else if(actionType.equals(INVOICE) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    		
		String invoiceDateRange = request.getParameter("invoiceDateRange") == null ? "" : request.getParameter("invoiceDateRange").toString();
		int partnerId = Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId").toString());
		
		if(invoiceDateRange.trim().length() > 0 && partnerId > 0) {
			
			ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
	    	data = AccountBusinessModel.checkPartnerInvoice(invoiceDateRange, partnerId);
	    	
	    	request.setAttribute(OBJECTDATA, data);
	        
		}
		
		//Partner List
        ArrayList<PartnerDataModel> partner = new ArrayList<PartnerDataModel>();
	    partner = PartnerBusinessModel.agentPartnerList("partner");
	    request.setAttribute("partner", partner);
	    
		String url = "./cp/cpAccountInvoice.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    	
	}
    

    else if(actionType.equals(POS_STATEMENT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
		String airwaybillNum = request.getParameter("search_airwaybillNum") == null ? "" : request.getParameter("search_airwaybillNum").toString();
		
		if(airwaybillNum.trim().length() > 0) {
			
			ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
	    	data = AccountBusinessModel.checkPosStatement(airwaybillNum);
	    	
	    	request.setAttribute(OBJECTDATA, data);
		}
		
		String url = "./cp/cpAccountPosState.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    	
	}
    
    else if(actionType.equals(GENPARTNERINVOICE) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    		
		String invoiceDateRange = request.getParameter("invoiceDateRange") == null ? "" : request.getParameter("invoiceDateRange").toString();
		int partnerId = Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId").toString());
		
		String result = "";
    	String returnValue = "";
		ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
		
		if(invoiceDateRange.trim().length() > 0 && partnerId > 0) {
	    	data = AccountBusinessModel.checkPartnerInvoice(invoiceDateRange, partnerId);
		}
		
		if(data != null && !data.isEmpty()){
        	returnValue = AccountBusinessModel.generatePartnerInvoicel(request, data, "xlsx");
        }

		if(returnValue.equals("OK")){ //成功
			result = "OK";
		} else { //失敗
			result = returnValue;
		}
        
		out.print(result);
    	
	}

    
    
    else if(actionType.equals(GST) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	String yyyy = request.getParameter("year") == null ? "" : request.getParameter("year").equals("") ? "" : request.getParameter("year").toString();
    	String MM = request.getParameter("month") == null ? "" : request.getParameter("month").equals("") ? "" : request.getParameter("month").toString();

    	
    	if(MM.length()==1){
    		MM = "0" + MM; //避免大意只輸入一個數字，幫加0在前面
    	}
    	if(yyyy.equals("")){
    		yyyy = todayYear;
    	}
    	if(MM.equals("")){
    		MM = todayMonth;
    	}
    	
//    	ArrayList<InvoiceDataModel> data = new ArrayList<InvoiceDataModel>();
//    	data = AccountBusinessModel.monthlyGST(yyyy, MM);
//
//        
//        if(data != null && !data.isEmpty()){
//        	
//        	//如果RESULT有值代表儲存DB後再進入這裡
//        	if(!RESULT.equals("")){
//        		InvoiceDataModel obj = (InvoiceDataModel)data.get(0);
//        		obj.setErrmsg(RESULT); //加入訊息的標籤
//        		data.set(0, obj); //再設入
//        		RESULT = ""; //清除掉記錄
//            }
//            
//        	request.setAttribute(OBJECTDATA, data);
//        }
    	
    	String url = "./dbGST.jsp";
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    	
    }

    
  }

    
  	//檢查是否具備足夠權限使用本項目
    private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
    	int iid = 2; //必須自行手動設定，對應DB的privilege
    	String url = "./stafflogin?returnUrl=./account";
    	
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