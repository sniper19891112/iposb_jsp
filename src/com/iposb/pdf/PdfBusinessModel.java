package com.iposb.pdf;


import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.iposb.account.AccountDataModel;
import com.iposb.area.AreaBusinessModel;
import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.consignment.ConsignmentDataModel;
import com.iposb.i18n.Resource;
import com.iposb.resource.ResourceBusinessModel;
import com.iposb.sys.ConnectionManager;
import com.iposb.utils.UtilsBusinessModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfBusinessModel extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static Document DOCUMENT = null;
	private static BaseFont BF_HELV = null;
	private static BaseFont BF_HELVBOLD = null;
	private static BaseFont BF_HELVOBLIQUE = null;
	private static BaseFont BF_HELVBOLDOBLIQUE = null;
	private static BaseColor BORDERCOLOR = BaseColor.GRAY; //sets gray border

	
	static Logger logger = Logger.getLogger(PdfBusinessModel.class);
	
	static NumberFormat formatter = new DecimalFormat("#,###,###.##");
	
	/**
	 * 產生 Consignment Note
	 * 
	 * @param request
	 * @param consignmentData
	 * @return
	 */
	public static String generateConsignmentNote (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData) {
	    
		String result = "generated success";
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);
	    		
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_consignmentNote.pdf";
	    	String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/"+data.getConsignmentNo()+"-IPOSB.pdf";

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			//pre-data
			
			String creditAreaNM = "";
	        if(data.getCreditArea() > 0) {
	        	creditAreaNM = AreaBusinessModel.parseAreaName(data.getCreditArea());
	        }
	        
			boolean tick1 = false;
			boolean tick2 = false;
			boolean tick3 = false;
			boolean tick4 = false;
			Image tick1img = Image.getInstance(request.getSession().getServletContext().getRealPath("/")+"etc/tick1.png");
	        Image tick2img = Image.getInstance(request.getSession().getServletContext().getRealPath("/")+"etc/tick2.png");
	        Image tick3img = Image.getInstance(request.getSession().getServletContext().getRealPath("/")+"etc/tick3.png");
	        Image tick4img = Image.getInstance(request.getSession().getServletContext().getRealPath("/")+"etc/tick4.png");
			
			String tickvalue = data.getTickItem();
			if(!tickvalue.equals("") && tickvalue != null){
				String tickSplit[] = tickvalue.split("\\,");
				for(int x = 0; x < tickSplit.length; x++){
					String tmp = tickSplit[x];
					
					if(tmp.equals("tick1")) {
						tick1 = true;
					}
					if(tmp.equals("tick2")) {
						tick2 = true;
					}
					if(tmp.equals("tick3")) {
						tick3 = true;
					}
					if(tmp.equals("tick4")) {
						tick4 = true;
					}
				}
			}
			
			String remark = "";
			if (data.getPayMethod()==5) { //COD
	        	remark += "C.O.D. - RM " + data.getAmount() + "\r\n";
	        }
	        if (data.getPayMethod()==6) { //Freight charge
	        	remark += "Freight Charges - RM " + data.getAmount() + "\r\n";
	        }
	        
	        if(data.getHelps().trim().length() > 0) {
	        	remark += data.getHelps().toString();
	        }
	        
	        String generateDT = UtilsBusinessModel.todayDate();
			
			// ----------------- We were here slip --------------
			PdfContentByte canvas = writer.getDirectContent();
			canvas.beginText();
			canvas.setFontAndSize(BF_HELV, 12);
			canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverName(), 83, 685, 0); //receiver
			canvas.setFontAndSize(BF_HELV, 9);
			canvas.showTextAligned(Element.ALIGN_LEFT, data.getConsignmentNo(), 123, 640, 0); //consignment No.
			if(data.getShipmentType()==1) { //Document
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 225, 617, 0);
	        } else if(data.getShipmentType()==2) { //parcel
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 179, 617, 0);
	        }
			
			canvas.endText();
			
			
			// ------------------ SLIP 4 ------------------
			canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 12);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getConsignmentNo(), 118, 538, 0);
	        
	        if(data.getPrivilege() == 2 && data.getAccNo() != ""){
	        	canvas.setFontAndSize(BF_HELVBOLD, 10);
	        	canvas.showTextAligned(Element.ALIGN_CENTER, data.getAccNo(), 217, 538, 0);
	        }
	        
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_CENTER, generateDT, 273, 546, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, creditAreaNM, 191, 538, 0);
	        
	        //sender
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderName(), 65, 525, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress1(), 65, 515, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress2(), 65, 505, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress3(), 65, 495, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 65, 485, 0);
	        //receiver
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverName(), 65, 474, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress1(), 65, 464, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress2(), 65, 454, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress3(), 65, 444, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 65, 434, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAttn(), 83, 424, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPhone(), 183, 424, 0);
	        
	        //tick item & remark
	        if(tick1) {
	        	tick1img.setAbsolutePosition(83f, 390f);
	            document.add(tick1img);
	        }
	        if(tick2) {
	        	tick2img.setAbsolutePosition(113f, 390f);
	            document.add(tick2img);
	        }
	        if(tick3) {
	        	tick3img.setAbsolutePosition(143f, 390f);
	            document.add(tick3img);
	        }
	        if(tick4) {
	        	tick4img.setAbsolutePosition(173f, 390f);
	            document.add(tick4img);
	        }
			

	        canvas.setFontAndSize(BF_HELV, 9);
	        canvas.showTextAligned(Element.ALIGN_LEFT, remark, 48, 378, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 9);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getStationRemark(), 48, 365, 0);
	        
	        //TYPE, PCS, KG, GM
	        canvas.setFontAndSize(BF_HELV, 7);
	        if(data.getShipmentType()==1) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 48, 348, 0);
	        } else if(data.getShipmentType()==2) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 48, 338, 0);
	        } else if(data.getShipmentType()==3) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 48, 328, 0);
	        }
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf(data.getQuantity()), 126, 338, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, (data.getWeight() > 0 ? String.valueOf(data.getWeight()) : ""), 176, 338, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "", 223, 338, 0);
	        
	        //Destination Code
	        String origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
	        String destination = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, origin + (data.getSenderZoneserial() > 0 ? " "+data.getSenderZoneserial() : ""), 99, 305, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, destination  + (data.getReceiverZoneserial() > 0 ? " "+data.getReceiverZoneserial() : ""), 195, 305, 0);
	        
	        canvas.endText();
	        
	        //barcode	        
	        Barcode128 code128 = new Barcode128();
	        code128.setCode(data.getConsignmentNo());
	        code128.setCodeType(Barcode128.CODE128);
	        code128.setFont(null); //使之不出現文字
	        Image code128Image = code128.createImageWithBarcode(cb, null, null);
	        code128Image.setAbsolutePosition(105, 300);
	        code128Image.scalePercent(90);
	        document.add(code128Image);

	        
	        // ------------------ SLIP 5 ------------------
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 12);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getConsignmentNo(), 118, 273, 0);
	        
	        if(data.getPrivilege() == 2 && data.getAccNo() != ""){
	        	canvas.setFontAndSize(BF_HELVBOLD, 10);
	        	canvas.showTextAligned(Element.ALIGN_CENTER, data.getAccNo(), 217, 273, 0);
	        }
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_CENTER, generateDT, 273, 280, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, creditAreaNM, 191, 273, 0);
	        
	        //sender
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderName(), 65, 260, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress1(), 65, 250, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress2(), 65, 240, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress3(), 65, 230, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 65, 220, 0);
	        //receiver
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverName(), 65, 207, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress1(), 65, 197, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress2(), 65, 187, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress3(), 65, 177, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 65, 167, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAttn(), 83, 152, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPhone(), 183, 152, 0);
	        
	        //tick item & special helps
	        if(tick1) {
	        	tick1img.setAbsolutePosition(83f, 118f);
	            document.add(tick1img);
	        }
	        if(tick2) {
	        	tick2img.setAbsolutePosition(113f, 118f);
	            document.add(tick2img);
	        }
	        if(tick3) {
	        	tick3img.setAbsolutePosition(143f, 118f);
	            document.add(tick3img);
	        }
	        if(tick4) {
	        	tick4img.setAbsolutePosition(173f, 118f);
	            document.add(tick4img);
	        }
			

	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, remark, 48, 106, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 9);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getStationRemark(), 48, 93, 0);
	        
	        //TYPE, PCS, KG, GM
	        canvas.setFontAndSize(BF_HELV, 7);
	        if(data.getShipmentType()==1) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 48, 75, 0);
	        } else if(data.getShipmentType()==2) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 48, 65, 0);
	        } else if(data.getShipmentType()==3) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 48, 55, 0);
	        }
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf(data.getQuantity()), 126, 67, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, (data.getWeight() > 0 ? String.valueOf(data.getWeight()) : ""), 176, 67, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "", 223, 68, 0);
	        
	        //Destination Code
	        origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
	        destination = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, origin + (data.getSenderZoneserial() > 0 ? " "+data.getSenderZoneserial() : ""), 99, 32, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, destination + (data.getReceiverZoneserial() > 0 ? " "+data.getReceiverZoneserial() : ""), 195, 32, 0);
	        
	        canvas.endText();
	        
	        //barcode	        
	        code128 = new Barcode128();
	        code128.setCode(data.getConsignmentNo());
	        code128.setCodeType(Barcode128.CODE128);
	        code128.setFont(null); //使之不出現文字
	        code128Image = code128.createImageWithBarcode(cb, null, null);
	        code128Image.setAbsolutePosition(105, 27);
	        code128Image.scalePercent(90);
	        document.add(code128Image);
	        
	        
	        // ------------------ SLIP 1 ------------------
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 12);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getConsignmentNo(), 408, 805, 0);
	        
	        if(data.getPrivilege() == 2 && data.getAccNo() != ""){
	        	canvas.setFontAndSize(BF_HELVBOLD, 10);
	        	canvas.showTextAligned(Element.ALIGN_CENTER, data.getAccNo(), 497, 805, 0);
	        }
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_CENTER, generateDT, 556, 815, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, creditAreaNM, 470, 805, 0);
	        //sender
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderName(), 355, 791, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress1(), 355, 781, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress2(), 355, 771, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress3(), 355, 761, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 355, 751, 0);
	        //receiver
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverName(), 355, 740, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress1(), 355, 730, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress2(), 355, 720, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress3(), 355, 710, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 355, 700, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAttn(), 373, 689, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPhone(), 473, 689, 0);
	        
	        
	        //tick item & special helps
	        if(tick1) {
	        	tick1img.setAbsolutePosition(373f, 655f);
	            document.add(tick1img);
	        }
	        if(tick2) {
	        	tick2img.setAbsolutePosition(403f, 655f);
	            document.add(tick2img);
	        }
	        if(tick3) {
	        	tick3img.setAbsolutePosition(433f, 655f);
	            document.add(tick3img);
	        }
	        if(tick4) {
	        	tick4img.setAbsolutePosition(463f, 655f);
	            document.add(tick4img);
	        }
			

	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, remark, 333, 643, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 9);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getStationRemark(), 333, 630, 0);
	        
	        
	        //TYPE, PCS, KG, GM
	        canvas.setFontAndSize(BF_HELV, 7);
	        if(data.getShipmentType()==1) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 613, 0);
	        } else if(data.getShipmentType()==2) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 603, 0);
	        } else if(data.getShipmentType()==3) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 593, 0);
	        }
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf(data.getQuantity()), 413, 603, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, (data.getWeight() > 0 ? String.valueOf(data.getWeight()) : ""), 460, 603, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "", 505, 603, 0);
	        
	        //Destination Code
	        origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
	        destination = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, origin + (data.getSenderZoneserial() > 0 ? " "+data.getSenderZoneserial() : ""), 389, 570, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, destination + (data.getReceiverZoneserial() > 0 ? " "+data.getReceiverZoneserial() : ""), 485, 570, 0);
	        
	        canvas.endText();
	        
	        //barcode	        
	        code128 = new Barcode128();
	        code128.setCode(data.getConsignmentNo());
	        code128.setCodeType(Barcode128.CODE128);
	        code128.setFont(null); //使之不出現文字
	        code128Image = code128.createImageWithBarcode(cb, null, null);
	        code128Image.setAbsolutePosition(395, 565);
	        code128Image.scalePercent(90);
	        document.add(code128Image);
	        
	        
	        // ------------------ SLIP 2 ------------------
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 12);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getConsignmentNo(), 408, 538, 0);
	        
	        if(data.getPrivilege() == 2 && data.getAccNo() != ""){
	        	canvas.setFontAndSize(BF_HELVBOLD, 10);
	        	canvas.showTextAligned(Element.ALIGN_CENTER, data.getAccNo(), 497, 538, 0);
	        }
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_CENTER, generateDT, 556, 546, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, creditAreaNM, 470, 538, 0);
	        //sender
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderName(), 355, 525, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress1(), 355, 515, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress2(), 355, 505, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress3(), 355, 495, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 355, 485, 0);
	        //receiver
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverName(), 355, 474, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress1(), 355, 464, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress2(), 355, 454, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress3(), 355, 444, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 355, 434, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAttn(), 373, 424, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPhone(), 473, 424, 0);
	        
	        
	        //tick item & special helps
	        if(tick1) {
	        	tick1img.setAbsolutePosition(373f, 390f);
	            document.add(tick1img);
	        }
	        if(tick2) {
	        	tick2img.setAbsolutePosition(403f, 390f);
	            document.add(tick2img);
	        }
	        if(tick3) {
	        	tick3img.setAbsolutePosition(433f, 390f);
	            document.add(tick3img);
	        }
	        if(tick4) {
	        	tick4img.setAbsolutePosition(463f, 390f);
	            document.add(tick4img);
	        }
			
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, remark, 333, 378, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 9);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getStationRemark(), 333, 365, 0);
	        
	        
	        //TYPE, PCS, KG, GM
	        canvas.setFontAndSize(BF_HELV, 7);
	        if(data.getShipmentType()==1) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 348, 0);
	        } else if(data.getShipmentType()==2) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 338, 0);
	        } else if(data.getShipmentType()==3) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 328, 0);
	        }
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf(data.getQuantity()), 413, 338, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, (data.getWeight() > 0 ? String.valueOf(data.getWeight()) : ""), 460, 338, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "", 505, 338, 0);
	        
	        //Destination Code
	        origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
	        destination = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, origin + (data.getSenderZoneserial() > 0 ? " "+data.getSenderZoneserial() : ""), 389, 305, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, destination + (data.getReceiverZoneserial() > 0 ? " "+data.getReceiverZoneserial() : ""), 485, 305, 0);
	        
	        canvas.endText();
	        
	        //barcode	        
	        code128 = new Barcode128();
	        code128.setCode(data.getConsignmentNo());
	        code128.setCodeType(Barcode128.CODE128);
	        code128.setFont(null); //使之不出現文字
	        code128Image = code128.createImageWithBarcode(cb, null, null);
	        code128Image.setAbsolutePosition(395, 300);
	        code128Image.scalePercent(90);
	        document.add(code128Image);
	        
	        
	        // ------------------ SLIP 3 ------------------
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 12);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getConsignmentNo(), 408, 273, 0);

	        if(data.getPrivilege() == 2 && data.getAccNo() != ""){
	        	canvas.setFontAndSize(BF_HELVBOLD, 10);
	        	canvas.showTextAligned(Element.ALIGN_CENTER, data.getAccNo(), 497, 273, 0);
	        }
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_CENTER, generateDT, 556, 280, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, creditAreaNM, 470, 273, 0);
	        //sender
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderName(), 355, 260, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress1(), 355, 250, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress2(), 355, 240, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAddress3(), 355, 230, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 355, 220, 0);
	        //receiver
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverName(), 355, 207, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress1(), 355, 197, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress2(), 355, 187, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAddress3(), 355, 177, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPostcode(), 355, 167, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAttn(), 373, 152, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverPhone(), 473, 152, 0);
	        
	        //tick item & special helps
	        if(tick1) {
	        	tick1img.setAbsolutePosition(373f, 118f);
	            document.add(tick1img);
	        }
	        if(tick2) {
	        	tick2img.setAbsolutePosition(403f, 118f);
	            document.add(tick2img);
	        }
	        if(tick3) {
	        	tick3img.setAbsolutePosition(433f, 118f);
	            document.add(tick3img);
	        }
	        if(tick4) {
	        	tick4img.setAbsolutePosition(463f, 118f);
	            document.add(tick4img);
	        }
			
	        
	        canvas.setFontAndSize(BF_HELV, 8);
	        canvas.showTextAligned(Element.ALIGN_LEFT, remark, 333, 106, 0);
	        
	        canvas.setFontAndSize(BF_HELV, 9);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getStationRemark(), 333, 93, 0);
	        
	        //TYPE, PCS, KG, GM
	        canvas.setFontAndSize(BF_HELV, 7);
	        if(data.getShipmentType()==1) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 75, 0);
	        } else if(data.getShipmentType()==2) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 65, 0);
	        } else if(data.getShipmentType()==3) {
	        	canvas.showTextAligned(Element.ALIGN_LEFT, "X", 333, 55, 0);
	        }
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf(data.getQuantity()), 413, 67, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, (data.getWeight() > 0 ? String.valueOf(data.getWeight()) : ""), 460, 67, 0);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "", 505, 68, 0);
	        
	        //Destination Code
	        origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
	        destination = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, origin + (data.getSenderZoneserial() > 0 ? " "+data.getSenderZoneserial() : ""), 389, 32, 0);
	        canvas.showTextAligned(Element.ALIGN_LEFT, destination + (data.getReceiverZoneserial() > 0 ? " "+data.getReceiverZoneserial() : ""), 485, 32, 0);
	        
	        canvas.endText();
	        
	        //barcode	        
	        code128 = new Barcode128();
	        code128.setCode(data.getConsignmentNo());
	        code128.setCodeType(Barcode128.CODE128);
	        code128.setFont(null); //使之不出現文字
	        code128Image = code128.createImageWithBarcode(cb, null, null);
	        code128Image.setAbsolutePosition(395, 27);
	        code128Image.scalePercent(90);
	        document.add(code128Image);
	        
	        

	        document.close();
			document = null;
			
			
			
			//upload to Amazon S3
			String targetDir = "pdf";
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}

			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
	
	/**
	 * 產生 Consignment Note 的貼紙
	 * 
	 * @param request
	 * @param consignmentData
	 * @return
	 */
	protected static String generateSticker (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData) {
	    
		String result = "generated success";
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);

	    	String path = request.getSession().getServletContext().getRealPath("/")+"temp/"+data.getConsignmentNo()+"-sticker.pdf";

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
	    	boolean success = false;
			if(path != null && path.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(path)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-sticker.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-sticker.pdf");
			}

			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			
			int BARCODE_X = 85;
			int BARCODE_Y = 750;
			
			for(int i = 1; i <= data.getQuantity(); i++) { //根據quantity數量來產生同數量的sticker
				
				
				PdfContentByte canvas = writer.getDirectContent();
				canvas.beginText();
				
		        //Destination Code
		        String origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
		        String destination = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
		        canvas.setFontAndSize(BF_HELVBOLD, 11);
		        canvas.showTextAligned(Element.ALIGN_CENTER, origin + (data.getSenderZoneserial() > 0 ? " "+data.getSenderZoneserial() : ""), BARCODE_X - 8, BARCODE_Y + 20, 90);
		        canvas.showTextAligned(Element.ALIGN_CENTER, destination + (data.getReceiverZoneserial() > 0 ? " "+data.getReceiverZoneserial() : ""), BARCODE_X + 168, BARCODE_Y + 20, 90);
		        
		        canvas.endText();
		        
		        //barcode
		        Barcode128 code128 = new Barcode128();
		        code128.setCode(data.getConsignmentNo() + " " + lpad(3, i));
		        code128.setCodeType(Barcode128.CODE128);
		        Image code128Image = code128.createImageWithBarcode(cb, null, null);
		        code128Image.setAbsolutePosition(BARCODE_X, BARCODE_Y);
		        code128Image.scalePercent(115);
		        document.add(code128Image);
		        
		        BARCODE_Y -= 70;
		        
				if(i == 10) {
					BARCODE_X += 250;
					BARCODE_Y = 750;
				}
				
				if(i == 20) {
					document.newPage();
					BARCODE_X = 85;
					BARCODE_Y = 750;
				}
	        
			}
	        
	        
	        document.close();
			document = null;
			
			
			
			//upload to Amazon S3
			String targetDir = "sticker";
			boolean isUploaded = ResourceBusinessModel.uploadFile(path, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}

			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
	
	/**
	 * 產生 General Cargo 的貼紙
	 * 
	 * @param request
	 * @param consignmentData
	 * @return
	 */
	public static String generatePartnerSticker (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData) {
	    
		String result = "";
		String timeNow = UtilsBusinessModel.todayDate()+" "+UtilsBusinessModel.timeNow();
		String filename = timeNow + "-sticker.pdf";
		
	    try {
	    	
	    	String path = request.getSession().getServletContext().getRealPath("/")+"temp/"+timeNow+"-sticker.pdf";
	    	

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
	    	boolean success = false;
			if(path != null && path.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(path)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getGeneralCargoNo()+"-sticker.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getGeneralCargoNo()+"-sticker.pdf");
			}

			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			
			int BARCODE_X = 85;
			int BARCODE_Y = 758;

	    	
	    	for (int i = 0; i < consignmentData.size(); i++) {
	    		
	    		ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(i);
	    		
	    		PdfContentByte canvas = writer.getDirectContent();
				canvas.beginText();
	    		
	    		//Destination Code
				int Xmove = 130;
				if(data.getPartnerId()==1) { //amber
					Xmove = 85;
				} else if(data.getPartnerId()==2) { //FSK
					Xmove = 130;
				}
				
		        String origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
		        String destination = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
		        canvas.setFontAndSize(BF_HELVBOLD, 11);
		        canvas.showTextAligned(Element.ALIGN_CENTER, origin + (data.getSenderZoneserial() > 0 ? " "+data.getSenderZoneserial() : ""), BARCODE_X - 8, BARCODE_Y + 22, 90);
		        canvas.showTextAligned(Element.ALIGN_CENTER, destination + (data.getReceiverZoneserial() > 0 ? " "+data.getReceiverZoneserial() : ""), BARCODE_X + Xmove, BARCODE_Y + 22, 90);
		        
		        canvas.setFontAndSize(BF_HELV, 7);
		        
		        String partnerNM = "";
		        if(data.getCname() != null) {
		        	partnerNM = data.getCname();
		        }
		        canvas.showTextAligned(Element.ALIGN_CENTER, partnerNM, BARCODE_X + 60, BARCODE_Y + 44, 0);
		        
		        canvas.endText();
		        
		        //barcode
		        Barcode128 code128 = new Barcode128();
		        code128.setCode(data.getGeneralCargoNo());
		        code128.setCodeType(Barcode128.CODE128);
		        Image code128Image = code128.createImageWithBarcode(cb, null, null);
		        code128Image.setAbsolutePosition(BARCODE_X, BARCODE_Y);
		        code128Image.scalePercent(115);
		        document.add(code128Image);
		        
		        BARCODE_Y -= 70;
		        
				if(i == 10) {
					BARCODE_X += 250;
					BARCODE_Y = 750;
				}
				
				if(i == 20) {
					document.newPage();
					BARCODE_X = 85;
					BARCODE_Y = 750;
				}
		        
	    	}

	        document.close();
			document = null;
			
			result = filename; //成功產生，則把檔名當作 result 回傳
			
			//upload to Amazon S3
			String targetDir = "sticker";
			boolean isUploaded = ResourceBusinessModel.uploadFile(path, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}

			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
	/**
	 * 產生 Consignment Manifest
	 * 
	 * @param request
	 * @param consignmentData
	 * @param creator
	 * @return
	 */
	public static String generateManifest (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData, String creator, boolean isCargoManifest) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);
			String fromtoNM = AreaBusinessModel.parse2AreaCode(data.getSenderArea(), data.getReceiverArea());
	    	
			
			String nameStart = "manifest";
			if(isCargoManifest){
				nameStart = "cargomanifest";
			}
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_manifest.pdf";
	    	String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/"+nameStart+"_"+fromtoNM.replaceAll(" ", "")+"_"+data.getDispatchDT().substring(0, 10)+".pdf";
	    	result = nameStart+"_"+fromtoNM.replaceAll(" ", "")+"_"+data.getDispatchDT().substring(0, 10)+".pdf"; //傳回檔名
	    	
	    	//** ORIGINAL
	    	//String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_manifest.pdf";
	    	//String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/manifest_"+fromtoNM.replaceAll(" ", "")+"_"+data.getDispatchDT().substring(0, 10)+".pdf";
	    	//result = "manifest_"+fromtoNM.replaceAll(" ", "")+"_"+data.getDispatchDT().substring(0, 10)+".pdf"; //傳回檔名

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			//Date
			PdfContentByte canvas = writer.getDirectContent();
			
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 15);
	        canvas.showTextAligned(Element.ALIGN_CENTER, fromtoNM, 348, 810, 0);
	        canvas.endText();
	        
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, "Date: " + data.getDispatchDT().substring(0, 10), 570, 770, 0);
	        canvas.endText();
			
			float[] colsWidth = {0.15f, 0.35f, 0.5f, 0.3f, 0.55f, 0.55f, 0.6f, 0.15f, 0.2f, 0.13f, 0.22f, 0.24f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
	        float[] colsWidth2 = {0.1f, 0.3f, 0.5f, 0.3f, 0.45f, 0.45f, 0.4f, 0.3f, 0.15f, 0.2f, 0.13f, 0.22f, 0.24f}; // for Cargo Manifest
	        
			PdfPTable table = new PdfPTable( isCargoManifest? colsWidth2 : colsWidth );
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("No", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Date", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Customer Name", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Term of Payment", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Sender", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Receiver", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Consignment No.", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			if(isCargoManifest){// has Flight Number
				cell = new PdfPCell(new Phrase("Flight No.", new Font(BF_HELVBOLD, 7)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			
			cell = new PdfPCell(new Phrase("Type", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Zone", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Pcs", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Weight (KG)", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Sum (RM)", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			String flightNum = "";
			
			int totalQty = 0;
			double totalWeight = 0.0;
			double totalAmount = 0.0;
			
			double totalPayMethod1 = 0.0;
			double totalPayMethod2 = 0.0;
			double totalPayMethod3 = 0.0;
			double totalPayMethod4 = 0.0;
			double totalPayMethod5 = 0.0;
			double totalPayMethod6 = 0.0;
			double totalPayMethod7 = 0.0;
			
			for (int i = 0; i < data.getTotal(); i++) {
				
				data = (ConsignmentDataModel)consignmentData.get(i);
				
				String no = (i+1)+".";
				String dt = data.getCreateDT().substring(0, 10);
				String customerName = "";
				String paymethod = ConsignmentBusinessModel.parsePayMethod(data.getPayMethod(), "en_US");
				
				if(isCargoManifest){
					flightNum = data.getFlightNum();
				}
					
					if(data.getPayMethod()==1) { //cash
						customerName = "Cash";
						totalPayMethod1 += data.getAmount();
					} else if(data.getPayMethod()==2) { //Online Payment
						customerName = "Online Payment";
						totalPayMethod2 += data.getAmount();
					} else if(data.getPayMethod()==3) { //Bank Transfer
						customerName = "Bank Transfer";
						totalPayMethod3 += data.getAmount();
					} else if(data.getPayMethod()==4) { //credit
						customerName = data.getEname(); //shows company name of partner
						totalPayMethod4 += data.getAmount();
					} else if(data.getPayMethod()==5) { //C.O.D.
						customerName = "C.O.D. CUSTOMER";
						totalPayMethod5 += data.getAmount();
					} else if(data.getPayMethod()==6) { //freight charges
						customerName = "CASH CUSTOMER";
						totalPayMethod6 += data.getAmount();
					} else if(data.getPayMethod()==7) { //company mail
						customerName = "CO MAIL";
						totalPayMethod7 += data.getAmount();
					}
				String sender = data.getSenderName();
				String receiver = data.getReceiverName();
				String conNo = data.getConsignmentNo();
				String shipmentType = "";
					if(data.getShipmentType()==0){
						shipmentType = "?";
					} else if(data.getShipmentType()==1){
						shipmentType = "DOC";
					} else if(data.getShipmentType()==2){
						shipmentType = "PCL";
					}
				String zone = AreaBusinessModel.parseAreaCode(data.getReceiverArea()) + (data.getReceiverZone()==0? "" : String.valueOf(data.getReceiverZone()));
				String quantity = String.valueOf(data.getQuantity());
				String weight = String.valueOf(data.getWeight());
				String amount = String.valueOf(formatter.format(data.getAmount()));
				
				String thirdpartyCN = "";
				if(!data.getGeneralCargoNo().equals("")) {
					thirdpartyCN = "\n\n" + data.getCname() + ": " + data.getGeneralCargoNo();
				}
				
				String consignmentSerials = "";
				for(int s = 1; s <= data.getQuantity(); s++){
					String serialsJSON = data.getSerials().isEmpty() ? "{}" : data.getSerials();
					JSONObject serialsObj = new JSONObject(serialsJSON);
					
					boolean isScanned = false;
					if(serialsObj.has(conNo)){
						String[] serials = serialsObj.getString(conNo).split(",");
						for(int ss = 0; ss < serials.length; ss++){
							String serial = serials[ss];
							if(s == Integer.parseInt(serial)){
								isScanned = true;
								break; // break loop if serial is scanned
							}
						}
						if(s != 1){
							consignmentSerials += "\n";
						}
						String formatedSerial = String.format("%03d", s);
						consignmentSerials += conNo +" "+ formatedSerial + (isScanned ? "" : "*");
					}
				}
				
				
				cell = new PdfPCell(new Phrase(no, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(40f);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(dt, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(customerName, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(paymethod, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(sender, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(receiver, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(consignmentSerials + thirdpartyCN, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				if(isCargoManifest){
					cell = new PdfPCell(new Phrase(flightNum, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				
				cell = new PdfPCell(new Phrase(shipmentType, new Font(BF_HELV, 7)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(zone, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(quantity, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				
				totalQty += data.getQuantity();
				
				cell = new PdfPCell(new Phrase(weight, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				
				totalWeight += data.getWeight();
				
				cell = new PdfPCell(new Phrase(amount, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				
				totalAmount += data.getAmount();
				
			}
			

			//total
			cell = new PdfPCell(new Phrase("Total:", new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(20f);
			cell.setColspan(9);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(totalQty), new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			
			cell = new PdfPCell(new Phrase(String.valueOf(totalWeight), new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(totalAmount)), new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Phrase("* is not yet scanned", new Font(BF_HELV, 8)));
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(12);
			table.addCell(cell);
			
			
			//blank
			cell = new PdfPCell(new Phrase("", new Font(BF_HELV, 8)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(12);
			table.addCell(cell);
			
			
			//statistic
			cell = new PdfPCell(new Phrase("Cash:\nOnline Payment:\nBank Transfer:\nCredits:\nC.O.D.:\nFreight Charges:\nCompany Mail:", new Font(BF_HELV, 8)));
			cell.setBorderColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(6);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM "+formatter.format(totalPayMethod1)+"\nRM "+formatter.format(totalPayMethod2)+"\nRM "+formatter.format(totalPayMethod3)+"\nRM "+formatter.format(totalPayMethod4)+"\nRM "+formatter.format(totalPayMethod5)+"\nRM "+formatter.format(totalPayMethod6)+"\nRM "+formatter.format(totalPayMethod7)), new Font(BF_HELV, 8)));
			cell.setBorderColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("", new Font(BF_HELV, 8)));
			cell.setBorderColor(BaseColor.WHITE);
			cell.setColspan(7);
			table.addCell(cell);
	        
			
			document.add(table);
			
	        
			Paragraph paragraph = new Paragraph();		
//			document.add(new Paragraph(Chunk.NEWLINE)); //空行
//			document.add(new Paragraph(Chunk.NEWLINE)); //空行
			
			//signature
	        paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_CENTER);	        
	        paragraph.add(new Chunk("______________                                                                                                                         ______________", new Font(BF_HELV, 10)));
	        document.add(new Paragraph(paragraph));
	        
	        paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_CENTER);	        
	        paragraph.add(new Chunk(" PREPARED BY                                                                                                                            RECEIVED BY  ", new Font(BF_HELV, 10)));
	        document.add(new Paragraph(paragraph));
	        
			document.close();
			document = null;
			
			
			
			//save data to DB
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			if(isCargoManifest){
				flightNum = data.getFlightNum();
			}
			
			pstmt = conn.prepareStatement("INSERT INTO manifest (manifestDT, origin, destination, quantity, pcs, amount, flightNum, filename, creator, createDT) VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())");			

			pstmt.setString(1, data.getDispatchDT().substring(0, 10));
			pstmt.setInt(2, data.getSenderArea());
			pstmt.setInt(3, data.getReceiverArea());
			pstmt.setInt(4, data.getTotal());
			pstmt.setInt(5, totalQty);
			pstmt.setDouble(6, totalAmount);
			pstmt.setString(7, flightNum);
			pstmt.setString(8, result);
			pstmt.setString(9, creator);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			
			//upload to Amazon S3
			String targetDir = "manifest";
			
			// Use same folder as normal manifest
//			if(isCargoManifest){
//				targetDir = "cargomanifest";
//			}
			
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}

	
	/**
	 * 產生 Consignment Manifest
	 * 
	 * @param request
	 * @param consignmentData
	 * @param creator
	 * @return
	 */
	public static String generatePosAviationManifest (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData, String creator) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);
			String fromtoNM = AreaBusinessModel.parse2AreaCode(data.getSenderArea(), data.getReceiverArea());
			
			String nameStart = "posmanifest";
			
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_posManifest.pdf";
	    	String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/"+nameStart+"_"+fromtoNM.replaceAll(" ", "")+"_"+data.getDispatchDT().substring(0, 10)+".pdf";
	    	result = nameStart+"_"+fromtoNM.replaceAll(" ", "")+"_"+data.getDispatchDT().substring(0, 10)+".pdf"; //傳回檔名

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			//Date
			PdfContentByte canvas = writer.getDirectContent();
	        
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getFlightNum(), 82, 697, 0);
	        canvas.endText();
	        
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getDispatchDT().substring(0, 10), 82, 680, 0);
	        canvas.endText();
	        
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getSenderAreaname(), 135, 664, 0);
	        canvas.endText();
	        
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_LEFT, data.getReceiverAreaname(), 135, 647, 0);
	        canvas.endText();
			
			float[] colsWidth = {0.02f, 0.30f, 0.17f, 0.45f, 0.13f, 0.22f, 0.18f, 0.2f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
	        
			PdfPTable table = new PdfPTable(colsWidth);
			table.setWidthPercentage(97);
			
			PdfPCell cell = null;
			
			int totalQty = 0;
			double totalWeight = 0.0;
			//double totalAmount = 0.0;
			
			for (int i = 0; i < 8; i++) {
				cell = new PdfPCell(new Phrase("", new Font(BF_HELV, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(180f);
				table.addCell(cell);
			}
			
			for (int i = 0; i < data.getTotal(); i++) {
				
				data = (ConsignmentDataModel)consignmentData.get(i);
				
				String senderName = data.getSenderName();
				String airwaybillNum = data.getAirwaybillNum() + "\n("+senderName+")";
				String quantity = "" + data.getQuantity();
				String natureGoods = data.getNatureGood();
				String specialCargo = "";
				String weight = "" + data.getWeight();
				String areaName = data.getReceiverAreaname();
				String finalDest = data.getReceiverZonename();
				
				boolean noZone = finalDest.trim().equals("") ? true : false;
				
				totalQty += data.getQuantity();
				totalWeight += data.getWeight();
				
				cell = new PdfPCell(new Phrase("", new Font(BF_HELV, 13)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(40f);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(airwaybillNum, new Font(BF_HELV, 13)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(quantity, new Font(BF_HELV, 13)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(natureGoods, new Font(BF_HELV, 13)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(specialCargo, new Font(BF_HELV, 13)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(weight, new Font(BF_HELV, 13)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(noZone ? areaName : finalDest, new Font(BF_HELV, noZone ? 11 : 9)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("", new Font(BF_HELV, 8)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
			}
			

			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_LEFT, ""+totalQty, 150, 60, 0);
	        canvas.endText();

			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_LEFT, ""+totalWeight, 395, 60, 0);
	        canvas.endText();
			
			document.add(table);
			document.close();
			document = null;
			
			//save data to DB
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			
			
			pstmt = conn.prepareStatement("INSERT INTO manifest (manifestDT, origin, destination, quantity, pcs, amount, flightNum, filename, creator, createDT) VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())");			

			pstmt.setString(1, data.getDispatchDT().substring(0, 10));
			pstmt.setInt(2, data.getSenderArea());
			pstmt.setInt(3, data.getReceiverArea());
			pstmt.setInt(4, data.getTotal());
			pstmt.setInt(5, totalQty);
			pstmt.setDouble(6, 0);
			pstmt.setString(7, data.getFlightNum());
			pstmt.setString(8, result);
			pstmt.setString(9, creator);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			
			//upload to Amazon S3
			String targetDir = "manifest";
			
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
	/**
	 * 產生 Runsheet
	 * 
	 * @param request
	 * @param consignmentData
	 * @param creator
	 * @return
	 */
	public static String generateRunsheet (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData, String creator) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);
	    	
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_runsheet.pdf";
	    	String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/runsheet_"+data.getDistributeDT().substring(0, 10)+"_"+data.getDeliveryDriver()+".pdf";
	    	result = "runsheet_"+data.getDistributeDT().substring(0, 10)+"_"+data.getDeliveryDriver()+".pdf"; //傳回檔名

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			//Date
			PdfContentByte canvas = writer.getDirectContent();
			
			String area = AreaBusinessModel.parseAreaName(data.getReceiverArea());
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 15);
	        canvas.showTextAligned(Element.ALIGN_CENTER, area, 328, 810, 0);
	        canvas.endText();
	        
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, "Date: " + data.getDistributeDT().substring(0, 10), 570, 770, 0);
	        canvas.endText();
			
			
			float[] colsWidth = {0.08f, 0.4f, 0.3f, 0.1f, 0.15f, 0.3f, 0.15f, 0.4f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table = new PdfPTable(colsWidth);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("No", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Receiver", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Consignment No.", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Origin", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("PCS", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Amount Payment (CASH/COD/AC/CHQ)", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Time", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Name (I/C & Company Stamp)", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			int totalQty = 0;
			double tobecollected = 0.0;
			
			for (int i = 0; i < data.getTotal(); i++) {
				
				data = (ConsignmentDataModel)consignmentData.get(i);
				
				String no = (i+1)+".";
				String receiver = data.getReceiverName();
				String thirdpartyCN = "";
				String conNo = data.getConsignmentNo();
				String origin = AreaBusinessModel.parseAreaCode(data.getSenderArea());
				int quantity = data.getQuantity();
				double total = 0.0;
				double weight =data.getWeight();
				
				if(!data.getGeneralCargoNo().equals("")) {
					thirdpartyCN = "\n\n\n" + data.getCname() + ": " + data.getGeneralCargoNo();
				}
				
				cell = new PdfPCell(new Phrase(no, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(50f);
				table.addCell(cell);
		        
				cell = new PdfPCell(new Phrase(receiver+thirdpartyCN, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				//barcode	        
		        Barcode128 code128 = new Barcode128();
		        code128.setCode(conNo);
		        code128.setCodeType(Barcode128.CODE128);
		        Image code128Image = code128.createImageWithBarcode(cb, null, null);
		        code128Image.scalePercent(90);
		        cell.addElement(code128Image);
		        table.addCell(cell);
		        
//				cell = new PdfPCell(new Phrase(conNo, new Font(BF_HELV, 8)));
//				cell.setBorderColor(BaseColor.LIGHT_GRAY);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(origin, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(quantity+"\n\n"+weight+" KG", new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				totalQty += data.getQuantity();
				
				
				if( (data.getTerms().equals("CC")) || (data.getPayMethod()==5) || (data.getPayMethod()==6) ) {
					total = data.getAmount();
					tobecollected += data.getAmount();
				}
				
				cell = new PdfPCell(new Phrase(String.valueOf(total), new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("", new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("", new Font(BF_HELV, 7)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);				
				
			}
	        
			document.add(table);
			
			//total
			Paragraph paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_CENTER);	        
	        paragraph.add(new Chunk("Total:   " + totalQty + " PCS                             Amount To Be Collected:   RM " + String.valueOf(formatter.format(tobecollected)), new Font(BF_HELVBOLD, 8)));
	        document.add(new Paragraph(paragraph));
			
			document.add(new Paragraph(Chunk.NEWLINE)); //空行
			
			//driver & attendee name
	        paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_LEFT);	        
	        paragraph.add(new Chunk("         DRIVER: " + data.getEname() + "  (" + data.getIC() + ")", new Font(BF_HELV, 10)));
	        document.add(new Paragraph(paragraph));
	        
	        String attendee1 = "n/a";
	        if(data.getAttendee1().toString().trim().length() > 0) {
	        	attendee1 = data.getAttendee1() + "  (" + data.getAttendee1IC() + ")";
	        }
	        paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_LEFT);	        
	        paragraph.add(new Chunk("ATTENDEE 1: " + attendee1, new Font(BF_HELV, 10)));
	        document.add(new Paragraph(paragraph));
	        
	        String attendee2 = "n/a";
	        if(data.getAttendee2().toString().trim().length() > 0) {
	        	attendee2 = data.getAttendee2() + "  (" + data.getAttendee2IC() + ")";
	        }
	        paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_LEFT);	        
	        paragraph.add(new Chunk("ATTENDEE 2: " + attendee2, new Font(BF_HELV, 10)));
	        document.add(new Paragraph(paragraph));
	        
			document.close();
			document = null;
			
			
			//save data to DB
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			conn = ConnectionManager.getConnection();
			if(conn == null) {
				logger.fatal("*** NO connection");
	        }
			
			pstmt = conn.prepareStatement("INSERT INTO runsheet (runsheetDT, area, driver, attendee1, attendee2, quantity, pcs, amount, filename, creator, createDT) VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())");			

			pstmt.setString(1, data.getDistributeDT().substring(0, 10));
			pstmt.setInt(2, data.getReceiverArea());
			pstmt.setString(3, data.getDriverID());
			pstmt.setString(4, data.getAttendee1ID());
			pstmt.setString(5, data.getAttendee2ID());
			pstmt.setInt(6, data.getTotal());
			pstmt.setInt(7, totalQty);
			pstmt.setDouble(8, tobecollected);
			pstmt.setString(9, result);
			pstmt.setString(10, creator);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();

			
			//upload to Amazon S3
			String targetDir = "runsheet";
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
	/**
	 * 產生 Runsheet 報表
	 * 
	 * @param request
	 * @param consignmentData
	 * @return
	 */
	public static String generateRunsheetReport (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData, String driver) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);
	    	String areaNM = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	    	
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_generalheader.pdf";
	    	String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/runsheetReport_"+areaNM+"_"+data.getDistributeDT().substring(0, 10)+"_"+driver+".pdf";
	    	result = "runsheetReport_"+areaNM+"_"+data.getDistributeDT().substring(0, 10)+"_"+driver+".pdf"; //傳回檔名
	    	
	    	if(driver.equals("")) { //全區，沒有指定driver
	    		outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/runsheetReport_"+areaNM+"_"+data.getDistributeDT().substring(0, 10)+".pdf";
	    		result = "runsheetReport_"+areaNM+"_"+data.getDistributeDT().substring(0, 10)+".pdf"; //傳回檔名
	    	}
	    	

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			PdfContentByte canvas = writer.getDirectContent();
			
			String areaName = AreaBusinessModel.parseAreaName(data.getReceiverArea());

			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_CENTER, areaName, 380, 815, 0);
	        canvas.endText();
	        
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 15);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "Daily Delivery Runsheet Report", 380, 800, 0);
	        canvas.endText();
	        
	        canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getEname(), 380, 783, 0);
	        canvas.endText();
	        
	        //Date
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, "Distribute Date: " + data.getDistributeDT().substring(0, 10), 570, 770, 0);
	        canvas.endText();
			
			
			float[] colsWidth = {0.1f, 0.25f, 0.25f, 0.3f, 0.3f, 0.1f, 0.2f, 0.3f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table = new PdfPTable(colsWidth);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("No", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Consignment No.", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Partner C/N", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Sender", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Receiver", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("PCS", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Status", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Delivery Time", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			int totalTobedelivery = consignmentData.size();
			int totalDelivered = 0;
			int totalTobecollected = 0;
			int totalCollected = 0;
			double amountTobecollected = 0.0;
			double amountCollected = 0.0;
			
			for (int i = 0; i < consignmentData.size(); i++) {
				
				data = (ConsignmentDataModel)consignmentData.get(i);
				
				String no = (i+1)+".";
				String sender = data.getSenderName();
				String receiver = data.getReceiverName();
				String conNo = data.getConsignmentNo();
				String pcs = String.valueOf(data.getQuantity());
				String thirdpartyCN = data.getGeneralCargoNo();
				String deliveryTime = data.getDeliveryDT();
//				if(data.getDeliveryDT().length() > 10) {
//					deliveryTime = data.getDeliveryDT().substring(11, 19);
//				}

				String stageTXT = "";
				int stage = data.getStage();
				if(stage == 0) {
					stageTXT = Resource.getString("ID_LABEL_STAGE0", "en_US");
				} if(stage == 1) {
					stageTXT = Resource.getString("ID_LABEL_STAGE1", "en_US");
				} if(stage == 2) {
					stageTXT = Resource.getString("ID_LABEL_STAGE2", "en_US");
				} if(stage == 3) {
					stageTXT = Resource.getString("ID_LABEL_STAGE3", "en_US");
				} if(stage == 4) {
					stageTXT = Resource.getString("ID_LABEL_STAGE4", "en_US");
				} else if(stage == 5) {
					stageTXT = Resource.getString("ID_LABEL_STAGE5", "en_US");
				} else if(stage == 6) {
					stageTXT = Resource.getString("ID_LABEL_STAGE6", "en_US");
				} else if(stage == 7) {
					stageTXT = Resource.getString("ID_LABEL_STAGE7", "en_US");
					totalDelivered += 1;
				} else if(stage == 9) {
					stageTXT = Resource.getString("ID_LABEL_STAGE9", "en_US");
				}
				
				
				if( (data.getTerms().equals("CC")) || (data.getPayMethod()==5) || (data.getPayMethod()==6) ) {
					totalTobecollected += 1;
					amountTobecollected += data.getAmount();
					
					if(stage == 7) {
						totalCollected += 1;
						amountCollected += data.getAmount();
					}
					
				}

				
				cell = new PdfPCell(new Phrase(no, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(30f);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(conNo, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(thirdpartyCN, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
		        
				cell = new PdfPCell(new Phrase(sender, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(receiver, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(pcs, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);	
				
				cell = new PdfPCell(new Phrase(stageTXT, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);	
				
				cell = new PdfPCell(new Phrase(deliveryTime, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);	
				
			}
	        
			document.add(table);
			
			document.add(new Paragraph(Chunk.NEWLINE)); //空行
			document.add(new Paragraph(Chunk.NEWLINE)); //空行
			

	        
	        float[] colsWidth2 = {0.3f, 0.2f, 0.3f, 0.2f, 0.3f, 0.2f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table2 = new PdfPTable(colsWidth2);
			table2.setWidthPercentage(100);
			
			//Total Consignments
			cell = new PdfPCell(new Phrase("Total Consignments:", new Font(BF_HELV, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(totalTobedelivery)), new Font(BF_HELV, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Total C.O.D.:", new Font(BF_HELV, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(totalTobecollected)), new Font(BF_HELV, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Amount to be Collected:", new Font(BF_HELV, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " +formatter.format(amountTobecollected)), new Font(BF_HELV, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			
			cell = new PdfPCell(new Phrase("Reached Receiver:", new Font(BF_HELVBOLD, 7)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(totalDelivered)), new Font(BF_HELVBOLD, 7)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Collected C.O.D.:", new Font(BF_HELVBOLD, 7)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(totalCollected)), new Font(BF_HELVBOLD, 7)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);

			cell = new PdfPCell(new Phrase("Collected:", new Font(BF_HELVBOLD, 7)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " +formatter.format(amountCollected)), new Font(BF_HELVBOLD, 7)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			
			cell = new PdfPCell(new Phrase("", new Font(BF_HELVBOLD, 7)));
			cell.setColspan(4);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);

			cell = new PdfPCell(new Phrase("Pending:", new Font(BF_HELVBOLD, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " +formatter.format(amountTobecollected - amountCollected)), new Font(BF_HELVBOLD, 7)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			document.add(table2);
	        
			document.add(new Paragraph(Chunk.NEWLINE)); //空行
			
			
			//commission
	        int totalPax = 1;
	        double totalDeliverComm = totalDelivered * 0.2; //20 cents per delivery
	        double totalCODComm = totalCollected * 0.2; //20 cents per collection of money
	        String driverNM = data.getDriver().equals("") ? "n/a" : data.getDriver();
	        String attendee1NM = data.getAttendee1();
	        String attendee2NM = data.getAttendee2();
	        double attendee1Comm = 0.0;
	        double attendee2Comm = 0.0;
	        
	        if( (attendee1NM.length()>0) && (!attendee1NM.equals("n/a")) ){
	        	totalPax += 1;
	        }
	        if( (attendee2NM.length()>0) && (!attendee2NM.equals("n/a")) ){
	        	totalPax += 1;
	        }
	        
	        double comm = (totalDeliverComm / totalPax) + (totalCODComm / totalPax);
	        
	        if( (attendee1NM.length()>0) && (!attendee1NM.equals("n/a")) ){
	        	attendee1Comm = comm;
	        }
	        if( (attendee2NM.length()>0) && (!attendee2NM.equals("n/a")) ){
	        	attendee2Comm = comm;
	        }
	        
			
			float[] colsWidth3 = {0.3f, 0.2f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table3 = new PdfPTable(colsWidth3);
			table3.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.setWidthPercentage(33);
			
			//Total Delivery Commission:
			cell = new PdfPCell(new Phrase("Total Delivery Commission:", new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " + formatter.format(totalDeliverComm)), new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);
			
			//Total C.O.D. Commission:
			cell = new PdfPCell(new Phrase("Total C.O.D. Commission:", new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(15f);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " + formatter.format(totalCODComm)), new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Driver:\r\n"+driverNM, new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(20f);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " + formatter.format(comm)), new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Attendee 1:\r\n"+attendee1NM, new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(20f);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " + formatter.format(attendee1Comm)), new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Attendee 2:\r\n"+attendee2NM, new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(20f);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf("RM " + formatter.format(attendee2Comm)), new Font(BF_HELV, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);

			
			document.add(table3);
	        
			
	        
			document.close();
			document = null;

			
			//upload to Amazon S3
			String targetDir = "runsheetReport";
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}


	
	/**
	 * 產生 Third Party 的 Status Report
	 * 
	 * @param request
	 * @param consignmentData
	 * @param stage
	 * @param creator
	 * @return
	 */
	public static String generatePartnerReport (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData, int thisstage, String creator) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);
	    	String areaNM = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	    	
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_generalheader.pdf";
	    	String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/partnerReport_"+areaNM+"_"+data.getCreateDT().substring(0, 10)+"_"+data.getPartnerId()+"_stage"+thisstage+".pdf";
	    	result = "partnerReport_"+areaNM+"_"+data.getCreateDT().substring(0, 10)+"_"+data.getPartnerId()+"_stage"+thisstage+".pdf"; //傳回檔名

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			PdfContentByte canvas = writer.getDirectContent();
			
			String areaName = AreaBusinessModel.parseAreaName(data.getReceiverArea());

			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_CENTER, areaName, 380, 815, 0);
	        canvas.endText();
	        
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 15);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "Partner Consignment Status Report", 380, 800, 0);
	        canvas.endText();
	        
	        canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getEname(), 380, 783, 0);
	        canvas.endText();
	        
	        //Date
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, "Create Date: " + data.getCreateDT().substring(0, 10), 570, 770, 0);
	        canvas.endText();
			
			
			float[] colsWidth = {0.1f, 0.3f, 0.3f, 0.4f, 0.4f, 0.3f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table = new PdfPTable(colsWidth);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("No", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Consignment No.", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Partner C/N", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Sender", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Receiver", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Status", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			for (int i = 0; i < consignmentData.size(); i++) {
				
				data = (ConsignmentDataModel)consignmentData.get(i);
				
				String no = (i+1)+".";
				String sender = data.getSenderName();
				String receiver = data.getReceiverName();
				String conNo = data.getConsignmentNo();
				String thirdpartyCN = data.getGeneralCargoNo();

				String stageTXT = "";
				int stage = data.getStage();
				if(stage == 0) {
					stageTXT = Resource.getString("ID_LABEL_STAGE0", "en_US");
				} if(stage == 1) {
					stageTXT = Resource.getString("ID_LABEL_STAGE1", "en_US");
				} if(stage == 2) {
					stageTXT = Resource.getString("ID_LABEL_STAGE2", "en_US");
				} if(stage == 3) {
					stageTXT = Resource.getString("ID_LABEL_STAGE3", "en_US");
				} if(stage == 4) {
					stageTXT = Resource.getString("ID_LABEL_STAGE4", "en_US");
				} else if(stage == 5) {
					stageTXT = Resource.getString("ID_LABEL_STAGE5", "en_US");
				} else if(stage == 6) {
					stageTXT = Resource.getString("ID_LABEL_STAGE6", "en_US");
				} else if(stage == 7) {
					stageTXT = Resource.getString("ID_LABEL_STAGE7", "en_US");
					stageTXT += "\r\n\r\n"+data.getDeliveryDT();
				} else if(stage == 9) {
					stageTXT = Resource.getString("ID_LABEL_STAGE9", "en_US");
				}
				
				
				cell = new PdfPCell(new Phrase(no, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(30f);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(conNo, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(thirdpartyCN, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
		        
				cell = new PdfPCell(new Phrase(sender, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(receiver, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(stageTXT, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);	
				
			}
	        
			document.add(table);
			
			//total
			Paragraph paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_CENTER);	        
	        paragraph.add(new Chunk("Total: " + String.valueOf(formatter.format(consignmentData.size())), new Font(BF_HELVBOLD, 8)));
	        document.add(new Paragraph(paragraph));
	        
			document.close();
			document = null;

			
			//upload to Amazon S3
			String targetDir = "partnerReport";
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
	
	/**
	 * 供 Partner 產生屬於他們自己的 Status Report
	 * 
	 * @param request
	 * @param consignmentData
	 * @param stage
	 * @param creator
	 * @return
	 */
	public static String generatePartnerDeliveryReport (HttpServletRequest request, ArrayList<ConsignmentDataModel> consignmentData, int thisstage, String creator) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	ConsignmentDataModel data = (ConsignmentDataModel)consignmentData.get(0);
	    	String areaNM = AreaBusinessModel.parseAreaCode(data.getReceiverArea());
	    	
	    	long lMillis = System.currentTimeMillis();
	    	String timestamp = String.valueOf(lMillis);
	    	
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_generalheader.pdf";
	    	String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/partnerDeliveryReport_"+areaNM+"_"+data.getCreateDT().substring(0, 10)+"_"+data.getPartnerId()+"_stage"+thisstage+"_"+timestamp+".pdf";
	    	result = "partnerDeliveryReport_"+areaNM+"_"+data.getCreateDT().substring(0, 10)+"_"+data.getPartnerId()+"_stage"+thisstage+"_"+timestamp+".pdf"; //傳回檔名

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			PdfContentByte canvas = writer.getDirectContent();
			
			String areaName = AreaBusinessModel.parseAreaName(data.getReceiverArea());

			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_CENTER, areaName, 380, 815, 0);
	        canvas.endText();
	        
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 15);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "Partner Consignment Status Report", 380, 800, 0);
	        canvas.endText();
	        
	        canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 13);
	        canvas.showTextAligned(Element.ALIGN_CENTER, data.getEname(), 380, 783, 0);
	        canvas.endText();
	        
	        //Date
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELV, 10);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, "Create Date: " + data.getCreateDT().substring(0, 10), 570, 770, 0);
	        canvas.endText();
			
			
			float[] colsWidth = {0.1f, 0.3f, 0.3f, 0.4f, 0.4f, 0.3f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table = new PdfPTable(colsWidth);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("No", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Consignment No.", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Partner C/N", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Sender", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Receiver", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Status", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			for (int i = 0; i < consignmentData.size(); i++) {
				
				data = (ConsignmentDataModel)consignmentData.get(i);
				
				String no = (i+1)+".";
				String sender = data.getSenderName();
				String receiver = data.getReceiverName();
				String conNo = data.getConsignmentNo();
				String thirdpartyCN = data.getGeneralCargoNo();

				String stageTXT = "";
				int stage = data.getStage();
				if(stage == 0) {
					stageTXT = Resource.getString("ID_LABEL_STAGE0", "en_US");
				} if(stage == 1) {
					stageTXT = Resource.getString("ID_LABEL_STAGE1", "en_US");
				} if(stage == 2) {
					stageTXT = Resource.getString("ID_LABEL_STAGE2", "en_US");
				} if(stage == 3) {
					stageTXT = Resource.getString("ID_LABEL_STAGE3", "en_US");
				} if(stage == 4) {
					stageTXT = Resource.getString("ID_LABEL_STAGE4", "en_US");
				} else if(stage == 5) {
					stageTXT = Resource.getString("ID_LABEL_STAGE5", "en_US");
				} else if(stage == 6) {
					stageTXT = Resource.getString("ID_LABEL_STAGE6", "en_US");
				} else if(stage == 7) {
					stageTXT = Resource.getString("ID_LABEL_STAGE7", "en_US");
					stageTXT += "\r\n\r\n"+data.getDeliveryDT();
				} else if(stage == 9) {
					stageTXT = Resource.getString("ID_LABEL_STAGE9", "en_US");
				}
				
				
				cell = new PdfPCell(new Phrase(no, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(30f);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(conNo, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(thirdpartyCN, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
		        
				cell = new PdfPCell(new Phrase(sender, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(receiver, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(stageTXT, new Font(BF_HELV, 8)));
				cell.setBorderColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);	
				
			}
	        
			document.add(table);
			
			//total
			Paragraph paragraph = new Paragraph();
	        paragraph.setAlignment(Element.ALIGN_CENTER);	        
	        paragraph.add(new Chunk("Total: " + String.valueOf(formatter.format(consignmentData.size())), new Font(BF_HELVBOLD, 8)));
	        document.add(new Paragraph(paragraph));
	        
			document.close();
			document = null;

			
			//upload to Amazon S3
			String targetDir = "partnerDeliveryReport";
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
	
	
	/**
	 * 產生 account 每個 station 每日的報表
	 * 
	 * @param request
	 * @param accData
	 * @param origin
	 * @param destination
	 * @param payMethod
	 * @return
	 */
	public static String generateMemberReport (HttpServletRequest request, ArrayList<AccountDataModel> accData, String memberId) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	AccountDataModel data = (AccountDataModel)accData.get(0);
	    	
	    	String DT = "ALL";
	    	if(data.getAccDT().length() > 0) {
	    		DT = data.getAccDT();
	    	}
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_generalheader.pdf";
    		String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/memberReport_"+memberId+"_"+DT+".pdf";
	    	result = "memberReport_"+memberId+"_"+DT+".pdf"; //傳回檔名
	    	

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			//Date
			PdfContentByte canvas = writer.getDirectContent();
			
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 18);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "Member Report", 328, 805, 0);
	        canvas.endText();
	        
        	
        	canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 12);
	        canvas.showTextAligned(Element.ALIGN_CENTER, "User ID: " + memberId, 328, 785, 0);
	        canvas.endText();
	        
	        
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 10);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, "Date: " + DT, 570, 770, 0);
	        canvas.endText();
			
			
			float[] colsWidth = {0.08f, 0.2f, 0.25f, 0.25f, 0.45f, 0.6f, 0.15f, 0.15f, 0.2f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table = new PdfPTable(colsWidth);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("No", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Date", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Consignment Number", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Partner Consignment", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Sender Name", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("From - To", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Quantity", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Amount (RM)", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Term of Payment", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			double totalAmount = 0.0;
			
			for (int i = 0; i < accData.size(); i++) {
				
				data = (AccountDataModel)accData.get(i);
				
				if( (data.getCreditArea() > 0) && (data.getCreditArea() != data.getSenderArea()) ) { //如果是credit term user, 則必須根據 creditArea 作歸類，如果creditArea 和 origin 不一樣，則不 show 此筆
					
				} else {
				
					String no = (i+1)+".";
					String createDT = data.getCreateDT().length() > 10 ? data.getCreateDT().substring(0, 10) : data.getCreateDT();
					String consignmentNo = data.getConsignmentNo();
					String generalCargoNo = data.getGeneralCargoNo();
					String sender = data.getSenderName();
					String fromto = data.getSenderAreaname() + " - " + data.getReceiverAreaname();
					int quantity = data.getQuantity();
					double amount = data.getAmount();
					String paymethod = ConsignmentBusinessModel.parsePayMethod(data.getPayMethod(), "en_US");
					
					cell = new PdfPCell(new Phrase(no, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setFixedHeight(25f);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(createDT, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(consignmentNo, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(generalCargoNo, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(sender, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(fromto, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(String.valueOf(quantity), new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(amount)), new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(paymethod, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
	
					totalAmount += data.getAmount();
					}
			}
			
			
			//total
			cell = new PdfPCell(new Phrase("Total:", new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(20f);
			cell.setColspan(6);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(totalAmount)), new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("", new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			document.add(table);
			
	
			document.close();
			document = null;
			
			
			
			//upload to Amazon S3
			String targetDir = "memberReport";
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}
	
	
public static String generateSalesReport (HttpServletRequest request, ArrayList<AccountDataModel> accData, int origin, int destination, int payMethod) {
	    
		String result = "";
		Document document = new Document(PageSize.A4, 20, 20, 80, 30);
		
	    try {
	    	
	    	AccountDataModel data = (AccountDataModel)accData.get(0);
	    	
	    	String originNM = AreaBusinessModel.parseAreaCode(origin);
	    	String destinationNM = "";
	    	if(destination > 0) { //有選擇 destination
	    		destinationNM = "-" + AreaBusinessModel.parseAreaCode(destination);
	    	}
	    	
    		String templatePath = request.getSession().getServletContext().getRealPath("/")+"etc/template_generalheader.pdf";
    		String outputPath = request.getSession().getServletContext().getRealPath("/")+"temp/salesReport_"+originNM+destinationNM+"_"+data.getAccDT()+".pdf";
	    	result = "salesReport_"+originNM+destinationNM+"_"+data.getAccDT()+".pdf"; //傳回檔名
	    	

			//先檢查是否已經產生過PDF，如果有，要先刪除，以免無法複寫。
			boolean success = false;
			if(outputPath != null && outputPath.length() > 10){ //10是随便放，不确定是否NULL
				success = (new File(outputPath)).delete();
			}
			
			if(success){
//					logger.info("Deleted old PDF before re-generate new PDF file: " +data.getConsignmentNo()+"-IPOSB.pdf");
			} else {
//					logger.info("Deleted FAILED old PDF file OR the file doesn't exist: " +data.getConsignmentNo()+"-IPOSB.pdf");
			}
			
			
			
			BF_HELV = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BF_HELVBOLD = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
	        BF_HELVOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "Cp1252", false);
	        BF_HELVBOLDOBLIQUE = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, "Cp1252", false);
	        
			
			// Create output PDF
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader(templatePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			document.newPage();
			cb.addTemplate(page, 0, 0);
			
			
			//Date
			PdfContentByte canvas = writer.getDirectContent();
			
			String area = AreaBusinessModel.parseAreaName(origin);
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 18);
	        canvas.showTextAligned(Element.ALIGN_CENTER, area + " Sales Report", 328, 805, 0);
	        canvas.endText();
	        
	        
	        String fromtoNM = "";
	        if(destination > 0) {
	        	fromtoNM = "("+AreaBusinessModel.parse2AreaName(origin, destination)+")";
	        }
        	
        	String paymethodNM = "";
        	if(payMethod > -1) {
        		paymethodNM = " (" + ConsignmentBusinessModel.parsePayMethod(payMethod, "en_US") + ")";
        	}
        	
        	canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 12);
	        canvas.showTextAligned(Element.ALIGN_CENTER, fromtoNM + paymethodNM, 328, 785, 0);
	        canvas.endText();
	        
	        
	        canvas = writer.getDirectContent();
			canvas.beginText();
	        canvas.setFontAndSize(BF_HELVBOLD, 10);
	        canvas.showTextAligned(Element.ALIGN_RIGHT, "Date: " + data.getAccDT(), 570, 770, 0);
	        canvas.endText();
			
			
			float[] colsWidth = {0.08f, 0.3f, 0.3f, 0.5f, 0.5f, 0.15f, 0.15f, 0.3f}; //An array with 2 float values was used to defined a table with 2 columns. The floats in the array define internal table relative widths
			PdfPTable table = new PdfPTable(colsWidth);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("No", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Consignment Number", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Partner Consignment", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Sender Name", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("From - To", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Quantity", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Amount (RM)", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Term of Payment", new Font(BF_HELVBOLD, 7)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			double totalAmount = 0.0;
			
			for (int i = 0; i < accData.size(); i++) {
				
				data = (AccountDataModel)accData.get(i);
				
				if( (data.getCreditArea() > 0) && (data.getCreditArea() != origin) ) { //如果是credit term user, 則必須根據 creditArea 作歸類，如果creditArea 和 origin 不一樣，則不 show 此筆
					
				} else {
				
					String no = (i+1)+".";
					String consignmentNo = data.getConsignmentNo();
					String generalCargoNo = data.getGeneralCargoNo();
					String sender = data.getSenderName();
					String fromto = data.getSenderAreaname() + " - " + data.getReceiverAreaname();
					int quantity = data.getQuantity();
					double amount = data.getAmount();
					String paymethod = ConsignmentBusinessModel.parsePayMethod(data.getPayMethod(), "en_US");
					
					cell = new PdfPCell(new Phrase(no, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setFixedHeight(25f);
					table.addCell(cell);
			        
					cell = new PdfPCell(new Phrase(consignmentNo, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(generalCargoNo, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(sender, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(fromto, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(String.valueOf(quantity), new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(amount)), new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(paymethod, new Font(BF_HELV, 8)));
					cell.setBorderColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
	
					totalAmount += data.getAmount();
					}
			}
			
			
			//total
			cell = new PdfPCell(new Phrase("Total:", new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(20f);
			cell.setColspan(6);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(formatter.format(totalAmount)), new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("", new Font(BF_HELVBOLD, 9)));
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			document.add(table);
			
	
			document.close();
			document = null;
			
			
			
			//upload to Amazon S3
			String targetDir = "salesReport";
			boolean isUploaded = ResourceBusinessModel.uploadFile(outputPath, targetDir);
			if(!isUploaded) { //任何一個沒有上傳到，即標註"失敗"
				result = "failed";
			}
				


			
	    } catch (Exception ex) {
	        logger.error(ex.getMessage());
	        return ex.getMessage().toString();
	    } finally {
	    	BF_HELV = null;
	    	BF_HELVOBLIQUE = null;
	    	BF_HELVBOLDOBLIQUE = null;
	    	BF_HELVBOLD = null;
	    }
	    
	    return result;
	    
	}

	
	
	/**
     * 補齊不足長度
     * @param length 長度
     * @param number 數字
     * @return
     */
    private static String lpad (int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }

	
}
