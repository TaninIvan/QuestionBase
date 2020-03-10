package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Question;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface QuestionRepositoryCustom {
    List<Question> filterQuestionsByHeaders(Map<String,String> headers, Pageable pageable) throws ParseException;
}
