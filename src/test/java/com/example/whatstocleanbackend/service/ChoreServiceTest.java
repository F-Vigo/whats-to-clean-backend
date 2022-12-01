package com.example.whatstocleanbackend.service;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.domain.chore.Room;
import com.example.whatstocleanbackend.domain.chore.Section;
import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;
import com.example.whatstocleanbackend.domain.date.Date;
import com.example.whatstocleanbackend.repository.ChoreRepository;
import com.example.whatstocleanbackend.repository.UserIndexRepository;
import com.example.whatstocleanbackend.service.ChoreService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.example.whatstocleanbackend.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class ChoreServiceTest {


    private final Chore CHORE_2 = new Chore(SECTION, DESCRIPTION, Periodicity.TWICE_A_WEEK);

    private final Date DATE = Date.createDate(2020, 11, 28); // Monday (should only match DAILY)private final Integer INDEX = 1;

    @MockBean
    private UserIndexRepository userIndexRepository;

    @MockBean
    private ChoreRepository choreRepository;

    @Autowired
    private ChoreService service;

    @Test
    public void whenGetByDate_returnChoreList() {
        when(choreRepository.getAll(INDEX)).thenReturn(List.of(CHORE, CHORE_2));
        List<Chore> actual = service.getByDate(DATE.getYear(), DATE.getMonth(), DATE.getDay(), INDEX);
        assertEquals(List.of(CHORE), actual);
    }

    @Test
    public void whenGetAll_withRightIndex_returnChoreList() {
        when(choreRepository.getAll(INDEX)).thenReturn(List.of(CHORE));
        List<Chore> actual = service.getByDate(DATE.getYear(), DATE.getMonth(), DATE.getDay(), INDEX);
        assertEquals(List.of(CHORE), actual);
    }

    @Test
    public void whenGetAll_withWrongIndex_returnEmptyList() {
        when(choreRepository.getAll(INDEX)).thenReturn(List.of(CHORE));
        List<Chore> actual = service.getByDate(DATE.getYear(), DATE.getMonth(), DATE.getDay(), 2);
        assertEquals(List.of(), actual);
    }

    @Test
    public void whenAdd_withNewChore_thenChoreIsAdded() {
        when(choreRepository.getAll(INDEX)).thenReturn(List.of(CHORE));
        service.add(CHORE_2, INDEX);
        verify(choreRepository, never()).add(CHORE_2, INDEX);
    }

    @Test
    public void whenAdd_withOldChore_thenChoreIsNotAdded() {
        when(choreRepository.getAll(INDEX)).thenReturn(List.of(CHORE));
        service.add(CHORE, INDEX);
        verify(choreRepository, never()).add(CHORE, INDEX);
    }

    @Test
    public void whenUpdate_thenRepositoryIsCalled() {
        service.update(CHORE, ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
        verify(choreRepository, times(1)).update(CHORE, ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
    }

    @Test
    public void whenDelete_thenRepositoryIsCalled() {
        service.delete(ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
        verify(choreRepository, times(1)).delete(ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
    }

    @Test
    public void whenSaveFile_thenRepositoryIsCalled() {
        byte[] byteArray = new byte[]{};
        service.saveFile(INDEX, byteArray);
        verify(choreRepository, times(1)).saveFile(INDEX, byteArray);
    }

    @Test
    public void whenCreateFile_thenRepositoryIsCalled() {
        service.createFile(INDEX);
        verify(choreRepository, times(1)).createFile(INDEX);
    }
}
