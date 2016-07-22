package com.luxoft.wheretogo.models;

import com.google.common.io.ByteStreams;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by eleonora on 07.07.16.
 */
@Entity
@Table(name = "groups")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class Group {
    @Transient
    private static final Logger LOG = Logger.getLogger(Group.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, max = 30)
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name = "deleted", nullable = false)
    @Type(type = "yes_no")
    private Boolean deleted;

    @Size(min = 10, max = 250)
    @Column(name="description")
    private String description;

    @Size(min = 7, max = 100)
    @Column(name="location")
    private String location;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "groups_users", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> groupParticipants;

    @Column(name="picture")
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

}
