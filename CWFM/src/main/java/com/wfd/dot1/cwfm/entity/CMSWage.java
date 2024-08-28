package com.wfd.dot1.cwfm.entity;

import javax.persistence.*;

@Entity
@Table(name = "CMSWAGE")
public class CMSWage {

    @Id
    @Column(name = "WAGEID")
    private int wageId;

    @Column(name = "BASIC")
    private float basic;

    @Column(name = "DA")
    private float da;

    @Column(name = "ALLOWANCE")
    private float allowance;

	public int getWageId() {
		return wageId;
	}

	public void setWageId(int wageId) {
		this.wageId = wageId;
	}

	public float getBasic() {
		return basic;
	}

	public void setBasic(float basic) {
		this.basic = basic;
	}

	public float getDa() {
		return da;
	}

	public void setDa(float da) {
		this.da = da;
	}

	public float getAllowance() {
		return allowance;
	}

	public void setAllowance(float allowance) {
		this.allowance = allowance;
	}

    // Getters and setters
}

