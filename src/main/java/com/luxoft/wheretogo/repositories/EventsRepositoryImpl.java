package com.luxoft.wheretogo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.models.Event;

@Repository
public class EventsRepositoryImpl extends AbstractRepository<Event> implements EventsRepository {

	public EventsRepositoryImpl() {
		super(Event.class);
	}

	public void add(Event object) {
		super.add(object);
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
