package com.ivantanin.questionbase.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ToString
public class QuestionDto {

    @NotNull private Long id;
    @NotNull private String questionText;
    private String correctAnswers;
    private String author;
    @NotNull private LocalDateTime creationDate;
}
