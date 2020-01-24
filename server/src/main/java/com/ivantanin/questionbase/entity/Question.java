package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
//import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "Questions")
//@ToString(of = {"id", "topic", "questionText", "correctAnswers", "reward", "author", "creationDate"})
public class Question {

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private Collection<Answer> usersAnswers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "questions_topics", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "topic_name"))
    private Collection<Topic> questionTopics;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ques_id", nullable = false)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "correct_answers", nullable = false)
    private String correctAnswers;

    @Column(name = "reward")
    private Integer reward;

    @Column(name = "author")
    private String author;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime creationDate;

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getCorrectAnswers() { return correctAnswers;}

    @Override
    public String toString(){
        return "{" + this.id.toString() + ";" + this.questionText + ";(" + this.correctAnswers + ");" +
                    this.reward + ";" + this.author + ";" + this.creationDate + ";" + this.topic + ";}";

    }

}
