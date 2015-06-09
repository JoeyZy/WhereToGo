package com.luxoft.wheretogo.repositories;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.model.User;

@Repository("users")
public class UsersRepository extends AbstractRepository<User> {

	public UsersRepository() {
		super(User.class);
	}
}
