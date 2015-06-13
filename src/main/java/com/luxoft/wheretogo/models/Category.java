package com.luxoft.wheretogo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "categories")
	private List<Event> events;

	public Category() {
	}

	public Category(int id, String name, List<Event> events) {
		this.id = id;
		this.name = name;
		this.events = events;
	}

	public String toString() {
		return name;
	}

}
