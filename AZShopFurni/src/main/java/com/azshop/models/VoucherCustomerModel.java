package com.azshop.models;

public class VoucherCustomerModel {
	private int voucherID;
	private int customerID;
	public VoucherCustomerModel() {
		super();
	}
	public VoucherCustomerModel(int voucherID, int customerID) {
		super();
		this.voucherID = voucherID;
		this.customerID = customerID;
	}
	public int getVoucherID() {
		return voucherID;
	}
	public void setVoucherID(int voucherID) {
		this.voucherID = voucherID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	@Override
	public String toString() {
		return "VoucherCustomerModel [voucherID=" + voucherID + ", customerID=" + customerID + "]";
	}
	
}
