package com.wfd.dot1.cwfm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "CMSCONTRACTOR_WC")
public class CMSContractorWC {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wcId;
    private String wcCode;
    private long contractorId;
    private long unitId;
    private Integer natureOfId;
    private Date wcFromDtm;
    private Date wcToDtm;
    private Integer wcTotal;
    private Integer deleteSw;
    private String licenceType;
    private String isVerified;
    private String attachmentNm;
    private int extendToSubcontractor;
    private Date createdDtm;
    private Date updatedDtm;

    public long getWcId() {
        return wcId;
    }

    public void setWcId(long wcId) {
        this.wcId = wcId;
    }

    public String getWcCode() {
        return wcCode;
    }

    public void setWcCode(String wcCode) {
        this.wcCode = wcCode;
    }

    public long getContractorId() {
        return contractorId;
    }

    public void setContractorId(long contractorId) {
        this.contractorId = contractorId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public Integer getNatureOfId() {
        return natureOfId;
    }

    public void setNatureOfId(Integer natureOfId) {
        this.natureOfId = natureOfId;
    }

    public Date getWcFromDtm() {
        return wcFromDtm;
    }

    public void setWcFromDtm(Date wcFromDtm) {
        this.wcFromDtm = wcFromDtm;
    }

    public Date getWcToDtm() {
        return wcToDtm;
    }

    public void setWcToDtm(Date wcToDtm) {
        this.wcToDtm = wcToDtm;
    }

    public Integer getWcTotal() {
        return wcTotal;
    }

    public void setWcTotal(Integer wcTotal) {
        this.wcTotal = wcTotal;
    }

    public Integer getDeleteSw() {
        return deleteSw;
    }

    public void setDeleteSw(Integer deleteSw) {
        this.deleteSw = deleteSw;
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String string) {
        this.isVerified = string;
    }

    public String getAttachmentNm() {
        return attachmentNm;
    }

    public void setAttachmentNm(String attachmentNm) {
        this.attachmentNm = attachmentNm;
    }

    public int getExtendToSubcontractor() {
        return extendToSubcontractor;
    }

    public void setExtendToSubcontractor(int i) {
        this.extendToSubcontractor = i;
    }

    public Date getCreatedDtm() {
        return createdDtm;
    }

    public void setCreatedDtm(Date createdDtm) {
        this.createdDtm = createdDtm;
    }

    public Date getUpdatedDtm() {
        return updatedDtm;
    }

    public void setUpdatedDtm(Date updatedDtm) {
        this.updatedDtm = updatedDtm;
    }
    
}
