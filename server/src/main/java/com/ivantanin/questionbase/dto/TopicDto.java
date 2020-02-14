package com.ivantanin.questionbase.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class TopicDto {

    @NotNull
    private String topicName;


}
