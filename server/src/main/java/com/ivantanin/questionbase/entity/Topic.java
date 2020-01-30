package com.ivantanin.questionbase.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Topic {

    @Id
    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @ManyToMany(mappedBy = "questionTopics", cascade = CascadeType.REFRESH)
    private List<Question> questions;
}
