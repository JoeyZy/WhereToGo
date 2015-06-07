package com.luxoft.wheretogo.service;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	@Qualifier("categories")
	private AbstractRepository<Category> categoriesRepository;

	@Override
	public void addCategory(Category category) {
		categoriesRepository.add(category);
	}

	@Override
	public List<Category> findAll() {
		return categoriesRepository.findAll();
	}

	@Override
	public Category getById(int categoryId) {
		return categoriesRepository.getById(categoryId);
	}

	@Override
	public Category getByName(String categoryName) {
		return categoriesRepository.getByName(categoryName);
	}
}
