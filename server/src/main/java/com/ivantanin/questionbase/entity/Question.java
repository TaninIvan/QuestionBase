package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NamedNativeQuery  (name = "Question.findMostPopularQuestion",
                    query =
                            "SELECT DISTINCT q.* FROM question q, answer a\n" +
                                    "WHERE q.id=a.question_id AND question_id IN\n" +
                                    "\t(SELECT question_id FROM (\n" +
                                    "\t\tSELECT question_id, count FROM (\n" +
                                    "\t\t\tSELECT question_id, COUNT(question_id) \n" +
                                    "\t\t\tFROM answer GROUP BY question_id) AS Q1\n" +
                                    "\t\t) AS Q2\n" +
                                    "\t\tWHERE count=(SELECT max(count) FROM (\n" +
                                    "\t\t\tSELECT question_id, COUNT(question_id) \n" +
                                    "\t\t\tFROM answer GROUP BY question_id) AS Q3)\n" +
                                    "\t)",
                    resultClass = Question.class)

@NamedQuery         (name = "Question.findMostPricedQuestion",
                    query = "SELECT q FROM Question q WHERE reward = (SELECT MAX(reward) FROM Question)")
public class Question {

    public Question() {
        this.setCreationDate(LocalDateTime.now());
        this.topics = new HashSet<Topic>();
        this.usersAnswers = new HashSet<Answer>();
    }

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
