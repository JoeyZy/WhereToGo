package com.luxoft.wheretogo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

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
    private static final Logger LOG = Logger.getLogger(Event.class);

    @Id
    private long id;

    @Size(min = 2, max = 30)
    private String name;

}
