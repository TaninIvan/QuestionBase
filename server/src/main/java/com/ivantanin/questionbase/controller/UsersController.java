package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class UsersController {
    private QuestionService questionService;

    public UsersController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping("/")
    @ResponseBody
    public String welcome(){
        return "Welcome to Question Base";
    }

    @RequestMapping(value ="/questions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Question> getQuestion(){
        return questionService.findAll();
    }
}
