package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.GroupIdGenerator;
import org.springframework.stereotype.Repository;

/**
 * Created by eleonora on 08.07.16.
 */
@Repository
public class GroupIdRepositoryImpl extends AbstractRepository<GroupIdGenerator> implements GroupIdGeneratorRepository {

    public GroupIdRepositoryImpl() {super(GroupIdGenerator.class); }

    @Override
    public Long getNextId(){
        return (Long) sessionFactory.getCurrentSession().save(new GroupIdGenerator());
    }
}
