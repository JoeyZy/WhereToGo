package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.*;
import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.models.json.EventResponse;
import com.luxoft.wheretogo.repositories.EventIdGeneratorRepository;
import com.luxoft.wheretogo.repositories.EventsRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by maks on 05.07.16.
 */
public class EventsServiceImplTest {

    @Mock
    EventsRepository eventsRepository;

    @Mock
    EventIdGeneratorRepository idGenerator;

    @Mock
    CategoriesService categoriesService;

    @InjectMocks
    EventsServiceImpl eventsService;

    @Spy
    List<Event> events = new ArrayList<Event>();

    private final long ONE_DAY_IN_MILISECONDS = 86400000L;
    private final long ONE_WEEK_IN_MILISECONDS = 604800000L;

    // time in the future
    private Date dateStart1 = new Date(new Date().getTime() + ONE_WEEK_IN_MILISECONDS);
    private Date dateFinish1 = new Date(new Date().getTime() + ONE_WEEK_IN_MILISECONDS);

    // time in the past
    private Date dateStart2 = new Date(new Date().getTime() - ONE_WEEK_IN_MILISECONDS);
    private Date dateFinish2 = new Date(new Date().getTime() - ONE_WEEK_IN_MILISECONDS);

    private List<Category> lc1 = new ArrayList<Category>();
    private List<Category> lc2 = new ArrayList<Category>();

    private User user1 = new User();
    private User user2 = new User();
    private User user3 = new User();

    private SimpleDateFormat format =new SimpleDateFormat("dd-MM-yy");
    private ArchiveServiceRequest request = new ArchiveServiceRequest();

