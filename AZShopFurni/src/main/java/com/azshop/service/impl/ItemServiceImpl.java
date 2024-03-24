package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IItemDAO;
import com.azshop.dao.impl.ItemDAOImpl;
import com.azshop.models.ItemModel;
import com.azshop.service.IItemService;

public class ItemServiceImpl implements IItemService{
	IItemDAO itemDAO = new ItemDAOImpl();

	@Override
	public List<ItemModel> findAll() {
		return itemDAO.findAll();
	}

	@Override
	public List<ItemModel> findByProductID(int productID) {
		return itemDAO.findByProductID(productID);
	}

	@Override
	public ItemModel findOne(int id) {
		return itemDAO.findOne(id);
	}

	@Override
	public void insertItem(ItemModel model) {
		itemDAO.insertItem(model);
	}

	@Override
	public void deleteItem(int IteID) {
		itemDAO.deleteItem(IteID);
	}

	@Override
	public void updateItem(ItemModel model) {
		itemDAO.updateItem(model);
	}

	@Override
	public ItemModel findOneByProductID(int productID) {
		return itemDAO.findOneByProductID(productID);
	}

	@Override
	public int CreateItemID(int Id) {
		List<ItemModel> Item = itemDAO.findAllByProductID(Id);
		int itemId = Item.get(Item.size()-1).getItemID();
		return itemId +1;
	}

	@Override
	public List<ItemModel> findAllByProductID(int productID) {
		return itemDAO.findAllByProductID(productID);
	}

}
