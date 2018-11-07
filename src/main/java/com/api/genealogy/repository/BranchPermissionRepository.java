package com.api.genealogy.repository;

import com.api.genealogy.entity.BranchPermissionEntity;
import org.springframework.data.repository.CrudRepository;

public interface BranchPermissionRepository extends CrudRepository<BranchPermissionEntity, Integer> {
    BranchPermissionEntity findBranchPermissionEntityById(Integer branchPermissionId);
}

