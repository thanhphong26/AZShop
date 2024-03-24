package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.ISearchHistoryDAO;
import com.azshop.dao.impl.SearchHistoryDAOImpl;
import com.azshop.models.SearchHistoryModel;
import com.azshop.service.ISearchHistoryService;

public class SearchHistoryServiceImpl implements ISearchHistoryService{
	ISearchHistoryDAO searchHistoryDao=new SearchHistoryDAOImpl();
	@Override
	public List<SearchHistoryModel> getHistorySearchByCID(int customerID) {
		return searchHistoryDao.getHistorySearchByCID(customerID);
	}
	@Override
	public void insertSearchHistory(SearchHistoryModel model) {
		searchHistoryDao.insertSearchHistory(model);
	}

}
