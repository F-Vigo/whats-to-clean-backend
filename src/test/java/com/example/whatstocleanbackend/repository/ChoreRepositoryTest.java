package com.example.whatstocleanbackend.repository;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.domain.chore.Room;
import com.example.whatstocleanbackend.domain.chore.Section;
import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;
import com.example.whatstocleanbackend.exception.IndexException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static com.example.whatstocleanbackend.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class ChoreRepositoryTest {
    private final String CHORE_STRING = "room :: section :: description :: DAILY";

    @Autowired
    private ChoreRepository repository;

    @Test
    @Order(1)
    public void auxiliaryCreateFileAndClearMethodsWorkFine() {
        clear(); // Needed for some reason
        assertEquals(0, new File("store").listFiles().length);
        createFile();
        assertEquals(1, new File("store").listFiles().length);
        clear();
        assertEquals(0, new File("store").listFiles().length);
    }

    @Test
    public void whenCreateFile_thenFileIsCreated() {
        clear();
        assertEquals(0, new File("store").listFiles().length);
        repository.createFile(INDEX);
        assertEquals(1, new File("store").listFiles().length);
        assertTrue(new File("store/1.txt").exists());
    }

    @Test
    public void whenGetAll_withRightIndex_thenChoresAreRetrieved() {
        getAllAux(INDEX, List.of(CHORE));
    }

    @Test
    public void whenGetAll_withWrongIndex_thenChoresAreNotRetrieved() {
        assertThrows(IndexException.class, () -> getAllAux(2, List.of()));
    }

    @Test
    public void whenAdd_withRightIndex_thenChoreIsAdded() {
        assertEquals(0, repository.getAll(INDEX).size());
        repository.add(CHORE, INDEX);
        assertEquals(List.of(CHORE), repository.getAll(INDEX));
    }

    @Test
    public void whenUpdate_withRightIndex_thenChoreIsUpdated() {
        repository.add(CHORE, INDEX);
        Chore newChore = new Chore(SECTION, "newDescription", PERIODICITY);
        repository.update(newChore, ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
        assertEquals(List.of(newChore), repository.getAll(INDEX));
    }

    @Test
    public void whenDelete_withRightIndex_thenChoreIsDeleted() {
        try (PrintStream printer = new PrintStream(String.format("store/%d.txt", INDEX))) {
            printer.println(CHORE_STRING);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(List.of(CHORE), repository.getAll(INDEX));
        repository.delete(ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
        assertEquals(0, repository.getAll(INDEX).size());
    }





    @BeforeEach
    public void createFile() {
        try {
            new File(String.format("store/%d.txt", INDEX)).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void clear() {
        Arrays.stream(new File("store").listFiles()).forEach(File::delete);
    }

    private void getAllAux(Integer index, List<Chore> expected) {
        try (PrintStream printer = new PrintStream(String.format("store/%d.txt", INDEX))) {
            printer.println(CHORE_STRING);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, repository.getAll(index));
    }




}
