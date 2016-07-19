package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.GroupResponse;
import com.luxoft.wheretogo.repositories.GroupIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.GroupsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eleonora on 07.07.16.
 */

@Service
@Transactional
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private GroupIdGeneratorRepository idGenerator;

    @Override
    public boolean add(Group group) {
        if (group.getOwner().isActive()) {
            groupsRepository.add(group);
            return true;
        }
        return false;
    }

    @Override
    public void update(Group group, String ownerEmail) {
        Group oldGroup = initGroupParticipants(group);
        User owner;
        if (oldGroup != null) {
            owner = oldGroup.getOwner();
            if (!owner.getEmail().equals(ownerEmail)) {
                return;
            }
            group.setOwner(owner);
            if (group.getGroupParticipants() == null) {
                group.setGroupParticipants(oldGroup.getGroupParticipants());
            }
        }
        groupsRepository.merge(group);
    }

    @Override
    public void update(Group group) {
        Group oldGroup = findById(group.getId());
        if (oldGroup != null) {
            group.setOwner(oldGroup.getOwner());
            if (group.getGroupParticipants() == null) {
                group.setGroupParticipants(oldGroup.getGroupParticipants());
            }
        }
        groupsRepository.merge(group);
    }

    @Override
    public Group findById(long groupId) {
        return groupsRepository.findById(groupId);
    }

    @Override
    public Group findByName(String groupName) {
        return groupsRepository.findByName(groupName);
    }

    @Override
    public List<Group> findAll() {
        return groupsRepository.findAll();
    }


    @Override
    public Long getNextGroupId() { return idGenerator.getNextId();}

    @Override
    public List<GroupResponse> getRelevantGroupResponses() {
        return convertToGroupResponses(findAll());
    }

    private List<GroupResponse> convertToGroupResponses(List<Group> groups) {
        List<GroupResponse> groupResponses = new ArrayList<>();
        for (Group group : groups) {
            if (!group.getDeleted()) {
                groupResponses.add(new GroupResponse(group.getId(), group.getName(),
                        group.getOwner().getFirstName() + " " + group.getOwner().getLastName(),
                        group.getLocation(), group.getDescription(), group.getPicture(), group.getDeleted()));
            }
        }
        return groupResponses;
    }

    public Group initGroupParticipants(Group group) {
        group = groupsRepository.findById(group.getId());
        if (group != null) {
            Hibernate.initialize(group.getGroupParticipants());
        }
        return group;
    }
    @Override
    public Set<GroupResponse> getUserRelevantGroupResponses(User user) {
        List<Group> groups = groupsRepository.findByOwner(user);
        Set<Group> d = user.getGroups();
        for (Group g : d) {
            groups.add(groupsRepository.findById(g.getId()));
        }
        return new HashSet<>(convertToGroupResponses(groups));
    }

}
