package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.service.QuestionService;
import com.ivantanin.questionbase.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/question")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    TopicService topicService;

    @GetMapping("save")
    public String saveQuestion(){
        System.out.println("Save request for question");
        return  questionService.createQuestion("How are you?", "Fine","Tanin Ivan", 10, "Personal") + " saved!";
    }

    @GetMapping("read")
    public Question readQuestion(){
        return questionService.get(1L);
    }

    @GetMapping("read/all")
    public Iterable<Question> readAllQuestions(){
        return questionService.getAll();
    }

    @GetMapping("delete")
    public String deleteQuestion(){
        questionService.delete(1L);
        return "Question has deleted!";
    }

    @GetMapping("delete/all")
    public String deleteAllQuestions(){
        questionService.deleteAll();
        return "All questions have deleted!";
    }

    @GetMapping("update/addTopic")
    public String addTopic(){
       questionService.addTopic(questionService.get(1L),new Topic("History"));
       return "Success";
    }
}
