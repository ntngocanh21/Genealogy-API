package com.api.genealogy.service;

import com.api.genealogy.constant.Gender;
import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.People;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.PeopleRepository;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.relations.FamilyRelations;
import com.api.genealogy.service.relations.Key;
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

    @Override
    public PeopleResponse createPeople(String username, People people) {
        PeopleResponse peopleResponse = new PeopleResponse();
        PeopleEntity peopleEntity = parsePeopleToPeopleEntity(people);
        PeopleEntity newPeople = peopleRepository.save(peopleEntity);

        BranchEntity branchEntity = branchRepository.findBranchEntityById(people.getBranchId());
        branchEntity.setMember(branchEntity.getMember()+1);
        branchRepository.save(branchEntity);

        ArrayList<People> peopleList = new ArrayList<>();
        peopleList.add(parsePeopleEntityToPeople(newPeople));
        peopleResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        peopleResponse.setPeopleList(peopleList);
        return peopleResponse;
    }

    @Override
    public CodeResponse deletePeople(String username, Integer peopleId) {
        CodeResponse codeResponse = new CodeResponse();
        PeopleEntity peopleEntity = peopleRepository.findPeopleEntityById(peopleId);
        if (peopleEntity == null){
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"No people found"));
        }
        else {
            peopleRepository.deleteById(peopleId);

            BranchEntity branchEntity = branchRepository.findBranchEntityById(peopleEntity.getBranchEntity().getId());
            List<PeopleEntity> peopleEntityList = peopleRepository.findPeopleEntitiesByBranchEntity_IdOrderByLifeIndex(branchEntity.getId());
            branchEntity.setMember(peopleEntityList.size());
            branchRepository.save(branchEntity);

            codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        }
        return codeResponse;
    }

    @Override
    public CodeResponse updatePeople(String username, People people) {
        CodeResponse codeResponse = new CodeResponse();
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

    @Override
    public PeopleResponse getFamilyRelation(int peopleId) {
        FamilyRelations familyRelations = new FamilyRelations();

        PeopleEntity centerPeople = peopleRepository.findPeopleEntityById(peopleId);
        List<PeopleEntity> peopleEntityList = peopleRepository.findPeopleEntitiesByBranchEntity_IdOrderByLifeIndex(centerPeople.getBranchEntity().getId());

        List<PeopleEntity> centerPeopleOrigin = new ArrayList<>();
        centerPeopleOrigin = getPeopleOriginList(centerPeople, centerPeopleOrigin);

        List<People> peopleResultList = new ArrayList<>();

        for (PeopleEntity people : peopleEntityList){
            if (people.getId() == centerPeople.getId()){
                People peopleCenter = parsePeopleEntityToPeople(people);
                peopleCenter.setAppellation("Center");
                peopleResultList.add(peopleCenter);
            } else {
                Key key = new Key();
                if (people.getGender() == 1){
                    key.setGender(Gender.MALE);
                } else {
                    key.setGender(Gender.FEMALE);
                }

                if((people.getParentEntity() != null && people.getParentEntity().getId() == centerPeople.getId()) ||
                        (centerPeople.getParentEntity() != null && centerPeople.getParentEntity().getId() == people.getId()) ||
                        (centerPeople.getParentEntity() != null && people.getParentEntity() != null && people.getParentEntity().getId() == centerPeople.getParentEntity().getId())
                ){
                    key.setInFamily(true);
                } else {
                    key.setInFamily(false);
                }

                int lifeDistance = people.getLifeIndex() - centerPeople.getLifeIndex();
                if (lifeDistance > 5){
                    lifeDistance = 5;
                }
                if (lifeDistance <-5){
                    lifeDistance = -5;
                }

                key.setLifeDistance(lifeDistance);
                key.setOriginOlder(null);


                switch (lifeDistance){
                    case 0:
                    case -1:
                        if (lifeDistance == 0){
                            key.setInFamily(null);
                        }

                        if (key.getInFamily() != null && key.getInFamily() == true){
                            key.setOriginOlder(null);
                        } else{
                            List<PeopleEntity> peopleOriginList = new ArrayList<>();
                            peopleOriginList = getPeopleOriginList(people, peopleOriginList);
                            outerloop:
                            for (int indexCenterPeopleOrigin = 0; indexCenterPeopleOrigin < centerPeopleOrigin.size(); indexCenterPeopleOrigin++){
                                for (int indexPeopleOrigin = 0; indexPeopleOrigin < peopleOriginList.size(); indexPeopleOrigin++ ){
                                    if (centerPeopleOrigin.get(indexCenterPeopleOrigin).getId() == peopleOriginList.get(indexPeopleOrigin).getId()) {
                                        PeopleEntity people1 = centerPeopleOrigin.get(indexCenterPeopleOrigin -1);
                                        PeopleEntity people2 = peopleOriginList.get(indexPeopleOrigin-1);

                                        if (people1.getBirthday().after(people2.getBirthday())) {
                                            key.setOriginOlder(true);
                                            break outerloop;
                                        } else {
                                            key.setOriginOlder(false);

                                        }

                                    }
                                }
                            }
                        }
                        break;
                    case -5:
                    case -4:
                    case -3:
                    case -2:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        key.setInFamily(null);
                        break;
                    default:
                        break;
                }

                People peopleResult = parsePeopleEntityToPeople(people);
                peopleResult.setAppellation(familyRelations.getFamilyRelation(key.getKey()));
                peopleResultList.add(peopleResult);
            }
        }
        PeopleResponse peopleResponse = new PeopleResponse();
        peopleResponse.setError(new MessageResponse(0, "Success"));
        peopleResponse.setPeopleList(peopleResultList);

        return peopleResponse;
    }

    private List<PeopleEntity> getPeopleOriginList(PeopleEntity peopleEntity, List<PeopleEntity> peopleOriginList){
        if (peopleOriginList.size() == 0) {
            peopleOriginList.add(peopleEntity);
            getPeopleOriginList(peopleEntity, peopleOriginList);
        } else {
            if (peopleEntity.getParentEntity() != null){
                peopleOriginList.add(peopleEntity.getParentEntity());
                getPeopleOriginList(peopleEntity.getParentEntity(), peopleOriginList);
            }
        }
        return peopleOriginList;
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
        if(people.getPartnerId() != null){
            peopleEntity.setPartnerEntity(peopleRepository.findPeopleEntityById(people.getPartnerId()));
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
        if(peopleEntity.getPartnerEntity()!= null){
            people.setPartnerId(peopleEntity.getPartnerEntity().getId());
        }
        return people;
    }

    public List<People> parseListPeopleEntityToListPeople(List<PeopleEntity> peopleEntities) {
        List<People> peopleList = new ArrayList<>();
        for (PeopleEntity peopleEntity : peopleEntities) {
            People people = parsePeopleEntityToPeople(peopleEntity);
            peopleList.add(people);
        }
        return peopleList;
    }
}