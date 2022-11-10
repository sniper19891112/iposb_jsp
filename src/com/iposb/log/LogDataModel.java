package com.iposb.log;

public class LogDataModel {
	
	//log_search
	private String keyword = "";
	private String IP = "";
	private String country = "";
	private String searchDT = "";
	
	//log_dbbooking
	private int status = -1;
	private String remark = "";
	private String modifyDT = "";
	
	//tracking
	private String trackType = "";
	private String bookingType = "";
	private String trackDate = "";
	
	//log_changbooking
	private String checkinDT = "";
	private String checkoutDT = "";
	private int checkinTime = 0;
	private String selectedRoom = "";
	private String joinDT = "";
	private String pickupDT = "";
	private String pickupTime = "";
	private String dropoffDT = "";
	private String dropoffTime = "";
	private int needAuto = -1;
	private int needChauffeur = -1;
	private String pickup = "";
	private String dropoff = "";
	private int numAdult = 0;
	private int numChild = 0;
	private int numCar = 0;
	private int numPax = 0;
	private int numBag = 0;
	private String flight = "";
	private String flightTime = "";
	private String transfer = "";
	private String guestName = "";
	private String contactNo = "";
	private String localPhone = "";
	private int payMethod = -1;
	private String helps = "";
	
	//internalnote
	private int nid = 0;
	private String consignmentNo = "";
	private String creator = "";
	private String createDT = "";
	private int isDel = 0;
	private String modifier = "";
	private int totalNote = 0;
	
	//stage log
	private int stage = 0;
	private String reasonPending = "";
	private String reasonText = "";
	private String userId = "";
	private int serial = 0;
	
	//change amount
	private int lid = 0;
//	private String consignmentNo = "";
	private double originalPrice = 0.0;
	private int discountRate = 0;
//	private String remark = "";
//	private String creator = "";
	private int total = 0;

	
	public LogDataModel(){
	}



	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getIP() {
		return IP;
	}


	public void setIP(String ip) {
		IP = ip;
	}


	public String getSearchDT() {
		return searchDT;
	}


	public void setSearchDT(String searchDT) {
		this.searchDT = searchDT;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getModifyDT() {
		return modifyDT;
	}


	public void setModifyDT(String modifyDT) {
		this.modifyDT = modifyDT;
	}


	public String getTrackType() {
		return trackType;
	}


	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}


	public String getBookingType() {
		return bookingType;
	}


	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}


	public String getTrackDate() {
		return trackDate;
	}


	public void setTrackDate(String trackDate) {
		this.trackDate = trackDate;
	}


	public String getCheckinDT() {
		return checkinDT;
	}


	public void setCheckinDT(String checkinDT) {
		this.checkinDT = checkinDT;
	}


	public String getCheckoutDT() {
		return checkoutDT;
	}


	public void setCheckoutDT(String checkoutDT) {
		this.checkoutDT = checkoutDT;
	}


	public int getCheckinTime() {
		return checkinTime;
	}


	public void setCheckinTime(int checkinTime) {
		this.checkinTime = checkinTime;
	}


	public String getSelectedRoom() {
		return selectedRoom;
	}


	public void setSelectedRoom(String selectedRoom) {
		this.selectedRoom = selectedRoom;
	}


	public String getJoinDT() {
		return joinDT;
	}


	public void setJoinDT(String joinDT) {
		this.joinDT = joinDT;
	}


	public String getPickupDT() {
		return pickupDT;
	}


	public void setPickupDT(String pickupDT) {
		this.pickupDT = pickupDT;
	}


	public String getPickupTime() {
		return pickupTime;
	}


	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}


	public String getDropoffDT() {
		return dropoffDT;
	}


	public void setDropoffDT(String dropoffDT) {
		this.dropoffDT = dropoffDT;
	}


	public String getDropoffTime() {
		return dropoffTime;
	}


	public void setDropoffTime(String dropoffTime) {
		this.dropoffTime = dropoffTime;
	}


	public String getPickup() {
		return pickup;
	}


	public void setPickup(String pickup) {
		this.pickup = pickup;
	}
	
	public String getDropoff() {
		return dropoff;
	}


	public void setDropoff(String dropoff) {
		this.dropoff = dropoff;
	}


	public int getNumAdult() {
		return numAdult;
	}


	public void setNumAdult(int numAdult) {
		this.numAdult = numAdult;
	}


	public int getNumChild() {
		return numChild;
	}


	public void setNumChild(int numChild) {
		this.numChild = numChild;
	}


	public int getNumPax() {
		return numPax;
	}


	public void setNumPax(int numPax) {
		this.numPax = numPax;
	}


	public String getGuestName() {
		return guestName;
	}


	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public String getLocalPhone() {
		return localPhone;
	}


	public void setLocalPhone(String localPhone) {
		this.localPhone = localPhone;
	}


	public int getPayMethod() {
		return payMethod;
	}


	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}


	public String getHelps() {
		return helps;
	}


	public void setHelps(String helps) {
		this.helps = helps;
	}


	public int getNeedAuto() {
		return needAuto;
	}


	public void setNeedAuto(int needAuto) {
		this.needAuto = needAuto;
	}


	public int getNeedChauffeur() {
		return needChauffeur;
	}


	public void setNeedChauffeur(int needChauffeur) {
		this.needChauffeur = needChauffeur;
	}

	public int getNumCar() {
		return numCar;
	}


	public void setNumCar(int numCar) {
		this.numCar = numCar;
	}


	public int getNumBag() {
		return numBag;
	}


	public void setNumBag(int numBag) {
		this.numBag = numBag;
	}


	public String getFlight() {
		return flight;
	}


	public void setFlight(String flight) {
		this.flight = flight;
	}


	public String getTransfer() {
		return transfer;
	}


	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getCreateDT() {
		return createDT;
	}


	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}


	public int getIsDel() {
		return isDel;
	}


	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}


	public String getModifier() {
		return modifier;
	}


	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public int getTotalNote() {
		return totalNote;
	}


	public void setTotalNote(int totalNote) {
		this.totalNote = totalNote;
	}


	public int getNid() {
		return nid;
	}


	public void setNid(int nid) {
		this.nid = nid;
	}


	public String getFlightTime() {
		return flightTime;
	}


	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}


	public String getConsignmentNo() {
		return consignmentNo;
	}


	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}


	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	public int getStage() {
		return stage;
	}

	
	public void setStage(int stage) {
		this.stage = stage;
	}



	public int getSerial() {
		return serial;
	}



	public void setSerial(int serial) {
		this.serial = serial;
	}



	public String getReasonPending() {
		return reasonPending;
	}



	public void setReasonPending(String reasonPending) {
		this.reasonPending = reasonPending;
	}



	public String getReasonText() {
		return reasonText;
	}



	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}



	public int getLid() {
		return lid;
	}



	public void setLid(int lid) {
		this.lid = lid;
	}
	
	
	
	public double getOriginalPrice() {
		return originalPrice;
	}



	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}



	public int getDiscountRate() {
		return discountRate;
	}



	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}



	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}


	
}