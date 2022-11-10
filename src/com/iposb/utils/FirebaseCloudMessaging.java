package com.iposb.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class FirebaseCloudMessaging implements ServletContextListener {
	
	static Logger logger = Logger.getLogger(FirebaseCloudMessaging.class);
	
	public void service(ServletRequest request, ServletResponse response)throws ServletException, IOException {

	}
    public void contextInitialized(ServletContextEvent event){
    	
		try {
	    	//FileInputStream serviceAccount = new FileInputStream("D:/iposb-serviceAccountKey.json");
	    	InputStream serviceAccount = FirebaseCloudMessaging.class.getClassLoader().getResourceAsStream("iposb-logistics-firebase-adminsdk-69xvx-c2a74a1b41.json");
	    	
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://iposb-logistics.firebaseio.com")
					.build();

			FirebaseApp.initializeApp(options);
			//logger.info(FirebaseApp.getInstance());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
        
    }
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("--- FirebaseCloudMessaging Context Destroyed ---");
	}
	
	
	/**
	 * Send Push Notification to single device
	 * 
	 * @param title (notification message)
	 * @param body (notification sub-message)
	 * @param token
	 * 
	 * @return boolean isSuccess
	 */
	public static boolean notifyIndividual(String title, String body, String token){
		boolean isSuccess = false;
		
		if(!token.equals("")){
			// This registration token comes from the client FCM SDKs.
			String registrationToken = token;
			
			Notification notify = new Notification(title,body);
			
			// See documentation on defining a message payload.
			Message message = Message.builder()
				.setNotification(notify)
			    .setToken(registrationToken)
			    .build();
			
			// Send a message to the device corresponding to the provided
			// registration token.
			String response = "";
			try {
				response = FirebaseMessaging.getInstance().sendAsync(message).get();
				
				// Response is a message ID string.
				logger.info("Successfully sent message: " + response);
				isSuccess = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
				response = e.toString();
				logger.error(response);
				isSuccess = false;
			} catch (ExecutionException e) {
				e.printStackTrace();
				response = e.toString();
				logger.error(response);
				isSuccess = false;
			}
		}
		
		return isSuccess;
	}
}
