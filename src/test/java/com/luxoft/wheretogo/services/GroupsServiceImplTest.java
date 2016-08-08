package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.GroupResponse;
import com.luxoft.wheretogo.repositories.GroupIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.GroupsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by maks on 03.08.16.
 */
public class GroupsServiceImplTest {

    @Mock
    GroupsRepository groupsRepository;

    @Mock
    GroupIdGeneratorRepository idGenerator;

    @InjectMocks
    GroupsServiceImpl groupsService;

    User user1;

    Group groupOfUser1;

    GroupResponse groupResponse1;

    @Before
    public void setUp() throws Exception {
        initUsers();
        initGroups();
        initGroupResponses();
        initConnectionBetweenUsersAndGroups();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addWhenOwnerIsActive() throws Exception {
        assertTrue(groupsService.add(groupOfUser1));

    }

    @Test
    public void addWhenOwnerIsNotActive() throws Exception {
        user1.setActive(false);
        assertFalse(groupsService.add(groupOfUser1));
    }

    @Test
    public void update() throws Exception {
        when(groupsRepository.findById(anyInt())).thenReturn(groupOfUser1);
        groupsService.update(groupOfUser1);
        verify(groupsRepository, atLeastOnce()).findById(anyInt());
    }


    @Test
    public void findById() throws Exception {
        when(groupsRepository.findById(anyInt())).thenReturn(groupOfUser1);
        assertEquals(groupsService.findById(groupOfUser1.getId()), groupOfUser1);
    }

    @Test
    public void findByName() throws Exception {
        when(groupsRepository.findByName(anyString())).thenReturn(groupOfUser1);
        assertEquals(groupsService.findByName(groupOfUser1.getName()), groupOfUser1);
    }

    @Test
    public void findAll() throws Exception {
        List<Group> groups = new ArrayList<>(user1.getGroups());
        when(groupsRepository.findAll()).thenReturn(groups);
        assertEquals(groupsService.findAll(), groups);
    }

    @Test
    public void getNextGroupId() throws Exception {
        Long one = new Long(1);
        when(idGenerator.getNextId()).thenReturn(1l);
        assertEquals(groupsService.getNextGroupId(), one);
    }

    @Test
    public void getRelevantGroupResponses() throws Exception {
        List<GroupResponse> expectedResult = new ArrayList<>();
        expectedResult.add(groupResponse1);
        List<Group> groups = new ArrayList<>(user1.getGroups());
        when(groupsRepository.findAll()).thenReturn(groups);
        List<GroupResponse> actualResult = groupsService.getRelevantGroupResponses();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getUserRelevantGroupResponses() throws Exception {
        List<GroupResponse> expectedResult = new ArrayList<>();
        expectedResult.add(groupResponse1);
        List<Group> groups = new ArrayList<>(user1.getGroups());
        when(groupsRepository.findByOwner(any(User.class))).thenReturn(groups);
        when(groupsRepository.findById(anyInt())).thenReturn(groupOfUser1);
        List<GroupResponse> actualResult = new ArrayList(groupsService.getUserRelevantGroupResponses(user1));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getUserGroups() throws Exception {
        List<GroupResponse> expectedResult = new ArrayList<>();
        expectedResult.add(groupResponse1);
        List<Group> groups = new ArrayList<>(user1.getGroups());
        when(groupsRepository.findByOwner(any(User.class))).thenReturn(groups);
        List<GroupResponse> actualResult = new ArrayList(groupsService.getUserGroups(user1));
        assertEquals(expectedResult, actualResult);
    }

    public void initUsers() {
        user1 = new User();
        user1.setFirstName("user1");
        user1.setLastName("user1");
        user1.setActive(true);
    }

    public void initGroups() {
        groupOfUser1 = new Group();
        groupOfUser1.setId(1);
        groupOfUser1.setName("groupOfUser1");
        groupOfUser1.setDeleted(false);
    }

    public void initGroupResponses() {
        groupResponse1 = new GroupResponse();
        groupResponse1.setId(1);
        groupResponse1.setName("groupOfUser1");
        groupResponse1.setDeleted(false);
        groupResponse1.setPicture("");
    }

    public void initConnectionBetweenUsersAndGroups() {
        groupOfUser1.setOwner(user1);
        Set<Group> groups = new HashSet<Group>();
        groups.add(groupOfUser1);
        User u = new User();
        groupResponse1.setOwner(u);
        user1.setGroups(groups);
    }


}