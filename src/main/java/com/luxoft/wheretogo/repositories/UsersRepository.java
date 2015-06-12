package com.luxoft.wheretogo.repositories;

import java.util.List;

import com.luxoft.wheretogo.models.User;

public interface UsersRepository {

	void add(User user);

	List<User> findAll();

	User findById(int userId);

	User findByLogin(String userLogin);
}
