package com.luxoft.wheretogo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.repositories.AbstractRepository;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	@Qualifier("events")
	private AbstractRepository<Event> eventsRepository;

	@Override
	public void addEvent(Event event) {
		eventsRepository.add(event);
	}

	@Override
	public List<Event> findAll() {
		return eventsRepository.findAll();
	}

	@Override
	public Event getById(int eventId) {
		return eventsRepository.getById(eventId);
	}

	@Override
	public Event getByName(String eventName) {
		return eventsRepository.getByName(eventName);
	}
}
