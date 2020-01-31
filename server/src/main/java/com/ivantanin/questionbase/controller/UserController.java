package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("save")
    public String saveUser(){
        System.out.println("Save request for user");
        return  userService.createUser("Tester", "DifficultPassword", 0) + "saved!";
    }

    @GetMapping("read")
    public String readUser(){
        return String.valueOf(userService.get(1L));
    }

    @GetMapping("read/all")
    public String readAllUser(){
        return userService.getAll();
    }

    @GetMapping("delete")
    public String deleteQuestion(){
        userService.delete(1L);
        return "User has deleted!";
    }

    @GetMapping("delete/all")
    public String deleteAllQuestions(){
        userService.deleteAll();
        return "All users have deleted!";
    }
}
