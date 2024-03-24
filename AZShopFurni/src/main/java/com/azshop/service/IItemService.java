package com.azshop.service;

import java.util.List;

import com.azshop.models.ItemModel;
import com.azshop.models.ProductModel;

public interface IItemService {
	List<ItemModel> findAll();
	List<ItemModel> findByProductID(int productID);
	List<ItemModel> findAllByProductID(int productID);
	ItemModel findOne(int id);
	void insertItem (ItemModel model);
	void deleteItem (int IteID);
	void updateItem (ItemModel model);
	ItemModel findOneByProductID(int productID);
	public int CreateItemID(int Id);
}
