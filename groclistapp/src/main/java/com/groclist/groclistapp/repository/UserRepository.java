package com.groclist.groclistapp.repository;

import com.groclist.groclistapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUname(String uName);
    List<User> findAll();
}
