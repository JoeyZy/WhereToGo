package com.luxoft.wheretogo.controller.editor;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryEditor extends CustomCollectionEditor {

	@Autowired
	private CategoriesService categoriesService;

	public CategoryEditor() {
		super(List.class);
	}

	@Override
	protected Object convertElement(Object element) {
		if (element != null) {
			int id = Integer.valueOf((String) element);
			Category category = categoriesService.findById(id);
			return category;
		}
		return null;
	}
}
