package com.wfd.dot1.cwfm.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CMSPERSON")
public class CMSPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEEID")
    private int employeeId;

    @Column(name = "EMPLOYEECODE", nullable = false)
    private String employeeCode;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "RELATIONNAME", nullable = false)
    private String relationName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Column(name = "DATEOFBIRTH", nullable = false)
    private Date dateOfBirth;

    @Column(name = "DATEOFJOINING", nullable = false)
    private Date dateOfJoining;

    @Column(name = "DATEOFTERMINATION", nullable = false)
    private Date dateOfTermination;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOODGROUP")
    private GeneralMaster bloodGroup;

    @Column(name = "HAZARDOUSAREA")
    private String hazardousArea;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "GENDER")
    private GeneralMaster gender;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACADEMICS")
    private GeneralMaster academics;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOMODATION")
    private GeneralMaster accomodation;

    @Column(name = "BANKBRANCH")
    private String bankBranch;

    @Column(name = "ACCOUNTNO")
    private String accountNo;

    @Column(name = "EMERGENCYNAME")
    private String emergencyName;

    @Column(name = "EMERGENCYNUMBER")
    private String emergencyNumber;

    @Column(name = "MOBILENUMBER")
    private String mobileNumber;

    @Column(name = "ACCESSLEVEL")
    private Byte accessLevel;

    @Column(name = "ESICNUMBER")
    private String esicNumber;

    @Column(name = "UANNUMBER")
    private String uanNumber;

    @Column(name = "ISPFELIGIBLE")
    private Byte ispfEligible;

    @Column(name = "IDMARK")
    private String idMark;

    @Column(name = "PANNO")
    private String panno;

    @Column(name = "CREATEDTM", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDateTime;

    @Column(name = "UPDATEDTM", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updatedDateTime;

    @Column(name = "UPDATEDBY")
    private String updatedBy;
    private String unitId;
    private String departmentId;
    @Column(name = "contractorId", insertable = false, updatable = false)
    private String contractorId;
    private String skillId;
    private String tradeId;
    @Column(name = "AADHARNUMBER")
    private String aadharNumber;
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getDateOfTermination() {
		return dateOfTermination;
	}

	public void setDateOfTermination(Date dateOfTermination) {
		this.dateOfTermination = dateOfTermination;
	}

	public GeneralMaster getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(GeneralMaster bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getHazardousArea() {
		return hazardousArea;
	}

	public void setHazardousArea(String hazardousArea) {
		this.hazardousArea = hazardousArea;
	}

	public GeneralMaster getGender() {
		return gender;
	}

	public void setGender(GeneralMaster gender) {
		this.gender = gender;
	}

	public GeneralMaster getAcademics() {
		return academics;
	}

	public void setAcademics(GeneralMaster academics) {
		this.academics = academics;
	}

	public GeneralMaster getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(GeneralMaster accommodation) {
		this.accomodation = accommodation;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getEmergencyName() {
		return emergencyName;
	}

	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}

	public String getEmergencyNumber() {
		return emergencyNumber;
	}

	public void setEmergencyNumber(String emergencyNumber) {
		this.emergencyNumber = emergencyNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Byte getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(Byte accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getEsicNumber() {
		return esicNumber;
	}

	public void setEsicNumber(String esicNumber) {
		this.esicNumber = esicNumber;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public Byte getIspfEligible() {
		return ispfEligible;
	}

	public void setIspfEligible(Byte ispEligible) {
		this.ispfEligible = ispEligible;
	}

	public String getIdMark() {
		return idMark;
	}

	public void setIdMark(String idMark) {
		this.idMark = idMark;
	}

	public String getPanNo() {
		return panno;
	}

	public void setPanNo(String panNumber) {
		this.panno = panNumber;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	 public CMSPerson() {
	        // Constructor body, if needed
	    }
	  public CMSPerson(int employeeId, String employeeCode, String firstName, String relationName, String lastName,
              Date dateOfBirth, Date dateOfJoining, Date dateOfTermination, String bloodGroup,
              String hazardousArea, String gender, String academics, String accommodation,
              String bankBranch, String accountNo, String emergencyName, String emergencyNumber,
              String mobileNumber, Byte accessLevel, String esicNumber, String uanNumber,
              Byte ispEligible, String idMark, String panNumber, Date createdDateTime,
              Date updatedDateTime, String updatedBy, String unitId, String departmentId,
              String contractorId, String skillId, String tradeId) {
 this.employeeId = employeeId;
 this.employeeCode = employeeCode;
 this.firstName = firstName;
 this.relationName = relationName;
 this.lastName = lastName;
 this.dateOfBirth = dateOfBirth;
 this.dateOfJoining = dateOfJoining;
 this.dateOfTermination = dateOfTermination;
 this.bloodGroup = convertToGeneralMaster(bloodGroup); 
 this.hazardousArea = hazardousArea;
 this.gender = convertToGeneralMaster(gender);
 this.academics = convertToGeneralMaster(academics);
 this.accomodation = convertToGeneralMaster(accommodation);
 this.bankBranch = bankBranch;
 this.accountNo = accountNo;
 this.emergencyName = emergencyName;
 this.emergencyNumber = emergencyNumber;
 this.mobileNumber = mobileNumber;
 this.accessLevel = accessLevel;
 this.esicNumber = esicNumber;
 this.uanNumber = uanNumber;
 this.ispfEligible = ispEligible;
 this.idMark = idMark;
 this.panno = panNumber;
 this.createdDateTime = createdDateTime;
 this.updatedDateTime = updatedDateTime;
 this.updatedBy = updatedBy;
 this.unitId = unitId;
 this.departmentId = departmentId;
 this.contractorId = contractorId;
 this.skillId = skillId;
 this.tradeId = tradeId;
}
//	public CMSPerson(Long employeeId, String employeeCode, String firstName, String relationName, String lastName, Date dateOfBirth, Date dateOfJoining, Date dateOfTermination, String bloodGroup, String hazardousArea, String gender, String academics, String accommodation, String bankBranch, String accountNo, String emergencyName, String emergencyNumber, String mobileNumber, int accessLevel, String esicNumber, String uanNumber, int ispEligible, String idMark, String panNumber, Date createdDateTime, Date updatedDateTime, String updatedBy, String unitId, String departmentId, String contractorId, String skillId, String tradeId) {
//	    this.employeeId = employeeId;
//	    this.employeeCode = employeeCode;
//	    this.firstName = firstName;
//	    this.relationName = relationName;
//	    this.lastName = lastName;
//	    this.dateOfBirth = dateOfBirth;
//	    this.dateOfJoining = dateOfJoining;
//	    this.dateOfTermination = dateOfTermination;
//	    this.bloodGroup = bloodGroup;
//	    this.hazardousArea = hazardousArea;
//	    this.gender = gender;
//	    this.academics = academics;
//	    this.accommodation = accommodation;
//	    this.bankBranch = bankBranch;
//	    this.accountNo = accountNo;
//	    this.emergencyName = emergencyName;
//	    this.emergencyNumber = emergencyNumber;
//	    this.mobileNumber = mobileNumber;
//	    this.accessLevel = accessLevel;
//	    this.esicNumber = esicNumber;
//	    this.uanNumber = uanNumber;
//	    this.ispEligible = ispEligible;
//	    this.idMark = idMark;
//	    this.panNumber = panNumber;
//	    this.createdDateTime = createdDateTime;
//	    this.updatedDateTime = updatedDateTime;
//	    this.updatedBy = updatedBy;
//	    this.unitId = unitId;
//	    this.departmentId = departmentId;
//	    this.contractorId = contractorId;
//	    this.skillId = skillId;
//	    this.tradeId = tradeId;
//	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	private GeneralMaster convertToGeneralMaster(String value) {
	    // Assuming there's a static method in GeneralMaster class to convert String to GeneralMaster
	    return new GeneralMaster(value);
	}
    // Getters and setters
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNITID")
    private CMSPrincipalEmployer unit;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "DEPARTMENT_ID")
//    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractorId")
    private CMSContractor contractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKILL_ID")
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRADE_ID")
    private Trade trade;
	public String getPanno() {
		return panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	public CMSPrincipalEmployer getUnit() {
		return unit;
	}

	public void setUnit(CMSPrincipalEmployer unit) {
		this.unit = unit;
	}

	public CMSContractor getContractor() {
		return contractor;
	}

	public void setContractor(CMSContractor contractor) {
		this.contractor = contractor;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
}
