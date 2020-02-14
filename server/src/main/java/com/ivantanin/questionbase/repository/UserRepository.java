package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long>,
        PagingAndSortingRepository<User,Long> {
    Page<User> findAll (Pageable pageReq);
}
