package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.repositories.CategoriesRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Override
	public void add(Category category) {
		categoriesRepository.add(category);
	}

	@Override
	public List<Category> findAll() {
		return categoriesRepository.findAll();
	}

	@Override
	public Category findById(long categoryId) {
		return categoriesRepository.findById(categoryId);
	}

	public List<CategoryResponse> countEventsByCategories(List<Event> events) {
		List<CategoryResponse> categoryResponse = new ArrayList<>();
		Hibernate.initialize(categoriesRepository.findAll());
		List<Category> categories = categoriesRepository.findAll();

		for (Category category : categories) {
			Integer counter = 0;
			for (Event event : events) {
				if (category.equals(event.getCategories().iterator().next())) {
					counter++;
				}
			}
			categoryResponse.add(new CategoryResponse(category.getId(), category.getName(), counter));
		}
		return categoryResponse;
	}

}
