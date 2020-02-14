package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>,
        PagingAndSortingRepository<Question,Long> {
    Page<Question> findAll (Pageable pageReq);
}
