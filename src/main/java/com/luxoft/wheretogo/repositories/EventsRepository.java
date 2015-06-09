package com.luxoft.wheretogo.repositories;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.model.Event;

@Repository("events")
public class EventsRepository extends AbstractRepository<Event> {

	public EventsRepository() {
		super(Event.class);
	}

}
