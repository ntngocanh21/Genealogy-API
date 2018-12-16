package com.api.genealogy.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.NotificationEntity;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Integer> {
}
