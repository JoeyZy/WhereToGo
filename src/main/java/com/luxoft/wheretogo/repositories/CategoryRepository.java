package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.repositories.db.DB;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categories")
public class CategoryRepository extends AbstractRepository<Category> {

	{
		elementsList = (List<Category>) DB.db.get("categories");
	}

	public CategoryRepository() {
		super(Category.class);
	}

}
