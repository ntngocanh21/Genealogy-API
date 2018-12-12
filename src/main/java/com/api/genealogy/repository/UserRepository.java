package com.api.genealogy.repository;


import com.api.genealogy.entity.UserEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findUserEntityByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.deviceId = :deviceId WHERE u.username = :username")
    void updateDeviceId(@Param("deviceId") String deviceId, @Param("username") String username);
}

