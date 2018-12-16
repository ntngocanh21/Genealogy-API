package com.api.genealogy.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.genealogy.entity.BranchPermissionEntity;

public interface BranchPermissionRepository extends CrudRepository<BranchPermissionEntity, Integer> {
    BranchPermissionEntity findBranchPermissionEntityById(Integer branchPermissionId);
}

