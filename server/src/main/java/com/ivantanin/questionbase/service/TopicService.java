package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Topic;

import javax.persistence.*;
import java.util.List;

public class TopicService {

    @PersistenceContext
    private EntityManager em;

    // Function for creating a new question
    public void createTopic(Topic topic){
        try {
            em.getTransaction().begin();
            em.persist(topic);
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public Topic getTopic(String name){
        TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t WHERE t.topic_name = :name", Topic.class);
        query.setParameter("name", name);
        return query.getResultList().get(0);
    }

    public List<Topic> getAllTopics(){
        TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t", Topic.class);
        return query.getResultList();
    }
}
