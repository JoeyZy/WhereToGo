package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
