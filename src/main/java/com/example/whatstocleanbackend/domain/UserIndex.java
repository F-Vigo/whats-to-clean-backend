package com.example.whatstocleanbackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_index")
public class UserIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "index")
    Integer index;
}
