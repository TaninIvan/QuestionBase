package com.ivantanin.questionbase;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuestionbaseApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuestionbaseApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		/*ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true)
				.setFieldAccessLevel(PRIVATE); */
		return new ModelMapper();
	}

}
