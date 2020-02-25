package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long>,
        PagingAndSortingRepository<User,Long> {

    @NotNull Page<User> findAll (@NotNull Pageable pageReq);

    @Query("SELECT u FROM User u WHERE (SELECT COUNT(*) FROM Answer a WHERE u.id = a.user) = 0")
    Page<User> findAllUsersWithoutAnswers(Pageable pageable);

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}
