package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>,
        PagingAndSortingRepository<Question,Long> {
    Page<Question> findAll (Pageable pageReq);
    List<Question> findAll(Sort sort);
    List<Question> findAll();
    List<Question> findAllByIdIn(List<Long> ids);
    List<Question> findMostPopularQuestion();
    List<Question> findMostPricedQuestion();
}
