package com.ivantanin.questionbase;

import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class QuestionbaseApplication {

	@Autowired
	private QuestionService questionService;

	public static void main(String[] args) {

		SpringApplication.run(QuestionbaseApplication.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
		public void testJpaMethods(){

		Question question = new Question();
		question.setCorrectAnswers("Ivan;Vanya;Tanin Ivan;");
		question.setReward(50);
		question.setAuthor("Tanin Ivan");
		question.setQuestionText("What is my name?");

		Question question1 = new Question();
		question1.setCorrectAnswers("Fine;");
		question1.setReward(100);
		question1.setAuthor("Ivan");
		question1.setQuestionText("How are you?");
		question1.setTopic("Mood");


		questionService.createQuestion(question);
		questionService.createQuestion(question1);

		questionService.findAll().forEach(System.out::println);

		questionService.findAllById((long) 2).forEach(System.out::println);
	}

}
