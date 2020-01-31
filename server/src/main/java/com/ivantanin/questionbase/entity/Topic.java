package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Topic {

    @Id
    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @ManyToMany(mappedBy = "topics", cascade = CascadeType.DETACH)
    @JsonIgnoreProperties("topics")
    private Set<Question> questions;

    public void setQuestions(Question question) {
        this.getQuestions().add(question);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;
        Topic topic = (Topic) o;
        return topicName.equals(topic.topicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicName);
    }
}
