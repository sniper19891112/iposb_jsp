package com.iposb.account;

public class AccountDataModel {
	
	private int aid = 0;
	private String consignmentNo = "";
	private String accDT = "";
	private String accMonth = "";
	private String statMonth = "";
	private String inputMonth = "";
	private String outputMonth = "";
	private int total = 0;
	private double turnover = 0.0;
	private double profit = 0.0;
	private String remark = "";
	private String errmsg = "";
	
	//consignment
	private int cid = 0;
	private String createDT = "";
	private String dispatchDT = "";
	private String senderName = "";
	private int senderArea = 0;
	private int receiverArea = 0;
	private String senderAreaname = "";
	private String receiverAreaname = "";
	private int quantity = 0;
	private double amount = 0.0;
	private int payMethod = 0;
	private int creditArea = 0;
	private int payStatus = 0;
	private int status = 0;
	
	//settlement
	private int sid = 0;
	private String settlementDT = "";
	private double bankin = 0.0;
	private String filename = "";
	private String creator = "";
	
	//invoice
	private String generalCargoNo = "";
	private double weight = 0.0;
	private String terms = "";
	private String ename = "";
	private String address = "";
	private double first = 0.0;
	private double firstPrice = 0.0;
	private double addition = 0.0;
	private double additionPrice = 0.0;
	private double codPrice = 0.0;
	

	public AccountDataModel(){
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getAccMonth() {
		return accMonth;
	}

	public void setAccMonth(String accMonth) {
		this.accMonth = accMonth;
	}

	public String getStatMonth() {
		return statMonth;
	}

	public void setStatMonth(String statMonth) {
		this.statMonth = statMonth;
	}

	public String getInputMonth() {
		return inputMonth;
	}

	public void setInputMonth(String inputMonth) {
		this.inputMonth = inputMonth;
	}

	public String getOutputMonth() {
		return outputMonth;
	}

	public void setOutputMonth(String outputMonth) {
		this.outputMonth = outputMonth;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getSenderArea() {
		return senderArea;
	}

	public void setSenderArea(int senderArea) {
		this.senderArea = senderArea;
	}

	public int getReceiverArea() {
		return receiverArea;
	}

	public void setReceiverArea(int receiverArea) {
		this.receiverArea = receiverArea;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
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

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSettlementDT() {
		return settlementDT;
	}

	public void setSettlementDT(String settlementDT) {
		this.settlementDT = settlementDT;
	}

	public double getBankin() {
		return bankin;
	}

	public void setBankin(double bankin) {
		this.bankin = bankin;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAccDT() {
		return accDT;
	}

	public void setAccDT(String accDT) {
		this.accDT = accDT;
	}

	public String getDispatchDT() {
		return dispatchDT;
	}

	public void setDispatchDT(String dispatchDT) {
		this.dispatchDT = dispatchDT;
	}

	public int getCreditArea() {
		return creditArea;
	}

	public void setCreditArea(int creditArea) {
		this.creditArea = creditArea;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getFirst() {
		return first;
	}

	public void setFirst(double first) {
		this.first = first;
	}

	public double getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(double firstPrice) {
		this.firstPrice = firstPrice;
	}

	public double getAddition() {
		return addition;
	}

	public void setAddition(double addition) {
		this.addition = addition;
	}

	public double getAdditionPrice() {
		return additionPrice;
	}

	public void setAdditionPrice(double additionPrice) {
		this.additionPrice = additionPrice;
	}

	public double getCodPrice() {
		return codPrice;
	}

	public void setCodPrice(double codPrice) {
		this.codPrice = codPrice;
	}

	public String getGeneralCargoNo() {
		return generalCargoNo;
	}

	public void setGeneralCargoNo(String generalCargoNo) {
		this.generalCargoNo = generalCargoNo;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	

}