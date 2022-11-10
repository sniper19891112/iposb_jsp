package com.iposb.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.iposb.i18n.Resource;
import com.iposb.resource.ResourceBusinessModel;
import com.iposb.sys.ConnectionManager;

public class Mailer {

	private static String SMTPSERVER = "";
	private static String SMTPPORT = "";
	private static String FROM = "";                                                                       
	private static String AUTHPASSWORD1 = "";
	private static String AUTHPASSWORD2 = "";
	private static String SITENAME = "";
	private static Properties PROPS = new Properties();
	
	static Logger logger = Logger.getLogger(UtilsBusinessModel.class);
	
	
	private Mailer(){	    
	}
	
	
	/**
	 * 發送通知email給管理員
	 * @return
	 */
	public static String sendEmailToAdmin(String title, String toEmail){

		String Subject = title;
		String result = "";
		
		try {

	        String content = "";
	        
	        //內容
	        content = "Email notification about: " + title;
	        result = sendEmail(Subject, "noreply@iposb.com", toEmail, content, null); //發送email
	        
		}
		catch (Exception ex){
			ex.toString();
			logger.error(ex.toString());
			result = "error";
		}
		return result;
	}
	
	
	
	
	/**
	 * 發送包裹已寄達的email給寄件者
	 * @return
	 */
	public static String sendParcelReachedEmail(String consignmentNo, String toEmail, String locale, HttpServletRequest request){

		String Subject = Resource.getString("ID_LABEL_PARCELREACHED", consignmentNo, locale);
		String result = "";

		try {

	        String content = "";
	        
	        //內容
	        content = "Dear Sir/Madam, <br/><br/>Your shipment has reached the consignee safely. <br/>Please kindly refer attached for the proof of delivery for your perusal. <br/><br/>Thank you for choosing iPosb as your delivery partner.";
	        String remoteFile = ResourceBusinessModel.getStage7ConPics(consignmentNo);
//logger.info("remoteFile: "+remoteFile);	        
	        
	        //把檔案從遠端抓回來，放在 temp 裡
	        FileDataSource fds = null;
	        if(remoteFile != ""){
		        FileUtils.copyURLToFile(new URL(remoteFile), new File(request.getSession().getServletContext().getRealPath("/")+"temp/"+consignmentNo+"_delivered.jpg"));
		        
		        fds = new FileDataSource(request.getSession().getServletContext().getRealPath("/")+"temp/"+consignmentNo+"_delivered.jpg");
	        }
	        result = sendEmail(Subject, "noreply@iposb.com", toEmail, content, fds); //發送email
	        
	        logger.info("Sent Parcel Reached Email to sender: " + toEmail);
	        
		}
		catch (Exception ex){
			ex.toString();
			logger.error(ex.toString());
			result = "error";
		}
		return result;
	}
	
	
	
	/**
	 * 發送新密碼予會員
	 * @return
	 */
	public static String sendNewPasswordEmail(String pw, String toEmail, String locale){

		String Subject = Resource.getString("ID_LABEL_YOURNEWPASSWORD", locale);
		String result = "";

		try {

	        String content = "";
	        
	        //內容
	        content = Resource.getString("ID_LABEL_FORGOTPASSWORDTXTEMAIL", pw, locale);
	        result = sendEmail(Subject, "noreply@iposb.com", toEmail, content, null); //發送email
		}
		catch (Exception ex){
			ex.toString();
			logger.error(ex.toString());
			result = "error";
		}
		return result;
	}
	
	
	
