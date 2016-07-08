package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Group;

/**
 * Created by eleonora on 07.07.16.
 */
public interface GroupsRepository {

    void add(Group group);

    Group findById(long groupId);

    Group findByName(String groupName);
}
