package com.iposb.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;


public class UtilsBusinessModel {
	
	private static int SERVERTIME = 0; //0: MY Server  8: HK Server

	static Logger logger = Logger.getLogger(UtilsBusinessModel.class);
	
	
	private UtilsBusinessModel(){
	}
	

	/**
	 * 取得系統現在的時間
	 * @return
	 */
	public static String timeNow() {
	    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		
		Date myDate = new java.util.Date();
		long myTime = (myDate.getTime()/1000) + (60 * 60 * SERVERTIME); //Server 時間
		myDate.setTime(myTime*1000);
		String mDate = sdf.format(myDate);
		return mDate; 
	}
	
	
	/**
	 * 取得今天的日期 yyyy-MM-dd 
	 * @return
	 */
	public static String todayDate() {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		
		Date myDate = new java.util.Date();
		long myTime = (myDate.getTime()/1000) + (60 * 60 * SERVERTIME); //Server 時間
		myDate.setTime(myTime*1000);
		String mDate = sdf.format(myDate);
		return mDate; 
	}

	
	
	/**
	 * 計算 x 天後的日期
	 * 參考自：https://www.rgagnon.com/javadetails/java-0101.html
	 * @param x, 可以為正或負值
	 * @return
	 */
	public static String dateAdd(String date, int x) {
		
		String newDate = "";
		if(date != null && date.length() > 0){
			
			String DATE_FORMAT = "yyyy-MM-dd";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
			Calendar c1 = Calendar.getInstance(); 
			// roll down the month
			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(5, 7)) - 1;
			int day = Integer.parseInt(date.substring(8, 10));
			c1.set(year, month , day); // 2010-06-28 => 2010,6,28
			c1.add(Calendar.DATE, x);
			newDate = sdf.format(c1.getTime());
		}
		
		return newDate;
	}
	
	
	/**
	 * 目標日期與系統日期相差的天數
	 * @param date
	 * @return 負值：未到（還有x天）  正值：已過x天
	 * 參考自：https://www.kodejava.org/examples/90.html
	 */
	public static long compareDate(String dt) {
		
		long diffDays = 0;
		if(dt != null && dt.length() >= 10){

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance(); 
			// roll down the month
			int year1 = Integer.parseInt(dt.substring(0, 4));
			int month1 = Integer.parseInt(dt.substring(5, 7)) - 1;
			int day1 = Integer.parseInt(dt.substring(8, 10));
			c1.set(year1, month1 , day1); // 2010-06-28 => 2010,6,28
			
			String nowDt = todayDate();
			int year2 = Integer.parseInt(nowDt.substring(0, 4));
			int month2 = Integer.parseInt(nowDt.substring(5, 7)) - 1;
			int day2 = Integer.parseInt(nowDt.substring(8, 10));
			c2.set(year2, month2 , day2);
			
			// Get the represented date in milliseconds
			long milis1 = c1.getTimeInMillis();
			long milis2 = c2.getTimeInMillis();
			
			// Calculate difference in milliseconds
			long diff = milis2 - milis1;
			
			// Calculate difference in days
			diffDays = diff / (24 * 60 * 60 * 1000);
		}

		return diffDays;
	}
	
	
	
	/**
	 * 隨機產生的碼
	 * @param len 長度
	 * @return
	 * 參考自：https://bright-green.com/blog/2005_04_05/generating_a_random_string.html
	 */
	public static String randomCode(int len) {
		
		StringBuffer sb = new StringBuffer();
		Random rnd = new Random();
		int te = 0;

		String str = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ23456789ABCDEFGHJKLMNPQRSTUVWXYZ23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
		
		for(int i=0; i<len; i++){
	 		te = rnd.nextInt(18);
	 		sb.append(str.charAt(te));
	 	}
		
		return sb.toString();
	}

	
	/**
	 * 删除input字符串中的html格式
	 * 參考自：https://www.javaeye.com/topic/217508
	 * @param input
	 * @param length
	 * @return
	 */
	public static String removeHTML(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");

		return str;
	}
	
}