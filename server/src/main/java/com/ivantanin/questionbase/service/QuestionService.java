package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;

@Service
public class QuestionService {

    @Autowired
    private  final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {

        this.questionRepository = questionRepository;
    }

    public void createQuestion(Question question){

        question.setCreationData(new Date().toString());
        questionRepository.save(question);
    }

    public List<Question> findAll() {
        return  questionRepository.findAll();
    }

    public List<Question> findAllById(Long id){
        return questionRepository.findAllById(id);
    }

    public void deleteById(Long id){
        deleteById(id);
    }


}
