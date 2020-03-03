package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.AnswerDto;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AnswerRepository;
import com.ivantanin.questionbase.repository.QuestionRepository;
import com.ivantanin.questionbase.repository.UserRepository;
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
public class AnswerService {

    @Autowired AnswerRepository answerRepository;
    @Autowired UserRepository userRepository;
    @Autowired QuestionRepository questionRepository;

    // Setter Based Injection
    @Autowired ModelMapper modelMapper;
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // create
    public Answer createAnswer(Long questionId, Long userId, Answer answer){
        Question question = questionRepository.findById(questionId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        answer.setQuestion(question);
        answer.setUser(user);
        log.fine("New answer saved!");
        return answerRepository.save(answer);
    }

    // get
    public Answer get(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public List<Answer> getAll() {
        return answerRepository.findAll();
    }

    public List<Answer> getAnswerPage(Pageable pageable) {
        Page<Answer> answers = answerRepository.findAll(pageable);
        return answers.getContent();
    }

    public List<Answer> getAnswersByUserId(Long userId, Pageable pageable) {
        return answerRepository.findAllByUserId(userId,pageable).getContent();
    }

    // update
    public void updateAnswer(Answer newAnswer) {
        answerRepository.save(newAnswer);
    }

    //delete
    @Transactional
    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        answerRepository.deleteAll();
    }

    // other
    public boolean isCorrect(Answer answer) {
        return answer.getQuestion().getCorrectAnswers().contains(answer.getText());
    }

    // CONVERTERS
    public AnswerDto convertToDto(Answer answer) {
        return modelMapper.map(answer, AnswerDto.class);
    }

    public Answer convertToEntity(AnswerDto answerDto) throws ParseException {
        return modelMapper.map(answerDto, Answer.class);
    }
}
