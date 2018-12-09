package com.api.genealogy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.api.genealogy.entity.NotificationEntity;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Integer> {
	@Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.deviceId = :deviceId WHERE u.username = :username")
	List<NotificationEntity> getListOfNotifications(@Param("deviceId") String deviceId, @Param("username") String username);
}
