package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.UserInfo;
import com.luxoft.wheretogo.repositories.UsersRepository;
import com.luxoft.wheretogo.utils.ImageUtils;
import com.luxoft.wheretogo.utils.PropertiesUtils;
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
	public void add(UserInfo user) {
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

		if(!user.getPicture().equals("")){
			if(user.getPicture().substring(0,4).equals("data")){
				user.setPicture(ImageUtils.generatePicturePath(user.getPicture(), PropertiesUtils.getProp("users.images.path")));
				//Deleting old image from server needed only if group profile is editing
				ImageUtils.deleteOldPicture(user.getPicture());
			}
		}
		usersRepository.update(user);
	}

	@Override
	public List<User> getNotParticipants(long groupId) {
		return usersRepository.getNotParticipants(groupId);
	}

	@Override
	public User initGroups(User user) {
		if (Hibernate.isInitialized(user.getGroups())) {
			return user;
		}
		user = usersRepository.findById(user.getId());
		if (user != null) {
			Hibernate.initialize(user.getGroups());
		}
		return user;
	}

	@Override
	public User initEvents(User user) {
		if (Hibernate.isInitialized(user.getEvents())) {
			return user;
		}
		user = usersRepository.findById(user.getId());
		if (user != null) {
			Hibernate.initialize(user.getEvents());
		}
		return user;
	}
}
