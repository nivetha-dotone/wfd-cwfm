package com.wfd.dot1.cwfm.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERLOGIN")
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USRLGINID")
    private Long id;

    @Column(name = "LOGINID", unique = true)
    private String loginId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CONTACTEMAIL")
    private String contactEmail;

    @Column(name = "CONTACTNUMBER")
    private Long contactNumber;

    @Column(name = "ACCTSTATUS")
    private Integer accountStatus;

    @Column(name = "ACCTLOCKED")
    private Integer accountLocked;

    @Column(name = "WRONGPWDATTEMPT")
    private Integer wrongPasswordAttempt;

    @Column(name = "USERROLE")
    private String userRole;

    @Column(name = "UNITID")
    private Long unitId;

    @Column(name = "CONTRACTORID")
    private Long contractorId;
    
    @Column(name = "DEPTID")
    private Long departmentId;

    @Column(name = "LASTPWDCHANGED")
    private LocalDateTime lastPasswordChanged;

    @Column(name = "UPDATEDTM")
    private LocalDateTime updatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Integer getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Integer accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Integer getWrongPasswordAttempt() {
		return wrongPasswordAttempt;
	}

	public void setWrongPasswordAttempt(Integer wrongPasswordAttempt) {
		this.wrongPasswordAttempt = wrongPasswordAttempt;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public LocalDateTime getLastPasswordChanged() {
		return lastPasswordChanged;
	}

	public void setLastPasswordChanged(LocalDateTime lastPasswordChanged) {
		this.lastPasswordChanged = lastPasswordChanged;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

    // getters and setters
}

