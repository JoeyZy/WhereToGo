package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.services.CategoriesService;
import com.luxoft.wheretogo.services.CurrenciesService;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RestServiceController {
	private static final Integer DELETED = 1;
	private static final Integer NOT_DELETED = 0;

	@Autowired
	private EventsService eventsService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private CurrenciesService currenciesService;

	@Autowired
	private UsersService usersService;

	@RequestMapping("/events")
	public List<EventResponse> events() {
		return eventsService.getRelevantEventResponses();
	}

	@RequestMapping("/myEvents")
	public List<EventResponse> myEvents(HttpServletRequest request) {
		return eventsService.getUserRelevantEventResponses((User) request.getSession().getAttribute("user"));
	}

	@RequestMapping("/event")
	public Event event(Event event) {
		return eventsService.findById(event.getId());
	}

	@RequestMapping(value = "/sessionUser", method = RequestMethod.GET)
	public User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}

	@RequestMapping(value= "/getEventId", method = RequestMethod.GET)
	public Long getNextEventId(){
		return eventsService.getNextEventId();
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public void addEvent(@RequestBody Event event, HttpServletRequest request) {
		event.setOwner((User) request.getSession().getAttribute("user"));
		event.setDeleted(NOT_DELETED);
		eventsService.add(event);
	}

	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
	public void deleteEvent(@RequestBody Event event) {
		event.setDeleted(DELETED);
		eventsService.update(event);
	}

	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
	public void updateEvent(@RequestBody Event event, HttpServletRequest request) {
		event.setDeleted(NOT_DELETED);
		eventsService.update(event);
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(@RequestBody User user) {
		usersService.add(user);
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public List<Category> categories() {
		return categoriesService.findAll();
	}

	@RequestMapping(value = "/currencies", method = RequestMethod.GET)
	public List<Currency> currencies() {
		return currenciesService.findAll();
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public User user(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public boolean logout(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return true;
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public User getUserInfo(User user) {
		return usersService.findByEmail(user.getEmail());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestBody User user, HttpServletRequest request) {
		User sessionUser = usersService.findByEmail(user.getEmail());
		if (sessionUser == null || !sessionUser.getPassword().equals(user.getPassword())) {
			return null;
		}
		request.getSession().setAttribute("user", sessionUser);

		return sessionUser;
	}

	@RequestMapping(value = "/assignEventToUser", method = RequestMethod.POST)
	public Event assignUserToEvent(@RequestBody Event event, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Event eventToUpdate = eventsService.findById(event.getId());
		eventToUpdate.getParticipants().add(user);
		user.getEvents().add(eventToUpdate);
		eventsService.update(eventToUpdate);
		usersService.update(user);
		return eventToUpdate;
	}

	@RequestMapping(value="/unassignEventFromUser", method = RequestMethod.POST)
	public Event unassignEventFromUser(@RequestBody Event event, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Event eventToUpdate = eventsService.findById(event.getId());

		if(eventToUpdate !=null && !eventToUpdate.getParticipants().isEmpty() && eventToUpdate.getParticipants().remove(user)) {
			eventsService.update(eventToUpdate);
		}

		if (user != null && !user.getEvents().isEmpty() && user.getEvents().remove(eventToUpdate)) {
			usersService.update(user);
		}
		return eventToUpdate;
	}

}