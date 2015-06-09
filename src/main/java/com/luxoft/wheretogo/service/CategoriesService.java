package com.luxoft.wheretogo.service;

import java.util.List;

import com.luxoft.wheretogo.model.Category;

public interface CategoriesService {

	void addCategory(Category category);

	List<Category> findAll();

	Category getById(int categoryId);

	Category getByName(String categoryName);

}
