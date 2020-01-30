package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/question")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("save")
    public String saveQuestion(){
        questionService.createQuestion("How are you?", "Fine","Tanin Ivan", 10);
        System.out.println("Request");
        return "Success!";
    }

    @GetMapping("read")
    public String readQuestion(){
        return questionService.getQuestion(1L);
    }

    @GetMapping("delete")
    public String deleteQuestion(){
        questionService.deleteQuestion(1L);
        return "Deleted!";
    }

    @GetMapping("deleteall")
    public String deleteAllQuestions(){
        questionService.deleteAll();
        return "All questions has deleted!";
    }
}
