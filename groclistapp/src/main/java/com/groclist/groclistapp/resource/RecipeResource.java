package com.groclist.groclistapp.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groclist.groclistapp.dto.GrocListResponse;
import com.groclist.groclistapp.model.Recipe;
import com.groclist.groclistapp.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipe")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeResource {

    @Autowired
    RecipeRepository recipeRepository;

    private Date date= new Date();

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @RequestMapping("/all")
    @GET
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<GrocListResponse> getAllRecipes(@RequestHeader(name = "x-request-id", required = false) String requestId){
        if (requestId == null || requestId.isEmpty()){
            logger.debug("No requestId was sent via the HTTP header 'x-request-id'.");
            requestId = UUID.randomUUID().toString();
            logger.debug("Generating a UUID for this request {}", requestId);
        }
        MDC.put("request_id", requestId);


        ObjectMapper obj = new ObjectMapper();
        List<String> recipeNames = new ArrayList<>();
        List<Recipe> recipes = recipeRepository.findAll();
        for (Recipe recipe: recipes) {
            recipeNames.add(recipe.getName());
        }
        try{
            MDC.clear();
            return new ResponseEntity<>(new GrocListResponse(new Timestamp(date.getTime()),
                    "Successfully obtained the recipe named",
                            requestId,
                            obj.writeValueAsString(recipeNames)), HttpStatus.OK);
        } catch (JsonProcessingException exp){
            logger.error("Error while parsing the merged list of ingredients into a JSON string");
            logger.error(exp.toString());
            return new ResponseEntity<>(new GrocListResponse(new Timestamp(date.getTime()),
                    exp.toString(),
                    requestId,
                    ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
