package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.model.Event;
import org.springframework.stereotype.Repository;

@Repository("events")
public class EventsRepository extends AbstractRepository<Event> {

	public EventsRepository() {
		super(Event.class);
	}

}
