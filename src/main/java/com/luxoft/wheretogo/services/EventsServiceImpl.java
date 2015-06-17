package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
	public List<Event> findAll() {
		return eventsRepository.findAll();
	}

	@Override
	public Event findById(int eventId) {
		return eventsRepository.findById(eventId);
	}
}
