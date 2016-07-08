package com.luxoft.wheretogo.services;


import com.luxoft.wheretogo.models.Group;

/**
 * Created by eleonora on 07.07.16.
 */
public interface GroupsService {

    boolean add(Group group);
    Long getNextGroupId();
    Group findById(long groupId);

    Group findByName(String groupName);
}
