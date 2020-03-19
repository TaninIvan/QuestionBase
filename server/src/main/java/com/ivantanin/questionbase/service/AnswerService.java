package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.AnswerDto;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AnswerRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@Log
public class AnswerService {

    @Autowired AnswerRepository answerRepository;
    @Autowired QuestionService questionService;
    @Autowired UserService userService;

    // Setter Based Injection
    @Autowired ModelMapper modelMapper;
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // create
    public Answer createAnswer(Long questionId, Long userId, Answer answer) throws Exception {
        if (answerRepository.findByUserAndQuestionIds(userId, questionId).orElse(null) != null)
            throw new Exception("This user has already answered this question!");
        Question question = questionService.get(questionId);
        User user = userService.getById(userId);

        answer.setQuestion(question);
        answer.setUser(user);
        if (isCorrect(answer)) {
            user.setScore(user.getScore() + question.getReward());
            userService.updateUser(user);
        }

        log.fine("New answer saved!");
        return answerRepository.save(answer);
    }

    public Answer toAnswer(String authorization, Answer answer) throws Exception {
        String username = null;

        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            String[] values = credentials.split(":", 2);
            username = values[0];
        }
        answer.setUser(userService.getByUsername(username));
        return createAnswer(answer.getQuestion().getId(),
                answer.getUser().getId(),answer);
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

    public Answer getAnswerByUserAndQuestionIds(Long userId, Long questionId) {
        return answerRepository.findByUserAndQuestionIds(userId,questionId).orElse(null);
    }

    // update
    public Answer updateAnswer(Answer newAnswer) {
        return answerRepository.save(newAnswer);
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
