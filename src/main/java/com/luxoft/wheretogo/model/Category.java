package com.luxoft.wheretogo.model;

import lombok.Data;

import java.util.List;

@Data
public class Category extends Model {

	private int id;
	private String name;
	private List<Event> events;

	public Category() {
	}

	public Category(int id, String name, List<Event> events) {
		this.id = id;
		this.name = name;
		this.events = events;
	}

}
