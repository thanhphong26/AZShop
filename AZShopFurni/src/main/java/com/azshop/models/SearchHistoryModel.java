package com.azshop.models;

import java.sql.Timestamp;

import com.google.api.client.util.DateTime;

import jnr.posix.Times;

public class SearchHistoryModel {
	private int customerID;
	private Timestamp createdAt;
	private String history;
	public SearchHistoryModel() {
		super();
	}
	public SearchHistoryModel(int customerID, Timestamp createdAt, String history) {
		super();
		this.customerID = customerID;
		this.createdAt = createdAt;
		this.history = history;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	@Override
	public String toString() {
		return "SearchHistoryModel [customerID=" + customerID + ", createdAt=" + createdAt + ", history=" + history
				+ "]";
	}
	
}
