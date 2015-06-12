package com.luxoft.wheretogo.services;

import java.util.List;

import com.luxoft.wheretogo.models.User;

public interface UsersService {

	void add(User user);

	List<User> findAll();

	User findById(int userId);

	User findByLogin(String userLogin);

}
