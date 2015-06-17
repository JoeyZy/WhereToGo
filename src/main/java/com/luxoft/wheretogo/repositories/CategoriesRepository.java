package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Category;

import java.util.List;

public interface CategoriesRepository {

	void add(Category category);

	List<Category> findAll();

	Category findById(int categoryId);

}
