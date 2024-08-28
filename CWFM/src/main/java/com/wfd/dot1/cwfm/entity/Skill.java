package com.wfd.dot1.cwfm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CMSSKILL")
public class Skill {
    
    @Id
    @Column(name = "SKILLID")
    private int skillId;
    
    @Column(name = "SKILLNM")
    private String skillName;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "ISACTIVE")
    private int isActive;
    
    @Column(name = "CREATEDDTM")
    private Date createdDateTime;

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
    
    // Constructors, getters, and setters
}

