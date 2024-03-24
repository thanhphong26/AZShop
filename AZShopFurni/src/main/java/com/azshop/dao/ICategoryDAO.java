package com.azshop.dao;

import java.util.List;

import com.azshop.models.CategoryModel;



public interface ICategoryDAO {
	CategoryModel findOne(int id);
	CategoryModel findRootCategoryByCategoryId(int id);
	
	List<CategoryModel> findAll();
	List<CategoryModel> getCategoriesByParentId(int parentId);
	
	void insert(CategoryModel model);
	void update(CategoryModel model);
	void delete(int id);
}