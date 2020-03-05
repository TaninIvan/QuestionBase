package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Question;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Question> filterQuestionsByHeaders(Map<String, String> headers, Pageable pageable) throws ParseException {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Question> q = cb.createQuery(Question.class);
        Root<Question> root = q.from(Question.class);

        List<Predicate> predicates = new ArrayList<>();

        if(headers.containsKey("author")) {
            Predicate predicateForAuthor
                    = cb.equal(root.get("author"), headers.get("author"));
            predicates.add(predicateForAuthor);

        }

        if(headers.containsKey("from")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date from = format.parse(headers.get("from"));

            Predicate predicateForStartDate
                    = cb.greaterThanOrEqualTo(root.get("creationDate"), from);
            predicates.add(predicateForStartDate);
        }

        if(headers.containsKey("to")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date to = format.parse(headers.get("to"));

            Predicate predicateForStartDate
                    = cb.lessThanOrEqualTo(root.get("creationDate"), to);
            predicates.add(predicateForStartDate);
        }

        if (!predicates.isEmpty())
            q.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Question> typedQuery = em.createQuery(q);
        // pagination and return
        typedQuery.setFirstResult(pageNumber*pageSize);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }
}
