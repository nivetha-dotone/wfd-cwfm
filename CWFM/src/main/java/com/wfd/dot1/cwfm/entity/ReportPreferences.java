package com.wfd.dot1.cwfm.entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTPREFERENCES")
public class ReportPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RPTREFID")
    private Long rptRefId;

    @Column(name = "RPTLINK")
    private String rptLink;

    @Column(name = "RPTNAME")
    private String rptName;

    @Column(name = "RPTDESCRIPTION")
    private String rptDescription;

//    @Column(name = "PARAMETERS")
//    private String parameters; // New attribute for parameters

    @Column(name = "ISACTIVE")
    private Byte isActive;

    @Column(name = "UPDATEDTM")
    private LocalDateTime updatedDtm;

    // Getters and setters
    public Long getRptRefId() {
        return rptRefId;
    }

    public void setRptRefId(Long rptRefId) {
        this.rptRefId = rptRefId;
    }

    public String getRptLink() {
        return rptLink;
    }

    public void setRptLink(String rptLink) {
        this.rptLink = rptLink;
    }

    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    public String getRptDescription() {
        return rptDescription;
    }

    public void setRptDescription(String rptDescription) {
        this.rptDescription = rptDescription;
    }

//    public String getParameters() {
//        return parameters;
//    }
//
//    public void setParameters(String parameters) {
//        this.parameters = parameters;
//    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getUpdatedDtm() {
        return updatedDtm;
    }

    public void setUpdatedDtm(LocalDateTime updatedDtm) {
        this.updatedDtm = updatedDtm;
    }
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "REPORT_PARAMETERS",
//               joinColumns = @JoinColumn(name = "report_id"),
//               inverseJoinColumns = @JoinColumn(name = "parameter_id"))
//    private List<GeneralMaster> selectedParameters;
//    public List<GeneralMaster> getSelectedParameters() {
//        return selectedParameters;
//    }
//
//    public void setSelectedParameters(List<GeneralMaster> selectedParameters) {
//        this.selectedParameters = selectedParameters;
//    }
//    public List<Long> getSelectedParameterIds() {
//        // Assuming parameters are stored as a comma-separated string of IDs
//        List<Long> selectedParameterIds = new ArrayList<>();
//        if (parameters != null && !parameters.isEmpty()) {
//            String[] parameterIds = parameters.split(",");
//            for (String parameterId : parameterIds) {
//                selectedParameterIds.add(Long.valueOf(parameterId.trim()));
//            }
//        }
//        return selectedParameterIds;
//    }
}
