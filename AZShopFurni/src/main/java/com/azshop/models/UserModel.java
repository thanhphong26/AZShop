package com.azshop.models;

import java.util.Date;

public class UserModel {
	private int userID;
	private String firstName;
	private String lastName;
	private String address;
	private int gender;
	private String phone;
	private Date dob;
	private String cid;
	private int type;
	private int kpi;
	private String area;
	private String avatar;
	private String email;
	public UserModel() {
		super();
	}
	public UserModel(int userID, String firstName, String lastName, String address, int gender, String phone, Date dob,
			String cid, int type, int kpi, String area, String avatar, String email) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
		this.phone = phone;
		this.dob = dob;
		this.cid = cid;
		this.type = type;
		this.kpi = kpi;
		this.area = area;
		this.avatar = avatar;
		this.email = email;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getKpi() {
		return kpi;
	}
	public void setKpi(int kpi) {
		this.kpi = kpi;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserModel [userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", gender=" + gender + ", phone=" + phone + ", dob=" + dob + ", cid=" + cid + ", type="
				+ type + ", kpi=" + kpi + ", area=" + area + ", avatar=" + avatar + "]";
	}
	
}
