package com.luxoft.wheretogo.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.GroupResponse;

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

	Group initGroupParticipants(Group group);

	Set<GroupResponse> getUserRelevantGroupResponses(User user);

}
