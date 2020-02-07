package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class TopicService {

    @Autowired TopicRepository topicRepository;
    private static Logger log = Logger.getLogger(TopicService.class.getName());

    public Topic createTopic(String topicName){
        Topic topic = new Topic();
        topic.setTopicName(topicName);

        topicRepository.save(topic);
        log.fine("New topic saved!");
        return topic;
    }

    public Topic createTopic(Topic topic){
        topicRepository.save(topic);
        log.fine("New topic saved!");
        return topic;
    }

    public Topic get(String topicName) {
        return topicRepository.findByTopicName(topicName).orElse(null);
    }

    public Iterable<Topic> getAll() {
        return topicRepository.findAll();
    }

    public void delete(String topicName) {
        topicRepository.deleteByTopicName(topicName);
    }

    public void deleteAll() {
        topicRepository.deleteAll();
    }
}