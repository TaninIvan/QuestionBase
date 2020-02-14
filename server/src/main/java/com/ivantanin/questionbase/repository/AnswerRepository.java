package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnswerRepository  extends CrudRepository<Answer, Long>,
        PagingAndSortingRepository<Answer,Long> {
    Page<Answer> findAll (Pageable pageReq);
}
