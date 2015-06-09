package com.luxoft.wheretogo.service;

import java.util.List;

import com.luxoft.wheretogo.model.Event;

public interface EventsService {

	void addEvent(Event event);

	List<Event> findAll();

	Event getById(int eventId);

	Event getByName(String eventName);

}
