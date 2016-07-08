package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.models.ArchiveServiceRequest;
import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.services.CurrenciesService;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
public class RestServiceController {
	private static final Integer DELETED = 1;
	private static final Integer NOT_DELETED = 0;

	@Autowired
	private EventsService eventsService;

	@Autowired
	private CurrenciesService currenciesService;

	@Autowired
	private UsersService usersService;

	@RequestMapping("/events")
	public List<EventResponse> events(HttpServletRequest request) {
		return eventsService.getRelevantEventResponses((User) request.getSession().getAttribute("user"));
	}

	@RequestMapping("/myEvents")
	public Set<EventResponse> myEvents(HttpServletRequest request) {
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
	public ResponseEntity<String> addEvent(@RequestBody Event event, HttpServletRequest request) {
		event.setOwner((User) request.getSession().getAttribute("user"));
		event.setDeleted(NOT_DELETED);
		boolean eventIsAdded = eventsService.add(event);
		if (!eventIsAdded) {
			return new ResponseEntity<>("User is not active", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.OK);
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

	@RequestMapping(value = "/myEventsCategories", method = RequestMethod.GET)
	public Set<CategoryResponse> myEventsCategories(HttpServletRequest request) {
		return eventsService.getUserEventsCounterByCategories((User) request.getSession().getAttribute("user"));
	}

	@RequestMapping(value = "/eventsCategories", method = RequestMethod.GET)
	public List<CategoryResponse> categories() {
		return eventsService.getEventsCounterByCategories();
	}

	@RequestMapping(value = "/currencies", method = RequestMethod.GET)
	public List<Currency> currencies() {
		return currenciesService.findAll();
	}

	// Hack. JavaScript gets its data from this response
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public User user(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().
					getAuthentication().getPrincipal()).getUsername();
			if (email.isEmpty()) {
				return null;
			}
			user = usersService.findByEmail(email);
			request.getSession().setAttribute("user", user);
		}
		return user;
	}

	/*@RequestMapping(value = "/user", method = RequestMethod.GET)
	public User user(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}*/

	/*@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public boolean logout(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return true;
	}*/

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public User getUserInfo(User user) {
		return usersService.findByEmail(user.getEmail());
	}

	/*@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestBody User user, HttpServletRequest request) {
		User sessionUser = usersService.findByEmail(user.getEmail());
		if (sessionUser == null || !sessionUser.getPassword().equals(user.getPassword())) {
			return null;
		}
		request.getSession().setAttribute("user", sessionUser);

		return sessionUser;
	}*/

	@RequestMapping(value = "/assignEventToUser", method = RequestMethod.POST)
	public Event assignUserToEvent(@RequestBody Event event, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Event eventToUpdate = eventsService.findById(event.getId());

		if (!(eventToUpdate.getParticipants() == null) && !eventToUpdate.getParticipants().contains(user)) {
			eventToUpdate.getParticipants().add(user);
			user.getEvents().add(eventToUpdate);
			eventsService.update(eventToUpdate);
			usersService.update(user);
		}
		return eventToUpdate;
	}

	/*@RequestMapping(value="/unassignEventFromUser", method = RequestMethod.POST)
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
	}*/


	@RequestMapping("/archivedEvents")
	public List<EventResponse> archivedEvents(ArchiveServiceRequest request, HttpServletRequest httpRequest) {
			return eventsService.getArchivedEventsResponse(request, (User) httpRequest.getSession().getAttribute("user"));
	}

	@RequestMapping("/archivedUsersEvents")
	public List<EventResponse> archivedUsersEvents(ArchiveServiceRequest request, HttpServletRequest httpRequest) {
			return eventsService.getArchivedUsersEventsResponse(request, (User) httpRequest.getSession().getAttribute("user"));
	}


	@RequestMapping(value = "/archivedEventsCategories", method = RequestMethod.GET)
	public List<CategoryResponse> archivedEventsCategories(ArchiveServiceRequest request, HttpServletRequest httpRequest) {
		return eventsService.getArchivedEventsCounterByCategories(request, (User) httpRequest.getSession().getAttribute("user"));
	}

	@RequestMapping(value = "/archivedUsersEventsCategories", method = RequestMethod.GET)
	public List<CategoryResponse> archivedUsersEventsCategories(ArchiveServiceRequest request, HttpServletRequest httpRequest) {
		return eventsService.getArchivedUsersEventsCounterByCategories(request, (User) httpRequest.getSession().getAttribute("user"));
	}

}