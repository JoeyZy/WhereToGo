package com.luxoft.wheretogo.services;

import java.util.List;

import com.luxoft.wheretogo.models.Event;

public interface EventsService {

	void add(Event event);

	List<Event> findAll();

	Event findById(int eventId);

}
