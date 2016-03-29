package com.luxoft.wheretogo.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.luxoft.wheretogo.models.Event;

public interface EventsRepository {

	void add(Event event);

	void merge(Event event);

	List<Event> findAll();

	List<Event> findByPeriod(LocalDateTime from, LocalDateTime to);

	Event findById(long eventId);

	Event findByName(String eventName);

}
