package com.example.whatstocleanbackend.domain.chore;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    String name;

    @JsonCreator
    public Room(@JsonProperty("name") String name) {
        this.name = name;
    }
}
