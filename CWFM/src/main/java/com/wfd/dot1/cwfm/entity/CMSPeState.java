package com.wfd.dot1.cwfm.entity;


	import javax.persistence.Entity;
	import javax.persistence.Id;
	import javax.persistence.Table;

	@Entity
	@Table(name = "CMSPESTATE")
	public class CMSPeState {
	    
	    @Id
	    private int UNITID;
	    
	    private Long  STATEID;

	    // Getters and setters
	    public int getUNITID() {
	        return UNITID;
	    }

	    public void setUNITID(int UNITID) {
	        this.UNITID = UNITID;
	    }

	    public Long  getSTATEID() {
	        return STATEID;
	    }

	    public void setSTATEID(Long  STATEID) {
	        this.STATEID = STATEID;
	    }
	}

