package com.ivantanin.questionbase.entity;

import javax.persistence.*;

@Entity
@Table(name = "Questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "text")
    private String text;

    @Column(name = "answer")
    private String answer;

    @Column(name = "reward")
    private Integer reward;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "creation_data", nullable = false)
    private String creationData;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", text='" + text + '\'' +
                ", answer='" + answer + '\'' +
                ", reward='" + reward + '\'' +
                ", author='" + author + '\'' +
                ", creationData=" + creationData +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public String getCreationData() { return creationData; }

    public void setCreationData(String creationData) {
        this.creationData = creationData;
    }
}
