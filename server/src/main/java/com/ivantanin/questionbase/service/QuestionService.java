package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class QuestionService {

    @Autowired QuestionRepository questionRepository;
    @Autowired TopicService topicService;

    private static Logger log = Logger.getLogger(QuestionService.class.getName());

    public Question createQuestion(String text, String answer, String author, int rew, String topicName){
        Question question = new Question();
        question.setCreationDate(LocalDateTime.now());
        question.setQuestionText(text);
        question.setAuthor(author);
        question.setCorrectAnswers(answer);
        question.setReward(rew);

        questionRepository.save(question);
        addTopic(question, new Topic(topicName));
        log.fine("New question saved");
        return question;
    }

    public void createQuestion(Question question){
        question.getTopics().forEach(topic -> addTopic(question,topic));  // костыль для тестовых данных
        questionRepository.save(question);
        log.fine("New question saved");
    }

    public Question get(Long id) {
        return questionRepository.findById(id).orElse(new Question());
    }

    public Iterable<Question> getAll() {
        return questionRepository.findAll();
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

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
