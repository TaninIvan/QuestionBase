package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AnswerRepository;
import com.ivantanin.questionbase.repository.QuestionRepository;
import com.ivantanin.questionbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AnswerService {

    @Autowired AnswerRepository answerRepository;
    @Autowired UserRepository userRepository;
    @Autowired QuestionRepository questionRepository;
    private static Logger log = Logger.getLogger(AnswerService.class.getName());

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

    public Iterable<Answer> getAll() {
        return answerRepository.findAll();
    }

    public List<Answer> getAnswerPage(Pageable pageable) {
        Page<Answer> answers = answerRepository.findAll(pageable);
        return answers.getContent();
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


}
