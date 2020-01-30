package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Transactional
    public void createQuestion(String text, String answer, String author, int rew){
        Question question = new Question();
        question.setCreationDate(LocalDateTime.now());
        question.setQuestionText(text);
        question.setAuthor(author);
        question.setCorrectAnswers(answer);
        question.setReward(rew);

        questionRepository.save(question);
        System.out.println("I saved");
    }

    public String getQuestion(Long id) {
        return String.valueOf(questionRepository.getById(id).orElse(new Question()));
    }
}
