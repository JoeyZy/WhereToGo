package com.luxoft.wheretogo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String login;

	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	public User() {

	}

	public User(int id, String login, String password, String firstName, String lastName) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
