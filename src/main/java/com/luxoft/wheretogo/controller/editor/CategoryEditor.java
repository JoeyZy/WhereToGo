package com.luxoft.wheretogo.controller.editor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Component;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.repositories.CategoriesRepository;

@Component
public class CategoryEditor extends CustomCollectionEditor {

	@Autowired
	private CategoriesRepository categoriesRepository;

	public CategoryEditor() {
		super(List.class);
	}

	@Override
	protected Object convertElement(Object element) {
		if (element != null) {
			int id = Integer.valueOf((String) element);
			Category category = categoriesRepository.findById(id);
			return category;
		}
		return null;
	}
}
