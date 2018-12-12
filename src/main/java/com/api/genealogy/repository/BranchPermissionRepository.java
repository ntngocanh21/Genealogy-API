package com.api.genealogy.repository;

import com.api.genealogy.entity.BranchPermissionEntity;
import com.api.genealogy.entity.PeopleEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BranchPermissionRepository extends CrudRepository<BranchPermissionEntity, Integer> {
    BranchPermissionEntity findBranchPermissionEntityById(Integer branchPermissionId);
}

