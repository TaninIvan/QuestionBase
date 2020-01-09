package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // return all users
    @GetMapping
    @ResponseBody
    public List<User> getUsers(){

        return userRepository.findAll();
    }

}
