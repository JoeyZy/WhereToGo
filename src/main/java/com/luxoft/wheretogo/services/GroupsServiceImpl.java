package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.repositories.GroupIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by eleonora on 07.07.16.
 */

@Service
@Transactional
public class GroupsServiceImpl implements GroupsService{

    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private GroupIdGeneratorRepository idGenerator;

    @Override
    public boolean add(Group group) {

        groupsRepository.add(group);
        return true;
    }

    @Override
    public Group findById(long id){
        return groupsRepository.findById(id);
    }

    @Override
    public Group findByName(String groupName){
        return groupsRepository.findByName(groupName);
    }
    @Override
    public Long getNextGroupId() { return idGenerator.getNextId();}
}
