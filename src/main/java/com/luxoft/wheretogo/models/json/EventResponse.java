package com.luxoft.wheretogo.models.json;

import com.luxoft.wheretogo.models.Category;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EventResponse {
	private long id;
	private String name;
	private List<String> category;
	private String owner;
	private String description;
	private String startTime;
	private String endTime;

	public EventResponse(long id, String name, List<Category> category, String owner, Date startDateTime, Date endDateTime) {
		this.id = id;
		this.name = name;
		setCategories(category);
		this.owner = owner;
		this.startTime = timeToString(startDateTime);
		this.endTime = timeToString(endDateTime);
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
}
