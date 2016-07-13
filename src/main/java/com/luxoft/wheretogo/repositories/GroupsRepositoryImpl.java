package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eleonora on 07.07.16.
 */
@Repository
public class GroupsRepositoryImpl extends AbstractRepository<Group> implements GroupsRepository {

    private final static Logger LOGGER = Logger.getLogger(GroupsRepositoryImpl.class);

    public GroupsRepositoryImpl() {
        super(Group.class);
    }

    public void add(Group group) {
        super.add(group);
    }

    @Override
    public List<Group> findAll() {
        return super.findAll("id");
    }

    @Override
    public Group findById(long groupId){ return super.findByProperty("id",groupId); }

    @Override
    public Group findByName(String groupName) {
        return super.findByProperty("name", groupName);
    }

    @Override
    public List<Group> findByOwner(User owner) {
        return super.findListByProperty("owner", owner);
    }

}
