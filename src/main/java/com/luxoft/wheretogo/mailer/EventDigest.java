package com.luxoft.wheretogo.mailer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.services.EventsService;

/**
 * Created by Sergii on 26.03.2016.
 */
public class EventDigest {

	@Autowired
	private EventsService eventsService;

	/**
	 * Returns events between  start and end dates
	 *
	 * @param from start date
	 * @param to   end date
	 * @return map (K = id of category, V = list of events)
	 */
	//	public Map<Long, List<Event>> getEvents(LocalDateTime from, LocalDateTime to) {
	//	Set<Long> categories = new HashSet<>();
	//
	//		Map<Long, List<Event>> result = new HashMap<>();
	////		new TreeMap<Long, List<Event>>(new TreeSet<Long>());
	//		for (Event event : eventsService.findByPeriod(from, to)) {
	//			categories.add(event.getCategories().)
	//		}
	public List<Event> getEvents(LocalDateTime from, LocalDateTime to) {
		return eventsService.findByPeriod(from, to);
	}

}
