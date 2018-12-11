package com.api.genealogy.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.api.genealogy.entity.PeopleEntity;

public interface PeopleRepository extends CrudRepository<PeopleEntity, Integer> {
    List<PeopleEntity> findPeopleEntitiesByBranchEntity_IdOrderByLifeIndex(Integer branchId);
    PeopleEntity findPeopleEntityById(Integer id);
    
    @Transactional
    @Modifying
    @Query("FROM PeopleEntity p")
	List<PeopleEntity> getAllPeopleFromSystem();
}

