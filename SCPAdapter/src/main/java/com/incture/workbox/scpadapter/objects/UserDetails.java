package com.incture.workbox.scpadapter.objects;

public class UserDetails {

	public UserDetails() {
		super();
	}

	public UserDetails(String userId, String emailId, String firstName, String lastName, String mobileNo,
			String displayName) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.displayName = displayName;
	}

	private String userId;
	private String emailId;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String displayName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", emailId=" + emailId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", mobileNo=" + mobileNo + ", displayName=" + displayName + "]";
	}

}
