package com.iposb.notification;

public class NotificationDataModel {

	
	private int nid = 0;
	private int agentId = 0;
	private String notifyType = "";
	private String content = "";
	private String param = "";
	private String createDT = "";
	private int isRead = 0;
	private String readDT = "";
	private String readBy = "";
	private int total = 0;

	
	public NotificationDataModel(){
	}


	public int getNid() {
		return nid;
	}


	public void setNid(int nid) {
		this.nid = nid;
	}


	public String getNotifyType() {
		return notifyType;
	}


	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}


	public String getCreateDT() {
		return createDT;
	}


	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}


	public int getIsRead() {
		return isRead;
	}


	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}


	public String getReadDT() {
		return readDT;
	}


	public void setReadDT(String readDT) {
		this.readDT = readDT;
	}


	public String getReadBy() {
		return readBy;
	}


	public void setReadBy(String readBy) {
		this.readBy = readBy;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getAgentId() {
		return agentId;
	}


	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}


	
}