package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    public Optional<Topic> findByTopicName(String topicName);
    public void  deleteByTopicName(String topicName);
}
