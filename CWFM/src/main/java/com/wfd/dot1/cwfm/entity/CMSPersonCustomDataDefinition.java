package com.wfd.dot1.cwfm.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CMSPERSONCUSTOMDATADEFINITION")
public class CMSPersonCustomDataDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CSTMDEFID")
    private Integer customDefinitionId;

    @Column(name = "CSTMDEFNAME", nullable = false)
    private String customDefinitionName;

    @Column(name = "CSTMDEFDESCRIPTION", length = 1000)
    private String customDefinitionDescription;

    @Column(name = "ISACTIVE", columnDefinition = "TINYINT DEFAULT 1")
    private Byte isActive;

    @Column(name = "CREATEDTM", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDateTime;

    @Column(name = "UPDATEDTM", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updatedDateTime;

	public Integer getCustomDefinitionId() {
		return customDefinitionId;
	}

	public void setCustomDefinitionId(Integer customDefinitionId) {
		this.customDefinitionId = customDefinitionId;
	}

	public String getCustomDefinitionName() {
		return customDefinitionName;
	}

	public void setCustomDefinitionName(String customDefinitionName) {
		this.customDefinitionName = customDefinitionName;
	}

	public String getCustomDefinitionDescription() {
		return customDefinitionDescription;
	}

	public void setCustomDefinitionDescription(String customDefinitionDescription) {
		this.customDefinitionDescription = customDefinitionDescription;
	}

	public Byte getIsActive() {
		return isActive;
	}

	public void setIsActive(Byte isActive) {
		this.isActive = isActive;
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

    // Getters and setters
}
