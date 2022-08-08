package com.bfhl.test.service;

import com.bfhl.test.entities.User;
import com.bfhl.test.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @Test
    void testGetAll_ReturnsAllRecords() {
        // arrange
        UserService service = new UserService(repository);
        List<User> list = new ArrayList<>();
        lenient().when(repository.findAll()).thenReturn(list);

        // act & assert
        assertThrows(RuntimeException.class, service::getAll);
    }
    @Test
    void testAddUser_ReturnsStringAfterNotFindingTheUserName(){
        UserService service = new UserService(repository);
        User dummy  = new User();
        dummy.setEmail("Dummy@dummy.com");
        dummy.setPassword("dummy");
        dummy.setUsername(null);
//        lenient().when(repository.save(dummy)).thenReturn(dummy);
        assertEquals("Username Required",service.addUser(dummy));
    }
    @Test
    void testAddUser_ReturnsStringAfterNotFindingTheEmail(){
        UserService service = new UserService(repository);
        User dummy  = new User();
        dummy.setEmail(null);
        dummy.setPassword("dummy");
        dummy.setUsername("Dummy");
        assertEquals("Email Required",service.addUser(dummy));
    }
    @Test
    void testAddUser_ReturnsStringAfterNotFindingThePassword(){
        UserService service = new UserService(repository);
        User dummy  = new User();
        dummy.setEmail("dummy@dummy.com");
        dummy.setPassword(null);
        dummy.setUsername("Dummy");
        assertEquals("Password Required",service.addUser(dummy));
    }
    @Test
    void testAddUser_ReturnsStringAfterSaving(){
        UserService service = new UserService(repository);
        User dummy  = new User();
        dummy.setEmail("dummy@dummy.com");
        dummy.setPassword("dummy");
        dummy.setUsername("Dummy");
        when(repository.save(dummy)).thenReturn(dummy);
        assertEquals("User posted successfully!",service.addUser(dummy));
    }
    
}