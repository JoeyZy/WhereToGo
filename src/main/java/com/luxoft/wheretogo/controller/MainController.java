package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.controller.editor.CategoryEditor;
import com.luxoft.wheretogo.controller.editor.UserEditor;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.services.CategoriesService;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	@Autowired
	private UserEditor userEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(List.class, "categories", categoryEditor);
		binder.registerCustomEditor(User.class, userEditor);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	@RequestMapping("/*")
	public String homePage(HttpServletRequest request) {
		return "index";
	}

	@RequestMapping("/categories")
	public String categories(Model model) {
		model.addAttribute("categories", categoriesService.findAll());
		return "categories";
	}

	@RequestMapping("/event")
	public String event(@RequestParam(value = "id", required = true) int id, Model model, HttpServletRequest request) {
		Event event = eventsService.findById(id);
		model.addAttribute("currentUserIsParticipant", event.getParticipants().contains(request.getSession().getAttribute("user")));
		model.addAttribute("event", eventsService.findById(id));
		return "event";
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.GET)
	public String createEvent(@RequestParam(value = "eventId", required = false) Integer eventId, Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("categories", categoriesService.findAll());
		if (eventId != null) {
			Event event = eventsService.findById(eventId);
			if (event != null) {
				model.addAttribute("event", event);
			}
		}
		return "addEvent";
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String addEvent(@Valid Event event, BindingResult result, HttpServletRequest request, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("categories", categoriesService.findAll());
			return "addEvent";
		}
		event.setOwner((User) request.getSession().getAttribute("user"));
		eventsService.add(event);
		model.addAttribute("currentUserIsParticipant",
				(event.getParticipants() != null) && (event.getParticipants().contains(request.getSession().getAttribute("user"))));
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

	@RequestMapping(value = "/addEventToUser")
	public String addUserToEvent(@RequestParam(value = "eventId", required = true) Integer eventId, Model model, HttpServletRequest request) {
		Event event = eventsService.findById(eventId);
		event.getParticipants().add((User) request.getSession().getAttribute("user"));
		eventsService.add(event);
		model.addAttribute("currentUserIsParticipant", event.getParticipants().contains(request.getSession().getAttribute("user")));
		model.addAttribute("event", event);
		return "event";
	}

	@RequestMapping(value = "/userInfo")
	public String user(@RequestParam(value = "id", required = true) int userId, Model model) {
		User user = usersService.findById(userId);
		model.addAttribute("user", user);
		//		user.getEvents();
		return "user";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public User login() {
		return new User();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public User login(@RequestBody User user, HttpServletRequest request) {
		User sessionUser = usersService.findByEmail(user.getEmail());
		if (sessionUser == null || !sessionUser.getPassword().equals(user.getPassword())) {
			return null;
		}
		request.getSession().setAttribute("user", sessionUser);

		return sessionUser;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public boolean logout(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			request.getSession().setAttribute("user", null);
		}
		return true;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public User user(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
}
