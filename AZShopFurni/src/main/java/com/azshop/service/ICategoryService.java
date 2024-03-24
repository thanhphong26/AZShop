package com.azshop.service;

import java.util.List;

import com.azshop.models.CategoryModel;
import com.azshop.models.submodels.CategoryLevelModel;

public interface ICategoryService {
	CategoryModel findOne(int id);
	CategoryModel findRootCategoryByCategoryId(int id);
	List<CategoryLevelModel> getCategoryLevels();
	List<CategoryModel> getRootCategories();
	List<CategoryModel> geChidlCategories(int parentId);
    List<CategoryModel> getCategoriesByParentId(int parentId);
}

	