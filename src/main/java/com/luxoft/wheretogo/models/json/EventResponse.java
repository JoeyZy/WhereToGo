package com.luxoft.wheretogo.models.json;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.models.User;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class EventResponse {
	private static final String EMPTY_PICTURE = "resources/images/noimg.png";
	private long id;
	private String name;
	private List<Category> categories;
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

	public EventResponse(long id, String name, List<Category> category, User owner, String targetGroup,
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
		String imageData = "";
		if(!picture.equals("")){
			try {
				Path p = Paths.get(picture);
				byte[] arr = Files.readAllBytes(p);
				imageData = new String(arr);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.picture = imageData;
		}else{
			picture.replace(EMPTY_PICTURE, "");
			this.picture = picture;
		}
		this.attends = attends;
	}


	public void setCategories(List<Category> categories) {
		this.categories = new ArrayList<>();
		for (Category category : categories) {
			this.categories.add(category);
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
