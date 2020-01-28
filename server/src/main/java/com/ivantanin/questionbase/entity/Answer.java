package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "answer")

public class Answer {

    @PersistenceContext
    private EntityManager em;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "question_id")
    private Question question;

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "answer_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime answerDate;

    public void setAnswerDate(LocalDateTime creationDate) {
        this.answerDate = creationDate;
    }

    public void setUser(User user) {
        em.getTransaction().begin();
        this.user = user;
        user.addAnswer(this);
        em.merge(this);
        em.merge(user);
        em.getTransaction().commit();
    }

    public User getUser() {
        return this.user;
    }

    public Question getQuestion(){
        return this.question;
    }

    public void setQuestion(Question q){
        em.getTransaction().begin();
        this.question = q;
        q.addUserAnswer(this);
        em.merge(this);
        em.merge(q);
        em.getTransaction().commit();
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return "{" + this.id.toString() + ";" + this.getUser().getUsername() + ";" + this.getUser().getId() + ";" +
                    this.getQuestion().getId() + ";" + this.getQuestion().getQuestionText() +"}";

    }
}
