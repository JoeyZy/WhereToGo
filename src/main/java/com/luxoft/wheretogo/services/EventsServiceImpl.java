package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.ArchiveServiceRequest;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.repositories.EventIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.EventsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private EventIdGeneratorRepository idGenerator;
	@Autowired
	private CategoriesService categoriesService;

	@Override
	public void add(Event event) {
		eventsRepository.add(event);
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
		for (Event event : events) {
			Hibernate.initialize(event.getParticipants());
		}
		return events;
	}

	@Override
	public Event findById(long eventId) {
		Event event = eventsRepository.findById(eventId);
		if (event != null) {
			Hibernate.initialize(event.getParticipants());
		}
		return event;
	}

	@Override
	public Event findByName(String eventName) {
		Event event = eventsRepository.findByName(eventName);
		if (event != null) {
			Hibernate.initialize(event.getParticipants());
		}
		return event;
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
	public List<EventResponse> getRelevantEventResponses(User user) {
		return convertToEventResponses(getRelevantEvents(findAll()), user);
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
					event.getOwner().getFirstName() + " " + event.getOwner().getLastName(),
					event.getStartDateTime(),
					event.getEndDateTime(),
					event.getDeleted(),//
					event.getPicture(),
					event.getLocation(),
					user != null && !event.getParticipants().isEmpty() && event.getParticipants().contains(user)));
		}
		return eventResponses;
	}

	private List<Event> getRelevantEvents(List<Event> events) {
		return events.stream().filter(event -> event.getStartDateTime().after(new Date()) && !(event.getDeleted() == 1)).collect(Collectors.toList());

	}

	@Override
	public Set<CategoryResponse> getUserEventsCounterByCategories(User user) {
		Set<Event> events = new HashSet<>(eventsRepository.findByOwner(user));
		events.addAll(user.getEvents());
		return new LinkedHashSet<>(categoriesService.countEventsByCategories(getRelevantEvents(new ArrayList<>(events))));
	}

	@Override
	public List<CategoryResponse> getEventsCounterByCategories() {
		return categoriesService.countEventsByCategories(getRelevantEvents(findAll()));
	}


	@Override
	public List<EventResponse> getArchivedEventsResponse(ArchiveServiceRequest request, User user) {
		List<Event> archivedevents = findAll().stream().filter(event -> {
				return event.getEndDateTime().after(request.getSearchFrom())
                        && event.getEndDateTime().before(request.getSearchTo())
                        && !(event.getDeleted() == 1);
		}).collect(Collectors.toList());
			return convertToEventResponses(filterArchivedEvents(archivedevents, user), user);
	}

	private List<Event> filterArchivedEvents(List<Event> events, User user) {
		return events.stream().filter(event -> {
			return event.getParticipants().contains(user) || event.getOwner().equals(user);
		}).collect(Collectors.toList());
	}

	@Override
	public List<EventResponse> getArchivedUsersEventsResponse(ArchiveServiceRequest request, User user) {
		List<Event> archivedevents = eventsRepository.findByOwner(user).stream().filter(event -> {
			return event.getEndDateTime().after(request.getSearchFrom())
					&& event.getEndDateTime().before(request.getSearchTo()) && !(event.getDeleted() == 1);
		}).collect(Collectors.toList());
		return convertToEventResponses(archivedevents, user);
	}

	@Override
	public List<CategoryResponse> getArchivedEventsCounterByCategories(ArchiveServiceRequest request, User user) {
	return categoriesService.countEventsByCategories(filterArchivedEvents(findAll().stream().filter(event -> {
		return event.getEndDateTime().after(request.getSearchFrom())
				&& event.getEndDateTime().before(request.getSearchTo())
				&& !(event.getDeleted() == 1);
	}).collect(Collectors.toList()), user));
	}

	@Override
	public List<CategoryResponse> getArchivedUsersEventsCounterByCategories(ArchiveServiceRequest request, User user) {
		return categoriesService.countEventsByCategories(eventsRepository.findByOwner(user).stream().filter(event -> {
			return event.getEndDateTime().after(request.getSearchFrom())
					&& event.getEndDateTime().before(request.getSearchTo()) && !(event.getDeleted() == 1);
		}).collect(Collectors.toList()));
	}
}
