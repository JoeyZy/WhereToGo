package com.luxoft.wheretogo.controller.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.repositories.CategoriesRepository;

@Component
public class CategoryEditor extends PropertyEditorSupport {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Override
	public void setAsText(String text) {
		Category category = categoriesRepository.findById(Integer.valueOf(text));
		this.setValue(category);
	}
}
