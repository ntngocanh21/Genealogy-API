package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.Branch;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.GenealogyRepository;
import com.api.genealogy.repository.PeopleRepository;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.response.BranchResponse;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.PeopleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService  {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private GenealogyRepository genealogyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public PeopleResponse getPeopleById(Integer peopleId) {
        PeopleEntity peopleEntity = peopleRepository.findPeopleEntityById(peopleId);
        return new PeopleResponse(new MessageResponse(0,"Success"), peopleEntity);
    }
}