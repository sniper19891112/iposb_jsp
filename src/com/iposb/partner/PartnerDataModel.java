package com.iposb.partner;

public class PartnerDataModel {
	
	//To be Partner
	private int category = 0;
	private String companyName = "";
	private String contactPerson = "";
	private String contactNumber = "";
	private String contactEmail = "";
	private String message = "";
	private String requestDT = "";
	private String IP= "";
	
	//Partnerlist
	private int pid = 0;
	private String userId = "";
	private String userId_Current = "";
	private String passwd = "";
	private String passwd2 = "";
	private String passwdOld = "";
	private String passwdNew = "";
	private String email = "";
	private String email_Current = "";
	private String ename = "";
	private String cname = "";
	private String website = "";
	private String officialEmail = "";
	private String phone = "";
	private String address = "";
	private String companyLicense = "";
	private String gst = "";
	private String companyDesc_enUS = "";
	private String companyDesc_zhCN = "";
	private String companyDesc_zhTW = "";
	private int privilege = 3;
	private int pricingPolicy = 0;
	private String createDT = "";
	private String modifyDT = "";
	private String registerIP = "";
	private String lastIP = "";
	private String lastLoginDT = "";
	private int loginTimes = 1;
	
	//AREA
	private int aid = 0;
	private String areaName = "";
	
	
	private String errmsg = "";
	private int total = 0;

	public PartnerDataModel(){
	}

	
	
	public int getLoginTimes() {
		return loginTimes;
	}



	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}



	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestDT() {
		return requestDT;
	}

	public void setRequestDT(String requestDT) {
		this.requestDT = requestDT;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyLicense() {
		return companyLicense;
	}

	public void setCompanyLicense(String companyLicense) {
		this.companyLicense = companyLicense;
	}
	
	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getCompanyDesc_enUS() {
		return companyDesc_enUS;
	}

	public void setCompanyDesc_enUS(String companyDesc_enUS) {
		this.companyDesc_enUS = companyDesc_enUS;
	}

	public String getCompanyDesc_zhCN() {
		return companyDesc_zhCN;
	}

	public void setCompanyDesc_zhCN(String companyDesc_zhCN) {
		this.companyDesc_zhCN = companyDesc_zhCN;
	}

	public String getCompanyDesc_zhTW() {
		return companyDesc_zhTW;
	}

	public void setCompanyDesc_zhTW(String companyDesc_zhTW) {
		this.companyDesc_zhTW = companyDesc_zhTW;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public String getPasswd2() {
		return passwd2;
	}

	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}

	public String getPasswdOld() {
		return passwdOld;
	}

	public void setPasswdOld(String passwdOld) {
		this.passwdOld = passwdOld;
	}

	public String getPasswdNew() {
		return passwdNew;
	}

	public void setPasswdNew(String passwdNew) {
		this.passwdNew = passwdNew;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getRegisterIP() {
		return registerIP;
	}

	public void setRegisterIP(String registerIP) {
		this.registerIP = registerIP;
	}

	public String getLastIP() {
		return lastIP;
	}

	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}

	public String getLastLoginDT() {
		return lastLoginDT;
	}

	public void setLastLoginDT(String lastLoginDT) {
		this.lastLoginDT = lastLoginDT;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}


	public String getUserId_Current() {
		return userId_Current;
	}



	public void setUserId_Current(String userId_Current) {
		this.userId_Current = userId_Current;
	}



	public String getEmail_Current() {
		return email_Current;
	}



	public void setEmail_Current(String email_Current) {
		this.email_Current = email_Current;
	}



	public int getPricingPolicy() {
		return pricingPolicy;
	}



	public void setPricingPolicy(int pricingPolicy) {
		this.pricingPolicy = pricingPolicy;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}