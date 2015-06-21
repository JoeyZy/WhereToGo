package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriesRepositoryImpl extends AbstractRepository<Category> implements CategoriesRepository {

	public CategoriesRepositoryImpl() {
		super(Category.class);
	}

	@Override
	public void add(Category category) {
		super.add(category);
	}

	@Override
	public List<Category> findAll() {
		return super.findAll("id");
	}

	@Override
	public Category findById(int categoryId) {
		return super.findByProperty("id", categoryId);
	}
}
