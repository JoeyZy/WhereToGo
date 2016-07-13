package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.repositories.UsersRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by maks on 05.07.16.
 */
public class UsersServiceImplTest {

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    UsersServiceImpl usersService;

    @Spy
    List<User> users = new ArrayList<User>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        users = getUserList();
    }

    @org.junit.Test
    public void add() throws Exception {
        doNothing().when(usersRepository).add(any(User.class));
        usersService.add(any(User.class));
        verify(usersRepository, atLeastOnce()).add(any(User.class));
    }

    @org.junit.Test
    public void findAll() throws Exception {
        when(usersRepository.findAll()).thenReturn(users);
        Assert.assertEquals(usersService.findAll(), users);
    }

    @org.junit.Test
    public void findById() throws Exception {
        User user = users.get(0);
        when(usersRepository.findById(anyInt())).thenReturn(user);
        Assert.assertEquals(usersService.findById(user.getId()), user);
    }

    @org.junit.Test
    public void findByIdWithNullObject() throws Exception {
        User user = users.get(0);
        when(usersRepository.findById(anyInt())).thenReturn(null);
        Assert.assertEquals(usersService.findById(user.getId()), null);
    }

    @org.junit.Test
    public void findByEmail() throws Exception {
        User user = users.get(0);
        when(usersRepository.findByEmail(anyString())).thenReturn(user);
        Assert.assertEquals(usersService.findByEmail(user.getEmail()), user);
    }

    @org.junit.Test
    public void findByEmailWithNullObject() throws Exception {
        User user = users.get(0);
        when(usersRepository.findByEmail(anyString())).thenReturn(null);
        Assert.assertEquals(usersService.findByEmail(user.getEmail()), null);
    }

    @org.junit.Test
    public void update() throws Exception {
        User user = users.get(0);
        doNothing().when(usersRepository).update(any(User.class));
        usersService.update(user);
        verify(usersRepository, atLeastOnce()).update(any(User.class));
    }

    public List<User> getUserList() {
        User u1 = new User();
        u1.setId(1);
        u1.setFirstName("John");
        u1.setLastName("Smith");
        u1.setPassword("1111");
        u1.setEmail("js@gmail.com");

        User u2 = new User();
        u2.setId(2);
        u2.setFirstName("July");
        u2.setLastName("Doe");
        u2.setPassword("2222");
        u2.setEmail("jd@gmail.com");

        users.add(u1);
        users.add(u2);

        return users;
    }

}