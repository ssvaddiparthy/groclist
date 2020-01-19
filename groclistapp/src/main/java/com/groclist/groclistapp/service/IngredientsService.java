package com.groclist.groclistapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groclist.groclistapp.exceptions.RecipeNotFoundException;
import com.groclist.groclistapp.model.Recipe;
import com.groclist.groclistapp.repository.RecipeRepository;
import com.groclist.groclistcommons.GrocListCommons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class IngredientsService {

    @Autowired
    RecipeRepository rr;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private GrocListCommons grocListCommons = new GrocListCommons();

    public IngredientsService() {
    }

    private String mergeIngredients(List<Recipe> recipes) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, HashMap<String, String>> ingredients = new HashMap<>();
        for (Recipe recipe : recipes) {
            logger.info("Getting the ingredients for recipe {}", recipe.getName());
            try {
                HashMap<String, HashMap<String, String>> curIngredients = mapper.readValue(recipe.getIngredients(), new TypeReference<HashMap>() {
                });
                for (String ingredientName : curIngredients.keySet()) {
                    if (ingredients.containsKey(ingredientName)) {
                        grocListCommons.mergeIngredient(ingredients, ingredientName, curIngredients.get(ingredientName));
                    } else {
                        ingredients.put(ingredientName, curIngredients.get(ingredientName));
                    }
                }
            } catch (JsonProcessingException exp) {
                exp.printStackTrace();
            }

        }
        try {
            List<HashMap<String, String>> ingredientsList = new ArrayList<>();
            for (String ing : ingredients.keySet()) {
                ingredientsList.add(new HashMap<>() {{
                    put("name", ing);
                    put("unit", ingredients.get(ing).get("unit"));
                    put("amount", ingredients.get(ing).get("amount"));
                }});
            }
            return mapper.writeValueAsString(ingredientsList);
        } catch (JsonProcessingException exp) {
            logger.error("Error while parsing the merged list of ingredients into a JSON string");
            logger.error(exp.toString());
        }
        return "{}";
    }

    public String getIngredientList(String recipeNames) throws RecipeNotFoundException {
        List<String> recipeNameList = Arrays.asList(recipeNames.split(","));
        List<Recipe> recipes = new ArrayList<>();

        for (String recipe : recipeNameList) {
            Recipe curRecipe = rr.findByName(recipe);

            if (curRecipe == null) {
                throw new RecipeNotFoundException(String.format("No recipe found with name", recipe));
            }
            recipes.add(curRecipe);
        }
        return mergeIngredients(recipes);
    }

}
