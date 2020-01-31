package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.AvatarRepository;
import com.ivantanin.questionbase.repository.QuestionRepository;
import com.ivantanin.questionbase.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    AvatarRepository avatarRepository;


    @Transactional
    public Question createQuestion(String text, String answer, String author, int rew){
        Question question = new Question();
        question.setCreationDate(LocalDateTime.now());
        question.setQuestionText(text);
        question.setAuthor(author);
        question.setCorrectAnswers(answer);
        question.setReward(rew);

        questionRepository.save(question);
        System.out.println("I saved new question!");
        return question;
    }

    public String get(Long id) {
        return String.valueOf(questionRepository.findById(id).orElse(new Question()));
    }

    public String getAll() {
        return String.valueOf(questionRepository.findAll());
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    @Transactional
    public String addTopic(Long questionId, String topicName) {
        Optional<Question> question = questionRepository.findById(questionId);
        Optional<Topic> topic = topicRepository.findByTopicName(topicName);

        if (!question.get().getTopics().contains(topic)) {
            question.get().setTopics(topic.get());
            topic.get().setQuestions(question.get());
            questionRepository.save(question.get());
            return "Topic added to question!";
        } else {
            return "This question already has this topic!";
        }

    }


}
