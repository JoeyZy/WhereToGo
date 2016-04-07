package com.luxoft.wheretogo.models;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.io.ByteStreams;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "events")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Size(min = 2, max = 30)
	private String name;

	private String description;
	private String location;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner")
	private User owner;

	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "events_categories", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "events_users", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> participants;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yy HH:mm", timezone="default")
	private Date startDateTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yy HH:mm", timezone="default")
	private Date endDateTime;

	@Column(name = "deleted", columnDefinition = "int(1)", nullable = false)
	private Integer deleted;

	private Integer cost;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currencyId")
	private Currency currency;

	@Column(name="picture")
	private Blob picture;

	public Event() {
	}

	public void setPicture(String value) {
		try {
			this.picture = new SerialBlob(value.getBytes());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getPicture() {
		if(this.picture == null) {
			return "";
		}

		try {
			return new String(ByteStreams.toByteArray(this.picture.getBinaryStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
