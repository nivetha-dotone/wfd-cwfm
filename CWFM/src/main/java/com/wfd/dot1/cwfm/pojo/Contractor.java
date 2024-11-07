package com.wfd.dot1.cwfm.pojo;

public class Contractor {

	private String contractorId;
	
	private String contractorName;
	
	private String unitId;
	
	private String contractorCode;
	
	private String contractorAddress;
	
	private String principalEmployer;
	
	   private boolean isBlocked;

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getPrincipalEmployer() {
		return principalEmployer;
	}

	public void setPrincipalEmployer(String principalEmployer) {
		this.principalEmployer = principalEmployer;
	}

	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}

	public String getContractorAddress() {
		return contractorAddress;
	}

	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
