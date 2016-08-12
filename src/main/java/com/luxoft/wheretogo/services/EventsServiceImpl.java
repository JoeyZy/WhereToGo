package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.configuration.ConfigureSpringSecurity;
import com.luxoft.wheretogo.models.ArchiveServiceRequest;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.repositories.EventIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.EventsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
	public boolean add(Event event) {
		if (event.getOwner().isActive()) {
			eventsRepository.add(event);
			return true;
		}
		return false;
	}

	@Override
	public void update(Event event, String ownerEmail, Collection<? extends GrantedAuthority> authorities) {
		Event oldEvent = initParticipants(event);
		User owner;
		if (oldEvent != null) {
			owner = oldEvent.getOwner();
			if (!owner.getEmail().equals(ownerEmail) &&
					!authorities.contains(ConfigureSpringSecurity.grantedAdminRole)) {
				return;
			}
			event.setOwner(oldEvent.getOwner());
			event.setParticipants(oldEvent.getParticipants());
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
			eventResponses.add(new EventResponse(event.getId(), event.getName(), event.getCategories(),
					event.getOwner().getFirstName() + " " + event.getOwner().getLastName(),
					(event.getTargetGroup() == null)? "" : event.getTargetGroup().getName(),
					event.getStartDateTime(),
					event.getEndDateTime(),
					event.getDeleted(),
					event.getPicture(),
					event.getLocation(),
					user != null && !event.getParticipants().isEmpty() && event.getParticipants().contains(user)));
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
	public Event initParticipants(Event event) {
		event = eventsRepository.findById(event.getId());
		if (event != null) {
			Hibernate.initialize(event.getParticipants());
			if(event.getTargetGroup() != null) {
				Hibernate.initialize(event.getTargetGroup().getGroupParticipants());
			}
		}
		return event;
	}

	private List<Event> getAvailableEvents(List<Event> events, User user) {
		return events.stream().filter(event -> event.getTargetGroup() == null ||
				user != null && (event.getTargetGroup().getOwner().equals(user)
				||event.getTargetGroup().getGroupParticipants().contains(user)))
				.collect(Collectors.toList());
	}

}
