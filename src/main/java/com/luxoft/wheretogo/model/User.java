package com.luxoft.wheretogo.model;

import lombok.Data;

@Data
public class User extends Model {

	private int id;
	private String login;
	private String password;
	private String name;
	private String lastName;

	public User(int id, String login, String password, String name, String lastName) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
	}

}
