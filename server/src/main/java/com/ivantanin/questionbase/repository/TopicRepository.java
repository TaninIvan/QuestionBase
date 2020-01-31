package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, String> {
     Optional<Topic> findByTopicName(String topicName);
     void  deleteByTopicName(String topicName);
}
