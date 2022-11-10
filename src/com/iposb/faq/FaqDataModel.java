package com.iposb.faq;

public class FaqDataModel {
	
	private int fid = 0;
	private String category = "";
	private String title_enUS = "";
	private String title_zhCN = "";
	private String title_zhTW = "";
	private String content_enUS = "";
	private String content_zhCN = "";
	private String content_zhTW = "";
	private int view = 0;
	private int isShow = 1;
	private String createDT = "";
	private String creator = "";
	private String modifyDT = "";
	private String modifier = "";
	private String errmsg = "";
	private int total = 0;

	public FaqDataModel(){
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getTitle_enUS() {
		return title_enUS;
	}

	public void setTitle_enUS(String title_enUS) {
		this.title_enUS = title_enUS;
	}

	public String getTitle_zhCN() {
		return title_zhCN;
	}

	public void setTitle_zhCN(String title_zhCN) {
		this.title_zhCN = title_zhCN;
	}

	public String getTitle_zhTW() {
		return title_zhTW;
	}

	public void setTitle_zhTW(String title_zhTW) {
		this.title_zhTW = title_zhTW;
	}

	public String getContent_enUS() {
		return content_enUS;
	}

	public void setContent_enUS(String content_enUS) {
		this.content_enUS = content_enUS;
	}

	public String getContent_zhCN() {
		return content_zhCN;
	}

	public void setContent_zhCN(String content_zhCN) {
		this.content_zhCN = content_zhCN;
	}

	public String getContent_zhTW() {
		return content_zhTW;
	}

	public void setContent_zhTW(String content_zhTW) {
		this.content_zhTW = content_zhTW;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
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

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}




}