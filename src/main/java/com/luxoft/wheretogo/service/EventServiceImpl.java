package com.luxoft.wheretogo.service;

import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by vkutsovol on 04.06.15.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	@Qualifier("events")
	private AbstractRepository<Event> eventRepository;

	@Override
	public void addEvent(Event event) {
		eventRepository.add(event);
	}

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	@Override
	public Event getById(int id) {
		return eventRepository.getById(id);
	}

	@Override
	public Event getByName(String eventName) {
		return eventRepository.getByName(eventName);
	}
}
