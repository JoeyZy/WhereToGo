package com.luxoft.wheretogo.service;

import java.util.List;

import com.luxoft.wheretogo.model.User;

public interface UsersService {

	void addUser(User user);

	List<User> findAll();

	User getById(int userId);

	User getByName(String userName);
}
