package com.ivantanin.questionbase;

import com.ivantanin.questionbase.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestionbaseApplication {

	@Autowired
	QuestionService questionService;

	public void testQuestionRepository(){
		questionService.createQuestion("How are you?", "Fine","Tanin Ivan");
	}

	public static void main(String[] args) {

		SpringApplication.run(QuestionbaseApplication.class, args);
	}

}
