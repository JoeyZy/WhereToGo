package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.ArchiveServiceRequest;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventsService {

	void add(Event event);

	void update(Event event);

	List<Event> findAll();

	List<Event> findByPeriod(LocalDateTime from, LocalDateTime to);

	Event findById(long eventId);

	Event findByName(String eventName);

	List<EventResponse> getEventResponses();

	Set<EventResponse> getUserRelevantEventResponses(User user);

	List<EventResponse> getRelevantEventResponses(User user);

	Long getNextEventId();

	Set<CategoryResponse> getUserEventsCounterByCategories(User user);

	List<CategoryResponse> getEventsCounterByCategories();

	List<EventResponse> getArchivedEventsResponse(ArchiveServiceRequest request, User user);

	List<EventResponse> getArchivedUsersEventsResponse(ArchiveServiceRequest request, User user);

}
