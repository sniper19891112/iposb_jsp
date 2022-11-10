package com.iposb.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.iposb.code.CodeBusinessModel;
import com.iposb.logon.LogonBusinessModel;
import com.iposb.logon.LogonDataModel;


public class TimerMachine implements ServletContextListener {

	static Logger logger = Logger.getLogger(TimerMachine.class);
	private static int EXECUTETIME = 0;
	
	
	public void service(ServletRequest request, ServletResponse response)throws ServletException, IOException {

	}
	public void contextInitialized(ServletContextEvent event) {
		
		BasicConfigurator.configure();
		
		Properties prop = new Properties();
        try {
			prop.load(TimerMachine.class.getClassLoader().getResourceAsStream("credentials.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        String environment = prop.getProperty("environment");
		
		Timer timer = new Timer();
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
		Calendar calendar = Calendar.getInstance();  
		
		logger.info("###### TimerMachine Started @ \"" + calendar.getTime() + " ######");
		
		if(environment.equals("production")) {
			
		 	// Check if new year or not 
			calendar.set(Calendar.HOUR_OF_DAY, 0);// 12:00:00 A.M.
			calendar.set(Calendar.MINUTE, 0);  
			calendar.set(Calendar.SECOND, 0);  
			logger.info("---TimerMachine resetCodeSent(). Start Time = \"" + calendar.getTime() + "\" ---");
		 	timer.scheduleAtFixedRate(new resetCodeSent(), calendar.getTime(), 24 * 3600 * 1000); // 24 hours between calls
			
		 	// Send Birthday Code
			calendar.set(Calendar.HOUR_OF_DAY, 7);
			calendar.set(Calendar.MINUTE, 30);  
			calendar.set(Calendar.SECOND, 0);  
			logger.info("---TimerMachine sendBirthdayCode(). Start Time = \"" + calendar.getTime() + "\" ---");
		 	timer.scheduleAtFixedRate(new sendBirthdayCode(), calendar.getTime(), 24 * 3600 * 1000); // 24 hours between calls
		 	

		 	EXECUTETIME++;
		}
		
	 	
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("--- TimerMachine Context Destroyed ---");
	}
	
	private class sendBirthdayCode extends TimerTask {
		public void run() {
				
			ArrayList<LogonDataModel> birthdayUsers = new ArrayList<LogonDataModel>();
			birthdayUsers = LogonBusinessModel.checkBirthday();
			
			if(birthdayUsers != null && !birthdayUsers.isEmpty()){
				CodeBusinessModel.sendVoucher(birthdayUsers);
			}
			//else logger.info("No one's birthday today");
		}
	}
	
	private class resetCodeSent extends TimerTask {
		public void run() {

			TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
			Calendar today = Calendar.getInstance();

			//logger.info("day of the year: "+ today.get(Calendar.DAY_OF_YEAR));
			
			if(today.get(Calendar.DAY_OF_YEAR) == 1){ // First day of year
				CodeBusinessModel.resetCodeSent();
			}
		}
	}
	
	
	
}
