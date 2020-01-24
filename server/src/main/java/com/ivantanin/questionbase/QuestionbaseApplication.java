package com.ivantanin.questionbase;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class QuestionbaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionbaseApplication.class, args);

		QuestionService questionService = new QuestionService();

		questionService.fillDataBase();

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

	}



}
