package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.LoginResponse;
import com.api.genealogy.model.MessageResponse;
import com.api.genealogy.model.User;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.security.JwtGenerator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtGenerator jwtGenerator;

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
    public MessageResponse register(User user) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(user.getUsername());
        MessageResponse messageResponse = new MessageResponse();
        if (userEntity == null) {
            UserEntity userRegister = new UserEntity();
            userRegister.setFullname(user.getFullname());
            userRegister.setUsername(user.getUsername());
            userRegister.setPassword(user.getPassword());
            userRegister.setRole("ROLE_MEMBER");
            userRegister.setCreatedDate(new Date());
            userRegister.setLastUpdated(new Date());
            userRepository.save(userRegister);
            messageResponse.setCode(HTTPCodeResponse.SUCCESS);
            messageResponse.setDescription("You have been successfully registered");
            return messageResponse;
        }
        else{
            messageResponse.setCode(HTTPCodeResponse.OBJECT_EXISTED);
            messageResponse.setDescription("Username existed!!");
            return messageResponse;
        }
    }
}