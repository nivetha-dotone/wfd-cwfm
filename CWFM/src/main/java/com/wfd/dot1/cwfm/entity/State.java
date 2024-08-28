package com.wfd.dot1.cwfm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CMSSTATE")
public class State {
    
    public State() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
    @Column(name = "STATEID")
    private int stateId;
    
    @Column(name = "STATENM")
    private String stateName;
    
    public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	

    // Constructors, getters, and setters
}
