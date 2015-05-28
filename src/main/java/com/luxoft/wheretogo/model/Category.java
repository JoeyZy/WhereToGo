package com.luxoft.wheretogo.model;

import java.util.List;

import lombok.Data;

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
