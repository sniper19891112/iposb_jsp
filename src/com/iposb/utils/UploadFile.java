package com.iposb.utils;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Icon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iposb.consignment.ConsignmentDataModel;
import com.iposb.resource.ResourceBusinessModel;


public class UploadFile extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(UploadFile.class);



	/**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * 
     */
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 	boolean isUploaded = false;
	 	boolean createThumb = false;
	 	PrintWriter out = response.getWriter();
//	 	long time = System.currentTimeMillis();
	 	String targetDir = request.getParameter("targetDir") == null ? "" : request.getParameter("targetDir").toString();
	 	String value1 = request.getParameter("value1") == null ? "" : request.getParameter("value1").toString();
		

	 	
	     if (!ServletFileUpload.isMultipartContent(request)) {
	    	 logger.error("Request is not multipart, please 'multipart/form-data' enctype for your form.");
	         throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
	     }
	     
	     
	     ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	     try {
	    	 	 
	         List<FileItem> items = uploadHandler.parseRequest(request);
	         for (FileItem item : items) {
	             if (!item.isFormField()) {
	             		
//	                     File file = new File(request.getSession().getServletContext().getRealPath("/")+"temp/", time +"_"+ item.getName());
	            	 
	            	 	if(item.getName().isEmpty()){
	            	 		out.println("no file");
	            	 		return;
	            	 	}
	            	 	
	            	 	String path = request.getSession().getServletContext().getRealPath("/")+"temp" + File.separator + item.getName();
	            	 
	            	 	 File file = new File(path);
//	            	 	 logger.info("Current Path: "+path);
	                     //String filetype = getMimeType(file);
	            	 	 String filetype = getSuffix(file.getName());

	                     item.write(file);
	                     
	                     if(!targetDir.isEmpty()){
	                     	
	                     	String srcPath = request.getSession().getServletContext().getRealPath("/")+"temp" + File.separator + file.getName();
	                        isUploaded = ResourceBusinessModel.uploadFile(srcPath, targetDir); //upload to Amazon S3
	             			if(isUploaded) {
	             				out.println("success");
	             			} else {
	             				out.println("failed");
	             			}
	             			
	                     	
	                     	//如果是圖檔
	                     	if(filetype.equals("png") || filetype.equals("jpg") || filetype.equals("jpeg") || filetype.equals("gif")) {
	                     		
	                     		if(createThumb) { //如果要縮圖
	                     	
		                     		BufferedImage bi = ImageIO.read(file);
			                        double wRatio = (new Integer(218)).doubleValue() / bi.getWidth(); //宽度的比例  
			                        double hRatio = (new Integer(330)).doubleValue() / bi.getHeight(); //高度的比例  
			                           
			                        Image image = bi.getScaledInstance(220,220,Image.SCALE_SMOOTH); //设置图像的缩放大小  
			                           
			                        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio,hRatio),null);   //设置图像的缩放比例  
			                           
			                        image = op.filter(bi,null);  
			                        String filePath = request.getSession().getServletContext().getRealPath("/")+"temp";
			                        File zoomFile = new File(filePath + file.getName());  
			                           
			                        Icon ret = null;  
			                        try {  
			                        	ImageIO.write((BufferedImage)image, filetype, zoomFile);   
		//	                        	ret = new ImageIcon(zoomFile.getPath());   
			                        } catch (Exception e) {  
			                        	logger.error(e.toString());
			                        	e.printStackTrace();  
			                        }  
			                        
			                        srcPath = request.getSession().getServletContext().getRealPath("/")+"temp/" + zoomFile.getName();		                         
			                        targetDir = targetDir + "/thumb";
			                        isUploaded = ResourceBusinessModel.uploadFile(srcPath, targetDir); //upload to Amazon S3
			             			if(isUploaded) {
			             				out.println("success");
			             			} else {
			                         	out.print("failed");
			             			}
	                     		}
		             			
	                     	}
	                     	
	                     	
	                     } else { //不是上傳到 AWS

	                    	 if (filetype.equals("xls")) { //excel
	                    		 uploadExcel(path);
		                     } // end filetype.equals("xls")
	                    	 
	                 	} //end
	             }
	             
	             
	           
	 			
	         }
	     } catch (FileUploadException e) {
	     		logger.error(e.toString());
	            throw new RuntimeException(e);
	            
	     } catch (Exception e) {
	     		logger.error(e.toString());
	            throw new RuntimeException(e);
	     } finally {
	     	 out.print(isUploaded);
				 out.flush();
				 out.close();
	     }
	
	}
	
	/**
	 * 解析 General Cargo
	 * 
	 * @param path
	 * @return
	 * @throws FileUploadException
	 */
	private static ArrayList<ConsignmentDataModel> uploadExcel(String path) throws FileUploadException {

		try {
			ArrayList<ConsignmentDataModel> data = new ArrayList<ConsignmentDataModel>();
			 
			FileInputStream filenew = new FileInputStream(path);
			HSSFWorkbook workbook = new HSSFWorkbook(filenew);
			HSSFSheet sheet = workbook.getSheetAt(2);
			 
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
	   		            	 obj.setSenderName(cellVal);
	   		            	 System.out.print("CN No.: "+cellVal + "\t\t");
	   		            }
			            	if(column==2) {
	   		            	 obj.setSenderName(cellVal);
	   		            	 System.out.print("sender: "+cellVal + "\t\t");
	   		            }
			            	if(column==3) {
	   		            	 obj.setReceiverName(cellVal);
	   		            	 System.out.print("Receiver: "+cellVal + "\t\t");
	   		            }
			            	if(column==4) {
	//   		            	 obj.setArea(cellVal);
	   		            	 System.out.print("Area: "+cellVal + "\t\t");
	   		            }
			            	if(column==5) {
	//   		            	 obj.setQuantity(Integer.parseInt(cellVal));
	   		            	 System.out.print("Quantity: "+cellVal + "\t\t");
	   		            }
			            	if(column==6) {
	   		            	 obj.setWeight(Double.parseDouble(cellVal));
	   		            	 System.out.print("Weight: "+cellVal + "\t\t");
	   		            }
			            	if(column==7) {
	//   		            	 obj.setTerm(cellVal);
	   		            	 System.out.print("Term: "+cellVal + "\t\t");
	   		            }
			            	if(column==10) {
	//   		            	 obj.setAmount(Double.parseDouble(cellVal));
	   		            	 System.out.print("Total: "+cellVal + "\t\t");
	   		            }
	   		            
			            }
			            column++;
			            
			        }
			        data.add(obj);
			        System.out.println("");
			    }
			}
	
			 return data;
		 
		} catch (Exception e) {
     		logger.error(e.toString());
            throw new RuntimeException(e);
		} finally {
			
		}
		
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
	
	
	
	private String getSuffix(String filename) {
	     String suffix = "";
	     int pos = filename.lastIndexOf('.');
	     if (pos > 0 && pos < filename.length() - 1) {
	         suffix = filename.substring(pos + 1);
	     }
	     return suffix;
	}

}
