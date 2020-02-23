package com.groclist.groclistapp.resource;


import com.groclist.groclistapp.dto.GrocListResponse;
import com.groclist.groclistapp.dto.UserDTO;
import com.groclist.groclistapp.model.User;
import com.groclist.groclistapp.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private Date date = new Date();

    @Autowired
    private UserRepository userRepository;

    @POST
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GrocListResponse> addUser(@RequestHeader(name = "x-request-id", required = false) String requestId,
                                                    @RequestParam String uName,
                                                    @RequestParam String firstName,
                                                    @RequestParam String lastName,
                                                    @RequestParam String password,
                                                    @RequestParam String email,
                                                    @RequestParam int kidsCount,
                                                    @RequestParam int adultsCount,
                                                    @RequestParam String dob){

        if (requestId == null || requestId.isEmpty()){
            logger.debug("No requestId was sent via the HTTP header 'x-request-id'.");
            requestId = UUID.randomUUID().toString();
            logger.debug("Generating a UUID for this request {}", requestId);
        }
        MDC.put("request_id", requestId);

        // validate Date
        Date userDOB = null;
        try{
            userDOB = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
            logger.info(dob);
            logger.info(userDOB.toString());
        } catch (ParseException exception){
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    String.format("Invalid date. Expected {} to be in format dd/mm/yyyy", dob),
                    requestId,
                    ""
            ), HttpStatus.BAD_REQUEST);
        }

        //validate email address
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)){
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    String.format("Invalid email.", dob),
                    requestId,
                    ""
            ), HttpStatus.BAD_REQUEST);
        }

        userRepository.save(
                new User(uName, password, firstName, lastName, userDOB, adultsCount, kidsCount, email));

        return new ResponseEntity<>(new GrocListResponse(
                new Timestamp(date.getTime()),
                "Successfully saved user",
                requestId,
                ""
        ), HttpStatus.OK);
    }

    @GET
    @RequestMapping("/all")
    @ResponseBody
    public ResponseEntity<GrocListResponse> getAllUsers(@RequestHeader(name = "x-request-id", required = false) String requestId){

        if (requestId == null || requestId.isEmpty()){
            logger.debug("No requestId was sent via the HTTP header 'x-request-id'.");
            requestId = UUID.randomUUID().toString();
            logger.debug("Generating a UUID for this request {}", requestId);
        }
        MDC.put("request_id", requestId);


        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user: userList) {
            userDTOList.add(new UserDTO(user.getUname(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDob(),
                    user.getAdultsInFamily(),
                    user.getKidsInFamily()));
        }

        return new ResponseEntity<>(new GrocListResponse(
                new Timestamp(date.getTime()),
                "Successfully fetched all users",
                requestId,
                userDTOList.get(0).toString()
        ), HttpStatus.OK);
    }



}
