package com.ivantanin.questionbase;

import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.QuestionService;
import com.ivantanin.questionbase.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@SpringBootApplication
public class QuestionbaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionbaseApplication.class, args);

	// Testing Questions
		QuestionService questionService = new QuestionService();

		questionService.fillQuestionTable();

		Question ques = new Question();
		ques.setCorrectAnswers("three;3");
		ques.setReward(10);
		ques.setAuthor("Tanin Ivan");
		ques.setQuestionText("How many sides in a triangle??");
		ques.setCreationDate(LocalDateTime.now());
		ques.setTopic("Geometry");

		questionService.createQuestion(ques);

		System.out.println(questionService.getQuestion(4));
		questionService.getAllQuestions().forEach(System.out::println);

	// Testing users
		UserService userService = new UserService();

		userService.fillUsersTable();

		User usr = new User();
		usr.setUsername("Oxxxymiron");
		usr.setPassword("timeISmoney");
		usr.setScore(5000);

		userService.createUser(usr);

		System.out.println(userService.getUser(4));
		userService.getAllUsers().forEach(System.out::println);

		// Testing avatars
		AvatarService avatarService = new AvatarService();

		avatarService.fillAvatarTable();

		Avatar avatar4 = new Avatar();
		avatar4.setImage("C:\\Users\\ivan.tanin\\Desktop\\QuestionBase\\server\\src\\main\\resources\\ava4.jpg");

		avatarService.createAvatar(avatar4);

		System.out.println(avatarService.getAvatar(4));
		avatarService.getAllAvatars().forEach(System.out::println);
	}



}
