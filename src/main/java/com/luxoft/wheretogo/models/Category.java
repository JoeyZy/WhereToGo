package com.luxoft.wheretogo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
	private Set<Event> events;

	public Category() {
	}

}
