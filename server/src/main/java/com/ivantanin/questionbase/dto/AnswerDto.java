package com.ivantanin.questionbase.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ToString
public class AnswerDto {

    @NotNull
    private Long id;
    private Long userId;
    private Long questionId;
    private String text;
    @NotNull
    private LocalDateTime answerDate;

}
