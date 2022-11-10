package com.iposb.code;

public class CodeDataModel {
	
	private int cid = 0;
	private String code = "";
	private int discount = 0;
	private int allowTimes = 0;
	private int used = 0;
	private String periodStart = "";
	private String periodEnd = "";
	private String createDT = "";
	private String lastUsedDT = "";
	private String usedToBook = "";
	private String remark = "";
	private String errmsg= "";
	private int total = 0;
	private int isAllow = 0; //0: 不允許使用
	private int discountType = 0; // 0 = MYR; 1 = %
	
	public CodeDataModel(){
	}
	

	
	
	
	public int getCid() {
		return cid;
	}





	public void setCid(int cid) {
		this.cid = cid;
	}





	public int getDiscountType() {
		return discountType;
	}






	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}






	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public int getDiscount() {
		return discount;
	}






	public void setDiscount(int discount) {
		this.discount = discount;
	}






	public int getAllowTimes() {
		return allowTimes;
	}

	public void setAllowTimes(int allowTimes) {
		this.allowTimes = allowTimes;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public String getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}

	public String getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getLastUsedDT() {
		return lastUsedDT;
	}

	public void setLastUsedDT(String lastUsedDT) {
		this.lastUsedDT = lastUsedDT;
	}

	public String getUsedToBook() {
		return usedToBook;
	}

	public void setUsedToBook(String usedToBook) {
		this.usedToBook = usedToBook;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getIsAllow() {
		return isAllow;
	}

	public void setIsAllow(int isAllow) {
		this.isAllow = isAllow;
	}


}