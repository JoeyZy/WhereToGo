package com.luxoft.wheretogo.models.json;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.utils.ImageUtils;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
public class EventResponse {
	private static final String EMPTY_PICTURE = "resources/images/noimg.png";
	private long id;
	private String name;
	private Set<Category> categories;
	private List<String> category;
	private User owner;
	private String targetGroup;
	private String description;
	private String startTime;
	private String endTime;
	private String startDateTime;
	private String endDateTime;
	private Boolean deleted;
	private String picture;
	private String location;
	private Currency currency;
	private boolean attends;
	private int cost;
	private Set<User> participants;

	public EventResponse() {
	}

	public EventResponse(long id, String name, Set<Category> category, User owner, String targetGroup,
						 Date startDateTime, Date endDateTime, Boolean deleted, String picture, String location,
						 boolean attends, String description, Currency currency, int cost,Set<User> participants) {
		this.id = id;
		this.name = name;
		setCategories(category);
		this.owner = owner;
		this.description = description;
		this.targetGroup = targetGroup;
		this.startTime = timeToString(startDateTime);
		this.endTime = timeToString(endDateTime);
		this.startDateTime = timeToString(startDateTime);
		this.endDateTime = timeToString(endDateTime);
		this.deleted = deleted;
		this.location = location;
		this.currency = currency;
		this.cost = cost;
		this.participants = participants;
		this.picture = clearEmptyPicture(picture);
		if(!picture.equals("")){
			//this.picture = ImageUtils.giveMeImage(picture,true);
			//eventImage?id=9
			this.picture = "eventImage?id="+id;
		}else{
			picture.replace(EMPTY_PICTURE, "");
			this.picture = picture;
		}
		this.attends = attends;
	}


	public void setCategories(Set<Category> categories) {
		this.categories = new HashSet<>();
		this.category = new ArrayList<>();
		for (Category category : categories) {
			this.categories.add(category);
			this.category.add(category.getName());
		}
	}

	public void setStartTime(Date date) {
		startTime = timeToString(date);
	}

	public void setEndTime(Date date) {
		endTime = timeToString(date);
	}

	private String timeToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		return dateFormat.format(date);
	}

	private String clearEmptyPicture(String picture) {
		return picture == null ? "" : picture.replace(EMPTY_PICTURE, "");
	}
}
