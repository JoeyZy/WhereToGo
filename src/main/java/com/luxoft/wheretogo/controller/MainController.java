package com.luxoft.wheretogo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.luxoft.wheretogo.controller.editor.CategoryEditor;
import com.luxoft.wheretogo.controller.editor.UserEditor;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.services.CategoriesService;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.UsersService;

@Controller
public class MainController {

	@Autowired
	private EventsService eventsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private CategoriesService categoriesService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(List.class, "categories", new CategoryEditor());
//		binder.registerCustomEditor(User.class, new UserEditor());
	}

	@RequestMapping("/")
	public String homePage(HttpServletRequest request) {
		return "index";
	}

	@RequestMapping("/categories")
	public String categories(Model model) {
		model.addAttribute("categories", categoriesService.findAll());
		return "categories";
	}

	@RequestMapping("/event")
	public String event(@RequestParam(value = "id", required = true) int id, Model model) {
		model.addAttribute("event", eventsService.findById(id));
		return "event";
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.GET)
	public String createEvent(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("categories", categoriesService.findAll());
		return "addEvent";
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute("event") Event event, HttpServletRequest request) {
		event.setOwner((User) request.getSession().getAttribute("user"));
		eventsService.add(event);
		return "event";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("categories", categoriesService.findAll());
		return "addUser";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addUser";
		}

		usersService.add(user);
		return "index";
	}
}
