package com.wfd.dot1.cwfm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "CMSWORKORDER")
public class CMSWorkOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long workOrderId;
	    private long unitId;
	    private long contractorId;
	    private String name;
	    private Date validFrom;
	    private Date validDt;
	    private int typeId;
	    private int depId;
	    public CMSWorkOrder() {
			super();
			// TODO Auto-generated constructor stub
		}

		private int secId;
	    private String glCode;
	    private String costCenter;
	    private Integer ownerId;
	    private Integer suprId;
	    private short status;
		/* private Date releasedDate; */
	    @Column(name = "SAP_WORKORDER_NUM")
	    private String sapWorkOrderNum;
	    private String eicNumber;

	    public long getWorkOrderId() {
	        return workOrderId;
	    }

	    public void setWorkOrderId(long workOrderId) {
	        this.workOrderId = workOrderId;
	    }

	    public long getUnitId() {
	        return unitId;
	    }

	    public void setUnitId(long unitId) {
	        this.unitId = unitId;
	    }

	    public long getContractorId() {
	        return contractorId;
	    }

	    public void setContractorId(long contractorId) {
	        this.contractorId = contractorId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Date getValidFrom() {
	        return validFrom;
	    }

	    public void setValidFrom(Date validFrom) {
	        this.validFrom = validFrom;
	    }

	    public Date getValidDt() {
	        return validDt;
	    }

	    public void setValidDt(Date validDt) {
	        this.validDt = validDt;
	    }

	    public int getTypeId() {
	        return typeId;
	    }

	    public void setTypeId(int typeId) {
	        this.typeId = typeId;
	    }

	    public int getDepId() {
	        return depId;
	    }

	    public void setDepId(int depId) {
	        this.depId = depId;
	    }

	    public int getSecId() {
	        return secId;
	    }

	    public void setSecId(int secId) {
	        this.secId = secId;
	    }

	    public String getGlCode() {
	        return glCode;
	    }

	    public void setGlCode(String glCode) {
	        this.glCode = glCode;
	    }

	    public String getCostCenter() {
	        return costCenter;
	    }

	    public void setCostCenter(String costCenter) {
	        this.costCenter = costCenter;
	    }

	    public Integer getOwnerId() {
	        return ownerId;
	    }

	    public void setOwnerId(Integer ownerId) {
	        this.ownerId = ownerId;
	    }

	    public Integer getSuprId() {
	        return suprId;
	    }

	    public void setSuprId(Integer suprId) {
	        this.suprId = suprId;
	    }

	    public short getStatus() {
	        return status;
	    }

	    public void setStatus(short status) {
	        this.status = status;
	    }

		/*
		 * public Date getReleasedDate() { return releasedDate; }
		 * 
		 * public void setReleasedDate(Date releasedDate) { this.releasedDate =
		 * releasedDate; }
		 */

	    public String getSapWorkOrderNum() {
	        return sapWorkOrderNum;
	    }

	    public void setSapWorkOrderNum(String sapWorkOrderNum) {
	        this.sapWorkOrderNum = sapWorkOrderNum;
	    }

	    public String getEicNumber() {
	        return eicNumber;
	    }

	    public void setEicNumber(String eicNumber) {
	        this.eicNumber = eicNumber;
	    }
	}
