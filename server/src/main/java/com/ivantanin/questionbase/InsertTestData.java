package com.ivantanin.questionbase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AnswerRepository;
import com.ivantanin.questionbase.repository.AvatarRepository;
import com.ivantanin.questionbase.repository.QuestionRepository;
import com.ivantanin.questionbase.repository.UserRepository;
import com.ivantanin.questionbase.service.AnswerService;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.QuestionService;
import com.ivantanin.questionbase.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@Log
public class InsertTestData {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired QuestionService questionService;
    @Autowired QuestionRepository questionRepository;
    @Autowired AvatarService avatarService;
    @Autowired AvatarRepository avatarRepository;
    @Autowired AnswerService answerService;
    @Autowired AnswerRepository answerRepository;



    @PostConstruct
    public void postConstruct() throws Exception {
        ObjectMapper om = new ObjectMapper();

        if (userRepository.count() == 0) {
            try (InputStream is = new FileInputStream(new File("src\\main\\resources\\users.json"))) {
                String json = IOUtils.toString(is, StandardCharsets.UTF_8);

                List<User> users = om.readValue(json,
                        om.getTypeFactory().constructCollectionType(List.class, User.class));
                users.forEach(user -> {
                    try {
                        userService.createUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }


        if (questionRepository.count() == 0) {
            try (InputStream is = new FileInputStream(new File("src\\main\\resources\\questions.json"))) {
                String json = IOUtils.toString(is, StandardCharsets.UTF_8);

                List<Question> questions = om.readValue(json,
                        om.getTypeFactory().constructCollectionType(List.class, Question.class));
                questions.forEach(question -> {
                    try {
                        questionService.createQuestion(question);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        if (avatarRepository.count() == 0) {

            File dir = new File("src\\main\\resources\\testAvas\\");
            File[] files = dir.listFiles();
            if (files == null)
                throw new NullPointerException("Failed to read images!");

            long id = 1L;
            for(File file:files){
                avatarService.createAvatar(id,file);
                id++;
            }
        }

        if (answerRepository.count() == 0) {
            try (InputStream is = new FileInputStream(new File("src\\main\\resources\\answers.json"))) {
                String json = IOUtils.toString(is, StandardCharsets.UTF_8);

                List<Answer> answers = om.readValue(json,
                        om.getTypeFactory().constructCollectionType(List.class, Answer.class));
                Long questionId = 1L;
                Long userId = 1L;
                for (Answer answer : answers) {
                    answerService.createAnswer(questionId, userId, answer);
                    questionId++;
                    if (userId == 10L)
                        userId = 1L;
                    else userId++;
                }
            }
        }
    }
}
