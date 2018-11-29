package com.api.genealogy.service;

import com.api.genealogy.constant.GenealogyBranchRole;
import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.model.Branch;
import com.api.genealogy.model.Genealogy;
import com.api.genealogy.model.People;
import com.api.genealogy.model.Search;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.service.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService  {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GenealogyServiceImpl genealogyService;

    @Autowired
    private BranchServiceImpl branchService;

    @Autowired
    private PeopleServiceImpl peopleService;

    @Autowired
    private BranchRepository branchRepository;

//    @Override
//    public GenealogyAndBranchResponse searchGenealogyByName(Search search, String username) {
//        GenealogyAndBranchResponse genealogyAndBranchResponse = new GenealogyAndBranchResponse();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//        //search genealogy by name
//        CriteriaQuery<GenealogyEntity> criteriaQueryGenealogy = criteriaBuilder.createQuery(GenealogyEntity.class);
//        Root<GenealogyEntity> genealogyEntityRoot = criteriaQueryGenealogy.from(GenealogyEntity.class);
//        CriteriaQuery<GenealogyEntity> selectG = criteriaQueryGenealogy.where(criteriaBuilder.like(genealogyEntityRoot.get("name"), "%" + search.getName() + "%"));
//        selectG.orderBy(criteriaBuilder.asc(genealogyEntityRoot.get("name")));
//        TypedQuery<GenealogyEntity> typedQueryG = entityManager.createQuery(selectG);
//        List<GenealogyEntity> genealogyEntityListFound = typedQueryG.getResultList();
//        List<Genealogy> genealogyListFound = genealogyService.parseListGenealogyEntityToListGenealogy(genealogyEntityListFound);
//
//        //search branch by name
//        CriteriaQuery<BranchEntity> criteriaQueryBranch = criteriaBuilder.createQuery(BranchEntity.class);
//        Root<BranchEntity> branchEntityRoot = criteriaQueryBranch.from(BranchEntity.class);
//        CriteriaQuery<BranchEntity> selectB = criteriaQueryBranch.where(criteriaBuilder.like(branchEntityRoot.get("name"), "%" + search.getName() + "%"));
//        selectB.orderBy(criteriaBuilder.asc(branchEntityRoot.get("name")));
//        TypedQuery<BranchEntity> typedQueryB = entityManager.createQuery(selectB);
//        List<BranchEntity> BranchEntityListFound = typedQueryB.getResultList();
//        List<Branch> branchListFound = branchService.parseListBranchEntityToListBranch(BranchEntityListFound);
//
//        List<Genealogy> listGenealogyOfUser = genealogyService.getGenealogiesByUsername(username).getGenealogyList();
//        List<Branch> listBranchListFound = new ArrayList<>();
//        for(Genealogy genealogyFound : genealogyListFound){
//            listBranchListFound.addAll(genealogyFound.getBranchList());
//        }
//        compareGenealogyList(genealogyListFound, listGenealogyOfUser);
//
//        List<Branch> listBranchOfUser = new ArrayList<>();
//        for(Genealogy genealogy : listGenealogyOfUser){
//            listBranchOfUser.addAll(genealogy.getBranchList());
//        }
//        compareBranchList(branchListFound, listBranchOfUser);
//        compareBranchList(listBranchListFound, listBranchOfUser);
//
//        genealogyAndBranchResponse.setGenealogyList(genealogyListFound);
//        genealogyAndBranchResponse.setBranchList(branchListFound);
//        genealogyAndBranchResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
//
//        return genealogyAndBranchResponse;
//    }


    @Override
    public GenealogyResponse searchGenealogyByName(Search search, String username) {
        GenealogyResponse genealogyResponse = new GenealogyResponse();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        //search genealogy by name
        CriteriaQuery<GenealogyEntity> criteriaQueryGenealogy = criteriaBuilder.createQuery(GenealogyEntity.class);
        Root<GenealogyEntity> genealogyEntityRoot = criteriaQueryGenealogy.from(GenealogyEntity.class);
        CriteriaQuery<GenealogyEntity> selectG = criteriaQueryGenealogy.where(criteriaBuilder.like(genealogyEntityRoot.get("name"), "%" + search.getName() + "%"));
        selectG.orderBy(criteriaBuilder.asc(genealogyEntityRoot.get("name")));
        TypedQuery<GenealogyEntity> typedQueryG = entityManager.createQuery(selectG);
        List<GenealogyEntity> genealogyEntityListFound = typedQueryG.getResultList();
        List<Genealogy> genealogyListFound = genealogyService.parseListGenealogyEntityToListGenealogy(genealogyEntityListFound);

        List<Genealogy> listGenealogyOfUser = genealogyService.getGenealogiesByUsername(username).getGenealogyList();
        List<Branch> listBranchListFound = new ArrayList<>();
        for(Genealogy genealogyFound : genealogyListFound){
            listBranchListFound.addAll(genealogyFound.getBranchList());
        }
        compareGenealogyList(genealogyListFound, listGenealogyOfUser);

        List<Branch> listBranchOfUser = new ArrayList<>();
        for(Genealogy genealogy : listGenealogyOfUser){
            listBranchOfUser.addAll(genealogy.getBranchList());
        }
        compareBranchList(listBranchListFound, listBranchOfUser);

        genealogyResponse.setGenealogyList(genealogyListFound);
        genealogyResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        return genealogyResponse;
    }

    @Override
    public BranchResponse searchBranchByName(Search search, String username) {
        BranchResponse branchResponse = new BranchResponse();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        //search branch by name
        CriteriaQuery<BranchEntity> criteriaQueryBranch = criteriaBuilder.createQuery(BranchEntity.class);
        Root<BranchEntity> branchEntityRoot = criteriaQueryBranch.from(BranchEntity.class);
        CriteriaQuery<BranchEntity> selectB = criteriaQueryBranch.where(criteriaBuilder.like(branchEntityRoot.get("name"), "%" + search.getName() + "%"));
        selectB.orderBy(criteriaBuilder.asc(branchEntityRoot.get("name")));
        TypedQuery<BranchEntity> typedQueryB = entityManager.createQuery(selectB);
        List<BranchEntity> BranchEntityListFound = typedQueryB.getResultList();
        List<Branch> branchListFound = branchService.parseListBranchEntityToListBranch(BranchEntityListFound);

        List<Genealogy> listGenealogyOfUser = genealogyService.getGenealogiesByUsername(username).getGenealogyList();
        List<Branch> listBranchOfUser = new ArrayList<>();
        for(Genealogy genealogy : listGenealogyOfUser){
            listBranchOfUser.addAll(genealogy.getBranchList());
        }
        compareBranchList(branchListFound, listBranchOfUser);

        branchResponse.setBranchList(branchListFound);
        branchResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));

        return branchResponse;
    }

    public static void compareBranchList(List<Branch> listBranchInput, List<Branch> listBranchCompare){
        if (listBranchInput != null){
            if (listBranchCompare == null){
                for(Branch branchFound : listBranchInput){
                    branchFound.setRole(GenealogyBranchRole.GUEST);
                }
            } else {
                for(Branch branchFound : listBranchInput){
                    for(Branch branch : listBranchCompare){
                        if(branchFound.getId() == branch.getId()){
                            branchFound.setRole(branch.getRole());
                            break;
                        } else {
                            branchFound.setRole(GenealogyBranchRole.GUEST);
                        }
                    }
                }
            }
        }
    }

    public static void compareGenealogyList(List<Genealogy> listGenealogy, List<Genealogy> listGenealogyCompare){
        if (listGenealogy != null){
            if (listGenealogyCompare == null){
                for(Genealogy genealogyFound : listGenealogy){
                    genealogyFound.setRole(GenealogyBranchRole.GUEST);
                }
            } else {
                for(Genealogy genealogyFound : listGenealogy){
                    for(Genealogy genealogy : listGenealogyCompare){
                        if(genealogyFound.getId() == genealogy.getId()){
                            genealogyFound.setRole(genealogy.getRole());
                            break;
                        } else {
                            genealogyFound.setRole(GenealogyBranchRole.GUEST);
                        }
                    }
                }
            }
        }
    }

    @Override
    public PeopleResponse searchGenealogyByPeople(People people, String username) {
        PeopleResponse peopleResponse = new PeopleResponse();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<PeopleEntity> criteriaQueryPeople = criteriaBuilder.createQuery(PeopleEntity.class);
        Root<PeopleEntity> peopleEntityRoot = criteriaQueryPeople.from(PeopleEntity.class);
        criteriaQueryPeople.select(peopleEntityRoot);
        criteriaQueryPeople.orderBy(criteriaBuilder.asc(peopleEntityRoot.get("name")));
        criteriaQueryPeople.distinct(true);
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (people.getName() != null) {
            criteria.add(criteriaBuilder.like(peopleEntityRoot.get("name"), "%" + people.getName() + "%"));
        }
        if (people.getNickname() != null) {
            criteria.add(criteriaBuilder.like(peopleEntityRoot.get("nickname"), "%" + people.getNickname() + "%"));
        }
        if (people.getAddress() != null) {
            criteria.add(criteriaBuilder.like(peopleEntityRoot.get("address"), "%" + people.getAddress() + "%"));
        }
        if (people.getGender() != null) {
            criteria.add(criteriaBuilder.equal(peopleEntityRoot.get("gender"), people.getGender()));
        }
        if (people.getBirthday() != null){
            int year = people.getBirthday().getYear();
            Date dateBefore = new Date(year,1,1);
            Date dateAfter = new Date(year,12,31);
            criteria.add(criteriaBuilder.between(peopleEntityRoot.get("birthday"), dateBefore, dateAfter));
        }
        if (people.getDeathDay() != null){
            int year = people.getDeathDay().getYear();
            Date dateBefore = new Date(year,1,1);
            Date dateAfter = new Date(year,12,31);
            criteria.add(criteriaBuilder.between(peopleEntityRoot.get("deathDay"), dateBefore, dateAfter));
        }

        if (criteria.size() == 0) {
            throw new RuntimeException("no criteria");
        } else if (criteria.size() == 1) {
            criteriaQueryPeople.where(criteria.get(0));
        } else {
            criteriaQueryPeople.where(criteriaBuilder.and(criteria.toArray(new Predicate[0])));
        }

        TypedQuery<PeopleEntity> typedQuery= entityManager.createQuery(criteriaQueryPeople);
        List<PeopleEntity> resultList = typedQuery.getResultList();
        peopleResponse.setPeopleList(peopleService.parseListPeopleEntityToListPeople(resultList));
        peopleResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        return peopleResponse;
    }

}