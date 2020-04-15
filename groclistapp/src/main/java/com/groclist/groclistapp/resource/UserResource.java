package com.groclist.groclistapp.resource;


import com.groclist.groclistapp.dto.GrocListResponse;
import com.groclist.groclistapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private Date date = new Date();

    @Autowired
    private UserService userService;

    @POST
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GrocListResponse> addUser(@RequestHeader(name = "x-request-id", required = false) String requestId,
                                                    @RequestBody Map<String, Object> payload){


        if (requestId == null || requestId.isEmpty()){
            logger.debug("No requestId was sent via the HTTP header 'x-request-id'.");
            requestId = UUID.randomUUID().toString();
            logger.debug("Generating a UUID for this request {}", requestId);
        }
        MDC.put("request_id", requestId);

        try{
            userService.saveUser(payload);
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    "Successfully saved user",
                    requestId,
                    "Successfully saved user"
            ), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    "Failed to Save User",
                    requestId,
                    "Failed to Save User"
            ), HttpStatus.OK);
        }
    }

    @GET
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GrocListResponse> getUser(@RequestHeader(name = "x-request-id", required = false)
                                                                String requestId,
                                                    @RequestParam String uNames){

        if (requestId == null || requestId.isEmpty()){
            logger.debug("No requestId was sent via the HTTP header 'x-request-id'.");
            requestId = UUID.randomUUID().toString();
            logger.debug("Generating a UUID for this request {}", requestId);
        }
        MDC.put("request_id", requestId);

        try{

            String users = userService.getUser(uNames);
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    "Successfully fetched all users",
                    requestId,
                    users)
                    , HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    "Unable to fetch users",
                    requestId,
                    e.getMessage())
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
