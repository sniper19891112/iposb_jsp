package com.iposb.upload;


import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iposb.account.AccountBusinessModel;
import com.iposb.area.AreaBusinessModel;
import com.iposb.consignment.ConsignmentBusinessModel;
import com.iposb.consignment.ConsignmentDataModel;
import com.iposb.resource.ResourceBusinessModel;


public class UploadBusinessModel {

	
	
	static Logger logger = Logger.getLogger(UploadBusinessModel.class);
	private UploadBusinessModel(){
		
	}
	
	
	/**
	 * 上傳 photo
	 * 
	 * @param items
	 * @param tempPath
	 * @param path
	 * @param id
	 * @param actionUpload
	 * @param time
	 * @param stage
	 * @param consignmentNo
	 * @return
	 */
	protected static boolean uploadAppPics(List<FileItem> items, String tempPath, String path, int id, String actionUpload, long time, String stage, String consignmentNo) {
		
		boolean isSuccess = false;
		
		if(consignmentNo.length() > 9) {
			consignmentNo = consignmentNo.substring(0, 9);
		}
    	
        try {
            
            for (FileItem item : items) {
                if (!item.isFormField()) {
                	
                	if(actionUpload.equals("avatar")){
	                	File file = new File(tempPath, "avatar.jpg"); //改檔名
	                    item.write(file);
                    
                    	isSuccess = ResourceBusinessModel.uploadImage(file.getPath(), "images/avatar/"+path+"/"+id);
                    	
                    }else{
                    	Date dt = new Date();
                    	String timestamp = String.valueOf(new Timestamp(dt.getTime()));
                    	timestamp = timestamp
                					.replace(" ", "_")
                					.replace(":", "_")
            						.replace(".", "_");
                    	
//logger.info("conPics filename: " + timestamp + ".jpg");
                    	File file = new File(tempPath, timestamp +".jpg"); //改檔名
                        item.write(file);
                        
                        if(actionUpload.equals("conPics")) { //consignment photos
                        	isSuccess = ResourceBusinessModel.uploadImage(file.getPath(), "conPics/stage"+stage+"/"+consignmentNo);
                        } else if(actionUpload.equals("conDO")) { //capture D/O of stage 7
                        	isSuccess = ResourceBusinessModel.uploadImage(file.getPath(), "conDO/"+consignmentNo);
                        }
                	}
                	
                       
                }
            }
        } catch (FileUploadException e) {
        	logger.error(" UploadBusinessModel \"uploadAppPics\" error " + e.toString());
            throw new RuntimeException(e);
               
        } catch (Exception e) {
        	logger.error(" UploadBusinessModel \"uploadAppPics\" error " + e.toString());
            throw new RuntimeException(e);
        }
        
    	return isSuccess;

    }




