package com.ivantanin.questionbase;

import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestionbaseApplication {

	@Autowired
	private QuestionService questionService;

	public static void main(String[] args) {

		SpringApplication.run(QuestionbaseApplication.class, args);
	}
/*  Code fot testing

	@EventListener(ApplicationReadyEvent.class)
		public void testJpaMethods(){

		Question question = new Question();
		question.setAnswer("Ivan");
		question.setReward(50);
		question.setAuthor("Ivan");
		question.setText("What is my name?");

		Question question1 = new Question();
		question1.setAnswer("Fine");
		question1.setReward(100);
		question1.setAuthor("Ivan");
		question1.setText("How are you?");


		questionService.createQuestion(question);

		questionService.findAll().forEach(System.out::println);

		questionService.findAllById((Long) 2).forEach(System.out::println);
	}
*/
}
