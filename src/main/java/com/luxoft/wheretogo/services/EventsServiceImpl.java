package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.repositories.EventsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	private EventsRepository eventsRepository;

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
		List<EventResponse> eventResponses = new ArrayList<>();
		List<Event> events = findAll();
		for (Event event : events) {
			eventResponses.add(new EventResponse(event.getId(), event.getName(), event.getCategories(), event.getOwner().getFirstName() + " " + event.getOwner().getLastName(), event.getStartDateTime(), event.getEndDateTime(), event.getDeleted()));
		}
		return eventResponses;
	}

	@Override
	public List<EventResponse> getRelevantEventResponses() {
		List<EventResponse> eventResponses = new ArrayList<>();
		List<Event> events = findAll();
		List<Event> relevantEvents = new ArrayList<>();
		for (Event event : events) {
			if (event.getStartDateTime().after(new Date()) && !(event.getDeleted() == 1)) {
				relevantEvents.add(event);
			}
		}
		for (Event event : relevantEvents) {
			eventResponses.add(new EventResponse(event.getId(), event.getName(), event.getCategories(), event.getOwner().getFirstName() + " " + event.getOwner().getLastName(), event.getStartDateTime(), event.getEndDateTime(), event.getDeleted()));
		}
		return eventResponses;
	}

	@Override
	public List<EventResponse> getRelevantEventResponses() {
		List<EventResponse> eventResponses = new ArrayList<>();
		List<Event> events = findAll();
		List<Event> relevantEvents = new ArrayList<>();
		for(Event event :events) {
			if (event.getStartDateTime().after(new Date())){
				relevantEvents.add(event);
			}
		}

		for (Event event : relevantEvents) {
			eventResponses.add(new EventResponse(event.getId(), event.getName(), event.getCategories(), event.getOwner().getFirstName() + " " + event.getOwner().getLastName(), event.getStartDateTime(), event.getEndDateTime()));
		}
		return eventResponses;
	}
}
