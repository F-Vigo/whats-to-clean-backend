package com.example.whatstocleanbackend.repository;

import com.example.whatstocleanbackend.domain.UserIndex;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIndexRepository extends CrudRepository<UserIndex, Integer> {}
