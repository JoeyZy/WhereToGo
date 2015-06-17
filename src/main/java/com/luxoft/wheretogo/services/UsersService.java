package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.User;

import java.util.List;

public interface UsersService {

	void add(User user);

	List<User> findAll();

	User findById(int userId);

	User findByLogin(String userLogin);

}
