package com.wfd.dot1.cwfm.dto;

public class UserDTO {
    public UserDTO(String userId, String userName, String emailId, String firstName, String lastName,
			String contactNumber, String roleName, String status, String businessType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.emailId = emailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.roleName = roleName;
		this.status = status;
		this.businessType = businessType;
	}
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	private String userId;
    private String userName;
    private String emailId;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String roleName;
    private String status;
    private String businessType;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}
