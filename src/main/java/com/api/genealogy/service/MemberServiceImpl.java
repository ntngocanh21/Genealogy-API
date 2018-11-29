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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MemberServiceImpl implements MemberService {

    private final String TOPIC = "JavaSampleApproach";

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

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

        // Định nghĩa nội dung gửi đi
        JSONObject body = new JSONObject();
        Notification item = new Notification();
        item.setTitle("Member Request Join Branch");
        item.setType(PushNotificateionType.MEMBER_JOIN);
        item.setContent(userEntity.getFullname() + "joined in" + branchEntity.getName() + " of " + branchEntity.getGenealogyEntity().getName() + "Please approve request from Genealogy application");
        /** Lấy deviceId của chủ branch => bắn về */


        item.setDeviceId(branchEntity.getGenealogyEntity().getUserEntity().getDeviceId());
        // Tạm thời bỏ qua; Giá trị mặc định 0, nhận tự update 1
//    	item.setIsPushed(0);
        // Biết chính xác thằng nào request lên.
        item.setUsername(userEntity.getUsername());
        // Lưu vào DB
        // Đưa message lên Firebase
        notificationService.addNotification(item);
        try {
            body.put("to", "/topics/" + TOPIC);
            body.put("priority", "high");

            // Chạy nền cái app sẻ hiện ra
            JSONObject notification = new JSONObject();
            notification.put("title", item.getTitle());
            notification.put("body", item.getContent());

            // Không chạy nền
            JSONObject data = new JSONObject();
            data.put("title", "Custom Message Firebase");
            data.put("message", "Testing Again");
            data.put("image", "https://api.androidhive.info/images/minion.jpg");
            data.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

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