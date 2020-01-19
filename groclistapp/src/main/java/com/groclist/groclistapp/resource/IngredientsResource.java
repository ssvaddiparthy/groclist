package com.groclist.groclistapp.resource;

import com.groclist.groclistapp.dto.OngroceryResponse;
import com.groclist.groclistapp.service.IngredientsService;
import com.groclist.groclistapp.exceptions.RecipeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.util.UUID;
import java.util.Date;
import java.sql.Timestamp;


@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "http://localhost:3000")
public class IngredientsResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private Date date= new Date();

    @Autowired
    private IngredientsService ingredientsService;

    @GET
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<OngroceryResponse> getIngredients(@RequestParam String recipes, @RequestHeader(name = "x-request-id", required = false) String requestId){
        if (requestId == null || requestId.isEmpty()){
            logger.debug("No requestId was sent via the HTTP header 'x-request-id'.");
            requestId = UUID.randomUUID().toString();
            logger.debug("Generating a UUID for this request {}", requestId);
        }
        MDC.put("request_id", requestId);

        if (recipes.isEmpty()){
            return new ResponseEntity<>(
                    new OngroceryResponse(
                            new Timestamp(date.getTime()),
                            "Recipe List cannot be empty",
                            requestId,
                            ""), HttpStatus.BAD_REQUEST);
        }

        try{
            String ingredientList = ingredientsService.getIngredientList(recipes);
            MDC.clear();
            return new ResponseEntity<>(
                    new OngroceryResponse(new Timestamp(date.getTime()),
                            "Successfully obtained Ingredients",
                            requestId,
                            ingredientList), HttpStatus.OK);
        } catch (RecipeNotFoundException exception){
            MDC.clear();
            return new ResponseEntity<>(
                    new OngroceryResponse(new Timestamp(date.getTime()),
                            exception.getMessage(),
                            requestId,
                            ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
