package com.api.genealogy.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.NotificationTypeEntity;

public interface NotificationTypeReponsitory extends CrudRepository<NotificationTypeEntity, Integer> {
	NotificationTypeEntity findNotificationTypeEntityById(Integer id);
	NotificationTypeEntity findNotificationTypeEntityByNotificationName(String notificationName);
}
