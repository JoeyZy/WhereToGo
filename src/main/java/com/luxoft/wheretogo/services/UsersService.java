package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.User;

import java.util.List;

public interface UsersService {

	void add(User user);

	List<User> findAll();

	User findById(long userId);

	User findByEmail(String userLogin);

	void update(User user);

}
