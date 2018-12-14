package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.constant.PushNotificateionType;
import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.UserBranchPermissionEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.Notification;
import com.api.genealogy.model.User;
import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.repository.BranchPermissionRepository;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.NotificationTypeReponsitory;
import com.api.genealogy.repository.UserBranchPermissionRepository;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.UserResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

    @Autowired
    private UserBranchPermissionRepository userBranchPermissionRepository;

    @Autowired
    private BranchPermissionRepository branchPermissionRepository;
    
    @Autowired
    private NotificationTypeReponsitory notificationTypeReponsitory;

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

        JSONObject body = new JSONObject();
        Notification item = new Notification();
        item.setTitle("Your request join branch has been accepted");
        item.setNotification_type_id(notificationTypeReponsitory.findNotificationTypeEntityByNotificationName(PushNotificateionType.ACCEPT_JOIN).getId());
        item.setContent("Your request join branch " + branchRepository.findBranchEntityById(member.getBranch_id()).getName() + " has been accepted");
        item.setUser_id(userRepository.findUserEntityByUsername(member.getUsername()).getId());
        item.setReadStatus(false);
        notificationService.addNotification(item);
        try {
            body.put("to", "/topics/" + userRepository.findUserEntityByUsername(member.getUsername()).getDeviceId());
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", item.getTitle());
            notification.put("body", item.getContent());
            JSONObject data = new JSONObject();
            body.put("notification", notification);
            body.put("data", data);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            System.out.println("Firebase response"
                    + "\n Response: " + firebaseResponse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return codeResponse;
    }

    @Override
    public CodeResponse joinBranch(UserBranchPermission userBranchPermission) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(userBranchPermission.getUsername());
        BranchEntity branchEntity = branchRepository.findBranchEntityById(userBranchPermission.getBranch_id());
        UserBranchPermissionEntity userBranchPermissionEntity = new UserBranchPermissionEntity(false, branchEntity, userEntity, branchPermissionRepository.findBranchPermissionEntityById(userBranchPermission.getBranch_permission_id()));
        userBranchPermissionRepository.save(userBranchPermissionEntity);

        JSONObject body = new JSONObject();
        Notification item = new Notification();
        item.setTitle("Member Request Join Branch");
        item.setNotification_type_id(notificationTypeReponsitory.findNotificationTypeEntityByNotificationName(PushNotificateionType.MEMBER_JOIN).getId());
        item.setContent(userEntity.getFullname() + " joined in " + branchEntity.getName() + " of " + branchEntity.getGenealogyEntity().getName() + "Please approve request from Genealogy application");
        item.setUser_id(branchEntity.getGenealogyEntity().getUserEntity().getId());
        item.setReadStatus(false);
        notificationService.addNotification(item);
        try {
            body.put("to", "/topics/" + branchEntity.getGenealogyEntity().getUserEntity().getDeviceId());
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", item.getTitle());
            notification.put("body", item.getContent());
            JSONObject data = new JSONObject();
            body.put("notification", notification);
            body.put("data", data);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            System.out.println("Firebase Response"
                    + "\n Response: " + firebaseResponse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        CodeResponse codeResponse = new CodeResponse();
        codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        return codeResponse;
    }

    @Override
    public CodeResponse outBranch(UserBranchPermission userBranchPermission) {
        CodeResponse codeResponse = new CodeResponse();
        UserBranchPermissionEntity userBranchPermissionEntity = userBranchPermissionRepository
                .findUserBranchPermissionEntitiesByUserBranchEntity_UsernameAndBranchUserEntity_Id
                (userBranchPermission.getUsername(), userBranchPermission.getBranch_id());
        if(userBranchPermissionEntity != null){
            userBranchPermissionRepository.deleteById(userBranchPermissionEntity.getId());
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        } else {
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"False"));
        }
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