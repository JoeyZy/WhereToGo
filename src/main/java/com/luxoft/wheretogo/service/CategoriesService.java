package com.luxoft.wheretogo.service;

import com.luxoft.wheretogo.model.Category;

import java.util.List;

public interface CategoriesService {

	void addCategory(Category category);

	List<Category> findAll();

	Category getById(int categoryId);

	Category getByName(String categoryName);

}
