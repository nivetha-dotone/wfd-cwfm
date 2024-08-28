package com.wfd.dot1.cwfm.entity;

import java.util.Date;

public class WorkOrderLLWC {
    private long wollid;
    public long getWollid() {
		return wollid;
	}
	public void setWollid(long wollid) {
		this.wollid = wollid;
	}
	public String getWoNumber() {
		return woNumber;
	}
	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getActiveWorkmenCount() {
		return activeWorkmenCount;
	}
	public void setActiveWorkmenCount(int activeWorkmenCount) {
		this.activeWorkmenCount = activeWorkmenCount;
	}
	private String woNumber;
    private String licenseNumber;
    private String licenseType;
    private Date fromDate;
    private Date toDate;
    private int total;
    private int activeWorkmenCount;
    
    // Constructor, getters, and setters
}