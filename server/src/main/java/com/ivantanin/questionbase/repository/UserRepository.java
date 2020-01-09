package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}