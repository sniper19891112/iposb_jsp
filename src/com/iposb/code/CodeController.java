package com.iposb.code;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iposb.i18n.Resource;
import com.iposb.privilege.PrivilegeBusinessModel;
import com.iposb.utils.UtilsBusinessModel;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;



public class CodeController extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

  	//Initialize global variables
  	public static final String OBJECTDATA = "objectData";
  	public static final String VERIFY = "verify";
  	public static final String DBCODES = "dbcodes"; //導到清單頁
  	public static final String SEARCHPROMO = "searchpromo"; 
	public static final String DBCODES_NEW = "dbcodes_new"; //導到空白頁
	public static final String DBCODES_EDIT = "dbcodes_edit"; //導到編輯頁
	public static final String DBCODES_INSERT = "dbcodes_insert"; //寫入資料庫
	public static final String DBCODES_UPDATE = "dbcodes_update"; //更新資料庫
	public static final String CHECKBYAJAX = "checkbyajax"; // footer ajax check优惠码
	public static final String AJAXVERIFYPROMOCODE = "ajaxverifypromocode"; // 预订时检查优惠码是否可用
//	public static final String GENPROMOCODE = "genPromoCode";
	
	static Logger logger = Logger.getLogger(CodeController.class);
  
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
	
    PrintWriter out = response.getWriter();
    
    String actionType = request.getParameter("actionType");
   
    if(actionType == null){
    	
    	String code = request.getParameter("code") == null ? "" : request.getParameter("code").toString();
    	
    	ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
    	
    	if(!code.equals("")){
    		data = CodeBusinessModel.verifyCode(code, request);
    		
    		if(data != null && !data.isEmpty()){
//            	session.setAttribute("promoCode", codeDetail.getCode());
            	request.setAttribute(OBJECTDATA, data);
            }
    	}
    	
    	String url = "./code.jsp";
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
        
    } 
    
    else if(actionType.equals(VERIFY) == true){
    	String promoCode = request.getParameter("promoCode");
    	
        ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
        data = CodeBusinessModel.verifyCode(promoCode, request);
        CodeDataModel codeDetail = null;

        if(data != null && !data.isEmpty()){
        	codeDetail = (CodeDataModel) data.get(0);
        	       	
        	if(codeDetail.getAllowTimes() < 1){ //超過允許使用次數
        		out.print("FINISHQUOTA");
        	} else if(UtilsBusinessModel.compareDate(codeDetail.getPeriodEnd()) > 0){ //已過期
        		out.print("EXPIRED");
        	} else if(codeDetail.getIsAllow() == 0){ //不能再使用
        		out.print("INVALIDCODE");
        	} else { //都通過檢查
        		out.print(codeDetail.getDiscount());
        	}
        }
        else{
        	out.print("INVALIDCODE");
        }

    }
    
    
    else if(actionType.equals(DBCODES) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	//防止user亂輸入頁碼
    	int pageNo = 0;
    	String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
    	boolean isNum = isNumeric(page);
    	
    	String orderBy = request.getParameter("orderBy") == null ? "cid" : request.getParameter("orderBy").equals("") ? "cid" : request.getParameter("orderBy").toString();
    	
    	if(isNum){
    		pageNo = Integer.parseInt(page);
    	}
    	if( !(pageNo > 0) || !(isNum) ){
    		pageNo = 1;
    	}
    	
    	ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
        data = CodeBusinessModel.codeList(orderBy, pageNo);

        if(data != null && !data.isEmpty()){
        	request.setAttribute(OBJECTDATA, data);
        }
    	
    	String url = "./cp/cpCode.jsp";
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
    
    else if (actionType.equals(SEARCHPROMO) == true) {

		String code = request.getParameter("searchcode") == null ? "" : new String(request.getParameter("searchcode").trim().getBytes("ISO-8859-1"), "UTF-8");

		if (code.trim().equals("")) {

			String url = "./cpCode";
			response.sendRedirect(url);

		} else {

			ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
			data = CodeBusinessModel.searchCode(code);

			if (data != null && !data.isEmpty()) {
				request.setAttribute(OBJECTDATA, data);
			}

			String url = "./cp/cpCode.jsp?actionType=searchcode&code="
					+ code;
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);

		}

	}
    
    
    else if(actionType.equals(DBCODES_NEW) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	    	
    	String url = "./dbCodes.jsp?actionType=dbcodes_insert";
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
    else if(actionType.equals(DBCODES_EDIT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	String code = request.getParameter("code");
    	
    	String url = "/dbCodes.jsp?actionType=dbcodes_update";
    	ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
        data = CodeBusinessModel.codeDetails(code);

        if(data != null && !data.isEmpty()){
        	request.setAttribute(OBJECTDATA, data);
        	url = "./dbCodes.jsp?actionType=dbcodes_update";
        }
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        return;
    }
    
    else if(actionType.equals(DBCODES_INSERT) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	//code基本資料
    	ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
    	data = CodeBusinessModel.setRequestData(request);

    	String checkCode = "";
    	String code = request.getParameter("code");
 	
        if(code.trim().length() == 10) {
        	checkCode = CodeBusinessModel.checkCode(code);
        } else {
        	out.print("Code needs to be 10 characters");
			out.flush();
			return;
        }
    
    	
        if(checkCode == "exist"){
        	out.print("Code already exists");
			out.flush();
			return;
        } 
//        else if(checkCode == "noPeriodEnd"){
//        	out.print("Please input Period(End)");
//			out.flush();
//			return;
//        } else if(checkCode == "noPeriodStart"){
//        	out.print("Please input Period(Start)");
//			out.flush();
//			return;
//        } else if(checkCode == "negativeAllowTimes"){
//        	out.print("Allow Times cannot be negative");
//			out.flush();
//			return;
//        } else if(checkCode == "noAllowTimes"){
//        	out.print("Please input Allow Times");
//			out.flush();
//			return;
//        } else if(checkCode == "negativeDiscount"){
//        	out.print("Discount cannot be negative");
//			out.flush();
//			return;
//        } else if(checkCode == "noDiscount"){
//        	out.print("Please input Discount");
//			out.flush();
//			return;
//        } 
        else if (checkCode == "OK"){
	        
			if(data != null && data.size() > 0){
				//CodeDataModel obj = (CodeDataModel)data.get(0);//拿回出來用
				
				String result = "";
				result = CodeBusinessModel.addNewCode(data);
				logger.info(">>>Insert Code, data size:" + data.size());
				
				if(result.equals("OK")){ //成功
					out.print("insertSuccess");
					out.flush();
					return;
				} else { //失敗
					out.print(result);
					out.flush();
					return;
				}
				
				
			} else {
				out.print("noData");
				out.flush();
				return;
			}
        }
    }
    
    else if(actionType.equals(DBCODES_UPDATE) == true){
    	
    	if(!checkPrivilege(response, privilege)){
    		return;
    	}
    	
    	//組合Code主要資料
    	ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
    	//ArrayList<CodeDataModel> data_new = new ArrayList<CodeDataModel>();
    	data = CodeBusinessModel.setRequestData(request);
    	
		//重新查詢最新的資料
		//String code = request.getParameter("code");
        
    	String result = "";
		result = CodeBusinessModel.updateCode(data);
		
    	
		if(data != null && data.size() > 0){
			CodeDataModel obj = (CodeDataModel)data.get(0);//拿回出來用
        	
			if(result.equals("OK")){ //成功
				out.print("updateSuccess");
				out.flush();
				
//				data_new = CodeBusinessModel.codeDetails(code); //取得最新的資料取代掉
//				if(data_new != null && data_new.size() > 0){
//					data.clear(); //先清除
//					data = data_new; //把最新的資料assign給 data object
//					obj = (CodeDataModel)data.get(0);
//					obj.setErrmsg("updateSuccess");
//				}
			} else if(result.equals("")){ //沒有資料
				out.print("noData");
				out.flush();
				
//				obj.setErrmsg("noData");
			} else { //失敗
				out.print(result);
				out.flush();
				
//				obj.setErrmsg(result);
			}
			
//			logger.info(">>>Update Code, data size:" + data.size());
			data.set(0, obj); //加入訊息的標籤
			
			request.setAttribute(OBJECTDATA, data);
		}
		
//		String url = "/cp/cpCode.jsp";
//		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//      dispatcher.forward(request, response);
        return;
    }
    
    
    
    else if(actionType.equals(CHECKBYAJAX) == true){
    	
    	String code = request.getParameter("code") == null ? "" : request.getParameter("code").toString();
    	String locale = request.getParameter("locale") == null ? "en_US" : request.getParameter("locale").toString();
    	String discount = Resource.getString("ID_LABEL_UNKNOWN",locale);
    	String periodStart = "--";
    	String periodEnd = "--";
    	String isAllow = "--";
    	String result = "";
    	
    	ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
		CodeDataModel codeData = new CodeDataModel();
    	
    	if(!code.equals("") && code.length() == 10){
    		data = CodeBusinessModel.verifyCode(code, request);
    		
    		if(data != null && !data.isEmpty()){
				
    			codeData = (CodeDataModel)data.get(0);
    			
    			periodStart = codeData.getPeriodStart() == null ? "--" : codeData.getPeriodStart().equals("") ? "--" : codeData.getPeriodStart();
    			periodEnd  = codeData.getPeriodEnd() == null ? "--" : codeData.getPeriodEnd().equals("") ? "--" : codeData.getPeriodEnd();
    			if(UtilsBusinessModel.compareDate(codeData.getPeriodEnd()) <= 0){
    				if(codeData.getAllowTimes() >= 1){ // Allow Times 还没用完
    					if(codeData.getIsAllow() == 1){ // 可以使用
    						isAllow = Resource.getString("ID_LABEL_VALID",locale);
    						if(codeData.getDiscountType() == 0){
        						discount = "MYR " + codeData.getDiscount();
    						}else if(codeData.getDiscountType() == 1){
    							discount = codeData.getDiscount() + "%";
    						}
    					}else{
    						periodStart = "--";
    						periodEnd = "--";
    						isAllow = Resource.getString("ID_LABEL_INVALIDCODE",locale);
    					}
    				}else{
    					isAllow = Resource.getString("ID_LABEL_FINISHQUOTA",locale);
    				}
    			}else{
    				isAllow = Resource.getString("ID_LABEL_EXPIRED",locale);
    			}
    			
				
				
				result = "{\"code\": \"" + code + "\", " +
						 "\"discount\": \"" + discount + "\", " +
						 "\"periodStart\": \"" + periodStart + "\", " +
						 "\"periodEnd\": \"" + periodEnd + "\", " +
						 "\"isAllow\": \"" + isAllow + "\"}";
				
				out.print(result);
				out.flush();
				return;
				
            }else{
            	result = "{\"code\": \"" + code + "\", " +
						 "\"discount\": \"" + discount + "\", " +
						 "\"periodStart\": \"--\", " +
						 "\"periodEnd\": \"--\", " +
						 "\"isAllow\": \"" + Resource.getString("ID_LABEL_INVALIDCODE",locale) + "\"" +
						 "}";
            	out.print(result);
            	out.flush();
            	return;
            }
    	}else{
    		result = "{\"code\": \"" + code + "\", " +
					 "\"discount\": \"" + discount + "\", " +
					 "\"periodStart\": \"--\", " +
					 "\"periodEnd\": \"--\", " +
					 "\"isAllow\": \"" + Resource.getString("ID_LABEL_INVALIDCODE",locale) + "\"" +
					 "}";
        	out.print(result);
        	out.flush();
        	return;
    	}
    	
    }
    
    
    
    else if(actionType.equals(AJAXVERIFYPROMOCODE) == true){
    	
    	String promoCode = request.getParameter("promoCode") == null ? "" : request.getParameter("promoCode").toString();
    	String locale = request.getParameter("locale") == null ? "en_US" : request.getParameter("locale").toString();
    	int discount = 0;
    	String isAllow = "";
    	int discountType = 0;
    	int verify = 0;
    	String result = "";
    	
    	ArrayList<CodeDataModel> data = new ArrayList<CodeDataModel>();
		CodeDataModel codeData = new CodeDataModel();
    	
    	if(!promoCode.equals("") && promoCode.length() == 10){
    		data = CodeBusinessModel.verifyCode(promoCode, request);
    		
    		if(data != null && !data.isEmpty()){
				
    			codeData = (CodeDataModel)data.get(0);
    			
    			if(UtilsBusinessModel.compareDate(codeData.getPeriodEnd()) <= 0){
    				if(codeData.getAllowTimes() >= 1){ // Allow Times 还没用完
    					if(codeData.getIsAllow() == 1){ // 可以使用
    						discount = codeData.getDiscount();
    						discountType = codeData.getDiscountType();
    						verify = 1;
    						isAllow = Resource.getString("ID_LABEL_VALID",locale);
    					}else{
    						isAllow = Resource.getString("ID_LABEL_INVALIDCODE",locale);
    					}
    				}else{
    					isAllow = Resource.getString("ID_LABEL_FINISHQUOTA",locale);
    				}
    			}else{
    				isAllow = Resource.getString("ID_LABEL_EXPIRED",locale);
    			}
    			
				
				
				result = "{\"promoCode\": \"" + promoCode + "\", " +
						 "\"discount\": \"" + discount + "\", " +
						 "\"discountType\": \"" + discountType + "\", " +
						 "\"isAllow\": \"" + isAllow + "\", " +
						 "\"verify\": \"" + verify + "\"}";
				
				out.print(result);
				out.flush();
				return;
				
            }else{
            	result = "{\"promoCode\": \"" + promoCode + "\", " +
						 "\"discount\": \"" + discount + "\", " +
						 "\"discountType\": \"" + discountType + "\", " +
						 "\"isAllow\": \"" + Resource.getString("ID_LABEL_INVALIDCODE",locale) + "\", " +
						 "\"verify\": \"0\"}";
            	out.print(result);
            	out.flush();
            	return;
            }
    	}else{
    		result = "{\"promoCode\": \"" + promoCode + "\", " +
					 "\"discount\": \"" + discount + "\", " +
					 "\"discountType\": \"" + discountType + "\", " +
					 "\"isAllow\": \"" + Resource.getString("ID_LABEL_INVALIDCODE",locale) + "\", " +
					 "\"verify\": \"0\"}";
        	out.print(result);
        	out.flush();
        	return;
    	}
    	
    }
    
//    else if(actionType.equals(GENPROMOCODE) == true){
//    	String code = CodeBusinessModel.genPromoCode();
//    	
//    	return;
//    }

  }
  
  
  //檢查是否具備足夠權限使用本項目
  private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
	int iid = 22; //必須自行手動設定，對應DB的privilege
  	String url = "./stafflogin?returnUrl=./cpCode";
  	
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