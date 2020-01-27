package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.User;

import javax.persistence.*;
import java.util.List;

public class AnswerService {

    @PersistenceContext
    private EntityManager em;

    // Function for creating a new question
    public void createAnswer(Answer answer){
        try {
            em.getTransaction().begin();
            em.persist(answer);
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Answer getAnswer(long id){
        TypedQuery<Answer> query = em.createQuery("SELECT an FROM Answer an WHERE an.id = :id", Answer.class);
        query.setParameter("id", id);
        return query.getResultList().get(0);
    }

    public List<Answer> getAllAnswersByUser(long id){
        TypedQuery<Answer> query = em.createQuery("SELECT an FROM Answer an, User u WHERE u.id = :id AND an.user.id = u.id", Answer.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<User> getAllUsersByAnswer(long id){
        TypedQuery<User> query = em.createQuery("SELECT u FROM Answer an, User u WHERE an.id = :id AND an.user.id = u.id", User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


}
