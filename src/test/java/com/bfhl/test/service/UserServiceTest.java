package com.bfhl.test.service;

import com.bfhl.test.entities.User;
import com.bfhl.test.repositories.MongoRepo;
import com.bfhl.test.repositories.RedisUserRepo;
import com.bfhl.test.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    MongoRepo repository;
    @Mock
    RedisUserRepo redisUserRepo;

    @Test
    void testGetAll_ReturnsAllRecords() {
        // arrange
        UserService service = new UserService(repository,redisUserRepo);
        List<?> list = new ArrayList<>();
        lenient().when(repository.findAll()).thenReturn(new ArrayList<>());

        // act & assert
        assertEquals(0,list.size());
    }
    @Test
    void testAddUser_ReturnsStringAfterNotFindingTheUserName() throws JsonProcessingException {
        UserService service = new UserService(repository,redisUserRepo);
        User dummy  = new User();
        dummy.setEmail("Dummy@dummy.com");
        dummy.setPassword("dummy");
        dummy.setUsername(null);
//        lenient().when(repository.save(dummy)).thenReturn(dummy);
        assertEquals("Username Required",service.addUser(dummy));
    }
    @Test
    void testAddUser_ReturnsStringAfterNotFindingTheEmail() throws JsonProcessingException {
        UserService service = new UserService(repository,redisUserRepo);
        User dummy  = new User();
        dummy.setEmail(null);
        dummy.setPassword("dummy");
        dummy.setUsername("Dummy");
        assertEquals("Email Required",service.addUser(dummy));
    }
    @Test
    void testAddUser_ReturnsStringAfterNotFindingThePassword() throws JsonProcessingException {
        UserService service = new UserService(repository,redisUserRepo);
        User dummy  = new User();
        dummy.setEmail("dummy@dummy.com");
        dummy.setPassword(null);
        dummy.setUsername("Dummy");
        assertEquals("Password Required",service.addUser(dummy));
    }
    @Test
    void testAddUser_ReturnsStringAfterSaving() throws JsonProcessingException {
        UserService service = new UserService(repository,redisUserRepo);
        User dummy  = new User("id","name","email","pass");
        assertEquals("dummy@dummy.com",redisUserRepo.save(dummy));
    }

}