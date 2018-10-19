package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.People;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.PeopleRepository;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.PeopleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService  {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public PeopleResponse getPeopleByBranchId(Integer branchId) {
        List<PeopleEntity> peopleEntities = (List<PeopleEntity>) peopleRepository
                .findPeopleEntitiesByBranchEntity_IdOrderByLifeIndex(branchId);
        List<People> peopleList = parseListPeopleEntityToListPeople(peopleEntities);
        MessageResponse messageResponse = new MessageResponse(0,"Success");
        PeopleResponse peopleResponse = new PeopleResponse(messageResponse, peopleList);
        return peopleResponse;
    }

    private List<People> parseListPeopleEntityToListPeople(List<PeopleEntity> peopleEntities) {
        List<People> peopleList = new ArrayList<>();
        for (PeopleEntity peopleEntity : peopleEntities) {
            People people = parsePeopleEntityToPeople(peopleEntity);
            peopleList.add(people);
        }
        return peopleList;
    }

    @Override
    public PeopleResponse createPeople(String username, People people) {
        PeopleResponse peopleResponse = new PeopleResponse();
        PeopleEntity peopleEntity = parsePeopleToPeopleEntity(people);

        PeopleEntity newPeople = peopleRepository.save(peopleEntity);
        ArrayList<People> peopleList = new ArrayList<>();
        peopleList.add(parsePeopleEntityToPeople(newPeople));
        peopleResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        peopleResponse.setPeopleList(peopleList);
        return peopleResponse;
    }

    @Override
    public CodeResponse deletePeople(String username, Integer peopleId) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        PeopleEntity peopleEntity = peopleRepository.findPeopleEntityById(peopleId);
        if (peopleEntity == null){
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"No people found"));
        }
        else {
            peopleRepository.deleteById(peopleId);
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        }
        return codeResponse;
    }

    @Override
    public CodeResponse updatePeople(String username, People people) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        PeopleEntity peopleEntity = peopleRepository.findPeopleEntityById(people.getId());
        if (peopleEntity != null){
            peopleRepository.save(parsePeopleToPeopleEntity(people));
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        }
        else {
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"People doesn't exist"));
        }
        return codeResponse;
    }


    private PeopleEntity parsePeopleToPeopleEntity(People people) {
        PeopleEntity peopleEntity = new PeopleEntity();
        peopleEntity.setImage(people.getImage());
        peopleEntity.setAddress(people.getAddress());
        peopleEntity.setBirthday(people.getBirthday());
        peopleEntity.setBranchEntity(branchRepository.findBranchEntityById(people.getBranchId()));
        peopleEntity.setDeathDay(people.getDeathDay());
        peopleEntity.setDegree(people.getDegree());
        peopleEntity.setDescription(people.getDescription());
        peopleEntity.setGender(people.getGender());
        peopleEntity.setName(people.getName());
        peopleEntity.setNickname(people.getNickname());
        peopleEntity.setLifeIndex(people.getLifeIndex());
        if(people.getParentId() != null){
            peopleEntity.setParentEntity(peopleRepository.findPeopleEntityById(people.getParentId()));
        }
        return peopleEntity;
    }

    private People parsePeopleEntityToPeople(PeopleEntity peopleEntity) {
        People people = new People();
        people.setId(peopleEntity.getId());
        people.setAddress(peopleEntity.getAddress());
        people.setBirthday(peopleEntity.getBirthday());
        people.setBranchId(peopleEntity.getBranchEntity().getId());
        people.setDeathDay(peopleEntity.getDeathDay());
        people.setDegree(peopleEntity.getDegree());
        people.setDescription(peopleEntity.getDescription());
        people.setGender(peopleEntity.getGender());
        people.setImage(peopleEntity.getImage());
        people.setLifeIndex(peopleEntity.getLifeIndex());
        people.setName(peopleEntity.getName());
        people.setNickname(peopleEntity.getNickname());
        if(peopleEntity.getParentEntity()!= null){
            people.setParentId(peopleEntity.getParentEntity().getId());
        }
        return people;
    }
}