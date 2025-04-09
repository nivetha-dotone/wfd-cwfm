package com.wfd.dot1.cwfm.pojo;

import java.util.List;

public class ContractorRegistration{
    private String contractorregId;
	private String principalEmployer;
	private String vendorCode;
	private String contractorId;
	private String contractorName;
	private String managerName;
	private String locofWork;
	private String totalStrength;
	
	private String rcMaxEmp;
	private String pfNum;
	private String natureOfWork;
	private String contractFrom;
	private String contractTo;
	private String contractType;
	private String mainContractor;
	private String status;
	private String requestType;
	private String rcVerified;
	private String createdBy;
	private List<ContractorRegistrationPolicy> regPolicy;
	
	public List<ContractorRegistrationPolicy> getRegPolicy() {
		return regPolicy;
	}
	public void setRegPolicy(List<ContractorRegistrationPolicy> regPolicy) {
		this.regPolicy = regPolicy;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getContractorregId() {
		return contractorregId;
	}
	public void setContractorregId(String contractorregId) {
		this.contractorregId = contractorregId;
	}
	public String getPrincipalEmployer() {
		return principalEmployer;
	}
	public void setPrincipalEmployer(String principalEmployer) {
		this.principalEmployer = principalEmployer;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getLocofWork() {
		return locofWork;
	}
	public void setLocofWork(String locofWork) {
		this.locofWork = locofWork;
	}
	public String getTotalStrength() {
		return totalStrength;
	}
	public void setTotalStrength(String totalStrength) {
		this.totalStrength = totalStrength;
	}
	public String getRcMaxEmp() {
		return rcMaxEmp;
	}
	public void setRcMaxEmp(String rcMaxEmp) {
		this.rcMaxEmp = rcMaxEmp;
	}
	public String getPfNum() {
		return pfNum;
	}
	public void setPfNum(String pfNum) {
		this.pfNum = pfNum;
	}
	public String getNatureOfWork() {
		return natureOfWork;
	}
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
	public String getContractFrom() {
		return contractFrom;
	}
	public void setContractFrom(String contractFrom) {
		this.contractFrom = contractFrom;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getContractTo() {
		return contractTo;
	}
	public void setContractTo(String contractTo) {
		this.contractTo = contractTo;
	}
	public String getRcVerified() {
		return rcVerified;
	}
	public void setRcVerified(String rcVerified) {
		this.rcVerified = rcVerified;
	}
	public String getMainContractor() {
		return mainContractor;
	}
	public void setMainContractor(String mainContractor) {
		this.mainContractor = mainContractor;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
	
}
