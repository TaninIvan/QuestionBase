package com.ivantanin.questionbase;

import com.google.gson.Gson;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

@Configuration
public class InsertTestData {

    private static Logger log = Logger.getLogger(InsertTestData.class.getName());

    @Autowired UserService userService;
    @Autowired QuestionService questionService;
    @Autowired AvatarService avatarService;
    @Autowired AnswerService answerService;

    @Bean
    public MyType run() {
        return new MyType("hi");
    }

    @Bean
    @Qualifier("wow")
    public MyType runWow() {
        return new MyType("wow");
    }

    @PostConstruct
    public void postConstruct() {
        if (!userService.getAll().iterator().hasNext()) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src\\main\\resources\\users.json"));

                Gson gson = new Gson();
                User[] users = gson.fromJson(String.valueOf(jsonArray), User[].class);
                for (User user : users) {
                    userService.createUser(user);
                }
            } catch (IOException | ParseException e) {
                log.warning(e.getMessage());
            }
        }

        if (!questionService.getAll().iterator().hasNext()) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src\\main\\resources\\questions.json"));
                Gson gson = new Gson();
                Question[] questions = gson.fromJson(String.valueOf(jsonArray), Question[].class);
                for (Question question : questions) {
                    questionService.createQuestion(question);
                }
            } catch (IOException | ParseException e) {
                log.warning(e.getMessage());
            }
        }

        if (!avatarService.getAll().iterator().hasNext()) {

            avatarService.createAvatar(1L, "src\\main\\resources\\testAvas\\ava1.jpg");
            avatarService.createAvatar(2L, "src\\main\\resources\\testAvas\\ava2.jpg");
            avatarService.createAvatar(3L, "src\\main\\resources\\testAvas\\ava3.jpg");
            avatarService.createAvatar(4L, "src\\main\\resources\\testAvas\\ava4.jpg");
            avatarService.createAvatar(5L, "src\\main\\resources\\testAvas\\ava5.jpg");
            avatarService.createAvatar(6L, "src\\main\\resources\\testAvas\\ava6.jpg");
            avatarService.createAvatar(7L, "src\\main\\resources\\testAvas\\ava7.jpg");
            avatarService.createAvatar(8L, "src\\main\\resources\\testAvas\\ava8.jpg");
            avatarService.createAvatar(9L, "src\\main\\resources\\testAvas\\ava9.jpg");
            avatarService.createAvatar(10L, "src\\main\\resources\\testAvas\\ava10.jpg");
        }

        if (!answerService.getAll().iterator().hasNext()) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src\\main\\resources\\answers.json"));
                Gson gson = new Gson();
                Answer[] answers = gson.fromJson(String.valueOf(jsonArray), Answer[].class);
                Long questionId = 1L;
                Long userId = 1L;

                for (Answer answer : answers) {
                    answerService.createAnswer(questionId, userId, answer);

                    questionId++;
                    if (userId == 10L) {
                        userId = 1L;
                    } else userId++;
                }
            } catch (IOException | ParseException e) {
                log.warning(e.getMessage());
            }
        }
    }
}
