package com.azshop.dao;

import java.util.List;

import com.azshop.models.SearchHistoryModel;

public interface ISearchHistoryDAO {
	List<SearchHistoryModel> getHistorySearchByCID(int customerID);
	void insertSearchHistory(SearchHistoryModel model);
}
