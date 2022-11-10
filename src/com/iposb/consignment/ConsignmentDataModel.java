package com.iposb.consignment;

public class ConsignmentDataModel {
	
	private int cid = 0;
	private String consignmentNo = "";
	private int serial = 0;
	private String generalCargoNo = "";
	private String userId = "";
	private int partnerId = 0;
	private int agentId = 0;
	private String senderName = "";
	private String senderAddress1 = "";
	private String senderAddress2 = "";
	private String senderAddress3 = "";
	private String senderPostcode = "";
	private int senderZone = 0;
	private String senderZonename = "";
	private int senderZoneserial = 0;
	private int senderArea = 0;
	private String senderCountry = "";
	private String senderAreaname = "";
	private String senderPhone = "";
	private String receiverAttn = "";
	private String senderIC = "";
	private String receiverName = "";
	private String receiverAddress1 = "";
	private String receiverAddress2 = "";
	private String receiverAddress3 = "";
	private String receiverPostcode = "";
	private int receiverZone = 0;
	private String receiverZonename = "";
	private int receiverZoneserial = 0;
	private String receiverPhone = "";
	private int receiverArea = 0;
	private String receiverCountry = "";
	private String receiverAreaname = "";
	private String helps = "";
	private String tickItem = "";
	private int shipmentMethod = 0;
	private int shipmentType = 0;
	private int quantity = 0;
	private String flightNum = "";
	private String airwaybillNum = "";
	private String natureGood = "";
	private String receiptNum = "";
	private double weight = 0.0;
	private double length = 0.0;
	private double width = 0.0;
	private double height = 0.0;
	private double amount = 0.0;
	private String promoCode = "";
	private double discount = 0.0;
	private String discountReason = "";
	private int deposit = 0;
	private int payMethod = 0;
	private int payStatus = 0;
	private String payDT = "";
	private String payDeadline = "";
	private int creditArea = 0;
	private int status = 0;
	private int stage = 0;
	private String pickupDriver = "";
	private String pickupDT = "";
	private String deliveryDriver = "";
	private String deliveryDT = "";
	private String remarkEmail = "";
	private String remarkNotify = "";
	private String remark = "";
	private String stationRemark = "";
	private String terms = "";
	private String useLocale = "";
	private String verify = "";
	private String createDT = "";
	private String dispatchDT = "";
	private String distributeDT = "";
	private String cancelDT = "";
	private int cancelBy = 0;
	private String reason = "";
	private String reasonPending = "";
	private String reasonText = "";
	private int staffCreate = 0;
	private String howtocreate = "";
	
	private String invoiceNo = "";
	private int total = 0;
	private String errmsg= "";
	

	//memberlist
	private int mid = 0;
	private String email = "";
	private int gender = 0;
	private String ename = "";
	private String cname = "";
	private String IC = "";
	private int privilege = 0;
	private String accNo = "";
	private int totalConsignment = 0;
	
	//favourite
	private int fid = 0;
	
	//draft
	private int did = 0;
	private String modifyDT = "";
	private int isDel = 0;
	
	//stafflist
	private int sid = 0;
	
	//partnerlist
	private String abbreviationPartner = "";
	private String abbreviationAgent = "";
	
	//runsheet
	private int rid = 0;
	private String runsheetDT = "";
	private int area = 0;
	private String driver = "";
	private int pcs = 0;
	private String driverID = "";
	private String attendee1 = "";
	private String attendee1IC = "";
	private String attendee1ID = "";
	private String attendee2 = "";
	private String attendee2IC = "";
	private String attendee2ID = "";
	
	//pricing
	private String title_enUS = "";
//	private int freightType = 0;
	private int pricing = 0;
	private int pid = 0;
	
	private String serials = "{}";

	
	public ConsignmentDataModel(){
	}


	public int getCid() {
		return cid;
	}


	public void setCid(int cid) {
		this.cid = cid;
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


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public String getSenderPhone() {
		return senderPhone;
	}


	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}


	public String getReceiverAttn() {
		return receiverAttn;
	}


	public void setReceiverAttn(String receiverAttn) {
		this.receiverAttn = receiverAttn;
	}


	public String getSenderIC() {
		return senderIC;
	}


	public void setSenderIC(String senderIC) {
		this.senderIC = senderIC;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
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


	public String getHelps() {
		return helps;
	}


	public void setHelps(String helps) {
		this.helps = helps;
	}


	public int getShipmentType() {
		return shipmentType;
	}


	public void setShipmentType(int shipmentType) {
		this.shipmentType = shipmentType;
	}


	public String getFlightNum() {
		return flightNum;
	}


	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}


	public String getAirwaybillNum() {
		return airwaybillNum;
	}


	public void setAirwaybillNum(String airwaybillNum) {
		this.airwaybillNum = airwaybillNum;
	}


	public String getNatureGood() {
		return natureGood;
	}


	public void setNatureGood(String natureGood) {
		this.natureGood = natureGood;
	}


