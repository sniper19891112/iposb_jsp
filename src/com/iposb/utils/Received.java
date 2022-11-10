package com.iposb.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class Received extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(Received.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException { 
		super.init(config); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		return;
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String trackType = request.getParameter("verify") == null ? "" : request.getParameter("verify");
		String bookingType = request.getParameter("bookingType") == null ? "" : request.getParameter("bookingType");
		String bookingCode = request.getParameter("bookingCode") == null ? "" : request.getParameter("bookingCode");
		String IP = IPTool.getUserIP(request);
		
		if(trackType.equals("ConfirmationEmail")) { //user read Reservation Receipt via email
		
			Tracking.update(trackType, bookingType, bookingCode, IP);

			try {
				
				String separator = System.getProperty("file.separator");
				String rootPath = System.getProperty("user.dir");
				String imgPath = rootPath + separator + "webapps" + separator + "ROOT" + separator + "etc" + separator;
				File myTifFile = new File(imgPath + "titlePoint.png");
			
//				logger.info("imgPath:"+imgPath);
				if(myTifFile.exists()){
					BufferedImage bufi = ImageIO.read(myTifFile);
					response.setContentType("image/png");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufi, "png", os);
					os.close();
//					logger.info("Showing the img...");
				} else {
					logger.error("Cannot read input file: " + myTifFile);
				}
				
			} catch (Exception ex) {
				logger.error(ex.toString());
				ex.printStackTrace();
			}
			
		}
		 
	}
		 
		 
	public void destroy() {
	 
	}

}
