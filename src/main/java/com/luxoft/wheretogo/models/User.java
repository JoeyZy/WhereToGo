package com.luxoft.wheretogo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

	public User() {
	}
}
