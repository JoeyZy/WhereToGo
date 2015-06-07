package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.controller.editor.CategoryEditor;
import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.model.User;
import com.luxoft.wheretogo.service.CategoriesService;
import com.luxoft.wheretogo.service.EventsService;
import com.luxoft.wheretogo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

	@Autowired
	private EventsService eventsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private CategoryEditor categoryEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Category.class, categoryEditor);
	}

	@RequestMapping("/")
	public String homePage() {
		return "index";
	}

	@RequestMapping("/categories")
	public String categories(Model model) {
		model.addAttribute("categories", categoriesService.findAll());
		return "categories";
	}

	@RequestMapping("/event")
	public String event(@RequestParam(value = "name", required = true) String name, Model model) {
		model.addAttribute("event", eventsService.getByName(name));
		return "event";
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.GET)
	public String createEvent(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("categories", categoriesService.findAll());
		return "addEvent";
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute("event") Event event, Model model) {
		event.setOwner("(te)");
		eventsService.addEvent(event);
		return "event";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("categories", categoriesService.findAll());
		return "addUser";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user, Model model) {
		usersService.addUser(user);
		return "index";
	}
}
