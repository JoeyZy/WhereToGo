package com.luxoft.wheretogo.services;


import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.GroupResponse;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by eleonora on 07.07.16.
 */
public interface GroupsService {

    boolean add(Group group);

    void update(Group group, String ownerEmail, Collection<? extends GrantedAuthority> authorities);

    void update(Group group);

    Long getNextGroupId();

    Group findById(long groupId);

    List<Group> findAll();

    Group findByName(String groupName);

    List<GroupResponse> getRelevantGroupResponses();

    GroupResponse initGroupParticipants(Group group);
    Group initGroupParticipantslist(long id);

    Set<GroupResponse> getUserRelevantGroupResponses(User user);

    Group initParticipants(Group group);
    Set<GroupResponse> getUserGroups(User user);

}