    @Before
    public void setUp() throws Exception {
        events = getEventList();

        user1.setFirstName("vasya");
        user1.setLastName("pupkin");
        ArrayList<Event> copylist = new ArrayList<>();
        copylist.add(events.get(0));

        user2.setEvents(new HashSet<>(copylist));

        user3.setFirstName("user3");
        user3.setLastName("user3");
        copylist = new ArrayList<>();
        copylist.add(events.get(1));
        user3.setEvents(new HashSet<>(copylist));
        request.setSearchFrom(format.format(new Date(new Date().getTime() - 2*ONE_WEEK_IN_MILISECONDS)));
        request.setSearchTo(format.format(new Date(new Date().getTime() + ONE_DAY_IN_MILISECONDS)));

        MockitoAnnotations.initMocks(this);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {
        boolean expectedResult = false;
        boolean actualResult = eventsService.add(events.get(0));
        Assert.assertEquals(expectedResult, actualResult);
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

        List<EventResponse> expectedResult = new ArrayList<>();
        expectedResult.add(getEventResponseList().get(1));
        List<Event> localEvents = new ArrayList<>();
        localEvents.add(events.get(0));
        when(eventsRepository.findAll()).thenReturn(localEvents);
        List<EventResponse> actualResult = eventsService.getEventResponses();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getUserRelevantEventResponses() throws Exception {
        List<EventResponse> list = new ArrayList<>();
        list.add(getEventResponseList().get(0));
        Set<EventResponse> set = new HashSet(list);
        List<Event> copyevents = new ArrayList<>();
        copyevents.add(events.get(0));
        when(eventsRepository.findByOwner(user2)).thenReturn(copyevents);
        Set<EventResponse> expList = eventsService.getUserRelevantEventResponses(user2);
        Assert.assertEquals(expList, set);
    }

    @Test
    public void getRelevantEventResponses() throws Exception {

        List<EventResponse> list = new ArrayList<>();
        list.add(getEventResponseList().get(0));
        List<Event> copyevents = new ArrayList<>();
        copyevents.add(events.get(0));
        when(eventsRepository.findAll()).thenReturn(copyevents);
        List<EventResponse> expList = eventsService.getRelevantEventResponses(user2);
        Assert.assertEquals(expList, list);
    }

    @Test
    public void getNextEventId() throws Exception {
        Long one = new Long(1);
        when(idGenerator.getNextId()).thenReturn(1l);
        Assert.assertEquals(eventsService.getNextEventId(), one);
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

        Assert.assertEquals(expectedResult,eventsService.getEventsCounterByCategories(userTest));
    }

    @Test
    public void getArchivedEventsResponse() throws Exception {
        List<EventResponse> expectedResult = new ArrayList<>();
        expectedResult.add(getEventResponseList().get(2));

        when(eventsRepository.findAll()).thenReturn(events);
        List<EventResponse> actualResult = eventsService.getArchivedEventsResponse(request, user3);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getArchivedUsersEventsResponse() throws Exception {
        List<EventResponse> list = new ArrayList<>();
        list.add(getEventResponseList().get(2));
        List<Event> copyevents = new ArrayList<>();
        copyevents.add(events.get(1));
        when(eventsRepository.findByOwner(user3)).thenReturn(copyevents);
        List<EventResponse> expList = eventsService.getArchivedUsersEventsResponse(request, user3);
        Set<EventResponse> actualResult = new HashSet<EventResponse>(expList);
        Set<EventResponse> expectedResult = new HashSet<EventResponse>(list);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getArchivedEventsCounterByCategories() throws Exception {
        User userTest = new User();
        List<Category> categoriesTest = getCategoryList();
        List<CategoryResponse> expectedResult = new ArrayList<>();
        ArchiveServiceRequest request = new ArchiveServiceRequest();

        expectedResult.add(new CategoryResponse(categoriesTest.get(0).getId(),categoriesTest.get(0).getName(),1));

        when(categoriesService.countEventsByCategories(any(List.class))).thenReturn(expectedResult);
        Assert.assertEquals(eventsService.getArchivedEventsCounterByCategories(request,userTest),expectedResult);
    }

    @Test
    public void getArchivedUsersEventsCounterByCategories() throws Exception {
        User userTest = new User();
        List<Category> categoriesTest = getCategoryList();
        List<CategoryResponse> expectedResult = new ArrayList<>();
        ArchiveServiceRequest request = new ArchiveServiceRequest();

        expectedResult.add(new CategoryResponse(categoriesTest.get(0).getId(),categoriesTest.get(0).getName(),1));

        when(categoriesService.countEventsByCategories(any(List.class))).thenReturn(expectedResult);
        Assert.assertEquals(eventsService.getArchivedUsersEventsCounterByCategories(request,userTest),expectedResult);
    }

    public List<Event> getEventList(){
        Event e1 = new Event();
        e1.setCost(100);
        e1.setCurrency(new Currency());
        e1.setStartDateTime(dateStart1);
        e1.setDeleted(false);
        e1.setDescription("blaaaaaaaaaa");
        e1.setEndDateTime(dateFinish1);
        e1.setId(1);
        e1.setCategories(lc1);
        e1.setLocation("blaaaq");
        e1.setName("bluka");
        e1.setOwner(user1);
        e1.setPicture("blaaa");
        Set<User> users = new HashSet<>();
        users.add(user2);
        e1.setParticipants(users);

        events.add(e1);

        Event e2 = new Event();
        e2.setCost(100);
        e2.setCurrency(new Currency());
        e2.setStartDateTime(dateStart2);
        e2.setDeleted(false);
        e2.setDescription("blyaaaaaaaaaa");
        e2.setEndDateTime(dateFinish2);
        e2.setId(2);
        e2.setCategories(lc2);
        e2.setLocation("blyaaaq");
        e2.setName("blyuka");
        e2.setOwner(user3);
        e2.setPicture("blyaaa");
        users = new HashSet<>();
        users.add(user2);
        e2.setParticipants(users);

        events.add(e2);

        return events;

    }
    public List<EventResponse> getEventResponseList(){
        List<EventResponse> eventResponses = new ArrayList<>();
        eventResponses.add(new EventResponse(1, "bluka", lc1,
                "vasya pupkin",
                "",
                dateStart1,
                dateFinish1,
                false,
                "blaaa",
                "blaaaq",
                true));

        eventResponses.add(new EventResponse(1, "bluka", lc1,
                "vasya pupkin",
                "",
                dateStart1,
                dateFinish1,
                false,
                "blaaa",
                "blaaaq",
                false));

        eventResponses.add(new EventResponse(2, "blyuka", lc1,
                "user3 user3",
                "",
                dateStart2,
                dateFinish2,
                false,
                "blyaaa",
                "blyaaaq",
                true));

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
        e1.setDeleted(false);
        e1.setStartDateTime(new Date(1356991200000L));
        eventsTestSet.add(e1);

        e2.setCategories(listCat2);
        e2.setDeleted(false);
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