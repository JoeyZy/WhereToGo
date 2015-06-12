package com.luxoft.wheretogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.services.UsersService;

@RestController
public class UserController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public User login(@RequestBody User user) {
		return usersService.findByLogin(user.getLogin());
	}

}
