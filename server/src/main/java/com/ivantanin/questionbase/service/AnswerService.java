package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AnswerRepository;
import com.ivantanin.questionbase.repository.QuestionRepository;
import com.ivantanin.questionbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Transactional
    public Answer createAnswer(Long questionId, Long userId, String str){
        Answer answer = new Answer();
        Question question = questionRepository.findById(questionId).get();
        User user = userRepository.findById(userId).get();

        answer.setAnswerDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setText(str);

        answerRepository.save(answer);
        System.out.println("I saved new answer!");
        return answer;
    }

    public String get(Long id) {
        return String.valueOf(answerRepository.findById(id).orElse(new Answer()));
    }

    public String getAll() {
        return String.valueOf(answerRepository.findAll());
    }

    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

    public void deleteAll() {
        answerRepository.deleteAll();
    }

    public boolean isCorrect(Answer answer) {
        if (answer.getQuestion().getCorrectAnswers().contains(answer.getText())) {
            return true;
        } else return false;
    }


}
