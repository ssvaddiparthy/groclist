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
import java.util.*;

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
                                                    @RequestBody Map<String, Object> payload){

        String email, firstName, lastName, uName, password;
        Integer kidsCount, adultsCount;

        if (requestId == null || requestId.isEmpty()){
            logger.debug("No requestId was sent via the HTTP header 'x-request-id'.");
            requestId = UUID.randomUUID().toString();
            logger.debug("Generating a UUID for this request {}", requestId);
        }
        MDC.put("request_id", requestId);

        // validate Date
        Date userDOB = null;
        try{
            userDOB = new SimpleDateFormat("YYYY-MM-dd").parse((String) payload.get("dob"));
            logger.info(userDOB.toString());
        } catch (ParseException exception){
            logger.error(String.format("Invalid date. Expected {} to be in format dd/mm/yyyy", (String) payload.get("dob")));
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    String.format("Invalid date. Expected {} to be in format dd/mm/yyyy", (String) payload.get("dob")),
                    requestId,
                    ""
            ), HttpStatus.BAD_REQUEST);
        }

        //validate email address
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid((String) payload.get("email"))){
            logger.error(String.format("Invalid email.", (String) payload.get("email")));
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    String.format("Invalid email.", (String) payload.get("email")),
                    requestId,
                    ""
            ), HttpStatus.BAD_REQUEST);
        } else{
            email = (String) payload.get("email");
        }

        // validate if password and repeat password are the same
        if (!((String) payload.get("pwd")).equals(((String) payload.get("re_pwd")))){
            logger.error("Passwords Do not match");
            return new ResponseEntity<>(new GrocListResponse(
                    new Timestamp(date.getTime()),
                    "Passwords Do not match",
                    requestId,
                    ""
            ), HttpStatus.BAD_REQUEST);
        } else{
            password = (String) payload.get("pwd");
        }

        uName = (String) payload.get("uname");
        firstName = (String) payload.get("fname");
        lastName = (String) payload.get("lname");
        kidsCount = Integer.parseInt((String) payload.get("kid_num"));
        adultsCount = Integer.parseInt((String) payload.get("adult_num"));

        userRepository.save(
                new User(uName, password, firstName, lastName, userDOB, adultsCount, kidsCount, email));

        return new ResponseEntity<>(new GrocListResponse(
                new Timestamp(date.getTime()),
                "Successfully saved user",
                requestId,
                uName
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
            logger.info(user.getEmailAddress());
        }

        return new ResponseEntity<>(new GrocListResponse(
                new Timestamp(date.getTime()),
                "Successfully fetched all users",
                requestId,
                userDTOList.toString()
        ), HttpStatus.OK);
    }



}
