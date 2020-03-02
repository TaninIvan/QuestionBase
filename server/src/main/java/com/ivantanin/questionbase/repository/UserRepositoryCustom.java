package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> getUsersByScore(Integer from, Integer to, Pageable pageable);
}
