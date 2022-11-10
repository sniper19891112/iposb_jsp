<%@page import="java.util.Date, com.iposb.i18n.*, com.iposb.utils.*, com.iposb.logon.LogonBusinessModel, org.apache.shiro.SecurityUtils, org.apache.shiro.subject.*, org.apache.shiro.session.*, org.apache.shiro.authc.UsernamePasswordToken" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
	String locale = "en_US";
	int id = 0; // mid/pid/sid
	String userId = "";
	String userName = "";
	int priv = -9;
	//boolean login = false;
	String resPath = "https://static.iposb.com"; //Amazon S3 + CloudFront
	String avatarPath = ""; // for profile picture's path
	String avatarDT = "";
	
//	Subject currentUser = SecurityUtils.getSubject();
//	if ( currentUser.isAuthenticated() || currentUser.isRemembered() ) {
//
//		userId = currentUser.getPrincipal().toString();
//		// USERNAME
//		if(currentUser.getSession().getAttribute("username") != null && !currentUser.getSession().getAttribute("username").toString().trim().equals("")){
//			userName = currentUser.getSession().getAttribute("username").toString();
//		}else {
//			currentUser.getSession().setAttribute("username", LogonBusinessModel.getDataById("ename", "", userId));
//			userName = currentUser.getSession().getAttribute("username").toString();
//		}
//		// MID/PID/SID
//		if(currentUser.getSession().getAttribute("id") != null && !currentUser.getSession().getAttribute("id").toString().trim().equals("")){
//			id = Integer.parseInt(currentUser.getSession().getAttribute("id").toString());
//		}else {
//			currentUser.getSession().setAttribute("id", LogonBusinessModel.getDataById("id", "", userId));
//			id = Integer.parseInt(currentUser.getSession().getAttribute("id").equals("") ? "0" : currentUser.getSession().getAttribute("id").toString());
//		}
//		// ModifyDT
//		if(currentUser.getSession().getAttribute("modifyDT") != null && !currentUser.getSession().getAttribute("modifyDT").toString().trim().equals("")){
//			avatarDT = currentUser.getSession().getAttribute("modifyDT").toString();
//		}else {
//			currentUser.getSession().setAttribute("modifyDT",
//					!LogonBusinessModel.getDataById("modifyDT", "", userId).equals("") ?
//							LogonBusinessModel.getDataById("modifyDT", "", userId).trim().replace(" ", "_") : ""
//					);
//			avatarDT = currentUser.getSession().getAttribute("modifyDT").toString().trim();
//		}
//
//
//		if (currentUser.hasRole("99")) {
//			priv = 99;
//		} else if (currentUser.hasRole("9")) {
//			priv = 9;
//		} else if (currentUser.hasRole("8")) {
//			priv = 8;
//		} else if (currentUser.hasRole("7")) {
//			priv = 7;
//		} else if (currentUser.hasRole("6")) {
//			priv = 6;
//		} else if (currentUser.hasRole("5")) {
//			priv = 5;
//		} else if (currentUser.hasRole("4")) {
//			priv = 4;
//		} else if (currentUser.hasRole("3")) {
//			priv = 3;
//		} else if (currentUser.hasRole("2")) {
//			priv = 2;
//		} else if (currentUser.hasRole("1")) {
//			priv = 1;
//		} else if (currentUser.hasRole("0")) {
//			priv = 0;
//		}
//
//		if( priv >=0 && priv <3 ){ avatarPath = "member"; }
//		else if( priv >=3 && priv < 5 ){ avatarPath = "partner"; }
//		else if( (priv >=5 && priv < 10) || priv==99){ avatarPath = "staff"; }
//	}
	
	String lang = request.getParameter("lang");
	boolean foundCookie = false;
	
	if (lang == null || lang == ""){ //非user自己切換語系
		Cookie[] cookies = request.getCookies();
		if(cookies != null){ //有cookie
	    	for (int i = 0; i < cookies.length; i++) {
	    		Cookie cookie1 = cookies[i];
	      		if (cookie1.getName().equals("lang")) {
	                locale = cookie1.getValue();
	                foundCookie = true;
	            }
	    	}
		}
		
		if (!foundCookie) { //沒有cookie
//			String countryCode = IPTool.countryLookup(request, "code");
			String countryCode = "CN";
			if( countryCode.equals("TW") || countryCode.equals("HK") || countryCode.equals("MO") ){
				locale = "zh_TW";
			} else if(countryCode.equals("CN")){
				locale = "zh_CN";
			} else {
				locale = "en_US";
			}
			Cookie cookie = new Cookie("lang", locale);
			response.addCookie(cookie); //加入cookie
			cookie = new Cookie("country", countryCode);
	    	response.addCookie(cookie); //加入cookie
		}
	} else { //user自己切換語系
		Cookie cookie = new Cookie("lang", lang);
		response.addCookie(cookie);
		locale = lang;
	}
	
	
	
	String countryCode = ""; //MY, CN, HK, ...
	Cookie[] cookies = request.getCookies();
	if(cookies != null){ //有cookie
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie1 = cookies[i];
			if (cookie1.getName().equals("country")) {
				countryCode = cookie1.getValue();
			}
		}
	}

	
%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->  
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->  
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->  