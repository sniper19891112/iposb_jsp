package com.iposb.utils;

public class UtilsDataModel {
	
	//weather
	private String area = "";
	private int wid = -1;
	private String day = "";
	private String month = "";
	private String weekday = "";
	private String hc = "";
	private String lc = "";
	private String icon = "";
	private String pop = "";
	private String modifyDT = "";
	
	
	private String forex = "";
	private String errmsg= "";
	
	public UtilsDataModel(){
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getForex() {
		return forex;
	}

	public void setForex(String forex) {
		this.forex = forex;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHc() {
		return hc;
	}

	public void setHc(String hc) {
		this.hc = hc;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPop() {
		return pop;
	}

	public void setPop(String pop) {
		this.pop = pop;
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public String getModifyDT() {
		return modifyDT;
	}

	public void setModifyDT(String modifyDT) {
		this.modifyDT = modifyDT;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}



}