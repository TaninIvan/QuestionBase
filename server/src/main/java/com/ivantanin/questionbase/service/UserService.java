package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.util.logging.Logger;

public class UserService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory( "Persistence" );
    private EntityManager em = emf.createEntityManager();

    // Function for creating a new user
    public void createUser(User user){
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void fillUsersTable(){

        Query query = em.createQuery("SELECT u.id FROM User u");
        if (query.getResultList().isEmpty()) {
            User user1 = new User();
            user1.setUsername("Luxor");
            user1.setPassword("qwerty");
            user1.setScore(1000);

            User user2 = new User();
            user2.setUsername("Fox");
            user2.setPassword("lovefox");
            user2.setScore(300);

            User user3 = new User();
            user3.setUsername("Druz");
            user3.setPassword("1239hfewh23@");
            user3.setScore(100000);

            em.getTransaction().begin();
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.getTransaction().commit();

            log.info("Users table has no any records");

        } else {
            log.info("Users table has already had some records");
        }
    }

    public User getUser(long id){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getResultList().get(0);
    }

    public List<User> getAllUsers(){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

}
