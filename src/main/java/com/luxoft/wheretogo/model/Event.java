package com.luxoft.wheretogo.model;

import java.util.List;

import lombok.Data;

@Data
public class Event extends Model {

	private int id;
	private Category category;
	private String name;
	private String description;
	private User organizer;
	private List<User> comers;

	public Event() {
	}

	public Event(int id, Category category, String name, String description, User organizer, List<User> comers) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.description = description;
		this.organizer = organizer;
		this.comers = comers;
	}

}
