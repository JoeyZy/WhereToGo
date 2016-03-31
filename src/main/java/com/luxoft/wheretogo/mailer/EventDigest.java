package com.luxoft.wheretogo.mailer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.UsersService;

/**
 * Created by Sergii on 26.03.2016.
 */
public class EventDigest {

	@Autowired
	private EventsService eventsService;

	@Autowired
	private UsersService usersService;

	/**
	 * Returns events between  start and end dates
	 *
	 * @param from start date
	 * @param to   end date
	 * @return map (K = id of category, V = list of events)
	 */
	public Map<String, List<Event>> getEvents(LocalDateTime from, LocalDateTime to) {

		Map<String, List<Event>> result = new HashMap<>();

		for (Event event : eventsService.findByPeriod(from, to)) {
			// now each event has only one category, but it may have more than one
			// this feature is disabled on UI side but back-end still supports multiple categories :)
			// anyway, putting event to each category that it belongs to is not so clever :)
			String eventCatId = event.getCategories().get(0).getName();

			if (result.containsKey(eventCatId)) {
				result.get(eventCatId).add(event);
			} else {
				List<Event> events = new LinkedList<>();
				events.add(event);
				result.put(eventCatId, events);
			}
		}
		return result;
	}

	//in the future we will filter out users by categories
	//and send digest based on user's interests
	public List<User> getUsers() {
		return usersService.findAll();
	}
}
