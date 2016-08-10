package com.luxoft.wheretogo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.luxoft.wheretogo.utils.ImageUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.luxoft.wheretogo.configuration.ConfigureSpringSecurity;
import com.luxoft.wheretogo.models.ArchiveServiceRequest;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.repositories.EventIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.EventsRepository;

import static java.nio.file.Paths.get;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private EventIdGeneratorRepository idGenerator;
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private Environment environment;

	@Override
	public boolean add(Event event) {
		if (event.getOwner().isActive()) {
			event.setPicture(ImageUtils.generatePicturePath(event.getPicture(),environment.getProperty("events.images.path")));
			eventsRepository.add(event);
			return true;
		}
		return false;
	}

	@Override
	public void update(Event event, String ownerEmail, Collection<? extends GrantedAuthority> authorities) {
		Event oldEvent = initParticipants(event.getId());
		User owner;
		event.setPicture(ImageUtils.generatePicturePath(event.getPicture(),environment.getProperty("events.images.path")));
		if (oldEvent != null) {
			owner = oldEvent.getOwner();
			if (!owner.getEmail().equals(ownerEmail) &&
					!authorities.contains(ConfigureSpringSecurity.grantedAdminRole)) {
				return;
			}
			event.setOwner(oldEvent.getOwner());
			event.setParticipants(oldEvent.getParticipants());
			//Old image deletion
			File file = new File(oldEvent.getPicture());
			file.delete();
		}
		eventsRepository.merge(event);
	}

	@Override
	public void update(Event event) {
		Event oldEvent = findById(event.getId());
		if (oldEvent != null) {
			event.setOwner(oldEvent.getOwner());
			if (event.getParticipants() == null) {
				event.setParticipants(oldEvent.getParticipants());
			}
		}
		eventsRepository.merge(event);
	}

	@Override
	public List<Event> findAll() {
		return eventsRepository.findAll();
	}

	@Override
	public List<Event> findByPeriod(LocalDateTime from, LocalDateTime to) {
		List<Event> events = eventsRepository.findByPeriod(from, to);
		return events;
	}

	@Override
	public Event findById(long eventId) {
		return eventsRepository.findById(eventId);
	}

	@Override
	public Event findByName(String eventName) {
		return eventsRepository.findByName(eventName);
	}

	@Override
	public List<EventResponse> getEventResponses() {
		return convertToEventResponses(findAll(), null);
	}

	@Override
	public Set<EventResponse> getUserRelevantEventResponses(User user) {
		List<Event> events = eventsRepository.findByOwner(user);
		events.addAll(user.getEvents());
		return new HashSet<>(convertToEventResponses(getRelevantEvents(events), user));
	}

	@Override
	public Set<EventResponse> getGroupUserRelevantEventResponses(Group group, User user) {
		if(!(group.getGroupParticipants().contains(user) || group.getOwner().equals(user))) {
			return new HashSet<>();
		}
		List<Event> events = eventsRepository.findByTargetGroup(group);
		return new HashSet<>(convertToEventResponses(getRelevantEvents(events), null));
	}

	@Override
	public List<EventResponse> getRelevantEventResponses(User user) {
		return convertToEventResponses(getAvailableEvents(getRelevantEvents(findAll()), user), user);
	}

	@Override
	public Long getNextEventId() {
		return idGenerator.getNextId();
	}

	private List<EventResponse> convertToEventResponses(List<Event> events, User user) {
		List<EventResponse> eventResponses = new ArrayList<>();
		for (Event event : events) {
			Hibernate.initialize(event.getParticipants());
			eventResponses.add(new EventResponse(event.getId(), event.getName(), event.getCategories(),
					event.getOwner(),
					(event.getTargetGroup() == null)? "" : event.getTargetGroup().getName(),
					event.getStartDateTime(),
					event.getEndDateTime(),
					event.getDeleted(),
					event.getPicture(),
					event.getLocation(),
					user != null && !event.getParticipants().isEmpty() && event.getParticipants().contains(user),
					event.getDescription(),
					event.getCurrency(),
					event.getCost(),
					event.getParticipants()));
		}
		return eventResponses;
	}

	private List<Event> getRelevantEvents(List<Event> events) {
		return events.stream().filter(event -> event.getStartDateTime().after(new Date()) &&
				!event.getDeleted()).collect(Collectors.toList());

	}

	@Override
	public Set<CategoryResponse> getUserEventsCounterByCategories(User user) {
		Set<Event> events = new HashSet<>(eventsRepository.findByOwner(user));
		events.addAll(user.getEvents());
		return new LinkedHashSet<>(categoriesService.countEventsByCategories(getAvailableEvents(getRelevantEvents(
				new ArrayList<>(events)), user)));
	}

	@Override
	public List<CategoryResponse> getEventsCounterByCategories(User user) {
		return categoriesService.countEventsByCategories(getAvailableEvents(getRelevantEvents(findAll()), user));
	}

	@Override
	public List<EventResponse> getArchivedEventsResponse(ArchiveServiceRequest request, User user) {
		List<Event> archivedEvents = findAll().stream().filter(event -> event.getEndDateTime()
				.after(request.getSearchFrom())
				&& event.getEndDateTime().before(request.getSearchTo())
				&& !event.getDeleted()).collect(Collectors.toList());
		return convertToEventResponses(filterArchivedEvents(archivedEvents, user), user);
	}

	private List<Event> filterArchivedEvents(List<Event> events, User user) {
		return events.stream().filter(event -> event.getParticipants().contains(user) ||
				event.getOwner().equals(user)).collect(Collectors.toList());
	}

	@Override
	public List<EventResponse> getArchivedUsersEventsResponse(ArchiveServiceRequest request, User user) {
		List<Event> archivedEvents = eventsRepository.findByOwner(user).stream().filter(event -> event
				.getEndDateTime().after(request.getSearchFrom())
				&& event.getEndDateTime().before(request.getSearchTo())
				&& !event.getDeleted()).collect(Collectors.toList());
		return convertToEventResponses(archivedEvents, user);
	}

	@Override
	public List<CategoryResponse> getArchivedEventsCounterByCategories(ArchiveServiceRequest request, User user) {
		return categoriesService.countEventsByCategories(filterArchivedEvents(findAll().stream()
				.filter(event -> event.getEndDateTime().after(request.getSearchFrom())
						&& event.getEndDateTime().before(request.getSearchTo())
						&& !event.getDeleted()).collect(Collectors.toList()), user));
	}

	@Override
	public List<CategoryResponse> getArchivedUsersEventsCounterByCategories(ArchiveServiceRequest request, User user) {
		return categoriesService.countEventsByCategories(eventsRepository.findByOwner(user).stream()
				.filter(event -> event.getEndDateTime().after(request.getSearchFrom())
						&& event.getEndDateTime().before(request.getSearchTo())
						&& !event.getDeleted()).collect(Collectors.toList()));
	}

	@Override
	public EventResponse initParticipants(Event event) {
		event = eventsRepository.findById(event.getId());
		if (event != null) {
			Hibernate.initialize(event.getParticipants());
			if(event.getTargetGroup() != null) {
				Hibernate.initialize(event.getTargetGroup().getGroupParticipants());
			}
		}
		//Event e = event;
		//e.setPicture("");
		List<Event> list = new ArrayList<>();
		list.add(event);
		EventResponse response = convertToEventResponses(list, event.getOwner()).get(0);
		return response;
		//return event;
	}
	public Event initParticipants(long id) {
		Event event = eventsRepository.findById(id);
		if (event != null) {
			Hibernate.initialize(event.getParticipants());
			if(event.getTargetGroup() != null) {
				Hibernate.initialize(event.getTargetGroup().getGroupParticipants());
			}
		}
		return event;
	}

	private List<Event> getAvailableEvents(List<Event> events, User user) {
		return events.stream().filter(event -> (event.getTargetGroup() == null ||
				(user != null && (event.getTargetGroup().getOwner().equals(user)
				||event.getTargetGroup().getGroupParticipants().contains(user)))))
				.collect(Collectors.toList());
	}

}
