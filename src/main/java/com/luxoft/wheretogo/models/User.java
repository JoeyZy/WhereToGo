package com.luxoft.wheretogo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
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

	@JsonIgnore
	@Email
	@Column(unique = true)
	private String email;

	@JsonIgnore
	@Column(unique = true)
	private String description;

	@JsonIgnore
	@Column(unique = true)
	private String phoneNumber;

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

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "groupParticipants")
	private Set<Group> groups;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_categories", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> user_categories;

	@NotNull
	private boolean active;

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="picture")
	private String picture;

	public void setPicture(String path){
		this.picture = path;
	}
	public String getPicture(){
		if(this.picture == null) {
			return "";
		}
		return this.picture;
	}
}
