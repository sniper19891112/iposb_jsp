package com.iposb.utils;

/**
 * 统计在线人数..sessionCreated中添加ip判断
 * https://hi.baidu.com/danteyo/blog/item/4e9493ec3180be38269791f8.html
 */

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionCounter implements HttpSessionListener,ServletRequestListener {
	
	private static final long serialVersionUID = 1L;
	
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static int activeSessions = 0;
    private HttpServletRequest REQUEST;
    private static ArrayList<String> list = new ArrayList<String>();
    
    static Logger logger = Logger.getLogger(SessionCounter.class);

    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
//        HttpSession session = request.getSession();
    }

    public void destroy() {
    }

    public void requestDestroyed(ServletRequestEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void requestInitialized(ServletRequestEvent sre){
    	REQUEST = (HttpServletRequest)sre.getServletRequest();
    }


    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        String loginIp = IPTool.getUserIP(REQUEST);
        boolean rs = true;
        if(list.size() > 0){
            for(int i = 0;i < list.size(); i ++){
                if(loginIp.equals(list.get(i))){
                    rs = false; //如果队列中存在相同的IP 则SESSION不增加
                }
            }
        }
        if(rs){ //沒有找到相同IP
            list.add(loginIp);
            String country = IPTool.countryLookup(REQUEST, "code");
//            logger.info("--- New Visitor: "+country+"  IP: " + loginIp +" ("+UtilsBusinessModel.timeNow()+"); Total SESSION: "+list.size());
            logger.info("--- New Visitor: "+country+"  IP: " + loginIp +" ("+UtilsBusinessModel.timeNow()+"); Accumulate Visitors: "+list.size());
        }
    }

    //TODO: 不怎麼有效
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

//        String loginIp = REQUEST.getRemoteAddr();
//        if(list.size() > 0){
//            for(int i = 0;i < list.size(); i ++){
//                if(loginIp.equals(list.get(i))){
//                    list.remove(i);
//                    logger.info("--- Destroy SESSION: IP = " + loginIp +" ("+UtilsBusinessModel.timeNow()+"); Total SESSION: "+list.size());
//                }
//            }
//        }
    }
    

    public static int getActiveSessions() {
        return list.size();
    }

    public void setActiveSessions(int i) {
        activeSessions = i;
    }

}