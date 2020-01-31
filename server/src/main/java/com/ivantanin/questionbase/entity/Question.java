package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Question {

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("question")
    private Set<Answer> usersAnswers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "questions_topics", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "topic_name"))
    @JsonIgnoreProperties("questions")
    private Set<Topic> topics;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "correct_answers", nullable = false)
    private String correctAnswers;

    private Integer reward;
    private String author;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime creationDate;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", correctAnswers='" + correctAnswers + '\'' +
                ", reward=" + reward +
                ", author='" + author + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public void setTopics(Topic topic) {
        this.getTopics().add(topic);
    }
}
