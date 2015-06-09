package com.luxoft.wheretogo.repositories;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.model.Category;

@Repository("categories")
public class CategoriesRepository extends AbstractRepository<Category> {

	public CategoriesRepository() {
		super(Category.class);
	}

}
