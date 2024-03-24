package com.azshop.service;

import java.util.List;

import com.azshop.models.SearchHistoryModel;

public interface ISearchHistoryService {
	List<SearchHistoryModel> getHistorySearchByCID(int customerID);
	void insertSearchHistory(SearchHistoryModel model);
}
