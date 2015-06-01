package com.luxoft.wheretogo.controller.editor;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class CategoryEditor extends PropertyEditorSupport {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void setAsText(String text) {
		Category category = categoryRepository.getById(Integer.valueOf(text));
		this.setValue(category);
	}
}
