package com.api.genealogy.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.NotificationTypeEntity;
import com.api.genealogy.entity.UserBranchPermissionEntity;

public interface NotificationTypeReponsitory extends CrudRepository<NotificationTypeEntity, Integer> {
	NotificationTypeEntity findNotificationTypeEntityById(Integer id);
	UserBranchPermissionEntity findNotificationTypeEntityByNotificationName(String name);
}
