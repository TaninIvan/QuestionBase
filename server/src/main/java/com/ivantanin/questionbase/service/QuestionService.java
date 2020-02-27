package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.QuestionDto;
import com.ivantanin.questionbase.dto.TopicDto;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.QuestionRepository;
import com.ivantanin.questionbase.repository.TopicRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class QuestionService {

    @Autowired QuestionRepository questionRepository;
    @Autowired TopicService topicService;
    @Autowired TopicRepository topicRepository;
    @Autowired ModelMapper modelMapper;

    // create
    public Question createQuestion(String text, String answer, String author, int rew, String topicName){
        Question question = new Question();
        question.setCreationDate(new Date());
        question.setQuestionText(text);
        question.setAuthor(author);
        // All correct answers are stored without capital letters
        question.setCorrectAnswers(answer.toLowerCase());
        question.setReward(rew);

        questionRepository.save(question);
        addTopic(question, new Topic(topicName));
        log.fine("New question saved");
        return question;
    }

    public Question createQuestion(Question question){
        // To avoid errors when loading from json file in InsertTestData.java
        question.getTopics().forEach(topic -> addTopic(question, topic));
        // All correct answers are stored without capital letters
        question.setCorrectAnswers(question.getCorrectAnswers().toLowerCase());
        log.fine("New question saved");
        return questionRepository.save(question);

    }

    // get
    public Question get(Long id) {
        return questionRepository.findById(id).orElse(new Question());
    }

    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionPage(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.getContent();
    }

    public List<Question> getLastQuestions(int last) {
        return questionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .limit(last)
                .collect(Collectors.toList());
    }

    public  List<Question> getMostPopularQuestion() {
        return questionRepository.findMostPopularQuestion();
    }

    public  List<Question> getMostPricedQuestion() {
        return questionRepository.findMostPricedQuestion();
    }

    // update
    @Transactional
    public void updateQuestion(Question newQuestion) {
        Question question = questionRepository.findById(newQuestion.getId()).orElse(new Question());

        if (!question.equals(newQuestion)) {
            // To avoid errors when loading from json file in InsertTestData.java
            newQuestion.getTopics().forEach(topic -> addTopic(newQuestion, topic));
            // All correct answers are stored without capital letters
            newQuestion.setCorrectAnswers(newQuestion.getCorrectAnswers().toLowerCase());

            question = newQuestion;
        }

    }

    // delete
    @Transactional
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        questionRepository.deleteAll();
    }

    // topic
    public String addTopic(Question question, Topic newtopic) {
        String topicName = newtopic.getTopicName();
        Topic topic = topicService.get(topicName);

        if (topicRepository.existsById(newtopic.getTopicName())) {
            topic = new Topic(topicName);
            topicService.createTopic(topic);
        }

        if (!question.getTopics().contains(topic)) {
            question.setTopics(topic);
            topic.setQuestions(question);
            questionRepository.save(question);
            topicService.createTopic(topic);
            log.fine("Topic added to the question!");
        } else log.fine("Topic already added!");
        return topicName;
    }

    // CONVERTERS
    public QuestionDto convertToDto(Question question) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        question.getTopics().forEach(topic -> questionDto.addTopicName(topic.getTopicName()));
        return questionDto;
    }

    public Question convertToEntity(QuestionDto questionDto) throws ParseException {
        Question question = modelMapper.map(questionDto, Question.class);
        questionDto.getTopicNameSet().forEach(topicName -> addTopic(question,new Topic(topicName)));
        // All correct answers are stored without capital letters
        question.setCorrectAnswers(question.getCorrectAnswers().toLowerCase());
        return question;
    }
}
