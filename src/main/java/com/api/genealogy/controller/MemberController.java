package com.api.genealogy.controller;

import com.api.genealogy.constant.PushNotificateionType;
import com.api.genealogy.model.Notification;
import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.service.AndroidPushNotificationsService;
import com.api.genealogy.service.AndroidPushNotificationsServiceImpl;
import com.api.genealogy.service.MemberService;
import com.api.genealogy.service.NotificationService;
import com.api.genealogy.service.UserService;
import com.mysql.cj.log.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class MemberController {
	

	
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationService notificationService;
   

    @Autowired
	private AndroidPushNotificationsService androidPushNotificationsService;
    
    /**
     * Step procedure:
     * - Open Genealogy Application, move to Branches item in NavigationBar.
     * - Click type of genealogy that you want to pick.
     * - Then click member to get information of Member.
     * @param userBranchPermission
     * @return
     */
    @PostMapping("/member")
    public ResponseEntity getMemberOfBranch(@RequestBody UserBranchPermission userBranchPermission) {
        return new ResponseEntity<>(memberService.getMemberOfBranch(userBranchPermission), HttpStatus.OK);
    }

    @DeleteMapping("/member")
    public ResponseEntity declineRequestMemberOfBranch(@RequestBody UserBranchPermission userBranchPermission) {
        return new ResponseEntity<>(memberService.declineRequestMemberOfBranch(userBranchPermission), HttpStatus.OK);
    }

    @PutMapping("/member")
    public ResponseEntity acceptRequestMemberOfBranch(@RequestBody UserBranchPermission userBranchPermission) {
        return new ResponseEntity<>(memberService.acceptRequestMemberOfBranch(userBranchPermission), HttpStatus.OK);
    }

    @PutMapping("/member/role")
    public ResponseEntity changeRoleMemberOfBranch(@RequestBody UserBranchPermission userBranchPermission) {
        return new ResponseEntity<>(memberService.changeRoleMemberOfBranch(userBranchPermission), HttpStatus.OK);
    }

    @PostMapping("/member/branch")
    public ResponseEntity joinBranch(@RequestBody UserBranchPermission userBranchPermission) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		userBranchPermission.setUsername(currentUserName);
        return new ResponseEntity<>(memberService.joinBranch(userBranchPermission), HttpStatus.OK);
    }
    
}
