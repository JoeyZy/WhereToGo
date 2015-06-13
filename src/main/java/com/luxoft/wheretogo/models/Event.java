package com.luxoft.wheretogo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String category;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "owner")
	private User owner;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "events_categories", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	public Event() {
	}

	public Event(int id, String category, String name, String description, User owner) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.description = description;
		this.owner = owner;
	}

	public String toString() {
		return id + ", " + category + ", " + name + ", " + description + ", " + owner.getFirstName();
	}

}
