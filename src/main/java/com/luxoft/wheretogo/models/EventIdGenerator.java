package com.luxoft.wheretogo.models;

/**
 * Cause of MySQL does not support sequence
 * it is the rightmost approach to get id prior of creating of event.
 * Also it's requires to avoid duplication of saving identical events at JS multiple
 * submition that happens on some PC and browsers.
 */



import javax.persistence.*;

@Entity
@Table(name="events_id")
public class EventIdGenerator {

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
