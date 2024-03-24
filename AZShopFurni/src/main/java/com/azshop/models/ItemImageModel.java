package com.azshop.models;

public class ItemImageModel {
	private int itemimageID;
	private int itemID;
	private String image;
	public ItemImageModel() {
		super();
	}
	public ItemImageModel(int itemimageID, int itemID, String image) {
		super();
		this.itemimageID = itemimageID;
		this.itemID = itemID;
		this.image = image;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getItemimageID() {
		return itemimageID;
	}
	public void setItemimageID(int itemimageID) {
		this.itemimageID = itemimageID;
	}
	@Override
	public String toString() {
		return "ItemImageModel [itemimageID=" + itemimageID + ", itemID=" + itemID + ", image=" + image + "]";
	}
}
