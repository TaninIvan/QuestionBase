package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {
}
