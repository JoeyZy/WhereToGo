package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.models.*;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.models.json.GroupResponse;
import com.luxoft.wheretogo.services.CurrenciesService;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.GroupsService;
import com.luxoft.wheretogo.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
public class RestServiceController {
	private static final Boolean DELETED = true;
	private static final Boolean NOT_DELETED = false;

	@Autowired
	private EventsService eventsService;

	@Autowired
	private CurrenciesService currenciesService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private GroupsService groupsService;

	@RequestMapping("/events")
	public List<EventResponse> events(HttpServletRequest request) {
		return eventsService.getRelevantEventResponses((User) request.getSession().getAttribute("user"));
	}

	@RequestMapping("/groups")
	public List<GroupResponse> groups(HttpServletRequest request) {
		return groupsService.getRelevantGroupResponses();
	}

	@RequestMapping(value= "/getGroupId", method = RequestMethod.GET)
	public Long getNextGroupId(){
		return groupsService.getNextGroupId();
	}

	@RequestMapping("/myEvents")
	public Set<EventResponse> myEvents(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user = usersService.initEvents(user);
		return eventsService.getUserRelevantEventResponses(user);
	}

	@RequestMapping("/event")
	public Event event(Event event) {
		return eventsService.initParticipants(event);
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
	public ResponseEntity<String> addEvent(@RequestBody EventInfo eventInfo, HttpServletRequest request) {

		Event event = new Event();
		event.setInfo(eventInfo);
		long targetGroupID = eventInfo.getTargetGroup();
		if(targetGroupID != -1) {
			event.setTargetGroup(groupsService.findById(targetGroupID));
		}
		event.setOwner((User) request.getSession().getAttribute("user"));
		event.setDeleted(NOT_DELETED);
		boolean eventIsAdded = eventsService.add(event);
		if (!eventIsAdded) {
			return new ResponseEntity<>("User is not active", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
	public void deleteEvent(@RequestBody EventInfo eventInfo, Authentication authentication) {
		Event event = eventsService.findById(eventInfo.getId());
		event.setDeleted(DELETED);
		eventsService.update(event, authentication.getName(), authentication.getAuthorities());
	}

	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
	public void updateEvent(@RequestBody EventInfo eventInfo, Authentication authentication) {
		Event event = eventsService.findById(eventInfo.getId());
		event.setInfo(eventInfo);
		event.setTargetGroup(groupsService.findById(eventInfo.getTargetGroup()));
		event.setDeleted(NOT_DELETED);
		eventsService.update(event, authentication.getName(), authentication.getAuthorities());
	}

    @RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)
    public void deleteGroup(@RequestBody Group group, Authentication authentication) {
            group.setDeleted(DELETED);
            groupsService.update(group, authentication.getName(), authentication.getAuthorities());
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    public void updateGroup(@RequestBody Group group, Authentication authentication) {
        group.setDeleted(NOT_DELETED);
        groupsService.update(group, authentication.getName(), authentication.getAuthorities());
    }

	@RequestMapping("/group")
	public Group group(Group group) {
        return groupsService.initGroupParticipants(group);
	}

	@RequestMapping(value = "/addGroup", method = RequestMethod.POST)
	public ResponseEntity<String> addGroup(@RequestBody Group group, HttpServletRequest request) {
		group.setOwner((User) request.getSession().getAttribute("user"));
        group.setDeleted(NOT_DELETED);
		boolean groupIsAdded = groupsService.add(group);
		if (!groupIsAdded) {
			return new ResponseEntity<>("User is not active", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping("/groupEvents")
	public Set<EventResponse> groupEvents(Group group, HttpServletRequest request) {
		return eventsService.getGroupRelevantEventResponses(group);
	}

    @RequestMapping(value = "/assignUserToGroup", method = RequestMethod.POST)
    public Group assignUserToGroup(@RequestBody Group group, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Group groupToUpdate = groupsService.initGroupParticipants(group);

        if (!(groupToUpdate.getGroupParticipants() == null) &&
                !groupToUpdate.getGroupParticipants().contains(user)) {
            groupToUpdate.getGroupParticipants().add(user);

            user.setGroups(usersService.initGroups(user).getGroups());

            user.getGroups().add(groupToUpdate);
            groupsService.update(groupToUpdate);
            usersService.update(user);

            request.getSession().setAttribute("user", user);
        }
        return groupToUpdate;
    }

    @RequestMapping(value="/unassignUserFromGroup", method = RequestMethod.POST)
    public Group unassignUserFromGroup(@RequestBody Group group, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Group groupToUpdate = groupsService.findById(group.getId());
        groupToUpdate = groupsService.initGroupParticipants(groupToUpdate);

        if(groupToUpdate != null && groupToUpdate.getGroupParticipants().remove(user)) {
            groupsService.update(groupToUpdate);
        }

        user.setGroups(usersService.initGroups(user).getGroups());
        request.getSession().setAttribute("user", user);

        if (user != null && user.getGroups().remove(groupToUpdate)) {
            usersService.update(user);
        }
        return groupToUpdate;
    }

	@RequestMapping("/myGroups")
	public Set<GroupResponse> myGroups(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user = usersService.initGroups(user);
		return groupsService.getUserRelevantGroupResponses(user);
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(@RequestBody UserInfo user) {
		usersService.add(user);
	}

	@RequestMapping(value = "/myEventsCategories", method = RequestMethod.GET)
	public Set<CategoryResponse> myEventsCategories(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user = usersService.initEvents(user);
		return eventsService.getUserEventsCounterByCategories(user);
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
	public User user(HttpServletRequest request, Principal principal) {
		User user = (User) request.getSession().getAttribute("user");
		if ((user == null) && (principal != null)) {
			String email = principal.getName();
			if (email.isEmpty()) {
				return null;
			}
			user = usersService.findByEmail(email);
			request.getSession().setAttribute("user", user);
		}
		return user;
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public UserInfo getUserInfo(Principal principal) {
        User user = usersService.findByEmail(principal.getName());
        return new UserInfo(user.getRole(), user.getEmail(), user.getFirstName(), user.getLastName(), user.isActive());
	}

	@RequestMapping(value = "/assignEventToUser", method = RequestMethod.POST)
	public Event assignUserToEvent(@RequestBody Event event, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Event eventToUpdate = eventsService.initParticipants(event);
        user.setEvents(usersService.initEvents(user).getEvents());

		if (!(eventToUpdate.getParticipants() == null) && !eventToUpdate.getParticipants().contains(user)) {
			eventToUpdate.getParticipants().add(user);
			user.getEvents().add(eventToUpdate);
			eventsService.update(eventToUpdate);
			usersService.update(user);
		}
        request.getSession().setAttribute("user", user);
		return eventToUpdate;
	}

	@RequestMapping(value="/unassignEventFromUser", method = RequestMethod.POST)
	public Event unassignGroupFromUser(@RequestBody Event event, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Event eventToUpdate = eventsService.initParticipants(event);

		if(eventToUpdate !=null && !eventToUpdate.getParticipants().isEmpty() && eventToUpdate.getParticipants().remove(user)) {
			eventsService.update(eventToUpdate);
		}

        user.setEvents(usersService.initEvents(user).getEvents());
		if (user != null && !user.getEvents().isEmpty() && user.getEvents().remove(eventToUpdate)) {
			usersService.update(user);
		}
        request.getSession().setAttribute("user", user);
		return eventToUpdate;
	}

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