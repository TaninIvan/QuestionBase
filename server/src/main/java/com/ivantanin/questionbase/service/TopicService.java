package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TopicService {

    @Autowired TopicRepository topicRepository;
    private static Logger log = Logger.getLogger(TopicService.class.getName());

    // create
    public Topic createTopic(String topicName){
        Topic topic = new Topic();
        topic.setTopicName(topicName);
        log.fine("New topic saved!");
        return topicRepository.save(topic);
    }

    public Topic createTopic(Topic topic){
        log.fine("New topic saved!");
        return topicRepository.save(topic);
    }

    // get
    public Topic get(String topicName) {
        return topicRepository.findByTopicName(topicName).orElse(null);
    }

    public Iterable<Topic> getAll() {
        return topicRepository.findAll();
    }

    public List<Topic> getTopicList(
            int page, int size, String sortDir, String sort) {
        PageRequest pageReq
                = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        Page<Topic> topics = topicRepository.findAll(pageReq);
        return topics.getContent();
    }

    // update
    public void updateUser(Topic newTopic) {
        topicRepository.save(newTopic);
    }

    // delete
    public void delete(String topicName) {
        topicRepository.deleteByTopicName(topicName);
    }

    public void deleteAll() {
        topicRepository.deleteAll();
    }
}