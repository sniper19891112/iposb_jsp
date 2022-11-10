package com.iposb.logon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import com.iposb.area.AreaBusinessModel;
import com.iposb.area.AreaDataModel;
import com.iposb.i18n.Resource;
import com.iposb.privilege.PrivilegeBusinessModel;
import com.iposb.utils.GoogleRecaptcha;
import com.iposb.utils.IPTool;
import com.iposb.utils.Mailer;

public class LogonController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	// Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String MEMBERLOGIN = "login";
	public static final String STAFFLOGIN = "stafflogin";
	public static final String APPLOGIN = "applogin";
	public static final String NEWAPPLOGIN = "newapplogin";
	public static final String LOGOUT = "logout";
	public static final String REGISTERNOW = "registernow";
	public static final String REGISTER = "register";
	public static final String ACTIVATEACCOUNT = "activateAccount";
	public static final String SENDVERIFYEMAIL = "sendVerifyEmail";
	public static final String UPDATE = "update";
	public static final String SENDNEWPASSWORD = "sendNewPassword";
	public static final String PROFILE = "profile";
	public static final String STAFFPROFILE = "staffprofile";
	public static final String GETUSERIDINSESSION = "getUserIdInSession";
//	public static final String SENDVOUCHER = "sendVoucher";
	public static final String CPMEMBER = "cpmember";
	public static final String CPSTAFF = "cpstaff";
	public static final String SEARCHMEMBER = "searchmember";
	public static final String SEARCHSTAFF = "searchStaff";
	public static final String ISEXIST = "isExist";
	public static final String MD5 = "md5";
	public static final String AJAXUPDATEMEMBER = "ajaxUpdateMember";
	public static final String AJAXUPDATESTAFF = "ajaxUpdateStaff";
	public static final String AJAXSEARCHMEMBER = "ajaxSearchMember";
	public static final String AJAXSEARCHPARTNER = "ajaxSearchPartner";
	public static final String CHANGEPASSWORD = "changepassword";
	public static final String UPDATEPASSWORD = "updatePassword"; // 會員更改密碼
	public static final String APPUPDATEPASSWORD = "appupdatePassword"; // App
																		// 會員更改密碼
	public static final String FILTERPRIV = "filterPriv";
	public static final String EXPORTMEMBERLIST = "exportMemberList";
	
	static Logger logger = Logger.getLogger(LogonController.class);

	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		return;
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
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
		String locale = request.getParameter("lang");

		if (actionType == null) {
			String returnUrl = request.getParameter("returnUrl") == null ? ""
					: request.getParameter("returnUrl");
			String url = "./login.jsp?returnUrl=./" + returnUrl;

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}

		else if (actionType.equals(REGISTERNOW) == true) {
			
			String role = request.getParameter("role") == null ? "" : request.getParameter("role");
			
			String url = "./register.jsp?role=" + role;
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}
		
		/*else if (actionType.equals(MEMBERLOGIN) == true) {
			String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
			String passwd = request.getParameter("passwd") == null ? "" : request.getParameter("passwd");
			String stayloggedin = request.getParameter("stayloggedin") == null ? "" : request.getParameter("stayloggedin");
			String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl");
			String IP = IPTool.getUserIP(request);
			String url = "";

			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.verifyMember(userId, passwd);
			LogonDataModel userData = null;

			if (data != null && !data.isEmpty() && data.size() > 0) {
				LogonBusinessModel.updateIP(userId, IP);
				userData = (LogonDataModel) data.get(0);

				String called = "";
				if (userData.getUserId().trim().length() > 0) {
					called = userData.getUserId();
				} else {
					called = userData.getEmail();
				}

				loginCookie = new Cookie("userId", called);
				if (stayloggedin.equals("on")) {
					loginCookie.setMaxAge(365 * 24 * 60 * 60); // one year
				} else {
					loginCookie.setMaxAge(60 * 60); // an hour
				}
				response.addCookie(loginCookie);
				
				
				loginNMCookie = new Cookie("userName", userData.getEname()); //顯示全名
				if (stayloggedin.equals("on")) {
					loginNMCookie.setMaxAge(365 * 24 * 60 * 60); // one year
				} else {
					loginNMCookie.setMaxAge(60 * 60); // an hour
				}
				response.addCookie(loginNMCookie);
				

				privilegeCookie = new Cookie("priv", String.valueOf(userData
						.getPrivilege()));
				if (stayloggedin.equals("on")) {
					privilegeCookie.setMaxAge(365 * 24 * 60 * 60); // one year
				} else {
					privilegeCookie.setMaxAge(60 * 60); // an hour
				}
				response.addCookie(privilegeCookie);

				logger.info("--- User login success: " + userId);

				if (returnUrl.trim().equals("")) {
					url = "./my";
				} else {
					url = returnUrl;
				}
				
				if(userData.getPrivilege() == 0){
					url = "./profile";
				}
				
				response.sendRedirect(url);
			} else {
				url = "./login?result=loginFailed";
				logger.info("--- User login failed: " + userId);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(url);
				dispatcher.forward(request, response);
				return;
			}
		}

		else if (actionType.equals(STAFFLOGIN) == true) {
			String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
			String passwd = request.getParameter("passwd") == null ? "" : request.getParameter("passwd");
			String stayloggedin = request.getParameter("stayloggedin") == null ? "" : request.getParameter("stayloggedin");
			String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl");
			String IP = IPTool.getUserIP(request);
			String url = "./cp";

			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.verifyStaff(userId, passwd);
			LogonDataModel userData = null;

			if (data != null && !data.isEmpty() && data.size() > 0) {
				if (!currentUser.isAuthenticated()) {
					boolean rememberMe = false;
					if (stayloggedin.equals("on")) {
						rememberMe = true;
					}
					// create UsernamePasswordToken
					UsernamePasswordToken token = new UsernamePasswordToken(userId, passwd);
					currentUser.login(token); // 執行shiro.ini裡的查詢
					token.setRememberMe(rememberMe);
				}
				
				LogonBusinessModel.updateStaffIP(userId, IP);
				userData = (LogonDataModel) data.get(0);
				
				if( !(userData.getPrivilege() >= 5) ){
					url = "./activateAccount.jsp?role=staff";
				}

				String called = "";
				if (userData.getUserId().trim().length() > 0) {
					called = userData.getUserId();
				} else {
					called = userData.getEmail();
				}

				loginCookie = new Cookie("userId", called);
				if (stayloggedin.equals("on")) {
					loginCookie.setMaxAge(365 * 24 * 60 * 60); // one year
				} else {
					loginCookie.setMaxAge(60 * 60); // an hour
				}
				response.addCookie(loginCookie);
				
				loginNMCookie = new Cookie("userName", userData.getEname()); //顯示全名
				if (stayloggedin.equals("on")) {
					loginNMCookie.setMaxAge(365 * 24 * 60 * 60); // one year
				} else {
					loginNMCookie.setMaxAge(60 * 60); // an hour
				}
				response.addCookie(loginNMCookie);

				privilegeCookie = new Cookie("priv", String.valueOf(userData.getPrivilege()));
				if (stayloggedin.equals("on")) {
					privilegeCookie.setMaxAge(365 * 24 * 60 * 60); // one year
				} else {
					privilegeCookie.setMaxAge(60 * 60); // an hour
				}
				response.addCookie(privilegeCookie);

				logger.info("--- Staff login success: " + userId);

				response.sendRedirect(url);
			} else {
				url = "./stafflogin?result=loginFailed";
				logger.info("--- Staff login failed: " + userId);
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
				return;
			}
		}
		*/
		
		else if (actionType.equals(MEMBERLOGIN) == true) {
			String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString().trim();
			String passwd = request.getParameter("passwd") == null ? "" : request.getParameter("passwd").toString().trim();
			String stayloggedin = request.getParameter("stayloggedin") == null ? "" : request.getParameter("stayloggedin");
			String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString().trim();
			String IP = IPTool.getUserIP(request);
			String url = "./";
			boolean loginSucess = false;
			int priv = -9;

			// Shiro Login
			if (!currentUser.isAuthenticated()) {

				if (currentUser.isRemembered()) {
					currentUser.logout(); // 清除 session
				}
				
				boolean rememberMe = false;
				if (stayloggedin.equals("on")) {
					rememberMe = true;
				}
				// create UsernamePasswordToken
				UsernamePasswordToken token = new UsernamePasswordToken(loginId, passwd);
				token.setRememberMe(rememberMe);
				
				try {
					currentUser.login(token); // 執行shiro.ini裡的查詢
					token.clear();
					
					
					if(rememberMe) { //TODO: 因為 rememberMe 無效，所以暫時手動設 session 為1個月
						currentUser.getSession().setTimeout(30 * 24 * 60 * 60 * 1000);
					}
					
					
					if (currentUser.hasRole("99")) {
						priv = 99;
					} else if (currentUser.hasRole("9")) {
						priv = 9;
					} else if (currentUser.hasRole("8")) {
						priv = 8;
					} else if (currentUser.hasRole("7")) {
						priv = 7;
					} else if (currentUser.hasRole("6")) {
						priv = 6;
					} else if (currentUser.hasRole("5")) {
						priv = 5;
					} else if (currentUser.hasRole("4")) {
						priv = 4;
					} else if (currentUser.hasRole("3")) {
						priv = 3;
					} else if (currentUser.hasRole("2")) {
						priv = 2;
					} else if (currentUser.hasRole("1")) {
						priv = 1;
					} else if (currentUser.hasRole("0")) { // Needs Activate
						priv = 0;
					} else if (currentUser.hasRole("-9")) { //disabled
						priv = -9;
					}
					

					String modifyDT = LogonBusinessModel.getDataById("modifyDT", "memberlist", loginId);
					currentUser.getSession().setAttribute("modifyDT", !modifyDT.equals("") ?modifyDT.trim().replace(" ", "_") : "");
					currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", "memberlist", loginId));
					currentUser.getSession().setAttribute("id", LogonBusinessModel.getDataById("id", "memberlist", loginId));
					LogonBusinessModel.updateIP(loginId, IP); // 記錄 IP

					loginSucess = true;

				} catch (UnknownAccountException uae) {
					logger.info("There is no user with userId of " + token.getPrincipal());
				} catch (IncorrectCredentialsException ice) {
					logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
				} catch (LockedAccountException lae) {
					logger.info("The account for userId " + token.getPrincipal() + " is locked.  " + "Please contact your administrator to unlock it.");
				} catch (AuthenticationException ae) {
					logger.error("ERROR: " + ae);
				}

				if (loginSucess) {

					if (returnUrl.trim().equals("")) {
						if  ( (priv == 5)||(priv == 6)||(priv == 7)||(priv == 8)||(priv == 9)||(priv == 99) ) {
							url = "./cp";
							logger.info("--- Staff login success thru Member Login Page: " + loginId);
						} else if (priv == 4) {
							url = "./partnerCP";
							logger.info("--- Partner login success thru Member Login Page: " + loginId);
						} else if (priv == 3) {
							url = "./agentCP";
							logger.info("--- Agent login success thru Member Login Page: " + loginId);
						}  else if ( (priv == 0)||(priv == 1)||(priv == 2) ) {
							url = "./my";
							logger.info("--- Member login success thru Member Login Page: " + loginId);
						}
					} else {
						url = returnUrl;
					}
					
				} else {
					logger.info("--- User login failed: " + loginId);
					
					session.setAttribute("sessionMsg", "loginFailed");

					url = "./login";
				}
				
			}
			
			if (currentUser.hasRole("0")) url = "./activateAccount.jsp"; // Need Activate account
			response.sendRedirect(url);
			return;
		}
		
		else if (actionType.equals(STAFFLOGIN) == true) {
			String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString().trim();
			String passwd = request.getParameter("passwd") == null ? "" : request.getParameter("passwd").toString().trim();
			String stayloggedin = request.getParameter("stayloggedin") == null ? "" : request.getParameter("stayloggedin");
			String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString().trim();
			String IP = IPTool.getUserIP(request);
			String url = "./cp";
			boolean loginSucess = false;
			int priv = -9;

			// Shiro Login
			if (!currentUser.isAuthenticated()) {
				
				if (currentUser.isRemembered()){
					currentUser.logout(); // 清除 session
				}
				
				boolean rememberMe = false;
				if (stayloggedin.equals("on")) {
					rememberMe = true;
				}
				// create UsernamePasswordToken
				UsernamePasswordToken token = new UsernamePasswordToken(loginId, passwd);
				token.setRememberMe(rememberMe);

				try {
					currentUser.login(token); // 執行shiro.ini裡的查詢
					token.clear();
					
					
					if(rememberMe) { //TODO: 因為 rememberMe 無效，所以暫時手動設 session 為1個月
						currentUser.getSession().setTimeout(30 * 24 * 60 * 60 * 1000);
					}


					if (currentUser.hasRole("99")) {
						priv = 99;
					} else if (currentUser.hasRole("9")) {
						priv = 9;
					} else if (currentUser.hasRole("8")) {
						priv = 8;
					} else if (currentUser.hasRole("7")) {
						priv = 7;
					} else if (currentUser.hasRole("6")) {
						priv = 6;
					} else if (currentUser.hasRole("5")) {
						priv = 5;
					} else if (currentUser.hasRole("4")) {
						priv = 4;
					} else if (currentUser.hasRole("3")) {
						priv = 3;
					} else if (currentUser.hasRole("2")) {
						priv = 2;
					} else if (currentUser.hasRole("1")) {
						priv = 1;
					} else if (currentUser.hasRole("0")) { // Needs Activate
						priv = 0;
					} else if (currentUser.hasRole("-9")) { //disabled
						priv = -9;
					}


					String modifyDT = LogonBusinessModel.getDataById("modifyDT", "stafflist", loginId);
					currentUser.getSession().setAttribute("modifyDT", !modifyDT.equals("") ?modifyDT.trim().replace(" ", "_") : "");
					currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", "stafflist", loginId));
					currentUser.getSession().setAttribute("id", LogonBusinessModel.getDataById("id", "stafflist", loginId));
					LogonBusinessModel.updateIP(loginId, IP); // 記錄 IP

					loginSucess = true;

				} catch (UnknownAccountException uae) {
					logger.info("There is no user with userId of " + token.getPrincipal());
				} catch (IncorrectCredentialsException ice) {
					logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
				} catch (LockedAccountException lae) {
					logger.info("The account for userId " + token.getPrincipal() + " is locked.  " + "Please contact your administrator to unlock it.");
				} catch (AuthenticationException ae) {
					logger.error("ERROR: " + ae);
				}

				if (loginSucess) {

					if (returnUrl.trim().equals("")) {
						if  ( (priv == 5)||(priv == 6)||(priv == 7)||(priv == 8)||(priv == 9)||(priv == 99) ) {
							url = "./cp";
							logger.info("--- Staff login success thru Staff Login Page: " + loginId);
						} else if (priv == 4) {
							url = "./partnerCP";
							logger.info("--- Partner login success thru Staff Login Page: " + loginId);
						} else if (priv == 3) {
							url = "./agentCP";
							logger.info("--- Agent login success thru Member Login Page: " + loginId);
						} else if ( (priv == 0)||(priv == 1)||(priv == 2) ) {
							url = "./my";
							logger.info("--- Member login success thru Staff Login Page: " + loginId);
						}
					} else {
						url = returnUrl;
					}
					
				} else {
					logger.info("--- Staff login failed: " + loginId);
					
					session.setAttribute("sessionMsg", "loginFailed");

					url = "./stafflogin";
				}
				
			}
			
			if (currentUser.hasRole("0")) url = "./activateAccount.jsp?role=staff"; // Need Activate account
			
			response.sendRedirect(url);
			return;
		}

		else if (actionType.equals(APPLOGIN) == true) {
			String email = request.getParameter("email") == null ? "" : request .getParameter("email").toString().trim();
			String passwd = request.getParameter("passwd") == null ? "" : request.getParameter("passwd").toString().trim();
			String tokenFCM = request.getParameter("tokenFCM") == null ? "" : request.getParameter("tokenFCM").toString();
			String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");
			String IP = IPTool.getUserIP(request);

			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.verifyStaff(email, passwd);
			LogonDataModel userData = null;
			
			Map<String, String> jsonObj = new LinkedHashMap<String, String>();

			if (data != null && !data.isEmpty() && data.size() > 0) {

				// Shiro Login 暫時性 hardcode
				if (!currentUser.isAuthenticated()) {
					// create UsernamePasswordToken
					UsernamePasswordToken token = new UsernamePasswordToken(email, passwd);
					token.setRememberMe(true);

					try {
						currentUser.login(token); // 執行shiro.ini裡的查詢
						token.clear();
						
						//hardcode rememberme 1個月
						currentUser.getSession().setTimeout(30 * 24 * 60 * 60 * 1000);
						
						currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", "stafflist", email));

					} catch (UnknownAccountException uae) {
						logger.info("There is no user with userId of " + token.getPrincipal());
					} catch (IncorrectCredentialsException ice) {
						logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
					} catch (LockedAccountException lae) {
						logger.info("The account for userId " + token.getPrincipal() + " is locked.  " + "Please contact your administrator to unlock it.");
					} catch (AuthenticationException ae) {
						logger.error("ERROR: " + ae);
					}
					
				}
				
				boolean sameToken = LogonBusinessModel.tokenIsSame(email, tokenFCM, "staff");
				if(!sameToken){
					LogonBusinessModel.updateToken(email, tokenFCM, "staff");
				}

				userData = (LogonDataModel) data.get(0);

				LogonBusinessModel.updateStaffIP(userData.getUserId(), IP);
				logger.info("--- User login success via apps: " + email);

				jsonObj.put("result", "1");
				jsonObj.put("sessionId", session.getId());
				jsonObj.put("mid", String.valueOf(userData.getMid()));
				jsonObj.put("ename", userData.getEname());
				jsonObj.put("cname", userData.getCname());
				jsonObj.put("email", userData.getEmail());
				jsonObj.put("userId", userData.getUserId());
				jsonObj.put("privilege", String.valueOf(userData.getPrivilege()));
				jsonObj.put("stage1", String.valueOf(userData.getStage1()));
				jsonObj.put("stage2", String.valueOf(userData.getStage2()));
				jsonObj.put("stage3", String.valueOf(userData.getStage3()));
				jsonObj.put("stage4", String.valueOf(userData.getStage4()));
				jsonObj.put("stage5", String.valueOf(userData.getStage5()));
				jsonObj.put("stage6", String.valueOf(userData.getStage6()));
				jsonObj.put("stage7", String.valueOf(userData.getStage7()));

			} else {

				logger.info("--- User login failed via apps: " + email);

				jsonObj.put("result", "0");
				jsonObj.put("error_msg", "Incorrect email or password!");

			}

			String jsonText = JSONValue.toJSONString(jsonObj);
			out.print(callback + "(" + jsonText + ")");

		}
		
		else if (actionType.equals(NEWAPPLOGIN) == true) {
//			String email = request.getParameter("email") == null ? "" : request .getParameter("email").toString().trim();
//			String passwd = request.getParameter("passwd") == null ? "" : request.getParameter("passwd").toString().trim();
//			String tokenFCM = request.getParameter("tokenFCM") == null ? "" : request.getParameter("tokenFCM").toString();
//			String IP = IPTool.getUserIP(request);
//			logger.info(currentUser.getSession().getTimeout());
			
			BufferedReader logindata = request.getReader();
			String loginjson = logindata.readLine();
			JSONObject loginobj = new JSONObject(loginjson);
			String email = loginobj.getString("email");
			String passwd = loginobj.getString("passwd");
			String tokenFCM = loginobj.getString("tokenFCM");
			String IP = IPTool.getUserIP(request);
			
			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.verifyStaff(email, passwd);
			LogonDataModel userData = null;
			
			Map<String, String> jsonObj = new LinkedHashMap<String, String>();

			if (data != null && !data.isEmpty() && data.size() > 0) {

				// Shiro Login 暫時性 hardcode
				if (!currentUser.isAuthenticated()) {
					// create UsernamePasswordToken
					UsernamePasswordToken token = new UsernamePasswordToken(email, passwd);
					token.setRememberMe(true);

					try {
						currentUser.login(token); // 執行shiro.ini裡的查詢
						token.clear();
						
						//hardcode rememberme 1個月
						currentUser.getSession().setTimeout(30 * 24 * 60 * 60 * 1000);
						
						currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", "stafflist", email));

					} catch (UnknownAccountException uae) {
						logger.info("There is no user with userId of " + token.getPrincipal());
					} catch (IncorrectCredentialsException ice) {
						logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
					} catch (LockedAccountException lae) {
						logger.info("The account for userId " + token.getPrincipal() + " is locked.  " + "Please contact your administrator to unlock it.");
					} catch (AuthenticationException ae) {
						logger.error("ERROR: " + ae);
					}
					
				}
				
				boolean sameToken = LogonBusinessModel.tokenIsSame(email, tokenFCM, "staff");
				if(!sameToken){
					LogonBusinessModel.updateToken(email, tokenFCM, "staff");
				}

				userData = (LogonDataModel) data.get(0);

				LogonBusinessModel.updateStaffIP(userData.getUserId(), IP);
				logger.info("--- User login success via apps: " + email);

				jsonObj.put("result", "1");
				jsonObj.put("sessionId", session.getId());
				jsonObj.put("mid", String.valueOf(userData.getMid()));
				jsonObj.put("ename", userData.getEname());
				jsonObj.put("cname", userData.getCname());
				jsonObj.put("email", userData.getEmail());
				jsonObj.put("userId", userData.getUserId());
				jsonObj.put("privilege", String.valueOf(userData.getPrivilege()));
				jsonObj.put("stage1", String.valueOf(userData.getStage1()));
				jsonObj.put("stage2", String.valueOf(userData.getStage2()));
				jsonObj.put("stage3", String.valueOf(userData.getStage3()));
				jsonObj.put("stage4", String.valueOf(userData.getStage4()));
				jsonObj.put("stage5", String.valueOf(userData.getStage5()));
				jsonObj.put("stage6", String.valueOf(userData.getStage6()));
				jsonObj.put("stage7", String.valueOf(userData.getStage7()));

			} else {

				logger.info("--- User login failed via apps: " + email);

				jsonObj.put("result", "0");
				jsonObj.put("error_msg", "Incorrect email or password!");

			}

			String jsonText = JSONValue.toJSONString(jsonObj);
			out.print(jsonText);

		}

		else if (actionType.equals(LOGOUT) == true) {

			if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
				logger.info("--- User logout: " + userId);
				currentUser.logout(); // 清除 session
			}

			session = request.getSession(true);
			session.setAttribute("sessionMsg", "logoutSuccess");

			String url = "./login";
			if(privilege>=5) url = "./stafflogin";
			else if(privilege==3 || privilege==4) url = "./partnerlogin";
			
			response.sendRedirect(url);
			return;
		}

		else if (actionType.equals(REGISTER) == true) {
			
			ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
			userData = LogonBusinessModel.setRequestData(request);
			
			String role = request.getParameter("role") == null ? "" : request.getParameter("role");
			String result = "";
			String url = "";
			boolean isValid;
			String table = "memberlist";
			
			String captchatoken = request.getParameter("captchatoken") == null ? "" : request.getParameter("captchatoken");
			
			try {
				isValid = GoogleRecaptcha.isValid(captchatoken);
				
				if (isValid) {
					
					if(role.equals("staff")) {
						result = LogonBusinessModel.addNewStaff(userData);
						table = "stafflist";
					} else {
						String verify = LogonBusinessModel.generateAccountVerifyCode();
						result = LogonBusinessModel.addNewMember(userData, verify);
					}

					if (result.equals("OK")) { // 成功
						LogonDataModel obj = (LogonDataModel) userData.get(0);// 拿回出來用
						
						if(!role.equals("staff")) {
							LogonBusinessModel.sendVerifyEmailNormal(obj.getUserId());//send verification email
						}
						
						// Shiro Login
						if (!currentUser.isAuthenticated() && !currentUser.isRemembered()) {

							// create UsernamePasswordToken
							UsernamePasswordToken token = new UsernamePasswordToken(obj.getUserId(), obj.getPasswd(), true);
							try {
								currentUser.login(token); // 執行shiro.ini裡的查詢
								token.clear();
								
								//TODO: 因為 rememberMe 無效，所以暫時手動設 session 為1個月
								currentUser.getSession().setTimeout(30 * 24 * 60 * 60 * 1000);

								String modifyDT = LogonBusinessModel.getDataById("modifyDT", table, obj.getUserId());
								currentUser.getSession().setAttribute("modifyDT", !modifyDT.equals("") ?modifyDT.trim().replace(" ", "_") : "");
								currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", table, obj.getUserId()));
								currentUser.getSession().setAttribute("id", LogonBusinessModel.getDataById("id", table, obj.getUserId()));

							} catch (UnknownAccountException uae) {
								logger.info("There is no user with userId of " + token.getPrincipal());
							} catch (IncorrectCredentialsException ice) {
								logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
							} catch (LockedAccountException lae) {
								logger.info("The account for userId " + token.getPrincipal() + " is locked.  " + "Please contact your administrator to unlock it.");
							} catch (AuthenticationException ae) {
								logger.error("ERROR: " + ae);
							}

							url = "./activateAccount.jsp?role="+role; //waiting for verification
							response.sendRedirect(url);
							return;
						}
						
					} else { // 失敗
						LogonDataModel obj = (LogonDataModel) userData.get(0);// 拿回出來用
						obj.setErrmsg(result);
						userData.set(0, obj); // 加入錯誤訊息的標籤
						request.setAttribute(OBJECTDATA, userData);
						url = "./register.jsp?role="+role;
					}

					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
					return;

				} else {
					logger.info("*** Visitor missed to tick the captcha upon register. IP: " + IPTool.getUserIP(request));

					if (userData.size() > 0) {
						LogonDataModel obj = (LogonDataModel) userData.get(0);// 拿回出來用
						obj.setErrmsg("gRecaptcha");
						userData.set(0, obj); // 加入錯誤訊息的標籤
						request.setAttribute(OBJECTDATA, userData);
					}
					url = "./register.jsp?role="+role;

					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
					return;
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		else if (actionType.equals(ACTIVATEACCOUNT) == true) {
			
			String email = request.getParameter("email") == null ? "" : request.getParameter("email").toString();
			String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
//			String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString();
			
//			ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
//			userData = LogonBusinessModel.setRequestData(request);
			
			String result = "";
			String activateResult = "";
			String url = "";
			
			if(email != "" && verify != ""){
				result = LogonBusinessModel.checkVerifyCode(email, verify);
				
				if(result == "OK"){
					activateResult = LogonBusinessModel.activateAccount(email);
					
					if(activateResult == "success"){
						logger.info("****** "+ email +": Account activation success");
						
	    	            //privilegeCookie = new Cookie("priv", "1");
	    	            //privilegeCookie.setMaxAge(365 * 24 * 60 * 60); // one year    //(60*60) = an hour
	    	            //response.addCookie(privilegeCookie);
						
						privilege = 1;
						
						url = "./register.jsp";
					}
				} else if(result == "alreadyVerified"){
					logger.info("****** "+ email +": Account already activated");
				} else {
					logger.info("****** "+ email +": Account activation failed");
				}
				
			} else {
				url = "./activateAccount.jsp";
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		else if (actionType.equals(SENDVERIFYEMAIL) == true) {
			
			String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
			
			String url = "";
			
			LogonBusinessModel.sendVerifyEmailNormal(loginId);
			
			url = "./activateAccount.jsp?resend=success";
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		else if (actionType.equals(UPDATE) == true) {
			
			String role = request.getParameter("role") == null ? "" : request .getParameter("role");

			ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
			userData = LogonBusinessModel.setRequestData(request);

			String table = "memberlist";
			String url = "";
			int result = 0;
			String updatedUserId = "";

			//String userId = loginCookie == null ? "" : loginCookie.getValue().toString();
			//String priv = privilegeCookie == null ? "" : privilegeCookie.getValue().toString();
			
			if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
				logger.info("--- User is updating profile: " + userId);
				
				if(role.equals("staff")) {
					result = LogonBusinessModel.updateStaff(userData, userId);
				} else {
					result = LogonBusinessModel.updateMember(userData, userId, privilege);
				}
			}

			/*if (privilege == 0) { 
				LogonDataModel obj = (LogonDataModel) userData.get(0);
				updatedUserId = obj.getEmail();
				
				loginCookie = new Cookie("userId", updatedUserId);
	            loginCookie.setMaxAge(365 * 24 * 60 * 60); // one year    //(60*60) = an hour
	            response.addCookie(loginCookie);
			}*/
			
			if (userData.size() > 0) {

				// 重新查詢
				ArrayList<LogonDataModel> userDataNew = new ArrayList<LogonDataModel>();
				if(role.equals("staff")) {
					userDataNew = LogonBusinessModel.getStaffData(userId);
					table = "stafflist";
				} else {
					userDataNew = LogonBusinessModel.getMemberData(userId);
				}

				if (userDataNew != null && !userDataNew.isEmpty()) {
					LogonDataModel obj = (LogonDataModel) userDataNew.get(0);

					if (result == 1) { // 成功
						obj.setErrmsg("updateSuccess");
						String modifyDT = LogonBusinessModel.getDataById("modifyDT", table, userId);
						currentUser.getSession().setAttribute("modifyDT", !modifyDT.equals("") ?modifyDT.trim().replace(" ", "_") : "");
						currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", table, userId)); // Get new username
						currentUser.getSession().setAttribute("id", LogonBusinessModel.getDataById("id", table, userId));
						
						// Mailer.sendEmailToAdmin("User "+userId+" has updated profile",
						// "book@sabahbooking.com"); //發送通知給管理員
					} else if (result == 0) { // 失敗
						obj.setErrmsg("updateFailed");
						logger.info("--- update profile Failed");
					} else if (result == -1) { // 密碼不正確
						obj.setErrmsg("wrongPassword");
						logger.info("--- profile wrong Password");
					} else if (result == -2) { // 帳號或電郵已被使用
						obj.setErrmsg("userIdEmailExist");
						logger.info("--- profile userId / Email Exist");
					}

					userDataNew.set(0, obj); // 加入訊息的標籤
					request.setAttribute(OBJECTDATA, userDataNew);

				}
			}

			url = "./profile.jsp?role="+role;
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(SENDNEWPASSWORD) == true) {

			String email = request.getParameter("email") == null ? "" : request.getParameter("email").toString().trim();
			String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString().trim();

			String result = "";
			ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
			if (role.equals("staff")) {
				userData = LogonBusinessModel.getStaffData(email);
			} else {
				userData = LogonBusinessModel.getMemberData(email);
			}

			if (userData != null && userData.size() == 1) {
				String pw = Mailer.getPassword(4);
				int upd = 0;
				if (role.equals("staff")) {
					upd = LogonBusinessModel.updateStaffPassword(email, pw);
				} else {
					upd = LogonBusinessModel.updateMemberPassword(email, pw);
				}

				if (upd == 1) {
					result = Mailer.sendNewPasswordEmail(pw, email, locale);
					if(result.equals("OK")) {
						logger.info("--- reset sucessfully & mailed to user: " + email);
					} else {
						logger.info("--- reset sucessfully but mailed failed: " + email);
					}
				} else {
					result = "--- updateFailed";
					logger.info("--- reset failed: " + email);
				}
			} else {
				logger.info("--- Found no data... Reset failed: " + email);
			}

			out.print(result);
		}

		
		else if (actionType.equals(PROFILE) == true) {
			String url = "";
			
			if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
				ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
				userData = LogonBusinessModel.getMemberData(userId);

				if (userData != null && !userData.isEmpty()) {
					request.setAttribute(OBJECTDATA, userData);
					url = "./profile.jsp";
				} else { // 沒有資料
					LogonDataModel obj = new LogonDataModel();
					obj.setErrmsg("queryFailed");
					userData = new ArrayList<LogonDataModel>();
					// userData.set(0, obj); //加入錯誤訊息的標籤
					request.setAttribute(OBJECTDATA, userData);
					url = "./login?returnUrl=./profile";
				}

			} else { // cookie失效
				url = "./login?returnUrl=./profile";
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		
		else if (actionType.equals(STAFFPROFILE) == true) {

			String url = "";

			if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
				ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
				userData = LogonBusinessModel.getStaffData(userId);

				if (userData != null && !userData.isEmpty()) {
					request.setAttribute(OBJECTDATA, userData);
					url = "./profile.jsp?role=staff";
				} else { // 沒有資料
					LogonDataModel obj = new LogonDataModel();
					obj.setErrmsg("queryFailed");
					userData = new ArrayList<LogonDataModel>();
					// userData.set(0, obj); //加入錯誤訊息的標籤
					request.setAttribute(OBJECTDATA, userData);
					url = "./stafflogin?returnUrl=./staffprofile";
				}

			} else { // cookie失效
				url = "./stafflogin?returnUrl=./staffprofile";
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		
		else if (actionType.equals(GETUSERIDINSESSION) == true) {
			out.print(userId); // 因為是ajax送過來,所以不需要設定導頁,只需out.print即可!
		}

		else if (actionType.equals(CPMEMBER) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			// 防止user亂輸入頁碼
			int pageNo = 0;
			String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
			boolean isNum = isNumeric(page);

			String orderBy = request.getParameter("orderBy") == null ? "mid" : request.getParameter("orderBy").equals("") ? "mid" : request.getParameter("orderBy").toString();

			if (isNum) {
				pageNo = Integer.parseInt(page);
			}
			if (!(pageNo > 0) || !(isNum)) {
				pageNo = 1;
			}

			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.memberList(orderBy, pageNo);

			if (data != null && !data.isEmpty()) {
				request.setAttribute(OBJECTDATA, data);
			}

			String url = "./cp/cpMember.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(CPSTAFF) == true) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			// 防止user亂輸入頁碼
			int pageNo = 0;
			String page = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
			boolean isNum = isNumeric(page);

			String orderBy = request.getParameter("orderBy") == null ? "sid" : request.getParameter("orderBy").equals("") ? "sid" : request.getParameter("orderBy").toString();

			if (isNum) {
				pageNo = Integer.parseInt(page);
			}
			if (!(pageNo > 0) || !(isNum)) {
				pageNo = 1;
			}

			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.staffList(orderBy, pageNo);

			if (data != null && !data.isEmpty()) {
				request.setAttribute(OBJECTDATA, data);
				
				ArrayList<AreaDataModel> aData = new ArrayList<AreaDataModel>();
				aData = AreaBusinessModel.areaList();
				
				request.setAttribute("area", aData);
			}

			String url = "./cp/cpStaff.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(SEARCHMEMBER) == true) {

			String searchUserId = request.getParameter("searchuser") == null ? "" : new String(request.getParameter("searchuser").trim().getBytes("ISO-8859-1"), "UTF-8");

			if (searchUserId.trim().equals("")) {

				String url = "./cp/cpMember";
				response.sendRedirect(url);

			} else {

				ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
				data = LogonBusinessModel.searchMember(searchUserId);

				if (data != null && !data.isEmpty()) {
					request.setAttribute(OBJECTDATA, data);
				}

				String url = "./cp/cpMember.jsp?actionType=searchmember&userId="+ searchUserId;
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);

			}

		}
		
		else if (actionType.equals(SEARCHSTAFF) == true) {

			String searchUserId = request.getParameter("searchuser") == null ? "" : new String(request.getParameter("searchuser").trim().getBytes("ISO-8859-1"), "UTF-8");

			if (searchUserId.trim().equals("")) {

				String url = "./cp/cpStaff";
				response.sendRedirect(url);

			} else {

				ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
				data = LogonBusinessModel.searchStaff(searchUserId);

				if (data != null && !data.isEmpty()) {
					request.setAttribute(OBJECTDATA, data);
				}

				String url = "./cp/cpStaff.jsp?actionType=searchStaff&userId="+ searchUserId;
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);

			}

		}

		else if (actionType.equals(ISEXIST) == true) {
			String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString().trim();
			String email = request.getParameter("email") == null ? "" : request.getParameter("email").toString().trim();

			String isExist = ""; // user沒輸入任何字串
			if (email != null && email.length() >= 3) {
				if (LogonBusinessModel.checkExist(email)) {
					isExist = "Y";
				} else {
					isExist = "N";
				}
			} else if (loginId != null && loginId.length() >= 3) {
				if (LogonBusinessModel.checkExist(loginId)) {
					isExist = "Y";
				} else {
					isExist = "N";
				}
			}
			out.print(isExist);
		}

		else if (actionType.equals(MD5) == true) {
			String txt = request.getParameter("txt") == null ? "" : request
					.getParameter("txt").toString().trim();
			out.print(LogonBusinessModel.getMD5(txt));
		}

		else if (actionType.equals(AJAXUPDATEMEMBER)) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			String area = request.getParameter("area") == null ? "" : request.getParameter("area").toString();

			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.setRequestData(request);
			
			LogonDataModel logonData = new LogonDataModel();
			logonData = data.get(0);
			
			String accNo = "";
			
			if(logonData.getPrivilege() == 2) { //credit term user
				if(logonData.getAccNo() == "") {
					accNo = LogonBusinessModel.generateAccNo(area);
				} else {
					accNo = logonData.getAccNo();
				}
			} else if (logonData.getPrivilege() != 2){
				if(logonData.getAccNo() != "") {
					accNo = logonData.getAccNo();
				}
			}

			String returnValue = LogonBusinessModel.adminUpdateMember(data, privilege, accNo, locale);

			out.print(returnValue);
			out.flush();
			out.close();
		}
		
		
		else if (actionType.equals(AJAXUPDATESTAFF)) {

			if (!checkPrivilege(response, privilege)) {
				return;
			}

			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			data = LogonBusinessModel.setRequestData(request);

			String returnValue = LogonBusinessModel.adminUpdateStaff(data, privilege, locale);

			out.print(returnValue);
			out.flush();
			out.close();

		}
		

		else if (actionType.equals(AJAXSEARCHMEMBER) == true) {

			String searchUserId = request.getParameter("userId") == null ? "" : new String(request.getParameter("userId").trim().getBytes("ISO-8859-1"), "UTF-8");
			String result = "";

			if (searchUserId.trim().equals("")) {
				result = "{\"error\": \"noUserId\"}";
				out.print(result);
				out.flush();

				return;

			} else {

				ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
				data = LogonBusinessModel.searchMember(searchUserId);

				if (data != null && !data.isEmpty()) {
					LogonDataModel logonData = new LogonDataModel();
					logonData = data.get(0);

					String gender = "";
					if (logonData.getGender() == 1) {
						gender = Resource.getString("ID_LABEL_MALE", locale);
					} else if (logonData.getGender() == 2) {
						gender = Resource.getString("ID_LABEL_FEMALE", locale);
					}

					result = "{\"userId\": \"" + logonData.getUserId() + "\", "
							+ "\"email\": \"" + logonData.getEmail() + "\", "
							+ "\"ename\": \"" + logonData.getEname() + "\", "
							+ "\"cname\": \"" + logonData.getCname() + "\", "
							+ "\"gender\": \"" + gender + "\", "
							+ "\"nationality\": \""
							+ logonData.getNationality() + "\", "
							+ "\"countryCode\": \""
							+ logonData.getCountryCode() + "\", "
							+ "\"phone\": \"" + logonData.getPhone() + "\", "
							+ "\"dob\": \"" + logonData.getDob() + "\", "
							+ "\"IC\": \"" + logonData.getIC() + "\", "
							+ "\"createDT\": \"" + logonData.getCreateDT() + "\", "
							+ "\"registerIP\": \""
							+ logonData.getRegisterIP() + "\", "
							+ "\"lastIP\": \"" + logonData.getLastIP() + "\", "
							+ "\"lastLoginDT\": \""
							+ logonData.getLastLoginDT() + "\", "
							+ "\"loginTimes\": \"" + logonData.getLoginTimes()
							+ "\", " + "\"error\": \"success\", "
							+ "\"totalBooking\": \""
							+ logonData.getTotalBooking() + "\"}";

					out.print(result);
					out.flush();
					return;

				} else {
					result = "{\"error\": \"noData\"}";
					out.print(result);
					out.flush();
					return;
				}
			}
		}

		else if (actionType.equals(AJAXSEARCHPARTNER) == true) {

			// String userId = request.getParameter("userId") == null ? "" : new
			// String(request.getParameter("userId").trim().getBytes("ISO-8859-1"),
			// "UTF-8");
			int pid = Integer.parseInt(request.getParameter("pid") == null ? "0" : request.getParameter("pid").toString());
			String result = "";

			if (pid == 0) {
				result = "{\"error\": \"noUserId\"}";
				out.print(result);
				out.flush();

				return;

			} else {

				ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
				data = LogonBusinessModel.searchPartnerByPid(pid);

				if (data != null && !data.isEmpty()) {
					LogonDataModel logonData = new LogonDataModel();
					logonData = data.get(0);

					result = "{\"userId\": \"" + logonData.getUserId() + "\", "
							+ "\"email\": \"" + logonData.getEmail() + "\", "
							+ "\"ename\": \"" + logonData.getEname() + "\", "
							+ "\"cname\": \"" + logonData.getCname() + "\", "
							+ "\"website\": \"" + logonData.getWebsite()
							+ "\", " + "\"officialEmail\": \""
							+ logonData.getOfficialEmail() + "\", "
							+ "\"contactPerson\": \""
							+ logonData.getContactPerson() + "\", "
							+ "\"phone\": \"" + logonData.getPhone() + "\", "
							+ "\"address\": \"" + logonData.getAddress()
							+ "\", " + "\"companyLicense\": \""
							+ logonData.getCompanyLicense() + "\", "
							+ "\"mocatLicense\": \""
							+ logonData.getMocatLicense() + "\", "
							+ "\"gst\": \"" + logonData.getGst() + "\", "
							+ "\"createDT\": \"" + logonData.getCreateDT()
							+ "\", " + "\"registerIP\": \""
							+ logonData.getRegisterIP() + "\", "
							+ "\"lastIP\": \"" + logonData.getLastIP() + "\", "
							+ "\"lastLoginDT\": \""
							+ logonData.getLastLoginDT() + "\", "
							+ "\"loginTimes\": \"" + logonData.getLoginTimes()
							+ "\", " + "\"error\": \"success\", "
							+ "\"totalBooking\": \""
							+ logonData.getTotalBooking() + "\"}";

					out.print(result);
					out.flush();
					return;

				} else {
					result = "{\"error\": \"noData\"}";
					out.print(result);
					out.flush();
					return;
				}
			}
		}

		else if (actionType.equals(CHANGEPASSWORD) == true) {

			String url = "";
			String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString();

			if (currentUser.isAuthenticated()) {
				ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
				userData = LogonBusinessModel.getMemberData(userId);
				
				if (userData.isEmpty()) { //代表不是會員，那麼有可能是 staff
					userData = LogonBusinessModel.getStaffData(userId);
					role = "staff";
				}

				if (userData != null && !userData.isEmpty()) {
					request.setAttribute(OBJECTDATA, userData);
					url = "./password.jsp?role="+role;
				} else { // 沒有資料
					LogonDataModel obj = new LogonDataModel();
					obj.setErrmsg("queryFailed");
					userData = new ArrayList<LogonDataModel>();
					// userData.set(0, obj); //加入錯誤訊息的標籤
					request.setAttribute(OBJECTDATA, userData);
					url = "./login?returnUrl=./password";
				}

			} else { // cookie失效
				url = "./login?returnUrl=./password";
				if(role.equals("staff")) {
					url = "./stafflogin?returnUrl=./password";
				}
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}

		else if (actionType.equals(UPDATEPASSWORD) == true) {

			String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString().trim();
			
			ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
			userData = LogonBusinessModel.setRequestData(request);

			int result = 0;
			String url = "";

			if (currentUser.isAuthenticated()) {
				logger.info("--- User is updating password: " + userId);
				if(role.equals("staff")) {
					result = LogonBusinessModel.updateStaffPassword(userData, userId);
				} else {
					result = LogonBusinessModel.updatePassword(userData, userId);
				}
			}

			url = "./password.jsp?role="+role;
			LogonDataModel obj = (LogonDataModel) userData.get(0);

			if (result == 0) { // 失敗
				obj.setErrmsg("updateFailed");
				logger.info("--- update profile Failed");
			} else if (result == 1) { // 成功
				obj.setErrmsg("updateSuccess");
				logger.info("--- update success");
			} else if (result == -1) { // 密碼不正確
				obj.setErrmsg("wrongPassword");
				logger.info("--- profile wrong Password");
			} else if (result == -2) { // 帳號或電郵已被使用
				obj.setErrmsg("userIdEmailExist");
				logger.info("--- profile userId / Email Exist");
			}

			userData.set(0, obj); // 加入錯誤訊息的標籤
			request.setAttribute(OBJECTDATA, userData);

			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;

		}

		else if (actionType.equals(APPUPDATEPASSWORD) == true) {

			Map<String, String> jsonObj = new LinkedHashMap<String, String>();
			ArrayList<LogonDataModel> userData = new ArrayList<LogonDataModel>();
			userData = LogonBusinessModel.setRequestData(request);

			LogonDataModel obj = (LogonDataModel) userData.get(0);// 拿回出來用

			logger.info("--- App user (" + obj.getUserId() + ") is updating password...");

			int result = 0;
			result = LogonBusinessModel.updateStaffPassword(userData, obj.getUserId());

			if (result == 0) { // 失敗

				jsonObj.put("result", "0");
				logger.info("--- update Password Failed");

			} else if (result == 1) { // 成功

				jsonObj.put("result", "1");
				logger.info("--- update Password Success");

			} else if (result == -1) { // 密碼不正確

				jsonObj.put("result", "-1");
				logger.info("--- wrong Password");

			} else if (result == -2) { // 帳號或電郵已被使用

				jsonObj.put("result", "-2");
				logger.info("--- userId / Email Exist");

			}

			String jsonText = JSONValue.toJSONString(jsonObj);
			out.print(jsonText);

		} else if(actionType.equals(FILTERPRIV) == true){ 
	    	
			int privFilter = Integer.parseInt(request.getParameter("privFilter") == null ? "-1" : request.getParameter("privFilter").equals("") ? "-1" :request.getParameter("privFilter").toString());
			
			if (privFilter == -1) {
	
				String url = "./cpMember";
				response.sendRedirect(url);
	
			} else {
	
				ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
				data = LogonBusinessModel.searchPrivMember(privFilter); 
	
				if (data != null && !data.isEmpty()) {
					request.setAttribute(OBJECTDATA, data);
				}
	
				String url = "./cp/cpMember.jsp?actionType=filterPriv&privFilter="+privFilter;
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
			}
		}
		
		else if(actionType.equals(EXPORTMEMBERLIST) == true){
	    	
	    	if(!checkPrivilege(response, privilege)){
	    		return;
	    	}
	    		
			int privFilter = Integer.parseInt(request.getParameter("privFilter") == null ? "-1" : request.getParameter("privFilter").toString());
			
			String result = "";
	    	String returnValue = "";
			ArrayList<LogonDataModel> data = new ArrayList<LogonDataModel>();
			
			if(privFilter > -2) {
		    	data = LogonBusinessModel.checkMemberList(privFilter);
			}
			
			if(data != null && !data.isEmpty()){
	        	returnValue = LogonBusinessModel.exportMemberList(request, data, "xlsx");
	        }

			if(returnValue.equals("OK")){ //成功
				result = "OK";
			} else { //失敗
				result = returnValue;
			}
	        
			out.print(result);
	    	
		}
	}

	// 檢查是否具備足夠權限使用本項目
	private boolean checkPrivilege(HttpServletResponse response, int privilege) throws IOException {
		int iid = 4; // 必須自行手動設定，對應DB的privilege
		String url = "./stafflogin?returnUrl=./cp";

		if (PrivilegeBusinessModel.determinePrivilege(iid, privilege)) {
			return true;
		} else {
			response.sendRedirect(url);
			return false;
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