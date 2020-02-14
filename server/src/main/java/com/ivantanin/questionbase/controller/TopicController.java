package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.TopicDto;
import com.ivantanin.questionbase.dto.UserDto;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/topic")
@RestController
public class TopicController {

    @Autowired TopicService topicService;
    @Autowired ModelMapper modelMapper;

    @GetMapping("save")
    public String saveTopic(){
        System.out.println("Save request for topic");
        return  topicService.createTopic("History") + "saved!";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TopicDto createUser(@RequestBody TopicDto topicDto) throws ParseException {
        Topic topic = convertToEntity(topicDto);
        Topic topicCreated = topicService.createTopic(topic);
        return convertToDto(topicCreated);
    }

    @GetMapping(value = "/{topicName}")
    @ResponseBody
    public Topic getTopic(@PathVariable("topicName")String topicName){
        return topicService.get(topicName);
    }

    @GetMapping("all")
    public Iterable<Topic> getAllTopics(){
        return topicService.getAll();
    }

    @GetMapping("all/page")
    @ResponseBody
    public List<TopicDto> getTopics(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort) {

        List<Topic> topics = topicService.getTopicList(page, size, sortDir, sort);
        return topics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    private TopicDto convertToDto(Topic topic) {
        return modelMapper.map(topic, TopicDto.class);
    }

    private Topic convertToEntity(TopicDto topicDto) throws ParseException {
        return modelMapper.map(topicDto, Topic.class);
    }
}

