package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

public class QuestionController {
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Start
    @RequestMapping("/")
    public String welcome(){

        return "Welcome to Question Base";
    }

    // return all questions
    @RequestMapping("/questions")
    @GetMapping
    public List<Question> getQuestions(){

        return questionService.findAll();
    }

    // find and return the question by id
    @GetMapping("{id}")
    public Question getOne(@PathVariable("id") Question question){

        return question;
    }

    // delete the question by id
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        questionService.deleteById(id);
    }
}
