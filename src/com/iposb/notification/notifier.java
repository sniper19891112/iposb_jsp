package com.iposb.notification;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.iposb.partner.PartnerBusinessModel;

/**
 * Server-Send option :
 * https://www.w3schools.com/html/html5_serversentevents.asp
 * 
 * Java servelt for server-send :
 * https://stackoverflow.com/questions/6466713/java-servlet-and-server-sent-events
 * 
 * Tutorial :
 * https://peaktechie.blogspot.in/2012/04/small-tutorial-on-html5-server-sent.html
 * 
 * @author NetQuas
 *
 */


public class notifier extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        
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
    	
        response.setContentType("text/event-stream;charset=UTF-8");

        PrintWriter out = response.getWriter();
        
        int num = 0;
        
        if(privilege == 3) { //agent
    		int agentId = PartnerBusinessModel.getPidByIdPriv(userId, privilege);
    		num = NotificationBusinessModel.countAgentNotification(agentId);
		} else if (privilege > 5) { //not included driver
			num = NotificationBusinessModel.countNotification();
		}
        
//        if(num > 0) {
            out.print("event: newNotification\n\n");
            out.print("data: " + num + "\n\n");
//          System.out.println("Data Sent:" + num);
            out.flush();
//        }

        try {
            Thread.sleep(60000); //60 sec
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

}