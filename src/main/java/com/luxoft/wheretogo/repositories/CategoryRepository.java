package com.luxoft.wheretogo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.repositories.db.DB;

@Repository("categories")
public class CategoryRepository extends AbstractRepository<Category> {

	public CategoryRepository() {
		super(Category.class);
	}

	{
		elementsList = (List<Category>) DB.db.get("categories");
	}

}
