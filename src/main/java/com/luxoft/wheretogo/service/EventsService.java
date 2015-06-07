package com.luxoft.wheretogo.service;

import com.luxoft.wheretogo.model.Event;

import java.util.List;

public interface EventsService {

	void addEvent(Event event);

	List<Event> findAll();

	Event getById(int eventId);

	Event getByName(String eventName);


}
