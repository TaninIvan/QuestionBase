package com.ivantanin.questionbase.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Topic {

    @Id
    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @ManyToMany(mappedBy = "questionTopics", cascade = CascadeType.REFRESH)
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String name) {
        this.topicName = name;
    }

    public void addQuestion(Question ques) {
        this.questions.add(ques);
    }
}
