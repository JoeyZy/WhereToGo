package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
}
