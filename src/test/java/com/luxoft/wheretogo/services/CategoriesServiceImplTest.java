package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.json.CategoryResponse;
import com.luxoft.wheretogo.repositories.CategoriesRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by eleonora on 05.07.16.
 */
public class CategoriesServiceImplTest {

    @Mock
    CategoriesRepository categoriesRepository;

    @InjectMocks
    CategoriesServiceImpl categoriesService;

    @Spy
     List<Category> categories = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categories = getCategoryList();
    }

    @Test
    public void add() throws Exception {
        doNothing().when(categoriesRepository).add(any(Category.class));
        categoriesService.add(any(Category.class));
        verify(categoriesRepository,atLeastOnce()).add(any(Category.class));
    }

    @Test
    public void findAll() throws Exception {
        when(categoriesRepository.findAll()).thenReturn(categories);
        Assert.assertEquals(categoriesService.findAll(),categories);
    }

    @Test
    public void findById() throws Exception {
        Category category = categories.get(0);
        when(categoriesRepository.findById(anyInt())).thenReturn(category);
        Assert.assertEquals(categoriesRepository.findById(category.getId()),category);
    }

    @Test
    public void countEventsByCategories() throws Exception {
        List<Event> testEvents = initializeEventsAndRep();
        when(categoriesRepository.findAll()).thenReturn(categories);

        List<CategoryResponse> expectedResult = new ArrayList<>();
        expectedResult.add(new CategoryResponse(categories.get(0).getId(),categories.get(0).getName(),1));
        expectedResult.add(new CategoryResponse(categories.get(1).getId(),categories.get(1).getName(),3));
        expectedResult.add(new CategoryResponse(categories.get(2).getId(),categories.get(2).getName(),2));

        Assert.assertEquals(categoriesService.countEventsByCategories(testEvents),expectedResult);
    }

    public List<Category> getCategoryList(){
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Category1");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Category2");

        Category category3 = new Category();
        category3.setId(3);
        category3.setName("Category3");

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        return categories;
    }

    public List<Event> initializeEventsAndRep(){
        List<Event> events = new ArrayList<>();

        Event ev1 = new Event();
        Event ev2 = new Event();
        Event ev3 = new Event();
        Event ev4 = new Event();
        Event ev5 = new Event();
        Event ev6 = new Event();

        List<Category> listCat1 = new ArrayList<>();
        List<Category> listCat2 = new ArrayList<>();
        List<Category> listCat3 = new ArrayList<>();

        listCat1.add(categories.get(0));
        listCat2.add(categories.get(1));
        listCat3.add(categories.get(2));

        ev1.setCategories(listCat1);

        ev2.setCategories(listCat2);
        ev3.setCategories(listCat2);
        ev4.setCategories(listCat2);

        ev5.setCategories(listCat3);
        ev6.setCategories(listCat3);

        events.add(ev1);
        events.add(ev2);
        events.add(ev3);
        events.add(ev4);
        events.add(ev5);
        events.add(ev6);

        categoriesRepository.add(categories.get(0));
        categoriesRepository.add(categories.get(1));
        categoriesRepository.add(categories.get(2));

        return events;
    }

}