package com.wfd.dot1.cwfm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CMSCONTRACTOR")
public class CMSContractor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long contractorId;
    private String name;
    private String address;
    private String code;
    private boolean isBlocked;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean b) {
        this.isBlocked = b;
    }
    
//    public CMSPrincipalEmployer getPrincipalEmployer() {
//		return principalEmployer;
//	}
//
//	public void setPrincipalEmployer(CMSPrincipalEmployer principalEmployer) {
//		this.principalEmployer = principalEmployer;
//	}

	public CMSContrPemm getContrPEMM() {
		return contrPEMM;
	}

	public void setContrPEMM(CMSContrPemm contrPEMM) {
		this.contrPEMM = contrPEMM;
	}

//	public WorkOrderLLWC getWorkOrderLLWC() {
//		return workOrderLLWC;
//	}
//
//	public void setWorkOrderLLWC(WorkOrderLLWC workOrderLLWC) {
//		this.workOrderLLWC = workOrderLLWC;
//	}
//
//	public CMSContractorWC getContracorWC() {
//		return contracorWC;
//	}
//
//	public void setContracorWC(CMSContractorWC contracorWC) {
//		this.contracorWC = contracorWC;
//	}
//
//	public CMSSubcontractor getSubcontractor() {
//		return subcontractor;
//	}
//
//	public void setSubcontractor(CMSSubcontractor subcontractor) {
//		this.subcontractor = subcontractor;
//	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

//	@OneToOne(mappedBy = "contractor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private CMSPrincipalEmployer principalEmployer;

    @OneToOne(mappedBy = "contractor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CMSContrPemm contrPEMM;

//    @OneToOne(mappedBy = "contractor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private WorkOrderLLWC workOrderLLWC;
//
//    @OneToOne(mappedBy = "contractor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private CMSContractorWC contracorWC;
//
//    @OneToOne(mappedBy = "contractor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private CMSSubcontractor subcontractor;

}