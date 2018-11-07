package com.api.genealogy.controller;

import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private MemberService memberService;

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
}
