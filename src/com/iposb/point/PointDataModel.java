package com.iposb.point;

public class PointDataModel {
	
	private int pid = 0;
	private String userId = "";
	private int points = 0;
	private int purpose = 0;
	private String remark = "";
	private String createDT = "";
	
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
	
	
	public int getPoints() {
		return points;
	}
	
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	
	public int getPurpose() {
		return purpose;
	}
	
	
	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}
	
	
	public String getRemark() {
		return remark;
	}
	
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public String getCreateDT() {
		return createDT;
	}
	
	
	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}
	
	
}