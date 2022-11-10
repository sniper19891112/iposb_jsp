package com.iposb.my;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.iposb.area.AreaBusinessModel;
import com.iposb.area.AreaDataModel;
import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.consignment.ConsignmentDataModel;


public class MyController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	// Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String MYCONSIGNMENT = "myConsignment";
	public static final String SEARCHMYCONSIGNMENT = "searchMyConsignment"; // user 搜寻自己订单
	
	public static final String ADDRESSBOOK = "addressbook";
	public static final String NEWADDRESSBOOK = "newaddressbook";
	public static final String INSERTADDRESSBOOK = "insertaddressbook";
	public static final String UPDATEADDRESSBOOK= "updateaddressbook";
	public static final String EDITADDRESSBOOK = "editaddressbook";
	
	public static final String CREDITACCOUNT = "creditaccount";
	public static final String NEWCREDITACCOUNT = "newcreditaccount";
	public static final String INSERTCREDITACCOUNT = "insertcreditaccount";
	public static final String UPDATECREDITACCOUNT= "updatecreditaccount";
	public static final String EDITCREDITACCOUNT = "editcreditaccount";

	private static String RESULT = "";
	
	static Logger logger = Logger.getLogger(MyController.class);

	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		return;
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
//		String locale = request.getParameter("lang");

		if ((actionType == null)||(actionType.equals(MYCONSIGNMENT) == true)) {

			// 防止user亂輸入頁碼
			int pageNo = 0;
			String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
			boolean isNum = isNumeric(page);

			if (isNum) {
				pageNo = Integer.parseInt(page);
			}
			if (!(pageNo > 0) || !(isNum)) {
				pageNo = 1;
			}

			String url = "";

			if (currentUser.isAuthenticated()) { //有登入
				
				if(privilege >= 5) { //staff
					
					url = "./staffprofile";
					
				} else if(privilege == 3 || privilege == 4) { //agent or partner
					
					url = "./companyprofile";
					
				} else {
					
					ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
					
					consignmentData = ConsignmentBusinessModel.getRecordByUserId(userId, pageNo);
					
					if(consignmentData != null) {
						request.setAttribute(OBJECTDATA, consignmentData);
						url = "./myConsignment.jsp";
					} else {
						url = "./myConsignment.jsp?result=noConnection";
					}
					
				}


			} else { // cookie失效
				url = "./login?returnUrl=./my";
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
						
		}
		
		
		
		else if (actionType.equals(SEARCHMYCONSIGNMENT) == true) {
			
			String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();

			String url = "";
			
			if (currentUser.isAuthenticated()) { //有登入
				
				if(privilege >= 5) { //staff
					
					url = "./staffprofile";
					
				} else {
					
					ArrayList<ConsignmentDataModel> consignmentData = new ArrayList<ConsignmentDataModel>();
					
					if(consignmentNo.trim().length()==9){ //有consignment Number
						consignmentData = ConsignmentBusinessModel.getRecordByConsignmentNo(consignmentNo);
						
						if(consignmentData != null && consignmentData.size() > 0) {
							
							//credit account
			        	    if(privilege == 2) { //credit user
			        	    	int mid = MyBusinessModel.getMidByUserId(userId);
			        	  		ArrayList<MyDataModel> creditaccount = new ArrayList<MyDataModel>();
			        	  		creditaccount = MyBusinessModel.creditaccountList(mid);
			        	  	    request.setAttribute("creditaccount", creditaccount);
			        	    }
			        	    
							//Area List
					        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
					        area = AreaBusinessModel.areaList();
					        request.setAttribute("area", area);
						    
							request.setAttribute(OBJECTDATA, consignmentData);
							url = "./myConsignment.jsp?searchConsignmentNo=" + consignmentNo;
						} else {
							url = "./myConsignment.jsp";
						}
						
					} else {
						url = "./myConsignment.jsp";
					}
					
				}


			} else { // cookie失效
				url = "./login?returnUrl=./my";
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
			
		}
		
		
		else if (actionType.equals(ADDRESSBOOK) == true) {
	    	
	    	String url = "";
	    	
	    	if(privilege==1 || privilege==2) { //normal user or credit term user
	    		
	    		int mid = MyBusinessModel.getMidByUserId(userId);
	    		
	    		ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
	        	data = MyBusinessModel.addressbookList (mid); //查詢所有addressbook List

	            if(data != null && !data.isEmpty()){
	            	
	            	//如果RESULT有值代表儲存DB後再進入這裡
	                if(!RESULT.equals("")){
	                	MyDataModel obj = (MyDataModel)data.get(0);
	            		obj.setErrmsg(RESULT); //加入訊息的標籤
	            		data.set(0, obj); //再設入
	            		RESULT = ""; //清除掉記錄
	                }
	                
	            	request.setAttribute(OBJECTDATA, data);
	            }

	    		url = "./myAddressbook.jsp";
	    		
	    	} else {
	    		url = "./login.jsp?returnUrl=./addressbook";
	    	}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		
		else if(actionType.equals(NEWADDRESSBOOK) == true){
			
			//組成 area dropdown list
	        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	        area = AreaBusinessModel.areaList();
	        request.setAttribute("area", area);
	        
	        
	    	String url = "./myAddressbook.jsp?actionType=insertaddressbook";
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    }
		
		
		else if(actionType.equals(INSERTADDRESSBOOK) == true){
		       
	    	String result = "";
	    	String url = "";

	    	ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
	    	data = MyBusinessModel.setRequestData(request);
	    	
	    	int mid = MyBusinessModel.getMidByUserId(userId);
	                
			result = MyBusinessModel.addNewAddressbook(data, mid);//寫入資料庫
	        	
			if(result.equals("OK")){ //成功
				
				url = "./addressbook?result=createaddressbookSuccess";
				response.sendRedirect(url);
				
			} else { //失敗, 導回填寫資料頁
	        	
	        	url = "./myAddressbook.jsp?result=createaddressbookFailed";
	        	
	        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	            dispatcher.forward(request, response);
			}
			
	        return;
	    }
	    
	    
	    
	    else if(actionType.equals(UPDATEADDRESSBOOK) == true){
		       
	    	String result = "";
	    	String url = "";

	    	ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		  	data = MyBusinessModel.setRequestData(request);
	        
		  	int mid = MyBusinessModel.getMidByUserId(userId);
		  	
			result = MyBusinessModel.updateAddressbook(data, mid);//更改資料庫
	        	
			if(result.equals("OK")){ //成功
				
				url = "./addressbook?result=updateaddressbookSuccess";
				response.sendRedirect(url);
				
			} else { //失敗, 導回填寫資料頁

	        	url = "./myAddressbook.jsp?result=updateaddressbookFailed";
	        	
	        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	            dispatcher.forward(request, response);
			}
			
	        return;
	    }
		
		
	    else if (actionType.equals(EDITADDRESSBOOK) == true) {
	    	
	    	String url = "";
	    	
	    	if(privilege==1 || privilege==2) { //normal user or credit term user
	    		
	    		 String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
	    		 int mid = MyBusinessModel.getMidByUserId(userId);

		   		 ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		   		  
		   		 data = MyBusinessModel.addressbookDetails(verify, mid);
	
		   		 if(data != null && !data.isEmpty()){
		   			  
		   			//如果RESULT有值代表儲存CP後再進入這裡
		               if(!RESULT.equals("")){
		            	MyDataModel obj = (MyDataModel)data.get(0);
		           		obj.setErrmsg(RESULT); //加入訊息的標籤
		           		data.set(0, obj); //再設入
		           		RESULT = ""; //清除掉記錄
		               }
		   	            
		               	request.setAttribute(OBJECTDATA, data);
		               	
		               	//組成 area dropdown list
		            	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		        		area = AreaBusinessModel.areaList();

		        	    if(area != null && !area.isEmpty()){
		        	    	request.setAttribute("area", area);
		        	    }
		        	    
		        	    
		               	url = "./myAddressbook.jsp?actionType=updateaddressbook";
		               	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				   		dispatcher.forward(request, response);
				   		return;
		               	
		   		 } else {
		   			 url = "./addressbook";
		   			 response.sendRedirect(url);
		   			 return;
		   		 }


	    	} else {
	    		url = "./login.jsp?returnUrl=./addressbook";
	    	}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		
		
	    else if (actionType.equals(CREDITACCOUNT) == true) {
	    	
	    	String url = "";
	    	
	    	if(privilege == 2) { //Credit Term User
	    		
	    		int mid = MyBusinessModel.getMidByUserId(userId);
	    		
	    		ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
	        	data = MyBusinessModel.creditaccountList (mid); //查詢所有creditaccount List

	            if(data != null && !data.isEmpty()){
	            	
	            	//如果RESULT有值代表儲存DB後再進入這裡
	                if(!RESULT.equals("")){
	                	MyDataModel obj = (MyDataModel)data.get(0);
	            		obj.setErrmsg(RESULT); //加入訊息的標籤
	            		data.set(0, obj); //再設入
	            		RESULT = ""; //清除掉記錄
	                }
	                
	            	request.setAttribute(OBJECTDATA, data);
	            }

	    		url = "./myCreditaccount.jsp";
	    		
	    	} else {
	    		url = "./login.jsp?returnUrl=./creditaccount";
	    	}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		
	    else if(actionType.equals(NEWCREDITACCOUNT) == true){
			
			//組成 area dropdown list
	        ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	        area = AreaBusinessModel.areaList();
	        request.setAttribute("area", area);
	        
	        
	    	String url = "./myCreditaccount.jsp?actionType=insertcreditaccount";
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	        return;
	    }
		
		
		
	    else if(actionType.equals(INSERTCREDITACCOUNT) == true){
		       
	    	String result = "";
	    	String url = "";

	    	ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
	    	data = MyBusinessModel.setRequestData(request);
	    	
	    	int mid = MyBusinessModel.getMidByUserId(userId);
	                
			result = MyBusinessModel.addNewCreditaccount(data, mid);//寫入資料庫
	        	
			if(result.equals("OK")){ //成功
				
				url = "./creditaccount?result=createcreditaccountSuccess";
				response.sendRedirect(url);
				
			} else { //失敗, 導回填寫資料頁
	        	
	        	url = "./myCreditaccount.jsp?result=createcreditaccountFailed";
	        	
	        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	            dispatcher.forward(request, response);
			}
			
	        return;
	    }
		
		
		
	    else if(actionType.equals(UPDATECREDITACCOUNT) == true){
		       
	    	String result = "";
	    	String url = "";

	    	ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		  	data = MyBusinessModel.setRequestData(request);
	        
		  	int mid = MyBusinessModel.getMidByUserId(userId);
		  	
			result = MyBusinessModel.updateCreditaccount(data, mid);//更改資料庫
	        	
			if(result.equals("OK")){ //成功
				
				url = "./creditaccount?result=updatecreditaccountSuccess";
				response.sendRedirect(url);
				
			} else { //失敗, 導回填寫資料頁

	        	url = "./myCreditaccount.jsp?result=updatecreditaccountFailed";
	        	
	        	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	            dispatcher.forward(request, response);
			}
			
	        return;
	    }
		
		
		
	    else if (actionType.equals(EDITCREDITACCOUNT) == true) {
	    	
	    	String url = "";
	    	
	    	if(privilege ==2) { //credit term user
	    		
	    		 String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
	    		 int mid = MyBusinessModel.getMidByUserId(userId);

		   		 ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
		   		  
		   		 data = MyBusinessModel.creditaccountDetails(verify, mid);
	
		   		 if(data != null && !data.isEmpty()){
		   			  
		   			//如果RESULT有值代表儲存CP後再進入這裡
		               if(!RESULT.equals("")){
		            	MyDataModel obj = (MyDataModel)data.get(0);
		           		obj.setErrmsg(RESULT); //加入訊息的標籤
		           		data.set(0, obj); //再設入
		           		RESULT = ""; //清除掉記錄
		               }
		   	            
		               	request.setAttribute(OBJECTDATA, data);
		               	
		               	//組成 area dropdown list
		            	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
		        		area = AreaBusinessModel.areaList();

		        	    if(area != null && !area.isEmpty()){
		        	    	request.setAttribute("area", area);
		        	    }
		        	    
		        	    
		               	url = "./myCreditaccount.jsp?actionType=updatecreditaccount";
		               	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				   		dispatcher.forward(request, response);
				   		return;
		               	
		   		 } else {
		   			 url = "./creditaccount";
		   			 response.sendRedirect(url);
		   			 return;
		   		 }


	    	} else {
	    		url = "./login.jsp?returnUrl=./creditaccount";
	    	}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		
		

	}

	// 以正規表達式判斷輸入的字串是否為數字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	// Clean up resources
	public void destroy() {
	}

}