package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.UserBranchPermissionEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.Genealogy;
import com.api.genealogy.model.User;
import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.repository.UserBranchPermissionRepository;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.security.JwtGenerator;
import com.api.genealogy.service.response.LoginResponse;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.UserResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserBranchPermissionRepository userBranchPermissionRepository;

    @Override
    public LoginResponse login(User user) {
        MessageResponse messageResponse = new MessageResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(user.getUsername());
        if (userEntity == null) {
            messageResponse.setCode(HTTPCodeResponse.OBJECT_NOT_FOUND);
            messageResponse.setDescription("Wrong Information");
            return new LoginResponse(messageResponse, null);
        } else {
            if (BCrypt.checkpw(user.getPassword(), userEntity.getPassword())) {
                messageResponse.setCode(HTTPCodeResponse.SUCCESS);
                messageResponse.setDescription("Success");
                return new LoginResponse(messageResponse, jwtGenerator.generate(userEntity));
            }
            else{
                messageResponse.setCode(HTTPCodeResponse.OBJECT_NOT_FOUND);
                messageResponse.setDescription("Wrong Information");
                return new LoginResponse(messageResponse, null);
            }
        }
    }

    @Override
    public LoginResponse register(User user) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(user.getUsername());
        MessageResponse messageResponse = new MessageResponse();

        if (userEntity == null) {
            UserEntity userRegister = new UserEntity();
            userRegister.setFullname(user.getFullname());
            userRegister.setUsername(user.getUsername());
            userRegister.setPassword(user.getPassword());
            //userRegister.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            userRegister.setRole("ROLE_MEMBER");
            userRepository.save(userRegister);
            messageResponse.setCode(HTTPCodeResponse.SUCCESS);
            messageResponse.setDescription("You have been successfully registered");
            return new LoginResponse(messageResponse, jwtGenerator.generate(userRepository.findUserEntityByUsername(user.getUsername())));
        }
        else{
            messageResponse.setCode(HTTPCodeResponse.OBJECT_EXISTED);
            messageResponse.setDescription("Username existed!!");
            return new LoginResponse(messageResponse, null);
        }
    }
}