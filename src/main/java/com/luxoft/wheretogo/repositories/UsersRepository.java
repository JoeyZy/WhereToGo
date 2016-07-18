package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.User;

import java.util.List;
import java.util.Set;

public interface UsersRepository {

	void add(User user);

	List<User> findAll();

	User findById(long userId);

	User findByEmail(String userLogin);

	void update(User user);

	List<User> getNotParticipants(long groupId);
}
