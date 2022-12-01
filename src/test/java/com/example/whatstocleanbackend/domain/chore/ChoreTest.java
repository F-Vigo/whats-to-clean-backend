package com.example.whatstocleanbackend.domain.chore;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.domain.chore.Room;
import com.example.whatstocleanbackend.domain.chore.Section;
import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class ChoreTest {

    private final static Room ROOM = new Room("roomB");
    private final static Section SECTION = new Section("sectionB", ROOM);
    private final static String DESCRIPTION = "descriptionB";
    private final static Periodicity PERIODICITY = Periodicity.DAILY;
    private final static Chore CHORE = new Chore(SECTION, DESCRIPTION, PERIODICITY);

    // SAME
    @Test
    public void whenCompareTo_withEqualChores_thenReturns0() {
        Chore newChore = new Chore(SECTION, DESCRIPTION, PERIODICITY);
        assertResult(0, newChore);
    }


    // DESCRIPTION

    @Test
    public void whenCompareTo_withDescriptionIsLower_thenReturnsMinus1() {
        Chore newChore = new Chore(SECTION, "descriptionC", PERIODICITY);
        assertResult(-1, newChore);
    }

    @Test
    public void whenCompareTo_withDescriptionIsGreater_thenReturnsPlus1() {
        Chore newChore = new Chore(SECTION, "descriptionA", PERIODICITY);
        assertResult(+1, newChore);
    }


    // SECTION

    @Test
    public void whenCompareTo_withSectionIsLower_thenReturnsMinus1() {
        Chore newChore = new Chore(new Section("sectionC", ROOM), DESCRIPTION, PERIODICITY);
        assertResult(-1, newChore);
    }

    @Test
    public void whenCompareTo_withSectionIsGreater_thenReturnsPlus1() {
        Chore newChore = new Chore(new Section("sectionA", ROOM), DESCRIPTION, PERIODICITY);
        assertResult(+1, newChore);
    }


    // ROOM

    @Test
    public void whenCompareTo_withRoomIsLower_thenReturnsMinus1() {
        Chore newChore = new Chore(new Section("sectionB", new Room("roomC")), DESCRIPTION, PERIODICITY);
        assertResult(-1, newChore);
    }

    @Test
    public void whenCompareTo_withRoomIsGreater_thenReturnsPlus1() {
        Chore newChore = new Chore(new Section("sectionB", new Room("roomA")), DESCRIPTION, PERIODICITY);
        assertResult(+1, newChore);
    }


    // AUX

    private void assertResult(Integer expected, Chore other) {
        assertEquals(expected, CHORE.compareTo(other));
    }
}
