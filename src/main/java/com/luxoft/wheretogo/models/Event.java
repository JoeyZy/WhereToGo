package com.luxoft.wheretogo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.io.ByteStreams;
import com.luxoft.wheretogo.repositories.GroupsRepositoryImpl;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class Event {
	@Transient
	private static final Logger LOG = Logger.getLogger(Event.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Size(min = 2, max = 30)
	@Column(name = "name")
	private String name;

	@Size(min = 10, max = 1000)
	@Column(name = "description")
	private String description;

	@Size(min = 7, max = 100)
	@Column(name = "location")
	private String location;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner")
	private User owner;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "target_group")
	private Group targetGroup;

	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "events_categories", joinColumns = @JoinColumn(name = "event_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "events_users", joinColumns = @JoinColumn(name = "event_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> participants;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yy HH:mm", timezone="default")
	private Date startDateTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yy HH:mm", timezone="default")
	private Date endDateTime;

	@Column(name = "deleted", nullable = false)
	@Type(type = "yes_no")
	private Boolean deleted;

	@Column(name = "cost")
	private Integer cost;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currencyId")
	private Currency currency;

	/*@Column(name="picture")
	private Blob picture;

	public void setPicture(String value) {
		try {
			this.picture = new SerialBlob(value.getBytes());
		} catch (SQLException e) {
			LOG.warn("Can not convert image for saving", e);
		}
	}

	public String getPicture() {
		if(this.picture == null) {
			return "";
		}

		try {
			return new String(ByteStreams.toByteArray(this.picture.getBinaryStream()));
		} catch (Exception e) {
			LOG.warn("Can not convert image", e);
		}
		return "";
	}*/
	@Column(name="picture")
	private String picture;

	public void setPicture(String path){
		this.picture = path;
	}
	public String getPicture(){
		if(this.picture == null) {
			return "";
		}
		return this.picture;
	}

	public void setInfo(EventInfo eventInfo) {
		this.id = eventInfo.getId();
		this.name = eventInfo.getName();
		this.categories = eventInfo.getCategories();
		this.startDateTime = eventInfo.getStartTime();
		this.endDateTime = eventInfo.getEndTime();
		this.description = eventInfo.getDescription();
		this.location = eventInfo.getLocation();
		this.cost = eventInfo.getCost();
		this.currency = eventInfo.getCurrency();
		setPicture(eventInfo.getPicture());
	}
}
