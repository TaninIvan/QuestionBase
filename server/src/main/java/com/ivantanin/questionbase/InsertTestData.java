package com.ivantanin.questionbase;

import com.google.gson.Gson;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.AnswerService;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.QuestionService;
import com.ivantanin.questionbase.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
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

    @PostConstruct
    public void postConstruct() throws Exception {
        if (userService.getAll().isEmpty()) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser
                        .parse(new FileReader("src\\main\\resources\\users.json"));

                Gson gson = new Gson();
                User[] users = gson.fromJson(String.valueOf(jsonArray), User[].class);
                for (User user : users) {
                    userService.createUser(user);
                }
            } catch (IOException | ParseException e) {
                log.warning(e.getMessage());
            }
        }

        if (questionService.getAll().isEmpty()) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser
                        .parse(new FileReader("src\\main\\resources\\questions.json"));
                Gson gson = new Gson();
                Question[] questions = gson.fromJson(String.valueOf(jsonArray), Question[].class);
                for (Question question : questions) {
                    questionService.createQuestion(question);
                }
            } catch (IOException | ParseException e) {
                log.warning(e.getMessage());
            }
        }

        if (avatarService.getAll().isEmpty()) {

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

        if (answerService.getAll().isEmpty()) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser
                        .parse(new FileReader("src\\main\\resources\\answers.json"));
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
