package com.api.genealogy.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.EventEntity;

public interface EventRepository extends CrudRepository<EventEntity, Integer> {

	
}