package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Avatar;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvatarRepository extends CrudRepository<Avatar, Long> {
    List<Avatar> findAll();
}
