package com.api.genealogy.service;

import com.api.genealogy.constant.GenealogyBranchRole;
import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.*;
import com.api.genealogy.model.*;
import com.api.genealogy.repository.*;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.GenealogyResponse;
import com.api.genealogy.service.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GenealogyServiceImpl implements GenealogyService  {

    @Autowired
    private GenealogyRepository genealogyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserBranchPermissionRepository userBranchPermissionRepository;

    @Autowired
    private BranchPermissionRepository branchPermissionRepository;

    @Override
    public GenealogyResponse getGenealogies() {
        List<GenealogyEntity> genealogyEntities = (List<GenealogyEntity>) genealogyRepository.findAll();
        if (genealogyEntities.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse(404,"No genealogy found");
            GenealogyResponse genealogyResponse = new GenealogyResponse(messageResponse, null);
            return genealogyResponse;
        }
        List<Genealogy> genealogies = parseListGenealogyEntityToListGenealogy(genealogyEntities);
        MessageResponse messageResponse = new MessageResponse(0,"Success");
        GenealogyResponse genealogyResponse = new GenealogyResponse(messageResponse, genealogies);
        return genealogyResponse;
    }

    @Override
    public GenealogyResponse getGenealogiesByUsername(String username) {
        List<Genealogy> genealogyListResult = new ArrayList<>();
        List<GenealogyEntity> genealogyEntities = (List<GenealogyEntity>) genealogyRepository
                .findGenealogyEntitiesByUserEntity_UsernameOrderByName(username);
        if (!genealogyEntities.isEmpty()) {
            List<Genealogy> genealogies = parseListGenealogyEntityToListGenealogy(genealogyEntities);
            for (Genealogy genealogy : genealogies){
                List<BranchEntity> branchEntityList = branchRepository.findBranchEntitiesByGenealogyEntity_IdOrderByName(genealogy.getId());
                List<Branch> branchList = BranchServiceImpl.parseListBranchEntityToListBranch(branchEntityList);
                for(Branch branch: branchList){
                    branch.setRole(GenealogyBranchRole.ADMIN);
                }
                genealogy.setBranchList(branchList);
                genealogy.setRole(GenealogyBranchRole.ADMIN);
                genealogyListResult.add(genealogy);
            }
        }

        genealogyListResult = findGenealogyByUsernameAndRole(username, GenealogyBranchRole.MOD, genealogyListResult);
        genealogyListResult = findGenealogyByUsernameAndRole(username, GenealogyBranchRole.MEMBER, genealogyListResult);

        MessageResponse messageResponse = new MessageResponse(0,"Success");
        GenealogyResponse genealogyResponse = new GenealogyResponse(messageResponse, genealogyListResult);
        return genealogyResponse;

    }

    private List<Genealogy> findGenealogyByUsernameAndRole(String username, int role, List<Genealogy> genealogyListResult){
        UserEntity user = userRepository.findUserEntityByUsername(username);
        //find branch user had mod/member role
        List<UserBranchPermissionEntity> userBranchPermissionEntities = userBranchPermissionRepository
                .findUserBranchPermissionEntitiesByBranchPermissionEntityAndStatusAndUserBranchEntity(
                        branchPermissionRepository.findBranchPermissionEntityById(role),true, user);

        if (!userBranchPermissionEntities.isEmpty()) {
            for (UserBranchPermissionEntity userBranchPermissionEntity : userBranchPermissionEntities){
                GenealogyEntity genealogyEntity = userBranchPermissionEntity.getBranchUserEntity().getGenealogyEntity();

                boolean checkGenealogyInList = false;

                if (genealogyListResult.size() != 0) {
                    for (Genealogy genealogy : genealogyListResult) {
                        //if genealogy of branch == genelogy in resultList
                        if (genealogyEntity.getId() == genealogy.getId()) {
                            checkGenealogyInList = true;
                            List<Branch> branchList = genealogy.getBranchList();
                            Branch branch = BranchServiceImpl.parseBranchEntityToBranch(userBranchPermissionEntity.getBranchUserEntity());
                            branch.setRole(role);
                            branchList.add(branch);
                            genealogy.setBranchList(branchList);
                            genealogy.setRole(GenealogyBranchRole.MEMBER);
                        } else {
                            checkGenealogyInList = false;
                        }
                    }

                    if(checkGenealogyInList == false){
                        addGenealogyToResult(genealogyEntity, genealogyListResult, role, userBranchPermissionEntity);
                    }
                } else {
                    addGenealogyToResult(genealogyEntity, genealogyListResult, role, userBranchPermissionEntity);
                }
            }
        }
        return genealogyListResult;
    }

    @Override
    public GenealogyResponse createGenealogy(String username, Genealogy genealogy) {
        GenealogyResponse genealogyResponse = new GenealogyResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = new GenealogyEntity();
        genealogyEntity.setName(genealogy.getName());
        genealogyEntity.setHistory(genealogy.getHistory());
        genealogyEntity.setUserEntity(userEntity);
        genealogyEntity.setDate(new Date());
        genealogyEntity.setBranch(0);

        GenealogyEntity newGenealogy = genealogyRepository.save(genealogyEntity);
        ArrayList<Genealogy> genealogies = new ArrayList<>();
        Genealogy createdGenealogy = parseGenealogyEntityToGenealogy(newGenealogy);
        createdGenealogy.setRole(GenealogyBranchRole.ADMIN);
        genealogies.add(createdGenealogy);
        genealogyResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        genealogyResponse.setGenealogyList(genealogies);
        return genealogyResponse;
    }

    private void addGenealogyToResult(GenealogyEntity genealogyEntity, List<Genealogy> genealogyListResult, int role, UserBranchPermissionEntity userBranchPermissionEntity){
        Genealogy genealogy = parseGenealogyEntityToGenealogy(genealogyEntity);
        List<Branch> branchList = new ArrayList<>();
        Branch branch = BranchServiceImpl.parseBranchEntityToBranch(userBranchPermissionEntity.getBranchUserEntity());
        branch.setRole(role);
        branchList.add(branch);
        genealogy.setBranchList(branchList);
        genealogyListResult.add(genealogy);
    }

    @Override
    public CodeResponse updateGenealogy(String username, Genealogy genealogy) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = genealogyRepository.findGenealogyEntityByIdOrderByName(genealogy.getId());
        if (genealogyEntity != null){
            if(genealogyEntity.getUserEntity().getId() == userEntity.getId()){
                genealogyEntity.setName(genealogy.getName());
                genealogyEntity.setHistory(genealogy.getHistory());
                genealogyRepository.save(genealogyEntity);
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
            }
            else {
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.UNAUTHORIZED,"Don't have permission"));
            }
        }
        else {
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"Genealogy doesn't exist"));
        }
        return codeResponse;
    }

    @Override
    public CodeResponse deleteGenealogy(String username, Integer genealogyId) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = genealogyRepository.findGenealogyEntityByIdOrderByName(genealogyId);
        if (genealogyEntity == null){
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"No genealogy found"));
        }
        else {
            if(genealogyEntity.getUserEntity().getId() == userEntity.getId()){
                genealogyRepository.deleteById(genealogyId);
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
            }
            else {
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.UNAUTHORIZED,"Don't have permission"));
            }
        }
        return codeResponse;
    }

    public static Genealogy parseGenealogyEntityToGenealogy(GenealogyEntity genealogyEntity) {
        Genealogy genealogy = new Genealogy();
        genealogy.setId(genealogyEntity.getId());
        genealogy.setHistory(genealogyEntity.getHistory());
        genealogy.setName(genealogyEntity.getName());
        genealogy.setDate(genealogyEntity.getDate());
        genealogy.setBranch(genealogyEntity.getBranch());
        genealogy.setOwner(genealogyEntity.getUserEntity().getFullname());
        return genealogy;
    }

    public static List<Genealogy> parseListGenealogyEntityToListGenealogy(List<GenealogyEntity> genealogyEntities) {
        List<Genealogy> genealogies = new ArrayList<>();
        for (GenealogyEntity genealogyEntity : genealogyEntities) {
            Genealogy genealogy = parseGenealogyEntityToGenealogy(genealogyEntity);
            genealogies.add(genealogy);
        }
        return genealogies;
    }

}