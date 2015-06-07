package com.luxoft.wheretogo.controller.editor;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class CategoryEditor extends PropertyEditorSupport {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Override
	public void setAsText(String text) {
		Category category = categoriesRepository.getById(Integer.valueOf(text));
		this.setValue(category);
	}
}
