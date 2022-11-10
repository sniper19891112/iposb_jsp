package com.iposb.sys;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/* 
 * 參考自：　https://www.vaannila.com/log4j/log4j-tutorial/log4j-tutorial.html
 * 		　　https://puremonkey2010.blogspot.com/2010/08/java-apache-log4j-log.html
 * 		  https://logging.apache.org/log4j/1.2/manual.html
 */

public class Log4jInit extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(Log4jInit.class);

	public void init() {
		PropertyConfigurator.configure(getServletContext().getRealPath("/")+"WEB-INF/classes/log4j.properties");
		logger.info(".... log4j.properties loaded ...");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	}
}
