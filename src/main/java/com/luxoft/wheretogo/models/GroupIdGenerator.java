package com.luxoft.wheretogo.models;

import javax.persistence.*;

/**
 * Created by eleonora on 08.07.16.
 */

@Entity
@Table(name="groups_id")
public class GroupIdGenerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
