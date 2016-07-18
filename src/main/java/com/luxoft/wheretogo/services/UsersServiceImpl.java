package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.repositories.UsersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
@Transactional
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public void add(User user) {
		usersRepository.add(user);
	}

	@Override
	public List<User> findAll() {
		return usersRepository.findAll();
	}

	@Override
	public User findById(long userId) {
		User user = usersRepository.findById(userId);
		return user;
	}

	@Override
	public User findByEmail(String userLogin) {
		User user = usersRepository.findByEmail(userLogin);
		return user;
	}

	@Override
	public void update(User user) {
		usersRepository.update(user);
	}

	@Override
	public List<User> getNotParticipants(long groupId) {
		return usersRepository.getNotParticipants(groupId);
	}

	@Override
	public User initGroups(long userId) {
		User user = usersRepository.findById(userId);
		if (user != null) {
			Hibernate.initialize(user.getGroups());
		}
		return user;
	}

	@Override
	public User initEvents(long userId) {
		User user = usersRepository.findById(userId);
		if (user != null) {
			Hibernate.initialize(user.getEvents());
		}
		return user;
	}
}
