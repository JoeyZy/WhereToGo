package com.luxoft.wheretogo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "login"})
@ToString(of = {"id", "login"})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	@Size(min = 2, max = 30)
	private String login;

	@Size(min = 2, max = 30)
	private String password;

	@Size(min = 2, max = 30)
	@Column(name = "first_name")
	private String firstName;

	@Size(min = 2, max = 30)
	@Column(name = "last_name")
	private String lastName;

	@NotEmpty
	@Email
	private String email;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "participants")
	private Set<Event> events;

	public User() {
	}

}