	public String getReceiptNum() {
		return receiptNum;
	}


	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public double getLength() {
		return length;
	}


	public void setLength(double length) {
		this.length = length;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}


	public double getAmount() {
		return amount;
	}


	public String getPromoCode() {
		return promoCode;
	}


	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	public String getDiscountReason() {
		return discountReason;
	}


	public void setDiscountReason(String discountReason) {
		this.discountReason = discountReason;
	}


	public int getDeposit() {
		return deposit;
	}


	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}


	public int getPayMethod() {
		return payMethod;
	}


	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}


	public int getPayStatus() {
		return payStatus;
	}


	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}


	public String getPayDT() {
		return payDT;
	}


	public void setPayDT(String payDT) {
		this.payDT = payDT;
	}


	public String getPayDeadline() {
		return payDeadline;
	}


	public void setPayDeadline(String payDeadline) {
		this.payDeadline = payDeadline;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getStage() {
		return stage;
	}


	public void setStage(int stage) {
		this.stage = stage;
	}


	public String getPickupDriver() {
		return pickupDriver;
	}


	public void setPickupDriver(String pickupDriver) {
		this.pickupDriver = pickupDriver;
	}


	public String getDeliveryDriver() {
		return deliveryDriver;
	}


	public void setDeliveryDriver(String deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}


	public String getPickupDT() {
		return pickupDT;
	}


	public void setPickupDT(String pickupDT) {
		this.pickupDT = pickupDT;
	}


	public String getDeliveryDT() {
		return deliveryDT;
	}


	public void setDeliveryDT(String deliveryDT) {
		this.deliveryDT = deliveryDT;
	}


	public String getRemarkEmail() {
		return remarkEmail;
	}


	public void setRemarkEmail(String remarkEmail) {
		this.remarkEmail = remarkEmail;
	}


	public String getRemarkNotify() {
		return remarkNotify;
	}


	public void setRemarkNotify(String remarkNotify) {
		this.remarkNotify = remarkNotify;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getUseLocale() {
		return useLocale;
	}


	public void setUseLocale(String useLocale) {
		this.useLocale = useLocale;
	}


	public String getVerify() {
		return verify;
	}


	public void setVerify(String verify) {
		this.verify = verify;
	}


	public String getCreateDT() {
		return createDT;
	}


	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}


	public String getCancelDT() {
		return cancelDT;
	}


	public void setCancelDT(String cancelDT) {
		this.cancelDT = cancelDT;
	}


	public int getCancelBy() {
		return cancelBy;
	}


	public void setCancelBy(int cancelBy) {
		this.cancelBy = cancelBy;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getInvoiceNo() {
		return invoiceNo;
	}


	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public String getErrmsg() {
		return errmsg;
	}


	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getGender() {
		return gender;
	}


	public void setGender(int gender) {
		this.gender = gender;
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


	public int getTotalConsignment() {
		return totalConsignment;
	}


	public void setTotalConsignment(int totalConsignment) {
		this.totalConsignment = totalConsignment;
	}


	public int getFid() {
		return fid;
	}


	public void setFid(int fid) {
		this.fid = fid;
	}


	public int getMid() {
		return mid;
	}


	public void setMid(int mid) {
		this.mid = mid;
	}


	public int getReceiverZone() {
		return receiverZone;
	}


	public void setReceiverZone(int receiverZone) {
		this.receiverZone = receiverZone;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getDid() {
		return did;
	}


	public void setDid(int did) {
		this.did = did;
	}


	public int getIsDel() {
		return isDel;
	}


	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}


	public String getModifyDT() {
		return modifyDT;
	}


	public void setModifyDT(String modifyDT) {
		this.modifyDT = modifyDT;
	}


	public String getTerms() {
		return terms;
	}


	public void setTerms(String terms) {
		this.terms = terms;
	}


	public String getGeneralCargoNo() {
		return generalCargoNo;
	}


	public void setGeneralCargoNo(String generalCargoNo) {
		this.generalCargoNo = generalCargoNo;
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


	public int getSenderArea() {
		return senderArea;
	}


	public void setSenderArea(int senderArea) {
		this.senderArea = senderArea;
	}


	public String getSenderCountry() {
		return senderCountry;
	}


	public void setSenderCountry(String senderCountry) {
		this.senderCountry = senderCountry;
	}


	public String getSenderPostcode() {
		return senderPostcode;
	}


	public void setSenderPostcode(String senderPostcode) {
		this.senderPostcode = senderPostcode;
	}


	public int getSenderZone() {
		return senderZone;
	}


	public void setSenderZone(int senderZone) {
		this.senderZone = senderZone;
	}


	public int getSerial() {
		return serial;
	}


	public void setSerial(int serial) {
		this.serial = serial;
	}


	public String getDispatchDT() {
		return dispatchDT;
	}


	public void setDispatchDT(String dispatchDT) {
		this.dispatchDT = dispatchDT;
	}


	public String getDistributeDT() {
		return distributeDT;
	}


	public void setDistributeDT(String distributeDT) {
		this.distributeDT = distributeDT;
	}


	public String getSenderZonename() {
		return senderZonename;
	}


	public void setSenderZonename(String senderZonename) {
		this.senderZonename = senderZonename;
	}


	public String getReceiverZonename() {
		return receiverZonename;
	}


	public void setReceiverZonename(String receiverZonename) {
		this.receiverZonename = receiverZonename;
	}


	public int getSenderZoneserial() {
		return senderZoneserial;
	}


	public void setSenderZoneserial(int senderZoneserial) {
		this.senderZoneserial = senderZoneserial;
	}


	public int getReceiverZoneserial() {
		return receiverZoneserial;
	}


	public void setReceiverZoneserial(int receiverZoneserial) {
		this.receiverZoneserial = receiverZoneserial;
	}


	public String getSenderAreaname() {
		return senderAreaname;
	}


	public void setSenderAreaname(String senderAreaname) {
		this.senderAreaname = senderAreaname;
	}


	public String getReceiverAreaname() {
		return receiverAreaname;
	}


	public void setReceiverAreaname(String receiverAreaname) {
		this.receiverAreaname = receiverAreaname;
	}


	public String getTickItem() {
		return tickItem;
	}


	public void setTickItem(String tickItem) {
		this.tickItem = tickItem;
	}


	public int getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
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



	public String getAbbreviationPartner() {
		return abbreviationPartner;
	}


	public void setAbbreviationPartner(String abbreviationPartner) {
		this.abbreviationPartner = abbreviationPartner;
	}


	public String getAbbreviationAgent() {
		return abbreviationAgent;
	}


	public void setAbbreviationAgent(String abbreviationAgent) {
		this.abbreviationAgent = abbreviationAgent;
	}


	public String getIC() {
		return IC;
	}


	public void setIC(String iC) {
		IC = iC;
	}


	public String getAttendee1() {
		return attendee1;
	}


	public void setAttendee1(String attendee1) {
		this.attendee1 = attendee1;
	}


	public String getAttendee1IC() {
		return attendee1IC;
	}


	public void setAttendee1IC(String attendee1ic) {
		attendee1IC = attendee1ic;
	}


	public String getAttendee2() {
		return attendee2;
	}


	public void setAttendee2(String attendee2) {
		this.attendee2 = attendee2;
	}


	public String getAttendee2IC() {
		return attendee2IC;
	}


	public void setAttendee2IC(String attendee2ic) {
		attendee2IC = attendee2ic;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public int getStaffCreate() {
		return staffCreate;
	}


	public void setStaffCreate(int staffCreate) {
		this.staffCreate = staffCreate;
	}


	public String getDriverID() {
		return driverID;
	}


	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}


	public String getAttendee1ID() {
		return attendee1ID;
	}


	public void setAttendee1ID(String attendee1id) {
		attendee1ID = attendee1id;
	}


	public String getAttendee2ID() {
		return attendee2ID;
	}


	public void setAttendee2ID(String attendee2id) {
		attendee2ID = attendee2id;
	}


	public int getRid() {
		return rid;
	}


	public void setRid(int rid) {
		this.rid = rid;
	}


	public String getRunsheetDT() {
		return runsheetDT;
	}


	public void setRunsheetDT(String runsheetDT) {
		this.runsheetDT = runsheetDT;
	}


	public String getDriver() {
		return driver;
	}


	public void setDriver(String driver) {
		this.driver = driver;
	}


	public int getPcs() {
		return pcs;
	}


	public void setPcs(int pcs) {
		this.pcs = pcs;
	}


	public int getArea() {
		return area;
	}


	public void setArea(int area) {
		this.area = area;
	}


	public int getCreditArea() {
		return creditArea;
	}


	public void setCreditArea(int creditArea) {
		this.creditArea = creditArea;
	}


	public String getTitle_enUS() {
		return title_enUS;
	}


	public void setTitle_enUS(String title_enUS) {
		this.title_enUS = title_enUS;
	}


//	public int getFreightType() {
//		return freightType;
//	}
//
//
//	public void setFreightType(int freightType) {
//		this.freightType = freightType;
//	}


	public int getPricing() {
		return pricing;
	}


	public void setPricing(int pricing) {
		this.pricing = pricing;
	}


	public int getPid() {
		return pid;
	}


	public void setPid(int pid) {
		this.pid = pid;
	}


	public String getHowtocreate() {
		return howtocreate;
	}


	public void setHowtocreate(String howtocreate) {
		this.howtocreate = howtocreate;
	}


	public String getSerials() {
		return serials;
	}


	public void setSerials(String serials) {
		this.serials = serials;
	}


	public String getStationRemark() {
		return stationRemark;
	}


	public void setStationRemark(String stationRemark) {
		this.stationRemark = stationRemark;
	}


	public int getAgentId() {
		return agentId;
	}


	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}


	public int getShipmentMethod() {
		return shipmentMethod;
	}


	public void setShipmentMethod(int shipmentMethod) {
		this.shipmentMethod = shipmentMethod;
	}

	
}