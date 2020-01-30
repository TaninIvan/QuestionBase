package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Transactional
    public Question createQuestion(String text, String answer, String author, int rew){
        Question question = new Question();
        question.setCreationDate(LocalDateTime.now());
        question.setQuestionText(text);
        question.setAuthor(author);
        question.setCorrectAnswers(answer);
        question.setReward(rew);

        questionRepository.save(question);
        System.out.println("I saved question!");
        return question;
    }

    public String getQuestion(Long id) {
        return String.valueOf(questionRepository.findById(id).orElse(new Question()));
    }

    public String getAllQuestion() {
        return String.valueOf(questionRepository.findAll());
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }
}
