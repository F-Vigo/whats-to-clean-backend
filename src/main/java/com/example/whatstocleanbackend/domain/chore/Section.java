package com.example.whatstocleanbackend.domain.chore;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Section {
    String name;
    Room room;

    @JsonCreator
    public Section(@JsonProperty("name") String name, @JsonProperty("room") Room room) {
        this.name = name;
        this.room = room;
    }
}
