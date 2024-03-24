package com.azshop.models;

public class CategoryModel {
	private int categoryID;
	private String categoryName;
	private int parentCategoryID;
	private String image;

	public CategoryModel() {
		super();
	}

	public CategoryModel(int categoryID, String categoryName, int parentCategoryID, String image) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.parentCategoryID = parentCategoryID;
		this.image = image;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getParentCategoryID() {
		return parentCategoryID;
	}
	
	public String getImage() {
		return image;
	}

	public void setParentCategoryID(int parentCategoryID) {
		this.parentCategoryID = parentCategoryID;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "CategoryModel [categoryID=" + categoryID + ", categoryName=" + categoryName + ", parentCategoryID="
				+ parentCategoryID + ", image=" + image + "]";
	}

}
