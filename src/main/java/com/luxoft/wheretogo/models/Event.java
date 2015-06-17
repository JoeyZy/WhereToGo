package com.luxoft.wheretogo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "events")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, max = 30)
	private String name;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner")
	private User owner;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "events_categories", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories;

	public Event() {
	}

}
