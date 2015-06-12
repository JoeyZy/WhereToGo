package com.luxoft.wheretogo.services;

import java.util.List;

import com.luxoft.wheretogo.models.Category;

public interface CategoriesService {

	void add(Category category);

	List<Category> findAll();

	Category findById(int categoryId);

}
