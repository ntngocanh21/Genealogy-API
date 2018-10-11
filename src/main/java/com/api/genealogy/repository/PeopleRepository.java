package com.api.genealogy.repository;


import com.api.genealogy.entity.PeopleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeopleRepository extends CrudRepository<PeopleEntity, Integer> {
    List<PeopleEntity> findPeopleEntitiesByBranchEntity_Id(Integer branchId);
    PeopleEntity findPeopleEntityById(Integer id);
}

