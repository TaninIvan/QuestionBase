package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.TopicDto;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/topic")
@RestController
public class TopicController {

    @Autowired TopicService topicService;

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TopicDto createTopic(@RequestBody TopicDto topicDto) throws ParseException {
        Topic topic = topicService.convertToEntity(topicDto);
        Topic topicCreated = topicService.createTopic(topic);
        return topicService.convertToDto(topicCreated);
    }

    // GET
    @GetMapping(value = "/{topicName}")
    @ResponseBody
    public TopicDto getTopic(@PathVariable("topicName")String topicName){
        return topicService.convertToDto(topicService.get(topicName.substring(0,1).toUpperCase()
                + topicName.substring(1))); // topic name must be capitalized
    }

    @GetMapping("all")
    public Iterable<Topic> getAllTopics(){
        return topicService.getAll();
    }

    @GetMapping("all/page")
    @ResponseBody
    public List<TopicDto> getTopics(
            @PageableDefault(sort = {"topicName"}, direction = Sort.Direction.ASC) Pageable pageable) {

        List<Topic> topics = topicService.getTopicPage(pageable);
        return topics.stream()
                .map(topicService::convertToDto)
                .collect(Collectors.toList());
    }

    // PUT
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateTopic(@RequestBody TopicDto topicDto) throws ParseException {
        Topic topic = topicService.convertToEntity(topicDto);
        topicService.updateTopic(topic);
    }

    // DELETE
    @DeleteMapping("{topicName}" )
    public String deleteUser(@PathVariable("topicName") String topicName){
        topicService.delete(topicName.substring(0,1).toUpperCase()
                + topicName.substring(1)); // topic name must be capitalized
        return "Topic has deleted!";
    }

    @DeleteMapping("all")
    public String deleteAllTopics(){
        topicService.deleteAll();
        return "All topics have deleted!";
    }
}

