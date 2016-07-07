package com.luxoft.wheretogo.services;

import static org.junit.Assert.*;

import com.luxoft.wheretogo.models.*;
import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.repositories.EventsRepository;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.stubbing.OngoingStubbing;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by serhii on 05.07.16.
 */
public class EventsServiceImplTest {
    @Mock
    EventsRepository eventsRepository;

    @Mock
    CategoriesService categoriesService;

    @InjectMocks
    EventsServiceImpl eventsService;

    @Spy
    List<Event> events = new ArrayList<Event>();


    private List<Category> lc = new ArrayList<Category>();
    private Date dateStart = new Date();
    private Date dateFinish = new Date();
    private User user = new User();

    private List<Category> lc1 = new ArrayList<Category>();
    private Date dateStart1 = new Date();
    private Date dateFinish1 = new Date();
    private User user1 = new User();

    @Before
    public void setUp() throws Exception {
        user.setFirstName("vasya");
        user.setLastName("pupkin");
        MockitoAnnotations.initMocks(this);
        events = getEventList();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {
        doNothing().when(eventsRepository).add(any(Event.class));
        eventsService.add(any(Event.class));
        verify(eventsRepository, atLeastOnce()).add(any(Event.class));
    }

    @Test
    public void update() throws Exception {
        Event e1 = events.get(0);
        when(eventsRepository.findById(anyInt())).thenReturn(e1);
        eventsService.update(e1);
        verify(eventsRepository, atLeastOnce()).findById(anyInt());

    }

    @Test
    public void findAll() throws Exception {
        when(eventsRepository.findAll()).thenReturn(events);
        Assert.assertEquals(eventsService.findAll(), events);
    }

    @Test
    public void findByPeriod() throws Exception {
        LocalDateTime start = any(LocalDateTime.class);
        LocalDateTime end = any(LocalDateTime.class);
        when(eventsRepository.findByPeriod(start, end)).thenReturn(events);
        Assert.assertEquals(eventsService.findByPeriod(start,end), events);
    }

    @Test
    public void findById() throws Exception {
        Event e1 = events.get(0);
        when(eventsRepository.findById(anyInt())).thenReturn(e1);
        Assert.assertEquals(eventsService.findById(e1.getId()), e1);
    }

    @Test
    public void findByName() throws Exception {
        Event e1 = events.get(0);
        when(eventsRepository.findByName(anyString())).thenReturn(e1);
        Assert.assertEquals(eventsService.findByName(e1.getName()), e1);
    }

    @Test
    public void getEventResponses() throws Exception {

        List<EventResponse> list = new ArrayList<>();
        list.add(getEventResponseList().get(0));
        List<Event> copyevents = new ArrayList<>();
        copyevents.add(events.get(0));
        when(eventsRepository.findAll()).thenReturn(copyevents);
        List<EventResponse> expList = eventsService.getEventResponses();
        Assert.assertEquals(expList, list);
    }

    @Test
    public void getUserRelevantEventResponses() throws Exception {
//        List<EventResponse> list = getEventResponseList();
//        List<Event> copyevents = new ArrayList<>();
//        copyevents.add(events.get(0));
//        when(eventsRepository.findByOwner()).thenReturn(copyevents);
//
//        List<EventResponse> expList = eventsService.getEventResponses();
//        Assert.assertEquals(expList, list);
    }

    @Test
    public void getRelevantEventResponses() throws Exception {
//        List<EventResponse> list = getEventResponseList();
//        when(eventsRepository.findAll()).thenReturn(events);
//        List<EventResponse> expList = eventsService.getEventResponses().;
//        Assert.assertEquals(expList, list);
    }

    @Test
    public void getNextEventId() throws Exception {

    }

    @Test
    public void getUserEventsCounterByCategories() throws Exception {
        User userTest = initUser();
        Set<CategoryResponse> expectedResult = new HashSet<>();
        List<Category> categoriesTest = getCategoryList();
        List<Event> userEventsAsList = new ArrayList<>(userTest.getEvents());

        expectedResult.add(new CategoryResponse(categoriesTest.get(1).getId(),categoriesTest.get(1).getName(),1));

        when(eventsRepository.findByOwner(userTest)).thenReturn(userEventsAsList);
        when(categoriesService.countEventsByCategories(any(List.class))).thenReturn(new ArrayList<>(expectedResult));

        Assert.assertEquals(expectedResult,eventsService.getUserEventsCounterByCategories(userTest));

    }

    @Test
    public void getEventsCounterByCategories() throws Exception {
        User userTest = initUser();
        List<Event> userEventsAsList = new ArrayList<>(userTest.getEvents());
        List<CategoryResponse> expectedResult = new ArrayList<>();
        List<Category> categoriesTest = getCategoryList();

        expectedResult.add(new CategoryResponse(categoriesTest.get(1).getId(),categoriesTest.get(1).getName(),1));

        when(categoriesService.countEventsByCategories(any(List.class))).thenReturn(new ArrayList<>(expectedResult));

        Assert.assertEquals(expectedResult,eventsService.getEventsCounterByCategories());
    }

    @Test
    public void getArchivedEventsResponse() throws Exception {

    }

    @Test
    public void getArchivedUsersEventsResponse() throws Exception {

    }

    @Test
    public void getArchivedEventsCounterByCategories() throws Exception {
        User userTest = new User();
        List<Category> categoriesTest = getCategoryList();
        List<CategoryResponse> expectedResult = new ArrayList<>();
        List<Event> eventsToTest = new ArrayList<>();
        List<Category> listCat1 = new ArrayList<>();
        List<Category> listCat2 = new ArrayList<>();
        ArchiveServiceRequest request = new ArchiveServiceRequest();

        listCat1.add(categoriesTest.get(0));
        listCat2.add(categoriesTest.get(1));

        expectedResult.add(new CategoryResponse(categoriesTest.get(0).getId(),categoriesTest.get(0).getName(),1));

        when(categoriesService.countEventsByCategories(eventsToTest)).thenReturn(expectedResult);
        Assert.assertEquals(eventsService.getArchivedEventsCounterByCategories(request,userTest),expectedResult);
    }

    @Test
    public void getArchivedUsersEventsCounterByCategories() throws Exception {

    }
    public List<Event> getEventList(){
        Event e1 = new Event();
        e1.setCost(100);
        e1.setCurrency(new Currency());
        e1.setStartDateTime(dateStart);
        e1.setDeleted(0);
        e1.setDescription("blaaaaaaaaaa");
        e1.setEndDateTime(dateFinish);
        e1.setId(1);
        e1.setCategories(lc);
        e1.setLocation("blaaaq");
        e1.setName("bluka");
        e1.setOwner(user);
        e1.setPicture("blaaa");
        events.add(e1);

        Event e2 = new Event();
        e2.setCost(100);
        e2.setCurrency(new Currency());
        e2.setStartDateTime(dateStart1);
        e2.setDeleted(0);
        e2.setDescription("blyaaaaaaaaaa");
        e2.setEndDateTime(dateFinish1);
        e2.setId(1);
        e2.setCategories(lc1);
        e2.setLocation("blyaaaq");
        e2.setName("blyuka");
        e2.setOwner(user1);
        e2.setPicture("blyaaa");
        events.add(e2);

        return events;

    }
    public List<EventResponse> getEventResponseList(){
        List<EventResponse> eventResponses = new ArrayList<>();
        eventResponses.add(new EventResponse(1, "bluka", lc,
                "vasya pupkin",
                dateStart,
                dateFinish,
                0,//
                "blaaa",
                "blaaaq",
                false));
        return eventResponses;
    }

    public User initUser(){
        List<Category> categoriesTest = getCategoryList();
        Event e1 = new Event();
        Event e2 = new Event();
        User userTest = new User();
        Set<Event> eventsTestSet = new HashSet<>();

        List<Category> listCat1 = new ArrayList<>();
        List<Category> listCat2 = new ArrayList<>();

        listCat1.add(categoriesTest.get(0));
        listCat2.add(categoriesTest.get(1));

        e1.setCategories(listCat1);
        e1.setId(0);
        e1.setDeleted(0);
        e1.setStartDateTime(new Date(1356991200000L));
        eventsTestSet.add(e1);

        e2.setCategories(listCat2);
        e2.setDeleted(0);
        e2.setId(1);
        e2.setStartDateTime(new Date(1470614400000L));
        eventsTestSet.add(e2);

        userTest.setEvents(eventsTestSet);

        return userTest;
    }

    public List<Category> getCategoryList() {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Category1");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Category2");

        categories.add(category1);
        categories.add(category2);

        return categories;
    }

}