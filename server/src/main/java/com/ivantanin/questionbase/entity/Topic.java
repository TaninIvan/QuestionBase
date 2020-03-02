package com.ivantanin.questionbase.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = "questions")
public class Topic {

    public Topic() {
        this.questions = new HashSet<Question>();
    }

    public Topic(String topicName) {
        this.topicName = topicName;
        this.questions = new HashSet<Question>();
    }

    @Id
    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @ManyToMany(mappedBy = "topics", fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @ToString.Exclude
    private Set<Question> questions;

    public void addQuestion(Question question) {
        this.getQuestions().add(question);
    }
}

