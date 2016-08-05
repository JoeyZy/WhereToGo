package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.configuration.ConfigureSpringSecurity;
import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.GroupResponse;
import com.luxoft.wheretogo.repositories.GroupIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.GroupsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;

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
            //Generating image/file and path to store group data
            String imageDataString = group.getPicture();
            if(imageDataString.length()!=0){
                Random rnd = new Random();
                String fileName = generateString(rnd,"qwertyuiop0987654321asdfghjklzxcvbnm",6);
                String path = "/home/bobbi/Bob/WhereToGo/"+fileName;
                //Default path: apache-tomcat -> bin
                File img = new File(path);
                path = img.getAbsolutePath();
                try {
                    FileWriter wr = new FileWriter(img);
                    wr.write(imageDataString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                group.setPicture(path);
            }
            //End of generation
            groupsRepository.add(group);
            return true;
        }
        return false;
    }
    public static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
    @Override
    public void update(Group group, String ownerEmail, Collection<? extends GrantedAuthority> authorities) {
        Group oldGroup = initGroupParticipants(group);
        User owner;
        if (oldGroup != null) {
            owner = oldGroup.getOwner();
            if (!owner.getEmail().equals(ownerEmail) &&
                    !authorities.contains(ConfigureSpringSecurity.grantedAdminRole)) {
                return;
            }
            group.setOwner(owner);
            group.setGroupParticipants(oldGroup.getGroupParticipants());
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

    public Group initGroupParticipantslist(long id) {
        Group group = groupsRepository.findById(id);
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
    public Set<GroupResponse> getUserGroups(User user) {
        List<Group> groups = groupsRepository.findByOwner(user);
        return new HashSet<>(convertToGroupResponses(groups));
    }

    @Override
    public Group initParticipants(Group group) {
        group = groupsRepository.findById(group.getId());
        if (group != null) {
            Hibernate.initialize(group.getGroupParticipants());
        }
        return group;
    }

}
