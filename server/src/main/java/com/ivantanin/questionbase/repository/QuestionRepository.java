package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>,
        PagingAndSortingRepository<Question,Long>, QuestionRepositoryCustom {
    Page<Question> findAll (Pageable pageReq);
    List<Question> findAll(Sort sort);
    List<Question> findAll();
    List<Question> findAllByIdIn(List<Long> ids);
    List<Question> findMostPopularQuestion();
    List<Question> findMostPricedQuestion();

    @Query(value = "SELECT * FROM question ORDER BY creation_date DESC LIMIT :last", nativeQuery = true)
    List<Question> findLastQuestions(@Param("last") int last);

    @Modifying
    @Query(value = "DELETE FROM questions_topics qt " +
            "WHERE qt.question_id = :questionId AND qt.topic_name = :topicName",
            nativeQuery = true)
    void deleteTopicFromQuestion(@Param("questionId") Long questionId, @Param("topicName") String topicName);

}
