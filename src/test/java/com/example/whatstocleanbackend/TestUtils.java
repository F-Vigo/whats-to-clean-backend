package com.example.whatstocleanbackend;

import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.domain.chore.Room;
import com.example.whatstocleanbackend.domain.chore.Section;
import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;

public class TestUtils {

    public static final Room ROOM = new Room("room");
    public static final Section SECTION = new Section("section", ROOM);
    public static final String DESCRIPTION = "description";
    public static final Periodicity PERIODICITY = Periodicity.DAILY;
    public static final Chore CHORE = new Chore(SECTION, DESCRIPTION, PERIODICITY);

    public static final Integer INDEX = 1;
}
