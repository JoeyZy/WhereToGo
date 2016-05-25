package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.json.CategoryResponse;

import java.util.List;

public interface CategoriesService {

	void add(Category category);

	List<Category> findAll();

	Category findById(long categoryId);

	List<CategoryResponse> countEventsByCategories(List<Event> events);

}
