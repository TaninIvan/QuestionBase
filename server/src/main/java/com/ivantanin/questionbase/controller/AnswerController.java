package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/answer")
@RestController
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping("save")
    public String saveUser(){
        System.out.println("Save request for user");
        return  answerService.createAnswer(1L, 1L, "Fine") + " saved!";
    }

    @GetMapping("read")
    public String readUser(){
        return String.valueOf(answerService.get(1L));
    }

    @GetMapping("read/all")
    public String readAllUser(){
        return answerService.getAll();
    }

    @GetMapping("delete")
    public String deleteQuestion(){
        answerService.delete(1L);
        return "User has deleted!";
    }

    @GetMapping("delete/all")
    public String deleteAllQuestions(){
        answerService.deleteAll();
        return "All users have deleted!";
    }
}

