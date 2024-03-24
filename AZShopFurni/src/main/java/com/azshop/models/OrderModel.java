package com.azshop.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderModel {
	private int orderID;
	private Date orderDate;
	private String address;
	private String city;
	private int status;
	private int transportFee;
	private int discount;
	private int totalMoney;
	private String note;
	private Date deliveryTime;
	private int customerConfirmation;
	private int customerID;
	private int sellerID;
	private int shipperID;

	private List<DetailModel> details = new ArrayList<DetailModel>();
	private UserModel customer = new UserModel();
	private UserModel seller = new UserModel();
	private UserModel shipper = new UserModel();
	private PaymentModel payment = new PaymentModel();

	public OrderModel() {
		super();
	}

	public OrderModel(int orderID, Date orderDate, String address, String city, int status, int transportFee,
			int discount, int totalMoney, String note, Date deliveryTime, int customerConfirmation, int customerID,
			int sellerID, int shipperID) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.address = address;
		this.city = city;
		this.status = status;
		this.transportFee = transportFee;
		this.discount = discount;
		this.totalMoney = totalMoney;
		this.note = note;
		this.deliveryTime = deliveryTime;
		this.customerConfirmation = customerConfirmation;
		this.customerID = customerID;
		this.sellerID = sellerID;
		this.shipperID = shipperID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(int transportFee) {
		this.transportFee = transportFee;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getCustomerConfirmation() {
		return customerConfirmation;
	}

	public void setCustomerConfirmation(int customerConfirmation) {
		this.customerConfirmation = customerConfirmation;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getSellerID() {
		return sellerID;
	}

	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}

	public int getShipperID() {
		return shipperID;
	}

	public void setShipperID(int shipperID) {
		this.shipperID = shipperID;
	}

	public List<DetailModel> getDetails() {
		return details;
	}

	public void setDetails(List<DetailModel> details) {
		this.details = details;
	}

	public UserModel getCustomer() {
		return customer;
	}

	public void setCustomer(UserModel customer) {
		this.customer = customer;
	}

	public UserModel getSeller() {
		return seller;
	}

	public void setSeller(UserModel seller) {
		this.seller = seller;
	}

	public UserModel getShipper() {
		return shipper;
	}

	public void setShipper(UserModel shipper) {
		this.shipper = shipper;
	}

	public PaymentModel getPayment() {
		return payment;
	}

	public void setPayment(PaymentModel payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "OrderModel [orderID=" + orderID + ", orderDate=" + orderDate + ", address=" + address + ", city=" + city
				+ ", status=" + status + ", transportFee=" + transportFee + ", discount=" + discount + ", totalMoney="
				+ totalMoney + ", note=" + note + ", deliveryTime=" + deliveryTime + ", customerConfirmation="
				+ customerConfirmation + ", customerID=" + customerID + ", sellerID=" + sellerID + ", shipperID="
				+ shipperID + "]";
	}
}
