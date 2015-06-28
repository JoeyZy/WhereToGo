package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("new")
public class NewDesignController {

	@Autowired
	private EventsService eventsService;

	@RequestMapping("*")
	public String init() {
		return "sap/index";
	}

	@RequestMapping("/events")
	@ResponseBody
	public List<EventResponse> events() {
		return eventsService.getEventResponses();
	}

	@RequestMapping("/event")
	@ResponseBody
	public Event event(Event event) {
		return eventsService.findById(event.getId());
	}


}
