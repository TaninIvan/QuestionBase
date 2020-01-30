package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.repository.QuestionCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class QuestionService {

    @Autowired
    QuestionCrudRepository questionCrudRepository;

    public void createQuestion(String text, String answer, String author){
        Question question = new Question();
        question.setCreationDate(LocalDateTime.now());
        question.setQuestionText(text);
        question.setAuthor(author);
        question.setCorrectAnswers(answer);
    }
}
