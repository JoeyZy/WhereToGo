package com.luxoft.wheretogo.models.json;

import lombok.Data;


@Data
public class CategoryResponse {
	private String category;
	private Integer counter;
	private Long id;


	public CategoryResponse(Long id, String category, Integer counter) {
		this.category=category;
		this.counter=counter;
		this.id=id;
	}
}
