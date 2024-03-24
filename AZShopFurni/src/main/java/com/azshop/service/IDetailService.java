package com.azshop.service;

import java.util.List;

import com.azshop.models.DetailModel;

public interface IDetailService {
	List<DetailModel> findDetailByProductID(int productID);
	DetailModel findDetailByItemID(int orderID, int itemID);
	void updateDetail(DetailModel detail);
	public List<DetailModel> listDetail(int orderID);
	public List<List<Object>> listBestSeller();
}