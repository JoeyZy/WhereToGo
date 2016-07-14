package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;

import java.util.List;

/**
 * Created by eleonora on 07.07.16.
 */
public interface GroupsRepository {

    void add(Group group);

    void update(Group group);

    void delete(Group group);

    List<Group> findAll();

    Group findById(long groupId);

    Group findByName(String groupName);

    List<Group> findByOwner(User owner);
}
