package com.example.whatstocleanbackend.domain.chore;

import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chore implements Comparable<Chore> {
    Section section;
    String description;
    Periodicity periodicity;

    @JsonCreator
    public Chore(@JsonProperty("section") Section section, @JsonProperty("description") String description, @JsonProperty("periodicity") Periodicity periodicity) {
        this.section = section;
        this.description = description;
        this.periodicity = periodicity;
    }

    @Override
    public int compareTo(Chore that) {
        if (section.getRoom().getName() != that.getSection().getRoom().getName())
            return section.getRoom().getName().compareTo(that.getSection().getRoom().getName());
        if (section.getName() != that.getSection().getName())
            return section.getName().compareTo(that.getSection().getName());
        return description.compareTo(that.description);
    }
}
