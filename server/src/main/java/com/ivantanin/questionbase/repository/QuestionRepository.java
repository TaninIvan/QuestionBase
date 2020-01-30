package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long> {
}
