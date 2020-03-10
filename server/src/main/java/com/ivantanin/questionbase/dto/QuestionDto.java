package com.ivantanin.questionbase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class QuestionDto {

    @NotNull private Long id;
    @NotNull private String questionText;
    private String correctAnswers;
    private String author;
    private int reward;
    private Set<String> topicNameSet;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull private Date creationDate;

    public QuestionDto() {
        this.creationDate = new Date();
        this.topicNameSet = new HashSet<String>();
    }

    public void addTopicName(String topicName) {
        this.topicNameSet.add(topicName);
    }

}
