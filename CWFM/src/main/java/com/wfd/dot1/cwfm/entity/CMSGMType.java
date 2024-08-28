package com.wfd.dot1.cwfm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CMSGMTYPE")
public class CMSGMType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  gmTypeId;
    
    private String gmType;

    // Constructors, getters, and setters
    // Omitted for brevity

    public Long  getGmTypeId() {
        return gmTypeId;
    }

    public void setGmTypeId(Long  gmTypeId) {
        this.gmTypeId = gmTypeId;
    }

    public String getGmType() {
        return gmType;
    }

    public void setGmType(String gmType) {
        this.gmType = gmType;
    }
}

