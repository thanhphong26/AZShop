package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IItemImageDAO;
import com.azshop.dao.impl.ItemImageDAOImpl;
import com.azshop.models.ItemImageModel;
import com.azshop.service.IItemImageService;

public class ItemImageServiceImpl implements IItemImageService {

	IItemImageDAO itemImage = new ItemImageDAOImpl();

	@Override
	public List<ItemImageModel> findByProductID(int productID) {
		return itemImage.findByProductID(productID);
	}

	@Override
	public void insertItemImage(ItemImageModel ItemId) {
		itemImage.insertItemImage(ItemId);
	}

	@Override
	public void deleteItemImage(int IteamId) {
		itemImage.deleteItemImage(IteamId);
	}

	@Override
	public void updateItemImage(ItemImageModel ItemId) {
		itemImage.updateItemImage(ItemId);
	}

	@Override
	public int CreateItemimageID(int Id) {
		List<ItemImageModel> model = itemImage.findByProductID(Id);
		if (model.isEmpty() == false) {
			int id = model.get(model.size() - 1).getItemimageID();
			return id + 1;
		} else {
			return Id*100 + 1;
		}
	}

}
