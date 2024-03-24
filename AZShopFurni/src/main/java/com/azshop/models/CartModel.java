package com.azshop.models;

public class CartModel {
	private int customerID;
	private int itemID;
	private int quantity;
	private String color;
	private String size;
	private int promotionPrice;
	private String productName;
	private int totalPrice;
	private String image;
	private int productID;

	public CartModel() {
		super();
	}

	public CartModel(int customerID, int itemID, int quantity, String color, String size, int promotionPrice,
			String productName, int totalPrice, String image, int productID) {
		super();
		this.customerID = customerID;
		this.itemID = itemID;
		this.quantity = quantity;
		this.color = color;
		this.size = size;
		this.promotionPrice = promotionPrice;
		this.productName = productName;
		this.totalPrice = totalPrice;
		this.image = image;
		this.productID = productID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(int promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "CartModel [customerID=" + customerID + ", itemID=" + itemID + ", quantity=" + quantity + ", color="
				+ color + ", size=" + size + ", promotionPrice=" + promotionPrice + ", productName=" + productName
				+ ", totalPrice=" + totalPrice + ", image= " + image +"]";
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

}
