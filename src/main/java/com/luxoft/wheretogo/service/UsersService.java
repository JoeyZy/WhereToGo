package com.luxoft.wheretogo.service;

import com.luxoft.wheretogo.model.User;

import java.util.List;

public interface UsersService {

	void addUser(User user);

	List<User> findAll();

	User getById(int userId);

	User getByName(String userName);
}
