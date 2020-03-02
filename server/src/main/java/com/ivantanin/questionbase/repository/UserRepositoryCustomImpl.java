package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.User;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getUsersByScore(Integer from, Integer to, Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // вы полняем сам select
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> u = q.from(User.class);
        q.select(u).where(cb.between(u.get("score"), from, to));

        TypedQuery<User> typedQuery = em.createQuery(q);
        // pagination and return
        typedQuery.setFirstResult(pageNumber*pageSize);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }


}
