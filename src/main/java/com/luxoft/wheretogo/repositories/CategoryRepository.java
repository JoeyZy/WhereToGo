package com.luxoft.wheretogo.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.model.Category;
import com.luxoft.wheretogo.model.Event;
import com.luxoft.wheretogo.model.User;

@Repository("categories")
public class CategoryRepository extends AbstractRepository<Category> {

	List<Category> categoriesList = new ArrayList<>();

	public List<Category> findAll() {
		return categoriesList;
	}

	public Category getById(int id) {
		for (Category category : categoriesList) {
			if (category.getId() == id) {
				return category;
			}
		}
		return null;
	}

	public Category getByName(String name) {
		for (Category category : categoriesList) {
			if (category.getName().equals(name)) {
				return category;
			}
		}
		return null;
	}

	private void fillData() {
		categoriesList.add(getMovieCategory());
		categoriesList.add(new Category(1, "Theatre", null));
		categoriesList.add(new Category(2, "Nature", null));
		categoriesList.add(new Category(3, "Sport", null));
	}

	private Category getMovieCategory() {
		Category category = new Category();
		User organizer = new User(0, "test", "test", "Ivan", "Ivanov");
		List<User> comers = new ArrayList<>();
		comers.add(new User(1, "test2", "test2", "Andrey", "Andreyev"));
		comers.add(new User(2, "test3", "test3", "Anton", "Antonov"));
		comers.add(new User(2, "test4", "test4", "Bohdan", "Bohdanov"));
		Event kapitoshka =
				new Event(
						0,
						"Kapitoshka",
						"Капітошка — літературний персонаж, який був створений письменницею і сценаристом Наталею Гузеєвою, герой мультиплікаційних фільмів та дитячих книг. Художній образ Капітошки був втілений художником Генріхом Уманським.\n"
								+ "\n"
								+ "Мультиплікаційний фільм складається з двох серій: «Капітошка» та «Капітошка, повертайся!» У першій серії ми бачимо Вовченя, яке намагається навчитися бути страшним і грізним. Але потім він зустрічає краплинку дощу Капітошку, завдяки якому розуміє, що зовсім не обов'язково бути страшним.\n"
								+ "\n"
								+ "Друга серія, яка була випущена у 1989 році, розповідає про те, як Вовченя очікує на Капітошку, а також про те, як до нього навідується його зла тітонька. Вовченя намагалося їй пояснити, що не хоче бути злим вовком, таким, як вона. Тут з'являється Капітошка і разом з Вовченям вони проганяють тітку.",
						organizer, comers);
		List<Event> events = new ArrayList<>();
		events.add(kapitoshka);
		category.setId(0);
		category.setName("Movies");
		category.setEvents(events);
		return category;
	}

	{
		fillData();
	}
}
