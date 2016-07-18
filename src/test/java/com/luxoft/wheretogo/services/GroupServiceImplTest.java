package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.repositories.UsersRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by fg on 7/16/2016.
 */
public class GroupServiceImplTest {

    @Autowired
    UsersRepository usersRepository;

    @Test
    public void Test() {
        List<User> list = usersRepository.getNotParticipants(1);
        list.forEach(user -> System.out.println(user.toString()));
    }
}
