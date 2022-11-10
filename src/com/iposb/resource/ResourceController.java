package com.iposb.resource;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ResourceController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	// Initialize global variables
	public static final String OBJECTDATA = "objectData";
	public static final String GETCONPICS = "getConPics";
	public static final String GETCONDO = "getConDO";
	public static final String GETANIMAGE = "getAnImage";
	public static final String CHECKCONSIGNMENTNOTE = "checkConsignmentnote";
	public static final String CHECKINVOICE = "checkInvoice";
	// public static final String CHECKCONPICS = "checkConPics";

	static Logger logger = Logger.getLogger(ResourceController.class);

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

		PrintWriter out = response.getWriter();
		String actionType = request.getParameter("actionType");

		if (actionType == null) {
			return;
		}

		else if (actionType.equals(GETCONPICS) == true) {

			String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();

			out.print(ResourceBusinessModel.getConPics(consignmentNo));

		}
		
		else if (actionType.equals(GETCONDO) == true) {

			String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();

			out.print(ResourceBusinessModel.getConDO(consignmentNo));

		}

		else if (actionType.equals(GETANIMAGE) == true) {

			String path = request.getParameter("path") == null ? "" : request.getParameter("path").toString();

			out.print(ResourceBusinessModel.getAnImage(path));
		}

		else if (actionType.equals(CHECKCONSIGNMENTNOTE) == true) {

			String consignmentNoStream = request.getParameter("consignmentNoStream") == null ? "" : request.getParameter("consignmentNoStream").toString();

			out.print(ResourceBusinessModel.checkConsignmentnote(consignmentNoStream));

		}

		else if (actionType.equals(CHECKINVOICE) == true) {

			String invoiceNoconsignmentNoStream = request.getParameter("invoiceNoconsignmentNoStream") == null ? "" : request.getParameter("invoiceNoconsignmentNoStream").toString();

			out.print(ResourceBusinessModel.checkInvoice(invoiceNoconsignmentNoStream));

		}

		// else if(actionType.equals(CHECKCONPICS) == true){
		//
		// String consignmentNo = request.getParameter("consignmentNo") == null
		// ? "" : request.getParameter("consignmentNo").toString();
		//
		// out.print(ResourceBusinessModel.checkConPics(consignmentNo));
		//
		// }

	}

	// Clean up resources
	public void destroy() {
	}
}