package com.azshop.dao;

import java.util.List;

import com.azshop.models.ItemImageModel;

public interface IItemImageDAO {
	List<ItemImageModel> findByProductID(int productID);
    void insertItemImage (ItemImageModel ItemId);
	void deleteItemImage (int IteamId);
	void updateItemImage (ItemImageModel ItemId);
}
