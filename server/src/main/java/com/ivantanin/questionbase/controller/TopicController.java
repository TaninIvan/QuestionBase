package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/topic")
@RestController
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("save")
    public String saveTopic(){
        System.out.println("Save request for topic");
        return  topicService.createTopic("History") + "saved!";
    }

    @GetMapping("read")
    public String readTopic(){
        return topicService.get("History");
    }

    @GetMapping("read/all")
    public String readAllTopics(){
        return topicService.getAll();
    }

    @GetMapping("delete")
    public String deleteTopic(){
        topicService.delete("History");
        return "Topic has deleted!";
    }

    @GetMapping("delete/all")
    public String deleteAllTopics(){
        topicService.deleteAll();
        return "All topics have deleted!";
    }
}

