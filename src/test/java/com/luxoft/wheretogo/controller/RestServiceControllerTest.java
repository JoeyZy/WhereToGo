package com.luxoft.wheretogo.controller;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.models.json.GroupResponse;
import com.luxoft.wheretogo.services.CurrenciesService;
import com.luxoft.wheretogo.services.EventsService;
import com.luxoft.wheretogo.services.GroupsService;
import com.luxoft.wheretogo.services.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maks on 03.08.16.
 */
public class RestServiceControllerTest {

    @Mock
    EventsService eventsService;

    @Mock
    CurrenciesService currenciesService;

    @Mock
    UsersService usersService;

    @Mock
    private GroupsService groupsService;

    @InjectMocks
    RestServiceController serviceController;

    List<User> users = new ArrayList<>();

    List<Event> events = new ArrayList<>();

    List<EventResponse> eventResponses = new ArrayList<>();

    List<GroupResponse> groupResponses = new ArrayList<>();

    MockHttpServletRequest request;

    MockHttpSession session;

    @Before
    public void setUp() {
        initEvents();
        initUsers();
        initEventResponses();
        initGroupResponses();
        initRequests();
        initConnectionsBetweenModels();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void events() throws Exception {
        session.setAttribute("user", users.get(0));
        when(eventsService.getRelevantEventResponses(any(User.class))).thenReturn(eventResponses);
        assertEquals(eventResponses, serviceController.events(request));
        verify(eventsService, atLeastOnce()).getRelevantEventResponses(users.get(0));
    }

    @Test
    public void groups() throws Exception {
        when(groupsService.getRelevantGroupResponses()).thenReturn(groupResponses);
        assertEquals(groupResponses, serviceController.groups(request));
        verify(groupsService, atLeastOnce()).getRelevantGroupResponses();
    }

    @Test
    public void getNextGroupId() throws Exception {
        Long val = 5L;
        when(groupsService.getNextGroupId()).thenReturn(val);
        assertEquals(val, serviceController.getNextGroupId());
        verify(groupsService, atLeastOnce()).getNextGroupId();
    }

    @Test
    public void myEvents() throws Exception {
        session.setAttribute("user", users.get(0));
        Set<EventResponse> userEventResponses = new HashSet<>();
        userEventResponses.add(eventResponses.get(0));
        userEventResponses.add(eventResponses.get(1));
        when(eventsService.getUserRelevantEventResponses(any(User.class))).thenReturn(userEventResponses);
        when(usersService.initEvents(any(User.class))).thenReturn(users.get(0));
        assertEquals(userEventResponses, serviceController.myEvents(request));
        verify(eventsService, atLeastOnce()).getUserRelevantEventResponses(users.get(0));
    }

    /*@Test
    public void event() throws Exception {
        when(eventsService.initParticipants(any(Event.class))).thenReturn(events.get(0));
        assertEquals(events.get(0), serviceController.event(events.get(0)));
    }*/

    @Test
    public void getSessionUser() throws Exception {
        session.setAttribute("user", users.get(1));
        assertEquals(users.get(1), serviceController.getSessionUser(request));
    }

    @Test
    public void getNextEventId() throws Exception {
        Long val = 5L;
        when(eventsService.getNextEventId()).thenReturn(val);
        assertEquals(val, serviceController.getNextEventId());
        verify(eventsService, atLeastOnce()).getNextEventId();
    }

    @Test
    public void addEvent() throws Exception {

    }

    @Test
    public void deleteEvent() throws Exception {

    }

    @Test
    public void updateEvent() throws Exception {

    }

    @Test
    public void deleteGroup() throws Exception {

    }

    @Test
    public void updateGroup() throws Exception {

    }

    @Test
    public void group() throws Exception {

    }

    @Test
    public void addGroup() throws Exception {

    }

    @Test
    public void groupEvents() throws Exception {

    }

    @Test
    public void assignUserToGroup() throws Exception {

    }

    @Test
    public void unassignUserFromGroup() throws Exception {

    }

    @Test
    public void addAllUsersToGroup() throws Exception {

    }

    @Test
    public void getUserGroups() throws Exception {

    }

    @Test
    public void getAllUsers() throws Exception {

    }

    @Test
    public void myGroups() throws Exception {

    }

    @Test
    public void addUser() throws Exception {

    }

    @Test
    public void myEventsCategories() throws Exception {

    }

    @Test
    public void categories() throws Exception {

    }

    @Test
    public void currencies() throws Exception {

    }

    @Test
    public void user() throws Exception {

    }

    @Test
    public void getUserInfo() throws Exception {

    }

    @Test
    public void assignUserToEvent() throws Exception {

    }

    @Test
    public void unassignGroupFromUser() throws Exception {

    }

    @Test
    public void archivedEvents() throws Exception {

    }

    @Test
    public void archivedUsersEvents() throws Exception {

    }

    @Test
    public void archivedEventsCategories() throws Exception {

    }

    @Test
    public void archivedUsersEventsCategories() throws Exception {

    }

    public void initEvents() {
        for(int i = 1; i <= 3; i++) {
            Event e = new Event();
            e.setId(i);
            e.setName("event"+i);
            events.add(e);
        }
    }

    public void initUsers() {
        for(int i = 1; i <= 3; i++) {
            User user = new User();
            user.setId(i);
            user.setFirstName("name"+i);
            user.setLastName("surname"+i);
            users.add(user);
        }
    }

    public void initEventResponses() {
        for(int i = 1; i <= 3; i++) {
            EventResponse eventResponse = new EventResponse();
            eventResponse.setId(i);
            eventResponse.setName("event"+i);
            eventResponses.add(eventResponse);
        }
    }

    public void initGroupResponses() {
        for(int i = 1; i <= 3; i++) {
            GroupResponse groupResponse = new GroupResponse();
            groupResponse.setId(i);
            groupResponse.setName("group"+i);
            groupResponses.add(groupResponse);
        }
    }

    public void initRequests() {
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session);
    }

    public void initConnectionsBetweenModels() {
        events.get(0).setOwner(users.get(0));
        events.get(1).setOwner(users.get(0));
        events.get(2).setOwner(users.get(1));

        eventResponses.get(0).setOwner(users.get(0));
        eventResponses.get(1).setOwner(users.get(0));
        eventResponses.get(2).setOwner(users.get(1));

        Set<Event> eventsSet = new HashSet<Event>();
        eventsSet.add(events.get(0));
        eventsSet.add(events.get(1));
        users.get(0).setEvents(eventsSet);
        eventsSet.clear();
        eventsSet.add(events.get(2));
        users.get(1).setEvents(eventsSet);
    }

}