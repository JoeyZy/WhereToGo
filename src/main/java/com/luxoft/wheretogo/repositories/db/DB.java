package com.luxoft.wheretogo.repositories.db;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {

	public static Map<String, Object> db = new HashMap<>();

	static {

		/* Categories */
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(0, "Nature", new ArrayList<Event>()));
		categories.add(new Category(1, "Movie", new ArrayList<Event>()));
		categories.add(new Category(2, "Theatre", new ArrayList<Event>()));
		categories.add(new Category(3, "Pub", new ArrayList<Event>()));
		categories.add(new Category(4, "Sport", new ArrayList<Event>()));
		categories.add(new Category(5, "Other", new ArrayList<Event>()));

		db.put("categories", categories);

		/* Events */
		List<Event> events = new ArrayList<>();
		User organizer = new User(0, "test", "test", "Ivan", "Ivanov");
		List<User> comers = new ArrayList<>();
		comers.add(new User(1, "test2", "test2", "Andrey", "Andreyev"));
		comers.add(new User(2, "test3", "test3", "Anton", "Antonov"));
		comers.add(new User(2, "test4", "test4", "Bohdan", "Bohdanov"));
		Event event =
				new Event(
						0,
						null,
						"Mission: Impossible",
						"Mission: Impossible is a 1996 American action spy film directed by Brian De Palma, produced by and starring Tom Cruise. Based on the television series of the same name, the plot follows Ethan Hunt (Cruise) and his mission to uncover the mole who has framed him for the murders of his entire IMF team. Work on the script had begun early with filmmaker Sydney Pollack on board, before De Palma, Steven Zaillian, David Koepp, and Robert Towne were brought in. Mission: Impossible went into pre-production without a shooting script. De Palma came up with some action sequences, but Koepp and Towne were dissatisfied with the story that led up to those events.",
						organizer, comers);
		categories.get(1).getEvents().add(event);
		events.add(event);
		event.setCategory(categories.get(1));

		db.put("events", events);

	}

}
