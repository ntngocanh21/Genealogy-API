package com.api.genealogy.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.EventEntity;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Integer> {
    List<EventEntity> findEventEntityByUserCreatedEventEntity_UsernameAndBranchEventEntity_IdOrderByDateDesc(String username, Integer branchId);
    List<EventEntity> findEventEntityByBranchEventEntity_IdOrderByDate(Integer branchId);
}