package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.model.User;
import org.springframework.stereotype.Repository;

@Repository("users")
public class UsersRepository extends AbstractRepository<User> {

	public UsersRepository() {
		super(User.class);
	}
}
