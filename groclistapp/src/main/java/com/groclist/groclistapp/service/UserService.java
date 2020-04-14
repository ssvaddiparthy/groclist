package com.groclist.groclistapp.service;

import com.google.gson.Gson;
import com.groclist.groclistapp.dto.UserDto;
import com.groclist.groclistapp.exceptions.BadUserDetailException;
import com.groclist.groclistapp.model.User;
import com.groclist.groclistapp.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Gson gson;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private Date date = new Date();

    private UserDto entityToDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    private User dtoToEntity(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    private List<UserDto> entityToDto(List<User> userList){
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user: userList) {
            userDtoList.add(entityToDto(user));
        }
        return userDtoList;
    }

    private List<User> dtoToEntity(List<UserDto> userDtoList){
        List<User> userList = new ArrayList<>();
        for (UserDto userDto: userDtoList) {
            userList.add(dtoToEntity(userDto));
        }
        return userList;
    }

    private List<UserDto> getAllUsers(){
        return entityToDto(userRepository.findAll());
    }

    public String getUser(String uNames) {
        if (uNames.equals("all")){
            return gson.toJson(getAllUsers());
        } else{
            if (uNames.contains(",")){
                List<User> reqUsers = new ArrayList<>();
                String[] usernames = uNames.split(",");
                for (String username: usernames) {
                    reqUsers.add(userRepository.findByUname(username));
                }
                return gson.toJson(entityToDto(reqUsers));
            } else{
                return gson.toJson(entityToDto(userRepository.findByUname(uNames)));
            }
        }
    }

    public void saveUser(Map<String, Object> payload) throws Exception {
        String email, firstName, lastName, uName, password;
        Integer kidsCount, adultsCount;

        // validate Date
        Date userDOB = null;

        try{
            userDOB = new SimpleDateFormat("YYYY-MM-dd").parse((String) payload.get("dob"));
            logger.info(userDOB.toString());
        } catch (ParseException exception){
            logger.error(String.format("Invalid date. Expected {} to be in format dd/mm/yyyy", (String) payload.get("dob")));
            throw new BadUserDetailException(exception.getMessage());
        }

        //validate email address
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid((String) payload.get("email"))) {
            throw new BadUserDetailException(String.format("Unable to parse email %s", payload.get("email")));
        } else{
            email = (String) payload.get("email");
        }

        // validate if password and repeat password are the same
        if (!((String) payload.get("pwd")).equals(((String) payload.get("re_pwd")))){
            logger.error("Passwords Do not match");
            throw new BadUserDetailException("The sent passwords do not match");
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

    }
}
