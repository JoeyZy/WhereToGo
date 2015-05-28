package com.luxoft.wheretogo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.repositories.db.DB;

@Repository("events")
public class EventRepository extends AbstractRepository<Event> {

	public EventRepository() {
		super(Event.class);
	}

	{
		elementsList = (List<Event>) DB.db.get("events");
	}

}
