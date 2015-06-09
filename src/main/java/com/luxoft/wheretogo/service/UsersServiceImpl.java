package com.luxoft.wheretogo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luxoft.wheretogo.model.User;
import com.luxoft.wheretogo.repositories.AbstractRepository;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	@Autowired
	@Qualifier("users")
	private AbstractRepository<User> usersRepository;

	@Override
	public void addUser(User user) {
		usersRepository.add(user);
	}

	@Override
	public List<User> findAll() {
		return usersRepository.findAll();
	}

	@Override
	public User getById(int userId) {
		return usersRepository.getById(userId);
	}

	@Override
	public User getByName(String userName) {
		return usersRepository.getByName(userName);
	}
}
