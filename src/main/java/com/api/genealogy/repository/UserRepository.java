package com.api.genealogy.repository;


import com.api.genealogy.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findUserEntityByUsername(String username);
}

