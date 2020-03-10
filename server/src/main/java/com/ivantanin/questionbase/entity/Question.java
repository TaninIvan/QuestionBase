package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
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
        this.setCreationDate(new Date());
        this.topics = new HashSet<Topic>();
        this.usersAnswers = new HashSet<Answer>();
    }

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    transient private Set<Answer> usersAnswers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "questions_topics", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_name"))
    private Set<Topic> topics;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "question_text", nullable = false, unique = true)
    private String questionText;

    @Column(name = "correct_answers", nullable = false)
    private String correctAnswers;

    private Integer reward;
    private String author;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public void addTopic(Topic topic) {
        this.getTopics().add(topic);
    }
}
