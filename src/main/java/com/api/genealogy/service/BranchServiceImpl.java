package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.Branch;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.GenealogyRepository;
import com.api.genealogy.repository.UserRepository;
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

    @Override
    public BranchResponse getBranchesByGenealogyId(Integer genealogyId) {
        List<BranchEntity> branchEntities = (List<BranchEntity>) branchRepository
                .findBranchEntitiesByGenealogyEntity_Id(genealogyId);
        if (branchEntities.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse(0,"Success");
            BranchResponse branchResponse = new BranchResponse(messageResponse, null);
            return branchResponse;
        }
        List<Branch> branches = parseListBranchEntityToListBranch(branchEntities);
        MessageResponse messageResponse = new MessageResponse(0,"Success");
        BranchResponse branchResponse = new BranchResponse(messageResponse, branches);
        return branchResponse;
    }

    @Override
    public BranchResponse createBranch(String username, Integer genealogyId, Branch branch) {
        BranchResponse branchResponse = new BranchResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = genealogyRepository.findGenealogyEntityById(genealogyId);
        BranchEntity branchEntity = new BranchEntity();
        if(genealogyEntity.getUserEntity().getId() == userEntity.getId()){
            branchEntity.setName(branch.getName());
            branchEntity.setDescription(branch.getDescription());
            branchEntity.setGenealogyEntity(genealogyEntity);
            branchEntity.setDate(new Date());
            branchEntity.setMember(0);

            BranchEntity newBranch = branchRepository.save(branchEntity);

            ArrayList<Branch> branches = new ArrayList<>();
            branches.add(parseBranchEntityToBranch(newBranch));
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
                genealogyRepository.deleteById(branchId);
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

    private Branch parseBranchEntityToBranch(BranchEntity branchEntity) {
        Branch branch = new Branch();
        branch.setId(branchEntity.getId());
        branch.setDescription(branchEntity.getDescription());
        branch.setName(branchEntity.getName());
        branch.setDate(branchEntity.getDate());
        branch.setMember(branchEntity.getMember());
        return branch;
    }

    private List<Branch> parseListBranchEntityToListBranch(List<BranchEntity> branchEntities) {
        List<Branch> branches = new ArrayList<>();
        for (BranchEntity branchEntity : branchEntities) {
            Branch branch = parseBranchEntityToBranch(branchEntity);
            branches.add(branch);
        }
        return branches;
    }

}