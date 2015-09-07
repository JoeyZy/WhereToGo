package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.services.CategoriesService;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/new/*")
public class NewDesignController {

	@Autowired
	private EventsService eventsService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private UsersService usersService;

	@RequestMapping("/*")
	public String init() {
		return "spa/index";
	}

	@RequestMapping("/events")
	@ResponseBody
	public List<EventResponse> events() {
		return eventsService.getEventResponses();
	}

	@RequestMapping("/event")
	@ResponseBody
	public Event event(Event event) {
		return eventsService.findById(event.getId());
	}

	@RequestMapping(value="/sessionUser", method = RequestMethod.GET)
	@ResponseBody
	public User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	@ResponseBody
	public Event addEvent(@RequestBody Event event, HttpServletRequest request) {
		event.setOwner((User) request.getSession().getAttribute("user"));
		eventsService.add(event);
		return null;
	}

	@RequestMapping(value="/categories", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> categories() {
		return categoriesService.findAll();
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public User user(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	@ResponseBody
	public boolean logout(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return true;
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
	public User getUserInfo(User user) {
		return usersService.findByEmail(user.getEmail());
	}

}
