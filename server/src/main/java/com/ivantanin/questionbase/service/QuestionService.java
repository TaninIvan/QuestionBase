package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class QuestionService {

    private  final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {

        this.questionRepository = questionRepository;
    }

    // Function for creating a new question
    public void createQuestion(Question question){

        question.setCreationDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    // returning all questions
    public List<Question> findAll() {
        return  questionRepository.findAll();
    }

    // returning the question by id
    public List<Question> findAllById(Long id){

        return questionRepository.findAllById(id);
    }

    // deleting the question by id
    public void deleteById(Long id){

        deleteById(id);
    }


}
