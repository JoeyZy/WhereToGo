package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepositoryImpl extends AbstractRepository<User> implements UsersRepository {

	@Autowired
	private PasswordEncoder encoder;

	public UsersRepositoryImpl() {
		super(User.class);
	}

	@Override
	public void add(User user) {
		if (findByEmail(user.getEmail()) == null) {
			user.setPassword(encoder.encode(user.getPassword()));
			user.setActive(true);
			super.add(user);
		}
		super.update(user);
	}

	@Override
	public List<User> findAll() {
		return super.findAll(null);
	}

	@Override
	public User findById(long userId) {
		return super.findByProperty("id", userId);
	}

	@Override
	public User findByEmail(String userLogin) {
		return super.findByProperty("email", userLogin);
	}

	@Override
	public void update(User user) {
		super.update(user);
	}

}
