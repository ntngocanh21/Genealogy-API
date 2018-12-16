package com.api.genealogy.service;

import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.UserResponse;

public interface MemberService {
    UserResponse getMemberOfBranch(UserBranchPermission userBranchPermission);
    CodeResponse declineRequestMemberOfBranch(UserBranchPermission userBranchPermission);
    CodeResponse acceptRequestMemberOfBranch(UserBranchPermission userBranchPermission);
    CodeResponse changeRoleMemberOfBranch(UserBranchPermission userBranchPermission);
    CodeResponse joinBranch(UserBranchPermission userBranchPermission);
    CodeResponse outBranch(UserBranchPermission userBranchPermission);
}
