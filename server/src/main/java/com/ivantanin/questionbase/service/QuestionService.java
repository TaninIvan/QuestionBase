package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Question;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;


@Service
public class QuestionService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "questionPersistence" );
    private EntityManager em = emfactory.createEntityManager();

    // Function for creating a new question
    public void createQuestion(Question question){

        question.setCreationDate(LocalDateTime.now());
        if (question.getTopic() == null){
            question.setTopic("none");
        }

        if (question.getCorrectAnswers() == null){
            question.setCorrectAnswers("...");
        }

        if (question.getQuestionText() == null){
            question.setQuestionText("...");
        }

        try {
            em.getTransaction().begin();
            em.persist(question);
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void fillQuestionTable(){

        Query query = em.createQuery("SELECT q.id FROM Question q");
        if (query.getResultList().isEmpty()) {
            Question question = new Question();
            question.setCorrectAnswers("Ivan;Vanya;Tanin Ivan;");
            question.setReward(50);
            question.setAuthor("Tanin Ivan");
            question.setQuestionText("What is my name?");
            question.setCreationDate(LocalDateTime.now());

            Question question1 = new Question();
            question1.setCorrectAnswers("Fine;");
            question1.setReward(100);
            question1.setAuthor("Ivan");
            question1.setQuestionText("How are you?");
            question1.setTopic("Mood");
            question1.setCreationDate(LocalDateTime.now());

            Question question2 = new Question();
            question2.setCorrectAnswers("It is sky;it's sky; sky");
            question2.setReward(100);
            question2.setAuthor("DDT");
            question2.setQuestionText("What is autumn?");
            question2.setTopic("Music");
            question2.setCreationDate(LocalDateTime.now());

            em.getTransaction().begin();
            em.persist(question);
            em.persist(question1);
            em.persist(question2);
            em.getTransaction().commit();

            log.info("Questions table has no any records");

        } else {
            log.info("Questions table has already had some records");
        }
    }

    public Question getQuestion(long id){
        TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q WHERE q.id = :id", Question.class);
        query.setParameter("id", id);
        return query.getResultList().get(0);
    }

    public List<Question> getAllQuestions(){
        TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q", Question.class);
        return query.getResultList();
    }

}
