package com.groclist.groclistapp.repository;

import com.groclist.groclistapp.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    Recipe findByName(String name);
    List<Recipe> findAll();
}
