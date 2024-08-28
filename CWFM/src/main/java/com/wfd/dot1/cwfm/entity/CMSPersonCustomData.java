package com.wfd.dot1.cwfm.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CMSPERSONCUSTOMDATA")
public class CMSPersonCustomData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFID")
    private Long refId;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEEID")
    private CMSPerson employee;

    @ManyToOne
    @JoinColumn(name = "CSTMDEFID")
    private CMSPersonCustomDataDefinition customDataDefinition;

    @Column(name = "CUSTOMDATATEXT", nullable = false, length = 500)
    private String customDataText;

    @Column(name = "EFFECTIVEFROM")
    private Date effectiveFrom;

    @Column(name = "EFFECTIVETILL")
    private Date effectiveTill;

    @Column(name = "CREATEDTM", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDateTime;

    @Column(name = "UPDATEDTM", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updatedDateTime;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public CMSPerson getEmployee() {
		return employee;
	}

	public void setEmployee(CMSPerson employee) {
		this.employee = employee;
	}

	public CMSPersonCustomDataDefinition getCustomDataDefinition() {
		return customDataDefinition;
	}

	public void setCustomDataDefinition(CMSPersonCustomDataDefinition customDataDefinition) {
		this.customDataDefinition = customDataDefinition;
	}

	public String getCustomDataText() {
		return customDataText;
	}

	public void setCustomDataText(String customDataText) {
		this.customDataText = customDataText;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getEffectiveTill() {
		return effectiveTill;
	}

	public void setEffectiveTill(Date effectiveTill) {
		this.effectiveTill = effectiveTill;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    // Getters and setters
}
