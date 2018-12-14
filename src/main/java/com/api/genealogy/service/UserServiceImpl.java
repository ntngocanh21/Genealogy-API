package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.User;
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

    @Override
    public LoginResponse login(User user) {
        MessageResponse messageResponse = new MessageResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(user.getUsername());
        if (userEntity == null) {
            messageResponse.setCode(HTTPCodeResponse.OBJECT_NOT_FOUND);
            messageResponse.setDescription("Wrong Information");
            return new LoginResponse(messageResponse, null, null, null);
        } else {
            if (BCrypt.checkpw(user.getPassword(), userEntity.getPassword())) {
                messageResponse.setCode(HTTPCodeResponse.SUCCESS);
                messageResponse.setDescription("Success");
                userEntity.setDeviceId(user.getDeviceId());
                userRepository.save(userEntity);
                return new LoginResponse(messageResponse, jwtGenerator.generate(userEntity), userEntity.getAvatar(), userEntity.getFullname());
            }
            else{
                messageResponse.setCode(HTTPCodeResponse.OBJECT_NOT_FOUND);
                messageResponse.setDescription("Wrong Information");
                return new LoginResponse(messageResponse, null, null, null);
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
            userRegister.setAddress(user.getAddress());
            userRegister.setAvatar(user.getAvatar());
            userRegister.setBirthday(user.getBirthday());
            userRegister.setGender(user.getGender());
            userRegister.setMail(user.getMail());

            userRegister.setRole("ROLE_MEMBER");
            userRepository.save(userRegister);
            messageResponse.setCode(HTTPCodeResponse.SUCCESS);
            messageResponse.setDescription("You have been successfully registered");
            return new LoginResponse(messageResponse, jwtGenerator.generate(userRepository.findUserEntityByUsername(user.getUsername())), user.getAvatar(), user.getFullname());
        }
        else{
            messageResponse.setCode(HTTPCodeResponse.OBJECT_EXISTED);
            messageResponse.setDescription("Username existed!!");
            return new LoginResponse(messageResponse, null);
        }
    }

    @Override
    public UserResponse getProfile(String currentUserName) {
        List<User> users = new ArrayList<>();
        UserResponse userResponse = new UserResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(currentUserName);
        if (userEntity != null){
            userResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS, "Success"));
            users.add(parseUserEntityToUser(userEntity));
            userResponse.setUserList(users);
        } else {
            userResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND, "No user found"));
        }
        return userResponse;
    }

    @Override
    public UserResponse editProfile(String currentUserName, User user) {
        List<User> users = new ArrayList<>();
        UserResponse userResponse = new UserResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(currentUserName);
        if (userEntity != null){
            userRepository.save(parseUserToUserEntity(user, userEntity));
            userResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS, "Success"));
            users.add(parseUserEntityToUser(userEntity));
            userResponse.setUserList(users);
        } else {
            userResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND, "No user found"));
        }
        return userResponse;
    }

    public User parseUserEntityToUser(UserEntity userEntity) {
        User user = new User();
        user.setFullname(userEntity.getFullname());
        user.setAddress(userEntity.getAddress());
        user.setAvatar(userEntity.getAvatar());
        user.setBirthday(userEntity.getBirthday());
        user.setGender(userEntity.getGender());
        user.setMail(userEntity.getMail());
        user.setDeviceId(userEntity.getDeviceId());
        return user;
    }

    public UserEntity parseUserToUserEntity(User user, UserEntity userEntity) {
        userEntity.setFullname(user.getFullname());
        userEntity.setAddress(user.getAddress());
        userEntity.setAvatar(user.getAvatar());
        userEntity.setBirthday(user.getBirthday());
        userEntity.setGender(user.getGender());
        userEntity.setMail(user.getMail());
        userEntity.setDeviceId(user.getDeviceId());
        return userEntity;
    }

	@Override
	public void updateDeviceId(String deviceId, String username) {
        try {
        	userRepository.updateDeviceId(deviceId, username);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}

	@Override
	public String getDeviceIdFromUsername(String username) {
		try {
			return userRepository.findUserEntityByUsername(username).getDeviceId();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
		return "";
	}
}