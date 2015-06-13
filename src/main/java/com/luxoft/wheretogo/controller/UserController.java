package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public User login() {
		return new User();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public User login(@RequestBody User user, HttpServletRequest request) {
		User sessionUser = usersService.findByLogin(user.getLogin());
		request.getSession().setAttribute("user", sessionUser);

		return usersService.findByLogin(user.getLogin());
	}

}