	/**
	 * 上傳 General Cargo - Amber 的 excel 檔案
	 * 
	 * @param request
	 * @return
	 */
	protected static ArrayList<ConsignmentDataModel> parseAmberExcel(HttpServletRequest request) {
		
		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		
		if (!ServletFileUpload.isMultipartContent(request)) {
	    	 logger.error("Request is not multipart, please 'multipart/form-data' enctype for your form.");
	         throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
	     }
	     
	     
	     ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	     try {
	    	 	 
	         List<FileItem> items = uploadHandler.parseRequest(request);
	         for (FileItem item : items) {
	             if (!item.isFormField()) {
	             		
	            	 	if(item.getName().isEmpty()){
	            	 		logger.info("no file");
	            	 		return null;
	            	 	}
	            	 	
	            	 	String path = request.getSession().getServletContext().getRealPath("/")+"temp" + File.separator + item.getName();
	            	 
	            	 	File file = new File(path);
//	            	 	logger.info("Current Path: "+path);
	                    //String filetype = getMimeType(file);
	            	 	String filetype = getSuffix(file.getName());

	                    item.write(file);
	                    
	                    if (filetype.equals("xls")) {
	                    	
	                    	FileInputStream filenew = new FileInputStream(path);
		         			HSSFWorkbook workbook = new HSSFWorkbook(filenew);
		         			HSSFSheet sheet = workbook.getSheetAt(0);
		         			 
		         			//Iterate through each rows from first sheet
		         			Iterator<Row> rowIterator = sheet.iterator();
		         			while(rowIterator.hasNext()) {
		         		        Row row = rowIterator.next();
		         		        
		         		        boolean fetchdata = false;
		         		        
		         		        if( (row.getRowNum()+1) > 10) { //從第11行開始
		         		        	
		         		        	ConsignmentDataModel obj = new ConsignmentDataModel();
		         		         
		         			        //For each row, iterate through each columns
		         			        Iterator<Cell> cellIterator = row.cellIterator();
		         			        
		         			        int column = 1;
		         			        
		         			        while(cellIterator.hasNext()) {
		         			             
		         			            Cell cell = cellIterator.next();
		         			            
		         			            String cellVal = "";
		         			             
		         			            switch(cell.getCellType()) {
		         			                case Cell.CELL_TYPE_BOOLEAN:
		         	//		                    System.out.print(cell.getBooleanCellValue() + "\t\t");
		         			                    break;
		         			                case Cell.CELL_TYPE_NUMERIC:
		         	//		                    System.out.print(cell.getNumericCellValue() + "\t\t");
		         			                	cellVal = String.valueOf(cell.getNumericCellValue()).trim().equals("") ? "0" : String.valueOf(cell.getNumericCellValue());
		         			                    break;
		         			                case Cell.CELL_TYPE_STRING:
		         	//		                    System.out.print(cell.getStringCellValue() + "\t\t");
		         			                	cellVal = cell.getStringCellValue().replaceAll("'", "").replaceAll("\"", ""); //去除單引號和雙引號
		         			                    break;
		         			            }
		         			            
		         			            if(column==1) {
		         			            	 if (cellVal.trim().length() > 0) { //第一個column有值，才可以抽取整行的資料
		         	    		            	fetchdata = true;
		         	    		            }
		         			            }
		         			            
		         			            if(fetchdata) {
		         			            
		         			            	if(column==1) {
			         	   		            	 obj.setGeneralCargoNo(String.valueOf(Math.round(Float.parseFloat(cellVal))));
		//	         	   		            	 System.out.print("CN No.: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==2) {
			         	   		            	 obj.setSenderName(cellVal.replaceAll("'", "").replaceAll("\"", ""));
		//	         	   		            	 System.out.print("sender: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==3) {
			         	   		            	 obj.setReceiverName(cellVal.replaceAll("'", "").replaceAll("\"", ""));
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==4) {
			         			            	int area = AreaBusinessModel.parseAreaAid(cellVal);
		         	   		            	 	obj.setReceiverArea(area);
		//	         	   		            	System.out.print("Area: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==5) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setQuantity(Math.round(Float.parseFloat(cellVal)));
				         			           	} else {
				         			           		obj.setQuantity(-1);
				         			           	}	         	   		            	 	
		//	         	   		            	System.out.print("Quantity: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==6) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setWeight(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setWeight(0.0);
				         			           	}		         	   		            	 
		//	         	   		            	 System.out.print("Weight: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==7) {
		         	   		            	 	obj.setTerms(cellVal);
		//	         	   		            	System.out.print("Term: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==8) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setAmount(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setAmount(0.0);
				         			           	}
		//	         	   		            	System.out.print("Total: "+cellVal + "\t\t");
			         	   		            }
		         	   		            
		         			            }
		         			            column++;
		         			            
		         			        }
		         			       
		         			        if(fetchdata) { //有資料才加到 data object 裡
		         			        	obj.setPartnerId(1); // Amber
		         			        	obj.setSenderArea(4); // KUL
		         			        	obj.setPayMethod(6); // freight charges
		         			        	obj.setStage(4); //In transit
		         			        	obj.setVerify(ConsignmentBusinessModel.generateVerifyCode());
		         			        	obj.setStaffCreate(1); // 1: 由員工產生
		         			        	obj.setHowtocreate("Upload Excel");
		         						
		         			    	   data.add(obj);
		         			        }
	//	         			        System.out.println("");
		         			    }
		         			}
		         			
	                    } else if (filetype.equals("xlsx")) {
	                    	
	                    	FileInputStream filenew = new FileInputStream(path);
		         			XSSFWorkbook workbook = new XSSFWorkbook(filenew);
		         			XSSFSheet sheet = workbook.getSheetAt(0);
		         			 
		         			//Iterate through each rows from first sheet
		         			Iterator<Row> rowIterator = sheet.iterator();
		         			while(rowIterator.hasNext()) {
		         		        Row row = rowIterator.next();
		         		        
		         		        boolean fetchdata = false;
		         		        
		         		        if( (row.getRowNum()+1) > 10) { //從第11行開始
		         		        	
		         		        	ConsignmentDataModel obj = new ConsignmentDataModel();
		         		         
		         			        //For each row, iterate through each columns
		         			        Iterator<Cell> cellIterator = row.cellIterator();
		         			        
		         			        int column = 1;
		         			        
		         			        while(cellIterator.hasNext()) {
		         			             
		         			            Cell cell = cellIterator.next();
		         			            
		         			            String cellVal = "";
		         			             
		         			            switch(cell.getCellType()) {
		         			                case Cell.CELL_TYPE_BOOLEAN:
		         	//		                    System.out.print(cell.getBooleanCellValue() + "\t\t");
		         			                    break;
		         			                case Cell.CELL_TYPE_NUMERIC:
		         	//		                    System.out.print(cell.getNumericCellValue() + "\t\t");
		         			                	cellVal = String.valueOf(cell.getNumericCellValue()).trim().equals("") ? "0" : String.valueOf(cell.getNumericCellValue());
		         			                    break;
		         			                case Cell.CELL_TYPE_STRING:
		         	//		                    System.out.print(cell.getStringCellValue() + "\t\t");
		         			                	cellVal = cell.getStringCellValue();
		         			                    break;
		         			            }
		         			            
		         			            if(column==1) {
		         			            	 if (cellVal.trim().length() > 0) { //第一個column有值，才可以抽取整行的資料
		         	    		            	fetchdata = true;
		         	    		            }
		         			            }
		         			            
		         			            if(fetchdata) {
		         			            
		         			            	if(column==1) {
			         	   		            	 obj.setGeneralCargoNo(String.valueOf(Math.round(Float.parseFloat(cellVal))));
		//	         	   		            	 System.out.print("CN No.: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==2) {
			         	   		            	 obj.setSenderName(cellVal.replaceAll("'", "").replaceAll("\"", ""));
		//	         	   		            	 System.out.print("sender: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==3) {
			         	   		            	 obj.setReceiverName(cellVal.replaceAll("'", "").replaceAll("\"", ""));
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==4) {
			         			            	int area = AreaBusinessModel.parseAreaAid(cellVal);
		         	   		            	 	obj.setReceiverArea(area);
		//	         	   		            	System.out.print("Area: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==5) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setQuantity(Math.round(Float.parseFloat(cellVal)));
				         			           	} else {
				         			           		obj.setQuantity(-1);
				         			           	}	         	   		            	 	
		//	         	   		            	System.out.print("Quantity: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==6) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setWeight(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setWeight(0.0);
				         			           	}		         	   		            	 
		//	         	   		            	 System.out.print("Weight: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==7) {
		         	   		            	 	obj.setTerms(cellVal);
		//	         	   		            	System.out.print("Term: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==8) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setAmount(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setAmount(0.0);
				         			           	}
		//	         	   		            	System.out.print("Total: "+cellVal + "\t\t");
			         	   		            }
		         	   		            
		         			            }
		         			            column++;
		         			            
		         			        }
		         			       
		         			        if(fetchdata) { //有資料才加到 data object 裡
		         			        	obj.setPartnerId(1); // Amber
		         			        	obj.setSenderArea(4); // KUL
		         			        	obj.setPayMethod(6); // freight charges
		         			        	obj.setStage(4); //In transit
		         			        	obj.setVerify(ConsignmentBusinessModel.generateVerifyCode());
		         			        	obj.setStaffCreate(1); // 1: 由員工產生
		         			        	obj.setHowtocreate("Upload Excel");
		         			        	
		         			    	   data.add(obj);
		         			        }
	//	         			        System.out.println("");
		         			    }
		         			}
		         			
	                    } else { //不是excel
	                    	
	                    }
	                     
		                    
	         			
	             }
	             
	             
	           
	 			
	         }
	     } catch (FileUploadException e) {
	     		logger.error(e.toString());
	            throw new RuntimeException(e);
	            
	     } catch (Exception e) {
	     		logger.error(e.toString());
	            throw new RuntimeException(e);
	     } finally {

	     }
	     
	     return data;
		
	}
	
	
	
	/**
	 * 上傳 General Cargo - FSK 的 excel 檔案
	 * 
	 * @param request
	 * @return
	 */
	protected static ArrayList<ConsignmentDataModel> parseFSKExcel(HttpServletRequest request) {
		
		ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
		
		if (!ServletFileUpload.isMultipartContent(request)) {
	    	 logger.error("Request is not multipart, please 'multipart/form-data' enctype for your form.");
	         throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
	     }
	     
	     
	     ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	     try {
	    	 	 
	         List<FileItem> items = uploadHandler.parseRequest(request);
	         for (FileItem item : items) {
	             if (!item.isFormField()) {
	             		
	            	 	if(item.getName().isEmpty()){
	            	 		logger.info("no file");
	            	 		return null;
	            	 	}
	            	 	
	            	 	String path = request.getSession().getServletContext().getRealPath("/")+"temp" + File.separator + item.getName();
	            	 
	            	 	File file = new File(path);
//	            	 	logger.info("Current Path: "+path);
	                    //String filetype = getMimeType(file);
	            	 	String filetype = getSuffix(file.getName());

	                    item.write(file);
	                    
	                    if (filetype.equals("xls")) {
	                    	
	                    	FileInputStream filenew = new FileInputStream(path);
		         			HSSFWorkbook workbook = new HSSFWorkbook(filenew);
		         			HSSFSheet sheet = workbook.getSheetAt(0);
		         			 
		         			//Iterate through each rows from first sheet
		         			Iterator<Row> rowIterator = sheet.iterator();
		         			while(rowIterator.hasNext()) {
		         		        Row row = rowIterator.next();
		         		        
		         		        boolean fetchdata = false;
		         		        
		         		        if( (row.getRowNum()+1) > 4) { //從第5行開始
		         		        	
		         		        	ConsignmentDataModel obj = new ConsignmentDataModel();
		         		         
		         			        //For each row, iterate through each columns
		         			        Iterator<Cell> cellIterator = row.cellIterator();
		         			        
		         			        int column = 1;
		         			        
		         			        while(cellIterator.hasNext()) {
		         			             
		         			            Cell cell = cellIterator.next();
		         			            
		         			            String cellVal = "";
		         			             
		         			            switch(cell.getCellType()) {
		         			                case Cell.CELL_TYPE_BOOLEAN:
		         	//		                    System.out.print(cell.getBooleanCellValue() + "\t\t");
		         			                    break;
		         			                case Cell.CELL_TYPE_NUMERIC:
		         	//		                    System.out.print(cell.getNumericCellValue() + "\t\t");
		         			                	cellVal = String.valueOf(cell.getNumericCellValue()).trim().equals("") ? "0" : String.valueOf(cell.getNumericCellValue());
		         			                    break;
		         			                case Cell.CELL_TYPE_STRING:
		         	//		                    System.out.print(cell.getStringCellValue() + "\t\t");
		         			                	cellVal = cell.getStringCellValue();
		         			                    break;
		         			            }
		         			            
		         			            if(column==1) {
		         			            	 if (cellVal.trim().length() > 0) { //第一個column有值，才可以抽取整行的資料
		         	    		            	fetchdata = true;
		         	    		            }
		         			            }
		         			            
		         			            if(fetchdata) {
		         			            	
		         			            	if(column==3) {
		         			            		if (cellVal.trim().equals("CASH CUSTOMER")) {
		         			            			obj.setPayMethod(6); //Freight Charges
		         			            		} else if (cellVal.trim().equals("COD CUSTOMER")) {
		         			            			obj.setPayMethod(5); //COD
		         			            		} else {
		         			            			obj.setPayMethod(4); //Credits
		         			            		}
		//	         	   		            	 System.out.print("sender: "+cellVal + "\t\t");
			         	   		            }
		         			            
			         			            if(column==5) {
			         	   		            	 obj.setSenderName(cellVal);
		//	         	   		            	 System.out.print("sender: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==6) {
			         	   		            	 obj.setReceiverName(cellVal);
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
			         			           if(column==7) {
		         			            		 obj.setGeneralCargoNo(cellVal);
		//	         	   		            	 System.out.print("CN No.: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==8) {
			         			            	int shipmentType = 0;
			         			            	if(cellVal.equals("DOC")) {
			         			            		shipmentType = 1;
			         			            	} else if(cellVal.equals("PCL")) {
			         			            		shipmentType = 2;
			         			            	}
			         	   		            	obj.setShipmentType(shipmentType);
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==9) {
			         			            	String areaname = "";
			         			        	    if (cellVal.equals("SANDAKAN")) {
			         			        	    	areaname = "SDK";
			         			        	    } else if (cellVal.equals("KOTA KINABALU")) {
			         			        	    	areaname = "BKI";
			         			        	    } else if (cellVal.equals("TAWAU")) {
			         			        	    	areaname = "TWU";
			         			        	    }
			         			        	   	int area = AreaBusinessModel.parseAreaAid(areaname);
		         	   		            	 	obj.setReceiverArea(area);
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==11) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setQuantity(Math.round(Float.parseFloat(cellVal)));
				         			           	} else {
				         			           		obj.setQuantity(-1);
				         			           	}	         	   		            	 	
		//	         	   		            	System.out.print("Quantity: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==12) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setWeight(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setWeight(0.0);
				         			           	}		         	   		            	 
		//	         	   		            	 System.out.print("Weight: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==13) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setAmount(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setAmount(0.0);
				         			           	}
		//	         	   		            	System.out.print("Total: "+cellVal + "\t\t");
			         	   		            }
		         	   		            
		         			            }
		         			            column++;
		         			            
		         			        }
		         			       
		         			        if(fetchdata) { //有資料才加到 data object 裡
		         			    	   data.add(obj);
		         			        }
	//	         			        System.out.println("");
		         			    }
		         			}
		         			
	                    } else if (filetype.equals("xlsx")) {
	                    	
	                    	FileInputStream filenew = new FileInputStream(path);
		         			XSSFWorkbook workbook = new XSSFWorkbook(filenew);
		         			XSSFSheet sheet = workbook.getSheetAt(0);
		         			 
		         			//Iterate through each rows from first sheet
		         			Iterator<Row> rowIterator = sheet.iterator();
		         			while(rowIterator.hasNext()) {
		         		        Row row = rowIterator.next();
		         		        
		         		        boolean fetchdata = false;
		         		        
		         		        if( (row.getRowNum()+1) > 4) { //從第5行開始
		         		        	
		         		        	ConsignmentDataModel obj = new ConsignmentDataModel();
		         		         
		         			        //For each row, iterate through each columns
		         			        Iterator<Cell> cellIterator = row.cellIterator();
		         			        
		         			        int column = 1;
		         			        
		         			        while(cellIterator.hasNext()) {
		         			             
		         			            Cell cell = cellIterator.next();
		         			            
		         			            String cellVal = "";
		         			             
		         			            switch(cell.getCellType()) {
		         			                case Cell.CELL_TYPE_BOOLEAN:
		         	//		                    System.out.print(cell.getBooleanCellValue() + "\t\t");
		         			                    break;
		         			                case Cell.CELL_TYPE_NUMERIC:
		         	//		                    System.out.print(cell.getNumericCellValue() + "\t\t");
		         			                	cellVal = String.valueOf(cell.getNumericCellValue()).trim().equals("") ? "0" : String.valueOf(cell.getNumericCellValue());
		         			                    break;
		         			                case Cell.CELL_TYPE_STRING:
		         	//		                    System.out.print(cell.getStringCellValue() + "\t\t");
		         			                	cellVal = cell.getStringCellValue();
		         			                    break;
		         			            }
		         			            
		         			            if(column==1) {
		         			            	 if (cellVal.trim().length() > 0) { //第一個column有值，才可以抽取整行的資料
		         	    		            	fetchdata = true;
		         	    		            }
		         			            }
		         			            
		         			            if(fetchdata) {
		         			            	
		         			            	if(column==3) {
		         			            		if (cellVal.trim().equals("CASH CUSTOMER")) {
		         			            			obj.setPayMethod(6); //Freight Charges
		         			            		} else if (cellVal.trim().equals("COD CUSTOMER")) {
		         			            			obj.setPayMethod(5); //COD
		         			            		} else {
		         			            			obj.setPayMethod(4); //Credits
		         			            		}
		//	         	   		            	 System.out.print("sender: "+cellVal + "\t\t");
			         	   		            }
		         			            
			         			            if(column==5) {
			         	   		            	 obj.setSenderName(cellVal);
		//	         	   		            	 System.out.print("sender: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==6) {
			         	   		            	 obj.setReceiverName(cellVal);
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
		         			            	if(column==7) {
		         			            		obj.setGeneralCargoNo(cellVal);
		//	         	   		            	 System.out.print("CN No.: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==8) {
			         			            	int shipmentType = 0;
			         			            	if(cellVal.equals("DOC")) {
			         			            		shipmentType = 1;
			         			            	} else if(cellVal.equals("PCL")) {
			         			            		shipmentType = 2;
			         			            	}
			         	   		            	obj.setShipmentType(shipmentType);
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==9) {
			         			            	String areaname = "";
			         			        	    if (cellVal.equals("SANDAKAN")) {
			         			        	    	areaname = "SDK";
			         			        	    } else if (cellVal.equals("KOTA KINABALU")) {
			         			        	    	areaname = "BKI";
			         			        	    } else if (cellVal.equals("TAWAU")) {
			         			        	    	areaname = "TWU";
			         			        	    }
			         			        	   	int area = AreaBusinessModel.parseAreaAid(areaname);
		         	   		            	 	obj.setReceiverArea(area);
		//	         	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==11) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setQuantity(Math.round(Float.parseFloat(cellVal)));
				         			           	} else {
				         			           		obj.setQuantity(-1);
				         			           	}	         	   		            	 	
		//	         	   		            	System.out.print("Quantity: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==12) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setWeight(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setWeight(0.0);
				         			           	}		         	   		            	 
		//	         	   		            	 System.out.print("Weight: "+cellVal + "\t\t");
			         	   		            }
			         			            if(column==13) {
			         			            	boolean isNum = isNumeric(cellVal);
			         			            	if(isNum){
			         			            		obj.setAmount(Double.parseDouble(cellVal));
				         			           	} else {
				         			           		obj.setAmount(0.0);
				         			           	}
		//	         	   		            	System.out.print("Total: "+cellVal + "\t\t");
			         	   		            }
		         	   		            
		         			            }
		         			            column++;
		         			            
		         			        }
		         			       
		         			        if(fetchdata) { //有資料才加到 data object 裡
		         			    	   data.add(obj);
		         			        }
	//	         			        System.out.println("");
		         			    }
		         			}
		         			
	                    } else { //不是excel
	                    	
	                    }
	                     
		                    
	         			
	             }
	             
	             
	           
	 			
	         }
	     } catch (FileUploadException e) {
	     		logger.error(e.toString());
	            throw new RuntimeException(e);
	            
	     } catch (Exception e) {
	     		logger.error(e.toString());
	            throw new RuntimeException(e);
	     } finally {

	     }
	     
	     return data;
		
	}
	
	
	/**
	 * 上傳bankslip
	 * 
	 * @param items
	 * @param settlementDT
	 * @param station
	 * @param amount
	 * @param remark
	 * @param tempPath
	 * @param targetPath
	 * @param userId
	 * @return
	 */
	protected static String uploadSlip(List<FileItem> items, String settlementDT, int station, double amount, String remark, String tempPath, String targetPath, String userId) {
		
		String filename = "";
    	
        try {
            
            for (FileItem item : items) {
                if (!item.isFormField()) {
                	File file = null;                	
                    
                	String ext = FilenameUtils.getExtension(item.getName());
                	
                	if(ext.equals("")) {
                		ext = "jpg";
                	}
                	
                	if ( ext.equals("jpg") || ext.equals("png") || ext.equals("gif") ||ext.equals("pdf") ) {
                	
	                	file = new File(tempPath, settlementDT+"."+ext);
	                    item.write(file);
	                	
	                	if(!targetPath.equals("")){
	                		filename = ResourceBusinessModel.uploadSlip (file.getPath(), targetPath);
	                		
	                		AccountBusinessModel.insertSettlement(settlementDT, station, amount, remark, filename, userId); //insert record 到 settlement table
	                	}
	                	
                	} else {
                		filename = "Invalid file type: " + ext;
                	}
                       
                }
            }

            
        } catch (FileUploadException e) {
        	logger.error(" UploadBusinessModel \"uploadSlip\" error " + e.toString());
            throw new RuntimeException(e);
               
        } catch (Exception e) {
        	logger.error(" UploadBusinessModel \"uploadSlip\" error " + e.toString());
            throw new RuntimeException(e);
        }
        
        return filename;

    }
	


	private String getMimeType(File file) {
	     String mimetype = "";
	     if (file.exists()) {
	         if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
	             mimetype = "image/png";
	         } else if(getSuffix(file.getName()).equalsIgnoreCase("jpg")){
	             mimetype = "image/jpg";
	         } else if(getSuffix(file.getName()).equalsIgnoreCase("jpeg")){
	             mimetype = "image/jpeg";
	         } else if(getSuffix(file.getName()).equalsIgnoreCase("gif")){
	             mimetype = "image/gif";
	         } else if(getSuffix(file.getName()).equalsIgnoreCase("pdf")){
	             mimetype = "application/pdf";
	         } else if(getSuffix(file.getName()).equalsIgnoreCase("xls")){
	             mimetype = "application/vnd.ms-excel";
	         } else {
	             javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
	             mimetype  = mtMap.getContentType(file);
	         }
	     }
	     return mimetype;
	}
	
	
	
	private static String getSuffix(String filename) {
	     String suffix = "";
	     int pos = filename.lastIndexOf('.');
	     if (pos > 0 && pos < filename.length() - 1) {
	         suffix = filename.substring(pos + 1);
	     }
	     return suffix;
	}
	
	
	//以正規表達式判斷輸入的字串是否為數字
    public static boolean isNumeric(String str){
    		return str.matches("[-+]?\\d*\\.?\\d+");  
    }
	
	
	
//============================================================================================================================
	
	
	
	
}
