package com.api.genealogy.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.PeopleEntity;

public interface PeopleRepository extends CrudRepository<PeopleEntity, Integer> {
    List<PeopleEntity> findPeopleEntitiesByBranchEntity_IdOrderByLifeIndex(Integer branchId);
    PeopleEntity findPeopleEntityById(Integer id);
}

