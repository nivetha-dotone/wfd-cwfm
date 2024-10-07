package com.wfd.dot1.cwfm.pojo;

public class Workorder {

	private String workorderId;
	private String unitId;
	private String contractorId;
	private String name;
	private String validFrom;
	private String validTo;
	private String sapWorkorderNumber;
	private String eicNumber;
	public String getWorkorderId() {
		return workorderId;
	}
	public void setWorkorderId(String workorderId) {
		this.workorderId = workorderId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	public String getSapWorkorderNumber() {
		return sapWorkorderNumber;
	}
	public void setSapWorkorderNumber(String sapWorkorderNumber) {
		this.sapWorkorderNumber = sapWorkorderNumber;
	}
	public String getEicNumber() {
		return eicNumber;
	}
	public void setEicNumber(String eicNumber) {
		this.eicNumber = eicNumber;
	}
}
