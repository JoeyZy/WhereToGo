package com.luxoft.wheretogo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "email"})
@ToString(of = {"id", "email"})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String role = "user";

	@Email
	@Column(unique = true)
	private String email;

	@JsonIgnore
	@Size(min = 2, max = 60)
	private String password;

	@Size(min = 2, max = 30)
	@Column(name = "first_name")
	private String firstName;

	@Size(min = 2, max = 30)
	@Column(name = "last_name")
	private String lastName;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "participants")
	private Set<Event> events;
	@NotNull
	private boolean active;

	public User() {
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

}
