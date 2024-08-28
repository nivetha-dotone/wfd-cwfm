package com.wfd.dot1.cwfm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "CMSGENERALMASTER")
public class GeneralMaster {
//    private int gmtypeid;
//    @Column(name = "GMTYPE")
//    private String gmtype;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GMID")
    private long gmid;
    @Column(name = "GMNAME")
    private String gmname;
    
    private String gmdescription;
    @Column(name = "ISACTIVE")
    private boolean isActive;
    @Column(name = "CREATEDTM")
    private Date createdtm;
    @Column(name = "UPDATEDTM")
    private Date updatedtm;
    @Column(name = "UPDATEDBY")
    private String updatedby;

    @ManyToOne
    @JoinColumn(name = "GMTYPEID")
    private CMSGMType gmType;
    // Constructors
    public GeneralMaster() {
    }
    @Transient
    private String value;

    // Constructor that accepts a String parameter
    public GeneralMaster(String value) {
        this.setValue(value);
    }
    // Getters and setters

    public long getGmid() {
        return gmid;
    }

    public void setGmid(long gmid) {
        this.gmid = gmid;
    }

    public String getGmname() {
        return gmname;
    }

    public void setGmname(String gmname) {
        this.gmname = gmname;
    }

    public String getGmdescription() {
        return gmdescription;
    }

    public void setGmdescription(String gmdescription) {
        this.gmdescription = gmdescription;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedtm() {
        return createdtm;
    }

    public void setCreatedtm(Date createdtm) {
        this.createdtm = createdtm;
    }

    public Date getUpdatedtm() {
        return updatedtm;
    }

    public void setUpdatedtm(Date updatedtm) {
        this.updatedtm = updatedtm;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    @Override
    public String toString() {
        return "GeneralMaster{" +
                "gmID=" + gmid +
                ", gmName='" + gmname + '\'' +
                ", gmDescription='" + gmdescription + '\'' +
                ", gmType=" + gmType + // Assuming you have a proper toString() method for CMSGMType
                ", isActive=" + isActive +
                ", createdTM=" + createdtm +
                ", updatedTM=" + updatedtm +
                ", updatedBy='" + updatedby + '\'' +
                '}';
    }
	public CMSGMType getGmType() {
		return gmType;
	}

	public void setGmType(CMSGMType gmType) {
		this.gmType = gmType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
