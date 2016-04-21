package com.luxoft.wheretogo.models.json;

import com.luxoft.wheretogo.models.Category;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EventResponse {
	private static final String EMPTY_PICTURE = "resources/images/noimg.png";
	private long id;
	private String name;
	private List<String> category;
	private String owner;
	private String description;
	private String startTime;
	private String endTime;
	private Integer deleted;
	private String picture;
	private String location;

	public EventResponse(long id, String name, List<Category> category, String owner, Date startDateTime, Date endDateTime, Integer deleted, String picture, String location) {
		this.id = id;
		this.name = name;
		setCategories(category);
		this.owner = owner;
		this.startTime = timeToString(startDateTime);
		this.endTime = timeToString(endDateTime);
		this.deleted = deleted;
		this.location = location;
		this.picture = clearEmptyPicture(picture);
	}

	public void setCategories(List<Category> categories) {
		this.category = new ArrayList<>();
		for (Category category : categories) {
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
