package com.luxoft.wheretogo.service;

import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.model.Model;

import java.util.List;

/**
 * Created by vkutsovol on 04.06.15.
 */
public interface EventService {

	void addEvent(Event event);
	List<Event> findAll();
	Model getById(int id);
	Event getByName(String eventName);


}
