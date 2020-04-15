package com.groclist.groclistapp.service;

import org.springframework.security.core.userdetails.User;
import com.groclist.groclistapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uName) throws UsernameNotFoundException {
        com.groclist.groclistapp.model.User dbUser = userRepository.findByUname(uName);
        return new User(dbUser.getUname(), dbUser.getPassword(), new ArrayList<>());
    }
}
