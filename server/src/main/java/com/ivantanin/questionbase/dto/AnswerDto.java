package com.ivantanin.questionbase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AnswerDto {

    @NotNull
    private Long id;
    private Long userId;
    @NotNull
    private Long questionId;
    @NotNull
    private String text;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime answerDate;

    public AnswerDto() {
        this.answerDate = LocalDateTime.now();
    }

}
