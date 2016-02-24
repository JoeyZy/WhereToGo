package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.User;

import java.util.List;

public interface UsersRepository {

	void add(User user);

	List<User> findAll();

	User findById(long userId);

	User findByEmail(String userLogin);
}
