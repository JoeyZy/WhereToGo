package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Category;

import java.util.List;

public interface CategoriesService {

	void add(Category category);

	List<Category> findAll();

	Category findById(long categoryId);

}
