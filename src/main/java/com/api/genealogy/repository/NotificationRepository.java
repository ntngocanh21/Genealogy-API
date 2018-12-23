package com.api.genealogy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.NotificationEntity;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Integer> {
	List<NotificationEntity> findNotificationEntitiesByUserNotificationEntity_IdOrderByIdDesc(Integer userId);
}
