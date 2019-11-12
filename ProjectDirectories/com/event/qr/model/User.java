package com.event.qr.model;



public class User {
	private  int id;
private  int partyId;
private  String name;
private  String mobileNo;
private  String emailId;
private  String username;
private  String password;
private  String userImage;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getPartyId() {
	return partyId;
}

public void setPartyId(int partyid) {
	this.partyId = partyid;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getMobileNo() {
	return mobileNo;
}

public void setMobileNo(String mobileno) {
	this.mobileNo = mobileno;
}

public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailid) {
	this.emailId = emailid;
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

public String getUserImage() {
	return userImage;
}

public void setUserImage(String userimage) {
	this.userImage = userimage;
}

}