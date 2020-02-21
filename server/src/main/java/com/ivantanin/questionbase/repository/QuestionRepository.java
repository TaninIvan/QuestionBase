package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Question;
import org.jetbrains.annotations.NotNull;
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
    @NotNull Page<Question> findAll (@NotNull Pageable pageReq);

    @NotNull List<Question> findAll(@NotNull Sort sort);
    @NotNull List<Question> findAll();

    List<Question> findMostPopularQuestion();
    List<Question> findMostPricedQuestion();
}
