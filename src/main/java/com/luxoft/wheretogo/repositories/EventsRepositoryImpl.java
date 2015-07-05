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
		if (findByName(event.getName()) == null) {
			super.add(event);
		}
		super.update(event);
	}

	@Override
	public List<Event> findAll() {
		return super.findAll("id");
	}

	@Override
	public Event findById(long eventId) {
		return super.findByProperty("id", eventId);
	}

	@Override
	public Event findByName(String eventName) {
		return super.findByProperty("name", eventName);
	}
}
