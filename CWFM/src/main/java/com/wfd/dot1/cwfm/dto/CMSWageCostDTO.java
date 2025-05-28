package com.wfd.dot1.cwfm.dto;

public class CMSWageCostDTO {
    private Long wcTransId;
    private Integer unitId;
    private String unitCode;
    private Integer contractorId;
    private String contractorCode;
    private String workOrderNumber;
    private String startDate;
    private String endDate;
    private String woValidFrom;
    private String woValidTo;
    private String billType;
    private String services;
    // Getters & Setters
	public Long getWcTransId() {
		return wcTransId;
	}
	public void setWcTransId(Long wcTransId) {
		this.wcTransId = wcTransId;
	}
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public Integer getContractorId() {
		return contractorId;
	}
	public void setContractorId(Integer contractorId) {
		this.contractorId = contractorId;
	}
	public String getContractorCode() {
		return contractorCode;
	}
	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getWoValidFrom() {
		return woValidFrom;
	}
	public void setWoValidFrom(String woValidFrom) {
		this.woValidFrom = woValidFrom;
	}
	public String getWoValidTo() {
		return woValidTo;
	}
	public void setWoValidTo(String woValidTo) {
		this.woValidTo = woValidTo;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
}