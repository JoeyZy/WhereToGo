package com.luxoft.wheretogo.repositories.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.model.User;

public class DB {

	public static Map<String, Object> db = new HashMap<>();

	static {

		/* Categories */
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(0, "Movie", null));
		categories.add(new Category(1, "Theatre", null));
		categories.add(new Category(2, "Nature", null));
		categories.add(new Category(3, "Sport", null));

		db.put("categories", categories);

		/* Events */
		List<Event> events = new ArrayList<>();
		User organizer = new User(0, "test", "test", "Ivan", "Ivanov");
		List<User> comers = new ArrayList<>();
		comers.add(new User(1, "test2", "test2", "Andrey", "Andreyev"));
		comers.add(new User(2, "test3", "test3", "Anton", "Antonov"));
		comers.add(new User(2, "test4", "test4", "Bohdan", "Bohdanov"));
		Event kapitoshka =
				new Event(
						0,
						null,
						"Mission: Impossible",
						"Mission: Impossible is a 1996 American action spy film directed by Brian De Palma, produced by and starring Tom Cruise. Based on the television series of the same name, the plot follows Ethan Hunt (Cruise) and his mission to uncover the mole who has framed him for the murders of his entire IMF team. Work on the script had begun early with filmmaker Sydney Pollack on board, before De Palma, Steven Zaillian, David Koepp, and Robert Towne were brought in. Mission: Impossible went into pre-production without a shooting script. De Palma came up with some action sequences, but Koepp and Towne were dissatisfied with the story that led up to those events.",
						organizer, comers);
		events.add(kapitoshka);
		categories.get(0).setEvents(events);
		kapitoshka.setCategory(categories.get(0));

		db.put("events", events);

	}

}
