package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Avatar;

import javax.persistence.*;
import java.util.List;
import java.util.logging.Logger;

public class AvatarService {
    private static Logger log = Logger.getLogger(UserService.class.getName());

    @PersistenceContext
    private EntityManager em;

    // Function for creating a new avatar
    public void createAvatar(Avatar avatar){
        try {
            em.getTransaction().begin();
            em.persist(avatar);
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fillAvatarTable(){

        Query query = em.createQuery("SELECT av.avatar_id FROM Avatar av");
        if (query.getResultList().isEmpty()) {
            Avatar avatar1 = new Avatar();
            avatar1.setImage("C:\\Users\\ivan.tanin\\Desktop\\QuestionBase\\server\\src\\main\\resources\\ava1.png");

            Avatar avatar2 = new Avatar();
            avatar2.setImage("C:\\Users\\ivan.tanin\\Desktop\\QuestionBase\\server\\src\\main\\resources\\ava2.jpg");

            Avatar avatar3 = new Avatar();
            avatar3.setImage("C:\\Users\\ivan.tanin\\Desktop\\QuestionBase\\server\\src\\main\\resources\\ava3.jpg");

            em.getTransaction().begin();
            em.persist(avatar1);
            em.persist(avatar2);
            em.persist(avatar3);
            em.getTransaction().commit();

            log.info("Avatars table has no any records");

        } else {
            log.info("Avatars table has already had some records");
        }
    }

    public Avatar getAvatar(long id){
        TypedQuery<Avatar> query = em.createQuery("SELECT av FROM Avatar av WHERE av.avatar_id = :id", Avatar.class);
        query.setParameter("id", id);
        return query.getResultList().get(0);
    }

    public List<Avatar> getAllAvatars(){
        TypedQuery<Avatar> query = em.createQuery("SELECT av FROM Avatar av", Avatar.class);
        return query.getResultList();
    }
}
