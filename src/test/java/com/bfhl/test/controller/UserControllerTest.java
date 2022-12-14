package com.bfhl.test.controller;

import com.bfhl.test.entities.User;
import com.bfhl.test.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Arrays;

public class UserControllerTest {
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void first() throws JsonProcessingException {

        UserService service = Mockito.mock(UserService.class);
        UserController controller = new UserController(service);
        when(service.getAll()).thenReturn(Arrays.asList());
        String greet = service.function();
        List<?> list = service.getAll();
        assertEquals(0, list.size());
    }
    @Test
    public void TestForUserPosted() throws JsonProcessingException {
        UserService service = Mockito.mock(UserService.class);
        UserController controller = new UserController(service);
        User dummy = new User();
        dummy.setPassword("abed");
        dummy.setUsername(null);
        dummy.setEmail("dummy@dummy");
        when(service.addUser(dummy)).thenReturn("Username required");
        assertEquals("Username required",service.addUser(dummy));
    }
    @Test
    public void truee(){
        assertEquals(true,true);
    }


}
