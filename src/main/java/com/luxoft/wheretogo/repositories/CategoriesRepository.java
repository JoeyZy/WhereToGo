package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.model.Category;
import org.springframework.stereotype.Repository;

@Repository("categories")
public class CategoriesRepository extends AbstractRepository<Category> {

	public CategoriesRepository() {
		super(Category.class);
	}

}
