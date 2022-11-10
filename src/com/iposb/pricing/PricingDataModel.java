package com.iposb.pricing;

public class PricingDataModel {
	
	private int pid = 0;
	private int category = 0;
	private String title_enUS = "";
	private int fromArea = 0;
	private int toArea = 0;
	private double first = 0.0;
	private double firstPrice = 0.0;
	private double addition = 0.0;
	private double additionPrice = 0.0;
	private double codPrice = 0.0;
	private int isShow = 0;
	private String createDT = "";
	private String creator = "";
	private String modifyDT = "";
	private String modifier = "";
	private String errmsg = "";
	private int total = 0;
	
	//pricing_normal_
	private double weightEach = 0.0;
	private double additionCharge = 0.0;
	private int handling = 0;
	private int fuel = 0;
	private int firstRange = 0;
//	private double firstPrice = 0.0;
	private int secondRange = 0;
	private double secondPrice = 0.0;
	private int thirdRange = 0;
	private double thirdPrice = 0.0;
	private int forthRange = 0;
	private double forthPrice = 0.0;
//	private int fifthRange = 0;
	private double fifthPrice = 0.0;
	
	//pricing_credit
	private double discountFirstPriceA = 0.0;
	private double discountAdditionPriceA = 0.0;
	private double discountFirstPriceB = 0.0;
	private double discountAdditionPriceB = 0.0;
	private double discountFirstPriceC = 0.0;
	private double discountAdditionPriceC = 0.0;
	private double discountFirstPriceD = 0.0;
	private double discountAdditionPriceD = 0.0;
	
	

	public PricingDataModel(){
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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

	public String getTitle_enUS() {
		return title_enUS;
	}

	public void setTitle_enUS(String title_enUS) {
		this.title_enUS = title_enUS;
	}

	public int getFirstRange() {
		return firstRange;
	}

	public void setFirstRange(int firstRange) {
		this.firstRange = firstRange;
	}

	public int getSecondRange() {
		return secondRange;
	}

	public void setSecondRange(int secondRange) {
		this.secondRange = secondRange;
	}

	public double getSecondPrice() {
		return secondPrice;
	}

	public void setSecondPrice(double secondPrice) {
		this.secondPrice = secondPrice;
	}

	public int getThirdRange() {
		return thirdRange;
	}

	public void setThirdRange(int thirdRange) {
		this.thirdRange = thirdRange;
	}

	public double getThirdPrice() {
		return thirdPrice;
	}

	public void setThirdPrice(double thirdPrice) {
		this.thirdPrice = thirdPrice;
	}

	public int getForthRange() {
		return forthRange;
	}

	public void setForthRange(int forthRange) {
		this.forthRange = forthRange;
	}

	public double getForthPrice() {
		return forthPrice;
	}

	public void setForthPrice(double forthPrice) {
		this.forthPrice = forthPrice;
	}


	public double getFifthPrice() {
		return fifthPrice;
	}

	public void setFifthPrice(double fifthPrice) {
		this.fifthPrice = fifthPrice;
	}

	public double getWeightEach() {
		return weightEach;
	}

	public void setWeightEach(double weightEach) {
		this.weightEach = weightEach;
	}

	public int getFromArea() {
		return fromArea;
	}

	public void setFromArea(int fromArea) {
		this.fromArea = fromArea;
	}

	public int getToArea() {
		return toArea;
	}

	public void setToArea(int toArea) {
		this.toArea = toArea;
	}

	public double getAdditionCharge() {
		return additionCharge;
	}

	public void setAdditionCharge(double additionCharge) {
		this.additionCharge = additionCharge;
	}

	public int getHandling() {
		return handling;
	}

	public void setHandling(int handling) {
		this.handling = handling;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public double getDiscountFirstPriceA() {
		return discountFirstPriceA;
	}

	public void setDiscountFirstPriceA(double discountFirstPriceA) {
		this.discountFirstPriceA = discountFirstPriceA;
	}

	public double getDiscountAdditionPriceA() {
		return discountAdditionPriceA;
	}

	public void setDiscountAdditionPriceA(double discountAdditionPriceA) {
		this.discountAdditionPriceA = discountAdditionPriceA;
	}

	public double getDiscountFirstPriceB() {
		return discountFirstPriceB;
	}

	public void setDiscountFirstPriceB(double discountFirstPriceB) {
		this.discountFirstPriceB = discountFirstPriceB;
	}

	public double getDiscountAdditionPriceB() {
		return discountAdditionPriceB;
	}

	public void setDiscountAdditionPriceB(double discountAdditionPriceB) {
		this.discountAdditionPriceB = discountAdditionPriceB;
	}

	public double getDiscountFirstPriceC() {
		return discountFirstPriceC;
	}

	public void setDiscountFirstPriceC(double discountFirstPriceC) {
		this.discountFirstPriceC = discountFirstPriceC;
	}

	public double getDiscountAdditionPriceC() {
		return discountAdditionPriceC;
	}

	public void setDiscountAdditionPriceC(double discountAdditionPriceC) {
		this.discountAdditionPriceC = discountAdditionPriceC;
	}

	public double getDiscountFirstPriceD() {
		return discountFirstPriceD;
	}

	public void setDiscountFirstPriceD(double discountFirstPriceD) {
		this.discountFirstPriceD = discountFirstPriceD;
	}

	public double getDiscountAdditionPriceD() {
		return discountAdditionPriceD;
	}

	public void setDiscountAdditionPriceD(double discountAdditionPriceD) {
		this.discountAdditionPriceD = discountAdditionPriceD;
	}



	

}