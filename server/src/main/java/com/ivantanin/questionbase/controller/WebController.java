package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    QuestionService questionService;

    @RequestMapping("/savequestion")
    public String save(){
        questionService.createQuestion("How are you?", "Fine","Tanin Ivan", 10);
        System.out.println("Request");
        return "Success!";
    }

    @RequestMapping("/readquestion")
    public String read(){
        return questionService.getQuestion(1L);
    }

    @RequestMapping("/deletequestion")
    public String delete(){
        questionService.createQuestion("How are you?", "Fine","Tanin Ivan", 10);
        return "Deleted!";
    }
}
