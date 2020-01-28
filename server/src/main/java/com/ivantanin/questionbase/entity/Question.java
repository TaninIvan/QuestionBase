package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivantanin.questionbase.service.TopicService;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Questions")
public class Question {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> usersAnswers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "questions_topics", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "topic_name"))
    private List<Topic> questionTopics;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ques_id", nullable = false)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void addTopic(String name) {
        TopicService topicService = new TopicService();
        this.questionTopics.add(topicService.getTopic(name));
    }

    public  void setTopic(String name){

        Query query = em.createQuery("SELECT t FROM Topic t WHERE t.topicName = :name");
        query.setParameter("name", name);
        if (query.getResultList().isEmpty()) {
            Topic topic = new Topic();
            topic.setTopicName(name);
            topic.addQuestion(this);
            this.questionTopics.add(topic);
        } else {
            this.questionTopics.add(em.find(Topic.class, name));
        }

    }

    public List<Topic> getTopics() {
        return questionTopics;
    }

    public void addUserAnswer(Answer answer){
        this.usersAnswers.add(answer);

    }

    public List<Answer> getUsersAnswers() {
        return usersAnswers;
    }

    @Override
    public String toString(){
        return "{" + this.id.toString() + ";" + this.questionText + ";(" + this.correctAnswers + ");" +
                    this.reward + ";" + this.author + ";" + this.creationDate + "}";

    }

}
