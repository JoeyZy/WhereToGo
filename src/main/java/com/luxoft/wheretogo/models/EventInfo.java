package com.luxoft.wheretogo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.io.ByteStreams;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by maks on 26.07.16.
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@ToString(of = {"name"})
public class EventInfo {

    private static final Logger LOG = Logger.getLogger(Event.class);

    private long id;

    private String name;

    private Set<Category> categories;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yy HH:mm", timezone="default")
    private Date startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yy HH:mm", timezone="default")
    private Date endTime;

    private String description;
    private long targetGroup;
    private String location;
    private Integer cost;
    private Currency currency;
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
    }

    public EventInfo(long id, String name, Set<Category> categories, Date startTime, Date endTime,
                     String description, long targetGroup, String location, Integer cost,
                     Currency currency, Blob picture) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.targetGroup = targetGroup;
        this.location = location;
        this.cost = cost;
        this.currency = currency;
        this.picture = picture;
    }

    public EventInfo() {}

}