package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.json.EventResponse;

import java.util.List;

public interface EventsService {

	void add(Event event);

	void update(Event event);

	List<Event> findAll();

	Event findById(long eventId);

	Event findByName(String eventName);

	List<EventResponse> getEventResponses();

}
