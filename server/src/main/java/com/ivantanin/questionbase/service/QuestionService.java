package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired QuestionRepository questionRepository;
    @Autowired TopicService topicService;
    private static Logger log = Logger.getLogger(QuestionService.class.getName());

    // create
    public Question createQuestion(String text, String answer, String author, int rew, String topicName){
        Question question = new Question();
        question.setCreationDate(LocalDateTime.now());
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

    public Iterable<Question> getAll() {
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
    public void updateQuestion(Question newQuestion) {
        questionRepository.save(newQuestion);
    }

    // delete
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    // topic
    public void addTopic(Question question, Topic newtopic) {
        String topicName = newtopic.getTopicName();
        Topic topic = topicService.get(topicName);

        if (topic == null) {
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
    }
}
