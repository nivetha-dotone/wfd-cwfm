package com.wfd.dot1.cwfm.pojo;

import java.util.Date;
import java.util.List;

public class OrgLevelMapping {
    private int orgLevelEntryId;
    private int orgAcctSetId;
    private Date updatedTm;
    private String shortName; // Short name for display
    private String longDescription; // Long description for display
    private List<String> selectedEntryIds;  // List of selected ORGLEVELENTRYID values

    // Getters and Setters

    public List<String> getSelectedEntryIds() {
        return selectedEntryIds;
    }

    public void setSelectedEntryIds(List<String> selectedEntryIds) {
        this.selectedEntryIds = selectedEntryIds;
    }
    // Default constructor
    public OrgLevelMapping() {}

    // Constructor with fields
    public OrgLevelMapping(int orgLevelEntryId, int orgAcctSetId, Date updatedTm, String shortName, String longDescription) {
        this.orgLevelEntryId = orgLevelEntryId;
        this.orgAcctSetId = orgAcctSetId;
        this.updatedTm = updatedTm;
        this.shortName = shortName;
        this.longDescription = longDescription;
    }

    // Getter and Setter methods
    public int getOrgLevelEntryId() {
        return orgLevelEntryId;
    }

    public void setOrgLevelEntryId(int orgLevelEntryId) {
        this.orgLevelEntryId = orgLevelEntryId;
    }

    public int getOrgAcctSetId() {
        return orgAcctSetId;
    }

    public void setOrgAcctSetId(int orgAcctSetId) {
        this.orgAcctSetId = orgAcctSetId;
    }

    public Date getUpdatedTm() {
        return updatedTm;
    }

    public void setUpdatedTm(Date updatedTm) {
        this.updatedTm = updatedTm;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    
    @Override
    public String toString() {
        return "OrgLevelMapping{" +
               "shortName='" + shortName + '\'' +
               ", longDescription='" + longDescription + '\'' +
               '}';
    }
}
