package com.iposb.pickup;

public class PickupDataModel {
	
	private int pid = 0;
	private String driver = "";
	private String driverName = "";
	private String agent = "";
	private int status = 0;
	private int isMonitored = 0;
	private String createDT = "";
	private String assigner = "";
	private String assignDT = "";
	private String deliveryDT = "";
	private String errmsg = "";
	private int total = 0;
	
	// Zone/Area
	private String zoneName_enUS = "";
	private String areaName_enUS = "";

	// Consignment
	private String consignmentNo = "";
	private String consignmentDT = ""; // Consignment's CreateDT
	private String senderName = "";
	private String senderID = "";
	private String senderAddress1 = "";
	private String senderAddress2 = "";
	private String senderAddress3 = "";
	private String senderPhone = "";
	
	
	
	public int getPid() {
		return pid;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsMonitored() {
		return isMonitored;
	}

	public void setIsMonitored(int isMonitored) {
		this.isMonitored = isMonitored;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getAssigner() {
		return assigner;
	}

	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}

	public String getAssignDT() {
		return assignDT;
	}

	public void setAssignDT(String assignDT) {
		this.assignDT = assignDT;
	}

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
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

	public String getConsignmentDT() {
		return consignmentDT;
	}

	public void setConsignmentDT(String consignmentDT) {
		this.consignmentDT = consignmentDT;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getSenderAddress1() {
		return senderAddress1;
	}

	public void setSenderAddress1(String senderAddress1) {
		this.senderAddress1 = senderAddress1;
	}

	public String getSenderAddress2() {
		return senderAddress2;
	}

	public void setSenderAddress2(String senderAddress2) {
		this.senderAddress2 = senderAddress2;
	}

	public String getSenderAddress3() {
		return senderAddress3;
	}

	public void setSenderAddress3(String senderAddress3) {
		this.senderAddress3 = senderAddress3;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getZoneName_enUS() {
		return zoneName_enUS;
	}

	public void setZoneName_enUS(String zoneName_enUS) {
		this.zoneName_enUS = zoneName_enUS;
	}

	public String getAreaName_enUS() {
		return areaName_enUS;
	}

	public void setAreaName_enUS(String areaName_enUS) {
		this.areaName_enUS = areaName_enUS;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getDeliveryDT() {
		return deliveryDT;
	}

	public void setDeliveryDT(String deliveryDT) {
		this.deliveryDT = deliveryDT;
	}
	
}