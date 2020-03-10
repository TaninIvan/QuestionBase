package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, String>,
        PagingAndSortingRepository<Topic,String> {

     Optional<Topic> findByTopicName(String topicName);
     void  deleteByTopicName(String topicName);
     Page<Topic> findAll (Pageable pageReq);
     List<Topic> findAll();
}
