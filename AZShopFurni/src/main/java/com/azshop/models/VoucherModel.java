package com.azshop.models;

import java.util.Date;

public class VoucherModel {
	private int voucherID;
	private String description;
	private int discount;
	private int minimumPrice;
	private int quantity;
	private Date mfg;
	private Date exp;
	public VoucherModel() {
		super();
	}
	public VoucherModel(int voucherID, String description, int discount, int minimumPrice, int quantity, Date mfg,
			Date exp) {
		super();
		this.voucherID = voucherID;
		this.description = description;
		this.discount = discount;
		this.minimumPrice = minimumPrice;
		this.quantity = quantity;
		this.mfg = mfg;
		this.exp = exp;
	}
	public int getVoucherID() {
		return voucherID;
	}
	public void setVoucherID(int voucherID) {
		this.voucherID = voucherID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(int minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getMfg() {
		return mfg;
	}
	public void setMfg(Date mfg) {
		this.mfg = mfg;
	}
	public Date getExp() {
		return exp;
	}
	public void setExp(Date exp) {
		this.exp = exp;
	}
	@Override
	public String toString() {
		return "VoucherModel [voucherID=" + voucherID + ", description=" + description + ", discount=" + discount
				+ ", minimumPrice=" + minimumPrice + ", quantity=" + quantity + ", mfg=" + mfg + ", exp=" + exp + "]";
	}
	
}
