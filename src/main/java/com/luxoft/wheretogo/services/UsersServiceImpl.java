package com.luxoft.wheretogo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.repositories.UsersRepository;

@Service
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
	public User findById(int userId) {
		return usersRepository.findById(userId);
	}

	@Override
	public User findByLogin(String userLogin) {
		return usersRepository.findByLogin(userLogin);
	}
}
