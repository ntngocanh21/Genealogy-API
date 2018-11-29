package com.api.genealogy.repository;


import com.api.genealogy.entity.BranchEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BranchRepository extends CrudRepository<BranchEntity, Integer> {
    List<BranchEntity> findBranchEntitiesByGenealogyEntity_IdOrderByName(Integer genealogyId);
    BranchEntity findBranchEntityById(Integer id);
}

