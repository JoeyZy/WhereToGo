package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.EventIdGenerator;
import com.luxoft.wheretogo.repositories.AbstractRepository;
import com.luxoft.wheretogo.repositories.EventIdGeneratorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EventIdRepositoryImpl extends AbstractRepository<EventIdGenerator> implements EventIdGeneratorRepository {

    public EventIdRepositoryImpl() {
        super(EventIdGenerator.class);
    }

    @Override
    public Long getNextId() {
        return (Long) sessionFactory.getCurrentSession()
                .save(new EventIdGenerator());
    }
}
