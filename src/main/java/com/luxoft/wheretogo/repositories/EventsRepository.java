package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Event;

import java.util.List;

public interface EventsRepository {

	void add(Event event);

	List<Event> findAll();

	Event findById(int eventId);

}
