package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.model.People;
import com.api.genealogy.model.Search;
import com.api.genealogy.service.response.GenealogyAndBranchResponse;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.PeopleResponse;
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

    @Override
    public GenealogyAndBranchResponse searchGenealogyByName(Search search) {
        GenealogyAndBranchResponse genealogyAndBranchResponse = new GenealogyAndBranchResponse();
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory();
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<GenealogyEntity> criteriaQueryGenealogy = criteriaBuilder.createQuery(GenealogyEntity.class);
        Root<GenealogyEntity> genealogyEntityRoot = criteriaQueryGenealogy.from(GenealogyEntity.class);
        CriteriaQuery<GenealogyEntity> selectG = criteriaQueryGenealogy.where(criteriaBuilder.like(genealogyEntityRoot.get("name"), "%" + search.getName() + "%"));
        selectG.orderBy(criteriaBuilder.asc(genealogyEntityRoot.get("name")));
        TypedQuery<GenealogyEntity> typedQueryG = entityManager.createQuery(selectG);

        CriteriaQuery<BranchEntity> criteriaQueryBranch = criteriaBuilder.createQuery(BranchEntity.class);
        Root<BranchEntity> branchEntityRoot = criteriaQueryBranch.from(BranchEntity.class);
        CriteriaQuery<BranchEntity> selectB = criteriaQueryBranch.where(criteriaBuilder.like(branchEntityRoot.get("name"), "%" + search.getName() + "%"));
        selectB.orderBy(criteriaBuilder.asc(branchEntityRoot.get("name")));
        TypedQuery<BranchEntity> typedQueryB = entityManager.createQuery(selectB);

        List<GenealogyEntity> resultListG = typedQueryG.getResultList();
        List<BranchEntity> resultListB = typedQueryB.getResultList();

        genealogyAndBranchResponse.setGenealogyList(genealogyService.parseListGenealogyEntityToListGenealogy(resultListG));
        genealogyAndBranchResponse.setBranchList(branchService.parseListBranchEntityToListBranch(resultListB));

        genealogyAndBranchResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        return genealogyAndBranchResponse;
    }

    @Override
    public PeopleResponse searchGenealogyByPeople(People people) {
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