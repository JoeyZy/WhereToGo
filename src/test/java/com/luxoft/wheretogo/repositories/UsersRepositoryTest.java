package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.User;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
/**
 * Created by eleonora on 19.07.16.
 */
public class UsersRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    UsersRepository usersRepository;
    @Override
    protected IDataSet getDataSet() throws Exception{
        IDataSet dataset = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml"));
        return dataset;
    }
    @Test
    public void fingById(){
        Assert.assertNotNull(usersRepository.findById(1));
        Assert.assertNull(usersRepository.findById(3));
    }
    @Test
    public void findByEmail(){
        Assert.assertNotNull(usersRepository.findByEmail("123@gmail.com"));
        Assert.assertNull(usersRepository.findByEmail("12345@gmail.com"));
    }
    @Test
    public void findAll(){
        Assert.assertEquals(usersRepository.findAll().size(),2);
    }

    @Test
    public void save(){
        usersRepository.add(getSampleUser());
        Assert.assertEquals(usersRepository.findAll().size(),3);
    }

    @Test
    public void update(){
        User userToUpdate = usersRepository.findAll().get(0);
        userToUpdate.setFirstName("Updated first name");
        usersRepository.update(userToUpdate);
        Assert.assertEquals(usersRepository.findAll().get(0),userToUpdate);
    }

    public User getSampleUser(){
        User user = new User();
        user.setId(3);
        user.setActive(true);
        user.setEmail("3@gmail.com");
        user.setFirstName("mary");
        user.setLastName("cay");
        user.setPassword("33333");
        user.setRole("user");
        return user;
    }
}