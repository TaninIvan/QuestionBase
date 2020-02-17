package com.ivantanin.questionbase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivantanin.questionbase.entity.Answer;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime answerDate;

    public AnswerDto() {
        this.answerDate = LocalDateTime.now();
    }

}
