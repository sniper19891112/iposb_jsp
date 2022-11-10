package com.iposb.report;

/**
 * Source From : https://www.85flash.com/Get/wangyebiancheng/JSP/2005-9-8/059816571318626.htm
 */

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

import com.iposb.account.AccountBusinessModel;
import com.iposb.account.AccountDataModel;
import com.iposb.area.AreaBusinessModel;
import com.iposb.area.AreaDataModel;
import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.consignment.ConsignmentDataModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.logon.LogonDataModel;
import com.iposb.partner.PartnerBusinessModel;
import com.iposb.partner.PartnerDataModel;
import com.iposb.pdf.PdfBusinessModel;
import com.iposb.privilege.PrivilegeBusinessModel;

public class ReportController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	static Logger logger = Logger.getLogger(ReportController.class);

	// Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String CONSIGNMENTNOTE = "consignmentNote";
	public static final String CONSIGNMENTNOTESTICKER = "consignmentNoteSticker";
	public static final String PARTNERSTICKER = "partnerSticker";
	public static final String MANIFEST = "manifest";
	public static final String RUNSHEET = "runsheet";
	public static final String RUNSHEETREPORT = "runsheetReport";
	public static final String GENRUNSHEETREPORT = "genRunsheetReport";
	public static final String PARTNERREPORT = "partnerReport";
	public static final String GENPARTNERREPORT = "genPartnerReport";
	public static final String SALESREPORT = "salesReport";
	public static final String GENSALESREPORT = "genSalesReport";
	public static final String MEMBERREPORT = "memberReport";
	public static final String GENMEMBERREPORT = "genMemberReport";
	public static final String DELIVERYREPORT = "deliveryReport";
	public static final String GENDELIVERYREPORT = "genDeliveryReport";
	
	private static String RESULT = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		return;
	}

	// Process the HTTP Get request
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
		// String locale = request.getParameter("lang");

		if (actionType == null || actionType.equals("")) {

			String url = "./cp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}
		
		
		
		else if(actionType.equals(RUNSHEETREPORT) == true){
	    	
	    	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
	    	int area = Integer.parseInt(request.getParameter("area") == null ? "2" : request.getParameter("area").equals("") ? "2" : request.getParameter("area").toString());
			String driver = request.getParameter("driver") == null ? "" : request.getParameter("driver").toString();
			String distributeDT = request.getParameter("distributeDT") == null ? "" : request.getParameter("distributeDT").toString();

			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
			
			if(distributeDT.trim().length() > 0) {
				data = ConsignmentBusinessModel.getRunsheetRecord(area, driver, distributeDT);
			}
	        
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
	        
	        //Area List
	        ArrayList<AreaDataModel> arealist = new ArrayList<AreaDataModel>();
	        arealist = AreaBusinessModel.areaList();
	        request.setAttribute("area", arealist);
	        
	        //Staff List
	        ArrayList<LogonDataModel> staff = new ArrayList<LogonDataModel>();
	        staff = LogonBusinessModel.staffList();
	        request.setAttribute("staff", staff);
	      
		    
	    	
	    	String url = "./cp/cpRunsheetReport.jsp?area="+area+"&driver="+driver+"&distributeDT="+distributeDT;
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    	
		}
		
		
		else if (actionType.equals(GENRUNSHEETREPORT) == true) {
			
			if(!checkPrivilege(response, privilege)){
	    		return;
	    	}

			int area = Integer.parseInt(request.getParameter("area") == null ? "0" : request.getParameter("area").equals("") ? "0" : request.getParameter("area").toString());
			String driver = request.getParameter("driver") == null ? "" : request.getParameter("driver").toString();
			String distributeDT = request.getParameter("distributeDT") == null ? "" : request.getParameter("distributeDT").toString();

			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
			
			if(distributeDT.trim().length() > 0) {
				data = ConsignmentBusinessModel.getRunsheetRecord(area, driver, distributeDT);
			}
			
			String result = "";

			if (data != null && data.size() > 0) {
				result = PdfBusinessModel.generateRunsheetReport(request, data, driver); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}
		
		
		else if(actionType.equals(PARTNERREPORT) == true){
	    	
	    	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    	
	    	String createDT = request.getParameter("createDT") == null ? "" : request.getParameter("createDT").toString();
			int station = Integer.parseInt(request.getParameter("station") == null ? "2" : request.getParameter("station").equals("") ? "2" : request.getParameter("station").toString());
			int partnerId = Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId").equals("") ? "0": request.getParameter("partnerId").toString());
			int stage = Integer.parseInt(request.getParameter("stage") == null ? "0" : request.getParameter("stage").equals("") ? "0" : request.getParameter("stage").toString());

			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
	    	
			data = ConsignmentBusinessModel.getPartnerReport(createDT, station, partnerId, stage);

	        
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
	        
	        //Area List
	        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	        area = AreaBusinessModel.areaList();
	        request.setAttribute("area", area);
	        
		    //Partner List
	        ArrayList<PartnerDataModel> partner = new ArrayList<PartnerDataModel>();
		    partner = PartnerBusinessModel.agentPartnerList("partner");
		    request.setAttribute("partner", partner);
		    
	    	
	    	String url = "./cp/cpPartnerReport.jsp";
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    	
		}
		
		
		else if (actionType.equals(GENPARTNERREPORT) == true) {
			
			if(!checkPrivilege(response, privilege)){
	    		return;
	    	}

			String createDT = request.getParameter("createDT") == null ? "" : request.getParameter("createDT").toString();
			int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").toString());
			int partnerId = Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId").toString());
			int stage = Integer.parseInt(request.getParameter("stage") == null ? "0" : request.getParameter("stage").toString());

			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";

			consignmentData = ConsignmentBusinessModel.getPartnerReport(createDT, station, partnerId, stage);

			if (consignmentData != null && consignmentData.size() > 0) {
				result = PdfBusinessModel.generatePartnerReport(request, consignmentData, stage, userId); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}
		
		
		
		else if(actionType.equals(SALESREPORT) == true){
	    	
	    	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}

	    	String accDT = request.getParameter("accDT") == null ? "" : request.getParameter("accDT").toString();
	    	int origin = Integer.parseInt(request.getParameter("origin") == null ? "0" : request.getParameter("origin").equals("") ? "0" : request.getParameter("origin").toString());
			int destination = Integer.parseInt(request.getParameter("destination") == null ? "0" : request.getParameter("destination").equals("") ? "0" : request.getParameter("destination").toString());
			int payMethod = Integer.parseInt(request.getParameter("payMethod") == null ? "-1" : request.getParameter("payMethod").equals("") ? "-1" : request.getParameter("payMethod").toString());

	    	ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
	    	
	    	if(origin > 0 && accDT.trim().length() >= 21) {
	    		data = AccountBusinessModel.salesreport(accDT, origin, destination, payMethod);
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
		    
	    	
	    	String url = "./cp/cpAccountReport.jsp";
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    	
		}
		
		

		else if (actionType.equals(GENSALESREPORT) == true) {

			String accDT = request.getParameter("accDT") == null ? "" : request.getParameter("accDT").toString();
			int origin = Integer.parseInt(request.getParameter("origin") == null ? "0" : request.getParameter("origin").equals("") ? "0" : request.getParameter("origin").toString());
			int destination = Integer.parseInt(request.getParameter("destination") == null ? "0" : request.getParameter("destination").equals("") ? "0" : request.getParameter("destination").toString());
			int payMethod = Integer.parseInt(request.getParameter("payMethod") == null ? "-1" : request.getParameter("payMethod").equals("") ? "-1" : request.getParameter("payMethod").toString());

			ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
			String result = "";

			if (origin > 0) {
				data = AccountBusinessModel.salesreport(accDT, origin, destination, payMethod);
			}

			if (data != null && data.size() > 0) {
				result = PdfBusinessModel.generateSalesReport(request, data, origin, destination, payMethod); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}
		
		
		
		else if(actionType.equals(MEMBERREPORT) == true){
	    	
	    	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}

	    	String accDT = request.getParameter("accDT") == null ? "" : request.getParameter("accDT").toString();
	    	String memberId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
	    	
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
	    	
	    	ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
	    	
	    	if(memberId != "") {
	    		data = AccountBusinessModel.searchMemberReport(pageNo, accDT, memberId);
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
		    
	    	
	    	String url = "./cp/cpMemberReport.jsp";
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    	
		}
		
		

		else if (actionType.equals(GENMEMBERREPORT) == true) {

			String accDT = request.getParameter("accDT") == null ? "" : request.getParameter("accDT").toString();
			String memberId = request.getParameter("memberId") == null ? "" : request.getParameter("memberId").toString();
			
			ArrayList<AccountDataModel> data = new ArrayList<AccountDataModel>();
			String result = "";

			if (memberId != "") {
				data = AccountBusinessModel.memberReport(accDT, memberId);
			}

			if (data != null && data.size() > 0) {
				result = PdfBusinessModel.generateMemberReport(request, data, memberId); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}
		
		
		
		else if(actionType.equals(DELIVERYREPORT) == true){
	    	
			String url = "";
			
			if( (privilege != 3)&&(privilege != 4) ) { //neither agent nor partner
				url = "./partnerlogin?returnUrl=./partnerCpConsignment";
				response.sendRedirect(url);
				return;
			}    	
	    	
	    	String createDT = request.getParameter("createDT") == null ? "" : request.getParameter("createDT").toString();
			int station = Integer.parseInt(request.getParameter("station") == null ? "2" : request.getParameter("station").equals("") ? "2" : request.getParameter("station").toString());
			int partnerId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
			int stage = Integer.parseInt(request.getParameter("stage") == null ? "0" : request.getParameter("stage").equals("") ? "0" : request.getParameter("stage").toString());
			
			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
	    	
			data = ConsignmentBusinessModel.getPartnerReport(createDT, station, partnerId, stage);

	        
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
	        
	        //Area List
	        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	        area = AreaBusinessModel.areaList();
	        request.setAttribute("area", area);
		    
	    	
	    	url = "./partner/partnerDeliveryReport.jsp";
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    	
		}
		
		
		
		else if (actionType.equals(GENDELIVERYREPORT) == true) {
			
			int partnerId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
			String createDT = request.getParameter("createDT") == null ? "" : request.getParameter("createDT").toString();
			int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").toString());
			int stage = Integer.parseInt(request.getParameter("stage") == null ? "0" : request.getParameter("stage").toString());

			ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
			String result = "";

			consignmentData = ConsignmentBusinessModel.getPartnerReport(createDT, station, partnerId, stage);

			if (consignmentData != null && consignmentData.size() > 0) {
				result = PdfBusinessModel.generatePartnerDeliveryReport(request, consignmentData, stage, userId); // 產生PDF
			} else {
				result = "noData";
			}

			out.print(result);
		}
		
		

	}
	
	
	//檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
		int iid = 19; // 必須自行手動設定，對應DB的privilege
		String url = "./stafflogin?returnUrl=./report";

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
    

}
