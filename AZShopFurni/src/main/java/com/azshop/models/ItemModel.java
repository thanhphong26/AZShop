package com.azshop.models;

public class ItemModel {
	private int itemID;
	private int productID;
	private String color;
	private String colorCode;
	private String size;
	private int stock;
	private int originalPrice;
	private int promotionPrice;
	private String image;
	public ItemModel() {
		super();
	}
	public ItemModel(int itemID, int productID, String color, String colorCode, String size, int stock,
			int originalPrice, int promotionPrice, String image) {
		super();
		this.itemID = itemID;
		this.productID = productID;
		this.color = color;
		this.colorCode = colorCode;
		this.size = size;
		this.stock = stock;
		this.originalPrice = originalPrice;
		this.promotionPrice = promotionPrice;
		this.image = image;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}
	public int getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(int promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	@Override
	public String toString() {
		return "ItemModel [itemID=" + itemID + ", productID=" + productID + ", color=" + color + ", colorCode="
				+ colorCode + ", size=" + size + ", stock=" + stock + ", originalPrice=" + originalPrice
				+ ", promotionPrice=" + promotionPrice + ", image=" + image + "]";
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
