package com.api.genealogy.service;

import com.api.genealogy.constant.GenealogyBranchRole;
import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.UserBranchPermissionEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.Branch;
import com.api.genealogy.model.Genealogy;
import com.api.genealogy.repository.*;
import com.api.genealogy.service.response.BranchResponse;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService  {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private GenealogyRepository genealogyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchPermissionRepository branchPermissionRepository;

    @Autowired
    private UserBranchPermissionRepository userBranchPermissionRepository;

    @Autowired
    private GenealogyService genealogyService;

    @Override
    public BranchResponse getBranchesByGenealogyId(Integer genealogyId) {
        List<BranchEntity> branchEntities = (List<BranchEntity>) branchRepository
                .findBranchEntitiesByGenealogyEntity_IdOrderByName(genealogyId);
        List<Branch> branches = parseListBranchEntityToListBranch(branchEntities);
        MessageResponse messageResponse = new MessageResponse(0,"Success");
        BranchResponse branchResponse = new BranchResponse(messageResponse, branches);
        return branchResponse;
    }

    @Override
    public BranchResponse getBranchesByBranchId(String username, Integer branchId) {
        BranchEntity branchEntity = branchRepository
                .findBranchEntityById(branchId);
        List<BranchEntity> branchEntities = new ArrayList<>();
        branchEntities.add(branchEntity);
        List<Branch> branches = parseListBranchEntityToListBranch(branchEntities);

        List<Genealogy> listGenealogyOfUser = genealogyService.getGenealogiesByUsername(username).getGenealogyList();
        List<Branch> listBranchOfUser = new ArrayList<>();
        for(Genealogy genealogy : listGenealogyOfUser){
            listBranchOfUser.addAll(genealogy.getBranchList());
        }
        SearchServiceImpl.compareBranchList(branches, listBranchOfUser);

        MessageResponse messageResponse = new MessageResponse(0,"Success");
        BranchResponse branchResponse = new BranchResponse(messageResponse, branches);
        return branchResponse;
    }

    @Override
    public BranchResponse createBranch(String username, Branch branch) {
        BranchResponse branchResponse = new BranchResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = genealogyRepository.findGenealogyEntityByIdOrderByName(branch.getGenealogyId());
        BranchEntity branchEntity = new BranchEntity();
        if(genealogyEntity.getUserEntity().getId() == userEntity.getId()){
            branchEntity.setName(branch.getName());
            branchEntity.setDescription(branch.getDescription());
            branchEntity.setGenealogyEntity(genealogyEntity);
            branchEntity.setDate(new Date());
            branchEntity.setMember(0);

            BranchEntity newBranch = branchRepository.save(branchEntity);
            genealogyEntity.setBranch(genealogyEntity.getBranch()+1);
            genealogyRepository.save(genealogyEntity);

            UserBranchPermissionEntity userBranchPermissionEntity = new UserBranchPermissionEntity(true, newBranch,
                    userEntity, branchPermissionRepository.findBranchPermissionEntityById(GenealogyBranchRole.ADMIN));
            userBranchPermissionRepository.save(userBranchPermissionEntity);

            ArrayList<Branch> branches = new ArrayList<>();
            Branch createdBranch = parseBranchEntityToBranch(newBranch);
            createdBranch.setRole(GenealogyBranchRole.ADMIN);
            branches.add(createdBranch);
            branchResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
            branchResponse.setBranchList(branches);
            return branchResponse;
        }
        else {
            branchResponse.setError(new MessageResponse(HTTPCodeResponse.UNAUTHORIZED,"Don't have permission"));
            branchResponse.setBranchList(null);
            return branchResponse;
        }
    }

    @Override
    public CodeResponse deleteBranch(String username, Integer branchId) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        BranchEntity branchEntity = branchRepository.findBranchEntityById(branchId);
        if (branchEntity == null){
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"No branch found"));
        }
        else {
            if(branchEntity.getGenealogyEntity().getUserEntity().getId() == userEntity.getId()){
                branchRepository.deleteById(branchId);
                GenealogyEntity genealogyEntity = branchEntity.getGenealogyEntity();
                genealogyEntity.setBranch(genealogyEntity.getBranch()-1);
                genealogyRepository.save(genealogyEntity);
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
            }
            else {
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.UNAUTHORIZED,"Don't have permission"));
            }
        }
        return codeResponse;
    }

    @Override
    public CodeResponse updateBranch(String username, Branch branch) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        BranchEntity branchEntity = branchRepository.findBranchEntityById(branch.getId());
        if (branchEntity == null){
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"No branch found"));
        }
        else {
            if(branchEntity.getGenealogyEntity().getUserEntity().getId() == userEntity.getId()){
                branchEntity.setName(branch.getName());
                branchEntity.setDescription(branch.getDescription());
                branchRepository.save(branchEntity);
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
            }
            else {
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.UNAUTHORIZED,"Don't have permission"));
            }
        }
        return codeResponse;
    }

    public static Branch parseBranchEntityToBranch(BranchEntity branchEntity) {
        Branch branch = new Branch();
        branch.setId(branchEntity.getId());
        branch.setDescription(branchEntity.getDescription());
        branch.setName(branchEntity.getName());
        branch.setDate(branchEntity.getDate());
        branch.setMember(branchEntity.getMember());
        return branch;
    }

    public static List<Branch> parseListBranchEntityToListBranch(List<BranchEntity> branchEntities) {
        List<Branch> branches = new ArrayList<>();
        for (BranchEntity branchEntity : branchEntities) {
            Branch branch = parseBranchEntityToBranch(branchEntity);
            branches.add(branch);
        }
        return branches;
    }

}