	/**
	 * 產生新密碼
	 * @param n 密碼長度
	 * 參考自:https://bobcat.webappcabaret.net/javachina/jc/share/PwGen.htm
	 * @return
	 */
	public static String getPassword(int n) {
	    char[] pw = new char[n];
	    int c  = 'A';
	    int  r1 = 0;
	    for (int i = 0; i < n; i++){
	      r1 = (int)(Math.random() * 3);
	      switch(r1) {
	        case 0: c = '0' +  (int)(Math.random() * 10); break;
	        case 1: c = 'a' +  (int)(Math.random() * 26); break;
	        case 2: c = 'A' +  (int)(Math.random() * 26); break;
	      }
	      pw[i] = (char)c;
	    }
	    return new String(pw);
	}
	
	
	/**
	 * 發送email
	 * @param subject
	 * @param fromEmail
	 * @param toEmail
	 * @param content
	 * @param fds 預訂收據（附件）
	 * @return
	 */
	public static String sendEmail(String subject, String fromEmail, String toEmail, String content, FileDataSource fds){
		
		Thread.currentThread().setContextClassLoader(UtilsBusinessModel.class.getClassLoader());
		Properties prop = new Properties();
		
		try {

			prop.load(ConnectionManager.class.getClassLoader().getResourceAsStream("credentials.properties"));
			
			SMTPSERVER = prop.getProperty("smtp");
			SMTPPORT = prop.getProperty("port");
			FROM = prop.getProperty("from");                                                                       
			AUTHPASSWORD1 = prop.getProperty("pass1"); //info@iposb.com
			AUTHPASSWORD2 = prop.getProperty("pass2"); //noreply@iposb.com
			SITENAME = prop.getProperty("site");

			PROPS.clear();
            PROPS.put("mail.smtp.host", SMTPSERVER);
            PROPS.put("mail.smtp.port", SMTPPORT);   
	        PROPS.put("mail.smtp.auth", "true");
	        PROPS.put("mail.smtp.starttls.enable", "true");
	        PROPS.put("mail.transport.protocol", "smtp");
	        PROPS.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	        
	        // create some properties and get the default Session
	        final String AUTHADDRESS = fromEmail;
	        Session sessionMail = Session.getInstance(PROPS, new Authenticator() {
	             public PasswordAuthentication getPasswordAuthentication() {
	            	 String passwd = AUTHPASSWORD1;
	            	 if(AUTHADDRESS.equals("noreply@iposb.com") ){
	            		 passwd = AUTHPASSWORD2;
	            	 }
	                 return new PasswordAuthentication(AUTHADDRESS, passwd);
	             }
	            });                                                                  
	                                                                                 
//	        sessionMail.setDebug(true);
	
	        // create a message
	        Message msg = new MimeMessage(sessionMail);
	
	        // set the from and to address
	        InternetAddress addressFrom = new InternetAddress(fromEmail, FROM);
	        msg.setFrom(addressFrom);

	
	        InternetAddress[] addressTo = new InternetAddress[1];
	        addressTo[0] = new InternetAddress(toEmail);
	        msg.setRecipients(Message.RecipientType.TO, addressTo);
	
	        // Optional : You can also set your custom headers in the Email if you Want
	        msg.addHeader("site", SITENAME); //TODO: 沒用的
	
	        // Setting the Subject and Content Type
	        msg.setSubject(MimeUtility.encodeText(subject,"UTF-8","B")); //以便支援中文
	        
	        // 設定傳送信的MIME Type 為 html格式
	        Multipart mm = new MimeMultipart();
	        MimeBodyPart mbp = new MimeBodyPart();
	        MimeBodyPart mbp2 = new MimeBodyPart(); //create the second message part
	        mbp.setContent(content, "text/html;charset=UTF-8");
	        
	        if(fds != null){ //有 attachment
//logger.info(">>> add attachment <<<");
		        // attach the file to the message
		        mbp2.setDataHandler(new DataHandler(fds));
		        mbp2.setFileName(fds.getName());
	        }
	        
	        // create the Multipart and add its parts to it
	        mm.addBodyPart(mbp);
	        if(fds != null){ //有 attachment
//logger.info(">>> fds:" + fds);  	
	        	mm.addBodyPart(mbp2);
	        }
	        
	        msg.setContent(mm);
        
	        Transport.send(msg);
	        
	        return "OK";

		} 
		catch (IOException e) {
			logger.error("### IOException: "+e);
		    e.printStackTrace();
		    return "error";
		}
		catch (MessagingException mex) {				
			logger.error(mex.toString());
		    mex.printStackTrace();
			return "error";
		}
		catch (Exception ex){
			logger.error(ex.toString());
			ex.toString();
			return "error";
		}


	}
	
	
	public static String sendVerifyEmail(String toEmail, String verify){

		String Subject = "Confirm your iPosb account";
		String result = "";

		try {

	        String content = "";
	        
	        //內容
	        content = "Dear Sir/Madam, <br/><br/>Thank you for registering at iPosb. <br/>Please click on the following link to complete your registration. <br/><br/> <a href='https://www.iposb.com/activateAccount&email="+ toEmail +"&verify="+ verify +"' target='_blank'>https://www.iposb.com/activateAccount?email="+ toEmail +"&verify="+ verify +"<a/> <br/><br/>Thank you for choosing iPosb as your delivery partner.";
//			logger.info("remoteFile: "+remoteFile);	        
	        
	        result = sendEmail(Subject, "noreply@iposb.com", toEmail, content, null); //發送email
	        
	        logger.info("Sent Verification Email to User: " + toEmail);
	        
		}
		catch (Exception ex){
			ex.toString();
			logger.error(ex.toString());
			result = "error";
		}
		return result;
	}
	
	public static String sendVoucherEmail(String ename, String toEmail, String voucherCode){

		String Subject = "Birthday Special Discount!";
		String result = "";

		try {

	        String content = "";
	        
	        //內容
	        content = "Happy Birthday "+ ename +"! <br/><br/> This is your RM10 Birthday Discount Code: <br/><br/><strong style=\"font-size:large;\">"+voucherCode+"</strong>";
//			logger.info("remoteFile: "+remoteFile);	        
	        
	        result = sendEmail(Subject, "noreply@iposb.com", toEmail, content, null); //發送email
	        
		}
		catch (Exception ex){
			ex.toString();
			logger.error(ex.toString());
			result = "error";
		}
		return result;
	}
	
	
	
}
