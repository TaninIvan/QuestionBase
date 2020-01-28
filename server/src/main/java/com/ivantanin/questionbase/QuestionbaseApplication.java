package com.ivantanin.questionbase;

import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.AnswerService;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.QuestionService;
import com.ivantanin.questionbase.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;

@SpringBootApplication
public class QuestionbaseApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuestionbaseApplication.class, args);

	// Testing Questions
		QuestionService questionService = new QuestionService();

		// Fill the table
		questionService.fillQuestionTable();

		// Create and init new question
		Question ques = new Question();
		ques.setCorrectAnswers("three;3");
		ques.setReward(10);
		ques.setAuthor("Tanin Ivan");
		ques.setQuestionText("How many sides in a triangle?");
		ques.setCreationDate(LocalDateTime.now());


		//ques.setTopic("Geometry"); --------------- НЕ РАБОТАЕТ


		// Add new question to BD
		questionService.createQuestion(ques);

		// Print info about questions
		System.out.println(questionService.getQuestion(4));
		questionService.getAllQuestions().forEach(System.out::println);

	// Testing users
		UserService userService = new UserService();

		// Fill the table
		userService.fillUsersTable();

		// Create and init new user
		User usr = new User();
		usr.setUsername("Oxxxymiron");
		usr.setPassword("timeISmoney");
		usr.setScore(5000);

		// Add new user to BD
		userService.createUser(usr);

		// Print info about users
		System.out.println(userService.getUser(4));
		userService.getAllUsers().forEach(System.out::println);

	// Testing avatars

		AvatarService avatarService = new AvatarService();

		// Fill the table
		avatarService.fillAvatarTable();

		// Create and init new avatar
		Avatar avatar4 = new Avatar();
		avatar4.setImage("C:\\Users\\ivan.tanin\\Desktop\\QuestionBase\\server\\src\\main\\resources\\ava4.jpg");


		// Add new avatar to BD
		avatarService.createAvatar(avatar4);
		avatar4.setUser(usr);

		// Print info about avatars
		System.out.println(avatarService.getAvatar(4));
		avatarService.getAllAvatars().forEach(System.out::println);

	// Testing answers
		AnswerService answerService = new AnswerService();
		Answer answer1 = new Answer();

		// Create and init new answer
		answer1.setAnswerDate(LocalDateTime.now());
		answer1.setText("Я не помню");

		// Add new answer to BD
		answerService.createAnswer(answer1);
		answer1.setUser(usr);
		answer1.setQuestion(ques);

		// Print info about answers
		System.out.println(answerService.getAnswer(1));
		answerService.getAllAnswersByUser(4).forEach(System.out::println);
		answerService.getAllUsersByAnswer(1).forEach(System.out::println);
	}
}
