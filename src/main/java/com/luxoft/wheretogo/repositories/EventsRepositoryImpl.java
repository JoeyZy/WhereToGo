package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventsRepositoryImpl extends AbstractRepository<Event> implements EventsRepository {

	public EventsRepositoryImpl() {
		super(Event.class);
	}

	public void add(Event event) {
		super.add(event);
	}

	@Override
	public List<Event> findAll() {
		return super.findAll();
	}

	@Override
	public Event findById(int eventId) {
		return super.findByProperty("id", eventId);
	}
}
