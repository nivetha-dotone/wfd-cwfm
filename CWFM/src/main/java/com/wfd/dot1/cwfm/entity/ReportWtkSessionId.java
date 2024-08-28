package com.wfd.dot1.cwfm.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTWTKSESSIONID")
public class ReportWtkSessionId {
    @Id
    @Column(name = "REFID")
    private String refId;
    
    @Column(name = "PERSONREFID")
    private Long personRefId;
    
    @Column(name = "FROMDATE")
    private LocalDate fromDate;
    
    @Column(name = "TODATE")
    private LocalDate toDate;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Long getPersonRefId() {
		return personRefId;
	}

	public void setPersonRefId(Long personRefId) {
		this.personRefId = personRefId;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

    // Getters and setters
}
