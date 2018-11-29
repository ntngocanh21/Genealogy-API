package com.api.genealogy.controller;

import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.service.AndroidPushNotificationsService;
import com.api.genealogy.service.AndroidPushNotificationsServiceImpl;
import com.api.genealogy.service.MemberService;
import com.mysql.cj.log.Log;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class MemberController {
	
	private final String TOPIC = "JavaSampleApproach";
	
    @Autowired
    private MemberService memberService;

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
    	String url_image = "https://api.androidhive.info/images/minion.jpg";
    	JSONObject body = new JSONObject();
		try {
			body.put("to", "/topics/" + TOPIC);
			body.put("priority", "high");
			 
			JSONObject notification = new JSONObject();
			notification.put("title", "JSA Notification");
			notification.put("body", "Happy Message!");
			
			JSONObject data = new JSONObject();
			data.put("title", "Custom Message Firebase");
			data.put("message", "Testing Again");
			data.put("is_background", "true");
			data.put("image", url_image);
			data.put("timestamp", String.valueOf(new Date().getTime()));
	 
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
        return new ResponseEntity<>(memberService.joinBranch(userBranchPermission), HttpStatus.OK);
    }
    
}
