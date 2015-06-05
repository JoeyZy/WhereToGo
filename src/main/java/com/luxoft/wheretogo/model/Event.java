package com.luxoft.wheretogo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "events")
public class Event extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String category;

	private String description;

	private String owner;

	public Event() {
	}

	public Event(int id, String category, String name, String description, String owner) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.description = description;
		this.owner = owner;
	}

	public String toString() {
		return id + ", " + category + ", " + name + ", " + description + ", " + owner;
	}

}
