package com.iposb.sys;

/**
 * 為了解決 Exception in thread "HouseKeeper" java.lang.NullPointerException
 * 參考自：https://macleo.iteye.com/blog/1044368
 */

import java.io.File;
import java.io.IOException;  

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.apache.log4j.Logger; 
  
public class LoadServlet extends HttpServlet {
	/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		static Logger logger = Logger.getLogger(LoadServlet.class);

		public void init() throws ServletException {   
			
			File theDir = new File(getServletContext().getRealPath("/temp"));

			// if the directory does not exist, create it
			if (!theDir.exists()) {
				logger.info ("creating directory: " + theDir);
			    boolean result = theDir.mkdir();  
			    if(result){
			    	theDir.setWritable(true);
			    	theDir.setExecutable(true);
			    	theDir.setReadable(true);
			    	logger.info ("'" + theDir + "' created.");
			    }
			} else {
				logger.info ("Directory exists: " + theDir);
//				logger.info ("Is Execute allow : " + theDir.canExecute());
//				logger.info ("Is Write allow : " + theDir.canWrite());
//				logger.info ("Is Read allow : " + theDir.canRead());
			}
			
		}
		
		public void destroy() {   
			//此处添加处理
//			ProxoolFacade.shutdown(10);   
		}  
  
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws   
        	ServletException, IOException {   
			doGet(request,response);   
		}   
  
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws   
    		ServletException, IOException {   
           
		}  
		


}