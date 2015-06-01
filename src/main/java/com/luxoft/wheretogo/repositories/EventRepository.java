package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.repositories.db.DB;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("events")
public class EventRepository extends AbstractRepository<Event> {

	{
		elementsList = (List<Event>) DB.db.get("events");
	}

	public EventRepository() {
		super(Event.class);
	}

}
