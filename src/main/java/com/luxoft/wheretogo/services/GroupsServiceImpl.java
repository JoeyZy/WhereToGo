package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Event;
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
import java.util.List;

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
        Group oldEvent = findById(group.getId());
        User owner;
        if (oldEvent != null) {
            owner = oldEvent.getOwner();
            if (!owner.getEmail().equals(ownerEmail)) {
                return;
            }
            group.setOwner(owner);
            if (group.getParticipants() == null) {
                group.setParticipants(oldEvent.getParticipants());
            }
        }
        groupsRepository.merge(group);
    }

    @Override
    public Group findById(long groupId){
        Group group = groupsRepository.findById(groupId);
        if (group != null) {
            Hibernate.initialize(group.getParticipants());
        }
        return group;
    }

    @Override
    public Group findByName(String groupName) {
        Group group = groupsRepository.findByName(groupName);
        if (group != null) {
            Hibernate.initialize(group.getParticipants());
        }
        return group;
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
            groupResponses.add(new GroupResponse(group.getId(), group.getName(),
                    group.getOwner().getFirstName()+ " " +group.getOwner().getLastName(),
                    group.getLocation(), group.getDescription(), group.getPicture(), group.getDeleted()));
                }
        return groupResponses;
    }

}
