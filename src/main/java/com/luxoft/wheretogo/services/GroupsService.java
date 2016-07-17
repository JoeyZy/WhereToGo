package com.luxoft.wheretogo.services;


import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.json.GroupResponse;

import java.util.List;

/**
 * Created by eleonora on 07.07.16.
 */
public interface GroupsService {

    boolean add(Group group);

    void update(Group group, String ownerEmail);

    public void update(Group group);

    Long getNextGroupId();

    Group findById(long groupId);

    List<Group> findAll();

    Group findByName(String groupName);

    List<GroupResponse> getRelevantGroupResponses();

}
