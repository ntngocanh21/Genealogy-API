package com.api.genealogy.repository;


import com.api.genealogy.entity.GenealogyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenealogyRepository extends CrudRepository<GenealogyEntity, Integer> {
    List<GenealogyEntity> findGenealogyEntitiesByUserEntity_Username(String username);
    GenealogyEntity findGenealogyEntityById(Integer id);
}

