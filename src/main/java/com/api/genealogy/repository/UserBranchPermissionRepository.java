package com.api.genealogy.repository;


import com.api.genealogy.entity.BranchPermissionEntity;
import com.api.genealogy.entity.UserBranchPermissionEntity;
import com.api.genealogy.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserBranchPermissionRepository extends CrudRepository<UserBranchPermissionEntity, Integer> {
    List<UserBranchPermissionEntity> findUserBranchPermissionEntitiesByBranchUserEntity_IdAndStatus(Integer branchId, boolean status);
    UserBranchPermissionEntity findUserBranchPermissionEntitiesByUserBranchEntity_UsernameAndBranchUserEntity_Id(String username, int branchId);
    List<UserBranchPermissionEntity> findUserBranchPermissionEntitiesByBranchPermissionEntityAndStatusAndUserBranchEntity(BranchPermissionEntity branchPermissionEntity, boolean status, UserEntity userEntity);
};
