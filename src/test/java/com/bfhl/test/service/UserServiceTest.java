package com.bfhl.test.service;

import com.bfhl.test.entities.User;
import com.bfhl.test.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        when(repository.findAll()).thenReturn(list);

        // act & assert
        assertThrows(RuntimeException.class, service::getAll);
    }
}