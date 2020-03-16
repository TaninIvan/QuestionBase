package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.TopicDto;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.TopicRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log
public class TopicService {

    @Autowired TopicRepository topicRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired QuestionService questionService;

    // create
    public Topic createTopic(Topic topic) throws Exception {
        if(topicRepository.findByTopicName(topic.getTopicName()).isPresent())
            throw new Exception("Topic " + topic.getTopicName() + " already exists.");
        return topicRepository.save(topic);
    }

    // get
    public Topic get(String topicName) {
        return topicRepository.findByTopicName(topicName).orElse(null);
    }

    public List<Topic> getTopicPage(Pageable pageable) {
        Page<Topic> topics = topicRepository.findAll(pageable);
        return topics.getContent();
    }

    // update
    public Topic update(Topic topic) {
        return topicRepository.save(topic);
    }

    public void addQuestion(Topic topic, Question question) {
        topic.getQuestions().add(question);
        question.getTopics().add(topic);
    }

    // delete
    @Transactional
    public void delete(String topicName) {
        topicRepository.deleteByTopicName(topicName);
    }

    @Transactional
    public void deleteAll() {
        topicRepository.deleteAll();
    }

    // CONVERTERS
    public TopicDto convertToDto(Topic topic) {
        TopicDto topicDto = modelMapper.map(topic, TopicDto.class);
        if (!topic.getQuestions().isEmpty())
            topic.getQuestions().forEach(question -> topicDto.addQuestion(question.getId()));
        return topicDto;
    }

    public Topic convertToEntity(TopicDto topicDto) throws ParseException {
        Topic topic = modelMapper.map(topicDto, Topic.class);
        if (!topicDto.getQuestionsIds().isEmpty())
            topicDto.getQuestionsIds().forEach(questionId -> {
                try {
                    addQuestion(topic, questionService.get(questionId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        return topic;
    }
}