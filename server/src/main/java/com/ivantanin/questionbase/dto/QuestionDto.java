package com.ivantanin.questionbase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class QuestionDto {

    @NotNull private Long id;
    @NotNull private String questionText;
    private String correctAnswers;
    private String author;
    private int reward;
    private Set<String> topicNameSet;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull private LocalDateTime creationDate;

    public QuestionDto() {
        this.creationDate = LocalDateTime.now();
        this.topicNameSet = new HashSet<String>();
    }

    public void addTopicName(String topicName) {
        this.topicNameSet.add(topicName);
    }

}
