package com.luxoft.wheretogo.services;

import java.util.List;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.UserInfo;

public interface UsersService {

	void add(UserInfo user);

	List<User> findAll();

	User findById(long userId);

	User findByEmail(String userLogin);

	void update(User user);

	List<User> getNotParticipants(long groupId);

	User initGroups(User user);

	User initEvents(User user);
}
