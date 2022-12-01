package com.example.whatstocleanbackend;

import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.domain.chore.Room;
import com.example.whatstocleanbackend.domain.chore.Section;
import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static com.example.whatstocleanbackend.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class UtilsTest {

    @Test
    public void whenSameChores_withEqualRoomAndSectionAndDescription_thenReturnsTrue() {
        Chore otherChore = new Chore(SECTION, DESCRIPTION, PERIODICITY);
        assertTrue(Utils.sameChores(CHORE, otherChore));
    }

    @Test
    public void whenSameChores_withDifferentRoom_thenReturnsFalse() {
        Chore otherChore = new Chore(new Section("section", new Room("otherRoom")), DESCRIPTION, PERIODICITY);
        assertFalse(Utils.sameChores(CHORE, otherChore));
    }

    @Test
    public void whenSameChores_withDifferentSection_thenReturnsFalse() {
        Chore otherChore = new Chore(new Section("otherSection", new Room("room")), DESCRIPTION, PERIODICITY);
        assertFalse(Utils.sameChores(CHORE, otherChore));
    }

    @Test
    public void whenSameChores_withDifferentDescription_thenReturnsFalse() {
        Chore otherChore = new Chore(SECTION, "otherDescription", PERIODICITY);
        assertFalse(Utils.sameChores(CHORE, otherChore));
    }

    @Test
    public void whenSameChores_withDifferentPeriodicity_thenReturnsTrue() {
        Chore otherChore = new Chore(SECTION, DESCRIPTION, Periodicity.TWICE_A_WEEK);
        assertTrue(Utils.sameChores(CHORE, otherChore));
    }

}
