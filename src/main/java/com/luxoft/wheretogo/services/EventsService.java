package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Event;

import java.util.List;

public interface EventsService {

	void add(Event event);

	List<Event> findAll();

	Event findById(int eventId);

	Event findByName(String eventName);

}
