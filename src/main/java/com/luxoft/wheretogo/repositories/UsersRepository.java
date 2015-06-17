package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.User;

import java.util.List;

public interface UsersRepository {

	void add(User user);

	List<User> findAll();

	User findById(int userId);

	User findByLogin(String userLogin);
}
