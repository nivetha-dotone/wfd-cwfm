package com.wfd.dot1.cwfm.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTEXECUTIONSTATUS")
public class ReportExecutionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RPEEXNSTSID")
    private Long rpeExnstsid;

    @Column(name = "RPTREFID")
    private Long rptRefId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "RPTFROMDATE")
    private LocalDate rptFromDate;

    @Column(name = "RPTTODATE")
    private LocalDate rptToDate;

    @Column(name = "RPTPARAM")
    private String rptParam;

    @Column(name = "RPTSTATUS")
    private Character rptStatus;

    @Column(name = "RPTREQUESTEDON")
    private LocalDateTime rptRequestedOn;

	public Long getRpeExnstsid() {
		return rpeExnstsid;
	}

	public void setRpeExnstsid(Long rpeExnstsid) {
		this.rpeExnstsid = rpeExnstsid;
	}

	public Long getRptRefId() {
		return rptRefId;
	}

	public void setRptRefId(Long rptRefId) {
		this.rptRefId = rptRefId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDate getRptFromDate() {
		return rptFromDate;
	}

	public void setRptFromDate(LocalDate rptFromDate) {
		this.rptFromDate = rptFromDate;
	}

	public LocalDate getRptToDate() {
		return rptToDate;
	}

	public void setRptToDate(LocalDate rptToDate) {
		this.rptToDate = rptToDate;
	}

	public String getRptParam() {
		return rptParam;
	}

	public void setRptParam(String rptParam) {
		this.rptParam = rptParam;
	}

	public Character getRptStatus() {
		return rptStatus;
	}

	public void setRptStatus(Character rptStatus) {
		this.rptStatus = rptStatus;
	}

	public LocalDateTime getRptRequestedOn() {
		return rptRequestedOn;
	}

	public void setRptRequestedOn(LocalDateTime rptRequestedOn) {
		this.rptRequestedOn = rptRequestedOn;
	}

    // Getters and setters
}
