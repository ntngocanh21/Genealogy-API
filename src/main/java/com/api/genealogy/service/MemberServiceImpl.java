package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.UserBranchPermissionEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.User;
import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.repository.BranchPermissionRepository;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.UserBranchPermissionRepository;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBranchPermissionRepository userBranchPermissionRepository;

    @Autowired
    private BranchPermissionRepository branchPermissionRepository;

    @Override
    public UserResponse getMemberOfBranch(UserBranchPermission userBranchPermission) {
        UserResponse userResponse = new UserResponse();
        List<UserBranchPermissionEntity> userBranchPermissionEntities
                = userBranchPermissionRepository.findUserBranchPermissionEntitiesByBranchUserEntity_IdAndStatus
                (userBranchPermission.getBranch_id(), userBranchPermission.isStatus());
        userResponse.setError(new MessageResponse(0, "Success"));
        userResponse.setUserList(parseUserBranchPermissionEntityListToUserList(userBranchPermissionEntities));
        return userResponse;
    }

    @Override
    public CodeResponse declineRequestMemberOfBranch(UserBranchPermission member) {
        CodeResponse codeResponse = new CodeResponse();
        UserBranchPermissionEntity userBranchPermissionEntity = userBranchPermissionRepository
                .findUserBranchPermissionEntitiesByUserBranchEntity_UsernameAndBranchUserEntity_Id(member.getUsername(), member.getBranch_id());

        userBranchPermissionRepository.deleteById(userBranchPermissionEntity.getId());
        codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));

        return codeResponse;
    }

    @Override
    public CodeResponse acceptRequestMemberOfBranch(UserBranchPermission member) {
        CodeResponse codeResponse = new CodeResponse();
        UserBranchPermissionEntity userBranchPermissionEntity = userBranchPermissionRepository
                .findUserBranchPermissionEntitiesByUserBranchEntity_UsernameAndBranchUserEntity_Id(member.getUsername(), member.getBranch_id());

        userBranchPermissionEntity.setStatus(true);
        userBranchPermissionRepository.save(userBranchPermissionEntity);
        codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));

        return codeResponse;
    }

    @Override
    public CodeResponse joinBranch(UserBranchPermission userBranchPermission) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(userBranchPermission.getUsername());
        BranchEntity branchEntity = branchRepository.findBranchEntityById(userBranchPermission.getBranch_id());
        UserBranchPermissionEntity userBranchPermissionEntity = new UserBranchPermissionEntity(false, branchEntity, userEntity, branchPermissionRepository.findBranchPermissionEntityById(userBranchPermission.getBranch_permission_id()));
        userBranchPermissionRepository.save(userBranchPermissionEntity);

        CodeResponse codeResponse = new CodeResponse();
        codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        return codeResponse;
    }

    @Override
    public CodeResponse changeRoleMemberOfBranch(UserBranchPermission member) {
        CodeResponse codeResponse = new CodeResponse();
        UserBranchPermissionEntity userBranchPermissionEntity = userBranchPermissionRepository
                .findUserBranchPermissionEntitiesByUserBranchEntity_UsernameAndBranchUserEntity_Id(member.getUsername(), member.getBranch_id());

        userBranchPermissionEntity.setBranchPermissionEntity(branchPermissionRepository.findBranchPermissionEntityById(member.getBranch_permission_id()));
        userBranchPermissionRepository.save(userBranchPermissionEntity);
        codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));

        return codeResponse;
    }

    private User parseUserBranchPermissionEntityToUser (UserBranchPermissionEntity userBranchPermissionEntity){
        User user = new User();
        user.setUsername(userBranchPermissionEntity.getUserBranchEntity().getUsername());
        user.setFullname(userBranchPermissionEntity.getUserBranchEntity().getFullname());
        user.setRole(userBranchPermissionEntity.getBranchPermissionEntity().getId());
        return user;
    }

    private List<User> parseUserBranchPermissionEntityListToUserList (List<UserBranchPermissionEntity> userBranchPermissionEntityList){
        List<User> users = new ArrayList<>();
        for (UserBranchPermissionEntity userBranchPermissionEntity : userBranchPermissionEntityList) {
            User user = parseUserBranchPermissionEntityToUser(userBranchPermissionEntity);
            users.add(user);
        }
        return users;
    }
}