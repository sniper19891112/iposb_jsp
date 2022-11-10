package com.iposb.logon;

public class LogonDataModel {
	
	//memberlist
	private int mid = 0;
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
	private int gender = 0;
	private String nationality = "";
	private String countryCode = "";
	private String phone = "";
	private String dob = "";
	private String IC = "";
	private String fbid = "";
	private int newsletter = 0;
	private String verify = "";
	private int privilege = 0;
	private String accNo = "";
	private String discPack = "";
	private int stage1 = 0;
	private int stage2 = 0;
	private int stage3 = 0;
	private int stage4 = 0;
	private int stage5 = 0;
	private int stage6 = 0;
	private int stage7 = 0;
	private String createDT = "";
	private String modifyDT = "";
	private String registerIP = "";
	private String lastIP = "";
	private String lastLoginDT = "";
	private int total = 0;
	private String errmsg= "";
	private int totalBooking = 0;
	private int loginTimes = 0;
	
	//partnerlist
	private int pid = 0;
	private int cid = 0;
	private int aid = 0;
	private String contactPerson = "";
	private String website = "";
	private String officialEmail = "";
	private String address = "";
	private String companyLicense = "";
	private String mocatLicense = "";
	private String gst = "";
	
	//stafflist
	private int sid = 0;
	
	//other
	private String areaName = "";
	


	public LogonDataModel(){
	}

	
	


	public int getLoginTimes() {
		return loginTimes;
	}


	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}


	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
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

	public String getPasswd2() {
		return passwd2;
	}

	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getELastName() {
//		return eLastName;
//	}
//
//	public void setELastName(String lastName) {
//		eLastName = lastName;
//	}
//
//	public String getEFirstName() {
//		return eFirstName;
//	}
//
//	public void setEFirstName(String firstName) {
//		eFirstName = firstName;
//	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
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

	public int getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(int newsletter) {
		this.newsletter = newsletter;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	public int getTotalBooking() {
		return totalBooking;
	}


	public void setTotalBooking(int totalBooking) {
		this.totalBooking = totalBooking;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
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
	

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
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

	public String getMocatLicense() {
		return mocatLicense;
	}

	public void setMocatLicense(String mocatLicense) {
		this.mocatLicense = mocatLicense;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public int getStage1() {
		return stage1;
	}

	public void setStage1(int stage1) {
		this.stage1 = stage1;
	}

	public int getStage2() {
		return stage2;
	}

	public void setStage2(int stage2) {
		this.stage2 = stage2;
	}

	public int getStage3() {
		return stage3;
	}

	public void setStage3(int stage3) {
		this.stage3 = stage3;
	}

	public int getStage4() {
		return stage4;
	}

	public void setStage4(int stage4) {
		this.stage4 = stage4;
	}

	public int getStage5() {
		return stage5;
	}

	public void setStage5(int stage5) {
		this.stage5 = stage5;
	}

	public int getStage6() {
		return stage6;
	}

	public void setStage6(int stage6) {
		this.stage6 = stage6;
	}

	public int getStage7() {
		return stage7;
	}

	public void setStage7(int stage7) {
		this.stage7 = stage7;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}
	
	
	public String getIC() {
		return IC;
	}

	
	public void setIC(String iC) {
		IC = iC;
	}

	
	public String getFbid() {
		return fbid;
	}
	

	public void setFbid(String fbid) {
		this.fbid = fbid;
	}
	
	
	public int getNewsLetter(){
		return newsletter;
	}
	
	
	public void setNewsLetter(int newsletter){
		this.newsletter = newsletter;
	}


	public String getDiscPack() {
		return discPack;
	}


	public void setDiscPack(String discPack) {
		this.discPack = discPack;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
}