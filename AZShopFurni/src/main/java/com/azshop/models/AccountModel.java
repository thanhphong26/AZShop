package com.azshop.models;

public class AccountModel {
	private int userID;
	private String userName;
	private String password;
	public AccountModel() {
		super();
	}
	public AccountModel(int userID, String userName, String password) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.password = password;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "AccountModel [userID=" + userID + ", userName=" + userName + ", password=" + password + "]";
	}
	
}
