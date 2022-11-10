package com.iposb.my;

public class MyDataModel {
	
	//address book
	private int aid = 0;
	private int mid = 0;
	private String receiverName = "";
	private String receiverAttn = "";
	private String receiverAddress1 = "";
	private String receiverAddress2 = "";
	private String receiverAddress3 = "";
	private String receiverPostcode = "";
	private String receiverPhone = "";
	private int receiverZone = 0;
	private int receiverArea = 0;
	private String receiverCountry = "";
	private String verify = "";
	
	private String userId = "";
	private String errmsg= "";
	
	//credit account
	private int cid = 0;
	private String contactName = "";
	private String contactPhone = "";
	private String contactAddress = "";
	private String createDT = "";
	private String modifyDT = "";
	
	
	
	public MyDataModel(){
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress1() {
		return receiverAddress1;
	}

	public void setReceiverAddress1(String receiverAddress1) {
		this.receiverAddress1 = receiverAddress1;
	}

	public String getReceiverAddress2() {
		return receiverAddress2;
	}

	public void setReceiverAddress2(String receiverAddress2) {
		this.receiverAddress2 = receiverAddress2;
	}

	public String getReceiverAddress3() {
		return receiverAddress3;
	}

	public void setReceiverAddress3(String receiverAddress3) {
		this.receiverAddress3 = receiverAddress3;
	}

	public String getReceiverPostcode() {
		return receiverPostcode;
	}

	public void setReceiverPostcode(String receiverPostcode) {
		this.receiverPostcode = receiverPostcode;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public int getReceiverZone() {
		return receiverZone;
	}

	public void setReceiverZone(int receiverZone) {
		this.receiverZone = receiverZone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getReceiverArea() {
		return receiverArea;
	}

	public void setReceiverArea(int receiverArea) {
		this.receiverArea = receiverArea;
	}

	public String getReceiverCountry() {
		return receiverCountry;
	}

	public void setReceiverCountry(String receiverCountry) {
		this.receiverCountry = receiverCountry;
	}

	public String getReceiverAttn() {
		return receiverAttn;
	}

	public void setReceiverAttn(String receiverAttn) {
		this.receiverAttn = receiverAttn;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}

	public String getModifyDT() {
		return modifyDT;
	}

	public void setModifyDT(String modifyDT) {
		this.modifyDT = modifyDT;
	}

	


}