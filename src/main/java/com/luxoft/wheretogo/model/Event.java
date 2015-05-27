package com.luxoft.wheretogo.model;

import java.util.List;

import lombok.Data;

@Data
public class Event {

	private int id;
	private String name;
	private String description;
	private User organizer;
	private List<User> comers;

	public Event(int id, String name, String description, User organizer, List<User> comers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.organizer = organizer;
		this.comers = comers;
	}

}
