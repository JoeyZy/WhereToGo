package com.luxoft.wheretogo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.models.User;

@Repository
public class UsersRepositoryImpl extends AbstractRepository<User> implements UsersRepository {

	public UsersRepositoryImpl() {
		super(User.class);
	}

	@Override
	public void add(User object) {
		super.add(object);
	}

	@Override
	public List<User> findAll() {
		return super.findAll();
	}

	@Override
	public User findById(int userId) {
		return super.findByProperty("id", userId);
	}

	@Override
	public User findByLogin(String userLogin) {
		return super.findByProperty("login", userLogin);
	}

}
