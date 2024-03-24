package com.azshop.models;

public class SupplierModel {
	private int supplierID;
	private String supplierName;
	public SupplierModel() {
		super();
	}
	public SupplierModel(int supplierID, String supplierName) {
		super();
		this.supplierID = supplierID;
		this.supplierName = supplierName;
	}
	public int getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Override
	public String toString() {
		return "SupplierModel [supplierID=" + supplierID + ", supplierName=" + supplierName + "]";
	}	
	
}
