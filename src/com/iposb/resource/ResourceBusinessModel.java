package com.iposb.resource;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.json.simple.JSONValue;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.iposb.sys.ConnectionManager;

public class ResourceBusinessModel {
	
	private ResourceBusinessModel(){
	}
	
	static Logger logger = Logger.getLogger(ResourceBusinessModel.class);
	static String resPath = "https://static.iposb.com";
	private static String BUCKET_NAME = "";
	public static String ACCESS_KEY = "";
    public static String SECRET_KEY = "";
	

    protected static AWSCredentials loadAWSCredentials() throws IOException {
    	
    	Properties prop = new Properties();

        try {
        	
        	prop.load(ConnectionManager.class.getClassLoader().getResourceAsStream("credentials.properties"));
        	BUCKET_NAME = prop.getProperty("bucketname");
        	ACCESS_KEY = prop.getProperty("accesskey");
        	SECRET_KEY = prop.getProperty("secretkey");
        	
        } catch (IOException e) {
			logger.error("### IOException: "+e);
		    e.printStackTrace();
		}
        
        AWSCredentials awsCredentials = new AWSCredentials(ACCESS_KEY, SECRET_KEY);

        return awsCredentials;
    }

	/**
	 * 登入到 AWS
	 */ 
	public static RestS3Service loginAWS() {
	    AWSCredentials awsCredentials;
	    RestS3Service s3Service = null;
		try {
			awsCredentials = loadAWSCredentials();
			s3Service = new RestS3Service(awsCredentials);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (S3ServiceException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	    
	    return s3Service;
    }
	
	

	/**
	 * 列出該資料夾下的所有檔案
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> listFolderFiles(String path) throws Exception {
    	
    	ArrayList<String> allFiles = new ArrayList<String>();
    	RestS3Service s3Service = loginAWS();
    		
    	// List all files under specific directory
    	S3Object[] objects = s3Service.listObjects(BUCKET_NAME, path, "/"); //do not list subfolder
//    	S3Object[] objects = s3Service.listObjects(BUCKET_NAME, path, null); //list all files included subfolder 

        // Put each object's path into ArrayList
        for (int o = 0; o < objects.length; o++) {
        	allFiles.add(objects[o].getKey());
//            logger.info (" " + objects[o].getKey() + " (" + objects[o].getContentLength() + " bytes)");
        }
        
        return allFiles;
    }
	
	/**
	 * 取得 conPics 的照片
	 * @param consignmentNo
	 * @return
	 */
	public static String getConPics (String consignmentNo) {
		
		String jsonText = "";

		try {
			
			Map<String, String> jsonObj = new LinkedHashMap<String, String>();

			for (int i = 1 ; i <= 7 ; i++) {
				
				String path = "conPics/stage"+i+"/"+consignmentNo+"/";
		    	
				ArrayList<String> files = listFolderFiles(path);
				if(files != null && files.size() > 0){
					
					String pics = "";
					
					for(int j = 0; j < files.size(); j++){
//						logger.info (files.get(i)+"\n");
						String filename = new File(files.get(j).toString()).getName();
						pics += " <a href='https://s3-ap-southeast-1.amazonaws.com/iposb/conPics/stage"+i+"/"+consignmentNo+"/"+filename+"' target='_blank'><i class=\"fa fa-photo fa-lg red\"></i></a> ";
						
					}
					jsonObj.put("stage"+i, pics);
					
				} else {
					jsonObj.put("stage"+i, "");
				}

			}
	        
	    	jsonText = JSONValue.toJSONString(jsonObj);
	    	
	    	
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return jsonText;
	}
	
	
	
	/**
	 * 取得 consignment DO 的照片
	 * @param consignmentNo
	 * @return
	 */
	public static String getConDO (String consignmentNo) {
		
		String jsonText = "";

		try {
			
			Map<String, String> jsonObj = new LinkedHashMap<String, String>();

				
				String path = "conDO/"+consignmentNo+"/";
		    	
				ArrayList<String> files = listFolderFiles(path);
				if(files != null && files.size() > 0){
					
					String pics = "";
					
					for(int j = 0; j < files.size(); j++){
//						logger.info (files.get(i)+"\n");
						String filename = new File(files.get(j).toString()).getName();
						pics += " <a href='https://s3-ap-southeast-1.amazonaws.com/iposb/conDO/"+consignmentNo+"/"+filename+"' target='_blank'><i class=\"fa fa-photo fa-lg blue\" title=\"D/O\"></i></a>";
						
					}
					jsonObj.put("DO", pics);
					
				} else {
					jsonObj.put("DO", "");
				}
	        
	    	jsonText = JSONValue.toJSONString(jsonObj);
	    	
	    	
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return jsonText;
	}
	
	
	
	/**
	 * 取得 Delivered 狀態下的 conPics 照片
	 * @param path
	 * @param title
	 * @return
	 */
	public static String getStage7ConPics (String consignmentNo) {
		
		String filename = "";

		try {
		
			String path = "conPics/stage7/"+consignmentNo+"/";
	    	
			ArrayList<String> files = listFolderFiles(path);
			if(files != null && files.size() > 0){
				
				for(int j = 0; j < files.size(); j++){
					String filenm = new File(files.get(j).toString()).getName(); 
					filename = "https://s3-ap-southeast-1.amazonaws.com/iposb/conPics/stage7/"+consignmentNo+"/"+filenm.replaceAll(" ", "%20"); //去除檔名裡所有空白
				}
				
			}
	    	
	    	
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return filename;
	}
	
	
	
	/**
	 * 判斷該圖是否已存在
	 * 
	 * @param path
	 * @return
	 */
	public static String getAnImage (String path) {

		String result = "";
		
		try {
			
			loginAWS();
			
			String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + path; //要用絕對路徑，為了避免有 CloudFront 的 cache
    		
    		HttpURLConnection.setFollowRedirects(false);
    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
    	    con.setRequestMethod("HEAD");
    	    con.connect();
    	    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { //exist
    	    	result += "<img src='"+ URLName +"' width=\"680\" height=\"240\" />";
    	    } else {
    	    	result += "<i>Advertisement Image Not uploaded yet...</i>";
    	    }
    	    
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return result;
	}
	
	
	/**
	 * 上傳檔案到 Amanzon S3
	 * @param srcPath 來源路徑
	 * @param srcPath 目標資料夾
	 * @return
	 */
	public static boolean uploadFile (String srcPath, String targetDir) {

		boolean isSuccess = false;
		
		try {
			RestS3Service s3Service = loginAWS();
			
			File fileData = new File(srcPath);
			
			//先刪除S3上舊的檔案
			deleteFile(targetDir + "/" + fileData.getName());
			
	        S3Object fileObject = new S3Object(fileData);
	        s3Service.putObject(BUCKET_NAME, fileObject);
	        
//	        logger.info("*** Uploaded file to Amazon S3: " + targetDir + "/" + fileData.getName());
	        
	        
	        //Copying file to target directory
	        
	        // Create a target S3Object
	        S3Object targetObject = new S3Object(targetDir + "/" + fileData.getName());
	        
	        //move & rename
	        s3Service.renameObject(BUCKET_NAME, fileData.getName(), targetObject);
	        
	        
	        // Set a bucket policy that allows public read access to all objects under the virtual path "targetDir"
//	        String bucketNameForPolicy = BUCKET_NAME;
//	        String policyJSON =
//	            "{"
//	        	+ "\"Version\":\"2012-10-17\""
//	            + ",\"Id\":\"\""
//	            + ",\"Statement\": [{"
//	            	+ "\"Sid\": \"AddPerm\""
//	                + ",\"Effect\":\"Allow\""
//	                + ",\"Action\":\"s3:GetObject*\""
//	                + ",\"Principal\":{\"AWS\": \"*\"}"
//	                + ",\"Resource\":\"arn:aws:s3:::" + bucketNameForPolicy + "/"+targetDir+"/*\""
//	            + "}]}";
//	        s3Service.setBucketPolicy(bucketNameForPolicy, policyJSON);
	        
	        //Refer from: https://stackoverflow.com/questions/6524041/how-do-you-make-an-s3-object-public-via-the-aws-java-sdk
	        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY); 
	        AmazonS3 s3 = new AmazonS3Client(basicAWSCredentials);

            PutObjectRequest putObj = new PutObjectRequest(BUCKET_NAME, targetDir + "/" + fileData.getName(), fileData);

            //making the object Public
            putObj.setCannedAcl(CannedAccessControlList.PublicRead);

            //set metadata
	        ObjectMetadata metadata = new ObjectMetadata();
//	        metadata.setCacheControl("max-age=86400"); //24 hours
	        metadata.setCacheControl("max-age=2592000"); //30days (60sec * 60min * 24hours * 30days)
	        putObj.setMetadata(metadata);

            s3.putObject(putObj);
            
            
	        
	        //再檢查一下該檔案是否真的存在
			String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + targetObject; //要用絕對路徑，為了避免有 CloudFront 的 cache
    		HttpURLConnection.setFollowRedirects(false);
    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
    	    con.setRequestMethod("HEAD");
    	    con.connect();

    	    if (con.getResponseCode() != HttpURLConnection.HTTP_OK) { //not exist
    	    	isSuccess = true;
    	    	logger.info("*** Upload Sucess: " + targetDir + "/" + fileData.getName());
    	    } else {
    	    	logger.error("*** Upload Failed: " + targetDir + "/" + fileData.getName());
    	    }

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return isSuccess;
	}
	
	
	/**
	 * 刪除 Amazon S3 上的檔案
	 * @param filename  bucket名下的全路徑，不包含最前面的 / 符號。 例如：pdf/tour-XXXXXX-sabahbooking.pdf 或 resources/common/assets/js/autocomplete-bkihotel.js
	 * @return
	 */
	public static boolean deleteFile (String filename) {
		
		boolean isSuccess = false;
		
		try {
			RestS3Service s3Service = loginAWS();
			
			s3Service.deleteObject(BUCKET_NAME, filename);
//			logger.info("*** Deleted file from Amazon S3: " + filename);
			
			//再檢查一下該檔案是否還存在
			String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + filename; //要用絕對路徑，為了避免有 CloudFront 的 cache
    		HttpURLConnection.setFollowRedirects(false);
    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
    	    con.setRequestMethod("HEAD");
    	    con.connect();

    	    if (con.getResponseCode() != HttpURLConnection.HTTP_OK) { //not exist
    	    	isSuccess = true;
    	    }
			
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return isSuccess;
		
	}
	
	
	/**
	 * 檢查該Consignment Note 的 PDF是否存在
	 * 
	 * @param bookingType
	 * @param bookingCodeStream
	 * @return
	 */
	public static String checkConsignmentnote (String consignmentNoStream) {

		String jsonText = "";
		
		try {
			
			loginAWS();
			
			Map<String, Integer> jsonObj = new LinkedHashMap<String, Integer>();
	    	String [] num = null;
	        num = consignmentNoStream.split("-");
	        for (int i = 0 ; i < num.length ; i++) {
	    		String consignmentNo = num[i];
	    		String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + "pdf/" + consignmentNo + "-IPOSB.pdf"; //要用真實路徑，為了避免有 CloudFront 的 cache
	    		
	    		HttpURLConnection.setFollowRedirects(false);
	    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
	    	    con.setRequestMethod("HEAD");
	    	    con.connect();
//	    	    int code = con.getResponseCode() ;
//	    	    logger.info (code);  注意：在 localhost 測試時，返回的code 是 301，要放在正式主機上才會是 200
	    	    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { //exist: 200
	    	    	jsonObj.put("cd_"+consignmentNo, 1);
	    	    } else {
	    	    	jsonObj.put("cd_"+consignmentNo, -1);
	    	    }
	        }
	        
	    	jsonText = JSONValue.toJSONString(jsonObj);
	    	
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return jsonText;
	}
	
	
	/**
	 * 檢查該TAX INVOICE是否存在
	 * 
	 * @param bookingType
	 * @param bookingCodeStream
	 * @return
	 */
	public static String checkInvoice (String bookingCodeStream) {

		String jsonText = "";
		
		try {
			
			loginAWS();
			
			Map<String, Integer> jsonObj = new LinkedHashMap<String, Integer>();
	    	String [] num = null;
	        num = bookingCodeStream.split(";");
	        for (int i = 0 ; i < num.length ; i++) {
	    		String InvoiceNoBookingCode = num[i];
	    		String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + "invoice/" +InvoiceNoBookingCode + "-SabahBooking.pdf"; //要用絕對路徑，為了避免有 CloudFront 的 cache
	    		
	    		HttpURLConnection.setFollowRedirects(false);
	    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
	    	    con.setRequestMethod("HEAD");
	    	    con.connect();

//	    	    int code = con.getResponseCode() ;
//	    	    logger.info (code);  注意：在 localhost 測試時，返回的code 是 301，要放在正式主機上才會是 200
	    	    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { //exist: 200
	    	    	if(InvoiceNoBookingCode.length() >= 15) {
	    	    		jsonObj.put("cd_"+InvoiceNoBookingCode.substring(9, 15), 1);
	    	    	}
	    	    } else {
	    	    	if(InvoiceNoBookingCode.length() >= 15) {
	    	    		jsonObj.put("cd_"+InvoiceNoBookingCode.substring(9, 15), -1);
	    	    	}
	    	    }
	        }
	        
	    	jsonText = JSONValue.toJSONString(jsonObj);
	    	
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return jsonText;
	}
	
	
	
	/**
	 * 檢查該每個 stage 的 conPic 是否存在
	 * 
	 * @param consignmentNo
	 * @return
	 */
	/*
	public static String checkConPics (String consignmentNo) {

		String jsonText = "";
		
		try {
			
			loginAWS();
			
			Map<String, Integer> jsonObj = new LinkedHashMap<String, Integer>();

			for (int i = 1 ; i <= 7 ; i++) {
	    		String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/conPics/stage"+i+"/" +consignmentNo + ".jpg"; //要用絕對路徑，為了避免有 CloudFront 的 cache
	
	    		HttpURLConnection.setFollowRedirects(false);
	    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
	    	    con.setRequestMethod("HEAD");
	    	    con.connect();
	
//	    	    int code = con.getResponseCode() ;
//	    	    logger.info (code);  注意：在 localhost 測試時，返回的code 是 301，要放在正式主機上才會是 200
	    	    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { //exist: 200
	    	    	jsonObj.put("stage"+i, 1);
	    	    } else {
	    	    	jsonObj.put("stage"+i, -1);
	    	    }
			}
	        
	    	jsonText = JSONValue.toJSONString(jsonObj);
	    	
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return jsonText;
	}
	*/
	
	
	public static boolean uploadImage (String srcPath, String targetDir) {
		boolean isSuccess = false;
		File fileData  = null;
		try {
			
			RestS3Service s3Service = loginAWS();
			fileData  = new File(srcPath);
			
			S3Object fileObject = new S3Object(fileData);
			
			//先刪除S3上舊的檔案
//			deleteFile(targetDir + "/" + link);
//			
//	         
//	        System.out.println(">>>>> " + fileObject);
//	        
	        s3Service.putObject(BUCKET_NAME, fileObject);
	        logger.info("*** Uploaded file to Amazon S3: " + targetDir + "/" + fileData.getName());
	        
	        //Copying file to target directory
	        
	        // Create a target S3Object
	        S3Object targetObject = new S3Object(targetDir + "/" + fileData.getName());
	        
	        //move & rename
	        
	        s3Service.renameObject(BUCKET_NAME,fileData.getName(), targetObject);
	        
	        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY); 
	        AmazonS3 s3 = new AmazonS3Client(basicAWSCredentials);

	        PutObjectRequest putObj = new PutObjectRequest(BUCKET_NAME, targetDir + "/" + fileData.getName(), fileData);

	        //making the object Public
	        putObj.setCannedAcl(CannedAccessControlList.PublicRead);
	        
	        //set metadata
	        ObjectMetadata metadata = new ObjectMetadata();
//	        metadata.setCacheControl("max-age=86400"); //24 hours
	        metadata.setCacheControl("max-age=2592000"); //30days (60sec * 60min * 24hours * 30days)
	        putObj.setMetadata(metadata);

	        s3.putObject(putObj);
	       
	        //再檢查一下該檔案是否真的存在
			String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + targetObject; //要用絕對路徑，為了避免有 CloudFront 的 cache
    		HttpURLConnection.setFollowRedirects(false);
    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
    	    con.setRequestMethod("HEAD");
    	    con.connect();
    	    
//    	    System.out.println("URLName >>>>> "+"https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + targetObject.getName());
    	    String imageUrl = "https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + targetObject.getName();
//    	    System.out.println("imagerUrl >>> " + imageUrl);
    	    if (con.getResponseCode() != HttpURLConnection.HTTP_OK) { //not exist
    	    	isSuccess = true;
    	    }

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return isSuccess;
	}

	
	
	public static String uploadSlip (String srcPath, String targetDir) {
		String isSuccess = "";
		File fileData  = null;
		try {
			
			RestS3Service s3Service = loginAWS();
			fileData  = new File(srcPath);
			
			S3Object fileObject = new S3Object(fileData);
			
			//先刪除S3上舊的檔案
//			deleteFile(targetDir + "/" + link);
//			
//	         
//	        System.out.println(">>>>> " + fileObject);
//	        
	        s3Service.putObject(BUCKET_NAME, fileObject);
	        logger.info("*** Uploaded file to Amazon S3: " + targetDir + "/" + fileData.getName());
	        
	        //Copying file to target directory
	        
	        // Create a target S3Object
	        S3Object targetObject = new S3Object(targetDir + "/" + fileData.getName());
	        
	        //move & rename
	        
	        s3Service.renameObject(BUCKET_NAME,fileData.getName(), targetObject);
	        
	        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY); 
	        AmazonS3 s3 = new AmazonS3Client(basicAWSCredentials);

	        PutObjectRequest putObj = new PutObjectRequest(BUCKET_NAME, targetDir + "/" + fileData.getName(), fileData);

	        //making the object Public
	        putObj.setCannedAcl(CannedAccessControlList.PublicRead);
	        
	        //set metadata
	        ObjectMetadata metadata = new ObjectMetadata();
//	        metadata.setCacheControl("max-age=86400"); //24 hours
	        metadata.setCacheControl("max-age=2592000"); //30days (60sec * 60min * 24hours * 30days)
	        putObj.setMetadata(metadata);

	        s3.putObject(putObj);
	       
	        //再檢查一下該檔案是否真的存在
			String URLName ="https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + targetObject; //要用絕對路徑，為了避免有 CloudFront 的 cache
    		HttpURLConnection.setFollowRedirects(false);
    	    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
    	    con.setRequestMethod("HEAD");
    	    con.connect();
    	    
//    	    System.out.println("URLName >>>>> "+"https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + targetObject.getName());
    	    String imageUrl = "https://s3-ap-southeast-1.amazonaws.com/" + BUCKET_NAME + "/" + targetObject.getName();
//    	    System.out.println("imagerUrl >>> " + imageUrl);
    	    if (con.getResponseCode() != HttpURLConnection.HTTP_OK) { //not exist
    	    	isSuccess = fileData.getName();
    	    }

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return isSuccess;
	}
	
	
}