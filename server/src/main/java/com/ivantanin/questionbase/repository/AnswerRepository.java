package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository  extends CrudRepository<Answer, Long>,
        PagingAndSortingRepository<Answer,Long> {
    Page<Answer> findAll (Pageable pageReq);
    List<Answer> findAll();

    @Modifying
    @Query("DELETE Answer a WHERE a.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Answer a  SET a.question.id = null WHERE a.question.id = :questionId")
    void deleteQuestionId(@Param("questionId") Long questionId);

    @Modifying
    @Query("UPDATE Answer a  SET a.question.id = null")
    void deleteAllQuestionId();

    @Query("SELECT a FROM Answer a  WHERE a.user.id = :userId AND a.question.id = :questionId")
    Optional<Answer> findByUserAndQuestionIds(@Param("userId")Long userId, @Param("questionId")Long questionId);

    Page<Answer> findAllByUserId(Long userId, Pageable pageReq);
}
