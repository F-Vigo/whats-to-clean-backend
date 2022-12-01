package com.example.whatstocleanbackend;

import com.example.whatstocleanbackend.domain.chore.Chore;

public class Utils {

    public static Boolean sameChores(Chore chore1, Chore chore2) {
        Boolean sameRoom = chore1.getSection().getRoom().getName().equals(chore2.getSection().getRoom().getName());
        Boolean sameSection = chore1.getSection().getName().equals(chore2.getSection().getName());
        Boolean sameDescription = chore1.getDescription().equals(chore2.getDescription());
        return sameRoom && sameSection && sameDescription;
    }
}
