package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepositoryImpl extends AbstractRepository<User> implements UsersRepository {

	public UsersRepositoryImpl() {
		super(User.class);
	}

	@Override
	public void add(User user) {
		if (findByEmail(user.getEmail()) == null) {
			super.add(user);
		}
		super.update(user);
	}

	@Override
	public List<User> findAll() {
		return super.findAll(null);
	}

	@Override
	public User findById(long userId) {
		return super.findByProperty("id", userId);
	}

	@Override
	public User findByEmail(String userLogin) {
		return super.findByProperty("email", userLogin);
	}

}
