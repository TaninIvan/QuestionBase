package com.ivantanin.questionbase.repository;

import com.ivantanin.questionbase.entity.Avatar;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvatarRepository extends CrudRepository<Avatar, Long> {
    List<Avatar> findAll();

    @Override
    @Modifying
    @Query("DELETE Avatar a WHERE a.avatar_id = :avatarId")
    void deleteById(@Param("avatarId") Long avatarId);
}
