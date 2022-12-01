package com.example.whatstocleanbackend.service;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.domain.UserIndex;
import com.example.whatstocleanbackend.repository.UserIndexRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.example.whatstocleanbackend.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class UserIndexServiceTest {

    @Mock
    private static UserIndex USER_INDEX;

    @MockBean
    private UserIndexRepository userIndexRepository;

    @MockBean
    private ChoreService choreService;

    @Autowired
    private UserIndexService service;

    @Test
    public void logIn() {

        when(USER_INDEX.getIndex()).thenReturn(INDEX);
        when(userIndexRepository.save(USER_INDEX)).thenReturn(USER_INDEX);
        when(userIndexRepository.findAll()).thenReturn(List.of(USER_INDEX));

        Integer actual = service.logIn();

        assertEquals(USER_INDEX.getIndex(), actual);
        verify(userIndexRepository, times(1)).save(any(UserIndex.class));
        verify(userIndexRepository, times(1)).findAll();
        verify(choreService, times(1)).createFile(USER_INDEX.getIndex());
    }

    @Test
    public void logOut() {
        userIndexRepository.deleteById(INDEX);
        verify(userIndexRepository, times(1)).deleteById(INDEX);
    }

}
