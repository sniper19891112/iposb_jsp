package com.iposb.area;

public class AreaDataModel {
	
	private int aid = 0;
	private String name_enUS = "";
	private String name_zhCN = "";
	private String name_zhTW = "";
	private String code = "";
	
//	private int freightType = 0;
	private String cutoff = "";
	
	private int isMajor = 0;
	private int belongArea = 0;
	private String state = "";
	private int isShow = 1;
	private String createDT = "";
	private String creator = "";
	private String modifyDT = "";
	private String modifier = "";
	private String errmsg = "";
	private int total = 0;
	
	//zone
	private int zid = 0;
	private int serial = 0;
	private String areaName = "";
	private String areaCode = "";
	
	//settlement
	private int station = 0;

	public AreaDataModel(){
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getName_enUS() {
		return name_enUS;
	}

	public void setName_enUS(String name_enUS) {
		this.name_enUS = name_enUS;
	}

	public String getName_zhCN() {
		return name_zhCN;
	}

	public void setName_zhCN(String name_zhCN) {
		this.name_zhCN = name_zhCN;
	}

	public String getName_zhTW() {
		return name_zhTW;
	}

	public void setName_zhTW(String name_zhTW) {
		this.name_zhTW = name_zhTW;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifyDT() {
		return modifyDT;
	}

	public void setModifyDT(String modifyDT) {
		this.modifyDT = modifyDT;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getZid() {
		return zid;
	}

	public void setZid(int zid) {
		this.zid = zid;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

//	public int getFreightType() {
//		return freightType;
//	}
//
//	public void setFreightType(int freightType) {
//		this.freightType = freightType;
//	}

	public String getCutoff() {
		return cutoff;
	}

	public void setCutoff(String cutoff) {
		this.cutoff = cutoff;
	}

	public int getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(int isMajor) {
		this.isMajor = isMajor;
	}

	public int getBelongArea() {
		return belongArea;
	}

	public void setBelongArea(int belongArea) {
		this.belongArea = belongArea;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
}