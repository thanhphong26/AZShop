package com.azshop.service;

import java.util.List;

import com.azshop.models.ItemImageModel;

public interface IItemImageService {
	List<ItemImageModel> findByProductID(int productID);
    void insertItemImage (ItemImageModel ItemId);
	void deleteItemImage (int IteamId);
	void updateItemImage (ItemImageModel ItemId);
	int CreateItemimageID(int Id);
}
