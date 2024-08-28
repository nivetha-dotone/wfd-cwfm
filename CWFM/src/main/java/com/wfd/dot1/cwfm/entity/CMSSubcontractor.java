package com.wfd.dot1.cwfm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "CMSSUBCONTRACTOR")
public class CMSSubcontractor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long refId;
    private Long unitId;
    private Long contractorId;
    private Long subcontractorId;
    private Long workOrderId;
    private Long workmenType;
    private Date insertDtm;
    private Date updateDtm;

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getContractorId() {
        return contractorId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public Long getSubcontractorId() {
        return subcontractorId;
    }

    public void setSubcontractorId(Long subcontractorId) {
        this.subcontractorId = subcontractorId;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getWorkmenType() {
        return workmenType;
    }

    public void setWorkmenType(Long workmenType) {
        this.workmenType = workmenType;
    }

    public Date getInsertDtm() {
        return insertDtm;
    }

    public void setInsertDtm(Date insertDtm) {
        this.insertDtm = insertDtm;
    }

    public Date getUpdateDtm() {
        return updateDtm;
    }

    public void setUpdateDtm(Date updateDtm) {
        this.updateDtm = updateDtm;
    }
}

