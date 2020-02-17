package com.ivantanin.questionbase.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class TopicDto {

    @NotNull
    private String topicName;
    private Set<Long> questionsId;

    public TopicDto() {
        this.questionsId = new HashSet<Long>();
    }

    public void addQuestion(Long id) {
        this.questionsId.add(id);
    }

}
