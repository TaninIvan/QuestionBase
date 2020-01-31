package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Transactional
    public Topic createTopic(String topicName){
        Topic topic = new Topic();
        topic.setTopicName(topicName);

        topicRepository.save(topic);
        System.out.println("I saved new topic");
        return topic;
    }

    public String get(String topicName) {
        return String.valueOf(topicRepository.findByTopicName(topicName).orElse(new Topic()));
    }

    public String getAll() {
        return String.valueOf(topicRepository.findAll());
    }

    public void delete(String topicName) {
        topicRepository.deleteByTopicName(topicName);
    }

    public void deleteAll() {
        topicRepository.deleteAll();
    }
}